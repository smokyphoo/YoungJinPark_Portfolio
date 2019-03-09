package article.service;

import article.entity.Article;
import article.entity.ArticleAttachFiles;
import article.entity.ArticleReply;
import java.util.List;

public interface ArticleService {

  void registerArticle(Article article);

  void updateArticle(Article article);

  Article findByArticleId(Long ArticleId);

  void deleteArticle(Long id);

  List<ArticleReply> sortingArticleReplies(Long id);
}