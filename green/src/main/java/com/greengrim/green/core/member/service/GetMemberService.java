package com.greengrim.green.core.member.service;

import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberResponseDto.HomeInfo;
import com.greengrim.green.core.member.dto.MemberResponseDto.MemberInfo;
import com.greengrim.green.core.member.dto.MemberResponseDto.MyInfo;
import com.greengrim.green.core.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMemberService {

    private final MemberRepository memberRepository;

    public MemberInfo getMemberInfo(Member member) {
        return new MemberInfo(member);
    }
    public Optional<Member> findMemberById(Long id) {return memberRepository.findById(id); }

    public HomeInfo getHomeInfo(Member member) {
        return new HomeInfo(member);
    }

    public MyInfo getMyDetailInfo(Member member) {
        return new MyInfo(member);
    }
}
