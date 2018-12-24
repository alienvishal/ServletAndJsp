<html>
<head>
	<title>Update student</title>
	<link type="text/css" rel="stylesheet" href="css/style.css" />
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Foo Bar University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Student</h3>
		
		<form action="StudentControllerServlet" method="get">
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="id" value="${STUDENT_ID.id}" />
			<table>
				<tbody>
					<tr>
						<td><label>First Name:</label></td>
						<td><input type="text" name="firstname" value="${STUDENT_ID.firstName}" /></td>
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><input type="text" name="lastname" value="${STUDENT_ID.lastName}" /></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="email" name="email" value="${STUDENT_ID.emailAddress}" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Update" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<div style="clear:both;"></div>
		
		<p>
			<a href="StudentControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>