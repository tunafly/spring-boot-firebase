package com.example.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

  private final ResourceLoader resourceLoader;

  @PostConstruct
  public void init() throws IOException {
    String filename = "fcm.json";
    Resource resource = resourceLoader.getResource("classpath:" + filename);
    File file = resource.getFile();
    FileInputStream serviceAccount = new FileInputStream(file);
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();
    FirebaseApp.initializeApp(options);
  }
}
