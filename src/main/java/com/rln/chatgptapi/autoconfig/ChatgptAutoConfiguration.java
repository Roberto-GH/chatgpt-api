package com.rln.chatgptapi.autoconfig;

import com.rln.chatgptapi.property.ChatgptProperties;
import com.rln.chatgptapi.service.ChatgptService;
import com.rln.chatgptapi.service.impl.DefaultChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(ChatgptProperties.class)
public class ChatgptAutoConfiguration {

  private final ChatgptProperties chatgptProperties;

  @Autowired
  public ChatgptAutoConfiguration(ChatgptProperties chatgptProperties) {
    this.chatgptProperties = chatgptProperties;
  }

  @Bean
  @ConditionalOnMissingBean(ChatgptService.class)
  public ChatgptService chatgptService() {
    return new DefaultChatgptService(chatgptProperties);
  }

}
