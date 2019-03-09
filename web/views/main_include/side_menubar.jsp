<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left info">
                <p><security:authentication property="principal.member.nickname"/></p>
            </div>
        </div>
        <ul class="sidebar-menu" data-widget="tree">
            <li class="header">MAIN NAVIGATION</li>
            <li>
                <a href="/followList">
                    <span>Follow List</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
            </li>
        </ul>
    </section>
</aside>
</body>
</html>
