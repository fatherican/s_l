<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
    <%@include file="/common/jsp/header.jsp"%>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">请登录</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" name="objForm" id="objForm">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="用户名" id="userNo" name="userNo" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" id="password" name="password" type="password" value="">
                                </div>
                              <%--  <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>--%>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="button" id="loginBt" class="btn btn-lg btn-success btn-block" >登陆</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade bs-example-modal-sm" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="false">
        <div class="modal-dialog modal-sm">
            <div class="modal-content text-center">
                </br>
                    <span id="loginModalText">

                    </span>

                </br>
                </br>
            </div>
        </div>
    </div>

    <!-- jQuery Version 1.11.0 -->
    <script src="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/js/jquery-1.11.0.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/js/plugins/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/js/sb-admin-2.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#loginBt").click(function(){
                var userNoVal = $("#userNo").val();
                var passwordVal = $("#password").val();
                if(userNoVal=='' || passwordVal==''){
                    alert("用户名或密码为空");
                    return;
                }

                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "<%=request.getContextPath()%>/webUser/login.do",
                    data: $('#objForm').serialize(),
                    beforeSend:function(){
                        $("#loginModalText").html("正在登陆请稍后.....");
                        $("#loginModal").modal({
                            keyboard:false,
                            backdrop: 'static'
                        });
                    },
                    success: function (data) {
                        var code = data["code"];
                        if(code=='200'){
                            $("#loginModal").modal('hide');
                            location.href="<%=request.getContextPath()%>/webLeave/index.do"
                        }else{
                            $("#loginModal").modal('hide');
                            alert(data['msg']);
                        }




                    },
                    error: function(data) {




                    }

                });




            });
        });

    </script>
</body>

</html>
