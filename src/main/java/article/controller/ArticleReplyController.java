package article.controller;

import article.entity.Article;
import article.entity.ArticleReply;
import article.service.ArticleReplyService;
import article.service.ArticleService;
import java.security.Principal;
import java.util.List;
import member.entity.Member;
import member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.entity.Project;
import project.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ArticleReplyController {

  @Autowired
  private ArticleService articleService;
  @Autowired
  private ArticleReplyService articleReplyService;
  @Autowired
  private MemberService memberService;

  /*
    댓글 작성 진행
   */
  @RequestMapping("/{projectId}/{articleId}/registerArticleReplyProcess/")
  public String registryArticleProcess(@PathVariable Long projectId,
      @PathVariable Long articleId,
      @RequestParam String commentData, @AuthenticationPrincipal Principal principal) {

    Article article = articleService.findByArticleId(articleId);
    ArticleReply articleReply = new ArticleReply();
    Member member = memberService.findMemberByEmail(principal.getName());

    articleReply.setWriter(member.getNickname());
    articleReply.setComment(commentData);
    List<ArticleReply> articleReplies = article.getArticleReplies();
    articleReplies.add(articleReply);

    articleService.updateArticle(article);
    articleReplyService.saveArticleReply(articleReply);

    return "redirect:/project/" + projectId + "/" + article.getId() + "/getArticle";

  }

  /*
    댓글 삭제
   */
  @RequestMapping("/{projectId}/{articleId}/{articleReplyId}/deleteArticleReplyProcess")
  public String deleteArticle(@PathVariable Long projectId,
      @PathVariable Long articleId, @PathVariable Long articleReplyId) {

    Article article = articleService.findByArticleId(articleId);

    int articleReplyIndex = 0;
    int articleReplyTracking = 0;

    for (ArticleReply index : article.getArticleReplies()) {
      if (index.getId().equals(articleReplyId)) {
        articleReplyIndex = articleReplyTracking;
        break;
      }
      articleReplyTracking++;
    }

    article.getArticleReplies().remove(articleReplyIndex);

    articleService.updateArticle(article);
    articleReplyService.deleteArticleReply(articleReplyId);

    return "redirect:/project/" + projectId + "/" + article.getId() + "/getArticle";

  }
}
