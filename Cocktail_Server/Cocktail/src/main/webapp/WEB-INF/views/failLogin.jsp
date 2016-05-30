<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	    <% 
	String adminId = (String)session.getAttribute("adminLoginId");
	if(adminId == null){
%> 
	<script>
	  alert("로그인 되지 않았습니다.");
	     location.href="login";
	</script>
<%	}	%>

</body>
</html>