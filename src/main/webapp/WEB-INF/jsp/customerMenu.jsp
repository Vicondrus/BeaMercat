<!DOCTYPE html>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="principal" />
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
html, body, h1, h2, h3, h4, h5 {
	font-family: "Raleway", sans-serif
}

.navbar {
	overflow: hidden;
	background-color: #333;
}

.navbar a {
	float: left;
	font-size: 16px;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

.navbar b {
	float: left;
	display: block;
	margin-left: auto;
	margin-right: auto;
	padding: 10px 0px;
}

c {
	float: right;
	font-size: 16px;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

.dropdown {
	float: left;
	overflow: hidden;
}

.dropdown .dropbtn {
	font-size: 16px;
	border: none;
	outline: none;
	color: white;
	padding: 14px 16px;
	background-color: inherit;
	font-family: inherit;
	margin: 0;
}

.navbar a:hover, .dropdown:hover .dropbtn {
	background-color: #66cc66;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	float: none;
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #53c553;
	color: white;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.navbar .login-container {
	float: right;
}

.navbar .login-container button {
	float: right;
	padding: 6px;
	margin-top: 8px;
	margin-right: 16px;
	background: #333;
	font-size: 17px;
	border: none;
	cursor: pointer;
	color: white;
}

}
.navbar .login-container button:hover {
	background: #66cc66;
	color: white;
}

.navbar .login-container c {
	float: right;
	font-size: 16px;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}
</style>
</head>
<body>

	<div class="navbar">
		<b><img src="/forest.png" alt="HTML5 Icon"
			style="width: 32px; height: 32px;"></b> <a href="home">Home</a>
		<div class="dropdown">
			<button class="dropbtn">
				User <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="/user/viewUser">View</a>
				<a href="/user/updateUser">Update</a>
			</div>
		</div>
		<div class="dropdown">
			<button class="dropbtn">
				Products <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="/user/browseProducts">Browse</a> <a href="/user/searchProduct">Search</a>
				<a href="/user/searchByCategory">Search by Category</a>
			</div>
		</div>
		<div class="dropdown">
			<button class="dropbtn">
				Orders <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="/user/listOrders">View</a>
			</div>
		</div>
		<a href="/user/viewShoppingCart">Shopping Cart</a>
		<div class="login-container">
			<form action="/logout">
				<button type="submit">Sign out</button>
				<c>${principal.username}</c>
				<c id="date"></c>
			</form>
		</div>
	</div>

	<script>
		var d = new Date();
		document.getElementById("date").innerHTML = d.toDateString();
	</script>

</body>
</html>
