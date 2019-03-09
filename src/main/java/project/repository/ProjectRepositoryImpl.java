package project.repository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.entity.Project;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  @Autowired
  private SessionFactory sessionFactory;

  /*
    프로젝트 등록
   */
  @Override
  public void registerProject(Project project) {

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.save(project);
    session.getTransaction().commit();

  }

  /*
    프로젝트 수정
   */
  @Override
  public void updateProject(Project project) {

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.saveOrUpdate(project);
    session.getTransaction().commit();

  }

  /*
    이름으로 프로젝트 검색
   */
  @Override
  public Project findByProjectName(String projectName) {

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session.createQuery("from Project where name=:name");
    query.setParameter("name", projectName);
    Project project = (Project) query.uniqueResult();
    session.getTransaction().commit();
    return project;

  }

  /*
    ID로 프로젝트 검색
   */
  @Override
  public Project findByProjectId(Long projectId) {

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Project project = session.load(Project.class, projectId);
    session.getTransaction().commit();
    return project;

  }

  /*
    모든 프로젝트 검색
   */
  @Override
  public List<Project> findAllProject() {

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session.createQuery("from Project");
    List<Project> projects = query.list();
    session.getTransaction().commit();
    return projects;

  }

  /*
    프로젝트 이름으로 프로젝트 목록 검색
   */
  @Override
  public List<Project> searchProject(String keyword) {

    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query query = session
        .createQuery("from Project where  lower (name) like :keyword");
    query.setParameter("keyword",'%'+keyword.toLowerCase()+'%');
    List<Project> projectList = query.getResultList();
    session.getTransaction().commit();
    return projectList;

  }

  /*
    프로젝트 삭제
   */
  @Override
  public void deleteProject(Long id) {

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Project project = session.load(Project.class, id);
    session.delete(project);
    session.getTransaction().commit();

  }
}
