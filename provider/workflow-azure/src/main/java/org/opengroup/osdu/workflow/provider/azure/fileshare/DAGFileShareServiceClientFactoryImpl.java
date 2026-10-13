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

import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.file.share.ShareServiceClient;
import com.azure.storage.file.share.ShareServiceClientBuilder;
import org.opengroup.osdu.azure.partition.PartitionInfoAzure;
import org.opengroup.osdu.azure.partition.PartitionServiceClient;
import org.opengroup.osdu.common.Validators;
import org.opengroup.osdu.workflow.provider.azure.cache.FileShareServiceClientCache;

/**
 * Implementation for IFileShareServiceClientFactory.
 */
public class DAGFileShareServiceClientFactoryImpl implements IFileShareServiceClientFactory {
  private static final String CACHE_KEY_FORMAT = "%s-fileShareServiceClient";
  private static final String SYSTEM_CACHE_KEY = "system_fileShareServiceClient";
  private static final String ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_NAME =
      "AIRFLOW_STORAGE_ACCOUNT_NAME";
  private static final String ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_KEY = "AIRFLOW_STORAGE_ACCOUNT_KEY";

  private PartitionServiceClient partitionService;
  private FileShareServiceClientCache clientCache;

  /**
   * Constructor to initialize instance of {@link DAGFileShareServiceClientFactoryImpl}.
   * @param partitionServiceClient        Partition service client to use
   * @param fileShareServiceClientCache   File share service client cache to use
   */
  public DAGFileShareServiceClientFactoryImpl(final PartitionServiceClient partitionServiceClient,
                                              final FileShareServiceClientCache fileShareServiceClientCache) {
    this.partitionService = partitionServiceClient;
    this.clientCache = fileShareServiceClientCache;
  }

  /**
   * @param dataPartitionId data partition id.
   * @return ShareServiceClient corresponding to the given data partition id.
   */
  @Override
  public ShareServiceClient getFileShareServiceClient(final String dataPartitionId) {
    Validators.checkNotNullAndNotEmpty(dataPartitionId, "dataPartitionId");

    String cacheKey = String.format(CACHE_KEY_FORMAT, dataPartitionId);
    if (this.clientCache.containsKey(cacheKey)) {
      return this.clientCache.get(cacheKey);
    }

    PartitionInfoAzure pi = this.partitionService.getPartition(dataPartitionId);
    ShareServiceClient shareServiceClient;
    if(pi.getAirflowEnabled()) {
      shareServiceClient = buildShareServiceClient(pi.getStorageAccountName(),
          pi.getStorageAccountKey());
    } else {
      shareServiceClient = buildShareServiceClient(
          System.getenv(ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_NAME),
          System.getenv(ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_KEY));
    }

    this.clientCache.put(cacheKey, shareServiceClient);
    return shareServiceClient;
  }

  @Override
  public ShareServiceClient getSystemFileShareServiceClient() {
    if (this.clientCache.containsKey(SYSTEM_CACHE_KEY)) {
      return this.clientCache.get(SYSTEM_CACHE_KEY);
    }

    ShareServiceClient shareServiceClient = buildShareServiceClient(
        System.getenv(ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_NAME),
        System.getenv(ENV_KEY_AIRFLOW_STORAGE_ACCOUNT_KEY));

    this.clientCache.put(SYSTEM_CACHE_KEY, shareServiceClient);
    return shareServiceClient;
  }

  private ShareServiceClient buildShareServiceClient(String accountName, String accountKey) {
    String endpoint = String.format("https://%s.file.core.windows.net", accountName);
    final StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

    return new ShareServiceClientBuilder()
        .credential(credential)
        .endpoint(endpoint)
        .buildClient();
  }
}
