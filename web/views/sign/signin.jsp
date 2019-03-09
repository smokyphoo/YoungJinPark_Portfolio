<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <form:form class="login" action="/login" method="post">
        <p class="title">Log in</p>
        <input type="email" name="email" placeholder="example@email.com" autofocus/>
        <i class="fa fa-user"></i>
        <input type="password" name="password" placeholder="********"/>
        <i class="fa fa-key"></i>

        <a href="/signup">Sign up</a><br>
        <button>
            Log in
        </button>
    </form:form>

    <footer><a target="blank" href="http://boudra.me/">boudra.me</a></footer>
    </p>
</div>
</body>
</html>
