<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
    <!-- jQuery Version 1.11.0 -->
    <script src="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/js/jquery-1.11.0.js"></script>
    <script type="text/javascript">
        String.prototype.endWith=function(str){
            var reg=new RegExp(str+"$");
            return reg.test(this);
        }

        function showUpdatePasswordModal(){
            $("#updatePasswordModal").modal();
        }
        $(document).ready(function(){
            //设置active class
            var hrefVal = location.href;
            if(hrefVal.endWith("webLeave/checkedIndex.do")){//已审批列表
                $("#webLeaveCheckA").addClass("active");
                $("#webLeaveLi").addClass("active");
            }else if(hrefVal.endWith("webLeave/index.do")){//未审批界面
                $("#webLeaveUncheckA").addClass("active");
                $("#webLeaveLi").addClass("active");
            }else if(hrefVal.endWith("webUser/studentManagerIndex.do")){//学生管理界面
                $("#webUserLi").addClass("active");
                $("#webUserStudentA").addClass("active");
            }else if(hrefVal.endWith("webUser/teacherManagerIndex.do")){//学生管理界面
                $("#webUserLi").addClass("active");
                $("#webUserTeacherA").addClass("active");
            }else if(hrefVal.endWith("webManager/classManageListIndex.do")){//班级管理界面
                $("#webUserLi").addClass("active");
                $("#webClassManagerA").addClass("active");
            }else if(hrefVal.endWith("webManager/colleageManageListIndex.do")){//学院管理界面
                $("#webUserLi").addClass("active");
                $("#webColleageManagerA").addClass("active");
            }else if(hrefVal.endWith("webManager/informationImportIndex.do")){//信息导入界面
                $("#webImportLi").addClass("active");
                $("#webManagerImportA").addClass("active");
            }


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
                    url: "<%=request.getContextPath()%>/webUser/updatePassword.do",
                    data: $('#passwordForm').serialize(),
                    beforeSend:function(){
                        //使form表单不可用
                        $('#passwordFormFieldset').attr("disabled",true);
                    },
                    success: function (data) {
                        $('#passwordFormFieldset').attr("disabled",false);
                        var code = data["code"];
                        if(code=='200'){
                            $("#updatePasswordModal").modal('hide');
                            $("#passwordForm")[0].reset();
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
        <a class="navbar-brand" href="<%=request.getContextPath()%>/webLeave/index.do">学生请假管理系统</a>
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
                <li><a href="<%=request.getContextPath()%>/webUser/logout.do"><i class="fa fa-sign-out fa-fw"></i> 登出</a>
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
                <li id="webLeaveLi">
                    <a href="#"><i class="fa fa-edit fa-fw"></i> 假条审核<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a  id="webLeaveUncheckA" href="<%=request.getContextPath()%>/webLeave/index.do"> 未审批</a>
                        </li>
                        <li>
                            <a id="webLeaveCheckA"  href="<%=request.getContextPath()%>/webLeave/checkedIndex.do"> 已审批</a>
                        </li>
                    </ul>
                </li>
                <li id="webUserLi">
                    <a href="#"><i class="fa fa-dashboard fa-fw"></i>管理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a  id="webUserStudentA" href="<%=request.getContextPath()%>/webUser/studentManagerIndex.do">学生管理</a>
                        </li>
                        <c:if test="${user.role eq '3' or user.role eq '2'}">
                            <li>
                                <a id="webUserTeacherA"  href="<%=request.getContextPath()%>/webUser/teacherManagerIndex.do">教师管理</a>
                            </li>
                        </c:if>
                        <c:if test="${user.role eq '3' or user.role eq '2'}">
                            <li>
                                <a id="webClassManagerA"  href="<%=request.getContextPath()%>/webManager/classManageListIndex.do">班级管理</a>
                            </li>
                        </c:if>
                        <c:if test="${user.role eq '3'}">
                            <li>
                                <a id="webColleageManagerA"  href="<%=request.getContextPath()%>/webManager/colleageManageListIndex.do">学院管理</a>
                            </li>
                        </c:if>
                    </ul>
                </li>
                <li id="webImportLi">
                    <a href="#"><i class="fa fa-files-o  fa-fw"></i>信息导入<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a  id="webManagerImportA" href="<%=request.getContextPath()%>/webManager/informationImportIndex.do">导入</a>
                        </li>
                    </ul>
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
            <form class="form-horizontal" id="passwordForm" name="passwordForm" role="form" >
                <fieldset id="passwordFormFieldset">
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
