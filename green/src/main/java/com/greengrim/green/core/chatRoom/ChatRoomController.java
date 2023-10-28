package com.greengrim.green.core.chatRoom;

import java.util.List;
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
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
      List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
      chatRooms.stream().forEach(room->room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));
      return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
      return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public ChatRoom roomInfo(@PathVariable String roomId) {
      return chatRoomRepository.findRoomById(roomId);
    }

}
