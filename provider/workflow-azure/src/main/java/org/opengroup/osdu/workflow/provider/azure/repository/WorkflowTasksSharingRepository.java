// Copyright © Microsoft Corporation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.opengroup.osdu.workflow.provider.azure.repository;

import com.azure.storage.blob.sas.BlobContainerSasPermission;
import org.opengroup.osdu.azure.blobstorage.BlobStore;
import org.opengroup.osdu.azure.cosmosdb.CosmosStore;
import org.opengroup.osdu.core.common.logging.JaxRsDpsLog;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;
import org.opengroup.osdu.workflow.exception.WorkflowNotFoundException;
import org.opengroup.osdu.workflow.provider.azure.config.CosmosConfig;
import org.opengroup.osdu.workflow.provider.azure.interfaces.IWorkflowTasksSharingRepository;
import org.opengroup.osdu.workflow.provider.azure.model.WorkflowTasksSharingDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class WorkflowTasksSharingRepository implements IWorkflowTasksSharingRepository {

  private static final String LOGGER_NAME = WorkflowTasksSharingRepository.class.getName();

  @Autowired
  @Qualifier("IngestBlobStore")
  BlobStore blobStore;

  @Autowired
  DpsHeaders dpsHeaders;

  @Autowired
  CosmosStore cosmosStore;

  @Autowired
  CosmosConfig cosmosConfig;

  @Autowired
  private JaxRsDpsLog logger;

  @Override
  public String getSignedUrl(String workflowName, String runId) {
    final String dataPartitionId = dpsHeaders.getPartitionId();
    final OffsetDateTime startTime = OffsetDateTime.now();
    final int expiryDays = 1;
    final OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(expiryDays);
    // TODO 18.03.21 (expires at 18.09.21): Add support for custom permission (?)
    final BlobContainerSasPermission permissions = new BlobContainerSasPermission()
        .setCreatePermission(true)
        .setReadPermission(true)
        .setWritePermission(true)
        .setListPermission(true);

    final Optional<WorkflowTasksSharingDoc> optionalWorkflowTasksSharingDoc =
        cosmosStore.findItem(dataPartitionId, cosmosConfig.getDatabase(), cosmosConfig.getWorkflowTasksSharingCollection(), runId, workflowName, WorkflowTasksSharingDoc.class);

    String containerId;

    if (optionalWorkflowTasksSharingDoc.isPresent()) {
      containerId = optionalWorkflowTasksSharingDoc.get().getContainerId();
    } else {
      containerId = UUID.randomUUID().toString();
      blobStore.createBlobContainer(dataPartitionId, containerId);
      WorkflowTasksSharingDoc workflowTasksSharingDocNewContainer = workflowTasksSharingDocBuilder(workflowName, runId, containerId);
      cosmosStore.createItem(dataPartitionId, cosmosConfig.getDatabase(),
          cosmosConfig.getWorkflowTasksSharingCollection(), workflowTasksSharingDocNewContainer.getPartitionKey(), workflowTasksSharingDocNewContainer);
    }
    return blobStore.generatePreSignedUrlWithUserDelegationSas(dataPartitionId, containerId, startTime, expiryTime, permissions);
  }

  WorkflowTasksSharingDoc workflowTasksSharingDocBuilder(String workflowName, String runId, String containerId) {
    return WorkflowTasksSharingDoc.builder()
        .id(runId)
        .partitionKey(workflowName)
        .containerId(containerId)
        .workflowName(workflowName)
        .runId(runId)
        .createdAt(System.currentTimeMillis())
        .createdBy(dpsHeaders.getUserEmail())
        .build();
  }

  public void deleteTasksSharingInfoContainer(String dataPartitionId, String workflowName, String runId) throws WorkflowNotFoundException {
    final Optional<WorkflowTasksSharingDoc> optionalWorkflowTasksSharingDoc =
        cosmosStore.findItem(dataPartitionId, cosmosConfig.getDatabase(), cosmosConfig.getWorkflowTasksSharingCollection(), runId, workflowName, WorkflowTasksSharingDoc.class);

    if (optionalWorkflowTasksSharingDoc.isPresent()) {
      String containerId = optionalWorkflowTasksSharingDoc.get().getContainerId();
      blobStore.deleteBlobContainer(dataPartitionId, containerId);
      cosmosStore.deleteItem(
          dataPartitionId,
          cosmosConfig.getDatabase(),
          cosmosConfig.getWorkflowTasksSharingCollection(),
          runId,
          workflowName);
    }
  }
}
