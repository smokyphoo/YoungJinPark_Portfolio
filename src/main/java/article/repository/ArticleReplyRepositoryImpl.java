package article.repository;

import article.entity.ArticleReply;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleReplyRepositoryImpl implements ArticleReplyRepository {

  @Autowired
  private SessionFactory sessionFactory;

  /*
    댓글 저장
   */
  @Override
  public void saveArticleReply(ArticleReply articleReply) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.save(articleReply);
    session.getTransaction().commit();
  }

  /*
    댓글 삭제
   */
  @Override
  public void deleteArticleReply(Long articleReplyId) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    ArticleReply articleReply = session.load(ArticleReply.class, articleReplyId);
    session.delete(articleReply);
    session.getTransaction().commit();
  }

}
