package com.greengrim.green.core.member.usecase;

import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberInfo;

public interface GetMemberUseCase {
    MemberInfo getMemberInfo(Member member);
    Optional<Member> findMemberById(Long id);
}
