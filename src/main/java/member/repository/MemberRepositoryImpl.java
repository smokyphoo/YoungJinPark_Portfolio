package member.repository;

import java.util.List;
import member.entity.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Project;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

  @Autowired
  private SessionFactory sessionFactory;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /*
    회원 가입
   */
  @Override
  public void registerMember(Member member) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    String encodedPassword = bCryptPasswordEncoder.encode(member.getPassword());
    member.setPassword(encodedPassword);
    member.setSignupRoles("ROLE_USER");
    session.save(member);
    session.getTransaction().commit();
  }

  /*
    회원 정보수정
   */
  @Override
  public void updateMember(Member member) {
    Session session = this.sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.saveOrUpdate(member);
    session.getTransaction().commit();
  }

  /*
    회원 검색
   */
  @Override
  public Member findMemberByEmail(String email) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Member member = session.get(Member.class, email);
    session.getTransaction().commit();
    return member;
  }

  /*
    이메일 중복검사
   */
  @Override
  public String duplicatedEmailCheck(String email) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session
        .createQuery("select email from Member where  lower (email) like :email");
    query.setParameter("email",email);
    String result = (String) query.uniqueResult();
    session.getTransaction().commit();
    return result;
  }

  /*
    닉네임 중복검사
   */
  @Override
  public String duplicatedNicknameCheck(String nickname) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session
        .createQuery("select nickname from Member where lower (nickname) like :nickname");
    query.setParameter("nickname", nickname);
    String result = (String) query.uniqueResult();
    session.getTransaction().commit();
    return result;
  }

  /*
    회원 탈퇴
   */
  @Override
  @Transactional
  public void deleteMember(String email) {
    Session session = this.sessionFactory.getCurrentSession();
    session.beginTransaction();
    Member member = session.load(Member.class, email);
    session.delete(member);
    session.getTransaction().commit();
  }

  /*
    회원의 팔로우 리스트
   */
  @Override
  public List<Project> sortingFollowProjectList(String nickname) {
      Session session = sessionFactory.getCurrentSession();
      session.beginTransaction();
      Query query = session.createQuery("select followList from Member memberVar inner join memberVar.followedProjectList followList where memberVar.nickname =: nickname order by followList.modifiedDate desc");
      query.setParameter("nickname",nickname);
      List<Project> projects = query.list();
      session.getTransaction().commit();
      return projects;
  }

}