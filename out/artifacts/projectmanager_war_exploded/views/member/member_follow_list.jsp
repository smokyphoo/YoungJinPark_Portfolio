<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/views/main_include/header.jsp" %>
</head>
<body class="hold-transition skin-blue">
<div class="wrapper">
    <%@ include file="/views/main_include/side_menubar.jsp" %>
    <%@include file="/views/main_include/main_header.jsp" %>
    <div class="content-wrapper">
        <section class="content container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box-body">
                        <!-- 글 시작 -->
                        <div class="box">
                            <!-- 회원 팔로우 리스트 시작 -->
                            <div class="box box-solid box-primary">
                                <!-- 글 제목 -->
                                <div class="box-header">
                                    ${member.nickname}님의 팔로우 리스트
                                </div>
                                <table id="follow_list" class="table table-hover"
                                       style="width: 100%;">
                                    <thead>
                                    <tr>
                                        <td>프로젝트 이름</td>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${member.followedProjectList}"
                                               var="followList">
                                        <tr>
                                            <td>
                                                <a href="/project/${followList.id}/getProject/">
                                                        ${followList.name}
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                            <!-- 댓글 작성 끝 -->
                            <a href="/" class="btn btn-default" class="btn-default">메일으로 가기</a>
                        </div>
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

</script>
</body>
</html>