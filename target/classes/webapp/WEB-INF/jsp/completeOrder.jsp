<!DOCTYPE html>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />

<jsp:include page="generalMenu.jsp" />

<html>
<head>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<form action="/completeOrderAux" method="POST">
		<div class="container">
			<h1>Complete Order</h1>
			<p>Please review the order details</p>
			<hr>

			<label for="id"><b>Id</b></label> <input type="text"
				value="${order.id}" name="id" readonly> <label
				for="username"><b>Username</b></label> <input type="text"
				value="${order.customerUsername}" name="username" readonly>
			<label for="country"><b>Country</b></label> <input type="text"
				value="${order.address.country}" name="country" readonly> <label
				for="city"><b>City</b></label> <input type="text"
				value="${order.address.city}" name="city" readonly> <label
				for="street"><b>Street</b></label> <input type="text"
				value="${order.address.street}" name="street" readonly> <label
				for="number"><b>Number</b></label> <input type="text"
				value="${order.address.number}" name="number" readonly>
			<c:if test="${not empty order.address.apartment}">
				<label for="apartment"><b>Apartment</b></label>
				<input type="text" value="${order.address.apartment}"
					name="apartment" readonly>
			</c:if>
			<label for="zipCode"><b>ZipCode</b></label> <input type="text"
				value="${order.address.zipCode}" name="zipCode" readonly> <label
				for="createdDate"><b>Date</b></label> <input type="text"
				value="${order.createdDate}" name="createdDate" readonly>
		</div>
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

				<c:forEach var="listValue" items="${order.products}"
					varStatus="status">
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
		<button type="submit" class="registerbtn">Complete</button>
	</form>

</body>
</html>
