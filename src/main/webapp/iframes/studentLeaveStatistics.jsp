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
            window.parent.changeAddress("leave/studentLeaveStatistics")
            $("#submitBT").bind("click",function(){
                $("#prevId").html("");
                var host = $(window.parent.document.getElementById("host")).html();
                $("#submitBT").attr("disabled",true);
                $.ajax({
                    type: "POST",
                    dataType: "html",
                    url:host+"/leave/studentLeaveStatistics.do",
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
        学生 销假
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
                    <td style="width:10%" >用户ID</td>
                    <td style="width:10%">userId</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%"> </td>
                </tr>
                <tr>
                    <td style="width:10%">统计类型</td>
                    <td style="width:10%">statisticsType</td>
                    <td style="width:10%"> </td>
                    <td style="width:10%">空  统计全部  1  第一节课 2第二节课  4第三节课 8 第四节课 0天（请假类型为天的）</td>
                </tr>
                <tr>
                    <td style="width:10%">秘钥</td>
                    <td style="width:10%">token</td>
                    <td style="width:10%"> </td>
                    <td style="width:10%">秘钥的生成方式见 API URL token=</td>
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
					<input class="span2" type="text" name="userId" value='1'/>
                <span class="add-on">token</span>
                    <input class="span2" type="text" name="token" value='eeafb716f93fa090d7716749a6eefa72' />
                <span class="add-on">statisticsType</span>
                    <select  id="statisticsType" name="statisticsType">
                        <option value="">统计全部(值是 空)</option>
                        <option value="1">1  第一节课 </option>
                        <option value="2">2  第二节课</option>
                        <option value="4">4  第三节课</option>
                        <option value="8">8  第四节课</option>
                        <option value="0">0  请假的天数</option>
                    </select>
                <span class="add-on">startTime</span>
                    <input class="span2" type="text" name="startTime" value='2014-09-03  00:00:00' />
                <span class="add-on">endTime</span>
                    <input class="span2" type="text" name="endTime" value='2014-09-03  00:00:00' />
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