package com.greengrim.green.core.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member/chatRooms")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @PostMapping
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String title) {
      return chatRoomRepository.createChatRoom(title);
    }

    @GetMapping("/{roomId}")
    public ChatRoom roomInfo(@PathVariable Long roomId) {
      return chatRoomRepository.findRoomById(roomId);
    }
}
