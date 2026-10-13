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

package org.opengroup.osdu.workflow.provider.azure.fileshare;

import com.azure.storage.file.share.ShareServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opengroup.osdu.azure.partition.PartitionInfoAzure;
import org.opengroup.osdu.azure.partition.PartitionServiceClient;
import org.opengroup.osdu.workflow.provider.azure.cache.FileShareServiceClientCache;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DAGFileShareServiceClientFactoryImplTest {
  private static final String ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_NAME =
      "AIRFLOW_STORAGE_ACCOUNT_NAME";
  private static final String ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_KEY = "AIRFLOW_STORAGE_ACCOUNT_KEY";
  private static final String AIRFLOW_STORAGE_ACCOUNT_NAME = "airflow-storage-account";
  private static final String AIRFLOW_STORAGE_ACCOUNT_KEY = "airflow-storage-account-key";
  private static final String PARTITION_ID = "dataPartitionId";

  @Mock
  private PartitionServiceClient partitionService;

  @Mock
  private FileShareServiceClientCache clientCache;

  @InjectMocks
  private DAGFileShareServiceClientFactoryImpl dagFileShareServiceClientFactory;

  @Test
  void testGetFileShareServiceClient_whenAirflowEnabledIsTrue() {
    String CACHE_KEY_FORMAT = "%s-fileShareServiceClient";
    String cacheKey = String.format(CACHE_KEY_FORMAT, PARTITION_ID);

    PartitionInfoAzure pi = mock(PartitionInfoAzure.class);
    when(partitionService.getPartition(PARTITION_ID)).thenReturn(pi);
    when(pi.getAirflowEnabled()).thenReturn(true);
    when(pi.getStorageAccountName()).thenReturn(AIRFLOW_STORAGE_ACCOUNT_NAME);
    when(pi.getStorageAccountKey()).thenReturn(AIRFLOW_STORAGE_ACCOUNT_KEY);

    ShareServiceClient shareServiceClient = dagFileShareServiceClientFactory.getFileShareServiceClient(PARTITION_ID);

    verify(clientCache).put(eq(cacheKey), eq(shareServiceClient));
  }
}
