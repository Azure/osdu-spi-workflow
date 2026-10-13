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

package org.opengroup.osdu.workflow.provider.azure.utils.airflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.opengroup.osdu.workflow.model.TriggerWorkflowResponse;
import org.opengroup.osdu.workflow.model.WorkflowEngineRequest;
import org.opengroup.osdu.workflow.provider.azure.fileshare.FileShareConfig;
import org.springframework.stereotype.Component;

@Component
public interface IAirflowWorkflowEngineUtil {

  String getDagRunIdParameterName();

  String getFileNameFromWorkflow(String workflowName);

  String getAirflowDagsUrl();

  String getAirflowDagRunsUrl();

  String getAirflowDagRunsStatusUrl();

  String getAirflowActiveDagRunsCountUrl();

  String getFileShareName(FileShareConfig fileShareConfig);

  TriggerWorkflowResponse extractTriggerWorkflowResponse(String response)
      throws JsonProcessingException;

  Integer extractActiveDagRunsResponse(String response)
      throws JsonProcessingException;

  String getDagRunIdentificationParam(WorkflowEngineRequest rq);

  JSONObject addMicroSecParam(JSONObject requestBody);
}
