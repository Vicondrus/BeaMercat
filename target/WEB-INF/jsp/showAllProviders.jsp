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
				<th>Status</th>
				<th>Actions</th>
			</tr>

			<c:forEach var="listValue" items="${list}">
				<tr>
					<td>${listValue.name}</td>
					<td>${listValue.providerStatus}</td>
					<td><a href="/admin/viewProvider?name=${listValue.name}">Details</a><a>
							| </a><a href="/admin/updateProvider?name=${listValue.name}">Update</a><a>
							| </a><a href="/admin/deleteProvider?name=${listValue.name}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</body>
</html>