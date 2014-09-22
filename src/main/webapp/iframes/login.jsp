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
	 ]]>
  </style>
  <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js" type="text/javascript"></script>

  <script type="text/javascript">
		$(document).ready(function(){
            window.parent.changeToken("requestTime+key");
            window.parent.changeAddress("user/loginIn")
            $("#submitBT").bind("click",function(){
                $("#prevId").html("");
                var host = $(window.parent.document.getElementById("host")).html();
                $("#submitBT").attr("disabled",true);
                $.ajax({
                    type: "POST",
                    dataType: "html",
                    url:host+"/user/loginIn.do",
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
		<strong>请求参数</strong>
	</div>
	<div>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
                    <th style="width:10%">参数名称</th>
                    <th style="width:10%">参数命名</th>
                    <th style="width:10%">示例值</th>
                    <th>备注</th>
                </tr>
                <tr>
                    <td style="width:10%">工号/学号</td>
                    <td style="width:10%">userNo</td>
                    <td style="width:10%">90916p39</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">密码</td>
                    <td style="width:10%">password</td>
                    <td style="width:10%">123456</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">角色</td>
                    <td style="width:10%">role</td>
                    <td style="width:10%">0</td>
                    <td style="width:10%">0:学生角色(用户名 90916p39，密码 123456) </br>1:辅导员角色(用户名 1，密码 1) </br> 2:学管处角色(用户名 2，密码  2)  </td>
                </tr>
                <tr>
                    <td style="width:10%">当前的系统时间</td>
                    <td style="width:10%">requestTime</td>
                    <td style="width:10%">2014-07-15 05:04:02</td>
                    <td style="width:10%">时间格式 yyyy-MM-dd HH:mm:ss</td>
                </tr>
                <tr>
                    <td style="width:10%">秘钥</td>
                    <td style="width:10%">token</td>
                    <td style="width:10%"> </td>
                    <td style="width:10%">秘钥的生成方式见 API URL token=</td>
                </tr>
			</thead>
		</table>
	</div>
  </div>

  <div style="margin:0px 18px;">
	<div class="alert alert-info"><strong>模拟调用</strong></div>


	<div>
		<form class="form-inline" name="subForm" id="subForm">
			<div class="input-prepend">
			<p>
				<span class="add-on">userNo</span>
					<input class="span2" type="text" name="userNo" value='90916p39'/>
				<span class="add-on">password</span>
					<input class="span2" type="text" name="password" value='123456' />
				<span class="add-on">role</span>
					<input class="span2" type="text" name="role" value='0' />
                <span class="add-on">requestTime</span>
                    <input class="span2" type="text" name="requestTime" value='2014-07-15 05:04:02' />
                <span class="add-on">token</span>
                    <input class="span2" type="text" name="token" value='d76d707ec9e88abdd736f3e932bd4059' />
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