<%@page import="com.dips.demo.FileExt"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
<%!FileExt fe;%>
<%
fe=(FileExt)session.getAttribute("FileExt");
%>
<h1>欢迎：：<%=(String)session.getAttribute("title") %><a href="CleanCookie">退出</a></h1>
<br/>
<h1>当前目录为：<%=fe.getName()%></h1>
<h2>内容为：</h2>
<p>
<%=fe.getContent() %>
</p>
<a href="edit.jsp">编辑</a> <a href="add.jsp">添加</a> <a href="Delete">删除</a> <a href="Return">返回上一级</a>
<h2>目录为：</h2>
<p>
<%
ArrayList<String> strArray=fe.getSubFile();
for(int i=0;i<strArray.size();i++){
%>
<a href="GetFile?FileName=<%=strArray.get(i) %>">	
<%out.println(strArray.get(i));
%>
</a>
<br/>
<% 
}
%>
</p>
</body>
</html>