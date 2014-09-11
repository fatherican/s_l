//常用项配置和汉化
$.fn.comDataTable=function (init){
	var sStateKey = init.sStateKey;
	var oInit={
		"bDestroy":true,
    	"sDom": 'rt<"bottom"lip>', // 元素布局
	    "bPaginate":true, //翻页功能
		"bLengthChange":true, //改变每页显示数据数量
		"bFilter": false, //过滤功能
		"bSort": true, //排序功能
		"bInfo":true,//页脚信息
		"bAutoWidth":false,//自动宽度
		"bStateSave": false,//保存条件等状态在cookie里
		"fnStateLoadParams": function (oSettings, oData) {
			//总是从第一页开始
		    oData.iStart = 0; 
		    //不读取搜索相关的
		    oData.oSearch = "";
		    oData.aoSearchCols = "";
		    oData.iLength = 10;
		    oData.aaSorting = init.aaSorting;
		} ,
		"fnStateLoad" : function(oSettings, oData) {
			var o;
			if (typeof (sStateKey) != 'undefined' && sStateKey.length > 0) {
				$.ajax({
					url : '/dataTable/stateLoad',
					data : {sStateKey : sStateKey},
					type : 'POST',
					dataType : 'json',
					cache : false,
					async : false,
					timeout : 1000,
					success : function(json) {
						if (json.result == 'ok' && json.data != null && json.data != '') {
							o = json.data;
						}
					}
				});
			}
			return o;
		}, 
		"fnStateSaveParams": function (oSettings, oData) {
			if (typeof (sStateKey) != 'undefined' && sStateKey.length > 0) {
				oData.sStateKey = sStateKey;
			    //不保存搜索相关的
			    oData.oSearch = "";
			    oData.aoSearchCols = "";
			    oData.iLength = 10;
			    oData.aaSorting = init.aaSorting;
			}
		},
		"fnStateSave" : function(oSettings, oData) {
			if (typeof (sStateKey) != 'undefined' && sStateKey.length > 0) {
				$.ajax({
					url : '/dataTable/stateSave',
					"data": 'data=' + JSON.stringify(oData),
					type : 'POST',
					dataType : 'json',
					cache : false,
					timeout : 3000,
					success : function(json) {
						
					}
				});
			}
		},
        "oLanguage": {
			"sLengthMenu": "每页显示 _MENU_ 条",
			"sZeroRecords": "请选择条件后，按搜索按钮开始查询",
			"sProcessing": "正在查询...",
			"sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
			"sInfoEmpty": "没有符合条件的记录",
			"sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
			"sSearch": "搜索：",
			"oPaginate": {
				"sFirst": "首页",
				"sPrevious": "上一页",
				"sNext": "下一页",
				"sLast": "尾页"
			}
		},
	    "sPaginationType": "full_numbers",
	    "aLengthMenu": [[10, 20, 30, 50], [10, 20, 30, 50]],
		 "bProcessing": true,
	 	 "bServerSide": true
	};
	$.extend(true,oInit,init);
	$(this).dataTable(oInit);	
};
//刷新数据
$.fn.refreshData=function(){
	var oTable = $(this).dataTable();
  	oTable.fnPageChange("first");
};
//控制dataTable列的显示和隐藏
$.fn.dtColumnManager=function(init){
	var oTable = $(this).dataTable();
	var tableId = $(this).attr("id");
	var  showid=init.listTargetID;
	var hideInList=init.hideInList;
	var colList='';
	var settings=oTable.fnSettings().aoColumns;
	$(settings).each(function(index,element){
		if($.inArray(index,hideInList)==-1){
			colList+='<li><span>';
			if(element.bVisible)
				colList+='<input id="colum_'+index+'" type="checkbox" class="input-check" checked="checked" onclick="fnShowHide(\''+tableId+'\','+index+')"/> ';
			else
				colList+='<input  id="colum_'+index+'" type="checkbox" class="input-check" onclick="fnShowHide(\''+tableId+'\','+index+')"/> ';
			colList+=element.sTitle;
			colList+='</span></li>';
		}
	});
	if($('#'+showid)){
		$('#'+showid).append(colList);
	}
};
//dataTable列的显示和隐藏切换实现
var fnShowHide = function (tableId, iCol ) {
    var oTable = $('#'+tableId).dataTable();
    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
    oTable.fnSetColumnVis( iCol, bVis ? false : true );
};

//跳转到指定页初始化函数
var pageChangeInit = function (tableId, afterId){
	var pageChangeHtml = "<div style='display: inline-block; *display: inline; *zoom: 1; padding: 3px 0 0 10px;'>"; 
    pageChangeHtml += "<span class='inline-help'>&nbsp转到：</span>";
    pageChangeHtml += "<input type='text' name='pagechange' id='pagechange' class='input-t input-15p'/>";
    pageChangeHtml += "<span class='inline-help'>&nbsp页</span>&nbsp&nbsp";
    pageChangeHtml += "<input type='button' value='确定' onclick='pageChange(\""+tableId+"\")'/></div>";
    $("#"+afterId).after(pageChangeHtml);
    
};
//页面跳转函数
var pageChange = function (tableId) {
	var pagenoreg = /^\d+$/;
	var pageno = $("#pagechange").val();
	if(!pagenoreg.test(pageno)){
		alert("请输入有效的页数！");
		return;
	}
	if(pageno < 1){
		alert("页数至少为1，请重新输入！");
		return;
	}
	var oTable = $('#'+tableId).dataTable();
	var oSettings = oTable.fnSettings();
	var pagenomax = oSettings._iDisplayLength === -1 ? 0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength);
    if(pageno > pagenomax){
    	alert("当前最大页数为"+pagenomax+"，请重新填写跳转页数！");
    	return;
    }
	oTable.fnPageChange(parseInt(pageno)-1);
};
