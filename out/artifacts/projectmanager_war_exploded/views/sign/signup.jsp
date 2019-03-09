<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <security:authorize access="isAuthenticated()">
        <% response.sendRedirect("/"); %>
    </security:authorize>
    <link rel="stylesheet" href="/view_resources/sign/main.css">
    <link rel="stylesheet"
          href="/view_resources/main/bower_components/font-awesome/css/font-awesome.min.css"/>
</head>
<body>
<div class="wrapper">
    <form:form class="login" method="post" action="/signupProcess">

        <p class="title">Sign up</p>
        <input type="email" name="email" id="email" placeholder="example@email.com"
               oninput="checkEmail()" autofocus/>
        <i class="fa fa-user"></i>

        <input type="text" name="nickname" id="nickname" placeholder="Nickname"
               oninput="checkNickname()"
               autofocus/>
        <i class="fa fa-user"></i>

        <input type="password" name="password" id="password" placeholder="Password" required
               class="pass"
               oninput="pwdCheck()"/>
        <i class="fa fa-key"></i>

        <input type="password" name="repeatPassword" id="repeatPassword"
               placeholder="Repeat Password" required class="pass" oninput="pwdCheck()"/>
        <i class="fa fa-key"></i>

        <input type="submit" id="signupBtn" oninput="signupCheck()" value="Sign UP"/>
        <i class="fa fa-address-card"></i>

        <a href="/signin">Sign In</a><br>
    </form:form>
    <footer><a target="blank" href="http://boudra.me/">boudra.me</a></footer>
</div>
<%@include file="/views/main_include/plugin_js.jsp" %>
<script>

  var emailCheck = "true";
  var nicknameCheck = "true";
  var passwordCheck = "true";

  function checkEmail() {
    var inputEmail = $('#email').val();
    $.ajax({
      data: {
        email: inputEmail
      },
      contentType: 'application/json; charset=UTF-8',
      url: "/emailDuplicationCheck",
      success: function (data) {
        if (inputEmail == "" && data == "true") {
          $("#signupBtn").prop("disabled", true);
          $("#signupBtn").css("background-color", "#aaaaaa");
          $("#email").css("background-color", "#FFCECE");
          emailCheck = "true";
        } else if (data == "false") {
          $("#email").css("background-color", "#B0F6AC");
          emailCheck = "false";
        } else if (data == "true") {
          $("#signupBtn").prop("disabled", true);
          $("#signupBtn").css("background-color", "#aaaaaa");
          $("#email").css("background-color", "#FFCECE");
          emailCheck = "true";
        }
      }
    });
  }

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
          $("#signupBtn").prop("disabled", true);
          $("#signupBtn").css("background-color", "#aaaaaa");
          $("#nickname").css("background-color", "#FFCECE");
          nicknameCheck = "true";
        } else if (data == "false") {
          $("#nickname").css("background-color", "#B0F6AC");
          nicknameCheck = "false";
        } else if (data == "true") {
          $("#signupBtn").prop("disabled", true);
          $("#signupBtn").css("background-color", "#aaaaaa");
          $("#nickname").css("background-color", "#FFCECE");
          nicknameCheck = "true";
        }
      }
    });
  }

  function pwdCheck() {
    var password = $("#password").val();
    var repeatPassword = $("#repeatPassword").val();
    if (repeatPassword == "" && (password != repeatPassword || password == repeatPassword)) {
      $("#signupBtn").prop();
      $("#signupBtn").prop("disabled", true);
      $("#signupBtn").css("background-color", "#aaaaaa");
      $("#repeatPassword").css("background-color", "#FFCECE");
    } else if (password == repeatPassword) {
      $("#repeatPassword").css('background-color', '#B0F6AC');
      passwordCheck = "false";
      if (emailCheck == "false" && nicknameCheck == "false" && passwordCheck == "false") {
        $("#signupBtn").prop("disabled", false);
        $("#signupBtn").css('background-color', '#2196F3');
        passwordCheck = "false"
      }
    } else if (password != repeatPassword) {
      passwordCheck = "true";
      $("#signupBtn").prop("disabled", true);
      $("#signupBtn").css("background-color", "#aaaaaa");
      $("#repeatPassword").css("background-color", "#FFCECE");
    }
  }

  function signupCheck() {

    if (emailCheck == "true" || nicknameCheck == "true" || passwordCheck == "true") {
      $("#signupBtn").prop("disabled", true);
      $("#signupBtn").css("background-color", "#aaaaaa");
    } else {
      $("#signupBtn").prop("disabled", false);
    }
  }
</script>

</body>
</html>
