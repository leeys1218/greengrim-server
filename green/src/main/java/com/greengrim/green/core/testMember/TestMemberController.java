package com.greengrim.green.core.testMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/testMember")
@RequiredArgsConstructor
@Controller
public class TestMemberController {

  private final TestMemberService testMemberService;

  @Operation(summary = "테스트 멤버 불러오기", tags = {"Member Controller"})
  @GetMapping("/list")
  public List<TestMember> list(Model model) {
    return this.testMemberService.getList();
  }
}
