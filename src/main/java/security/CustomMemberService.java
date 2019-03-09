package security;

import java.util.Collection;
import member.entity.Member;
import member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/*
   UserDetailsService 구현하는 클래스
 */
@Service
public class CustomMemberService implements UserDetailsService {

  @Autowired
  MemberRepository memberRepository;

  /*
     유저의 email 주소를 받아 유저가 인증 되었는지 검사함
   */
  @Override
  public UserDetails loadUserByUsername(String email) {
    Member member = memberRepository.findMemberByEmail(email);
    return new CustomMemberDetails(member, getAuthorities(member));
  }

  /*
     유저의 권한을 가져옴
   */
  private Collection<GrantedAuthority> getAuthorities(Member member) {
    if (member.getSignupRoles().equals("ROLE_USER")) {
      return AuthorityUtils.createAuthorityList("ROLE_USER");
    } else {
      return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
    }
  }
}
