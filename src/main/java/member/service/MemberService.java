package member.service;

import java.util.List;
import member.entity.Member;
import project.entity.Project;

public interface MemberService {

  void registerMember(Member member);

  void updateMember(Member member);

  Member findMemberByEmail(String email);

  String duplicatedEmailCheck(String email);

  String duplicatedNicknameCheck(String nickname);

  void deleteMember(String email);

  List<Project> sortingFollowProjectList(String memberEmail);

}