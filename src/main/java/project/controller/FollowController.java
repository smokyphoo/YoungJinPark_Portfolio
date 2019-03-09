package project.controller;

import java.security.Principal;
import java.util.List;
import member.entity.Member;
import member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.entity.Project;
import project.service.ProjectService;

@Controller
public class FollowController {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private MemberService memberService;

  @PostMapping("/isFollowed")
  public @ResponseBody
  void isFollowed(@RequestParam("follow") String inputFollow,
      @RequestParam("projectId") Long projectId,
      Principal principal) {

    Project project = projectService.findByProjectId(projectId);
    Member member = memberService.findMemberByEmail(principal.getName());

    if (inputFollow.equals("unfollow")) {

      int projectIndex = 0;
      int projectTracking = 0;

      for (Member projectHasMembers : project.getMemberList()) {
        if (projectHasMembers.getEmail().equals(principal.getName())) {
          projectIndex = projectTracking;
        }
        projectTracking++;
      }

      int memberIndex = 0;
      int memberTracking = 0;
      for (Project memberHasProjects : member.getFollowedProjectList()) {
        if (memberHasProjects.getId().equals(projectId)) {
          memberIndex = memberTracking;
        }
        memberTracking++;
      }

      project.getMemberList().remove(projectIndex);
      member.getFollowedProjectList().remove(memberIndex);

      projectService.updateProject(project);
      memberService.updateMember(member);

    } else {

      List<Project> memberFollowedList = member.getFollowedProjectList();
      memberFollowedList.add(projectService.findByProjectId(projectId));
      member.setFollowedProjectList(memberFollowedList);

      List<Member> projectHasMembers = project.getMemberList();
      projectHasMembers.add(member);
      project.setMemberList(projectHasMembers);

      memberService.updateMember(member);
      projectService.updateProject(project);

    }

  }

}
