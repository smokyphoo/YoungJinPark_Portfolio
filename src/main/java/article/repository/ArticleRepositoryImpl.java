package article.repository;

import article.entity.Article;
import article.entity.ArticleAttachFiles;
import article.entity.ArticleReply;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

  @Autowired
  private SessionFactory sessionFactory;

  /*
    글 저장
   */
  @Override
  public void registerArticle(Article article) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.save(article);
    session.getTransaction().commit();
  }

  /*
    글 수정
   */
  @Override
  public void updateArticle(Article article) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.update(article);
    session.getTransaction().commit();
  }


  /*
    글 id로 검색
   */
  @Override
  public Article findByArticleId(Long ArticleId) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Article article = session.load(Article.class, ArticleId);
    session.getTransaction().commit();
    return article;
  }

  /*
    글 삭제
   */
  @Override
  public void deleteArticle(Long id) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Article article = session.load(Article.class, Long.valueOf(id));
    session.delete(article);
    session.getTransaction().commit();
  }

  /*
    글의 첨부파일 검색
   */

  public ArticleAttachFiles findAttachFile(String projectName,String uuid){
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session.createQuery("from Article where title=:title");
    query.setParameter("title",projectName);
    ArticleAttachFiles attachFile = (ArticleAttachFiles) query.uniqueResult();
    session.getTransaction().commit();
    return attachFile;
  }

  /*
    댓글 정렬
   */
  @Override
  public List<ArticleReply> sortingArticleReplies(Long id) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session.createQuery("select reply from Article article inner join article.articleReplies reply where article.id =:id order by reply.regDate desc");
    query.setParameter("id",id);
    List<ArticleReply> articleReplies = query.list();
    session.getTransaction().commit();
    return articleReplies;
  }
}
