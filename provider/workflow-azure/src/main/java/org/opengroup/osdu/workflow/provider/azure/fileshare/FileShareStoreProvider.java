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

import org.opengroup.osdu.azure.partition.PartitionServiceClient;
import org.opengroup.osdu.core.common.logging.ILogger;
import org.opengroup.osdu.workflow.provider.azure.cache.FileShareServiceClientCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileShareStoreProvider {

  @Bean("IngestFileShareServiceClientFactory")
  public IFileShareServiceClientFactory buildFileShareClientFactory(final PartitionServiceClient partitionServiceClient,
                                                                    final FileShareServiceClientCache fileShareServiceClientCache) {
    return new DAGFileShareServiceClientFactoryImpl(partitionServiceClient, fileShareServiceClientCache);
  }

  @Bean
  @Qualifier("IngestFileShareStore")
  public FileShareStore buildFileShareStore(@Qualifier("IngestFileShareServiceClientFactory") final IFileShareServiceClientFactory fileShareServiceClientFactory, final ILogger logger) {
    return new FileShareStore(fileShareServiceClientFactory, logger);
  }
}
