<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/views/main_include/header.jsp" %>
</head>

<body class="hold-transition skin-blue">
<div class="wrapper">
    <%@include file="/views/main_include/main_header.jsp" %>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
        </section>
        <!-- Main content -->
        <section class="content container-fluid">
            <div class="box">
                <form:form action="/modifyMemberProcess">

                    <div class="form-group">
                        <label>닉네임</label><br>
                        <input type="text" class="form-control" name="nickname" id="nickname"
                               placeholder="${member.nickname}" value="${member.nickname}"
                               oninput="checkNickname()"
                               autofocus/><br>
                        <label>현재 다니는 회사</label>
                        <input type="text" class="form-control" name="company"
                               placeholder="${member.company}" value="${member.company}"
                               autofocus/>
                        <label>직급</label>
                        <input type="text" class="form-control" name="position"
                               placeholder="${member.position}" value="${member.position}"
                               autofocus/>

                    </div>
                    <button a href="/deleteMemberProcess" class="btn btn-default">회원 탈퇴
                    </button><br><br><br>

                    <button type="submit" class="btn btn-default" id="submitBtn">확인</button>
                    <button a href="/" class="btn btn-default">취소</button>
                </form:form>
            </div>
        </section>
    </div>
    <%@include file="/views/main_include/footer.jsp" %>
</div>
<%@include file="/views/main_include/plugin_js.jsp" %>

<script>
  function checkNickname() {
    var inputNickname = $('#nickname').val();
    $.ajax({
      data: {
        nickname: inputNickname
      },
      contentType: 'application/json; charset=UTF-8',
      url: "/nicknameDuplicationCheck",
      success: function (data) {
        if (inputNickname == "" && data == "true") {
          $("#submitBtn").prop("disabled", true);
          $("#submitBtn").css("background-color", "#aaaaaa");
          $("#nickname").css("background-color", "#FFCECE");
          nicknameCheck = "true";
        } else if (data == "false") {
          $("#nickname").css("background-color", "#B0F6AC");
          nicknameCheck = "false";
        } else if (data == "true") {
          $("#submitBtn").prop("disabled", true);
          $("#submitBtn").css("background-color", "#aaaaaa");
          $("#nickname").css("background-color", "#FFCECE");
          nicknameCheck = "true";
        }
      }
    });
  }

</script>
</body>
</html>