package com.greengrim.green.core.chatparticipant;

import com.greengrim.green.core.chatroom.Chatroom;
import com.greengrim.green.core.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatparticipantService {

  private final ChatparticipantRepository chatparticipantRepository;

  public void save(Member member, Chatroom chatroom) {
    Chatparticipant chatparticipant = Chatparticipant.builder()
        .chatroom(chatroom)
        .member(member)
        .build();
    chatparticipantRepository.save(chatparticipant);
  }

  public void remove(Long memberId, Long chatroomId) {
    Chatparticipant chatparticipant = chatparticipantRepository.findByMemberIdAndChatroomId(memberId, chatroomId);
    chatparticipantRepository.delete(chatparticipant);
  }

  public List<Chatparticipant> findByMemberId(Long memberId) {
    return chatparticipantRepository.findByMemberId(memberId);
  }


}
