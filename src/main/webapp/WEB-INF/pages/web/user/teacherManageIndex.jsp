<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>请假管理-教师管理</title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- DataTables CSS -->
    <link href="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/css/plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- self custom CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/mycss.css" rel="stylesheet">



    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<%@include file="/common/jsp/header.jsp"%>

<div id="wrapper">
    <!-- Navigation -->
    <%@include file="/common/jsp/nav.jsp"%>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12 text-right">

            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        学生列表
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="row">
                            <div class="col-lg-12 text-right">
                                <button type="button" id="addStudentBt" class="btn btn-primary">新增</button>
                            </div>
                            <!-- /.col-lg-12 -->
                        </div>


                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="leavelist-table">
                                <thead>
                                <tr>
                                    <th>工号</th>
                                    <th>姓名</th>
                                    <th>学院</th>
                                    <th>角色</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->

        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- 添加student Modal -->
<div class="modal fade" id="addTeacherModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addModalLabel">教师信息添加(初始密码为  123456)</h4>
            </div>
            <form class="form-horizontal" id="addTeacherForm" name="addTeacherForm" role="form" >
                <fieldset id="addTeacherFormFieldset">
                    <div class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="addTeacherTeacherNum" class="col-sm-2 control-label">工号</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="teacherWorkNum" id="addTeacherTeacherNum" placeholder="工号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addTeacherTeacherName" class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="teacherName"  id="addTeacherTeacherName" placeholder="姓名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addStudentColleageId" class="col-sm-2 control-label">学院</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="addStudentColleageId" name="colleageId">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group <c:if test="${ user.role ne '3'}"> sr-only </c:if> ">
                                <label for="addTeacherRoleId"  class="col-sm-2 control-label">角色</label>
                                <div class="col-sm-10">
                                    <select class="form-control"  id="addTeacherRoleId"  name="role" <c:if test="${ user.role ne '3'}"> disabled </c:if>>
                                            <option value="1">辅导员</option>
                                            <option value="2">学管处</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="reset" id="addTeacherResetBt" class="btn btn-default sr-only"></button>
                        <button type="button" id="addTeacherFormBt" class="btn btn btn-success">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- 编辑 教师信息 Modal -->
<div class="modal fade" id="editTeacherModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">教师信息修改</h4>
            </div>
            <form class="form-horizontal" id="editTeacherForm" name="editTeacherForm" role="form" >
                <fieldset id="editTeacherFormFieldset">
                    <input type="hidden" name="token" id="editTeacherToken">
                    <input type="hidden" name="teacherId" id="editTeacherTeacherId">
                    <div class="modal-body">
                    <div class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="editTeacherTeacherNum" class="col-sm-2 control-label">工号</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="teacherWorkNum" id="editTeacherTeacherNum" placeholder="工号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="editTeacherTeacherName" class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="teacherName"  id="editTeacherTeacherName" placeholder="姓名">
                                </div>
                            </div>
                            <div class="form-group" >
                                <label for="editTeacherColleageId" class="col-sm-2 control-label">学院</label>
                                <div class="col-sm-10 "  >
                                    <select class="form-control disabled" id="editTeacherColleageId" name="colleageId" <c:if test="${user.role eq '2'}">disabled</c:if>>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group <c:if test="${ user.role ne '3'}"> sr-only </c:if> ">
                                <label for="editTeacherRoleId"  class="col-sm-2 control-label">角色</label>
                                <div class="col-sm-10">
                                    <select class="form-control"  id="editTeacherRoleId"  name="role" <c:if test="${ user.role ne '3'}"> disabled </c:if>>
                                        <option value="1">辅导员</option>
                                        <option value="2">学管处</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="editStudentBt" class="btn btn btn-success">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- 重置密码  Modal -->
<div class="modal fade" id="resetPasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" >重置密码</h4>
            </div>
            <form class="form-horizontal" id="resetPasswordForm" name="resetPasswordForm" role="form" >
                <fieldset id="resetPasswordFormFieldset">
                    <input type="hidden" name="userId" id="resetPasswordUserId">
                    <input type="hidden" name="token"  id="resetPasswordtoken">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="password" name="password" value="123456">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="resetPasswordBT" class="btn btn-primary">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!--  删除老师 Modal -->
<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" >确定删除用户</h4>
            </div>
            <fieldset id="deleteUserFormFieldset">
                <form class="form-horizontal" id="deleteUserForm" name="deleteUserForm" role="form" >
                        <input type="hidden" name="teacherId" id="deleteUserId">
                        <input type="hidden" name="token"  id="deleteUserToken">
                        <input type="hidden" name="role"  id="deleteUserRole">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" id="deleteUserBT" class="btn btn-primary">确定</button>
                        </div>
                    </fieldset>
                </form>
            </fieldset>
        </div>
    </div>
</div>

<!-- 查看老师负责的班级 Modal -->
<div class="modal fade" id="teacherManagedClassModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="">编辑老师负责的班级</h4>
            </div>
            <div class="modal-body">
                <form class="form-inline" id="teacherManagedClassForm" name="teacherManagedClassForm" role="form" >
                    <fieldset id="teacherMangagedClassFormFieldset">
                        <input type="hidden" name="userId" id="teacherManagedUserId">
                        <input type="hidden" name="token"  id="teacherManagedToken">
                        <c:if test="${user.role eq '3'}">
                            <div class="form-group">
                                <div class="input-group">
                                    <label class="input-group-addon" for="teacherManagedColleageId">学院</label>
                                    <select class="form-control" id="teacherManagedColleageId" name="colleageId">

                                    </select>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon" for="teacherManagedClassName">班级</div>
                                <input class="form-control" name="className" type="text" value="" id="teacherManagedClassName">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon" for="teacherManagedState">管理状态</div>
                                <select class="form-control" id="teacherManagedState" name="managed">
                                     <option value="">请选择</option>
                                    <option value="1">已负责</option>
                                    <option value="0">管理中</option>
                                     <option value="2">未管理</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                           <button id="queryBtn" type="button" class="btn btn-primary">查询</button>
                        </div>


                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="teacherManagedClassTable">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>学院</th>
                                        <th>班级</th>
                                        <th>负责人</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>



                    </fieldset>
                </form>
            </div>

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


<script type="text/javascript" src="<%=request.getContextPath()%>/resources/datables/js/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap-sb-admin/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script type="text/javascript">
    (function($){
        $.fn.serializeJson=function(){
            var serializeObj={};
            var array=this.serializeArray();
            var str=this.serialize();
            $(array).each(function(){
                if(serializeObj[this.name]){
                    serializeObj[this.name]=serializeObj[this.name]+"`"+this.value;
//                        if($.isArray(serializeObj[this.name])){
//                            serializeObj[this.name].push(","+this.value);
//                        }else{
//                            serializeObj[this.name]=[serializeObj[this.name],this.value];
//                        }
                }else{
                    serializeObj[this.name]=this.value;
                }
            });
            return serializeObj;
        };
    })(jQuery);



    $(document).ready(function () {
            bindEvent();
            initDataTable();
//        $('#leavelist-table').dataTable();
//        initColumn();


    });
    function bindEvent(){
        //重置密码确定按钮事件绑定
        $("#resetPasswordBT").click(function(){
            resetPassword(true);
        });


        //学院切换事件
        $("#editStudentColleageId").change(function(){
            var colleageId = $(this).val();
            getClasses(colleageId)
            $("#editStudentColleageName").val($("#editStudentColleageId  option:selected").text());
        });

        //班级切换事件
        $("#editStudentClassId").change(function(){
            $("#editStudentClassName").val($("#editStudentClassId  option:selected").text());
        });

        //修改用户信息确定按钮
        $("#editStudentBt").click(function(){
            submitEditStudentForm();
        });
        //删除学生按钮确定按钮
        $("#deleteUserBT").click(function(){
            confirmDeleteUser();
        });
        //新增学生按钮
        $("#addStudentBt").click(function(){
            addTeacherModal();
        });
        //新增学生确定 按钮
        $("#addTeacherFormBt").click(function(){
            submitaddTeacherForm();
        });


        $("#queryBtn").click(function(){
            teacherManagedPostData = $("#teacherManagedClassForm").serializeJson();
            teacherManagedTable.page('first').draw();
        });



    }



    var dataTable;
    //初始化表格
    function initDataTable() {
            var postData=new Object();
            dataTable=$("#leavelist-table").DataTable( {
                'dom': 'rt<"row"<"col-sm-3"i><"col-sm-6 float_left"p><"col-sm-3"l>>', // 元素布局
                'language': {
                    'emptyTable': '没有数据',
                    'loadingRecords': '加载中...',
                    'processing': '查询中...',
                    'search': '检索:',
                    'lengthMenu': '每页 _MENU_ 条',
                    'zeroRecords': '没有数据',
                    'paginate': {
                        'first':      '第一页',
                        'last':       '最后一页',
                        'previous':   '上一页',
                        'next':       '下一页'
                    },
                    'info': '共计 _TOTAL_  条',
                    'infoEmpty': '没有数据',
                    'infoFiltered': '(过滤总件数 _MAX_ 条)'
                },
                renderer: "bootstrap",
                "searching": false,//不显示搜索框
                "initComplete":function( settings, json ){
                    json=null;
                },
                "drawCallback": function( settings ) {
                    return null;
                },
                "columns": [
                    { "data": "userNo"},
                    { "data": "name","orderable": false},
                    { "data": "colleageName","orderable": false},
                    { "data": "role","orderable": false,"render": function ( data, type, full, meta ) {
                            var roleStr = '';
                            if(data=='1'){//辅导员
                                roleStr = '辅导员';
                            }else if(data=='2'){//学管处
                                roleStr='学管处';
                            }
                            return roleStr;
                        }
                    },
                    { "data": "cz","width": "20%" ,"orderable": false,"render": function ( data, type, full, meta ) {
                            var userId = full['userId'];
                            var token = full['token'];
                            var userNo = full['userNo'];
                            var name = full['name'];
                            var colleageId = full['colleageId'];
                            var colleageName = full['colleageName'];
                            var classId = full['classId'];
                            var className = full['className'];
                            var role = full['role'];
                            return '<a href="javascript:showResetPasswordModal(\''+userId+'\',\''+token+'\');">重置密码</a>&nbsp;<a href="javascript:editTeacherModal(\''+userId+'\',\''+token+'\',\''+userNo+'\',\''+name
                                    +'\',\''+colleageId+'\',\''+colleageName+'\',\''+classId+'\',\''+className+'\',\''+role+'\');">编辑</a>&nbsp;<a href="javascript:showDeleteUserModal(\''+userId+'\',\''+token+'\',\''+role+'\')">删除</a>' +
                                    '&nbsp;<a href="javascript:showTeacherManagedClassModal(\''+userId+'\',\''+token+'\')">管理班级</a>'
                        }
                    }
                ],
                serverSide: true,
                processing:true,
                "info": true,
                ajax: {
                    url: '<%=request.getContextPath()%>/webUser/teacherList.do',
                    type: 'POST',
                    dataType: "json",
                    data: function ( d ) {
                        return $.extend( {}, d,postData );
                    }
                    //                   "dataSrc":function(json){
                    //                       alert("dddd");
                    //                       return null;
                    //                   }
                    //                   success:function(){
                    //                       alert("ddd");
                    //                   },
                    //                   error:function(){
                    //                       alert("error");
                    //                   }
                }
            })
        }







    /**
     * 重置密码
     */
    function resetPassword(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webUser/resetTeacherPassword.do",
            data: $('#resetPasswordForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#resetPasswordFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    $("#resetPasswordModal").modal('hide');
                    clearResetPasswordForm();
                    alert("重置密码成功");
                }else{
                    alert("系统异常，请联系管理员");
                }
                //使form表单不可用
                $('#resetPasswordFormFieldset').attr("disabled",false);
            },
            error: function(data) {
                clearResetPasswordForm();
                $("#checkModal").modal('hide');
                alert("系统异常，请联系管理员");
            }

        });
    }

    function flushPage(dt,flushPageFlag){

        var info = dt.page.info();
        var recordsTotal = Number(info['recordsTotal']);
        var startIndex = Number(info['start']);
        var endIndex = Number(info['end']);
        if(endIndex-startIndex==1){
            dt.page('previous').draw(false);
        }else{
            if(flushPageFlag){
                dt.ajax.reload(null,true);
            }else{
                dt.ajax.reload(null,false);
            }
        }
    }

    /**
     *清楚  重置密码 form
     */
    function clearResetPasswordForm(){
        $("#resetPasswordUserId").val('');
        $("#resetPasswordtoken").val('');
    }



    /**
    * 显示修改密码 modal
     */
    function showResetPasswordModal(userId,token){
        $("#resetPasswordUserId").val(userId);
        $("#resetPasswordtoken").val(token);
        $("#resetPasswordModal").modal();
    }


    /**
     * 显示删除 用户 的modal
     */
    function showDeleteUserModal(userId,token,role){
        $("#deleteUserId").val(userId);
        $("#deleteUserToken").val(token);
        $("#deleteUserRole").val(role);
        $("#deleteUserModal").modal();
    }

    /**
     * 修改教师信息  modal
     */
    function editTeacherModal(userId,token,userNo,name,colleageId,colleageName,classId,className,role){
        $("#editTeacherTeacherId").val(userId);
        $("#editTeacherToken").val(token);
        $("#editTeacherTeacherNum").val(userNo);
        $("#editTeacherTeacherName").val(name);

        //重置学院信息
        $("#editTeacherColleageId").html("");
        //获取学院信息
        getColleage('editTeacherColleageId',colleageId);
        $("#editTeacherModal").modal();
    }

    /**
     * 新增学生form表单提交
     *
     */
    function submitaddTeacherForm(){
        $("#addTeacherFormFieldset").attr("disabled",false);
        var studentNumVal = $("#addTeacherTeacherNum").val();
        var studentNameVal = $("#addTeacherTeacherName").val();
        var colleageIdVal = $("#addStudentColleageId").val();
        var editStudentClassIdVal = $("#addTeacherRoleId").val();
        if(studentNumVal==''){
            alert("学号不允许为空");
            return;
        }
        if(studentNameVal==''){
            alert("姓名不允许为空");
            return;
        }
        if(colleageIdVal==''  || colleageIdVal==null){
            alert("学院不允许为空");
            return;
        }
        if(editStudentClassIdVal=='' || editStudentClassIdVal==null){
            alert("班级不允许为空");
            return;
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webUser/addTeacher.do",
            data: $('#addTeacherForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#addTeacherFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    $("#addTeacherModal").modal('hide');
                    alert("老师信息添加成功");
                }else{
                    alert(data['msg']);
                }
                //使form表单不可用
                $('#addTeacherFormFieldset').attr("disabled",false);
                $("#addTeacherModal").modal('hide');
                flushPage(dataTable);
            },
            error: function(data) {
                $('#addTeacherFormFieldset').attr("disabled",false);
                $("#addTeacherModal").modal('hide');
                alert("系统异常，请联系管理员");
            }

        });

    }

    /**
     * 显示新增教师的 modal
     */
    function addTeacherModal(){
        $("#addTeacherResetBt").trigger("click");//点击清空按钮
        //重置学院信息
        $("#addStudentColleageId").html("");
        //获取学院信息
        getColleage('addStudentColleageId','${user.colleageId}','');
        $("#addTeacherModal").modal();
    }





    /**
    *提交修改老师信息的表单
     */
    function submitEditStudentForm() {
        $("#editTeacherFormFieldset").attr("disabled",false);
        var teacherNumVal = $("#editTeacherTeacherNum").val();
        var teacherNameVal = $("#editTeacherTeacherName").val();
        var colleageIdVal = $("#editTeacherColleageId").val();
        if(teacherNumVal==''){
            alert("学号不允许为空");
            return;
        }
        if(teacherNameVal==''){
            alert("姓名不允许为空");
            return;
        }
        if(colleageIdVal==''  || colleageIdVal==null){
            alert("学院不允许为空");
            return;
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webUser/editTeacher.do",
            data: $('#editTeacherForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#editTeacherFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    alert("用户信息修改成功");
                }else{
                    alert(data['msg']);
                }
                //使form表单变成可用
                $('#editTeacherFormFieldset').attr("disabled",false);
                $("#editTeacherModal").modal('hide');
                flushPage(dataTable);
            },
            error: function(data) {
                $('#editTeacherFormFieldset').attr("disabled",false);
                $("#editTeacherModal").modal('hide');
                alert("系统异常，请联系管理员");
            }

        });
    }

    /**
    *提交删除老师的表单
     */
    function confirmDeleteUser() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webUser/deleteTeacher.do",
            data: $('#deleteUserForm').serialize(),
            beforeSend:function(){
                //删除用户按钮不可用
                $('#deleteUserFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    alert("老师信息删除成功");
                    flushPage(dataTable);
                }else{
                    alert("系统异常，请联系管理员");
                }
                //使form表单不可用
                $('#deleteUserFormFieldset').attr("disabled",false);
                $("#deleteUserModal").modal('hide');
            },
            error: function(data) {
                $("#deleteUserModal").modal('hide');
                alert("系统异常，请联系管理员");
            }

        });
    }

    /**
    *获得学院列表
    * @param colleageIdSelect
    * @param colleageId
    * @param classId
     */
    function getColleage(colleageIdSelect,colleageId,classId,neededEmptyOption){
        var role = '${user.role}';
        var selfColleageId = '${user.colleageId}';//当前用户的colleageId
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webUser/getColleages.do",
            success: function (data) {
                if(data['code']=='200'){
                    var colleages = data['data'];
                    var optionsStr = '';
                    if(neededEmptyOption){
                        optionsStr+='<option value="">请选择</option>'
                    }
                    for(var i =0;i<colleages.length;i++){
                        var colleage = colleages[i];
                        if(colleageId==colleage['colleageId']){
                            optionsStr+='<option selected value="'+colleage['colleageId']+'">'+colleage['colleageName']+'</option>';
                        }else{
                            if(role=='3'){//当前用户是超级管理员的情况，才允许设置学院
                                optionsStr+='<option value="'+colleage['colleageId']+'">'+colleage['colleageName']+'</option>';
                            }
                        }
                    }
                    $("#"+colleageIdSelect).html(optionsStr);
                }else{
                    alert("系统异常，请联系管理员");
                }
            },
            error: function(data) {
                $("#checkModal").modal('hide');
                alert("系统异常，请联系管理员");
            }

        })
    }

    /**
    *显示老师 负责的  班级列表
     */
    function showTeacherManagedClassModal(userId,token){
        initTeacherManagedClassForm(userId,token);
        initTeacherManagedClassTable(userId,token);
        $("#teacherManagedClassModal").modal();
    }

    function initTeacherManagedClassForm(userId,token){
        $("#teacherManagedUserId").val(userId);
        $("#teacherManagedToken").val(token);

        if('${user.role}'=='3'){//超级管理员，才具有筛选学院的功能
            getColleage('teacherManagedColleageId','','',true)
        }
    }

    var teacherManagedTable;
    /**
    *初始化 班级列表
     */
    var teacherManagedPostData;
    function initTeacherManagedClassTable(){
        var userId =  $("#teacherManagedUserId").val();//当前老师的userId

        teacherManagedPostData = $("#teacherManagedClassForm").serializeJson();
        if(teacherManagedTable==null){
            teacherManagedTable=$("#teacherManagedClassTable").DataTable( {
                'dom': 'rt<"row"<"col-sm-3"i><"col-sm-6 float_left"p><"col-sm-3"l>>', // 元素布局
                'language': {
                    'emptyTable': '没有数据',
                    'loadingRecords': '加载中...',
                    'processing': '查询中...',
                    'search': '检索:',
                    'lengthMenu': '每页 _MENU_ 条',
                    'zeroRecords': '没有数据',
                    'paginate': {
                        'first':      '第一页',
                        'last':       '最后一页',
                        'previous':   '上一页',
                        'next':       '下一页'
                    },
                    'info': '共计 _TOTAL_  条',
                    'infoEmpty': '没有数据',
                    'infoFiltered': '(过滤总件数 _MAX_ 条)'
                },
                renderer: "bootstrap",
                "searching": false,//不显示搜索框
                "initComplete":function( settings, json ){
                    json=null;
                },
                "drawCallback": function( settings ) {
                    return null;
                },
                "columns": [
                    {data:'dd',"orderable": false,render:function(data, type, full, meta ){
                        var managed=full["managed"];
                        var checkStr = "";
                        if(managed=='0' || managed=='1'){//其他老师在负责
                            checkStr='<input type="checkbox" disabled style="disabled:true"></input>';
                        }
                        if(managed=='2'){//无人负责
                            checkStr='<input type="checkbox"></input>';
                        }
                            return checkStr;
                       }
                    },
                    { "data": "colleageName","orderable": false},
                    { "data": "className","orderable": false},
                    { "data": "userName","orderable": false},
                    { "data": "cz","width": "20%" ,"orderable": false,"render": function ( data, type, full, meta ) {
                        var btnDelId = full["classId"]+"-del-btn";
                        var btnAddId = full["classId"]+"-add-btn";
                        var managed = full['managed'];
                        var czStr = '';
                        //
                        if(managed=='0'){//其他老师在负责
                            czStr+='<button type="button" disabled style="disabled:true;" class="btn btn-default">管理中</button>';
                        }
                        if(managed=='1'){//当前自己在负责
                            czStr+='<button type="button" id="'+btnDelId+'" class="btn btn-danger" onclick="removeManagedClass(\''+userId+'\',\''+full["classId"]+'\',\''+btnDelId+'\')">删除</button>';
                        }
                        if(managed=='2'){//无人负责
                            czStr+='<button type="button" id="'+btnAddId+'" class="btn btn-primary" onclick="addManagedClass(\''+userId+'\',\''+full["classId"]+'\',\''+btnAddId+'\')">添加</button>';
                        }
                        return czStr;
                        }
                    }
                ],
                "order": [],
                serverSide: true,
                processing:true,
                "info": true,
                ajax: {
                    url: '<%=request.getContextPath()%>/webUser/getTeacherClassList.do',
                    type: 'POST',
                    dataType: "json",
                    data: function ( d ) {
                        return $.extend( {}, d,teacherManagedPostData );
                    }
                    //                   "dataSrc":function(json){
                    //                       alert("dddd");
                    //                       return null;
                    //                   }
                    //                   success:function(){
                    //                       alert("ddd");
                    //                   },
                    //                   error:function(){
                    //                       alert("error");
                    //                   }
                }
            });
        }else{
            flushPage(teacherManagedTable);
        }

    }

   //删除该老师负责的班级
function removeManagedClass(userId,classId,btnId){
    var dataObj = new Object();
    dataObj["userId"]=userId;
    dataObj["classId"]=classId;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "<%=request.getContextPath()%>/webUser/removeManagedClass.do",
        data:dataObj,
        beforeSend:function(){
            //使 除除按钮不可用
            $("#"+btnId).attr("disabled",true);
        },
        success: function (data) {
            if(data['code']=='200'){
                flushPage(teacherManagedTable,true);
            }else{
                alert("系统异常，请联系管理员");
            }
        },
        error: function(data) {
            alert("系统异常，请联系管理员");
            flushPage(teacherManagedTable,true);
        }

    });
}


    //添加该老师负责的班级
    function addManagedClass(userId,classId,btnId){
        var dataObj = new Object();
        dataObj["userId"]=userId;
        dataObj["classId"]=classId;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webUser/addManagedClass.do",
            data:dataObj,
            beforeSend:function(){
                //使 除除按钮不可用
                $("#"+btnId).attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    flushPage(teacherManagedTable,true);
                }else{
                    alert("系统异常，请联系管理员");
                }
            },
            error: function(data) {
                alert("系统异常，请联系管理员");
                flushPage(teacherManagedTable,true);
            }

        });
    }



</script>
</body>

</html>
