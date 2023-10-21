package com.greengrim.green.core.member.usecase;

import com.greengrim.green.core.member.dto.MemberRequestDto;
import com.greengrim.green.core.member.dto.MemberResponseDto;

public interface RegisterMemberUseCase {

    MemberResponseDto.TokenInfo registerMember(MemberRequestDto.RegisterMember registerMember);
    MemberResponseDto.TokenInfo login(MemberRequestDto.LoginMember loginMember);
}
