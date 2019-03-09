package project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.Project;
import project.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  protected ProjectRepository projectRepository;

  @Override
  public void registerProject(Project project) {
    projectRepository.registerProject(project);
  }

  @Override
  public void updateProject(Project project) {
    projectRepository.updateProject(project);
  }

  @Override
  public Project findByProjectName(String projectName) {
    return projectRepository.findByProjectName(projectName);
  }

  @Override
  public Project findByProjectId(Long projectId) {
    return projectRepository.findByProjectId(projectId);
  }

  @Override
  public List<Project> findAllProject() {
    return projectRepository.findAllProject();
  }

  @Override
  public List<Project> searchProject(String search) {
    return projectRepository.searchProject(search);
  }

  @Override
  public void deleteProject(Long id) {
    projectRepository.deleteProject(id);
  }
}
