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

	<form action="/updateCategoryAux" method="POST"
		enctype="multipart/form-data">
		<div class="container">
			<h1>Update Category</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				value="${category.name}" name="name" readonly> <label
				for="categoryStatus"><b>Status</b></label> <select
				name="categoryStatus">
				<option value=<%out.print(Status.ACTIVE);%>
					<%if (((Category) request.getAttribute("category")).getCategoryStatus().equals(Status.ACTIVE)) {
				out.print("selected");
			}%>>Active</option>
				<option value=<%out.print(Status.DELETED);%>
					<%if (((Category) request.getAttribute("category")).getCategoryStatus().equals(Status.DELETED)) {
				out.print("selected");
			}%>>Deleted</option>
			</select>
			<hr>

			<button type="submit" class="registerbtn">Update</button>

		</div>
	</form>

</body>
</html>
