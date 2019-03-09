package article.service;

import article.entity.ArticleReply;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleReplyService {

  void saveArticleReply(ArticleReply articleReply);

  void deleteArticleReply(Long articleReplyId);
}
