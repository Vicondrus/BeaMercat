<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />


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
				<th>Id</th>
				<c:if test="${isAdmin}">
					<th>Customer</th>
				</c:if>
				<th>Status</th>
				<th>Created</th>
				<th>Actions</th>
			</tr>

			<c:forEach var="listValue" items="${list}" varStatus="status">
				<tr>
					<td>${listValue.id}</td>
					<c:if test="${isAdmin}">
						<td>${listValue.customerUsername}</td>
					</c:if>
					<td>${listValue.status}</td>
					<td>${listValue.createdDate}</td>
					<td><c:if test="${isUser}">
							<a href="/user/viewOrder?id=${listValue.id}">Details</a>
						</c:if> <c:if test="${isAdmin}">
							<a href="/admin/viewOrder?id=${listValue.id}">Details</a>
							<a> | </a>
							<a href="/admin/updateOrder?id=${listValue.id}">Update</a>
							<a> | </a>
							<a href="/admin/cancelOrder?id=${listValue.id}">Cancel</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</body>
</html>