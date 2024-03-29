package com.greengrim.green.core.challenge.service;

import com.greengrim.green.core.challenge.Challenge;
import com.greengrim.green.core.challenge.dto.ChallengeRequestDto.RegisterChallenge;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.EnterChallengeResponse;
import com.greengrim.green.core.challenge.repository.ChallengeRepository;
import com.greengrim.green.core.chatroom.Chatroom;
import com.greengrim.green.core.chatroom.service.ChatroomService;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterChallengeService {

    private final ChallengeRepository challengeRepository;

    private final EnterChallengeService enterChallengeService;
    private final ChatroomService chatroomService;

    public EnterChallengeResponse register(Member member, RegisterChallenge registerChallenge) {

        Chatroom chatroom = chatroomService.registerChatroom(member, registerChallenge.getTitle()); // 채팅방 생성

        Challenge challenge = Challenge.builder()
                .category(registerChallenge.getCategory())
                .title(registerChallenge.getTitle())
                .description(registerChallenge.getDescription())
                .imgUrl(registerChallenge.getImgUrl())
                .goalCount(registerChallenge.getGoalCount())
                .ticketTotalCount(registerChallenge.getTicketTotalCount())
                .ticketCurrentCount(registerChallenge.getTicketTotalCount())
                .weekMinCount(registerChallenge.getWeekMinCount())
                .capacity(registerChallenge.getCapacity())
                .keyword(registerChallenge.getKeyword())
                .headCount(1)
                .status(true)
                .member(member)
                .chatroom(chatroom)
                .build();

        challengeRepository.save(challenge);
        enterChallengeService.enterChallenge(member, challenge.getId());
        return new EnterChallengeResponse(challenge);
    }
}
