<%@ page import="cn.njcit.domain.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<head>
    <script type="text/javascript">
        function trunToLoginPage(){
            if(!location.href.endWith("webUser/loginPage.do")){
                location.href="<%=request.getContextPath()%>/webUser/loginPage.do"
            }
        }
        String.prototype.endWith=function(str){
            var reg=new RegExp(str+"$");
            return reg.test(this);
        }

        function trunToIndexPage(){
            if(location.href.endWith("webUser/loginPage.do")){//当用户已经登录并且链接地址跳转到登录时，那么自动跳转到首页
                location.href="<%=request.getContextPath()%>/webLeave/index.do"
            }
        }
    </script>
</head>

<%
    Object userOb = session.getAttribute("user");
    String contextPath = request.getContextPath();
    if(userOb!=null){
        User user = (User)userOb;
        if(user!=null){
%>
            <script type="text/javascript">
                        trunToIndexPage();
            </script>
<%
            //已经登录的用户，不做任何跳转
        }else{
%>

<script type="text/javascript">
    //未登录的而用户，如果当前页面不是登陆页那么就跳转到登陆页
    trunToLoginPage();
</script>

<%
    }
}else{
%>
<script type="text/javascript">
    //未登录的而用户，如果当前页面不是登陆页那么就跳转到登陆页
    trunToLoginPage();
</script>
<%
    }


%>
