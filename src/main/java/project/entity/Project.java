package project.entity;

import article.entity.Article;
import article.entity.ArticleAttachFiles;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import member.entity.Member;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column
  private String creator;

  @Column
  private String name;

  @Column
  private String description;

  @CreationTimestamp
  @Column
  private Date regDate;

  @UpdateTimestamp
  @Column
  private Date updated;

  @OneToMany(cascade = CascadeType.ALL)
  List<Article> articlesList;

  @OneToMany(cascade = CascadeType.ALL)
  List<ArticleAttachFiles> AttachFilesList;

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "followedProjectList")
  List<Member> memberList;

}
