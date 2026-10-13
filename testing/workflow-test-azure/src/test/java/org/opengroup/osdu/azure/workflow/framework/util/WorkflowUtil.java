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

import lombok.extern.slf4j.Slf4j;
import org.opengroup.osdu.azure.workflow.framework.consts.DefaultVariable;

@Slf4j
public class WorkflowUtil {

  public static String getUniqueWorkflowName(String dagName) {
    return String.format("%s_%d", dagName, System.currentTimeMillis());
  }

  public static void waitForDAGActivation(String... dagNames) {
    int secondsToWait = Integer.parseInt(DefaultVariable
        .getEnvironmentVariableOrDefaultKey(DefaultVariable.DAG_ACTIVATION_TIME));
    try {
      log.info("Waiting for DAG activation for {} seconds", secondsToWait);
      Thread.sleep(secondsToWait * 1000);
    } catch (InterruptedException e) {
      log.error("Error while waiting for dag to become active");
    }
    log.info("DAG activation successful");
  }
}
