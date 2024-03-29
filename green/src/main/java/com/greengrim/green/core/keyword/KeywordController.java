package com.greengrim.green.core.keyword;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.keyword.dto.KeywordResponseDto.keywordInfos;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeywordController {

  private final KeywordService keywordService;

  /**
   * [GET] 내 키워드 조회
   */
  @Operation(summary = "내 키워드 조회")
  @GetMapping("/visitor/keywords")
  public ResponseEntity<keywordInfos> getMyKeywords(
      @CurrentMember Member member) {
    return ResponseEntity.ok(keywordService.getMyKeywords(member));
  }

  /**
   * [GET] 랜덤 키워드 조회
   */
  @Operation(summary = "랜덤 키워드 조회")
  @GetMapping("/visitor/keywords/random")
  public ResponseEntity<List<String>> getRandomKeywords(
      @CurrentMember Member member) {
    return ResponseEntity.ok(keywordService.getRandomKeywords());
  }
}
