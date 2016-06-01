<!DOCTYPE html>
<html class="no-js">

<head>
<title>Cocktail Admin</title>
<!-- Bootstrap -->
<link href="resources/bootstrap/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link
	href="resources/bootstrap/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen">
<link
	href="resources/bootstrap/vendors/easypiechart/jquery.easy-pie-chart.css"
	rel="stylesheet" media="screen">
<link href="resources/bootstrap/assets/styles.css" rel="stylesheet"
	media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script
	src="resources/bootstrap/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
</head>

<body>

	<%@ include file="failLogin.jsp"%>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="home">Cocktail Admin</a>
				<div class="nav-collapse collapse">
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="icon-user"></i> ${sessionScope.adminLoginInfo.adminId} <i
								class="caret"></i>

						</a>
							<ul class="dropdown-menu">

								<li class="divider"></li>
								<li><a tabindex="-1" href="logout">Logout</a></li>
							</ul></li>
					</ul>



				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
				<li><a href="showStatistic"><i class="icon-chevron-right"></i>
							Statistics</a></li>
					<li ><a href="showSector"><i
							class="icon-chevron-right"></i> Sector</a></li>
					
					<li ><a href="showBeacon"><i class="icon-chevron-right"></i>
							Beacon</a></li>
					<li class="active"><a href="users"><i class="icon-chevron-right"></i>
							Users</a></li>
					<li ><a href="showRequirement"><i class="icon-chevron-right"></i>
							Requirement</a></li>

				</ul>
			</div>

			<!--/span-->
			<div class="span9" id="content">

				<div class="row-fluid">
					<div class="span12">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left"><h3>User Log Data</h3></div>

							</div>
							
							<div class="block-content collapse in">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>#</th>
											<th>userId</th>
											<th>sectorId</th>
											<th>brightness</th>
											<th>modeId</th>
											<th>callId</th>
											<th>log</th>
										</tr>
									</thead>
									<tbody>
										<%
											int i = 1;
										%>
										
											
												<c:forEach items="${logList}" var="row">
													<tr>
														<th><%=i++%></th>
														<th>${row.userId}</th>
														<th>${row.sectorId}</th>
														<th>${row.brightness}</th>
														<th>${row.modeId}</th>
														<th>${row.callId}</th>
														<th>${row.log}</th>
														
													</tr>
												</c:forEach>
										
											
											
										
							
									</tbody>
								</table>

							</div>
							  

						</div>
						<!-- /block -->
					</div>

				</div>
				<div class= "span3">
				<table class="table table-bordered">
				<thead><tr><th>modeId</th></tr></thead>
				<tr><th>0</th><th>Silent</th></tr>
				<tr><th>1</th><th>Vibrate</th></tr>
				<tr><th>2</th><th>Bell</th></tr>
				</table></div>
				<div class= "span3">
				<table class="table table-bordered">
				<thead><tr><th>callId</th></tr></thead>
				<tr><th>0</th><th>call rejection off</th></tr>
				<tr><th>1</th><th>call rejection on</th></tr>
				</table>
				</div>
			</div>
		</div>
		<hr>
		<footer>
			<div class="pull-right">
				<p>&copy; Cocktail 2016</p>
			</div>
		</footer>
	</div>
	<!--/.fluid-container-->
	<script src="resources/bootstrap/vendors/jquery-1.9.1.min.js"></script>
	<script src="resources/bootstrap/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="resources/bootstrap/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
	<script src="resources/bootstrap/assets/scripts.js"></script>
	<script>
		$(function() {
			// Easy pie charts
			$('.chart').easyPieChart({
				animate : 1000
			});
		});
	</script>
</body>

</html>