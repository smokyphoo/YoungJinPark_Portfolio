package main;

import java.security.Principal;
import java.util.List;
import member.entity.Member;
import member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.entity.Project;

@Controller
@RequestMapping
public class MainController {

  @Autowired
  private MemberService memberService;

  /*
    메인 페이지
   */
  @RequestMapping("/")
  public String main(@AuthenticationPrincipal Principal principal, Model projectModel) {

    if (!principal.getName().equals(null)) {

      Member member = memberService.findMemberByEmail(principal.getName());
      List<Project> projects = memberService.sortingFollowProjectList(member.getNickname());

      projectModel.addAttribute("projects", projects);

      return "/main/main";

    }else {

      return "/sign/signin";

    }

  }

}
