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
                        <div class="box box-solid box-primary">
                            <!-- 프로젝트 수정 시작 -->
                            <div class="box box-solid box-primary" style="height: 500px">
                                <div class="box-header">
                                    <h3 class="panel-title">${project.name}</h3>
                                </div>
                                <div class="box-body" style="height: 80%">
                                    <form action="/project/${project.id}/modifyArticleProcess/" style="height: 100%">
                                        <textarea type="textarea" name="description" style="width: 100%; height: 100%">${project.description}</textarea>
                                        <footer>
                                            <button type="submit" class="btn btn-default pull-right">확인
                                            </button>
                                        </footer>
                                    </form>
                                </div>
                                <a href="/project/${project.id}/getProject" class="btn btn-default">프로젝트로 가기</a>
                            </div>
                            <!-- 글 프로젝트 끝 -->
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

</html>