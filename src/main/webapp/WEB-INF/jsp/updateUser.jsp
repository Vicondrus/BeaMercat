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

	<form action="/admin/updateUserAux" method="POST">
		<div class="container">
			<h1>Update User</h1>
			<p>Please review your details</p>
			<hr>

			<label for="username"><b>Username</b></label> <input type="text"
				value="${user.username}" name="username" readonly> <label
				for="password"><b>Password</b></label> <input type="password"
				placeholder="Enter New Password" name="password"> <label
				for="firstName"><b>First Name</b></label> <input type="text"
				value="${user.firstName}" name="firstName"> <label
				for="lastName"><b>Last Name</b></label> <input type="text"
				value="${user.lastName}" name="lastName"> <label for="email"><b>Email</b></label>
			<input type="email" value="${user.email}" name="email"> <label
				for="country"><b>Country</b></label> <input type="text"
				value="${user.address.country}" name="country"> <label
				for="city"><b>City</b></label> <input type="text"
				value="${user.address.city}" name="city"> <label
				for="street"><b>Street</b></label> <input type="text"
				value="${user.address.street}" name="street"> <label
				for="number"><b>Number</b></label> <input type="number"
				value="${user.address.number}" name="number"> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				value="${user.address.apartment}" name="apartment"> <label
				for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" value="${user.address.zipCode}" name="zipCode">
			<label for="telephone"><b>Telephone</b></label> <input type="text"
				pattern="+[0-9]{11}" value="${user.telephone}" name="telephone">


			<c:if test="${isAdmin}">
				<label for="type"><b>Rights</b></label>
				<select name="type">
					<option value=<%out.print(UserType.ADMIN);%>
						<%if (((User) request.getAttribute("user")).getUserType().equals(UserType.ADMIN)) {
					out.print("selected");
				}%>>Administrator</option>
					<option value=<%out.print(UserType.CUSTOMER);%>
						<%if (((User) request.getAttribute("user")).getUserType().equals(UserType.CUSTOMER)) {
					out.print("selected");
				}%>>Customer</option>
				</select>
				<label for="type"><b>Status</b></label>
				<select name="status">
					<option value=<%out.print(Status.ACTIVE);%>
						<%if (((User) request.getAttribute("user")).getUserStatus().equals(Status.ACTIVE)) {
					out.print("selected");
				}%>>Active</option>
					<option value=<%out.print(Status.DELETED);%>
						<%if (((User) request.getAttribute("user")).getUserStatus().equals(Status.ACTIVE)) {
					out.print("selected");
				}%>>Deleted</option>
				</select>
			</c:if>
			<hr>

			<button type="submit" class="registerbtn">Update</button>
		</div>
	</form>

</body>
</html>
