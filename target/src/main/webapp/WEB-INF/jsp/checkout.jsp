<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html lang="en">
<meta charset="UTF-8">

<jsp:include page="generalMenu.jsp" />

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />


<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

<head>

</head>

<body>
	<c:if test="${not empty list}">
		<table style="width: 100%" id="myTable">
			<tr class="header">
				<th>Name</th>
				<th>Quantity</th>
				<th>Price</th>
				<th>Total</th>
			</tr>

			<c:forEach var="listValue" items="${list}" varStatus="status">
				<tr>
					<td>${listValue.product.name}</td>
					<td>${listValue.quant}</td>
					<td>${listValue.product.price}</td>
					<td>${listValue.quant * listValue.product.price}</td>
				</tr>
			</c:forEach>
		</table>
		<h2>Total: ${total}</h2>
		<br>
		<br>
		<h3>Please complete the form below</h3>
		<h4>in case the products should be delivered anywhere else than the user's address</h4>
		<form action="/user/checkoutAux" method=POST>
			<label for="country"><b>Country</b></label> <input type="text"
				placeholder="Enter Country" name="country"> <label
				for="city"><b>City</b></label> <input type="text"
				placeholder="Enter City" name="city"> <label for="street"><b>Street</b></label>
			<input type="text" placeholder="Enter Street Name" name="street">
			<label for="number"><b>Number</b></label> <input type="number"
				placeholder="Enter Street Number" name="number"> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				placeholder="Enter Apartment (if case)" name="apartment"> <label
				for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" placeholder="Enter ZipCode" name="zipCode">
			<button class="registerbtn" type="submit">Order</button>
		</form>
	</c:if>


</body>
</html>