<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://fonts.googleapis.com/css?family=Raleway"
	rel="stylesheet" type="text/css">
<c:url value="/css/formNTable.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

<head>

<jsp:include page="generalMenu.jsp" />

</head>

<body>
	<h1>User Details</h1>
	<ul style="list-style-type: none;">
		<li>Username: ${user.username}</li>
		<li>Name: ${user.firstName}</li>
		<li>Name: ${user.lastName}</li>
		<li>Email: ${user.email}</li>
		<li>Country: ${user.address.country}</li>
		<li>City: ${user.address.city}</li>
		<li>Street: ${user.address.street} No. ${user.address.number} <c:if test="${not empty user.address.apartment}">Ap. ${user.address.apartment}</c:if></li>
		<li>ZipCode: ${user.address.zipCode}</li>
		<li>Telephone: ${user.telephone}</li>
		
	</ul>


</body>
</html>