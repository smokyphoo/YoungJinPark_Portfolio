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
                            <!-- 프로젝트 내용 시작 -->
                            <div class="box box-solid box-primary">
                                <!-- 프로젝트 제목 -->
                                <div class="box-header">
                                    <h3>${project.name}</h3>
                                    <!-- 팔로우 버튼 시작 -->
                                    <c:if test="${project.creator ne principal.name}">
                                        <c:if test="${isFollow eq 'followed'}">
                                            <button id="followBtn"
                                                    class="btn btn-default"
                                                    value="unfollow">
                                                Unfollow
                                            </button>
                                        </c:if>
                                        <c:if test="${isFollow eq 'notFollowed'}">
                                            <button id="followBtn"
                                                    class="btn btn-default"
                                                    value="followed">
                                                Follow
                                            </button>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${project.creator eq principal.name}">
                                        <a href="/project/${project.id}/modifyProject" class="btn btn-default">
                                            프로젝트 수정
                                        </a>

                                        <a href="/project/${project.id}/deleteProjectProcess" class="btn btn-default">
                                            프로젝트 삭제
                                        </a>
                                    </c:if>
                                    <!-- 팔로우 버튼 끝 -->
                                    <!-- 다운로드 파일 버튼 시작 -->
                                    <c:if test="${project.attachFilesList.size()!=0}">
                                        <a class="btn pull-right btn-default"
                                           href="/project/download?fileName=${project.name}/confirmedVersion/${project.name}.zip">
                                            다운로드</a>
                                    </c:if>
                                    <!-- 다운로드 파일 버튼 끝 -->
                                </div>

                                <!-- 프로젝트 내용 시작 -->
                                <!-- 네비 테이블 ul 시작 -->
                                <ul class="nav nav-tabs" id="myTab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" id="home-tab" data-toggle="tab"
                                           href="#description" role="tab" aria-controls="home"
                                           aria-selected="true">Information</a>
                                    </li>

                                    <li class="nav-item">
                                        <a class="nav-link" id="profile-tab" data-toggle="tab"
                                           href="#request" role="tab" aria-controls="profile"
                                           aria-selected="false">Request</a>
                                    </li>

                                    <li class="nav-item">
                                        <a class="nav-link" id="contact-tab" data-toggle="tab"
                                           href="#previous_versions" role="tab"
                                           aria-controls="contact"
                                           aria-selected="false">Previous versions</a>
                                    </li>
                                </ul>
                                <!-- 네비 테이블 ul 끝 -->

                                <!-- 네비 테이블 div 시작-->
                                <div class="tab-content" id="myTabContent" style="height: 600px">
                                    <!-- 첫번째 네비 테이블 시작 -->
                                    <div class="tab-pane fade" id="description"
                                         role="tabpanel"
                                         aria-labelledby="home-tab">
                                        <div class="box-body">
                                            ${project.description}
                                            <footer>
                                                <c:if test="${member eq 'master'}">
                                                    <a href="/project/${project.id}/modifyArticle" class="btn btn-default">
                                                        프로젝트 수정
                                                    </a>
                                                    <a href="/project/${project.id}/deleteArticle" class="btn btn-default">
                                                        프로젝트 삭제
                                                    </a>
                                                </c:if>
                                            </footer>
                                        </div>
                                    </div>
                                    <!-- 첫번째 네비 테이블 끝 -->
                                    <!-- 두번째 네비 테이블 시작 -->
                                    <div class="tab-pane fade" id="request" role="tabpanel"
                                         aria-labelledby="profile-tab">
                                        <table id="request_list" class="table table-hover"
                                               style="width: 100%;">
                                            <thead>
                                            <tr>
                                                <td>요청 글</td>
                                            </tr>
                                            </thead>
                                            <c:forEach items="${project.articlesList}"
                                                       var="articleList">
                                                <tr>
                                                    <td>
                                                        <a href="/project/${project.id}/${articleList.id}/getArticle/">
                                                                ${articleList.title}
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                        <footer class="pull-right">
                                            <a href="/project/${project.id}/registerArticle"
                                               class="btn btn-default">
                                                글 등록</a>
                                        </footer>
                                    </div>
                                    <!-- 두번째 네비 테이블 끝 -->

                                    <!-- 세번째 네비 테이블 시작 -->
                                    <div class="tab-pane fade" id="previous_versions"
                                         role="tabpanel"
                                         aria-labelledby="contact-tab">
                                    </div>
                                    <!-- 세번째 네비 테이블 끝 -->
                                </div>
                                <!-- 네비 테이블 div 끝-->
                                <a href="/" class="btn btn-default">메인으로 가기</a>
                            </div>
                            <!-- 프로젝트 내용 끝 -->
                        </div>
                        <!-- 글 끝 -->
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

  var follow = $('#followBtn').val();
  var projectId = ${project.id};

  $(function () {
    $('#myTab li:first-child a').tab('show')
  });

  $(function () {
    $('#request_list').DataTable({
      "ordering": false,
      "searching": false,
      "pageLength": 15,
      "lengthChange": false
    })
  });

  $(function () {
    $('#followBtn').one('click', function () {
      $.ajax({
        type: 'post'
        , url: '/isFollowed'
        , dataType: 'TEXT'
        , data: {follow: follow, projectId: projectId}
        , success: function () {
          window.location.reload();
        }
      });
    })
  })

</script>
</html>