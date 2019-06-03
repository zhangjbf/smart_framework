<%--
  Created by IntelliJ IDEA.
  User: javab
  Date: 2019/6/3
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${name}
<form id="upload" method="post" action="<%=request.getContextPath()%>/upload" enctype="multipart/form-data">
    <input type="file" name="file">
    <button type="submit">提交</button>
</form>
</body>
</html>
