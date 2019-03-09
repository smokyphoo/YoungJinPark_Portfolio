package member.controller;

import java.security.Principal;
import member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import member.service.MemberService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import security.CustomMemberService;

@Controller
public class MemberController {

  @Autowired
  private MemberService memberService;

  @Autowired
  private CustomMemberService customMemberService;


  /*
    로그인
   */
  @RequestMapping("/signin")
  public String signin() {
    return "/sign/signin";
  }

  /*
    회원가입
   */
  @RequestMapping("/signup")
  public String signup() {
    return "sign/signup";
  }

  /*
    회원가입 진행
   */
  @RequestMapping("/signupProcess")
  public String signupProcess(@RequestParam("email") String email,
      @RequestParam("nickname") String nickname, @RequestParam("password") String password) {

    Member member = new Member();

    member.setEmail(email);
    member.setNickname(nickname);
    member.setPassword(password);
    memberService.registerMember(member);

    return "redirect:/signin";

  }

  /*
    이메일 중복체크
   */
  @RequestMapping(value = "/emailDuplicationCheck", method = {RequestMethod.GET,
      RequestMethod.POST})
  public @ResponseBody
  String emailDuplicationCheck(String email) {

    String checker = memberService.duplicatedEmailCheck(email);
    if (checker==null) {
      return "false";
    }else {
      return "true";
    }

  }

  /*
    닉네임 중복체크
   */
  @RequestMapping(value = "/nicknameDuplicationCheck", method = {RequestMethod.GET,
      RequestMethod.POST})
  public @ResponseBody
  String nicknameDuplicate(String nickname) {

    String checker = memberService.duplicatedNicknameCheck(nickname);

    if (checker==null) {
      return "false";
    }else {
      return "true";
    }

  }

  /*
    회원의 프로젝트 팔로우 리스트
   */
  @RequestMapping("followList")
  public String memberFollowList(Principal principal,Model memberModel){

    Member member = memberService.findMemberByEmail(principal.getName());

    memberModel.addAttribute("member",member);

    return "/member/member_follow_list";

  }

  /*
    회원 정보 수정페이지
   */
  @RequestMapping("modifyMember")
  public String modifyMember(Principal principal, Model model) {

    Member member = memberService.findMemberByEmail(principal.getName());

    model.addAttribute(member);

    return "member/modify_member";
  }

  /*
    회원 정보 수정
   */
  @RequestMapping("modifyMemberProcess")
  public String modifyMemberProcess(Principal principal, @RequestParam("nickname") String nickname,
      @RequestParam("company") String company, @RequestParam("position") String position) {

    Member member = memberService.findMemberByEmail(principal.getName());

    member.setNickname(nickname);
    member.setCompany(company);
    member.setPosition(position);
    memberService.updateMember(member);

    UserDetails customMemberDetails = customMemberService.loadUserByUsername(member.getEmail());
    Authentication authentication = new UsernamePasswordAuthenticationToken(customMemberDetails,
        null, customMemberDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return "redirect:/";
  }

  /*
    회원 삭제
   */
  @RequestMapping("deleteMemberProcess")
  public String deleteMemberProcess(Principal principal) {

    memberService.deleteMember(principal.getName());
    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

    return "/sign/signin";
  }

}
