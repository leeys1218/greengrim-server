package com.greengrim.green.core.keyword;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public void register(Long memberId, String keyword) {
        keywordRepository.save(new Keyword(memberId, keyword));
    }
}
