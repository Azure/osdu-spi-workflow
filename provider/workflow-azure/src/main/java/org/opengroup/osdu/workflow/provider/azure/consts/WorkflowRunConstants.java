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

public class WorkflowRunConstants {
  // Default number of workflow runs to send in the response when no limit is specified for get all run instances
  public static final int DEFAULT_WORKFLOW_RUNS_LIMIT = 50;
  // Maximum number of workflow runs to that can be sent in the response for get all run instances
  public static final int MAX_WORKFLOW_RUNS_LIMIT = 500;
  // As per the api spec, prefix for workflow run id cannot contain the word "backfill".
  public static final String INVALID_WORKFLOW_RUN_PREFIX = "backfill";
}
