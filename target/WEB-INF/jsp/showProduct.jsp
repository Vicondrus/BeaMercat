<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<jsp:include page="generalMenu.jsp" />

<sec:authentication property="principal" var="principal" />
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
			<img alt="no image" height="500" width="40"
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
						value=1 name="quantity" required> <input type="hidden"
						value="${product.id}" name="productId">
					<button type="submit" class="cart-btn">Add to Cart</button>
				</form>
			</div>
		</div>
	</main>

	<div style="bottom: 0">
		<c:if test="${not empty custReview}">
			<form action="/user/modifyReview" method="POST">
				<label for="mark"><b>Mark</b></label> <input type="number"
					value="${custReview.mark }" name="mark" required> <label
					for="details"><b>Details</b></label> <input type="text"
					value="${custReview.details}" name="details"> <input
					type="hidden" name="productId" value="${product.id }"> <input
					type="hidden" name="id" value="${custReview.id}">
				<button type="submit" class="registerbtn">Update Review</button>
			</form>
			<form action="/user/deleteReview" method="POST">
				<input type="hidden" name="id" value="${custReview.id}"> <input
					type="hidden" name="productId" value="${product.id }">
				<button type="submit" class="red-btn">Delete Review</button>
			</form>
		</c:if>

		<c:if test="${empty custReview}">
			<form action="/user/addReview" method="POST">
				<label for="mark"><b>Mark</b></label> <input type="number"
					placeholder="Asses quality using a mark (1-10)" name="mark"
					required> <label for="details"><b>Details</b></label> <input
					type="text" placeholder="Give some details about your experience"
					name="details"> <input type="hidden" name="productId"
					value="${product.id }"> <input type="hidden"
					name="reviewerUsername" value="${principal.username}">
				<button type="submit" class="registerbtn">Review</button>
			</form>
		</c:if>

		<c:if test="${not empty reviews}">
			<table style="width: 100%" id="myTable">
				<tr class="header">
					<th>User</th>
					<th>Mark</th>
					<th>Details</th>
				</tr>

				<c:forEach var="listValue" items="${reviews}" varStatus="status">
					<tr>
						<td>${listValue.reviewerUsername}</td>
						<td>${listValue.mark}</td>
						<td>${listValue.details}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

	</div>


</body>
</html>
