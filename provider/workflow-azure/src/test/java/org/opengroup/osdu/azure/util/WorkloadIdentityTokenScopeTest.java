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

package org.opengroup.osdu.azure.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.WorkloadIdentityCredential;
import com.azure.identity.WorkloadIdentityCredentialBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opengroup.osdu.azure.cache.AzureServicePrincipleTokenServiceCache;
import org.opengroup.osdu.azure.di.WorkloadIdentityConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkloadIdentityTokenScopeTest {
  private static final String MANAGEMENT_SCOPE = "https://management.azure.com/.default";

  @Spy
  private AzureServicePrincipal azureServicePrincipal = new AzureServicePrincipal();
  @Mock
  private WorkloadIdentityConfiguration workloadIdentityConfiguration;
  @Mock
  private AzureServicePrincipleTokenServiceCache tokenCache;
  @Mock
  private Environment environment;
  @Mock
  private WorkloadIdentityCredentialBuilder credentialBuilder;
  @Mock
  private WorkloadIdentityCredential credential;

  @InjectMocks
  private AzureServicePrincipleTokenService tokenService;

  @Test
  public void workloadIdentityTokenRequestsOnlyTheManagementScope() {
    ReflectionTestUtils.setField(tokenService, "aadClientId", "00000000-0000-0000-0000-000000000000");
    when(environment.acceptsProfiles(any(Profiles.class))).thenReturn(false);
    when(workloadIdentityConfiguration.getIsEnabled()).thenReturn(true);
    doReturn(credentialBuilder).when(azureServicePrincipal).createworkloadIdentityClientBuilder();
    when(credentialBuilder.build()).thenReturn(credential);
    String jwt = JWT.create()
        .withExpiresAt(new Date(System.currentTimeMillis() + 3_600_000))
        .sign(Algorithm.HMAC256("test"));
    when(credential.getToken(any(TokenRequestContext.class)))
        .thenReturn(Mono.just(new AccessToken(jwt, OffsetDateTime.now().plusHours(1))));

    assertEquals(jwt, tokenService.getAuthorizationToken());

    ArgumentCaptor<TokenRequestContext> request = ArgumentCaptor.forClass(TokenRequestContext.class);
    verify(credential).getToken(request.capture());
    assertEquals(Collections.singletonList(MANAGEMENT_SCOPE), request.getValue().getScopes());
  }
}
