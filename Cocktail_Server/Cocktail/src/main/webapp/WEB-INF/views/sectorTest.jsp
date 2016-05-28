<%@page import="com.knj.cocktail.domain.*"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>

<%

   request.setCharacterEncoding("UTF-8");

   List<Parameter> parameterList = (List<Parameter>) request.getAttribute("parameter");
   JSONObject jObject = new JSONObject();
   jObject.put("parameter", parameterList);

   out.println(jObject);

%>

