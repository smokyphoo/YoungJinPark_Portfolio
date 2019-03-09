package article.repository;

import article.entity.ArticleAttachFiles;

public interface ArticleAttachFilesRepository {

  ArticleAttachFiles findArticleAttachFilesById(Long id);

  void deleteArticleAttachFiles(Long id);
}
