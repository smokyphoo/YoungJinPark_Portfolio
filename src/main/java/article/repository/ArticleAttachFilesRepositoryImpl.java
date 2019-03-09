package article.repository;

import article.entity.ArticleAttachFiles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleAttachFilesRepositoryImpl implements ArticleAttachFilesRepository {

  @Autowired
  private SessionFactory sessionFactory;

  /*
    아이디로 첨부파일 검색
   */
  @Override
  public ArticleAttachFiles findArticleAttachFilesById(Long id) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    ArticleAttachFiles articleAttachFiles = session.load(ArticleAttachFiles.class, id);
    session.getTransaction().commit();
    return articleAttachFiles;
  }

  /*
    첨부파일 db내에서 삭제
   */
  @Override
  public void deleteArticleAttachFiles(Long articleAttachFilesId) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    ArticleAttachFiles articleAttachFiles = session
        .load(ArticleAttachFiles.class, articleAttachFilesId);
    session.delete(articleAttachFiles);
    session.getTransaction().commit();
  }
}
