<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<header class="main-header">
    <!-- Logo -->
    <a href="/" class="logo">
        <span class="logo-lg">Project Manager</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">

        <div class="col-sm-9">
            <form action="/project/search">
                <div class="input-group input-group-lg">
                    <input type="text" id="keyboard" name="keyword" class="form-control" placeholder="Search...">
                    <span class="input-group-btn">
                            <button type="submit" id="search-btn" class="btn btn-flat"><i
                                    class="fa fa-search"></i>
                        </button>
                    </span>
                </div>
            </form>
        </div>

        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">

                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <!-- hidden-xs hides the username on small devices so only the image appears. -->
                        <span class="hidden-xs"><security:authentication
                                property="principal.member.nickname"/></span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- The user image in the menu -->
                        <li class="user-body">
                            <p>
                                <security:authentication property="principal.member.nickname"/>
                            </p>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="/modifyMember/" class="btn btn-default btn-flat">profile</a>
                            </div>
                            <div class="pull-right">
                                <a href="/logout" class="btn btn-default btn-flat">Sign out</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>
</body>
</html>
