<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="com.project.entities.Category"%>
<%@page import="com.project.entities.Status"%>
<%@page import="com.project.entities.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<jsp:include page="generalMenu.jsp" />

<html>
<head>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<form action="/admin/deleteProductAux" method="POST"
		enctype="multipart/form-data">
		<div class="container">
			<h1>Update Product</h1>
			<p>Please review the form below</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				value="${product.name}" name="name" readonly> <label
				for="price"><b>Price</b></label> <input type="number" step="0.01"
				value="${product.price}" name="price" readonly> <label
				for="details"><b>Details</b></label> <input type="text"
				value="${product.details}" name="details" readonly> <label
				for="stock"><b>Stock</b></label> <input type="number"
				value="${product.stock}" name="stock" readonly> <label
				for="category"><b>Category</b></label> <input type="text"
				value="${product.category.name}" name="category" readonly> <label
				for="provider"><b>Provider</b></label> <input type="text"
				value="${product.provider.name}" name="provider" readonly> <label
				for="productStatus"><b>Status</b></label> <input type="text"
				value="${product.productStatus}" name="productStatus" required>
			<label for="img"><b>Image</b></label> <img alt="no image" height="42"
				width="42" src="data:image/jpg;base64,${image}" />
			<hr>

			<button type="submit" class="red-btn">Delete</button>

		</div>
	</form>

</body>
</html>
