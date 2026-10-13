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

import static java.util.Collections.singletonList;
import static org.opengroup.osdu.workflow.consts.DefaultVariable.DEFAULT_DATA_PARTITION_ID_TENANT1;
import static org.opengroup.osdu.workflow.consts.DefaultVariable.DOMAIN;
import static org.opengroup.osdu.workflow.consts.DefaultVariable.LEGAL_TAG;
import static org.opengroup.osdu.workflow.consts.DefaultVariable.OTHER_RELEVANT_DATA_COUNTRIES;
import static org.opengroup.osdu.workflow.consts.DefaultVariable.getEnvironmentVariableOrDefaultKey;

public class PayloadBuilder {

	public static String buildWorkflowIdPayload(String workflowId){
		Map<String, Object> payload = new HashMap<>();

		payload.put("WorkflowID", workflowId);

		return new Gson().toJson(payload);
	}

	public static String buildStartWorkflow(Map<String, Object> context, String type){
		Map<String, Object> payload = new HashMap<>();

		payload.put("WorkflowType", type);
		payload.put("DataType", "opaque");
		payload.put("Context", context);

		return new Gson().toJson(payload);
	}

	public static String buildUpdateStatus(String workflowId, String status){
		Map <String, Object> payload = new HashMap<>();

		payload.put("WorkflowID", workflowId);
		payload.put("Status", status);

		return new Gson().toJson(payload);
	}

	public static Map<String, Object> buildContext(){
		Map<String, Object> context = new HashMap<>();

		Map<String, Object> legal = new HashMap<>();
		legal.put("legaltags", singletonList(getEnvironmentVariableOrDefaultKey(LEGAL_TAG)));
		legal.put("otherRelevantDataCountries", singletonList(getEnvironmentVariableOrDefaultKey(OTHER_RELEVANT_DATA_COUNTRIES)));

		Map<String, Object> acl = new HashMap<>();
		acl.put("viewers", singletonList(getAcl()));

		context.put("legal", legal);
		context.put("acl", acl);

		return context;
	}

	private static String getAcl() {
		return String.format("data.viewers@%s", getAclSuffix());
	}

	private static String getAclSuffix() {
		return String.format("%s.%s",
				getEnvironmentVariableOrDefaultKey(DEFAULT_DATA_PARTITION_ID_TENANT1),
				getEnvironmentVariableOrDefaultKey(DOMAIN)
		);
	}
}
