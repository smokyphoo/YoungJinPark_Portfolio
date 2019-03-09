package article.repository;

import article.entity.ArticleReply;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleReplyRepository {

  void saveArticleReply(ArticleReply articleReply);

  void deleteArticleReply(Long articleReplyId);

}
