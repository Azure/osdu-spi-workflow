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

package org.opengroup.osdu.workflow.provider.azure.cache;

import io.lettuce.core.codec.RedisCodec;
import org.opengroup.osdu.core.common.cache.JsonCodec;
import org.opengroup.osdu.core.common.cache.RedisCache;
import org.opengroup.osdu.workflow.provider.azure.config.RedisConfig;
import org.opengroup.osdu.workflow.provider.azure.interfaces.IActiveDagRunsCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component("ActiveDagRunsCache")
@ConditionalOnProperty(value = "runtime.env.local", havingValue = "false", matchIfMissing = true)
public class ActiveDagRunsRedisCache extends RedisCache<String, Integer> implements IActiveDagRunsCache<String, Integer> {

  public ActiveDagRunsRedisCache(final RedisConfig redisConfig) {
    super(redisConfig.getRedisHost(), redisConfig.getRedisPort(), redisConfig.getRedisPassword(), redisConfig.getActiveDagRunsTtl(), String.class, Integer.class);
  }

  @Override
  public void incrementKey(String key) {
    this.increment(key);
  }

  @Override
  public void decrementKey(String key) {
    this.decrement(key);
  }

  @Override
  public RedisCodec<String, Integer> getCodec(Class<String> classOfK, Class<Integer> classOfV) {
    return new JsonCodec<>(classOfK, classOfV);
  }
}
