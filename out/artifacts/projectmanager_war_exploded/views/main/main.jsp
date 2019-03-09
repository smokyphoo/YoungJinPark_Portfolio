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
        <!-- Content Header (Page header) -->
        <!-- Main content -->
        <section class="content container-fluid">
            <div class="box box-solid box-primary" style="height: 600px">
                <div class="box box-solid box-primary" style="height: 600px">
                    <div class="box-header">최근 업데이트된 프로젝트</div>
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
                                    <td class="pull-right">
                                        ${projects.updated.toGMTString()}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <%@include file="/views/main_include/footer.jsp" %>
</div>
<%@include file="/views/main_include/plugin_js.jsp"%>
</body>
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