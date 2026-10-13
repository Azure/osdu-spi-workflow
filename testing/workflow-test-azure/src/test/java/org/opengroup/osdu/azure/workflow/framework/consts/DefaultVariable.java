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

import static java.util.Objects.isNull;

public enum DefaultVariable {
	WORKFLOW_HOST(""),
	DEFAULT_DATA_PARTITION_ID_TENANT1(""),
	DOMAIN(""),

	INTEGRATION_TESTER(""),
	NO_DATA_ACCESS_TESTER(""),
	LEGAL_TAG(""),
	OTHER_RELEVANT_DATA_COUNTRIES(""),

	FINISHED_WORKFLOW_ID(""),
	DAG_ACTIVATION_TIME("360"),
	TEST_DATA_DIRECTORY(""),
	TEST_DATA_FILE_NAME("testData.json");


	public static String getEnvironmentVariableOrDefaultKey(DefaultVariable key){
		String variable = getEnvironmentVariable(key.name());

		return isNull(variable) ? key.getDefaultValue() : variable;
	}

	private static String getEnvironmentVariable(String variableName){
		return System.getProperty(variableName, System.getenv(variableName));
	}

	private String defaultValue;

	DefaultVariable(String defaultValue){
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getVariableKey(DefaultVariable var) {
		return var.name();
	}
}

