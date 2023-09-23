package com.greengrim.green.core.testMember;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class TestMemberController {

  private final TestMemberService testMemberService;

  @GetMapping("/member")
  public List<TestMember> list(Model model) {
    return this.testMemberService.getList();
  }
}
