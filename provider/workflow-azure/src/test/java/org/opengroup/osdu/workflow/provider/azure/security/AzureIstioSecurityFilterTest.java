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

package org.opengroup.osdu.workflow.provider.azure.security;

import com.azure.spring.cloud.autoconfigure.implementation.aad.filter.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AzureIstioSecurityFilterTest {

  private static final String X_ISTIO_CLAIMS_PAYLOAD = "x-payload";

  private final AzureIstioSecurityFilter filter = new AzureIstioSecurityFilter();

  @AfterEach
  public void cleanup() {
    SecurityContextHolder.clearContext();
  }

  @Test
  public void shouldDecodeBase64UrlPayloadWithoutPadding() throws Exception {
    String claims = "{\"oid\":\"user-oid\",\"name\":\">>>???\"}";
    String payload = Base64.getUrlEncoder().withoutPadding()
        .encodeToString(claims.getBytes(StandardCharsets.UTF_8));

    assertTrue(payload.contains("-"), "payload must contain '-' to exercise base64url decoding");
    assertTrue(payload.contains("_"), "payload must contain '_' to exercise base64url decoding");
    assertFalse(payload.endsWith("="), "payload must not be padded");

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_ISTIO_CLAIMS_PAYLOAD, payload);

    AtomicReference<Authentication> captured = new AtomicReference<>();
    FilterChain chain = (req, res) -> captured.set(SecurityContextHolder.getContext().getAuthentication());

    filter.doFilter(request, new MockHttpServletResponse(), chain);

    Authentication authentication = captured.get();
    assertNotNull(authentication);
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    assertEquals("user-oid", principal.getClaim("oid"));
    assertEquals(">>>???", principal.getClaim("name"));
    assertEquals("ROLE_USER", authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(",")));
  }
}
