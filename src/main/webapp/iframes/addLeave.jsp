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
            window.parent.changeAddress("leave/addLeave")
            $("#submitBT").bind("click",function(){
                $("#prevId").html("");
                var host = $(window.parent.document.getElementById("host")).html();
                $("#submitBT").attr("disabled",true);
                $.ajax({
                    type: "POST",
                    dataType: "html",
                    url:host+"/leave/addLeave.do",
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
                    <th style="width:10%;">参数名称</th>
                    <th style="width:10%">参数命名</th>
                    <th style="width:10%">示例值</th>
                    <th>备注</th>
                </tr>
                <tr>
                    <td style="width:10%" >用户ID</td>
                    <td style="width:10%">userId</td>
                    <td style="width:10%">1</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">请假类型</td>
                    <td style="width:10%">leaveType</td>
                    <td style="width:10%">0</td>
                    <td style="width:10%">0:课程请假  1:天次请假    </td>
                </tr>
                <tr>
                    <td style="width:10%">秘钥</td>
                    <td style="width:10%">token</td>
                    <td style="width:10%"> </td>
                    <td style="width:10%">秘钥的生成方式见 API URL token=</td>
                </tr>

                <tr>
                    <td style="width:10%">请假备注信息</td>
                    <td style="width:10%">studentNote</td>
                    <td style="width:10%">我就是想请假，咋的</td>
                    <td style="width:10%" style="background:red;">最多不允许超过50个汉字，前台要做判断</td>
                </tr>
                <tr>
                    <td style="width:10%">学生的联系手机号</td>
                    <td style="width:10%">studentMobile</td>
                    <td style="width:10%">15111111111</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td colspan="4" style="background:red;">上面的字段对 课程请假  和  天数请假  都是通用 的，下面会对不同请假类型作传参区分</td>
                </tr>
                <tr>
                    <td colspan="4"  style="background:green;text-align: center;">课程请假</td>
                </tr>
                <tr>
                    <td style="width:10%">请假的节次</td>
                    <td style="width:10%">courseIndex</td>
                    <td style="width:10%">2</td>
                    <td style="width:10%">一次可以最多请两节大课,
                        </br>第一节大课 的序号 为1，第二节大课的序号为2，
                        </br>第三节大课的序号为4，第四节大课的序号为8 .从2的n次方开始算
                        </br>一天总共四节大课，假如要请1，3两个大课，那么值就是1+4 【5】，
                        </br>其他同理
                    </td>
                </tr>
                <tr>
                    <td style="width:10%">老师姓名</td>
                    <td style="width:10%">teacherName</td>
                    <td style="width:10%">习近平</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">请假日期</td>
                    <td style="width:10%">leaveDate</td>
                    <td style="width:10%">2014-06-06</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td colspan="4"  style="background:yellow;text-align: center;">天数请假</td>
                </tr>
                <tr>
                    <td style="width:10%">开始日期</td>
                    <td style="width:10%">leaveStartDate</td>
                    <td style="width:10%">2014-06-06</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">结束日期</td>
                    <td style="width:10%">leaveEndDate</td>
                    <td style="width:10%">2014-06-07</td>
                    <td style="width:10%"></td>
                </tr>
                <tr>
                    <td style="width:10%">请假天数</td>
                    <td style="width:10%">leaveDays</td>
                    <td style="width:10%">2</td>
                    <td style="width:10%"></td>
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
                <span class="add-on">leaveType</span>
                <select onchange="changeLeaveType();" id="leaveType" name="leaveType">
                    <option value="0">课程请假[0]</option>
                    <option value="1">天次请假[1]</option>
                </select>
                <script type="text/javascript">
                    $('#leaveType').change(function() {
                        var checkIndex=$(this).get(0).selectedIndex;
                        if(checkIndex==0){//只显示课程请假的参数
                            $("#courseP").show();
                            $("#daysP").hide();
                        }else{//只显示天次请假的参数
                            $("#courseP").hide();
                            $("#daysP").show();
                        }
                    });

                </script>
            </p>
			<p>
				<span class="add-on">userId</span>
					<input class="span2" type="text" name="userId" value='1'/>
				<span class="add-on">studentNote</span>
					<input class="span2" type="text" name="studentNote" value='我就是想请假，咋的' />
				<span class="add-on">studentMobile</span>
					<input class="span2" type="text" name="studentMobile" value='15111111111' />
                <span class="add-on">token</span>
                    <input class="span2" type="text" name="token" value='eeafb716f93fa090d7716749a6eefa72' />
			</p>
            <p id="courseP">
                <span class="add-on">courseIndex</span>
                <input class="span2" type="text" name="courseIndex" value='2'/>
                <span class="add-on">courseName</span>
                <input class="span2" type="text" name="courseName" value='计算机应用' />
                <span class="add-on">teacherName</span>
                <input class="span2" type="text" name="teacherName" value='习近平' />
                <span class="add-on">leaveDate</span>
                <input class="span2" type="text" name="leaveDate" value='2014-06-06' />
            </p>
            <p id="daysP" style="display: none;">
                <span class="add-on">leaveStartDate</span>
                <input class="span2" type="text" name="leaveStartDate" value='2014-06-06'/>
                <span class="add-on">leaveEndDate</span>
                <input class="span2" type="text" name="leaveEndDate" value='2014-06-07' />
                <span class="add-on">leaveDays</span>
                <input class="span2" type="text" name="leaveDays" value='2' />
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