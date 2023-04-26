package com.example.firebase.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FCMController {

  @PostMapping("/send")
  public void sendMessage(@RequestBody String message) throws Exception {
    Message fcmMessage = Message.builder()
        .setNotification(Notification.builder()
            .setTitle("FCM Notification")
            .setBody(message)
            .build())
        .setToken("")
        .build();
    FirebaseMessaging.getInstance().send(fcmMessage);
  }
}
