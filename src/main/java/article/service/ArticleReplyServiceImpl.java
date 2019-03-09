package article.service;

import article.entity.ArticleReply;
import article.repository.ArticleReplyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleReplyServiceImpl implements ArticleReplyService {

  @Autowired
  ArticleReplyRepository articleReplyRepository;

  @Override
  public void saveArticleReply(ArticleReply articleReply) {
    articleReplyRepository.saveArticleReply(articleReply);
  }

  @Override
  public void deleteArticleReply(Long articleReplyId) {
    articleReplyRepository.deleteArticleReply(articleReplyId);
  }

}
