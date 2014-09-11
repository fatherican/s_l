<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String isQueyPage = request.getParameter("isQueyPage")==null?"NULL":request.getParameter("isQueyPage");
%>
<% if ("yes".equals(isQueyPage)) {//当前页面需要使用Jquery %>
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
<%}%>
