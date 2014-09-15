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
    <link href="/resources/bootstrap-sb-admin/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/bootstrap-sb-admin/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/bootstrap-sb-admin/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="/resources/bootstrap-sb-admin/css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/bootstrap-sb-admin/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- DataTables CSS -->
    <link href="/resources/bootstrap-sb-admin/css/plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- self custom CSS -->
    <link href="/resources/css/mycss.css" rel="stylesheet">



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
            <div class="col-lg-12">
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
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="leavelist-table">
                                <thead>
                                <tr>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>班级</th>
                                    <th>学院</th>
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

<!-- 详情 Modal -->
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">详情</h4>
            </div>
            <form class="form-horizontal" role="form" >
                <fieldset id="formFieldset">
                    <div class="modal-body">
                        <table class="table table-striped table-bordered table-hover">
                            <tbody>
                            <tr>
                                <td class=" text-right"><strong>学号</strong></td>
                                <td  id="userNoTdDetail"></td>
                                <td class=" text-right"><strong>姓名</strong></td>
                                <td  id="studentNameTdDetail"></td>
                                <td class=" text-right"><strong>班级</strong></td>
                                <td  id="classNameTdDetail"></td>
                            </tr>
                            <tr>
                                <td class="  text-right"><strong>学院</strong></td>
                                <td  id="colleageNameTdDetail"></td>
                                <td class="  text-right"><strong>类型</strong></td>
                                <td  id="leaveTypeTdDetail"></td>
                                <td class="  text-right"><strong>天数</strong></td>
                                <td  id="leaveDaysTdDetail"></td>
                            </tr>
                            <tr>
                                <td class="  text-right"><strong>节次</strong></td>
                                <td  id="courseIndexTdDetail"></td>
                                <td class="  text-right"><strong>课程名</strong></td>
                                <td  id="courseNameTdDetail"></td>
                                <td  colspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-right"><strong>开始时间</strong></td>
                                <td  colspan="2" id="leaveStartDateTdDetail"></td>
                                <td class="  text-right"><strong>结束时间</strong></td>
                                <td  colspan="2" id="leaveEndDateTdDetail"></td>
                            </tr>
                            <tr>
                                <td class="text-right"><strong>入库时间</strong></td>
                                <td colspan="3" id="createTimeTdDetail"></td>
                                <td class="  text-right"><strong>手机号</strong></td>
                                <td  id="studentMobileTdDetail"></td>
                            </tr>
                            <tr>
                                <td class="  text-right"><strong>请假说明</strong></td>
                                <td colspan="5" id="studentNoteTdDetail"></td>
                            </tr>
                            <tr>
                                <td class="text-right"><strong>辅导员意见</strong></td>
                                <td colspan="5" >
                                    <div id="instructorNoteContentDetail">

                                    </div>
                                </td>
                            </tr>
                            <tr >
                                <td class="text-right"><strong>学管处意见</strong></td>
                                <td colspan="5">
                                    <div id="studentPipeNoteContentDetail">

                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- 同意或不同意 Modal -->
<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">审批</h4>
            </div>
            <form class="form-horizontal" id="objForm" name="objForm" role="form" >
                <%--假条ID--%>
                <input type="hidden" name="leaveId" id="leaveId">
                <%--审批意见--%>
                <input type="hidden" name="note" id="note">
                <%--审批结果 0 不同意 1同意--%>
                <input type="hidden" name="approved" id="approved">

                <fieldset id="formFieldset">
                    <div class="modal-body">
                        <table class="table table-striped table-bordered table-hover">
                            <tbody>
                            <tr>
                                <td class=" text-right"><strong>学号</strong></td>
                                <td  id="userNoTd"></td>
                                <td class=" text-right"><strong>姓名</strong></td>
                                <td  id="studentNameTd"></td>
                                <td class=" text-right"><strong>班级</strong></td>
                                <td  id="classNameTd"></td>
                            </tr>
                            <tr>
                                <td class="  text-right"><strong>学院</strong></td>
                                <td  id="colleageNameTd"></td>
                                <td class="  text-right"><strong>类型</strong></td>
                                <td  id="leaveTypeTd"></td>
                                <td class="  text-right"><strong>天数</strong></td>
                                <td  id="leaveDaysTd"></td>
                            </tr>
                            <tr>
                                <td class="  text-right"><strong>节次</strong></td>
                                <td  id="courseIndexTd"></td>
                                <td class="  text-right"><strong>课程名</strong></td>
                                <td  id="courseNameTd"></td>
                                <td  colspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-right"><strong>开始时间</strong></td>
                                <td  colspan="2" id="leaveStartDateTd"></td>
                                <td class="  text-right"><strong>结束时间</strong></td>
                                <td  colspan="2" id="leaveEndDateTd"></td>
                            </tr>
                            <tr>
                                <td class="text-right"><strong>入库时间</strong></td>
                                <td colspan="3" id="createTimeTd"></td>
                                <td class="  text-right"><strong>手机号</strong></td>
                                <td  id="studentMobileTd"></td>
                            </tr>
                            <tr>
                                <td class="  text-right"><strong>请假说明</strong></td>
                                <td colspan="5" id="studentNoteTd"></td>
                            </tr>
                            <tr>
                                <td class="text-right"><strong>辅导员意见</strong></td>
                                <td colspan="5" id="instructorNoteTd">
                                    <div id="instructorNoteTextArea" class="">
                                        <textarea id="instructorNote" class="form-control" rows="3"></textarea>
                                    </div>
                                    <div id="instructorNoteContent">

                                    </div>
                                </td>
                            </tr>
                            <tr id="studentPipeNoteTr">
                                <td class="text-right"><strong>学管处意见</strong></td>
                                <td colspan="5" id="studentPipeNoteTd">
                                    <div id="studentPipeNoteTextArea" >
                                        <textarea  id="studentPipeNote" class="form-control" rows="3"></textarea>
                                    </div>
                                    <div id="studentPipeNoteContent">

                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="agreeBt" class="btn btn btn-success">同意</button>
                        <button type="button" id="disAgreeBt" class="btn btn btn-danger">不同意</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- new passwordModal -->
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

<!--  delete user Modal -->
<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" >确定删除用户</h4>
            </div>
            <form class="form-horizontal" id="deleteUserForm" name="deleteUserForm" role="form" >
                    <input type="hidden" name="userId" id="deleteUserId">
                    <input type="hidden" name="token"  id="deleteUserToken">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="deleteUserBT" class="btn btn-primary">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- jQuery Version 1.11.0 -->
<script src="/resources/bootstrap-sb-admin/js/jquery-1.11.0.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/bootstrap-sb-admin/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/resources/bootstrap-sb-admin/js/plugins/metisMenu/metisMenu.min.js"></script>


<!-- Custom Theme JavaScript -->
<script src="/resources/bootstrap-sb-admin/js/sb-admin-2.js"></script>


<script type="text/javascript" src="/resources/datables/js/jquery.dataTables.js"></script>
<script src="/resources/bootstrap-sb-admin/js/plugins/dataTables/dataTables.bootstrap.js"></script>
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
                    { "data": "className"},
                    { "data": "colleageName","orderable": false},
                    { "data": "cz","width": "20%" ,"orderable": false,"render": function ( data, type, full, meta ) {
                            var userId = full['userId'];
                            var token = full['token'];
                            return '<a href="javascript:showResetPasswordModal(\''+userId+'\',\''+token+'\');">重置密码</a>&nbsp;<a href="javascript:showDeleteUserModal(\''+userId+'\',\''+token+'\')">删除</a>'
                        }
                    }
                ],
                serverSide: true,
                processing:true,
                "info": true,
                ajax: {
                    url: '/webUser/studentList.do',
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
    *显示详细信息
     */
    function showDetail(userNo,studentName,className,
                        leaveType,leaveDays,courseIndex,
                        createTime,studentMobile,studentNote,
                        instructorNote,studentPipeNote,instructorApproved,
                        studentPipeApproved,leaveStartDate,leaveEndDate,
                        colleageName,courseName,approved,leaveId,
                        flag){
        $("#userNoTdDetail").html(userNo);
        $("#studentNameTdDetail").html(studentName);
        $("#classNameTdDetail").html(className);
        if (leaveType == '0') {//节次
            $("#leaveTypeTdDetail").html('节次');
            $("#courseIndexTdDetail").html(courseIndex);
            $("#leaveDaysTdDetail").html('-');
        } else if (leaveType == '1') {//天数
            $("#leaveTypeTdDetail").html('天数');
            $("#courseIndexTdDetail").html('-');
            $("#leaveDaysTdDetail").html(leaveDays);
        }
        $("#createTimeTdDetail").html(createTime);
        $("#studentMobileTdDetail").html(studentMobile);
        $("#studentNoteTdDetail").html(studentNote);
        $("#instructorNoteDetail").val("");
        if (instructorNote == 'null'|| instructorNote == '') {
            instructorNote='无'
        }
        if (studentPipeNote == 'null'||studentPipeNote == '') {
            studentPipeNote='无'
        }
        $("#instructorNoteContentDetail").html(instructorNote);
        $("#studentPipeNoteContentDetail").html(studentPipeNote);
        $("#leaveStartDateTdDetail").html(formatMillSecondsToDataYYYYMMDD(Number(leaveStartDate)));
        $("#leaveEndDateTdDetail").html(formatMillSecondsToDataYYYYMMDD(Number(leaveEndDate)));
        $("#colleageNameTdDetail").html(colleageName);
        if(courseName=='null'){
            courseName='';
        }
        $("#courseNameTdDetail").html(courseName);
        $("#detailModal").modal();
    }





    /**
     * 重置密码
     */
    function resetPassword(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/webUser/resetStudentPassword.do",
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

    function flushPage(){

        var info = dataTable.page.info();
        var recordsTotal = Number(info['recordsTotal']);
        var startIndex = Number(info['start']);
        var endIndex = Number(info['end']);
        if(endIndex-startIndex==1){
            dataTable.page('previous').draw(false);
        }else{
            dataTable.ajax.reload(null,false);
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
    *显示 审批 modal
     */
    function showCheckModal(userNo,studentName,className,
                            leaveType,leaveDays,courseIndex,
                            createTime,studentMobile,studentNote,
                            instructorNote,studentPipeNote,instructorApproved,
                            studentPipeApproved,leaveStartDate,leaveEndDate,
                            colleageName,courseName,approved,leaveId,
                            flag) {
        $("#leaveId").val(leaveId);
        var role = '${user.role}';//1 辅导员 2 学管处 3 超级用户
        $("#userNoTd").html(userNo);
        $("#studentNameTd").html(studentName);
        $("#classNameTd").html(className);
        if (leaveType == '0') {//节次
            $("#leaveTypeTd").html('节次');
            $("#courseIndexTd").html(courseIndex);
            $("#leaveDaysTd").html('-');
        } else if (leaveType == '1') {//天数
            $("#leaveTypeTd").html('天数');
            $("#courseIndexTd").html('-');
            $("#leaveDaysTd").html(leaveDays);
        }
        $("#createTimeTd").html(createTime);
        $("#studentMobileTd").html(studentMobile);
        $("#studentNoteTd").html(studentNote);
        $("#instructorNote").val("");
        if (instructorNote == 'null'|| instructorNote == '') {
            instructorNote='无'
        }
        if (studentPipeNote == 'null'||studentPipeNote == '') {
            studentPipeNote='无'
        }
        if(role=='1' || role=='2'){
            if(instructorApproved=='-1'){//辅导员未审批，显示辅导员审批textArea
                $("#instructorNoteTextArea").removeClass("sr-only");
                $("#instructorNoteContent").addClass("sr-only");
                //学管处审批一行不显示
                $("#studentPipeNoteTr").addClass("sr-only");
            }else{//辅导员已经审批，显示辅导员审批结果，同时处理学管处文本显示
                $("#instructorNoteTextArea").addClass("sr-only");
                $("#instructorNoteContent").removeClass("sr-only");
                //学管处审批一行显示
                $("#studentPipeNoteTr").removeClass("sr-only");
                if(studentPipeApproved=='-1'){//学管处未审批,显示学管处textArea
                    $("#studentPipeNoteTextArea").removeClass("sr-only");
                    $("#studentPipeNoteContent").addClass("sr-only");
                }else{//学管处已审批，显示学管处审批内容
                    $("#studentPipeNoteTextArea").addClass("sr-only");
                    $("#studentPipeNoteContent").removeClass("sr-only");
                }
            }
        }else if('3'==role){
            if(studentPipeApproved=='-1') {//学管处未审批,显示学管处textArea
                $("#studentPipeNoteTextArea").removeClass("sr-only");
                $("#studentPipeNoteContent").addClass("sr-only");
            }else{//学管处已审批，显示学管处审批内容
                $("#studentPipeNoteTextArea").addClass("sr-only");
                $("#studentPipeNoteContent").removeClass("sr-only");
            }
        }
        $("#instructorNoteContent").html(instructorNote);
        $("#studentPipeNoteContent").html(studentPipeNote);
        $("#leaveStartDateTd").html(formatMillSecondsToDataYYYYMMDD(Number(leaveStartDate)));
        $("#leaveEndDateTd").html(formatMillSecondsToDataYYYYMMDD(Number(leaveEndDate)));
        $("#colleageNameTd").html(colleageName);
        if(courseName=='null'){
            courseName='';
        }
        $("#courseNameTd").html(courseName);

        //------------------控制modal的同意和不同意按钮的显示
        if(flag){//同意
            $("#agreeBt").removeClass("sr-only");
            $("#disAgreeBt").addClass("sr-only");

        }else{
            $("#agreeBt").addClass("sr-only");
            $("#disAgreeBt").removeClass("sr-only");
        }
        $("#checkModal").modal({
            keyboard:false,
            backdrop: 'static'
        });
    }

    /**
    *将毫秒转换成 yyyy-MM-dd  的时间格式
    * @param millSeconds  毫秒值
    * @returns {string}
     */
    function formatMillSecondsToDataYYYYMMDD(millSeconds){
        var date = new Date(millSeconds);
        return date.getFullYear()
                + "-"// "年"
                + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                        + (date.getMonth() + 1))
                + "-"// "月"
                + (date.getDate() < 10 ? "0" + date.getDate() : date
                        .getDate());
    }


    /**
    * 显示修改密码 modal
     */
    function showResetPasswordModal(userId,token){
        $("#resetPasswordUserId").val(userId);
        $("#resetPasswordtoken").val(token);
        $("#resetPasswordModal").modal();
    }


    function showDeleteUserModal(userId,token){
        $("#deleteUserId").val(userId);
        $("#deleteUserToken").val(token);
        $("#deleteUserModal").modal();
    }

</script>
</body>

</html>
