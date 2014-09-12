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
                            未审批请假列表
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>学号</th>
                                            <th>姓名</th>
                                            <th>班级</th>
                                            <th>学院</th>
                                            <th>请假类型</th>
                                            <th>假期开始时间</th>
                                            <th>假期结束时间</th>
                                            <th>节次</th>
                                            <th>入库时间</th>
                                            <th>请假说明</th>
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
        $(document).ready(function () {
//            bindEvent();
//            initDataTable();
                $('#dataTables-example').dataTable();
            //initColumn();
        });
        function bindEvent(){
//            //查询
//            $("#querybut").click(function(){
//                checkedArray = new Array();
//                $("#dataTable").refreshData();
//            });

        }

        //初始化表格
        function initDataTable() {
                var postData=new Object();
                $("#dataTables-example").DataTable( {
                    "dom": 'rtipl', // 元素布局
                    renderer: "bootstrap",
                    "searching": false,//不显示搜索框
                    "initComplete":function( settings, json ){
                        json=null;
                    },
                    "drawCallback": function( settings ) {
                        return null;
                    },
                    "columns": [
                        { "data": "name"},
                        { "data": "age","render": function ( data, type, full, meta ) {
                            return full["name"]+"|"+full["age"];
                        }
                        }
                    ],
                    serverSide: true,
                    processing:true,
                    "info": false,
                    ajax: {
                        url: '/datable/getData.do',
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
                });
        }


    </script>
</body>

</html>
