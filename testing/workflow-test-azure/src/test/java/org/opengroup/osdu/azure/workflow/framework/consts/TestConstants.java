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

package org.opengroup.osdu.azure.workflow.framework.consts;

import static org.opengroup.osdu.workflow.consts.DefaultVariable.WORKFLOW_HOST;
import static org.opengroup.osdu.workflow.consts.DefaultVariable.getEnvironmentVariableOrDefaultKey;

public class TestConstants extends org.opengroup.osdu.workflow.consts.TestConstants {

  //Api endpoints
  public static final String CREATE_SYSTEM_WORKFLOW_API_ENDPOINT ="v1/workflow/system";
  public static final String GET_SYSTEM_WORKFLOW_BY_ID_API_ENDPOINT = "v1/workflow/system/%s";
  public static final String CUSTOM_OPERATOR_API_ENDPOINT = "v1/customOperator";
  public static final String GET_SIGNED_URL_API_ENDPOINT = "v1/workflow/%s/workflowRun/%s/getSignedUrl";

  public static final String WORKFLOW_ID_FIELD = "WorkflowID";

  public static final String CREATE_SYSTEM_WORKFLOW_URL =
      getEnvironmentVariableOrDefaultKey(WORKFLOW_HOST) + CREATE_SYSTEM_WORKFLOW_API_ENDPOINT;
  public static final String GET_SYSTEMWORKFLOW_BY_ID_URL =
      getEnvironmentVariableOrDefaultKey(WORKFLOW_HOST) + GET_SYSTEM_WORKFLOW_BY_ID_API_ENDPOINT;
  public static final String CUSTOM_OPERATOR_URL =
      getEnvironmentVariableOrDefaultKey(WORKFLOW_HOST) + CUSTOM_OPERATOR_API_ENDPOINT;
  public static final String GET_SIGNED_URL_URL =
      getEnvironmentVariableOrDefaultKey(WORKFLOW_HOST) + GET_SIGNED_URL_API_ENDPOINT;
}
