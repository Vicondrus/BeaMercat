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
	<h1>Order Details</h1>
	<ul style="list-style-type: none;">
		<li>Id: ${order.id}</li>
		<li>Customer Username: ${order.customerUsername}</li>
		<li>Customer Full Name: ${order.customer.firstName}
			${order.customer.lastName}</li>
		<li>Country: ${order.address.country}</li>
		<li>City: ${order.address.city}</li>
		<li>Street: ${order.address.street} No. ${order.address.number} <c:if
				test="${not empty order.address.apartment}">Ap. ${order.address.apartment}</c:if></li>
		<li>ZipCode: ${order.address.zipCode}</li>
		<li>Status: ${order.status}</li>
		<li>Courier: ${order.courierName}</li>
		<li>Created: ${order.createdDate}</li>
	</ul>
	<br>
	<h3>Products</h3>
	<c:if test="${not empty order.products}">
		<table style="width: 100%" id="myTable">
			<tr class="header">
				<th>Name</th>
				<th>Quantity</th>
				<th>Price</th>
				<th>Total</th>
			</tr>

			<c:forEach var="listValue" items="${order.products}" varStatus="status">
				<tr>
					<td>${listValue.product.name}</td>
					<td>${listValue.quant}</td>
					<td>${listValue.product.price}</td>
					<td>${listValue.quant * listValue.product.price}</td>
				</tr>
			</c:forEach>
		</table>
		<h2>Total: ${order.total}</h2>
	</c:if>

</body>
</html>