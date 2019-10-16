<!DOCTYPE html>

<html>
<head>
<style>
html, body, h1, h2, h3, h4, h5 {
	font-family: "Raleway", sans-serif
}
/* Full-width input fields */
input[type=text], input[type=password], input[type=date], select, input[type=email],
	input[type=month], input[type=number] {
	width: 100%;
	padding: 15px;
	margin: 5px 0 22px 0;
	display: inline-block;
	border: none;
	background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus, input[type=date]:focus,
	select:focus, input[type=email]:focus, input[type=month]:focus, input[type=number]:focus
	{
	background-color: #ddd;
	outline: none;
}

/* Overwrite default styles of hr */
hr {
	border: 1px solid #f1f1f1;
	margin-bottom: 25px;
}

/* Set a style for the submit button */
.registerbtn {
	background-color: #4CAF50;
	color: white;
	padding: 16px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
	opacity: 0.9;
}

.registerbtn:hover {
	opacity: 1;
}
</style>
</head>
<body>

	<form action="/addAux" method="POST">
		<div class="container">
			<h1>Create User</h1>
			<p>Please complete the form below</p>
			<hr>

			<label for="username"><b>Username</b></label> <input type="text"
				placeholder="Enter Username" name="username" required> <label
				for="firstName"><b>First Name</b></label> <input type="text"
				placeholder="Enter First Name" name="firstName"> <label
				for="lastName"><b>Last Name</b></label> <input type="text"
				placeholder="Enter Last Name" name="lastName"> <label
				for="password"><b>Password</b></label> <input type="password"
				placeholder="Enter Password" name="password" required> <label
				for="email"><b>Email</b></label> <input type="email"
				placeholder="Enter Email" name="email" required> <label
				for="country"><b>Country</b></label> <input type="text"
				placeholder="Enter Country" name="country" required> <label
				for="city"><b>City</b></label> <input type="text"
				placeholder="Enter City" name="city" required> <label
				for="street"><b>Street</b></label> <input type="text"
				placeholder="Enter Street Name" name="street" required> <label
				for="number"><b>Number</b></label> <input type="number"
				placeholder="Enter Street Number" name="number" required> <label
				for="Apartment"><b>Apartment</b></label> <input type="number"
				placeholder="Enter Apartment (if case)" name="apartment"> <label
				for="zipCode"><b>ZipCode</b></label> <input type="text"
				pattern="[0-9]{6}" placeholder="Enter ZipCode" name="zipCode"
				required> <label for="telephone"><b>Telephone</b></label> <input
				type="text" pattern="+[0-9]{11}"
				placeholder="Enter Telephone Number (e.g. +40123456789)"
				name="telephone" required>
			<hr>

			<button type="submit" class="registerbtn">Create</button>

		</div>
	</form>

</body>
</html>
