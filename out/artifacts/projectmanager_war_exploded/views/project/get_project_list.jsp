<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/views/main_include/header.jsp" %>
</head>

<body class="hold-transition skin-blue">
<%@ include file="/views/main_include/side_menubar.jsp"%>
<%@include file="/views/main_include/main_header.jsp" %>
<div class="content-wrapper">
    <section class="content container-fluid">
        <div class="row">
            <div class="col-xs-12">
                <div class="box-body">
                    <!-- 프로젝트 목록 시작 -->
                    <div class="box box-solid box-primary" style="height: 600px">
                        <div class="box-header">Project</div>
                        <!-- 프로젝트 목록 내용 시작-->
                        <div class="box-body">
                            <!-- 프로젝트 목록-->
                            <table id="project_list" class="table table-hover">
                                <thead>
                                <tr>
                                </tr>
                                </thead>
                                <c:forEach items="${projects}" var="projects">
                                    <tr>
                                        <td>
                                            <a href="/project/${projects.id}/getProject">
                                                    ${projects.name}</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- 프로젝트 목록 끝 -->
                        <footer>
                            <a href="/project/registerProject" class="btn btn-default pull-right">
                                글 등록
                            </a>
                        </footer>
                    </div>
                    <!-- 프로젝트 목록 내용 끝-->
                </div>
                <!-- 프로젝트 목록 끝 -->
            </div>
        </div>
    </section>
</div>
<%@include file="/views/main_include/footer.jsp" %>
</div>
</body>

<%@include file="/views/main_include/plugin_js.jsp" %>
<script>
  $(function () {
    $('#project_list').DataTable({
      "ordering": false,
      "searching": false,
      "pageLength": 15,
      "lengthChange": false
    })
  });
</script>
</html>