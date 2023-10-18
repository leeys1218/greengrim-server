package com.greengrim.green.core.member.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.MemberErrorCode;
import com.greengrim.green.common.jwt.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member register(MemberRequestDto.RegisterMember registerMember) {
        Member member = Member.builder()
                .email(registerMember.getEmail())
                .nickName(registerMember.getNickName())
                .introduction(registerMember.getIntroduction())
                .profileImgUrl(registerMember.getProfileImgUrl())
                .point(0)
                .reportCnt(0)
                .status(true)
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
        MemberResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.getId());
        member.changeRefreshToken(tokenInfo.getRefreshToken());
        return tokenInfo;
    }

    public void checkRegister(MemberRequestDto.RegisterMember registerMember) {
        Boolean flag = memberRepository.existsByEmail(registerMember.getEmail());
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_MEMBER);
        }
        flag = memberRepository.existsByNickName(registerMember.getNickName());
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
