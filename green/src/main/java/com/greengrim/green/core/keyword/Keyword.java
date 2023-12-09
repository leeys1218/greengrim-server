package com.greengrim.green.core.keyword;

import com.greengrim.green.common.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Keyword extends BaseTime {

    public enum keywordType {
        STYLE, VERB, NOUN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String keyword;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private keywordType type;

    public Keyword(String keyword, keywordType type) {
        this.keyword = keyword;
        this.type = type;
    }
}
