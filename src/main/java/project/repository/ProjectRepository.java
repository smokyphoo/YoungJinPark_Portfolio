package project.repository;

import java.util.List;
import project.entity.Project;

public interface ProjectRepository {

  void registerProject(Project project);

  void updateProject(Project project);

  Project findByProjectName(String projectName);

  Project findByProjectId(Long projectId);

  List<Project> findAllProject();

  List<Project> searchProject(String search);

  void deleteProject(Long id);

}
