package com.greengrim.green.core.keyword.member;

import com.greengrim.green.core.keyword.Keyword;
import com.greengrim.green.core.keyword.Keyword.keywordType;
import com.greengrim.green.core.keyword.KeywordRepository;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordMemberService {

  private final KeywordMemberRepository keywordMemberRepository;
  private final KeywordRepository keywordRepository;

  @Transactional
  public void save(Keyword keyword, Member member) {
    keywordMemberRepository.save(new KeywordMember(keyword, member));
  }

  public List<Keyword> getKeywords(Long memberId) {
    List<Keyword> keywords = new ArrayList<>();
    keywordMemberRepository.findByMemberId(memberId).forEach(
        keywordMember -> keywords.add(keywordMember.getKeyword()));
    return keywords;
  }

  public void addBasicKeyword(Member member) {
    List<Keyword> keywords = keywordRepository.findAll();
    keywords.forEach(keyword -> {
      keywordMemberRepository.save(new KeywordMember(keyword, member));
    });
  }
}
