package com.greengrim.green.core.testMember;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestMemberService {

  private final TestMemberRepository testMemberRepository;

  public TestMember create(String name) {
    return this.testMemberRepository.save(
      new TestMember(name));
  }
  public List<TestMember> getList() {
    return this.testMemberRepository.findAll();
  }

  @Transactional
  public void delete(String name) {
    this.testMemberRepository.deleteTestMemberByName(name);
  }
}
