package com.team.userservice.view.controller.mock;

import com.team.userservice.infrastructure.kafka.ActivationSender;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

public class ActivationSenderMocker {
  private final ActivationSender activationSender;

  public ActivationSenderMocker(ActivationSender activationSender) {
    this.activationSender = activationSender;
  }

  public void sendActivationMock(Map<String, List<String>> broker) {
    doAnswer(invoc -> {
      String topic = invoc.getArgument(0, String.class);
      String message = invoc.getArgument(1, String.class);
      broker.get(topic).add(message);
      return null;
    }).when(activationSender)
      .sendActivation(anyString(), anyString());
  }
}
