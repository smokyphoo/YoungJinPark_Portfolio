package article.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Data
@Entity
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column
  private String creator;

  @Column
  private String title;

  @Column
  private String content;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column
  private Date regDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column
  private Date modifiedDate;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  List<ArticleAttachFiles> attachFiles;

  @OneToMany(cascade = CascadeType.ALL)
  List<ArticleReply> articleReplies;
}


