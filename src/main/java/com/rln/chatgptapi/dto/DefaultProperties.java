package com.rln.chatgptapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultProperties {

  @JsonProperty("max_tokens")
  private Integer maxTokens;
  private Double temperature;
  @JsonProperty("top_p")
  private Double topP;

}
