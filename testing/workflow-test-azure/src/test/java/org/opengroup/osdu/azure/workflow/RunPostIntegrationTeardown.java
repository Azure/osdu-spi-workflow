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

package org.opengroup.osdu.azure.workflow;

import org.opengroup.osdu.azure.workflow.framework.teardown.PostIntegrationTeardown;
import org.opengroup.osdu.azure.workflow.framework.util.HTTPClient;
import org.opengroup.osdu.azure.workflow.utils.HTTPClientAzure;

public class RunPostIntegrationTeardown {
  public static void main(String[] args) {
    HTTPClient client = new HTTPClientAzure();
    PostIntegrationTeardown postIntegrationTeardown = new PostIntegrationTeardown(client);
    postIntegrationTeardown.run();
  }
}
