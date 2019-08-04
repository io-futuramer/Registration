<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration Confirmation Page</title>
 	<link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="resources/css/common.css" rel="stylesheet"/>
</head>

<body>
     <div class="container">
     </div>
	<div class="content" style="text-align: center">
		<div class="jumbotron">
    		<h4 class="form-heading">${success}</h4>
    		<br/>
    		<br/>
    		<h4 class="form-signin-heading">Go back to <a href="<c:url value='/registration' />">Registration</a></h4>
		</div>
	</div>   
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>  
</body>

</html>
