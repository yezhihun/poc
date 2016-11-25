<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("app", request.getContextPath());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link href="${app}/js/jquery-easyui-1.4.1/themes/default/easyui.css"
	rel="stylesheet" type="text/css">
<link href="${app}/css/easyui-yg.css" rel="stylesheet" type="text/css">
<link href="${app}/vendor/bootstrap/css/bootstrap-datetimepicker.css"
	rel="stylesheet">

<script src="${app}/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="${app}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script src="${app}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"
	charset="UTF-8" type="text/javascript"></script>
<script src="${app}/vendor/bootstrap/js/bootstrap-datetimepicker.js"></script>
<title>任务列表</title>
<style type="text/css">
.btn_box:hover{ background:none;border:none}
.btn_box .l-btn-text {
    
        background-color: #286090;
    border-color: #204d74;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    color: #fff;
    border-radius: 5px;
    padding: 0px 17px;}
	.window{    min-width: 442px !important;}
	.a_dis{ pointer-events: none;cursor: not-allowed;filter: alpha(opacity=65);-webkit-box-shadow: none;box-shadow: none; opacity: .65;}
</style>
<script type="text/javascript">
	var datagrid;
	var crontt;
	$(function() {
		//$("#cron-page").hide();
		crontt = $("#cron-window");
		datagrid = $('#datagrid')
				.datagrid(
						{
							url : "${app}/qrtzTriggerController/showListJob",
							title : '',
							pagination : true,
							toolbar : "#toolbar",
							pageSize : 10,
							pageList : [ 10, 20, 30, 40, 50 ],
							border : true,
							idField : 'id',
							singleSelect : true,
							fit : false,
							queryParams : {
								startDate : $("#startDate").val(),
								endDate : $("#endDate").val()
							},
							columns : [ [
									{
										field : 'JOB_NAME',
										title : '任务名称',
										width : 180,
										align : 'center'
									},
									{
										field : 'DESCRIPTION',
										title : '描述',
										width : 180,
										align : 'center'
									},
								 /* {
										field : 'PREV_FIRE_TIME',
										title : '上次执行时间',
										width : 180,
										align : 'center',
										formatter : function(value) {
											if (value == -1) {
												return "";
											} else {
												return dateFormat(new Date(
														value),
														"yyyy-MM-dd hh:mm:ss");
											}
										}
									}, */
									{
										field : 'FIRE_TIME',
										title : '执行时间',
										width : 180,
										align : 'center',
										formatter : function(value) {
											if (value == -1) {
												return "";
											} else {
												return dateFormat(new Date(
														value),
														"yyyy-MM-dd hh:mm:ss");
											}
										}
									},
									{
										field : 'TRIGGER_STATE',
										title : '状态',
										width : 100,
										align : 'center',
										formatter : function(value, rowData,
												rowIndex) {
											if (value == "WAITING") {
												return "等待";
											} else if (value == "PAUSED") {
												return "暂停";
											} else if (value == "ACQUIRED") {
												return "正常执行";
											} else if (value == "BLOCKED") {
												return "阻塞";
											} else if (value == "ERROR") {
												return "错误";
											} else if (value == "DONE") {
												return "完成";
											} else {
												return value;
											}

										}
									},
									/* {
										field : 'CRON_EXPRESSION',
										title : '时间表达式',
										width : 180,
										align : 'center',
										editor:{
									        type:'text',
									        options:{
									        	required:true
									        }
									    }
									}, */
									{
										field : 'TRIGGER_NAME',
										title : '操作',
										width : 180,
										align : 'center',
										formatter : function(value, rowData,
												rowIndex) {
											var e = "<a href='#' onclick=editRow('"
													+ rowIndex
													+ "\',\'"
													+ value
													+ "')>修改</a>";
											var s = "<a href='#' onclick=saveRow('"
													+ rowIndex + "')>保存</a>";
											var c = "<a href='#' onclick=cancelRow('"
													+ rowIndex + "')>取消</a>";

											var d = "<a href='#' onclick=deleteJob('"
													+ rowData.JOB_NAME
													+ "')>删除</a>";
											var start = '<a href="#" onclick="startJob(\''
													+ value
													+ '\',\''
													+ rowData.JOB_NAME
													+ '\')">开始</a>';
											var pause = '<a href="#" onclick="pauseJob(\''
													+ value
													+ '\',\''
													+ rowData.JOB_NAME
													+ '\')">暂停</a>';
											if (rowData.editing){
												return s + " | " + c;
											}else{
												if(rowData.TRIGGER_STATE=="PAUSED"){
													return start +" | " + e + " | " + d;
												}else if(rowData.TRIGGER_STATE=="DONE"){
													/*start = '<a href="#" class="a_dis">-</a>';
													e = '<a href="#" class="a_dis">-</a>';
													d = '<a href="#" class="a_dis">删除</a>';
													return start +" | " + e + " | " + d;*/
													d = '<a href="#" class="a_dis">删除</a>';
													return d;
												}
												else {
													return pause +" | " + e + " | " + d;
												}
											}
											/* if (rowData.TRIGGER_STATE == "PAUSED") {
												return start + " | " + d;
											} else {
												return pause + " | " + d;
											} */
										}
									} ] ],
							onAfterEdit : function(index, row, changes) {
								row.editing = false;
								updateActions(index);
								row.CRON_EXPRESSION = $("#cron").val();
								updateCron(
										encodeURIComponent(row.CRON_EXPRESSION),
										row.TRIGGER_NAME);
								$("#cron-window").window("close");
							},
							onBeforeEdit : function(index, row) {
								row.editing = true;
								updateActions(index);
								$("#cron-window").window("open");
							},
							onCancelEdit : function(index, row) {
								row.editing = false;
								updateActions(index);
								$("#cron-window").window("close");
							}
						});

		$("#addJob").bind("click", function(data) {
			$("#monthly-window").show();
			$("#monthly-window").window("open");
		});
		$("#addBatchJob").bind("click", function(data) {
			$("#batch-monthly-window").show();
			$("#batch-monthly-window").window("open");
		});
		$("#searchBtn").bind("click", function(data){
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var data = {
					"startDate" : startDate,
					"endDate" : endDate
				};
			datagrid.datagrid('load', data);
		});
		$("#startDate").val(dateFormat(new Date(),"yyyy-M-d"));
	});

	function updateCron(_cron, _triggerName) {
		$.ajax({
			url : "${app}/qrtzTriggerController/updateCron",
			type : "post",
			data : {
				cron : _cron,
				triggerName : _triggerName
			},
			dataType : "json",
			success : function(data) {
				if (data.result == "success") {
					alert("操作成功!");
				} else {
					alert(data.msg);
				}
				datagrid.datagrid('load');
			},
			error : function() {
				alert(arguments[1]);
			}
		});
	}

	function updateJob(_triggerName){
		$("#update-trigger-name").val(_triggerName);
		$("#update-trigger-name").window("open");
	}

	function doUpdateJob(){
		var _date = $("#update-cron-date").val();
		var _triggerName = $("#update-trigger-name").val();
		$.ajax({
			url : "${app}/qrtzTriggerController/updateCron",
			type : "post",
			data : {
				date : _date,
				triggerName : _triggerName
			},
			success : function(data) {
				if (data.result == "success") {
					alert("操作成功!");
				} else {
					alert(data.msg);
				}
				$("#update-monthly-window").window("close");
				datagrid.datagrid('load');
			},
			error : function() {
				alert(arguments[1]);
			}
		}).then(function () {
			$("#update-cron-date").val("");
		});
	}

	$('#update-back').click(function () {
		$("#update-cron-date").val("");
	});

	function deleteJob(_jobName) {
		if (window.confirm("确定删除该任务吗？")) {
			$.ajax({
				url : "${app}/qrtzTriggerController/deleteJob",
				type : "post",
				data : {
					jobName : _jobName
				},
				success : function(data) {
					if (data.result == "success") {
						alert("操作成功!");
					} else {
						alert(data.msg);
					}
					datagrid.datagrid('load');
				}
			});
		}
	}

	function addJob(_url, _desc, _date) {
		$.ajax({
			url : _url,
			type : "post",
			data : {
				desc : _desc,
				date : _date
			},
			success : function(data) {
				if (data.result == "success") {
					alert("操作成功!");
				} else {
					alert(data.msg);
				}
				datagrid.datagrid('load');
			}
		});
	}

	function updateActions(index) {
		$('#datagrid').datagrid('updateRow', {
			index : index,
			row : {}
		});
	}

	function saveRow(index) {
		$('#datagrid').datagrid('endEdit', index);
	}

	function cancelRow(index) {
		$('#datagrid').datagrid('cancelEdit', index);
	}

	function editRow(index,_triggerName) {
		//$('#datagrid').datagrid('beginEdit', index);
		//alert(_triggerName);
		$("#update-trigger-name").val(_triggerName);
		$("#update-monthly-window").show();
		$("#update-monthly-window").window("open");
	}

	function startJob(triggerName, jobName) {
		$.ajax({
			url : "${app}/qrtzTriggerController/startJob/" + triggerName + "/"
					+ jobName,
			type : "post",
			success : function(data) {
				if(data.result == "error"){
					alert(data.msg);
				}
				datagrid.datagrid('load');
			},
			error : function(data) {
				alert("error");
			}
		});
	}

	function pauseJob(triggerName, jobName) {
		$.ajax({
			url : "${app}/qrtzTriggerController/pauseJob/" + triggerName + "/"
					+ jobName,
			type : "post",
			success : function(data) {
				datagrid.datagrid('load');
			},
			error : function(data) {
				alert("error");
			}
		});
	}

	function closeWindow(winName) {
		$("#" + winName + "").window("close");
	}

	var h;
	$('.need_datetimepicker').datetimepicker({
		zIndex : 9999,
		language : 'cn',
		todayBtn : 1,//标记今天
		clearBtn : 1,
		autoclose : 1,//自动关闭
		todayHighlight : 1, //
	//minView : "hour", //选择日期后
	});

	$('.need_datepicker').datetimepicker({
		zIndex : 9999,
		language : 'cn',
		todayBtn : 1,//标记今天
		clearBtn : 1,
		autoclose : 1,//自动关闭
		todayHighlight : 1, //
		minView : "month", //选择日期后
	});

	function colortime() {
		$(".table-condensed td").each(function() {
			h = $(this).text();
			if (h == 15 || h == 30) {
				$(this).css({
					"border" : "1px solid #337ab7"
				})
			} else {
				$(this).css({
					"filter" : "alpha(opacity=40)",
					"opacity" : "0.4"
				})
			}
		});
	}

	$('.need_datetimepicker,.need_datepicker').click(function() {
		colortime();
	});
	$('.table-condensed').click(function() {
		colortime();
	});

	function saveJob() {
		var date = $("#cron-date").val();
		var desc = $("#desc").val();
		if (!date) {
			alert("执行时间不可为空!");
			return;
		}
		if (!desc) {
			alert("任务描述不可为空!");
			return;
		}
		$("#cron-date").val("");
		$("#desc").val("");
		addJob("${app}/qrtzTriggerController/addJob", desc, date);
		$("#monthly-window").window("close");
	}

	function saveBatchJob() {
		var date = $("#batch-cron-date").val();
		var desc = $("#batch-desc").val();
		if (!date) {
			alert("执行时间不可为空!");
			return;
		}
		if (!desc) {
			alert("任务描述不可为空!");
			return;
		}
		$("#batch-cron-date").val("");
		$("#batch-desc").val("");
		addJob("${app}/qrtzTriggerController/addBatchJob", desc, date);
		$("#batch-monthly-window").window("close");
	}

	function cancelJob() {
		$("#monthly-window").window("close");
	}
	function cancelBatchJob() {
		$("#batch-monthly-window").window("close");
	}
</script>
</head>

<body>
	<div id="wrapper" class="yy_home">

		<div id="page-wrapper" style="min-height: 863px;">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">任务列表</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="form-inline row  biaoge biaoge_hide" role="form"
					id="top_form">
				<div class="col-xs-8">
					<div class="form-group col-sm-4 col-xs-6 mb20">
						<label>开始时间：</label> <input
							class="form-control need_datepicker" type="text"
							data-date-format="yyyy-mm-dd" id="startDate" readonly="readonly">
					</div>
					<div class="form-group col-sm-4 col-xs-6 mb20">
						<label>结束时间：</label> <input
							class="form-control need_datepicker" type="text"
							data-date-format="yyyy-mm-dd" id="endDate" readonly="readonly">
					</div>
					<div class="form-group col-xs-4 bor-l">
						<button class="btn btn-warning  btn-sm mr20 w120" id="searchBtn">
							<span class="glyphicon glyphicon-search mr5"></span>搜索
						</button>
					</div>
				</div>
			</div>
			<hr>
			<!-- /.row -->
			<div class="row">
				<div id="datagrid"></div>
			</div>

			<div id="toolbar" style="display: none;">
				<a href="javascript:void(0);" class="btn_box easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:'true'" id="addJob">添加任务</a>
				<a href="javascript:void(0);" class="btn_box easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:'true'" id="addBatchJob">添加批量任务</a>
			</div>
			<div id="monthly-window" class="easyui-window"
				data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false
										,iconCls:'icon-save',collapsible:false,inline:false"
				title="新增月还任务" style="display: none">
				<div class="easyui-layout"
					style="width: 430px; height: 180px; border: 1px rgb(202, 196, 196) solid; border-radius: 5px;">
					<div style="height: 100%;" class="row">
						<table style="margin-left: 70px;">
							<tr>
								<td><label style="margin: 10px;">任务描述:</label></td>
								<td><input type="text" id="desc" class="form-control"
									style="margin: 10px;" /></td>
							</tr>
							<tr>
								<td><label style="margin: 10px;">执行时间:</label></td>
								<td><input class="form-control need_datetimepicker"
									style="margin: 10px;" type="text"
									data-date-format="yyyy-m-d h:i:0" id="cron-date"
									readonly="readonly"></td>
							</tr>
						</table>
						<div style="text-align: center;">
							<button class="btn btn-warning  btn-sm " onclick="saveJob()"
								style="margin: 10px;">
								<span class="glyphicon mr5">保存</span>
							</button>
							<button class="btn  btn-sm btn-primary " style="margin: 10px;"
								onclick="cancelJob()">
								<span class="glyphicon  mr5">取消</span>
							</button>
						</div>
					</div>
				</div>

				<div id="update-monthly-window" class="easyui-window"
				data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false
										,iconCls:'icon-save',collapsible:false,inline:false"
				title="修改月还任务" hidden="hidden">
				<div class="easyui-layout"
					style="width: 430px; height: 180px; border: 1px rgb(202, 196, 196) solid; border-radius: 5px;">
					<div style="height: 100%;" class="row">
						<table style="margin-left: 70px;">
							<tr>
								<td><label style="margin: 10px;">执行时间:</label></td>
								<td><input class="form-control need_datetimepicker"
									style="margin: 10px;" type="text"
									data-date-format="yyyy-m-d h:i:0" id="update-cron-date"
									readonly="readonly"></td>
								<td style="display: none;"><input value="" id="update-trigger-name"> </td>
							</tr>
						</table>
						<div style="text-align: center;">
							<button class="btn btn-warning  btn-sm " onclick="doUpdateJob()"
								style="margin: 10px;">
								<span class="glyphicon mr5">保存</span>
							</button>
							<button id="update-back" class="btn  btn-sm btn-primary " style="margin: 10px;"
								onclick='$("#update-monthly-window").window("close");'>
								<span class="glyphicon  mr5">取消</span>
							</button>
						</div>
					</div>
				</div>

				<div id="batch-monthly-window" class="easyui-window"
					data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false
										,iconCls:'icon-save',collapsible:false,inline:false"
					title="新增批量月还任务" hidden="hidden">
					<div class="easyui-layout"
						style="width: 430px; height: 180px; border: 1px rgb(202, 196, 196) solid; border-radius: 5px;">
						<div style="height: 100%;" class="row">
							<table style="margin-left: 70px;">
								<tr>
									<td><label style="margin: 10px;">任务描述:</label></td>
									<td><input type="text" id="batch-desc"
										class="form-control" style="margin: 10px;" /></td>
								</tr>
								<tr>
									<td><label style="margin: 10px;">执行时间:</label></td>
									<td><input class="form-control need_datepicker"
										style="margin: 10px;" type="text" data-date-format="yyyy-m-d"
										minView="month" id="batch-cron-date" readonly="readonly"></td>
								</tr>
							</table>
							<div style="text-align: center;">
								<button class="btn btn-warning  btn-sm "
									onclick="saveBatchJob()" style="margin: 10px;">
									<span class="glyphicon mr5">保存</span>
								</button>
								<button class="btn  btn-sm btn-primary " style="margin: 10px;"
									onclick="cancelBatchJob()">
									<span class="glyphicon  mr5">取消</span>
								</button>
							</div>
						</div>

					</div>
					<!-- <div class=" container-fluid down-ps">
				<h3>
					<strong>备注：</strong>
				</h3>
				<div class=" col-xs-6">
					<p>
						<strong>1）表达式的格式：秒 分 时 日 月 周 年(可选)。</strong>
					</p>
					<table class="table table-striped table-bordered ">
						<tbody>
							<tr>
								<th>字段名</th>
								<th>允许的值</th>
								<th>允许的特殊符号</th>
							</tr>
							<tr>
								<td>秒</td>
								<td>0-59</td>
								<td>,</td>
							</tr>
							<tr>
								<td>分</td>
								<td>0-59</td>
								<td>,</td>
							</tr>
							<tr>
								<td>小时</td>
								<td>0-23</td>
								<td>, -</td>
							</tr>
							<tr>
								<td>日</td>
								<td>1-31</td>
								<td>,</td>
							</tr>
							<tr>
								<td>月</td>
								<td>1-12 or JAN-DEC</td>
								<td>, - * /</td>
							</tr>
							<tr>
								<td>周</td>
								<td>1-31</td>
								<td>, - * ? / L C</td>
							</tr>
							<tr>
								<td>年 (可选字段）</td>
								<td>empty, 1970-2099</td>
								<td>, - * /</td>
							</tr>
							<tr>
								<td>年 (可选字段）</td>
								<td>empty, 1970-2099</td>
								<td>, - * /</td>
							</tr>
							<tr>
								<td colspan="3"><hr style="margin: 0px"></td>
							</tr>
							<tr>
								<td>“?”字符</td>
								<td colspan="2">表示不确定的值</td>
							</tr>
							<tr>
								<td>“,”字符</td>
								<td colspan="2">指定数个值</td>
							</tr>
							<tr>
								<td>“-”字符</td>
								<td colspan="2">指定一个值的范围</td>
							</tr>
							<tr>
								<td>“/”字符</td>
								<td colspan="2">用在日表示一个月中的最后一天，用在周表示该月最后一个星期</td>
							</tr>
							<tr>
								<td>“L”字符</td>
								<td colspan="2">指定一个值的范围</td>
							</tr>
							<tr>
								<td>“W”字符</td>
								<td colspan="2">指定离给定日期最近的工作日(周一到周五)</td>
							</tr>
							<tr>
								<td>“#”字符</td>
								<td colspan="2">表示该月第几个周X。6#3表示该月第3个周五</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class=" col-xs-6">
					<p>
						<strong>2）表达式范例：</strong>
					</p>
					<table class="table table-striped table-bordered">
						<tbody>
							<tr>
								<td>每隔5秒执行一次</td>
								<td>*/5 * * * * ?</td>
							</tr>
							<tr>
								<td>每隔1分钟执行一次</td>
								<td>0 */1 * * * ?</td>
							</tr>
							<tr>
								<td>每天23点执行一次</td>
								<td>0 0 23 * * ?</td>
							</tr>
							<tr>
								<td>每天凌晨1点执行一次</td>
								<td>0 0 1 * * ?</td>
							</tr>
							<tr>
								<td>每月1号凌晨1点执行一次</td>
								<td>0 0 1 1 * ?</td>
							</tr>
							<tr>
								<td>每月最后一天23点执行一次</td>
								<td>0 0 23 L * ?</td>
							</tr>
							<tr>
								<td>每周星期天凌晨1点实行一次</td>
								<td>0 0 1 ? * L</td>
							</tr>
							<tr>
								<td>在26分、29分、33分执行一次</td>
								<td>0 26,29,33 * * * ?</td>
							</tr>
							<tr>
								<td>每天的0点、13点、18点、21点都执行一次</td>
								<td>0 0 0,13,18,21 * * ?</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div> -->
				</div>
				<!-- /#page-wrapper -->
			</div>
			<!-- /#wrapper -->
		</div>
	</div>
</div>
			<!-- Page-Level Demo Scripts - Tables - Use for reference -->
</body>
</html>
