<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 31/05/2022
  Time: 11:11 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>List</h2>
<c:forEach items='${ds}' var="cl">
    <h2>${cl.name}</h2>
</c:forEach>
</body>
</html>
