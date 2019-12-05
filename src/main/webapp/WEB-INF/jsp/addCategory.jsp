<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="generalMenu.jsp" />

<html>
<head>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<form action="/admin/addCategoryAux" method="POST">
		<div class="container">
			<h1>Create Category</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="name"><b>Name</b></label> <input type="text"
				placeholder="Enter Category Name" name="name" required>
			<hr>

			<button type="submit" class="registerbtn">Create</button>

		</div>
	</form>

</body>
</html>
