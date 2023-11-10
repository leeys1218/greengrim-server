package com.greengrim.green.core.chatroom.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.chatroom.dto.ChatroomResponseDto.ChatroomInfo;
import com.greengrim.green.core.chatroom.service.ChatroomService;
import com.greengrim.green.core.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitor/chatrooms")
public class ChatroomController {

  private final ChatroomService chatroomService;

  /**
   * [GET] 내가 속한 채팅방 목록 조회
   */
  @GetMapping
  public ResponseEntity<List<ChatroomInfo>> getMyChatrooms(@CurrentMember Member member) {
    return ResponseEntity.ok(chatroomService.getMyChatrooms(member));
  }

  /**
   * [POST] 채팅방 생성
   */
  @PostMapping
  public void registerChatroom(@CurrentMember Member member, @RequestParam String title) {
    chatroomService.registerChatroom(member, title);
  }


  /**
   * [POST] 채팅방 퇴장
   */
  @PostMapping("/exit")
  public void exitChatroom(@CurrentMember Member member, @RequestParam Long chatroomId) {
    chatroomService.exitChatroom(member, chatroomId);
  }

}
