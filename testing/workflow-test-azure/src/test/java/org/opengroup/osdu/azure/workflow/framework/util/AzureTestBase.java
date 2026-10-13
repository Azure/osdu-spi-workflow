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

import org.opengroup.osdu.workflow.util.v3.RetryException;
import org.opengroup.osdu.workflow.util.v3.TestBase;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.opengroup.osdu.workflow.consts.TestConstants.FINISHED_WORKFLOW_RUN_STATUSES;

public abstract class AzureTestBase extends TestBase {

  // This change can be moved to core by modifying the logic to extract workflow name from createdWorkflowRuns rather then
  // hard coded value of CREATE_WORKFLOW_WORKFLOW_NAME
  @Override
  protected void waitForWorkflowRunsToComplete(List<Map<String, String>> createdWorkflowRuns,
                                               Set<String> completedWorkflowRunIds) throws Exception {
    for(Map<String, String> createdWorkflow: createdWorkflowRuns) {
      String workflowName = createdWorkflow.get(WORKFLOW_NAME_FIELD) != null ? createdWorkflow.get(WORKFLOW_NAME_FIELD) : createdWorkflow.get(WORKFLOW_ID_FIELD);
      String workflowRunId = createdWorkflow.get(WORKFLOW_RUN_ID_FIELD);
      if(!completedWorkflowRunIds.contains(workflowRunId)) {
        String workflowRunStatus;
        try {
          workflowRunStatus = getWorkflowRunStatus(workflowName, workflowRunId);
        } catch (Exception e) {
          throw new RetryException(e.getMessage());
        }
        if(FINISHED_WORKFLOW_RUN_STATUSES.contains(workflowRunStatus)) {
          completedWorkflowRunIds.add(workflowRunId);
        } else {
          throw new RetryException(String.format(
              "Unexpected status %s received for workflow run id %s", workflowRunStatus,
              workflowRunId));
        }
      }
    }
  }

}
