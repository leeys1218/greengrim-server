package com.greengrim.green.core.grim.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.grim.dto.GrimRequestDto.RegisterGrimInfo;
import com.greengrim.green.core.grim.service.RegisterGrimService;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterGrimController {

  private final RegisterGrimService registerGrimService;

  /**
   * [POST] 그림 생성
   */
  @Operation(summary = "그림 생성")
  @PostMapping("/visitor/grims")
  public void generateGrim(
      @CurrentMember Member member,
      @RequestBody RegisterGrimInfo registerGrimInfo) {
    registerGrimService.generateGrim(member, registerGrimInfo);
  }
}
