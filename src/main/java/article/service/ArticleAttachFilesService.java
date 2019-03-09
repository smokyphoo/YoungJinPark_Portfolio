package article.service;


import article.entity.ArticleAttachFiles;

public interface ArticleAttachFilesService {

  ArticleAttachFiles findArticleAttachFiles(Long id);

  void deleteArticleAttachFiles(Long id);

}
