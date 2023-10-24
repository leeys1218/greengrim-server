package com.greengrim.green.core.member.usecase;

import com.greengrim.green.core.member.dto.MemberRequestDto;
import com.greengrim.green.core.member.dto.MemberResponseDto;

public interface RegisterMemberUseCase {

    MemberResponseDto.TokenInfo registerMember(MemberRequestDto.RegisterMemberReq registerMemberReq);
    MemberResponseDto.TokenInfo login(MemberRequestDto.LoginMemberReq loginMemberReq);
    MemberResponseDto.CheckNickNameRes checkNickName(MemberRequestDto.CheckNickNameReq checkNickNameReq);
}
