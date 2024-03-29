package com.greengrim.green.common.fcm;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitor/fcm")
public class FcmController {

  private final FcmService fcmService;

  /**
   * [GET] FCM 구독하기 (클라이언트 앱 종료시)
   */
  @Operation(summary = "FCM 구독")
  @GetMapping("/subscribe")
  public void subscribeTopic(@CurrentMember Member member) {
    fcmService.subscribe(member);
  }

  /**
   * [GET] FCM 구독 취소하기 (클라이언트 앱 실행시)
   */
  @Operation(summary = "FCM 구독취소")
  @GetMapping("/unsubscribe")
  public void unsubscribeTopic(@CurrentMember Member member) {
    fcmService.unsubscribe(member);
  }

}