package com.greengrim.green.core.chat;

import com.greengrim.green.core.chatRoom.ChatRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

  private final ChatService chatService;

  @PostMapping
  public ChatRoom createRoom(@RequestParam String name) {
    return chatService.createRoom(name);
  }

  @GetMapping
  public List<ChatRoom> findAllRoom() {
    return chatService.findAllRoom();
  }
}
