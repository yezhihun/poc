<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("app", request.getContextPath());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link href="${app}/js/jquery-easyui-1.4.1/themes/default/easyui.css"
	rel="stylesheet" type="text/css">
<link href="${app}/css/easyui-yg.css" rel="stylesheet" type="text/css">
<link href="${app}/vendor/bootstrap/css/bootstrap-datetimepicker.css"
	rel="stylesheet">
	<style>
		/*.window-header,.window-body{min-width:430px}  */
		.window{min-width: 442px !important;}
		.datetimepicker{z-index:999999}
	</style>
	
<script src="${app}/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="${app}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script src="${app}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"
	charset="UTF-8" type="text/javascript"></script>
<script src="${app}/vendor/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript">
	var datagrid;
	$(function() {
		var url = "${app}/monthlyRepaymentController/getPayChannelList";
		$.ajax({
			url : url,
			type : "GET"
		}).then(function (data) {
			if (data != null) {
				$.each(data, function (i, v) {
					$('#plantformType').append($('<option>').val(v.id).text(v.name));
				})
			}
		});
		datagrid = $('#datagrid').datagrid(
				{
					url : "${app}/monthlyRepaymentController/showList",
					title : '',
					pagination : true,
					pageSize : 10,
					pageList : [ 10, 20, 30, 40, 50 ],
					toolbar : "#toolbar",
					border : true,
					idField : 'id',
					singleSelect : true,
					fit : false,
					onLoadSuccess : function(data) {
						$("#totalMoney").empty()
								.append(format(data.totalMoney));
					},
					columns : [ [
							{
								field : 'orderNum',
								title : '进件编号',
								width : 190,
								align : 'center'
							},
							{
								field : 'requestId',
								title : '流水号',
								width : 180,
								align : 'center'
							},
							{
								field : 'payChannel',
								title : '支付渠道',
								width : 100,
								align : 'center',
								formatter : function(value, rowData, rowIndex) {
									return value;
								}
							},
							{
								field : 'payAmounts',
								title : '月还金额(元)',
								width : 100,
								align : 'center',
								formatter : function(value, rowData, rowIndex) {
									if (value != null) {
										var res = format(value);
										return res;
									}
								}
							},
							{
								field : 'loanDeadline',
								title : '贷款期限(月)',
								width : 100,
								align : 'center'
							},
							{
								field : 'repayDate',
								title : '月还时间',
								width : 100,
								align : 'center',
								formatter : function(val, rowData, rowIndex) {
									return dateFormat(new Date(val),
											"yyyy-MM-dd");
								}
							},
							{
								field : 'userName',
								title : '借款人',
								width : 100,
								align : 'center'
							},
							{
								field : 'bankDetailed',
								title : '支行名称',
								width : 160,
								align : 'center'
							},
							{
								field : 'bankCard',
								title : '卡号',
								width : 170,
								align : 'center'
							},
							{
								field : 'deductStatus',
								title : '交易状态',
								width : 120,
								align : 'center',
								formatter : function(val, rowData, rowIndex) {
									if(val==1){
										return "未处理";
									}else if(val==2){
										return "处理中";
									}else if(val==3){
										return "代扣成功";
									}else if(val==4){
										return "代扣失败";
									}else{
										return val;
									}
								}
							},
							{
								field : 'deductTime',
								title : '交易时间',
								width : 160,
								align : 'center',
								formatter : function(val, rowData, rowIndex) {
									if (val) {
										return dateFormat(new Date(val),
												"yyyy-MM-dd hh:mm:ss");
									} else {
										return "";
									}
								}
							}, {
								field : 'transactionStatusMsg',
								title : '交易状态信息',
								width : 120,
								align : 'center'
							}, {
								field : 'storeName',
								title : '门店名称',
								width : 120,
								align : 'center'
							}, {
								field : 'storeCity',
								title : '门店所在城市',
								width : 120,
								align : 'center'
							}, {
								field : 'storeArea',
								title : '门店区域',
								width : 150,
								align : 'center'
							} ] ]
				});

		/*时间插件*/
		var h;
		$('.need_datetimepicker').datetimepicker({
			language : 'cn',
			todayBtn : 1,//标记今天
			autoclose : 1,//自动关闭
			todayHighlight : 1,//
			minView : "month", //选择日期后

		}).on('changeDate', function(ev) {
			var tt = ev.date.valueOf();
			var time = new Date(tt);
			var d = time.getDate();
			var m = time.getMonth();
			m = m + 1;
			var y = time.getFullYear();
			var fre = y / 4;
			p_fre = Math.floor(fre);

			if (d == 15 || d == 30) {
				return true
			} else if (fre == p_fre && m == 2 && d == 29) {
				return true
			} else if (fre !== p_fre && m == 2 && d == 28) {
				return true
			} else {
				alert("请选择月还日！");
				$('.need_datetimepicker').val("");
			}
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

		$('.need_datetimepicker').click(function() {
			colortime();
		});
		$('.table-condensed').click(function() {
			colortime();
		});

		/*展开代码*/
		var ul_show = 1;
		$("#show_all").click(function(e) {
			e.preventDefault();
			if (ul_show == 1) {
				$("#top_form").removeClass("biaoge_hide");
				$(this).html("- 收起");
				ul_show = 0
			} else {
				$("#top_form").addClass("biaoge_hide");
				$(this).html("+显示全部");
				ul_show = 1
			}
		});

		$("#searchBtn").bind("click", function(data) {
			var repayDate = $("#repayDate").val();
			var orderNum = $("#orderNum").val();
			var plantformType = $("#plantformType").val();
			var deductStatus = $("#deductStatus").val();
			var failReason = $("#failReason").val();

			var data = {
				"repayDate" : repayDate,
				"deductStatus" : deductStatus,
				"failReason" : failReason,
				"orderNum" : orderNum,
				"plantformType" : plantformType
			};
			datagrid.datagrid('load', data);
		});

		$("#downBtn").bind(
				"click",
				function(data) {

					var repayDate = $("#repayDate").val();
					var orderNum = $("#orderNum").val();
					var plantformType = $("#plantformType").val();
					var deductStatus = $("#deductStatus").val();
					var failReason = $("#failReason").val();
					if (!repayDate) {
						alert("月还时间不可为空");
						return;
					}
					//var data = {"repayDate":repayDate,"deductStatus":deductStatus,"failReason":failReason,"orderNum":orderNum,"plantformType":plantformType};
					var param = "?repayDate=" + repayDate + "&deductStatus="
							+ deductStatus + "&failReason=" + failReason
							+ "&orderNum=" + orderNum + "&plantformType="
							+ plantformType;
					window.open("${app}/monthlyRepaymentController/export"
							+ param);
					//$.post('${app}/monthlyRepaymentController/export', data);
				});
		$("#refreshBtn").bind(
				"click",
				function(data){
					$("#syndata-window").show();
					$("#syndata-window").window("open");
		    });
	    });
	function cancelSyndata() {
		$("#syndata-window").window("close");
	}
	function subbmitSyndata() {
		var date = $("#syncDate").val();
		if (!date) {
			alert("同步时间不可为空!");
			return;
		} 
		var _url = "${app}/monthlyRepaymentController/synchronizedDate";
		$.ajax({
			url: _url,
     	    type : "post",
     	    data : {
     	    	syncDate : date
     	    },
      	    dataType : "json",
      	  success : function(data){
      		if(data && data.result=="success"){
    	   	     alert("同步数据完成");
          	  }else{
	        	    alert(data.msg);
           	 }
      	  },
      	  error:function(data){
				alert(data);
         	}
		})
		$("#syndata-window").window("close");
		
		/* $("#sync-date").val("");
		addJob("${app}/monthlyRepaymentController/addJob", desc, date);
		$("#syndata-window").window("close"); */
	}

</script>

<title>报表查询-月还列表</title>
<body>
	<div id="page-wrapper" style="min-height: 819px;">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">月还列表</h1>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="form-inline row  biaoge" role="form"
					id="top_form">
					<div class="col-xs-8">
						<div class="form-group col-sm-4 col-xs-6 mb20">
							<label>月还时间：</label> <input
								class="form-control need_datetimepicker" type="text"
								data-date-format="yyyy-mm-dd" id="repayDate" readonly="readonly">
						</div>
						<div class="form-group col-sm-4 col-xs-6 mb20">
							<label>进件编号：</label> <input type="text" class="form-control"
								placeholder="进件编号" id="orderNum">
						</div>
						<div class="form-group col-sm-4 col-xs-6 mb20">
							<label>支付渠道：</label> <select class="form-control"
								id="plantformType">
								<option value="">全部</option>
<%--								<option value="1">银联</option>
								<option value="2">富有</option>
								<option value="3">银企直连</option>
								<option value="4">首信易</option>
								<option value="5">银生宝</option>
								<option value="6">一九付</option>
								<option value="7">丰付</option>
								<option value="8">中金</option>--%>
							</select>
						</div>
						<div class="form-group col-sm-4 col-xs-6 mb20">
							<label>交易状态：</label> <select class="form-control"
								id="deductStatus">
								<option value="">全部</option>
								<option value="1">未处理</option>
								<option value="2">处理中</option>
								<option value="3">代扣成功</option>
								<option value="4">代扣失败</option>
							</select>
						</div>
						<!-- <div class="form-group col-sm-4 col-xs-6 mb20">
									<label>失败原因：</label> <select class="form-control"
										id="failReason">
										<option value="">全部</option>
										<option>姓名,身份证号已修改</option>
										<option>手机号已修改</option>
										<option>卡号已更换</option>
										<option>不配合,联系不上</option>
										<option>已汇款</option>
									</select>
								</div> -->
					</div>
					<div class="form-group col-xs-4 bor-l">
						<button class="btn btn-warning  btn-sm mr20 w120" id="searchBtn">
							<span class="glyphicon glyphicon-search mr5"></span>搜索
						</button>
						<button class="btn  btn-sm btn-primary  mr20 w120" id="downBtn">
							<span class="glyphicon glyphicon-save mr5"></span>下载
						</button>
						<!--<button class="btn btn-link btn-sm btn-xsqb" id="show_all">+
							显示全部</button> -->
						
							<div class="clear"></div>
							<button class="btn  btn-sm btn-info w120"  style="margin-top: 20px;" id="refreshBtn">
							<span class="glyphicon glyphicon-refresh mr5"></span>同步数据
						</button>
					</div>
				</div>
				<hr>
				<p class="xiangxi">
					<label>总金额: </label> <label id="totalMoney">0</label> 元
				</p>
				<div region="center" border="false" style="overflow: hidden;">
					<table id="datagrid"></table>
				</div>
			</div>
		</div>
	</div>

	<div id="syndata-window" class="easyui-window"
				data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false
										,iconCls:'icon-save',collapsible:false,inline:false"
				title="同步数据" style="display: none">
				<div class="easyui-layout"
					style="width: 430px; height: 120px; border: 1px rgb(202, 196, 196) solid; border-radius: 5px;">
					<div style="height: 100%;" class="row">
						<table style="margin-left: 70px;">
							<tr>
								<td><label style="margin: 10px;">同步时间:</label></td>
								<td><input class="form-control need_datetimepicker"
									style="margin: 10px;" type="text"
									data-date-format="yyyy-mm-dd" id="syncDate"
									readonly="readonly"></td>
							</tr>
						</table>
						<div style="text-align: center;">
							<button class="btn btn-warning  btn-sm " onclick="subbmitSyndata()"
								style="margin: 10px;">
								<span class="glyphicon mr5">确定</span>
							</button>
							<button class="btn  btn-sm btn-primary " style="margin: 10px;"
								onclick="cancelSyndata()">
								<span class="glyphicon  mr5">取消</span>
							</button>
						</div>
					</div>
				</div>
		</div>
</body>