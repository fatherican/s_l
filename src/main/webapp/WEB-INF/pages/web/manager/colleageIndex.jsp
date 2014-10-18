<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>请假管理-学院管理</title>

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
                       学院列表
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="row">
                            <div class="col-lg-12 text-right">
                                <button type="button" id="addColleageBt" class="btn btn-primary">新增</button>
                            </div>
                            <!-- /.col-lg-12 -->
                        </div>


                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="leavelist-table">
                                <thead>
                                <tr>
                                    <th>学院</th>
                                    <th>系号</th>
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
<div class="modal fade" id="addColleageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addModalLabel">添加学院</h4>
            </div>
            <form class="form-horizontal" id="addColleageForm" name="addColleageForm" role="form" >
                <fieldset id="addColleageFieldset">
                    <div class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="addColleageColleageName" class="col-sm-2 control-label">学院</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="colleageName" id="addColleageColleageName" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addColleagePrefix" class="col-sm-2 control-label">系号</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="prefix" id="addColleagePrefix" >
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="addColleageFormBt" class="btn btn btn-success">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- 编辑 colleage 信息 Modal -->
<div class="modal fade" id="editColleageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">学院信息修改</h4>
            </div>
            <form class="form-horizontal" id="editColleageForm" name="editColleageForm" role="form" >
                <fieldset id="editColleageFormFieldset">
                    <input type="hidden" name="colleageId" id="editColleageColleageId">
                    <div class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="editColleageColleageName" class="col-sm-2 control-label">学院名称</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="colleageName" id="editColleageColleageName" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="editColleagePrefix" class="col-sm-2 control-label">系号</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="prefix"  id="editColleagePrefix">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="editColleageBt" class="btn btn btn-success">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>



<!--  delete colleage Modal -->
<div class="modal fade" id="deleteColleageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" >确定删除该系</h4>
            </div>
            <fieldset id="deleteColleageFieldset">
                <form class="form-horizontal" id="deleteColleageForm" name="deleteColleageForm" role="form" >
                        <input type="hidden" name="colleageId" id="deleteColleageColleageId">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" id="deleteColleageBt" class="btn btn-primary">确定</button>
                        </div>
                    </fieldset>
                </form>
            </fieldset>
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
        //编辑学院确定按钮
        $("#editColleageBt").click(function(){
            editColleageForm();
        });


        //删除学院 确定按钮
        $("#deleteColleageBt").click(function(){
            deleteColleageForm();
        });
        //添加学院 按钮
        $("#addColleageBt").click(function(){
            addColleageModal();
        });
        //添加学院提交 按钮
        $("#addColleageFormBt").click(function(){
            addColleageForm();
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
                    { "data": "colleageName","orderable": false},
                    { "data": "prefix"},
                    { "data": "cz","width": "20%" ,"orderable": false,"render": function ( data, type, full, meta ) {
                            var colleageId = full['colleageId'];
                            var colleageName = full['colleageName'];
                            var prefix = full["prefix"];
                            return '<a href="javascript:editColleage(\''+colleageId+'\',\''+colleageName+'\',\''+prefix+'\');">编辑</a>&nbsp;<a href="javascript:deleteColleage(\''+colleageId+'\')">删除</a>';
                        }
                    }
                ],
                "order": [ 1, 'asc' ],
                serverSide: true,
                processing:true,
                "info": true,
                ajax: {
                    url: '<%=request.getContextPath()%>/webManager/colleageManageList.do',
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
     * 编辑学院
     */
    function editColleage(colleageId,colleageName,prefix){
        $("#editColleageColleageId").val(colleageId);
        $("#editColleageColleageName").val(colleageName);
        $("#editColleagePrefix").val(prefix);
        $("#editColleageModal").modal();
    }


    /**
     *提交修改学院表单
     */
    function editColleageForm() {
        if(!isInteger($.trim($("#editColleagePrefix").val()))){
            alert("系号必须是整数");
            return false;
        }

        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webManager/editColleage.do",
            data: $('#editColleageForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#editColleageFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    $("#editColleageModal").modal('hide');
                    flushPage();
                    alert("院系修改成功");
                }else{
                    alert(data['msg']);
                }
                //使form表单不可用
                $('#editColleageFormFieldset').attr("disabled",false);
            },
            error: function(data) {
                $('#editColleageFormFieldset').attr("disabled",false);
                $("#editColleageModal").modal('hide');
                alert("系统异常，请联系管理员");
            }
        });
    }

    /**
     * 删除学院
     */
    function deleteColleage(colleageId){
        $("#deleteColleageColleageId").val(colleageId);
        $("#deleteColleageModal").modal();

    }

    function deleteColleageForm(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webManager/deleteColleage.do",
            data: $('#deleteColleageForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#deleteColleageFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    flushPage();
                    alert("院系删除成功");
                }else{
                    alert(data['msg']);
                }
                $("#deleteColleageModal").modal('hide');
                //使form表单可用
                $('#deleteColleageFieldset').attr("disabled",false);
            },
            error: function(data) {
                $('#deleteColleageFieldset').attr("disabled",false);
                $("#deleteColleageModal").modal('hide');
                alert("系统异常，请联系管理员");
            }
        });
    }

    /**
     * 添加学院modal
     * */
    function addColleageModal(){
        $("#addColleageColleageName").val("");
        $("#addColleagePrefix").val("");
        $("#addColleageModal").modal();
    }

    function addColleageForm(){
        if(!isInteger($.trim($("#addColleagePrefix").val()))){
            alert("系号必须是整数");
            return false;
        }
        var colleageName= $.trim($("#addColleageColleageName").val());
        if(colleageName==''){
            alert("院系名称不允许为空");
            return false;
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webManager/addColleage.do",
            data: $('#addColleageForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#addColleageFieldset').attr("disabled",true);
            },
            success: function (data) {
                if(data['code']=='200'){
                    flushPage();
                    alert("院系添加成功");
                }else{
                    alert(data['msg']);
                }
                $("#addColleageModal").modal('hide');
                //使form表单可用
                $('#addColleageFieldset').attr("disabled",false);
            },
            error: function(data) {
                $('#addColleageFieldset').attr("disabled",false);
                $("#addColleageModal").modal('hide');
                alert("系统异常，请联系管理员");
            }
        });
    }

//==================工具方法===================================
    function isInteger(value){
        if(!(/^(\+|-)?\d+$/.test( value ))){
            return false;
        }
        return true;
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
</script>
</body>

</html>
