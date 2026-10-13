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

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static org.opengroup.osdu.azure.workflow.framework.util.WorkflowUtil.getUniqueWorkflowName;

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

  public static String getValidCreateWorkflowRequest(String dagName, String dagTemplateName) throws Exception{
    String workflowName = getUniqueWorkflowName(dagName);
    Map<String, Object> dagTemplateContext = new HashMap<>();
    dagTemplateContext.put("dagId", workflowName);
    String dagContent =  TestResourceProvider.getDAGFileContent(dagTemplateName, dagTemplateContext);
    return new Gson().toJson(buildCreateWorkflowRequest(workflowName, dagContent));
  }

  public static String getInvalidCreateWorkflowRequest(String dagName, String dagTemplateName) throws Exception{
    String workflowName = getUniqueWorkflowName(dagName);
    Map<String, Object> dagTemplateContext = new HashMap<>();
    dagTemplateContext.put("dagId", workflowName);
    String dagContent = TestResourceProvider.getDAGFileContent(dagTemplateName, dagTemplateContext);
    return buildInvalidCreateWorkflowRequest(workflowName,dagContent);
  }

  public static Map<String, Object> buildCreateWorkflowRequest(String workflowName,
                                                               String workflowContent) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("workflowName", workflowName);

    Map<String, Object> registrationInstructions = new HashMap<>();
    registrationInstructions.put("dagName", workflowName);
    registrationInstructions.put("dagContent", workflowContent);
    registrationInstructions.put("concurrentWorkflowRun", WORKFLOW_CONCURRENT_WORKFLOW_RUN);
    registrationInstructions.put("concurrentTaskRun", WORKFLOW_CONCURRENT_TASK_RUN);
    registrationInstructions.put("active", WORKFLOW_ACTIVE);

    payload.put("registrationInstructions", registrationInstructions);
    payload.put("description",WORKFLOW_DESCRIPTION);

    return payload;
  }

  public static String buildInvalidCreateWorkflowRequest(String workflowName,
                                                         String workflowContent) {
    Map<String, Object> payload = buildCreateWorkflowRequest(workflowName, workflowContent);

    payload.remove("WorkflowName");
    payload.put("workflowname", workflowName);

    return new Gson().toJson(payload);
  }
}
