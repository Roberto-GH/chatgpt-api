package com.rln.chatgptapi.service;

import com.rln.chatgptapi.dto.ChatRequest;
import com.rln.chatgptapi.dto.ChatResponse;
import com.rln.chatgptapi.dto.DefaultProperties;

public interface ChatgptService {

  String sendMessage(String message);
  ChatResponse sendChatRequest(ChatRequest request);
  DefaultProperties defaultsProperties();

}
