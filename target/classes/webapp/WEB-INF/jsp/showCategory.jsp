<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://fonts.googleapis.com/css?family=Raleway"
	rel="stylesheet" type="text/css">
<c:url value="/css/formNTable.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

<head>

<jsp:include page="generalMenu.jsp" />

</head>

<body>
	<h1>Category Details</h1>
	<ul style="list-style-type: none;">
		<li>Name: ${category.name}</li>
	</ul>

</body>
</html>