// Copyright 2017-2019, Schlumberger
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
package org.opengroup.osdu.azure.workflow.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static org.opengroup.osdu.azure.workflow.framework.util.CreateWorkflowTestsBuilder.WORKFLOW_ACTIVE;
import static org.opengroup.osdu.azure.workflow.framework.util.CreateWorkflowTestsBuilder.WORKFLOW_CONCURRENT_TASK_RUN;
import static org.opengroup.osdu.azure.workflow.framework.util.CreateWorkflowTestsBuilder.WORKFLOW_CONCURRENT_WORKFLOW_RUN;
import static org.opengroup.osdu.azure.workflow.framework.util.CreateWorkflowTestsBuilder.WORKFLOW_DESCRIPTION;
import static org.opengroup.osdu.workflow.consts.TestConstants.CREATE_WORKFLOW_WORKFLOW_NAME;


public class AzurePayLoadBuilder {

  private static final String DAG_CONTENT = "test-dag-content";

  public static String buildCreateWorkflowValidPayloadWithDagContent() {
    Map<String, String> registrationInstructions = new HashMap<String, String>();
    registrationInstructions.put("dagContent", DAG_CONTENT);
    Map<String, Object> payload = new HashMap<>();
    payload.put("workflowName", CREATE_WORKFLOW_WORKFLOW_NAME);
    payload.put("registrationInstructions", registrationInstructions);
    payload.put("description", "Test workflow record for integration tests.");
    return new Gson().toJson(payload);
  }

  public static String buildCreateWorkflowValidPayloadWithGivenDescription(String description) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("workflowName", CREATE_WORKFLOW_WORKFLOW_NAME);
    payload.put("registrationInstructions", new HashMap<String, String>());
    payload.put("description", description);
    return new Gson().toJson(payload);
  }

  public static Map<String, Object> buildCreateWorkflowRequestWithRegistrationInstructions(String workflowName) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("workflowName", workflowName);

    Map<String, Object> registrationInstructions = new HashMap<>();
    registrationInstructions.put("dagName", workflowName);
    registrationInstructions.put("concurrentWorkflowRun", WORKFLOW_CONCURRENT_WORKFLOW_RUN);
    registrationInstructions.put("concurrentTaskRun", WORKFLOW_CONCURRENT_TASK_RUN);
    registrationInstructions.put("active", WORKFLOW_ACTIVE);

    payload.put("registrationInstructions", registrationInstructions);
    payload.put("description",WORKFLOW_DESCRIPTION);

    return payload;
  }
}
