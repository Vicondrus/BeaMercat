<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="com.project.entities.Category"%>
<%@page import="com.project.entities.Status"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<html>
<head>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<form action="/addProductAux" method="POST">
		<div class="container">
			<h1>Create Product</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				placeholder="Enter Product Name" name="name" required> <label
				for="price"><b>Price</b></label> <input type="number" step="0.01"
				placeholder="Enter Product Price" name="price" required> <label
				for="details"><b>Details</b></label> <input type="text"
				placeholder="Enter Product Details" name="details"> <label
				for="stock"><b>Stock</b></label> <input type="number"
				placeholder="Enter Product Stock" name="stock" required> <label
				for="category"><b>Category</b></label> <select name="category"
				required>
				<%
					ArrayList<Category> list = new ArrayList<Category>();
					List<Category> aux = (List<Category>) request.getAttribute("categories");
					list.addAll(aux);
					for (Category a : list)
						if (a.getCategoryStatus().equals(Status.ACTIVE)) {
				%><option value="<%out.print(a.getName());%>">
					<%
						out.print(a.getName());
							}
					%>
				
			</select>
			<hr>

			<button type="submit" class="registerbtn">Create</button>

		</div>
	</form>

</body>
</html>
