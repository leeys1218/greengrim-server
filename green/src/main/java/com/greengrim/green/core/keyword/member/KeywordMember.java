package com.greengrim.green.core.keyword.member;

import com.greengrim.green.core.keyword.Keyword;
import com.greengrim.green.core.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Keyword keyword;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  public KeywordMember(Keyword keyword, Member member) {
    this.keyword = keyword;
    this.member = member;
  }
}
