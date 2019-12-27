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

	<form action="/updateProviderAux" method="POST">
		<div class="container">
			<h1>Update Provider</h1>
			<p>Please review the details</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				value="${provider.name}" name="name" readonly> <label
				for="email"><b>Email</b></label> <input type="email"
				value="${provider.email}" name="email"> <label for="country"><b>Country</b></label>
			<input type="text" value="${provider.address.country}" name="country">
			<label for="city"><b>City</b></label> <input type="text"
				value="${provider.address.city}" name="city"> <label
				for="street"><b>Street</b></label> <input type="text"
				value="${provider.address.street}" name="street"> <label
				for="number"><b>Number</b></label> <input type="number"
				value="${provider.address.number}" name="number"> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				value="${provider.address.apartment}" name="apartment"> <label
				for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" value="${provider.address.zipCode}"
				name="zipCode"> <label for="telephone"><b>Telephone</b></label>
			<input type="text" value="${provider.telephone}"
				name="telephone"> <label for="providerStatus"><b>Status</b></label>
			<select name="providerStatus">
				<option value=<%out.print(Status.ACTIVE);%>
					<%if (((Provider) request.getAttribute("provider")).getProviderStatus().equals(Status.ACTIVE)) {
				out.print("selected");
			}%>>Active</option>
				<option value=<%out.print(Status.DELETED);%>
					<%if (((Provider) request.getAttribute("provider")).getProviderStatus().equals(Status.DELETED)) {
				out.print("selected");
			}%>>Deleted</option>
			</select>
			<hr>

			<button type="submit" class="registerbtn">Update</button>
		</div>
	</form>

</body>
</html>
