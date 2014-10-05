<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>请假管理-信息管理</title>

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
        <div class="row text-center">

            <%--班级导入--%>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-green">
                    <form name="classForm" id="classForm" action="<%=request.getContextPath()%>/webManager/importClass.do" method="post" enctype="multipart/form-data">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-tasks fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">班级</div>
                                <div>导入</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-3">
                                <input type="file" name="file" id="classFile"/>
                            </div>

                        </div>
                        <div class="row text-center">
                           <input type="button" id="classBt" class="btn btn-default" value="提交"/>
                        </div>
                    </div>
                    </form>
                    <a href="#">
                        <div class="panel-footer" onclick="window.open('<%=request.getContextPath()%>/resources/excelTemplate/classTemplate.xls');">
                            <span class="pull-left">模    板</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>

                <%--学生导入--%>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-green">
                    <form name="studentForm" id="studentForm" action="<%=request.getContextPath()%>/webManager/importStudent.do" method="post" enctype="multipart/form-data">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">学生</div>
                                    <div>导入</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3">
                                    <input type="file" name="file" id="studentFile"/>
                                </div>

                            </div>
                            <div class="row text-center">
                                <input type="button" id="studentBt" class="btn btn-default" value="提交"/>
                            </div>
                        </div>
                    </form>
                    <a href="#">
                        <div class="panel-footer" onclick="window.open('<%=request.getContextPath()%>/resources/excelTemplate/studentTemplate.xls');">
                            <span class="pull-left">模  板</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>

                <%--教师导入--%>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-green">
                    <form name="teacherForm" id="teacherForm" action="<%=request.getContextPath()%>/webManager/importTeacher.do" method="post" enctype="multipart/form-data">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">教师</div>
                                    <div>导入</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3">
                                    <input type="file" name="file" id="teacherFile"/>
                                </div>
                            </div>
                            <div class="row text-center">
                                <input type="button" id="teacherBt" class="btn btn-default" value="提交"/>
                            </div>
                        </div>
                    </form>

                    <a href="#">
                        <div class="panel-footer" onclick="window.open('<%=request.getContextPath()%>/resources/excelTemplate/teacherTemplate.xls');">
                            <span class="pull-left">模  板</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
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
                <h4 class="modal-title" >确定删除用户</h4>
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

<!-- 下载错误文件modal-->
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="errorTitle"></h4>
            </div>
            <fieldset >
                <form class="form-horizontal"  >
                    <div class="modal-footer">
                        <button type="button" id="errorBt" class="btn btn-primary">下载错误文件</button>
                    </div>
            </fieldset>
            </form>
            </fieldset>
        </div>
    </div>
</div>

<!-- 导入成功后modal-->
<div class="modal fade" id="rightModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">数据导入成功</h4>
            </div>
            <fieldset >
                <form class="form-horizontal"  >
                    <div class="modal-footer">
                        <button type="button" id="rightBt" class="btn btn-primary">确定</button>
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

    var errorCode = '${errorCode}';
    var errorFileName = '${fileName}';
    var errorMsg = '${errorMsg}';
    var refresh='${refresh}';


    $(document).ready(function () {
            bindEvent();
        if(errorCode!=null && errorCode!=''){
            $("#errorTitle").html(errorMsg);
            $("#errorModal").modal({
                keyboard:false,
                backdrop: 'static'
            });
        }else{//在导入没有出任何错误的情况下，弹出导入数据成功modal
            if(refresh){
                $("#rightModal").modal({
                    keyboard:false,
                    backdrop: 'static'
                });
            }
        }

    });


    function bindEvent(){


        //点击错误 modal上的确定按钮
        $("#errorBt").click(function(){
            window.open("<%=request.getContextPath()%>/"+errorFileName);
            $("#errorModal").modal('hide');
        });
        //数据导入成功后的确定按钮
        $("#rightBt").click(function(){
            if(refresh){
                window.location.href="<%=request.getContextPath()%>/webManager/informationImportIndex.do";
            }
        });

        $("#classBt").click(function(){
            if($("#classFile").val()==''){
                alert("请选择文件");
                return false;
            }
            $("#classForm").submit();
        });

        $("#studentBt").click(function(){
            if($("#studentFile").val()==''){
                alert("请选择文件");
                return false;
            }
            $("#studentForm").submit();
        });

        $("#teacherBt").click(function(){
            if($("#teacherFile").val()==''){
                alert("请选择文件");
                return false;
            }
            $("#teacherForm").submit();
        });


    }


//==================工具方法===================================
</script>
</body>

</html>
