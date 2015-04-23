<%@page import="java.nio.file.attribute.UserPrincipal"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rent a Car - Main</title>

<style>
html, body {
	margin: 0px;
	padding: 0px;
	border: 0px;
	height: 100%;
	overflow: hidden;
}

body::after {
	content: "";
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	position: absolute;
	z-index: -1;
}

.main {
	width: 100%;
	height: 50px;
	position: absolute;
	text-align: center;
	top: 100px;
	font-size: 24pt;
}

.logout {
	width: 100%;
	height: 50px;
	position: absolute;
	text-align: center;
	top: 200px;
	font-size: 12pt;
}

.imgref {
	width: 100%;
	height: 20px;
	position: absolute;
	text-align: center;
	bottom: 20px;
	font-size: 8pt;
}
</style>

</head>
<body>
    <div class="main">
        Hello,
        <%=request.getUserPrincipal().getName()%>
    </div>

    <div class="logout">
        <a href="<%=pageContext.getServletContext().getInitParameter("logoutUrl")%>">Log out</a>
    </div>

</body>
</html>
