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

package org.opengroup.osdu.workflow.provider.azure.gsm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opengroup.osdu.azure.publisherFacade.MessagePublisher;
import org.opengroup.osdu.core.common.exception.CoreException;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;
import org.opengroup.osdu.core.common.model.status.Message;
import org.opengroup.osdu.core.common.model.status.StatusDetails;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AzureMessagePublisher}
 */
@ExtendWith(MockitoExtension.class)
class AzureMessagePublisherTest {

  private final static String TOPIC_NAME = "status_topic";
  private final static String CORRELATION_ID = "CORRELATION_ID";
  private final static String DATA_PARTITION_ID = "DATA_PARTITION_ID";

  @Mock
  private MessagePublisher messagePublisher;

  private AzureMessagePublisher publisher;

  @BeforeEach
  void setUp() {
    publisher = new AzureMessagePublisher(messagePublisher);
    publisher.setEventGridTopic(TOPIC_NAME);
    publisher.setServiceBusTopic(TOPIC_NAME);
  }

  @Test
  void shouldPublishGSMMessage() {
    //given
    doNothing().when(messagePublisher).publishMessage(any(), any(), any());
    Message[] messages = new Message[]{new StatusDetails()};
    Map<String, String> attributesMap = new HashMap<String, String>() {{
      put(DpsHeaders.CORRELATION_ID, CORRELATION_ID);
      put(DpsHeaders.DATA_PARTITION_ID, DATA_PARTITION_ID);
    }};

    //when
    publisher.publish(messages, attributesMap);

    //then
    verify(messagePublisher, times(1))
        .publishMessage(any(), any(), any());
  }

  @Test
  void shouldTrowCoreExceptionOnMissingMessages() {
    //given
    Map<String, String> attributesMap = new HashMap<String, String>() {{
      put(DpsHeaders.CORRELATION_ID, CORRELATION_ID);
      put(DpsHeaders.DATA_PARTITION_ID, DATA_PARTITION_ID);
    }};

    //when & then
    Assertions.assertThrows(CoreException.class,
        () -> publisher.publish(null, attributesMap)
    );

    //then
    verify(messagePublisher, times(0))
        .publishMessage(any(), any(), any());
  }

  @Test
  void shouldTrowCoreExceptionOnEmptyMessages() {
    //given
    Message[] messages = new Message[]{};
    Map<String, String> attributesMap = new HashMap<String, String>() {{
      put(DpsHeaders.CORRELATION_ID, CORRELATION_ID);
      put(DpsHeaders.DATA_PARTITION_ID, DATA_PARTITION_ID);
    }};

    //when & then
    Assertions.assertThrows(CoreException.class,
        () -> publisher.publish(messages, attributesMap)
    );

    //then
    verify(messagePublisher, times(0))
        .publishMessage(any(), any(), any());
  }

  @Test
  void shouldTrowCoreExceptionOnMissingAttributes() {
    //given
    Message[] messages = new Message[]{new StatusDetails()};

    //when & then
    Assertions.assertThrows(CoreException.class,
        () -> publisher.publish(messages, null)
    );

    //then
    verify(messagePublisher, times(0))
        .publishMessage(any(), any(), any());
  }

  @Test
  void shouldTrowCoreExceptionOnEmptyAttributes() {
    //given
    Message[] messages = new Message[]{new StatusDetails()};
    Map<String, String> attributesMap = new HashMap<>();

    //when & then
    Assertions.assertThrows(CoreException.class,
        () -> publisher.publish(messages, attributesMap)
    );

    //then
    verify(messagePublisher, times(0))
        .publishMessage(any(), any(), any());
  }

  @Test
  void shouldTrowCoreExceptionOnMissingCorrelationId() {
    //given
    Message[] messages = new Message[]{new StatusDetails()};
    Map<String, String> attributesMap = new HashMap<String, String>() {{
      put(DpsHeaders.DATA_PARTITION_ID, DATA_PARTITION_ID);
    }};

    //when & then
    Assertions.assertThrows(CoreException.class,
        () -> publisher.publish(messages, attributesMap)
    );

    //then
    verify(messagePublisher, times(0))
        .publishMessage(any(), any(), any());
  }

  @Test
  void shouldTrowCoreExceptionOnMissingDataPartitionId() {
    //given
    Message[] messages = new Message[]{new StatusDetails()};
    Map<String, String> attributesMap = new HashMap<String, String>() {{
      put(DpsHeaders.CORRELATION_ID, CORRELATION_ID);
    }};

    //when & then
    Assertions.assertThrows(CoreException.class,
        () -> publisher.publish(messages, attributesMap)
    );

    //then
    verify(messagePublisher, times(0))
        .publishMessage(any(), any(), any());
  }
}
