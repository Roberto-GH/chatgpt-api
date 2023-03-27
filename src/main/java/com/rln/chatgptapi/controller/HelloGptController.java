package com.rln.chatgptapi.controller;

import com.rln.chatgptapi.dto.ChatRequest;
import com.rln.chatgptapi.dto.ChatResponse;
import com.rln.chatgptapi.dto.DefaultProperties;
import com.rln.chatgptapi.exception.ChatgptException;
import com.rln.chatgptapi.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/chatgpt")
public class HelloGptController implements InitializingBean {

  private final ChatgptService chatGptService;

  @Autowired
  public HelloGptController(ChatgptService chatGptService) {
    this.chatGptService = chatGptService;
  }

  @Override
  public void afterPropertiesSet() {
    log.info(" ========= Starting Chag Gpt Boot ======== ");
  }

  @GetMapping("/basic")
  public String chatWith(@RequestParam String message) {
    log.info(message);
    return chatGptService.sendMessage(message);
  }

  @GetMapping("/default-properties")
  public DefaultProperties defaultsProperties() {
    return chatGptService.defaultsProperties();
  }

  @PostMapping("/prompt")
  public ChatResponse prompt(@RequestBody ChatRequest chatRequest) {
    log.info(chatRequest.toString());
    if (chatRequest.getPrompt().isEmpty()) {
      throw new ChatgptException("Mensaje vacio :" + 400);
    }
    ChatResponse chatResponse = chatGptService.sendChatRequest(chatRequest);
    log.info(chatResponse.toString());
    return chatResponse;
  }

  @GetMapping("/image")
  public String image(@RequestParam String message) {
    log.info(message);
    return chatGptService.imageGenerate(message);
  }

}
