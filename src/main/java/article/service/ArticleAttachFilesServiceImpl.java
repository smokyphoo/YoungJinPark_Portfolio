package article.service;

import article.entity.ArticleAttachFiles;
import article.repository.ArticleAttachFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleAttachFilesServiceImpl implements ArticleAttachFilesService {

  @Autowired
  ArticleAttachFilesRepository articleAttachFilesRepository;

  @Override
  public ArticleAttachFiles findArticleAttachFiles(Long id) {
    return articleAttachFilesRepository.findArticleAttachFilesById(id);
  }

  @Override
  public void deleteArticleAttachFiles(Long id) {
    articleAttachFilesRepository.deleteArticleAttachFiles(id);
  }
}
