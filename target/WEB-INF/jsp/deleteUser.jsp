<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@page import="com.project.entities.User"%>
<%@page import="com.project.entities.UserType"%>
<%@page import="com.project.entities.Status"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />


<c:choose>
	<c:when test="${isAdmin}">
		<jsp:include page="adminMenu.jsp" />
	</c:when>
	<c:when test="${isUser}">
		<jsp:include page="customerMenu.jsp" />

	</c:when>
	<c:otherwise>
		<meta http-equiv="refresh" content="0; url=http://www.ecosia.com/">
		</head>
	</c:otherwise>
</c:choose>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />


<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<form action="/deleteUserAux" method="POST">
		<div class="container">
			<h1>Update User</h1>
			<p>Please review your details</p>
			<hr>

			<label for="username"><b>Username</b></label> <input type="text"
				value="${user.username}" name="username" readonly> <label
				for="password"><b>Password</b></label> <input type="password"
				placeholder="Enter New Password" name="password" readonly> <label
				for="firstName"><b>First Name</b></label> <input type="text"
				value="${user.firstName}" name="firstName" readonly> <label
				for="lastName"><b>Last Name</b></label> <input type="text"
				value="${user.lastName}" name="lastName" readonly> <label
				for="email"><b>Email</b></label> <input type="email"
				value="${user.email}" name="email" readonly> <label
				for="country"><b>Country</b></label> <input type="text"
				value="${user.address.country}" name="country" readonly> <label
				for="city"><b>City</b></label> <input type="text"
				value="${user.address.city}" name="city" readonly> <label
				for="street"><b>Street</b></label> <input type="text"
				value="${user.address.street}" name="street" readonly> <label
				for="number"><b>Number</b></label> <input type="number"
				value="${user.address.number}" name="number" readonly> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				value="${user.address.apartment}" name="apartment" readonly>
			<label for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" value="${user.address.zipCode}" name="zipCode"
				readonly> <label for="telephone"><b>Telephone</b></label> <input
				type="text" pattern="+[0-9]{11}" value="${user.telephone}"
				name="telephone" readonly> <label for="userStatus"><b>Status</b></label>
			<input type="text" value="${user.userStatus}" name="userStatus"
				readonly> <label for="userType"><b>Telephone</b></label> <input
				type="text" value="${user.userType}" name="userType" readonly>

			<hr>

			<button type="submit" class="red-btn">Delete</button>
		</div>
	</form>

</body>
</html>
