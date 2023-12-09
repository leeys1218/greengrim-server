package com.greengrim.green.core.keyword.dto;

import java.util.List;
import lombok.Getter;

public class KeywordResponseDto {

  @Getter
  public static class keywordInfos {
    private final List<String> verb;
    private final List<String> noun;
    private final List<String> style;

    public keywordInfos(List<String> verb, List<String> noun, List<String> style) {
      this.verb = verb;
      this.noun = noun;
      this.style = style;
    }
  }
}
