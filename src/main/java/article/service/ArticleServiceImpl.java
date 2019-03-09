package article.service;

import article.entity.Article;
import article.entity.ArticleAttachFiles;
import article.entity.ArticleReply;
import article.repository.ArticleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Override
  public void registerArticle(Article article) {
    articleRepository.registerArticle(article);
  }

  @Override
  public void updateArticle(Article article) {
    articleRepository.updateArticle(article);
  }

  @Override
  public Article findByArticleId(Long articleId) {
    return articleRepository.findByArticleId(articleId);
  }

  @Override
  public void deleteArticle(Long id) {
    articleRepository.deleteArticle(id);
  }

  @Override
  public List<ArticleReply> sortingArticleReplies(Long id){
    return articleRepository.sortingArticleReplies(id);
  }

}
