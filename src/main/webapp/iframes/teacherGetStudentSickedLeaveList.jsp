<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/css.css" />

  <title></title>
  <style>
	<![CDATA[
		.title { font-size:14px; font-weight:bold; background:#EDF3F9; padding:0px 15px;}
        td .redBGColor{color:red}
        td .greenBGColor{background-color:green}
        .yellowBGColor{background-color:yellow}
	 ]]>
  </style>
  <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js" type="text/javascript"></script>

  <script type="text/javascript">
		$(document).ready(function(){
            window.parent.changeToken("userId + key");
            window.parent.changeAddress("leave/teacherGetStudentSickedLeaveList")
            $("#submitBT").bind("click",function(){
                $("#prevId").html("");
                var host = $(window.parent.document.getElementById("host")).html();
                $("#submitBT").attr("disabled",true);
                $.ajax({
                    type: "POST",
                    dataType: "html",
                    url:host+"/leave/teacherGetStudentSickedLeaveList.do",
                    data: $('#subForm').serialize(),
                    success: function (result) {
                        var strresult=result;
                        $("#prevId").html(strresult);
                        $("#submitBT").attr("disabled",false);
                    },
                    error: function(data) {
                        alert("error:"+data.responseText);
                        $("#submitBT").attr("disabled",false);
                    }

                });
            })


		});
  </script>
</head>
<body>
<div style="margin:0px 18px;">
    <div class="alert alert-info">
        <strong>说明</strong>
    </div>
    <div class="alert alert-info">
        老师获得学生的销假列表
    </div>
	<div class="alert alert-info">
		<strong>请求参数</strong>
	</div>
	<div>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
                    <th style="width:10%;">参数名称</th>
                    <th style="width:10%">参数命名</th>
                    <th style="width:10%">示例值</th>
                    <th>备注</th>
                </tr>
                <tr>
                    <td style="width:10%">用户ID</td>
                    <td style="width:10%">userId</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%">     </td>
                </tr>
                <tr>
                    <td style="width:10%">秘钥</td>
                    <td style="width:10%">token</td>
                    <td style="width:10%"> </td>
                    <td style="width:10%">秘钥的生成方式见 API URL token=</td>
                </tr>

                <tr>
                    <td style="width:10%">页码</td>
                    <td style="width:10%">pageNum</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%" style="background:red;">页码从1开始，不是0</td>
                </tr>
                <tr>
                    <td style="width:10%">每页显示的条数</td>
                    <td style="width:10%">pageSize</td>
                    <td style="width:10%">20</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">请求类型</td>
                    <td style="width:10%">viewType</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%"> 1 按照时间查看 2按照班级查看</td>
                </tr>
                <tr>
                    <td style="width:10%">销假状态 </td>
                    <td style="width:10%">leaveSicked</td>
                    <td style="width:10%">0</td>
                    <td style="width:10%">  0 未销假  1 已销假</td>
                </tr>
                <tr>
                    <td colspan="4" style="background:red;">上面的字段对 所有的请求列表数据类型  都适用 </td>
                </tr>
                <tr>
                    <td colspan="4"  style="background:green;text-align: center;">如果按照时间查看,对于已经销假的列表，下面俩个字段没有用，因为只查询3天的</td>
                </tr>
                <tr>
                    <td style="width:10%">查询开始时间</td>
                    <td style="width:10%">startTime</td>
                    <td style="width:10%">2014-03-03 00:00:00</td>
                    <td style="width:10%">格式yyyy-MM-dd hh:mm:ss</td>
                </tr>
                <tr>
                    <td style="width:10%">结束时间</td>
                    <td style="width:10%">endTime</td>
                    <td style="width:10%">2014-09-03  00:00:00</td>
                    <td style="width:10%">格式yyyy-MM-dd hh:mm:ss</td>
                </tr>
                <tr>
                    <td colspan="4"  style="background:green;text-align: center;">按照班级查看</td>
                </tr>
                <tr>
                    <td style="width:10%">班级ID</td>
                    <td style="width:10%">classId</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%"></td>
                </tr>

			</thead>
		</table>
	</div>
  </div>

  <div style="margin:0px 18px;">
	<div class="alert alert-info"><strong>模拟调用</strong></div>


	<div>
		<form class="form-inline" name="subForm" method="post" id="subForm">
			<div class="input-prepend">
			<p>
				<span class="add-on">userId</span>
					<input class="span2" type="text" name="userId" value='201407261644000001'/>
                <span class="add-on">token</span>
                    <input class="span2" type="text" name="token" value='0b7403611d4ad964cc4c2b6cffbc87f1' />
                <span class="add-on">pageNum</span>
                    <input class="span2" type="text" name="pageNum" value='1' />
                <span class="add-on">pageSize</span>
                 <input class="span2" type="text" name="pageSize" value='10' />
            </p>
            <p>
                <span class="add-on">viewType</span>
                <select onchange="changeLeaveType();" id="viewType" name="viewType">
                    <option value="1">按照时间查看[1]</option>
                    <option value="2">按照班级查看[2]</option>
                </select>
                <script type="text/javascript">
                    $('#viewType').change(function() {
                        var checkIndex=$(this).get(0).selectedIndex;
                        if(checkIndex==0){//显示时间段参数
                            $("#timeP").show();
                            $("#classP").hide();
                        }else{//隐藏时间段参数
                            $("#classP").show();
                            $("#timeP").hide();
                        }
                    });
                </script>
            </p>

            <p>
                <span class="add-on">leaveSicked</span>
                <select  id="leaveSicked" name="leaveSicked">
                    <option value="0">未销假[0]</option>
                    <option value="1">已销假[1]</option>
                </select>
            </p>
            <p id="timeP" >
                <span class="add-on">startTime</span>
                    <input class="span2" type="text" name="startTime" value='2014-03-03 00:00:00'/>
                <span class="add-on">endTime</span>
                    <input class="span2" type="text" name="endTime" value='2014-09-03  00:00:00' />
            </p>
            <p id="classP" style="display: none;">
                <span class="add-on">classId</span>
                <input class="span2" type="text" name="classId" value='1'/>
            </p>

                <p><input type="button" value="提交"  id="submitBT" class="btn" /></p>
			</div>
		</form>
	</div>
  </div>
  <div style="margin:0px 18px;">
	<div class="alert alert-info"><strong>返回结果</strong></div>
	<pre id="prevId">

	</pre>



  </div>


</body>
</html>