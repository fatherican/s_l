<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>请假管理-班级管理</title>

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
                        班级列表
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="row">
                            <div class="col-lg-12 text-right">
                                <button type="button" id="addClassBt" class="btn btn-primary">新增</button>
                            </div>
                            <!-- /.col-lg-12 -->
                        </div>


                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="leavelist-table">
                                <thead>
                                <tr>
                                    <th>学院</th>
                                    <th>班级</th>
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


<!-- 班级信息修改 Modal -->
<div class="modal fade" id="updateClassModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">班级信息修改</h4>
            </div>
            <form class="form-horizontal" id="updateClassForm" name="updateClassForm" role="form" >
                <fieldset id="updateClassFormFieldset">
                    <input type="hidden" name="classId" id="updateClassClassId">
                    <input type="hidden" name="prefix" id="updateClassPrefix">
                    <div class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="updateClassClassName" class="col-sm-2 control-label">班级</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="className" id="updateClassClassName" >
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="updateClassBt" class="btn btn btn-success">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>




<!-- 班级信息修改 Modal -->
<div class="modal fade" id="addClassModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" >添加班级</h4>
            </div>
            <form class="form-horizontal" id="addClassForm" name="updateClassForm" role="form" >
                <fieldset id="addClassFormFieldset">
                    <div class="modal-body">
                        <input type="hidden" name="prefix" id="addClassPrefix">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                 <label  class="col-sm-2 control-label" for="addClassColleageId">学院</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="addClassColleageId" name="colleageId">

                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addClassClassName" class="col-sm-2 control-label">班级</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="className" id="addClassClassName" >
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="addClassFormBt" class="btn btn btn-success">确定</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>


<!--  delete class Modal -->
<div class="modal fade" id="deleteClassModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" >确定删除班级</h4>
            </div>
            <fieldset id="deleteClassFieldset">
                <form class="form-horizontal" id="deleteClassForm" name="deleteClassForm" role="form" >
                        <input type="hidden" name="classId" id="deleteClassClassId">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" id="deletClassBT" class="btn btn-primary">确定</button>
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
            initColleage();
//        $('#leavelist-table').dataTable();
//        initColumn();


    });
    function bindEvent(){
        //更新班级确定按钮
        $("#updateClassBt").click(function(){
            updateClassForm();
        });
        //确定删除按钮
        $("#deletClassBT").click(function(){
            deleteClassForm();
        });

        //新增按钮
        $("#addClassBt").click(function(){
            addClassModal();
        });
        //新增确定按钮
        $("#addClassFormBt").click(function(){
            addClassForm();
        });

        //学院选择
        $("#addClassColleageId").change(function(){
            colleageChange();
        });

    }
    function initColleage(){
        var role = '${user.role}';
        var prefix = '${user.prefix}';
        if(role=='2'){
            $("#addClassPrefix").val(prefix);
        }
        getColleage();

    }

    //学院change事件
    function colleageChange(){
        $("#addClassPrefix").val($("#addClassColleageId  option:selected").attr("prefix"));
    }


    function getColleage(){
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
                    for(var i =0;i<colleages.length;i++){
                        var colleage = colleages[i];
                        var colleageId = colleage["colleageId"];
                        var prefix = colleage["prefix"];
                        if(role=='3'){
                            optionsStr+='<option prefix="'+prefix+'"  value="'+colleage['colleageId']+'">'+colleage['colleageName']+'</option>';
                        }else if(role=='2'){
                            if(selfColleageId==colleageId){
                                optionsStr+='<option prefix="'+prefix+'"  selected value="'+colleage['colleageId']+'">'+colleage['colleageName']+'</option>';
                            }
                        }
                    }
                    $("#addClassColleageId").html(optionsStr);
                    $("#addClassPrefix").val($("#addClassColleageId  option:selected").attr("prefix"));
                }else{
                    alert("系统异常，请联系管理员");
                }
            },
            error: function(data) {
                alert("系统异常，请联系管理员");
            }
        })
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
                    { "data": "colleageName"},
                    { "data": "className","orderable": false},
                    { "data": "cz","width": "20%" ,"orderable": false,"render": function ( data, type, full, meta ) {
                            var classId = full['classId'];
                            var className = full['className'];
                            var prefix = full['prefix'];
                            return '<a href="javascript:updateClass(\''+classId+'\',\''+className+'\',\''+prefix+'\');">编辑</a>&nbsp;<a href="javascript:deleteClass(\''+classId+'\')">删除</a>';
                        }
                    }
                ],
                serverSide: true,
                processing:true,
                "info": true,
                ajax: {
                    url: '<%=request.getContextPath()%>/webManager/classManageList.do',
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
     *显示  班级  modal
     */
    function updateClass(classId,className,prefix){
        $("#updateClassClassName").val(className);
        $("#updateClassClassId").val(classId);
        $("#updateClassPrefix").val(prefix);
        $("#updateClassModal").modal();
    }

    /**
     * 更新班级 名称
     */
    function updateClassForm(){
        if( $("#updateClassClassId").val()=='' ||  $("#updateClassClassName").val()==''){
            alert("班级名称不允许为空");
            return false;
        }
        var className =  $("#updateClassClassName").val();
        var prefix =  $("#updateClassPrefix").val();
        if(!className.startWith(prefix)){
            alert("班级必须以"+prefix+"开头");
           return false;
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webManager/editClass.do",
            data: $('#updateClassForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#updateClassFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                $("#updateClassModal").modal('hide');
                if(data['code']=='200'){
                    alert("班级修改成功");
                }else{
                    alert(data['msg']);
                }
                //使form表单不可用
                $('#updateClassFormFieldset').attr("disabled",false);
                flushPage();
            },
            error: function(data) {
                $("#updateClassModal").modal('hide');
                alert("系统异常，请联系管理员");
            }

        });
    }

    function deleteClass(classId){
        $("#deleteClassClassId").val(classId);
        $("#deleteClassModal").modal();
    }

    function deleteClassForm(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webManager/deleteClass.do",
            data: $('#deleteClassForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#deleteClassFieldset').attr("disabled",true);
            },
            success: function (data) {
                $("#deleteClassModal").modal('hide');
                if(data['code']=='200'){
                    alert("班级删除成功");
                }else{
                    alert(data['msg']);
                }
                //使form表单不可用
                $('#deleteClassFieldset').attr("disabled",false);
                flushPage();
            },
            error: function(data) {
                $("#deleteClassModal").modal('hide');
                alert("系统异常，请联系管理员");
            }
        });
    }
    //添加班级   modal
    function  addClassModal(){
        $("#addClassModal").modal();
    }

    function addClassForm(){
        if($.trim($("#addClassClassName").val())==''){
            alert("班级名称不允许为空");
            return false;
        }
        var className =  $("#addClassClassName").val();
        var prefix =  $("#addClassPrefix").val();
        if(!className.startWith(prefix)){
            alert("班级必须以"+prefix+"开头");
            return false;
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=request.getContextPath()%>/webManager/addClass.do",
            data: $('#addClassForm').serialize(),
            beforeSend:function(){
                //使form表单不可用
                $('#addClassFormFieldset').attr("disabled",true);
            },
            success: function (data) {
                $("#addClassModal").modal('hide');
                if(data['code']=='200'){
                    alert("班级添加成功");
                }else{
                    alert(data['msg']);
                }
                //使form表单不可用
                $('#addClassFormFieldset').attr("disabled",false);
                flushPage();
            },
            error: function(data) {
                $("#addClassModal").modal('hide');
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

    String.prototype.startWith=function(s){
        if(s==null||s==""||this.length==0||s.length>this.length)
            return false;
        if(this.substr(0,s.length)==s)
            return true;
        else
            return false;
        return true;
    }

</script>
</body>

</html>
