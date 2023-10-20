package com.greengrim.green.core.testMember;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/testMember")
@RequiredArgsConstructor
@RestController
public class TestMemberController {

  private final TestMemberService testMemberService;

  @Operation(summary = "테스트 멤버 생성")
  @PostMapping
  public TestMember create(@RequestParam final String name) {
    return this.testMemberService.create(name);
  }

  @Operation(summary = "테스트 멤버 불러오기")
  @GetMapping("/list")
  public List<TestMember> list(Model model) {
    return this.testMemberService.getList();
  }

  @Operation(summary = "테스트 멤버 제거")
  @DeleteMapping
  public void delete(@RequestParam final String name) {
    this.testMemberService.delete(name);
  }
}
