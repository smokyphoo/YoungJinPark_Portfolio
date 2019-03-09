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
                    <div class="box">
                        <div class="box-header">
                            ${attachFile.fileName}
                        </div>
                        <div class="box-body">
                            <div id="diffview">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <a href="/project/${project.id}/getProject" class="btn btn-default">프로젝트로 가기</a>
    <%@include file="/views/main_include/footer.jsp" %>
</div>
<%@include file="/views/main_include/plugin_js.jsp" %>
</body>
<script type="text/javascript">
  function renderDiff(diffDiv, before, after) {
    diffDiv.appendChild(codediff.buildView(before, after));
  }

</script>

<script type="notjavascript" id="before">
<c:forEach items="${beforeFile}" var="fileText1">
    ${fileText1}
</c:forEach>

</script>

<script type="notjavascript" id="after">
<c:forEach items="${afterFile}" var="fileText2">
    ${fileText2}
</c:forEach>

</script>

<script type="text/javascript">

  var beforeText = $("#before").html();
  var afterText = $("#after").html();
  var diffDiv = $("#diffview").get(0);

  renderDiff(diffDiv, beforeText, afterText);

</script>

</html>