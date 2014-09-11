<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <!-- jQuery Version 1.11.0 -->
    <script src="/resources/bootstrap-sb-admin/js/jquery-1.11.0.js"></script>
    <script type="text/javascript">
        function showUpdatePasswordModal(){
            $("#updatePasswordModal").modal();
        }
        $(document).ready(function(){
            $("#updatePasswordBT").click(function(){
                var oldPasswordVal = $("#oldPassword").val();
                var passwordVal = $("#password").val();
                var confirmPasswordVal = $("#confirmPassword").val();
                if(oldPasswordVal==''){
                    alert("原密码不允许为空");
                    return;
                }
                if(passwordVal=='' || confirmPasswordVal==''){
                    alert("新密码或确认密码不允许为空");
                    return;
                }
                if(passwordVal!=confirmPasswordVal){
                    alert("两次密码不一致");
                    return;
                }
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/webUser/updatePassword.do",
                    data: $('#objForm').serialize(),
                    beforeSend:function(){
                        //使form表单不可用
                        $('#formFieldset').attr("disabled",true);
                    },
                    success: function (data) {
                        $('#formFieldset').attr("disabled",false);
                        var code = data["code"];
                        if(code=='200'){
                            $("#updatePasswordModal").modal('hide');
                            $("#objForm")[0].reset();
                            alert("密码修改成功");
                            return;
                        }else{
                            alert(data['msg']);
                        }
                    },
                    error: function(data) {




                    }

                });




            });
        });
    </script>
</head>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/webLeave/index.do">学生请假管理系统</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="javascript:showUpdatePasswordModal();"><i class="fa fa-gear fa-fw"></i> 修改密码</a>
                </li>
                <li class="divider"></li>
                <li><a href="/webUser/logout.do"><i class="fa fa-sign-out fa-fw"></i> 登出</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="active">
                    <a href="#"><i class="fa fa-edit fa-fw"></i> 假条审核<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="active" href="#"> 未审批</a>
                        </li>
                        <li>
                            <a href="#"> 已审批</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i> 人员管理</a>
                </li>
                <li>
                    <a href="tables.html"><i class="fa fa-files-o  fa-fw"></i> 信息导入</a>
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>



<!-- Modal -->
<div class="modal fade" id="updatePasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>
            <form class="form-horizontal" id="objForm" name="objForm" role="form" >
                <fieldset id="formFieldset">
                    <div class="modal-body">

                            <div class="form-group">
                                <label for="oldPassword" class="col-sm-2 control-label">原密码</label>
                                <div class="col-sm-10">
                                    <input type="email" class="form-control" id="oldPassword" name="oldPassword" placeholder="原密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-sm-2 control-label">新密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="password"  name="password" placeholder="新密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=confirmPassword" class="col-sm-2 control-label">确认密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
                                </div>
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="updatePasswordBT" class="btn btn-primary">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
