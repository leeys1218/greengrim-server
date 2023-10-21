package com.greengrim.green.core.member.service;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto;
import com.greengrim.green.core.member.usecase.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberService implements UpdateMemberUseCase {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberResponseDto.TokenInfo refreshAccessToken(Member member) {
        MemberResponseDto.TokenInfo newTokenInfo
                = jwtTokenProvider.generateToken(member.getId());
        return new MemberResponseDto.TokenInfo(
                newTokenInfo.getAccessToken(), member.getRefreshToken());
    }
}
