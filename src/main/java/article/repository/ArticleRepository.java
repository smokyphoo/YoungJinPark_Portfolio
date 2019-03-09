package article.repository;

import article.entity.Article;
import article.entity.ArticleReply;
import java.util.List;

public interface ArticleRepository {

  void registerArticle(Article article);

  void updateArticle(Article article);

  Article findByArticleId(Long ArticleId);

  void deleteArticle(Long id);

  List<ArticleReply> sortingArticleReplies(Long id);
}
