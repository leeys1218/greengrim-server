package com.greengrim.green.core.keyword;

import com.greengrim.green.common.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class Keyword extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long memberId;
    @NotNull
    private String keyword;

    public Keyword(Long memberId, String keyword) {
        this.memberId = memberId;
        this.keyword = keyword;
    }
}
