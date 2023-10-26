package com.greengrim.green.core.member.usecase;

import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberRequestDto.ModifyProfile;
import com.greengrim.green.core.member.dto.MemberResponseDto;

public interface UpdateMemberUseCase {

    MemberResponseDto.TokenInfo refreshAccessToken(Member member);
    void modifyProfile(Member member, ModifyProfile modifyProfile);
    void deleteMember(Member member);
}
