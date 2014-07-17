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
<h1>»¶Ó­£º£º<%=(String)session.getAttribute("title") %></h1>
<br/>
<h1>µ±Ç°Ä¿Â¼Îª£º<%=fe.getName()%></h1>
<h2>ÄÚÈÝÎª£º</h2>
<p>
<form action="UploadFile" method="get">
<input type="text" name="<%=fe.getName()%>" value="<%=fe.getContent()%>">
<br/>
<input type="submit" value="Submit">
</form>
<p>
<h2>Ä¿Â¼Îª£º</h2>
<p>
<%
ArrayList<String> strArray=fe.getSubFile();
for(int i=0;i<strArray.size();i++){
out.println(strArray.get(i));
%>
<br/>
<% 
}
%>
</p>
</body>
</html>