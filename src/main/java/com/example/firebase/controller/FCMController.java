package com.example.firebase.controller;

import com.example.firebase.controller.FCMDto.DeviceDto;
import com.example.firebase.controller.FCMDto.DeviceMessageDto;
import com.example.firebase.controller.FCMDto.TopicMessageDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FCMController {

  private final Map<String, String> deviceMap = new HashMap<>();

  @PostMapping("/send/device-message")
  public void sendMessage(@RequestBody DeviceMessageDto deviceMessageDto) throws Exception {
    String deviceId = deviceMessageDto.getDeviceId();
    if (deviceMap.containsKey(deviceId)) {
      String token = deviceMap.get(deviceId);
      Message fcmMessage = Message.builder()
          .setNotification(Notification.builder()
              .setTitle(deviceMessageDto.getMessage().getTitle())
              .setBody(deviceMessageDto.getMessage().getBody())
              .build())
          .setToken(token)
          .build();
      FirebaseMessaging.getInstance().send(fcmMessage);
    } else {
      log.warn("[sendMessage] device:{} is not registered.", deviceId);
    }
  }

  @PostMapping("/send/topic-message")
  public void sendTopic(@RequestBody TopicMessageDto topicMessageDto) throws Exception {
    Message fcmMessage = Message.builder()
        .setNotification(Notification.builder()
            .setTitle(topicMessageDto.getMessage().getTitle())
            .setBody(topicMessageDto.getMessage().getBody())
            .build())
        .setTopic(topicMessageDto.getTopic())
        .build();
    FirebaseMessaging.getInstance().send(fcmMessage);
  }

  @PostMapping("/devices")
  public void registerDevice(@RequestBody DeviceDto deviceDto) {
    log.info("[registerDevice] deviceDto: {}", deviceDto);
    deviceMap.put(deviceDto.getDeviceId(), deviceDto.getToken());
  }

  @PostMapping("/topics")
  public void registerTopic(@RequestBody String topic) throws Exception {
    log.info("[registerTopic] topic: {}", topic);

    // Subscribe the devices corresponding to the registration tokens to the
    // topic.
    TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
        deviceMap.values().stream().toList(), topic);

    // See the TopicManagementResponse reference documentation
    // for the contents of response.
    log.info(response.getSuccessCount() + " tokens were subscribed successfully");
  }

  @DeleteMapping("/topics")
  public void unregisterTopic(@RequestBody String topic) throws Exception {
    log.info("[unregisterTopic] topic: {}", topic);

    // Unsubscribe the devices corresponding to the registration tokens from
    // the topic.
    TopicManagementResponse response = FirebaseMessaging.getInstance().unsubscribeFromTopic(
        deviceMap.values().stream().toList(), topic);
    // See the TopicManagementResponse reference documentation
    // for the contents of response.
    log.info(response.getSuccessCount() + " tokens were unsubscribed successfully");
  }

}
