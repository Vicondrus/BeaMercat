<!DOCTYPE html>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />

<c:choose>
	<c:when test="${isAdmin}">
		<jsp:include page="adminMenu.jsp" />
	</c:when>
	<c:when test="${isUser}">
		<jsp:include page="customerMenu.jsp" />

	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>