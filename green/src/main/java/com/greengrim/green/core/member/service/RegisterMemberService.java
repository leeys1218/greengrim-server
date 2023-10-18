package com.greengrim.green.core.member.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.MemberErrorCode;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.Role;
import com.greengrim.green.core.member.dto.MemberRequestDto;
import com.greengrim.green.core.member.dto.MemberResponseDto;
import com.greengrim.green.core.member.repository.MemberRepository;
import com.greengrim.green.core.member.usecase.RegisterMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMemberService implements RegisterMemberUseCase {

    private final MemberRepository memberRepository;
    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member register(MemberRequestDto.RegisterMember registerMember) {
        Member member = Member.builder()
                .email(registerMember.getEmail())
                .nickName(registerMember.getNickname())
                .introduction(registerMember.getIntroduction())
                .profileImgUrl(registerMember.getProfileImgUrl())
                .refreshToken("")
                .deviceToken("")
                .role(Role.ROLE_VISITOR)
                .build();
        save(member);
        return member;
    }

    @Override
    public MemberResponseDto.TokenInfo registerMember(MemberRequestDto.RegisterMember registerMember) {
        checkRegister(registerMember);
        Member member = register(registerMember);
        // token 발급 코드 수정 필요
        return new MemberResponseDto.TokenInfo("accessToken", "refreshToken");
    }

    public void checkRegister(MemberRequestDto.RegisterMember registerMember) {
        Boolean flag = memberRepository.existsByEmail(registerMember.getEmail());
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_MEMBER);
        }
        flag = memberRepository.existsByNickName(registerMember.getNickname());
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
