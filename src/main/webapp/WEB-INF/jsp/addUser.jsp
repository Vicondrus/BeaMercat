<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@page import="com.project.entities.User"%>
<%@page import="com.project.entities.UserType"%>
<%@page import="com.project.entities.Status"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />

<jsp:include page="generalMenu.jsp" />
<html>
<head>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>


	<c:if test="${param.error != null}">
		<div class="alert">
			<span class="closebtn"
				onclick="this.parentElement.style.display='none';">&times;</span>
			${param.error}
		</div>
	</c:if>

	<form action="/addUserAux" method="POST">
		<div class="container">
			<h1>Create User</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="username"><b>Username</b></label> <input type="text"
				placeholder="Enter Username" name="username" required> <label
				for="firstName"><b>First Name</b></label> <input type="text"
				placeholder="Enter First Name" name="firstName"> <label
				for="lastName"><b>Last Name</b></label> <input type="text"
				placeholder="Enter Last Name" name="lastName"> <label
				for="password"><b>Password</b></label> <input type="password"
				placeholder="Enter Password" name="password" required> <label
				for="email"><b>Email</b></label> <input type="email"
				placeholder="Enter Email" name="email" required> <label
				for="country"><b>Country</b></label> <input type="text"
				placeholder="Enter Country" name="country" required> <label
				for="city"><b>City</b></label> <input type="text"
				placeholder="Enter City" name="city" required> <label
				for="street"><b>Street</b></label> <input type="text"
				placeholder="Enter Street Name" name="street" required> <label
				for="number"><b>Number</b></label> <input type="number"
				placeholder="Enter Street Number" name="number" required> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				placeholder="Enter Apartment (if case)" name="apartment"> <label
				for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" placeholder="Enter ZipCode" name="zipCode"
				required> <label for="telephone"><b>Telephone</b></label> <input
				type="text" pattern="+[0-9]{11}"
				placeholder="Enter Telephone Number (e.g. +40123456789)"
				name="telephone" required>
			<c:if test="${isAdmin}">
				<label for="userType"><b>Rights</b></label>
				<select name="userType">
					<option value=<%out.print(UserType.ADMIN);%>>Administrator</option>
					<option value=<%out.print(UserType.CUSTOMER);%>>Customer</option>
					<option value=<%out.print(UserType.COURIER);%>>Courier</option>
				</select>
			</c:if>
			<hr>

			<button type="submit" class="registerbtn">Create</button>

		</div>
	</form>

</body>
</html>
