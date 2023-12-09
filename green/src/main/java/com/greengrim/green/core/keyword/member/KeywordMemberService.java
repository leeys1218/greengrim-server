package com.greengrim.green.core.keyword.member;

import com.greengrim.green.core.keyword.Keyword;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordMemberService {

  private final KeywordMemberRepository keywordMemberRepository;

  @Transactional
  public void save(Keyword keyword, Member member) {
    keywordMemberRepository.save(new KeywordMember(keyword, member));
  }
}
