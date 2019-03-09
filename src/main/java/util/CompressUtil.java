package util;

import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/*
    압축 관련 클래스
 */
public class CompressUtil {

  /*
     경로내에 있는 폴더안의 파일들을 모두 압축처리
   */
  public void compress(String folderPath, String projectName, String version) {
    String source = "C:\\upload\\project\\" + projectName + "\\confirmedVersion\\" + File.separator
        + projectName + version + ".zip";

    String destination = folderPath;
    try {
      ZipFile zipFile = new ZipFile(source);
      ZipParameters parameters = new ZipParameters();
      parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
      parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
      zipFile.addFolder(destination, parameters);
    } catch (ZipException e) {
      e.printStackTrace();
    }
  }

  /*
    업로드된 압축 파일 압축해제
   */
  public void decompress(String folderPath, String projectName, String version) {

    String source = projectName;

    try {
      ZipFile zipFile = new ZipFile(source);
      zipFile.extractAll(folderPath);
    } catch (ZipException e) {
      e.printStackTrace();
    }

  }
}
