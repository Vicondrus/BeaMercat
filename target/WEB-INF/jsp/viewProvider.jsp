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
	<h1>Provider Details</h1>
	<ul style="list-style-type: none;">
		<li>Name: ${provider.name}</li>
		<li>Email: ${provider.email}</li>
		<li>Country: ${provider.address.country}</li>
		<li>City: ${provider.address.city}</li>
		<li>Street: ${provider.address.street} No. ${provider.address.number} <c:if test="${not empty provider.address.apartment}">Ap. ${provider.address.apartment}</c:if></li>
		<li>ZipCode: ${provider.address.zipCode}</li>
		<li>Telephone: ${provider.telephone}</li>
	</ul>


</body>
</html>