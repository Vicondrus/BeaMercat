<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />

<%@page import="java.util.ArrayList"%>

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

	<form action="/user/searchByProviderAux" method="POST">
		<label for="prov"> <b>Provider</b></label> <select name="prov" required>
			<%
				ArrayList<String> clist = (ArrayList<String>) request.getAttribute("provs");
				for (String u : clist) {
			%>
			<option value="<%out.print(u);%>">
				<%
					out.print(u);
					}
				%>
			</option>
		</select>
		<button type="submit" class="registerbtn">Search</button>
	</form>

	<c:if test="${not empty list}">
		<h1>${list[0].provider.name}</h1>
		<table style="width: 100%" id="myTable">
			<tr class="header">
				<th>Image</th>
				<th>Name</th>
				<c:if test="${isUser}">
					<th>Price</th>
				</c:if>
				<c:if test="${isAdmin}">
					<th>Stock</th>
				</c:if>
				<th>Category</th>
				<th>Provider</th>
				<c:if test="${isAdmin}">
					<th>Status</th>
				</c:if>
				<th>Actions</th>
			</tr>

			<c:forEach var="listValue" items="${list}" varStatus="status">
				<tr>
					<td><img alt="no image" height="42" width="42"
						src="data:image/jpg;base64,${images[status.index]}" /></td>
					<td>${listValue.name}</td>
					<c:if test="${isUser}">
						<td>${listValue.price}</td>
					</c:if>
					<c:if test="${isAdmin}">
						<td>${listValue.stock}</td>
					</c:if>
					<td>${listValue.category.name}</td>
					<td>${listValue.provider.name}</td>
					<c:if test="${isAdmin}">
						<td>${listValue.productStatus}</td>
					</c:if>
					<td><c:if test="${isUser}">
							<a href="/user/viewProduct?id=${listValue.id}">Details</a>
						</c:if> <c:if test="${isAdmin}">
							<a href="/admin/viewProduct?id=${listValue.id}">Details</a>
							<a> | </a>
							<a href="/admin/updateProduct?id=${listValue.id}">Update</a>
							<a> | </a>
							<a href="/admin/deleteProduct?id=${listValue.id}">Delete</a>
						</c:if>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</body>
</html>