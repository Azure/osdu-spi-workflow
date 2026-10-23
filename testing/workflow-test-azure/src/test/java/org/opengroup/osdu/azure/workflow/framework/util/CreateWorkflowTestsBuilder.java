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

package org.opengroup.osdu.azure.workflow.framework.util;

public class CreateWorkflowTestsBuilder {

  //WORKFLOW CREATE REQUEST BODY
  public static final String WORKFLOW_DESCRIPTION = "This is a test workflow";
  public static final int WORKFLOW_CONCURRENT_WORKFLOW_RUN = 5;
  public static final int WORKFLOW_CONCURRENT_TASK_RUN = 5;
  public static final Boolean WORKFLOW_ACTIVE = true;

  //WORKFLOW CREATE RESPONSE BODY FIELDS
  public static final String WORKFLOW_ID_FIELD = "workflowId";
  public static final String WORKFLOW_NAME_FIELD = "workflowName";
  public static final String WORKFLOW_DESCRIPTION_FIELD = "description";
  public static final String WORKFLOW_CONCURRENT_WORKFLOW_RUN_FIELD = "concurrentWorkflowRun";
  public static final String WORKFLOW_CONCURRENT_TASK_RUN_FIELD = "concurrentTaskRun";
  public static final String WORKFLOW_ACTIVE_FIELD = "active";
}
