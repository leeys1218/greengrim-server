package com.greengrim.green.core.member.service;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberRequestDto.ModifyProfile;
import com.greengrim.green.core.member.dto.MemberResponseDto;
import com.greengrim.green.core.member.repository.MemberRepository;
import com.greengrim.green.core.member.usecase.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberService implements UpdateMemberUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDto.TokenInfo refreshAccessToken(Member member) {
        MemberResponseDto.TokenInfo newTokenInfo
                = jwtTokenProvider.generateToken(member.getId());
        return new MemberResponseDto.TokenInfo(
                newTokenInfo.getAccessToken(), member.getRefreshToken());
    }

    @Override
    public void modifyProfile(Member member, ModifyProfile modifyProfile) {
        member.modifyMember(modifyProfile.getNickName(),
                modifyProfile.getIntroduction(),
                modifyProfile.getProfileImgUrl());
        // TODO: S3에서 기존 프로필 삭제
        memberRepository.save(member);
    }
}
