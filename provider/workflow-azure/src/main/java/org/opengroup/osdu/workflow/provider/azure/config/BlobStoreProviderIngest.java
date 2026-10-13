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

package org.opengroup.osdu.workflow.provider.azure.config;

import com.azure.identity.DefaultAzureCredential;
import org.opengroup.osdu.azure.blobstorage.BlobStore;
import org.opengroup.osdu.azure.blobstorage.IBlobServiceClientFactory;
import org.opengroup.osdu.azure.logging.DependencyLogger;
import org.opengroup.osdu.azure.partition.PartitionServiceClient;
import org.opengroup.osdu.core.common.logging.ILogger;
import org.opengroup.osdu.workflow.provider.azure.cache.IngestBlobServiceClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobStoreProviderIngest {

  @Autowired
  DefaultAzureCredential defaultAzureCredential;

  @Autowired
  PartitionServiceClient partitionService;

  @Autowired
  IngestBlobServiceClientCache clientCache;

  @Bean
  @Qualifier("IngestBlobServiceClientFactory")
  public IBlobServiceClientFactory buildBlobClientFactory(final DefaultAzureCredential defaultAzureCredential,
                                                          final PartitionServiceClient partitionServiceClient,
                                                          final IngestBlobServiceClientCache blobServiceClientCache) {
    return new BlobServiceIngestClientFactory(defaultAzureCredential, partitionServiceClient, blobServiceClientCache);
  }

  @Bean
  @Qualifier("IngestBlobStore")
  public BlobStore buildBlobStore(@Qualifier("IngestBlobServiceClientFactory") final IBlobServiceClientFactory blobServiceClientFactory, final ILogger logger, DependencyLogger depLogger) {
    return new BlobStore(blobServiceClientFactory, logger, depLogger);
  }
}
