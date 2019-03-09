package security;

import java.util.Collection;
import member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/*
  Spring Security 의 User 클래스를 상속받아 로그인 정보 가져오는 클래스
 */
public class CustomMemberDetails extends User {

  private Member member;

  /*
    유저의 로그인 정보 가져옴
   */
  public CustomMemberDetails(Member member, Collection<GrantedAuthority> authorities) {
    super(member.getEmail(), member.getPassword(), authorities);
    this.member = member;
  }

  public Member getMember() {
    return member;
  }
}
