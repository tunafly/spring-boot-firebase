package com.example.firebase.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public interface FCMDto {

  @Data
  class DeviceDto {

    @NotNull
    private String deviceId;

    @NotNull
    private String token;
  }

  @Data
  @Builder
  class MessageDto {

    @NotNull
    private String title;

    @NotNull
    private String body;
  }

  @Data
  @Builder
  class TopicMessageDto {

    @NotNull
    private MessageDto message;

    @NotNull
    private String topic;
  }

  @Data
  @Builder
  class DeviceMessageDto {

    @NotNull
    private MessageDto message;

    @NotNull
    private String deviceId;
  }

}
