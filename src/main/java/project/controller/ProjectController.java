package project.controller;

import article.entity.ArticleAttachFiles;
import com.amazonaws.services.s3.AmazonS3;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import member.entity.Member;
import member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Project;
import project.service.ProjectService;
import util.CompressUtil;
import util.FileUtil;

/*
  프로젝트 작성 컨트롤러
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private MemberService memberService;

  FileUtil fileUtil = new FileUtil();

  private ArrayList<ArticleAttachFiles> list = new ArrayList<>();

  /*
    글 등록
   */
  @RequestMapping("/registerProject")
  public String registerProject() {

    return "/project/register_project";

  }

  /*
    글 등록 진행

    업로드된 임시파일등과 저장
    zip 이나 rar 파일일시 압축해제후 파일들은 confirmFiles 폴더로
    원본 압축파일은 confirmedVersion 폴더로 이동
   */
  @RequestMapping("/registerProjectProcess")
  public String registryArticleProcess(@RequestParam("name") String name,
      @RequestParam("description") String description,@AuthenticationPrincipal Principal principal) {

    Member member = memberService.findMemberByEmail(principal.getName());
    Project project = new Project();

    String filePath = "C:\\upload\\project\\" + name;
    String confirmedFilesPath = "C:\\upload\\project\\" + name + "\\confirmedFiles";
    String confirmedVersionPath = "C:\\upload\\project\\" + name + "\\confirmedVersion";

    File uploadFolder = new File(filePath);
    File confirmedFilesFolder = new File(confirmedFilesPath);
    File confirmedVersion = new File(confirmedVersionPath);

    project.setName(name);
    project.setDescription(description);
    project.setCreator(principal.getName());

    if (list != null) {

      uploadFolder.mkdir();
      confirmedFilesFolder.mkdir();
      confirmedVersion.mkdir();

      for (ArticleAttachFiles index : list) {

        String fileName = index.getFileName();
        String subStringFileName = fileName.substring(fileName.lastIndexOf(".") + 1);

        String beforeMoveFilePath =
            index.getUploadPath() + "\\" + index.getFileName();
        String afterMoveFilePath = confirmedFilesPath + "\\" + index
            .getFileName();
        File beforeMoveFile = new File(beforeMoveFilePath);
        File afterMoveFile = new File(afterMoveFilePath);
        beforeMoveFile.renameTo(afterMoveFile);

        index.setIsConfirmed(true);

        if (subStringFileName.equals("zip") || subStringFileName.equals("rar")) {
          String zipFileName = afterMoveFilePath;
          CompressUtil compressUtil = new CompressUtil();
          compressUtil.decompress(confirmedFilesPath, zipFileName, null);

          File moveToConfirmedVersionFolder = new File(
              confirmedVersionPath + "\\" + project.getName() + ".zip");
          afterMoveFile.renameTo(moveToConfirmedVersionFolder);
        }
        index.setUploadPath(confirmedFilesPath);
        list = (ArrayList<ArticleAttachFiles>) fileUtil.getAllFilesInFolders(confirmedFilesPath);

        project.setAttachFilesList(list);
      }
    }

    List<Project> memberHasFollowedProjectList = member.getFollowedProjectList();
    memberHasFollowedProjectList.add(project);
    member.setFollowedProjectList(memberHasFollowedProjectList);

    projectService.registerProject(project);
    memberService.updateMember(member);

    list.clear();

    return "redirect:/project/" + project.getId() + "/getProject";

  }

  /*
    프로젝트 임시저장
   */
  @PostMapping(value = "/upload")
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

      articleAttachFiles.setFileName(uploadFileName);
      articleAttachFiles.setUploadPath(uploadFolder);

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
    프로젝트 다운로드 진행
   */
  @GetMapping(value = "/download")
  @ResponseBody
  public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent,
      String fileName) {

    Resource resource = new FileSystemResource("C:\\upload\\project\\" + fileName);
    fileName.replace("\\", "/");
    String resourceName = resource.getFilename();

    if (resource.exists() == false) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
    HttpHeaders headers = new HttpHeaders();
    try {
      String downloadName;
      if (userAgent.contains("Trident")) {
        downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
      } else if (userAgent.contains("Edge")) {
        downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
      } else {
        downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
      }
      headers.add("Content-Disposition", "attachment; filename=" + downloadName);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<>(resource, headers, HttpStatus.OK);

  }

  /*
    프로젝트 글 읽기
   */
  @RequestMapping("/{projectId}/getProject")
  public String getProjectById(@PathVariable Long projectId,@AuthenticationPrincipal Principal principal,
      Model projectModel, Model principalModel, Model followModel) {

    Project project = projectService.findByProjectId(projectId);

    String result = null;

    for (Member projectHasMember : project.getMemberList()) {
      if (projectHasMember.getEmail().equals(principal.getName())) {
        result = "followed";
        break;
      }else{
        result = "notFollowed";
      }
    }

    projectModel.addAttribute(project);
    principalModel.addAttribute("principal", principal);
    followModel.addAttribute("isFollow", result);

    return "/project/get_project";

  }

  /*
    프로젝트 검색
   */
  @RequestMapping("/search")
  public String getArticleSearch(@RequestParam("keyword") String keyword,@AuthenticationPrincipal Principal principal, Model projectsModel,Model memberModel) {

    Member member = memberService.findMemberByEmail(principal.getName());

    if (keyword==null) {
      List<Project> projects = projectService.findAllProject();
      projectsModel.addAttribute("projects", projects);
    } else {
      List<Project> projects = projectService.searchProject(keyword);
      projectsModel.addAttribute("projects", projects);
    }

    memberModel.addAttribute(member);

    return "/project/get_project_list";

  }

  /*
    프로젝트 수정
   */
  @RequestMapping("/{projectId}/modifyProject")
  public String modifyArticle(@PathVariable Long projectId, Model projectModel) {

    Project project = projectService.findByProjectId(projectId);

    projectModel.addAttribute("project", project);

    return "/project/modify_project";

  }

  /*
    프로젝트 수정 진행
   */
  @RequestMapping("/{projectId}/modifyArticleProcess")
  public String modifyArticleProcess(@PathVariable Long projectId,
      @RequestParam("description") String description,
      Model projectModel) {

    Project project = projectService.findByProjectId(projectId);

    project.setDescription(description);
    projectService.updateProject(project);

    projectModel.addAttribute("project", project);

    return "redirect:/project/" + project.getId() + "/getProject";

  }

  /*
    프로젝트 삭제
   */
  @RequestMapping("/{projectId}/deleteProjectProcess")
  public String deleteArticle(@PathVariable Long projectId) {

    Project project = projectService.findByProjectId(projectId);

    List<Member> projectHasMemberList = project.getMemberList();

    int result = 0;
    int tracking = 0;

    for (Member memberIndex : projectHasMemberList) {
      for (Project projectIndex : memberIndex.getFollowedProjectList()) {
        if (projectIndex.getId().equals(project.getId())) {
          result = tracking;
          break;
        }
        tracking++;
      }
      memberIndex.getFollowedProjectList().remove(result);
      memberService.updateMember(memberIndex);
    }

    project.getMemberList().clear();

    projectService.updateProject(project);
    projectService.deleteProject(project.getId());

    return "redirect:/";

  }

}
