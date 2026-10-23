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

package org.opengroup.osdu.azure.workflow.framework.operators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.opengroup.osdu.azure.workflow.framework.util.CustomOperatorTestsBuilder;
import org.opengroup.osdu.azure.workflow.framework.util.CustomOperatorUtil;
import org.opengroup.osdu.azure.workflow.framework.util.TestBase;
import org.opengroup.osdu.azure.workflow.framework.util.TestResourceProvider;

import javax.ws.rs.HttpMethod;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.opengroup.osdu.azure.workflow.framework.consts.TestConstants.CUSTOM_OPERATOR_URL;
import static org.opengroup.osdu.azure.workflow.framework.consts.TestOperatorNames.SIMPLE_CUSTOM_OPERATOR;

public abstract class RegisterCustomOperatorIntegrationTests extends TestBase {
  private static final String SIMPLE_CUSTOM_OPERATOR_FILE = "simple_custom_operator.py";

  private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

  @Test
  public void should_returnForbidden_when_givenCustomOperatorContentWithIgnoreCustomOperatorContentAsTrue() throws Exception {
    String operatorContent = TestResourceProvider
        .getOperatorFileContent(SIMPLE_CUSTOM_OPERATOR_FILE);
    Map<String, Object> payload =
        CustomOperatorTestsBuilder.buildRegisterCustomOperatorPayload(
            CustomOperatorUtil.getUniqueOperatorName(SIMPLE_CUSTOM_OPERATOR), "SampleClass",
            operatorContent);
    ClientResponse response = client.send(
        HttpMethod.POST,
        CUSTOM_OPERATOR_URL,
        gson.toJson(payload),
        headers,
        client.getAccessToken()
    );

    assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatus());
  }
}
