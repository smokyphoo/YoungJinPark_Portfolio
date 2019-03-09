package member.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import project.entity.Project;

@Entity
@Table
@Data
public class Member {

  @Id
  @Column
  private String email;

  @Column
  private String password;

  @Column
  private String nickname;

  @Column
  private String company;

  @Column
  private String position;

  @Column
  private String signupRoles;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "email", referencedColumnName = "id")
  private List<Project> followedProjectList;

}
