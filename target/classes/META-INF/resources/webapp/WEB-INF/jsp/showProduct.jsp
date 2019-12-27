<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="generalMenu.jsp" />

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<c:url value="/css/productStyle.css" var="jstlCss" />
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

	<main class="container">
		<div class="left-column">
			<img alt="no image" height="auto" width="40"
				src="data:image/jpg;base64,${image}" />
		</div>

		<div class="right-column">

			<!-- Product Description -->
			<div class="product-description">
				<span>${product.category.name}</span>
				<h1>${product.name}</h1>
				<h3>${product.provider.name}</h3>
				<p>${product.details}</p>
			</div>

			<!-- Product Pricing -->
			<div class="product-price">
				<span>${product.price}$</span>
			</div>
			<div>
				<form action="/user/addToShoppingCart" method="POST">
					<label for="quantity"><b>Quantity</b></label> <input type="number"
						value=1 name="quantity" required>
						<input type="hidden" value="${product.id}" name="productId">
					<button type="submit" class="cart-btn">Add to Cart</button>
				</form>
			</div>
		</div>
	</main>
</body>
</html>
