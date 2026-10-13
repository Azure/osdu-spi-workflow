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

import com.azure.security.keyvault.secrets.SecretClient;
import lombok.Getter;
import lombok.Setter;
import org.opengroup.osdu.azure.KeyVaultFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties("osdu.azure.redis")
@Getter
@Setter
public class RedisConfig {
  /**
   * NOTE:
   * Redis instance used here is used for caching purposes and is not impacted by single / multi partition
   * The redis instance used here is the one that resides in service resource
   * the hostname and password for which is obtained from the central key vault
   */
  private boolean defaultRedisConfig = true;
  private String redisHost;
  private String redisPassword;
  private int redisPort;
  private int workflowMetadataTtl;
  private int activeDagRunsTtl;

  @Autowired
  private SecretClient kv;

  @PostConstruct
  private void setupRedisConfig() {
    if (this.isDefaultRedisConfig()) {
      this.redisHost = KeyVaultFacade.getSecretWithValidation(kv, "redis-hostname");;
      this.redisPassword = KeyVaultFacade.getSecretWithValidation(kv, "redis-password");;
    }
  }
}
