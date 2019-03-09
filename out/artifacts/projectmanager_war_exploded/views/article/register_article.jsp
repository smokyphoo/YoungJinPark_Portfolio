<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                        <div class="box"  style="height: 600px">
                            <!-- 프로젝트 내용 시작 -->
                            <div class="box box-solid box-primary">
                                <!-- 프로젝트 이름 시작 -->
                                <div class="box-header">
                                    프로젝트 등록
                                </div>
                                <!-- 프로젝트 이름 끝 -->
                                <form action="/project/${project.id}/registerArticleProcess">
                                    <div class="box-body form-group">
                                        <!-- 글 제목 -->
                                        <input type="text" class="form-control form-text" name="title" autofocus/>
                                        <!-- 글 이름 끝 -->
                                        <br><br>
                                        <!-- 글 설명 시작 -->
                                        <textarea class="form-control form-text" name="content" cols="30"
                                                  rows="10"></textarea>
                                        <!-- 글 설명 끝 -->
                                    </div>
                                    <footer>
                                        <button type="submit" class="btn btn-default pull-right">확인
                                        </button>
                                    </footer>
                                </form>
                            </div>
                            <br><br>
                            <!-- 업로드 시작 -->
                            <div class='uploadDiv'>
                                <input type="file" id="uploadFile" name="uploadFile" multiple>
                            </div>
                            <!-- 업로드 끝 -->
                        </div>
                        <a href="/project/${project.id}/getProject" class="btn btn-default">프로젝트로 가기</a>
                        <!-- 프로젝트 내용 끝 -->
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

  var inputElement = document.querySelector('input[type="file"]');
  var pond = FilePond.create(inputElement);

  pond.setOptions({
    allowMultiple: false,
    dropOnElement: true,
    allowRevert: false,
    server: {
      process: {
        url: '/project/article/upload',
        method: 'post'
      },
      revert: {
        url: '/deleteFile',
        method: 'post'
      }

    }
  })

</script>
</html>
