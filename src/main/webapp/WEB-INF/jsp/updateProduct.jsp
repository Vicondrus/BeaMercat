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

	<form action="/admin/updateProductAux" method="POST"
		enctype="multipart/form-data">
		<div class="container">
			<h1>Update Product</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				value="${product.name}" name="name" readonly> <label
				for="price"><b>Price</b></label> <input type="number" step="0.01"
				value="${product.price}" name="price" required> <label
				for="details"><b>Details</b></label> <input type="text"
				value="${product.details}" name="details"> <label
				for="stock"><b>Stock</b></label> <input type="number"
				value="${product.stock}" name="stock" required> <label
				for="category"><b>Category</b></label> <select name="category"
				required>
				<%
					Product product = ((Product) request.getAttribute("product"));
					ArrayList<String> list1 = new ArrayList<String>();
					List<String> aux1 = (List<String>) request.getAttribute("categories");
					list1.addAll(aux1);
					for (String a : list1) {
				%><option value="<%out.print(a);%>"
					<%if (product.getCategory().getName().equals(a)) {
					out.print("selected");
				}%>>
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
				%><option value="<%out.print(a);%>"
					<%if (product.getProvider().getName().equals(a)) {
					out.print("selected");
				}%>>
					<%
						out.print(a);
						}
					%>
				
			</select> <label for="productStatus"><b>Status</b></label> <select
				name="productStatus">
				<option value=<%out.print(Status.ACTIVE);%>
					<%if (((Product) request.getAttribute("product")).getProductStatus().equals(Status.ACTIVE)) {
				out.print("selected");
			}%>>Active</option>
				<option value=<%out.print(Status.DELETED);%>
					<%if (((Product) request.getAttribute("product")).getProductStatus().equals(Status.DELETED)) {
				out.print("selected");
			}%>>Deleted</option>
			</select> <label for="img"><b>Image</b></label> <img alt="no image"
				height="42" width="42" src="data:image/jpg;base64,${image}" /> <input
				type="file" accept="image/*" name="img">

			<hr>

			<button type="submit" class="registerbtn">Update</button>

		</div>
	</form>

</body>
</html>
