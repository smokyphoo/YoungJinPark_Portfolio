package article.controller;

import article.entity.Article;
import article.entity.ArticleAttachFiles;
import article.entity.ArticleReply;
import article.service.ArticleAttachFilesService;
import article.service.ArticleService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import member.entity.Member;
import member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Project;
import project.service.ProjectService;
import util.CompressUtil;

/*
  프로젝트 내의 요청글 작성 컨트롤러
 */
@Controller
@RequestMapping("/project")
public class ArticleController {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private ArticleService articleService;

  @Autowired
  private ArticleAttachFilesService articleAttachFilesService;

  @Autowired
  private MemberService memberService;

  private List<ArticleAttachFiles> list = new ArrayList<>();

  /*
    글 등록
   */
  @RequestMapping("/{projectId}/registerArticle")
  public String registerArticle(@PathVariable Long projectId, Model model) {

    Project project = projectService.findByProjectId(projectId);

    model.addAttribute("project", project);

    return "/article/register_article";

  }

  /*
    글 등록 진행

    업로드된 임시파일들과 함께 저장
   */
  @RequestMapping("/{projectId}/registerArticleProcess")
  public String registryArticleProcess(@PathVariable Long projectId,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @AuthenticationPrincipal Principal principal) {

    Project project = projectService.findByProjectId(projectId);
    Article article = new Article();

    article.setTitle(title);
    article.setContent(content);
    article.setCreator(principal.getName());

    if (list != null) {
      File uploadFolder = new File("C:\\upload\\project\\" + project.getName());
      if (uploadFolder.exists() == false) {
        uploadFolder.mkdir();
      }
      for (ArticleAttachFiles index : list) {
        String beforeMoveFilePath =
            index.getUploadPath() + "\\" + index.getUuid() + "_" + index.getFileName();
        String afterMoveFilePath =
            index.getUploadPath() + "\\" + project.getName() + "\\" + index.getUuid() + "_" + index
                .getFileName();
        File beforeMoveFile = new File(beforeMoveFilePath);
        File afterMoveFile = new File(afterMoveFilePath);
        beforeMoveFile.renameTo(afterMoveFile);
        index.setUploadPath("c:\\upload\\project\\" + project.getName() + "\\");
        index.setIsConfirmed(false);
      }
      article.setAttachFiles(list);
    }

    List<Article> articlesList = project.getArticlesList();
    articlesList.add(article);
    project.setArticlesList(articlesList);
    articleService.registerArticle(article);
    projectService.updateProject(project);
    list.clear();

    return "redirect:/project/" + project.getId() + "/" + article.getId() + "/getArticle";

  }

  /*
    파일 임시저장
   */
  @PostMapping(value = "/article/upload")
  @ResponseBody
  public ResponseEntity<List<ArticleAttachFiles>> uploadAjaxPost(MultipartFile[] uploadFile) {

    String uploadFolder = "c:\\upload\\project";
    File uploadPath = new File(uploadFolder);

    if (uploadPath.exists() == false) {
      uploadPath.mkdir();
    }

    for (MultipartFile multipartFile : uploadFile) {
      ArticleAttachFiles articleAttachFiles = new ArticleAttachFiles();
      String uploadFileName = multipartFile.getOriginalFilename();
      String originalFileName = multipartFile.getOriginalFilename();

      uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
      articleAttachFiles.setFileName(uploadFileName);

      UUID uuid = UUID.randomUUID();

      uploadFileName = uuid.toString() + "_" + uploadFileName;

      articleAttachFiles.setFileName(originalFileName);
      articleAttachFiles.setUploadPath(uploadFolder);
      articleAttachFiles.setUuid(uuid.toString());
      articleAttachFiles.setIsConfirmed(true);

      try {

        File saveFile = new File(uploadPath, uploadFileName);
        multipartFile.transferTo(saveFile);
        list.add(articleAttachFiles);
      } catch (Exception e) {
        e.getStackTrace();
      }
    }

    return new ResponseEntity<>(list, HttpStatus.OK);

  }

  /*
    요청글 읽기
    멤버의 아이디와 글작성자의 아이디가 동일할시 글 삭제,수정권한부여
   */
  @RequestMapping("/{projectId}/{articleId}/getArticle")
  public String getArticleById(@PathVariable Long projectId, @PathVariable Long articleId,
      @AuthenticationPrincipal Principal principal, Model projectModel, Model articleModel, Model articleRepliesModel,
      Model isCreator, Model memberModel) {

    Project project = projectService.findByProjectId(projectId);
    Article article = articleService.findByArticleId(articleId);
    List<ArticleReply> articleReplies = articleService.sortingArticleReplies(articleId);
    Member member = memberService.findMemberByEmail(principal.getName());

    String projectCreator = project.getCreator();
    String result = "notCreator";
    if (member.getNickname().equals(article.getCreator()) || principal.getName()
        .equals(projectCreator)) {
      result = "creator";
    }

    projectModel.addAttribute("project", project);
    articleModel.addAttribute("article", article);
    articleRepliesModel.addAttribute("articleReplies", articleReplies);
    memberModel.addAttribute("member",member);
    isCreator.addAttribute("isCreator", result);

    return "/article/get_article";

  }

  /*
    글 수정
   */
  @RequestMapping("/{projectId}/{articleId}/modifyArticle/")
  public String modifyArticle(@PathVariable Long projectId, @PathVariable Long articleId,
      Model projectModel, Model articleModel) {

    Project project = projectService.findByProjectId(projectId);
    Article article = articleService.findByArticleId(articleId);

    projectModel.addAttribute("project", project);
    articleModel.addAttribute("article", article);

    return "/article/modify_article";

  }

  /*
    글 수정 진행
   */
  @RequestMapping("/{projectId}/{articleId}/modifyArticleProcess/")
  public String modifyArticleProcess(@PathVariable Long projectId, @PathVariable Long articleId,
      @RequestParam("articleContent") String modifyArticleContent,
      Model articleModel) {

    Project project = projectService.findByProjectId(projectId);
    Article article = articleService.findByArticleId(articleId);

    article.setContent(modifyArticleContent);
    articleService.updateArticle(article);

    articleModel.addAttribute("article", article);

    return "redirect:/project/" + project.getId() + "/" + article.getId() + "/getArticle";

  }

  /*
    글 삭제 진행
   */
  @RequestMapping("/{projectId}/{articleId}/deleteArticle/")
  public String deleteArticle(@PathVariable Long projectId, @PathVariable Long articleId) {

    Article article = articleService.findByArticleId(articleId);
    Project project = projectService.findByProjectId(projectId);

    int result = 0;
    int tracking = 0;

    for (Article index : project.getArticlesList()) {
      if (index.getId().equals(articleId)) {
        result = tracking;
        break;
      }
      tracking++;
    }

    project.getArticlesList().remove(result);

    projectService.updateProject(project);
    articleService.updateArticle(article);
    articleService.deleteArticle(articleId);

    return "redirect:/project/" + project.getId() + "/" + article.getId() + "/getArticle";

  }

  /*
    요청글 에서 파일을 컨펌 요청 수행
    컨펌되어진 파일들중 동일한 이름이 있을경우 이전파일을 덮어쓰고 압축 진행
   */
  @RequestMapping("/{projectId}/{articleId}/{articleAttachId}/{articleAttachName}/confirm")
  public String confirmFiles(@PathVariable Long projectId, @PathVariable Long articleId,
      @PathVariable Long articleAttachId, @PathVariable String articleAttachName) {

    Project project = projectService.findByProjectId(projectId);
    Article article = articleService.findByArticleId(articleId);
    ArticleAttachFiles articleAttachFiles = articleAttachFilesService
        .findArticleAttachFiles(articleAttachId);

    int confirmIndex = -1;
    int confirmTracking = 0;

    for (ArticleAttachFiles confirm : project.getAttachFilesList()) {
      if (confirm.getFileName().equals(articleAttachFiles.getFileName())) {
        confirmIndex = confirmTracking;
        break;
      }
      confirmTracking++;
    }

    if (!(confirmIndex == -1)) {
      ArticleAttachFiles beforeAttachFile = project.getAttachFilesList().get(confirmIndex);
      project.getAttachFilesList().remove(confirmIndex);

      String confirmedFilePath =
          articleAttachFiles.getUploadPath() + "\\" + articleAttachFiles.getUuid() + "_"
              + articleAttachName;
      File confirmedFile = new File(confirmedFilePath);
      String beforeAttachFilePath =
          beforeAttachFile.getUploadPath() + "\\" + beforeAttachFile.getFileName();
      File beforeFile = new File(beforeAttachFilePath);

      articleAttachFiles.setFileName(beforeAttachFile.getFileName());
      articleAttachFiles.setUploadPath(beforeAttachFilePath);
      articleAttachFiles.setIsConfirmed(true);
      project.getAttachFilesList().add(articleAttachFiles);
      articleAttachFilesService.deleteArticleAttachFiles(articleAttachId);

      beforeFile.delete();
      confirmedFile.renameTo(beforeFile);

      CompressUtil compressUtil = new CompressUtil();
      String path = "C:\\upload\\project\\" + project.getName() + "\\confirmedFiles";
      compressUtil.compress(path, project.getName(), "1.0");
    }

    return "redirect:/project/" + project.getId() + "/" + article.getId() + "/getArticle";

  }

  /*
    컨펌된 파일과 요청 파일의 코드 비교
   */
  @RequestMapping("/{projectId}/{articleId}/{articleAttachId}/{articleAttachName}")
  public String textDifferTest(@PathVariable Long projectId, @PathVariable Long articleId,
      @PathVariable Long articleAttachId,
      @PathVariable String articleAttachName, Model beforeModel, Model afterModel, Model fileName,Model projectModel) {

    Project project = projectService.findByProjectId(projectId);
    ArticleAttachFiles attachFile = articleAttachFilesService
        .findArticleAttachFiles(articleAttachId);

    int projectHasAttachFilesIndex = -1;
    int projectHasAttachFilesTracking = 0;

    String afterAttachFilePath;
    String beforeAttachFilePath;

    for (ArticleAttachFiles projectAttachFiles : project.getAttachFilesList()) {
      if (projectAttachFiles.getFileName().equals(attachFile.getFileName())) {
        projectHasAttachFilesIndex = projectHasAttachFilesTracking;
        break;
      }
      projectHasAttachFilesTracking++;
    }

    afterAttachFilePath =
        attachFile.getUploadPath() + "\\" + attachFile.getUuid() + "_" + attachFile.getFileName();
    if (!(projectHasAttachFilesIndex == -1)) {
      ArticleAttachFiles beforeAttachFile = project.getAttachFilesList()
          .get(projectHasAttachFilesIndex);

      beforeAttachFilePath =
          beforeAttachFile.getUploadPath() + "\\" + beforeAttachFile.getFileName();
    } else {

      ClassLoader classLoader = getClass().getClassLoader();
      beforeAttachFilePath = classLoader.getResource("nullText.txt").getPath();
    }

    try {
      BufferedReader bf1 = new BufferedReader(new FileReader(beforeAttachFilePath));
      BufferedReader bf2 = new BufferedReader(new FileReader(afterAttachFilePath));
      ArrayList<String> beforeFileText = new ArrayList<>();
      ArrayList<String> afterFileText = new ArrayList<>();
      String temp1;
      String temp2;
      while ((temp1 = bf1.readLine()) != null) {
        beforeFileText.add(temp1);
      }
      while ((temp2 = bf2.readLine()) != null) {
        afterFileText.add(temp2);
      }

      afterModel.addAttribute("afterFile", afterFileText);

      if (projectHasAttachFilesIndex > -1) {
        beforeModel.addAttribute("beforeFile", beforeFileText);
      } else {
        beforeModel.addAttribute("beforeFile", null);
      }


    } catch (IOException e) {
      e.printStackTrace();
    }

    fileName.addAttribute("attachFile", attachFile);
    projectModel.addAttribute("project",project);

    return "/main/view";

  }

}
