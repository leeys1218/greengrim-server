package com.greengrim.green.core.testMember;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestMemberService {

  private final TestMemberRepository testMemberRepository;

  public List<TestMember> getList() {
    return this.testMemberRepository.findAll();
  }
}
