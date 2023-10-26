package com.greengrim.green.core.member.service;

import com.greengrim.green.common.jwt.JwtTokenProvider;
import com.greengrim.green.common.s3.S3Service;
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
    private final S3Service s3Service;

    @Override
    public MemberResponseDto.TokenInfo refreshAccessToken(Member member) {
        MemberResponseDto.TokenInfo newTokenInfo
                = jwtTokenProvider.generateToken(member.getId());
        return new MemberResponseDto.TokenInfo(
                newTokenInfo.getAccessToken(), member.getRefreshToken());
    }

    @Override
    public void modifyProfile(Member member, ModifyProfile modifyProfile) {
        // s3에서 기존 프로필 삭제
        s3Service.deleteFile(member.getProfileImgUrl());
        // Member 엔티티 업로드
        member.modifyMember(modifyProfile.getNickName(),
                modifyProfile.getIntroduction(),
                modifyProfile.getProfileImgUrl());
        memberRepository.save(member);
    }

    @Override
    public void deleteMember(Member member) {
        member.setStatusFalse();
        // TODO: 삭제된 member와 관련된 모든 리소스 삭제
        memberRepository.save(member);
    }
}
