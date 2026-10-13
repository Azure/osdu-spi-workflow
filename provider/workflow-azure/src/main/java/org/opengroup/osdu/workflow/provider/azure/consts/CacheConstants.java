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

package org.opengroup.osdu.workflow.provider.azure.consts;

public class CacheConstants {
  // Number of seconds the information for active number of dag runs will be retained in the local cache
  public static final int ACTIVE_DAG_RUNS_LOCAL_CACHE_EXPIRATION_SECONDS = 20;
  // Maximum number of entries the active dag runs local cache will contain
  public static final int ACTIVE_DAG_RUNS_LOCAL_CACHE_MAXIMUM_SIZE = 1000;
  // Key corresponding to which the count of active dag runs is stored in the cache
  public static final String ACTIVE_DAG_RUNS_COUNT_CACHE_KEY = "active-dag-runs-count";
  // Number of seconds the workflow metadata will be retained in the local cache
  public static final int WORKFLOW_METADATA_LOCAL_CACHE_EXPIRATION_SECONDS = 600;
  // Maximum number of entries the workflow metadata local cache will contain
  public static final int WORKFLOW_METADATA_LOCAL_CACHE_MAXIMUM_SIZE = 1000;
}
