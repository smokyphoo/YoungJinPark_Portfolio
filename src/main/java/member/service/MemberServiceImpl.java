package member.service;

import java.util.List;
import member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import member.repository.MemberRepository;
import project.entity.Project;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Override
  public void registerMember(Member member) {
    memberRepository.registerMember(member);
  }

  @Override
  public void updateMember(Member member) {
    memberRepository.updateMember(member);
  }

  @Override
  public Member findMemberByEmail(String email) {
    return memberRepository.findMemberByEmail(email);
  }

  @Override
  public String duplicatedEmailCheck(String email) {
    return memberRepository.duplicatedEmailCheck(email);
  }

  @Override
  public String duplicatedNicknameCheck(String nickname) {
    return memberRepository.duplicatedNicknameCheck(nickname);
  }

  @Override
  public void deleteMember(String email) {
    memberRepository.deleteMember(email);
  }

  @Override
  public List<Project> sortingFollowProjectList(String memberEmail) {
    return memberRepository.sortingFollowProjectList(memberEmail);
  }

}