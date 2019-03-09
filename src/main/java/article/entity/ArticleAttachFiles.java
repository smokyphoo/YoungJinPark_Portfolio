package article.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Table
@Data
@Entity
public class ArticleAttachFiles {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long Id;

  @Column(length = 250)
  private String fileName;

  @CreationTimestamp
  @Column
  private Date date;

  @Column
  private String uploadPath;

  @Column(length = 100)
  private String uuid;

  @Column
  private Boolean isConfirmed;

}
