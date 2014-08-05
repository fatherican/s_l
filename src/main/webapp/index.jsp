<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <link rel="stylesheet" type="text/css" href="resources/css/css.css" />
	
  <title></title>
  <style>
	<![CDATA[
		.title { font-size:14px; font-weight:bold; background:#EDF3F9; padding:0px 15px;}
	 ]]>
  </style>
  <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js" type="text/javascript"></script>
  
  <script type="text/javascript">
	$(document).ready(function(){
		$("#div_interface p a").click(function(){
			$("#div_interface p a").removeClass("btn-info");
			$(this).addClass("btn-info");
			
		});
	});
	
	/**
	*显示实际调用的地址
	*/
	function changeAddress(str){
		$("#address").html(str);
	}

    /**
     * 改变token的显示
     * @param str
     */
    function changeToken(str){
        $("#token").html(str);
    }
	
	function changeIframe(iframeSrc){
		var iframe = document.getElementById("ifm");
		iframe.src="../iframes/"+iframeSrc+".jsp";
	}
  </script>
  
  <script  type="text/javascript"> 
	function dyniframesize(down) {
		var pTar = null;
		if (document.getElementById){
			pTar = document.getElementById(down);
		}
		else{
			eval('pTar = ' + down + ';');
		}
		if (pTar && !window.opera){
			//begin resizing iframe
			pTar.style.display="block"
			if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){
				//ns6 syntax
				pTar.height = pTar.contentDocument.body.offsetHeight +20;
				pTar.width = pTar.contentDocument.body.scrollWidth+20;
			}
			else if (pTar.Document && pTar.Document.body.scrollHeight){
				//ie5+ syntax
				pTar.height = pTar.Document.body.scrollHeight;
				pTar.width = pTar.Document.body.scrollWidth;
			}
		}
	}
	

	</script> 
</head>

<body>


  <div class="navbar navbar-static-top navbar-inverse">
    <div class="navbar-inner">
      <span><a class="brand" href="#">学生请假系统 API</a>
    </div>
  </div>

  <div style="padding:18px;">
    <div id="div_interface" style="padding-bottom:5px;margin-bottom:15px;border-bottom:1px solid #DDDDDD">
      <p>
			<span onclick="changeIframe('login');"><a href="#" class="btn btn-info">登陆</a></span>
			<span  onclick="changeIframe('addLeave');"><a href="#" class="btn">请假</a></span>
			<span onclick="changeIframe('studentGetLeaveList');"><a href="#" class="btn">获得请假列表</a></span>
			<span onclick="changeIframe('delLeaveItem');"><a href="#" class="btn">删除请假</a></span>
			<span onclick="changeIframe('instructorApproval');"><a href="#" class="btn">销假</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>


	  </p>

      <p>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>
			<span><a href="#" class="btn">测试</a></span>

	  </p>
    </div>
  </div>
  <div style="margin:0px 18px;">
	  <div class="alert alert-info" >
		<strong>API URL   </strong>token=md5(<span id="token"></span>)   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测试key统一为：123456    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(MD5  可以直接在百度上搜索一个MD5在线加密 【32位小写】)
	  </div>
	  <div  style="margin-bottom:18px;">
			<span id="host">http://localhost:8080</span>/<span id="address"></span>.do
	  </div>
 </div>



 <iframe src="iframes/login.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" id="ifm" name="ifm" onload="javascript:dyniframesize('ifm');" width="100%"></iframe>

  <div style="margin:0px 18px;">
      <div class="alert alert-info" >
          <strong>结果代码说明 </strong>
      </div>
      <div  style="margin-bottom:18px;">
          200  成功    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          400  逻辑错误（例如传参有误等）   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          500 内部服务器错误  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          300（session过时）
      </div>
  </div>
</body>
</html>
