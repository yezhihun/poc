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
<title>报表查询-月还列表</title>

<!-- Bootstrap Core CSS -->
<link href="${app}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- MetisMenu CSS -->
<link href="${app}/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="${app}/vendor/datatables-plugins/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="${app}/vendor/datatables-responsive/dataTables.responsive.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="${app}/vendor/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="${app}/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="${app}/vendor/home/home.css" rel="stylesheet"
	type="text/css">
<link href="${app}/js/jquery-easyui-1.4.1/themes/default/easyui.css"
	rel="stylesheet" type="text/css">
<link href="${app}/css/easyui-yg.css" rel="stylesheet" type="text/css">

<style type="text/css">
.form-control[readonly] {
	background-color: #fff;
	opacity: 1;
}
</style>
<!-- jQuery -->
<script src="${app}/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${app}/vendor/bootstrap/js/bootstrap.min.js"></script>




<!-- Metis Menu Plugin JavaScript -->
<script src="${app}/vendor/metisMenu/metisMenu.min.js"></script>
<script src="${app}/js/common/common.js"></script>

<!-- DataTables JavaScript -->
<script src="${app}/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script
	src="${app}/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script
	src="${app}/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${app}/vendor/dist/js/sb-admin-2.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->


<script type="text/javascript"
	src="${app}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script src="${app}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"
	charset="UTF-8" type="text/javascript"></script>
	

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript">
	$(function() {
		$.get("${app}/monthlyRepaymentController/showListPage",function(data){
			$("#main").html(data);
		});
		$(".yg-menu").bind("click",function(){
			var url = $(this).attr("id");
			$.get(url,function(data){
				$("#main").empty().html(data);
			});
		});
		var url="${app}/userInfo";
		$.ajax({
			url: url
		}).then(function (data) {
			$('#username').append(data);
		},function (data) {
			console.log(data);
		});
	});

</script>
</head>

<body>
	<div id="wrapper" class="yy_home">

		<!-- Navigation -->

		<nav class="navbar navbar-default navbar-static-top yy_home_top_bg"
			role="navigation" style="margin-bottom: 0">
		<div class="yy_home_top  clearfix">
			<div class="navbar-header">
				<button type="button"
					class="btn btn-link f24 pull-right pc_fff visible-xs-block"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="glyphicon glyphicon-align-justify"></span>
				</button>
				<a class="navbar-brand p10" ><img
					src="${app}/vendor/img/logo1.png" height="30" alt="" /></a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">

				<li id="username"></li>
				<%--/.dropdown--%>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<%--<li><a href="#"><i class="fa fa-gear fa-fw"></i> 修改密码</a></li>--%>
						<li class="divider"></li>
						<%--https://www.smartscity.com/cas/logout--%>
						<li><a id="logout" href="${app}/logout"><i class="fa fa-sign-out fa-fw"></i>
								退出</a></li>
					<%--</ul> /.dropdown-user</li>--%>
				<%--/.dropdown--%>
			</ul>
			<!-- /.navbar-top-links -->
		</div>
		<div class="navbar-default sidebar yy_home_left" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li class="sidebar-search" style="display: none">
						<div class="input-group custom-search-form">
							<input type="text" class="form-control" placeholder="Search...">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div> <!-- /input-group -->
					</li>
					<li><a href="#"><i class="fa fa-list-alt fa-fw"></i> 报表查询<span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level collapse in">
							<li><a id="${app}/monthlyRepaymentController/showListPage" class="yg-menu active">月还列表</a></li>
						</ul>
					</li>
					<li><a href="#"><i class="fa fa-list-alt fa-fw"></i>月还<span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level collapse ">
							<li><a id="${app}/qrtzTriggerController/showPage" class="yg-menu ">月还任务</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>
		<div id="main">
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</body>
</html>
