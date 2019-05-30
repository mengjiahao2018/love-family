<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    　　<!--  登陆成功会显示名字，这里var保存用户名字到username变量，下面就可以通过EL获取 -->
    <title>欢迎你，<security:authentication property="principal.username" var="username"/>${username}</title>　　　　　　
</head>
<body>

<!--  登陆成功会显示名字 -->
<security:authorize access="isAuthenticated()">
    <h3>登录成功！${username}</h3>
</security:authorize>　　

<!--  MENBER角色就会显示 security:authorize标签里的内容-->
<security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <p>你是超级管理员</p>
</security:authorize>

<security:authorize access="hasRole('ROLE_ADMIN')">
    <p>你是管理员</p>
</security:authorize>

<security:authorize access="hasRole('ROLE_USER')">
    <p>你是普通用户</p>
</security:authorize>

<!--  登出按钮，注意这里是post，get是会登出失败的 -->
<sf:form id="logoutForm" action="${pageContext.request.contextPath}/j_spring_security_logout" method="post">
    <a href="#" onclick="document.getElementById('logoutForm').submit();">注销</a>
</sf:form>
</body>
</html>