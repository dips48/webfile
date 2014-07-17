<%@page import="com.dips.demo.*"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%!FileExt fe;%>
<%
fe=(FileExt)session.getAttribute("FileExt");
%>
<h1>»¶Ó­£º<%=(String)session.getAttribute("title") %></h1>
<br/>
<h1>µ±Ç°Îª<%=fe.getName()%>ÏÂ</h1>
<body>
<form action="CreateNew" method="get">
<input type="text" name="bt">
<input type="text" name="content">
<br/>
<input type="submit" value="Ìí¼Ó">
</form>
</body>
</html>