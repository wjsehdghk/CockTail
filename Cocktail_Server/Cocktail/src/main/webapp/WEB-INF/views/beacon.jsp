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
					
					<li class="active"><a href="showBeacon"><i class="icon-chevron-right"></i>
							Beacon</a></li>
					<li><a href="users"><i class="icon-chevron-right"></i>
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
								<div class="muted pull-left"><h3>Beacon Table</h3></div>

							</div>
							
							<div class="block-content collapse in">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>#</th>
											<th>BeaconId</th>
											<th>sectorId</th>
											<th>distance</th>
											<th>delete</th>
										</tr>
									</thead>
									<tbody>
										<%
											int i = 1;
										%>
										<c:choose>
											<c:when test="${fn:length(beaconList) > 0}">
												<c:forEach items="${beaconList}" var="row">
													<tr>
														<th><%=i++%></th>
														<th>${row.beaconId}</th>
														<th>${row.sectorId}</th>
														<th>${row.distance}</th>
														<th class="btn-group"><a href="${row.beaconId}Remove"><button
																	class="btn btn-danger btn-mini">delete</button></a></th>
													</tr>
												</c:forEach>
										
											</c:when>
											<c:otherwise>
												<tr>
													<th colspan="6">NOT FOUND RESULT</th>
												</tr>
											</c:otherwise>
										</c:choose>
										
							
									</tbody>
								</table>

							</div>
							  <div class="block-content collapse in">
							<form  method="post" action="insertBeacon">
								<table class="table table-striped " >
									
												<tr>
												
													<th> </th>
													<th><input class="input-small focused" type="text"
														name="beaconId"></th>
													<th><input class="input-small" type="text"
														name="sectorId"></th>
													<th><input class="input-small focused" type="text"
														name="distance"></th>
													<th>
													<div class="btn-group">
													
                                         <button class="btn btn-success" type="submit">Add New <i class="icon-plus icon-white"></i></button></a>
                                  
                                      </div>
													</th>	
												</tr>
												 
								</table>
								</form>
								</div> 

						</div>
						<!-- /block -->
					</div>

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