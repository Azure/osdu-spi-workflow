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

package org.opengroup.osdu.workflow.provider.azure.filters;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.opengroup.osdu.workflow.provider.azure.config.TriggerWorkflowConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;

@Slf4j
@Component
public class TriggerWorkflowConfigRequestSizeLimitFilter extends OncePerRequestFilter {

  @Autowired
  TriggerWorkflowConfig triggerWorkflowConfig;

  private static String URI = ".*/workflow/.*/workflowRun";
  private static String MESSAGE = "Request content exceeded limit of %s kB";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (request.getMethod().equals(HttpMethod.POST)
        && isApplicationJson(request)
        && request.getRequestURI().matches(URI)
        && request.getContentLengthLong() > triggerWorkflowConfig.getMaxRequestSizeInBytes()) {
      String errMsg = String.format(MESSAGE, triggerWorkflowConfig.getMaxRequestSize());
      log.error("Request size filter error: " + errMsg);
      response.sendError(HttpStatus.SC_REQUEST_TOO_LONG, errMsg);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean isApplicationJson(HttpServletRequest httpRequest) {
    return (MediaType.APPLICATION_JSON.isCompatibleWith(MediaType.parseMediaType(httpRequest.getHeader(HttpHeaders.CONTENT_TYPE))));
  }
}
