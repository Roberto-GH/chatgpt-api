package com.rln.chatgptapi.service.impl;

import com.rln.chatgptapi.dto.ChatRequest;
import com.rln.chatgptapi.dto.ChatResponse;
import com.rln.chatgptapi.dto.DefaultProperties;
import com.rln.chatgptapi.exception.ChatgptException;
import com.rln.chatgptapi.property.ChatgptProperties;
import com.rln.chatgptapi.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class DefaultChatgptService implements ChatgptService {

  protected final ChatgptProperties chatgptProperties;
  private final String AUTHORIZATION;
  private final RestTemplate restTemplate = new RestTemplate();
  @Value("${chatgpt.url}")
  private String URL;

  public DefaultChatgptService(ChatgptProperties chatgptProperties) {
    this.chatgptProperties = chatgptProperties;
    AUTHORIZATION = "Bearer " + chatgptProperties.getApiKey();
  }

  @Override
  public String sendMessage(String message) {
    ChatRequest chatRequest = new ChatRequest(chatgptProperties.getModel(), message, chatgptProperties.getMaxTokens(),
                                              chatgptProperties.getTemperature(), chatgptProperties.getTopP());
    ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(chatRequest));
    try {
      return chatResponse.getChoices().get(0).getText();
    } catch (Exception e) {
      log.error("Parse chatgpt message error", e);
      throw e;
    }
  }

  @Override
  public ChatResponse sendChatRequest(ChatRequest chatRequest) {
    return this.getResponse(this.buildHttpEntity(chatRequest));
  }

  @Override
  public DefaultProperties defaultsProperties() {
    return DefaultProperties
      .builder()
      .maxTokens(chatgptProperties.getMaxTokens())
      .temperature(chatgptProperties.getTemperature())
      .topP(chatgptProperties.getTopP())
      .build();
  }

  public HttpEntity<ChatRequest> buildHttpEntity(ChatRequest chatRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
    headers.add("Authorization", AUTHORIZATION);
    return new HttpEntity<>(chatRequest, headers);
  }

  public ChatResponse getResponse(HttpEntity<ChatRequest> chatRequestHttpEntity) {
    log.info("Request url: {}, httpEntity: {}", URL, chatRequestHttpEntity);
    ResponseEntity<ChatResponse> responseEntity = restTemplate.postForEntity(URL, chatRequestHttpEntity, ChatResponse.class);
    if (responseEntity.getStatusCode().isError()) {
      log.error("Error response status: {}", responseEntity);
      throw new ChatgptException("Error response status :" + responseEntity.getStatusCode().value());
    } else {
      log.info("Response: {}", responseEntity);
    }
    return responseEntity.getBody();
  }

}
