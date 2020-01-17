<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@page import="com.project.entities.Provider"%>
<%@page import="com.project.entities.Status"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />


<jsp:include page="generalMenu.jsp" />

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

	<form action="/deleteProviderAux" method="POST">
		<div class="container">
			<h1>Delete Provider</h1>
			<p>Please review the details</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				value="${provider.name}" name="name" readonly> <label
				for="email"><b>Email</b></label> <input type="email"
				value="${provider.email}" name="email" readonly> <label
				for="country"><b>Country</b></label> <input type="text"
				value="${provider.address.country}" name="country" readonly>
			<label for="city"><b>City</b></label> <input type="text"
				value="${provider.address.city}" name="city" readonly> <label
				for="street"><b>Street</b></label> <input type="text"
				value="${provider.address.street}" name="street" readonly> <label
				for="number"><b>Number</b></label> <input type="number"
				value="${provider.address.number}" name="number" readonly> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				value="${provider.address.apartment}" name="apartment" readonly>
			<label for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" value="${provider.address.zipCode}"
				name="zipCode" readonly> <label for="telephone"><b>Telephone</b></label>
			<input type="text" value="${provider.telephone}" name="telephone"
				readonly> <label for="providerStatus"><b>Status</b></label>
			<input type="text" value="${provider.providerStatus}"
				name="providerStatus" readonly>
			<hr>

			<button type="submit" class="red-btn">Delete</button>
		</div>
	</form>

</body>
</html>
