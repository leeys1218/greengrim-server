package com.greengrim.green.core.keyword;

import com.greengrim.green.core.keyword.member.KeywordMemberService;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordMemberService keywordMemberService;

    @Transactional
    public void register(Keyword keyword) {
        keywordRepository.save(keyword);
    }

    public void addMemberKeyword(Keyword keyword, Member member) {
        keywordMemberService.save(keyword, member);
    }
}
