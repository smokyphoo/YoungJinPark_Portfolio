package util;

import article.entity.ArticleAttachFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

  ArrayList<ArticleAttachFiles> attachFilesList = new ArrayList<>();

  /*
    폴더내의 모든 하위 폴더,파일의 정보 가져오기
   */
  public List<ArticleAttachFiles> getAllFilesInFolders(String folderPath) {

    File folderPathFile = new File(folderPath);
    File[] listOfFiles = folderPathFile.listFiles();

    for (File fileIndex : listOfFiles) {

      if (!fileIndex.isDirectory()) {
        ArticleAttachFiles articleAttachFiles = new ArticleAttachFiles();
        articleAttachFiles.setUploadPath(fileIndex.getParent());
        articleAttachFiles.setFileName(fileIndex.getName());
        articleAttachFiles.setIsConfirmed(true);
        attachFilesList.add(articleAttachFiles);
      } else {
        getAllFilesInFolders(fileIndex.getAbsolutePath());
      }
    }

    return attachFilesList;
  }

}
