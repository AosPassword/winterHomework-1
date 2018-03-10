<%--
  Created by IntelliJ IDEA.
  User: HC
  Date: 2018/3/10
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我！我就是她的达令！</title>
</head>
<body>
<input type="submit" name="return" onclick="javascript:history.back()" value="返回">
<%
    out.print((String)request.getAttribute("text"));
%>
</body>
</html>
