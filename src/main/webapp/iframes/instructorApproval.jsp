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
            window.parent.changeAddress("leave/instructorApproval")
            $("#submitBT").bind("click",function(){
                $("#prevId").html("");
                var host = $(window.parent.document.getElementById("host")).html();
                $("#submitBT").attr("disabled",true);
                $.ajax({
                    type: "POST",
                    dataType: "html",
                    url:host+"/leave/instructorApproval.do",
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
        辅导员或学管处审批</br>
        注意，测试此功能是，请先登录一个老师的账号，找到老师的userID</br>
        用户名:1  密码：1  （辅导员）</br>
        用户名:2  密码：2  （学管处）</br>
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
                    <td style="width:10%" >老师ID</td>
                    <td style="width:10%">userId</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%"> 1:待审批   2:最新审批 3:审批列表时间段查询</td>
                </tr>
                <tr>
                    <td style="width:10%">假条ＩＤ</td>
                    <td style="width:10%">leaveId</td>
                    <td style="width:10%">2</td>
                    <td style="width:10%">可以从请假列表这个接口来获得未审批的假条</td>
                </tr>
                <tr>
                    <td style="width:10%">秘钥</td>
                    <td style="width:10%">token</td>
                    <td style="width:10%"> </td>
                    <td style="width:10%">秘钥的生成方式见 API URL token=</td>
                </tr>
                <tr>
                    <td style="width:10%">审批结果</td>
                    <td style="width:10%">approvedState</td>
                    <td style="width:10%">0</td>
                    <td style="width:10%">0 不同意   1  同意</td>
                </tr>

                <tr>
                    <td style="width:10%">备注信息</td>
                    <td style="width:10%">note</td>
                    <td style="width:10%">朕，允许了</td>
                    <td style="width:10%" style="background:red;">不要超过50字</td>
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
                <span class="add-on">leaveId</span>
                    <input class="span2" type="text" name="leaveId" value='2' />
                <span class="add-on">approvedState</span>
                    <input class="span2" type="text" name="approvedState" value='0' />
                <span class="add-on">note</span>
                 <input class="span2" type="text" name="note" value='请填写审批说明' />
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