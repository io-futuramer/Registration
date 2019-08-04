<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
 
<head>
    <title>User Registration Form</title>
 	<link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="resources/css/common.css" rel="stylesheet"/>    
</head>
 
<body>

<div class="containerReg">
</div>
<div class="content">

	<form:form method="POST" modelAttribute="appuser" class="form-signin">
	
		<h2 class="form-signin-heading">Registration</h2>
		
		<spring:bind path="surname">          
			<form:input type="text" path="surname" class="form-control" placeholder="Surname"></form:input>
			<form:errors path="surname" cssClass="error"></form:errors>  			       
        </spring:bind>
        
        <spring:bind path="firstname">
			<form:input type="text" path="firstname" class="form-control" placeholder="Firstname"></form:input>
			<form:errors path="firstname" cssClass="error"></form:errors>
		</spring:bind>
		
		<br/>

		<div class="form-group">
			<div class="btn-group" data-toggle="buttons">
				<label class="btn btn-default ${requestScope['appuser'].gender == 'male'? 'active':''}">
					<input type="radio" name="gender" value="male" ${requestScope['appuser'].gender == 'male'? 'checked':''}/> Male
				</label>
				<label class="btn btn-default ${requestScope['appuser'].gender == 'female'? 'active':''}">
					<input type="radio" name="gender" value="female" ${requestScope['appuser'].gender == 'female'? 'checked':''}/> Female
				</label>
				<br/>
					<form:errors path="gender" cssClass="error"></form:errors>
			</div>
		</div>
				     
		<spring:bind path="birthdate">
			<form:input data-date-format="dd.MM.yyyy" type="text" path="birthdate" class="form-control" placeholder="Date of birth (dd.mm.yyyy)"></form:input>
			<form:errors path="birthdate" cssClass="error"></form:errors>
		</spring:bind>     

        <spring:bind path="email">
			<form:input type="text" path="email" class="form-control" placeholder="Email"></form:input>
			<form:errors path="email" cssClass="error"></form:errors>
        </spring:bind>
        
        <spring:bind path="username">
			<form:input type="text" path="username" class="form-control" placeholder="Username"></form:input>
			<form:errors path="username" cssClass="error"></form:errors>
        </spring:bind>

        <spring:bind path="password">		
			<form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
			<form:errors path="password" cssClass="error"></form:errors>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>         
	</form:form>
    
</div>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>

</html>
