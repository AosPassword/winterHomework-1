<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>莓人性没天理！</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" media="screen" href="css/style.css">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h1>
    <%
        String result = (String) request.getAttribute("result");
        if (result != null) {
            if (result.equals("success")) {%>
    <h1>管理!</h1>
    <form name="PageForm" action="manageSelect" method="post">
        <select name="action">
            <option value="INSERT INTO ">增</option>
            <option value="DELETE FROM ">删</option>
            <option value="UPDATE ">改</option>
            <option value="SELECT * FROM ">查</option>
        </select>
        <select name="type">
            <option value="video">视频</option>
            <option value="user">用户</option>
            <option value="barrage">弹幕</option>
            <option value="comment">评论</option>
            <option value="collection">收藏</option>
        </select>
        当
        <select name="username">
            <option value="name">名字</option>
            <option value="id">id</option>
            <option value="av_id">av号</option>
        </select>=<input type="text" name="text">
        <input type="submit">
    </form>
    <% } else {
    %>
    <script language="javascript">
        alert("您输入的密码不正确，请重新输入！");
        history.back();
    </script>
    <%
            }
        }
    %>
</h1>
</body>
</html>
