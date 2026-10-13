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

import org.opengroup.osdu.core.common.cache.VmCache;
import org.opengroup.osdu.workflow.provider.azure.interfaces.IActiveDagRunsCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static org.opengroup.osdu.workflow.provider.azure.consts.CacheConstants.ACTIVE_DAG_RUNS_LOCAL_CACHE_EXPIRATION_SECONDS;
import static org.opengroup.osdu.workflow.provider.azure.consts.CacheConstants.ACTIVE_DAG_RUNS_LOCAL_CACHE_MAXIMUM_SIZE;

@Component("ActiveDagRunsCache")
@ConditionalOnProperty(value = "runtime.env.local", havingValue = "true")
public class ActiveDagRunsVmCache extends VmCache<String, Integer> implements IActiveDagRunsCache<String, Integer> {
  public ActiveDagRunsVmCache() {
    super(ACTIVE_DAG_RUNS_LOCAL_CACHE_EXPIRATION_SECONDS, ACTIVE_DAG_RUNS_LOCAL_CACHE_MAXIMUM_SIZE);
  }

  @Override
  public void incrementKey(String key) {
    Integer value = this.get(key);
    this.put(key, value + 1);
  }

  @Override
  public void decrementKey(String key) {
    Integer value = this.get(key);
    this.put(key, value - 1);
  }
}
