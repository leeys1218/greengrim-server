package com.greengrim.green.core.member.usecase;

import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberSimpleInfo;

public interface GetMemberUseCase {

    MemberSimpleInfo getMemberInfo(Member member);
}
