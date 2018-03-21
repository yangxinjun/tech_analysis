<%--
  Created by IntelliJ IDEA.
  User: yxj
  Date: 2018/3/16
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.google.gson.*" %>
<%@ page import="search.search_index" %>
<%--<%@ page import="org.apache.lucene.queryparser.classic.ParseException" %>--%>
<%@page import="search.search_index"%>
<%--<jsp:useBean id="mybook" scope="page" class="search.search_index" />--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h1>满足条件的专家</h1>
<ul>
    <%
        String name = new String((request.getParameter("expert")).getBytes("ISO-8859-1"),"UTF-8");
        String company = new String((request.getParameter("workpalace")).getBytes("ISO-8859-1"),"UTF-8");
        String location = new String((request.getParameter("area")).getBytes("ISO-8859-1"),"UTF-8");
        String study = new String((request.getParameter("area")).getBytes("ISO-8859-1"),"UTF-8");
        String[] str1={"name","company"};
        String[] str2={name,company};
        JsonObject res=new JsonObject();
//        search.search_index temp=new search_index();
        try {
            res=search_index.search(str1,str2);
        }catch (Exception e) {
            System.out.println(e);
        }
        if(res==null)
        {
            res=search_index.search(str1,str2);
            name=res.get("name").toString();
            company=res.get("name").toString();
        }else {
            name="不存在";
            company="不存在";
            location="不存在";
            study="不存在";
        }

    %>
    <li><p><b>专家 名字:</b>
        <%= name%>
    </p></li>
    <li><p><b>所在单位:</b>
        <%= company%>
    </p></li>
    <li><p><b>所在城市:</b>
        <%= location%>
    </p></li>
    <li><p><b>研究方向:</b>
        <%= study%>
    </p></li>
</ul>
</body>
</html>
