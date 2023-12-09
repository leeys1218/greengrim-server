package com.greengrim.green.core.keyword;

import com.greengrim.green.core.keyword.Keyword.keywordType;
import com.greengrim.green.core.keyword.dto.KeywordResponseDto.keywordInfos;
import com.greengrim.green.core.keyword.member.KeywordMemberService;
import com.greengrim.green.core.member.Member;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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

    public keywordInfos getMyKeywords(Member member) {
        List<Keyword> keywords = keywordMemberService.getKeywords(member.getId());

        List<String> verb = new ArrayList<>();
        List<String> noun = new ArrayList<>();
        List<String> style = new ArrayList<>();
        keywords.forEach(keyword -> {
            if(keyword.getType().equals(keywordType.VERB))
                verb.add(keyword.getKeyword());
            else if(keyword.getType().equals(keywordType.NOUN))
                noun.add(keyword.getKeyword());
            else if(keyword.getType().equals(keywordType.STYLE))
                style.add(keyword.getKeyword());
        });

        return new keywordInfos(verb, noun, style);
    }

    public List<String> getRandomKeywords() {
        List<String> keywords = new ArrayList<>();
        keywordRepository.findAll().forEach(
            keyword -> keywords.add(keyword.getKeyword()));
        return keywords;
    }

}
