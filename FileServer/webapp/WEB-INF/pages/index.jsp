<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>

<link rel="SHORTCUT ICON" href='<c:url value="/images/favicon.icon"/>' type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File-Server</title>
</head>
<body>

<c:forEach items="${files}" var="file">
	<a href="<c:url value="/files/${file.getUrl()}"/>">${file.getName()}</a><br>
	 
</c:forEach>




</body>
</html>