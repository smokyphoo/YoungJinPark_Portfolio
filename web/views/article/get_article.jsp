<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/views/main_include/header.jsp" %>
</head>
<body class="hold-transition skin-blue">
<div class="wrapper">
    <%@ include file="/views/main_include/side_menubar.jsp"%>
    <%@include file="/views/main_include/main_header.jsp" %>
    <div class="content-wrapper">
        <section class="content container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box-body">
                        <!-- 글 시작 -->
                        <div class="box">
                            <!-- 글 내용 시작 -->
                            <div class="box box-solid box-primary">
                                <!-- 글 제목 -->
                                <div class="box-header">
                                    <h3>${article.title}</h3>
                                    <c:if test="${isCreator eq 'creator'}">

                                        <a href="/project/${project.id}/${article.id}/modifyArticle/"
                                           class="btn btn-default pull-right">
                                            수정
                                        </a>

                                        <a href="/project/${project.id}/${article.id}/deleteArticle/"
                                           class="btn btn-default pull-right">
                                            삭제
                                        </a>
                                    </c:if>
                                </div>
                                <div class="box-body">
                                    ${article.content}
                                </div>
                                <c:if test="${article.attachFiles.size() != 0}">
                                    <c:forEach items="${article.attachFiles}" var="attaches">
                                        <a href="/project/${project.id}/${article.id}/${attaches.id}/${attaches.fileName}">${attaches.fileName}</a>
                                        <a href="/project/${project.id}/${article.id}/${attaches.id}/${attaches.fileName}/confirm"
                                           class="btn btn-default">확인
                                        </a>
                                        <a href="/project/download?fileName=${project.id}/${attaches.uuid}_${attaches.fileName}"
                                           class="btn btn-default">다운로드
                                        </a>
                                        <br>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <!-- 글내용 끝 -->

                            <!-- 댓글 내용 시작-->
                            <c:if test="${article.articleReplies ne null}">
                                <div name="reply" class="box box-solid box-default">
                                    <div class="box-header">댓글</div>
                                    <div class="box-body">
                                        <c:forEach items="${article.articleReplies}"
                                                   var="replies">
                                            <div class="box box-solid box-default">
                                            <div class="box-header">작성자 : ${replies.writer} <br>등록시간: ${replies.regDate.toGMTString()}</div>
                                            <div class="box-body">${replies.comment}<br>
                                            <c:if test="${replies.writer eq member.nickname}">
                                            <a href="/project/${project.id}/${article.id}/${replies.id}/deleteArticleReplyProcess"
                                               class="btn btn-default pull-right">
                                                삭제
                                            </a>
                                            </div>
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                            <!-- 댓글 내용 끝 -->

                            <!-- 댓글 작성 시작 -->
                            <button id="writeBtn" onclick="writeBtnFunction()" class="pull-right">
                                댓글 쓰기
                            </button>
                            <div id="writeReply" style="display:none">
                                <form action="/project/${project.id}/${article.id}/registerArticleReplyProcess/"
                                      name="replyForm">
                                    <textarea class="form-control" name="commentData" cols="20"
                                              rows="10"></textarea>
                                    <button type="submit" class="btn btn-default">
                                        전송
                                    </button>
                                </form>
                            </div>
                            <!-- 댓글 작성 끝 -->
                        </div>
                        <a href="/project/${project.id}/getProject" class="btn btn-default">프로젝트로 가기</a>
                        <!--글 끝 -->
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@include file="/views/main_include/footer.jsp" %>
</div>
</body>

<%@include file="/views/main_include/plugin_js.jsp" %>
<script>

  function writeBtnFunction() {
    var x = document.getElementById("writeReply");
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }

  function modifyBtnFunction() {
    var x = document.getElementById("modifyReply");
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }

</script>
</body>
</html>