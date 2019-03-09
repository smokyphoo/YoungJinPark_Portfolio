package article.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Table
@Entity
public class ArticleReply {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column
  private String writer;

  @Column
  private String comment;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column
  private Date regDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column
  private Date modifiedDate;


}
