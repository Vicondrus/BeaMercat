<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

	<form action="/admin/addProviderAux" method="POST">
		<div class="container">
			<h1>Create Provider</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				placeholder="Enter Category Name" name="name" required> <label
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
			<hr>

			<button type="submit" class="registerbtn">Create</button>

		</div>
	</form>

</body>
</html>
