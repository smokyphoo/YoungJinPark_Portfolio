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
  @Column(length = 100)
  private String email;

  @Column(length = 100)
  private String password;

  @Column(length = 20)
  private String nickname;

  @Column(length = 40)
  private String company;

  @Column(length = 40)
  private String position;

  @Column(length = 40)
  private String signupRoles;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "email", referencedColumnName = "id")
  private List<Project> followedProjectList;

}
