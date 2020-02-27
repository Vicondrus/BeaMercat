<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="com.project.entities.Category"%>
<%@page import="com.project.entities.Status"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<jsp:include page="generalMenu.jsp" />

<html>
<head>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<form action="/admin/addProductAux" method="POST"
		enctype="multipart/form-data">
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
					ArrayList<String> list1 = new ArrayList<String>();
					List<String> aux1 = (List<String>) request.getAttribute("categories");
					list1.addAll(aux1);
					for (String a : list1) {
				%><option value="<%out.print(a);%>">
					<%
						out.print(a);
						}
					%>
				
			</select> <label for="provider"><b>Provider</b></label> <select
				name="provider" required>
				<%
					ArrayList<String> listw = new ArrayList<String>();
					List<String> auxw = (List<String>) request.getAttribute("providers");
					listw.addAll(auxw);
					for (String a : listw) {
				%><option value="<%out.print(a);%>">
					<%
						out.print(a);
						}
					%>
				
			</select>
			<hr>

			<label for="image"><b>Image</b></label> <input type="file"
				accept="image/*" placeholder="Enter Product Name" name="image">

			<button type="submit" class="registerbtn">Create</button>

		</div>
	</form>

</body>
</html>
