<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Student Tracker</title>
	<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Foo Bar University</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
		
		<!-- put new button:Add Student -->
		<input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" 
			class="add-student-button" />
			<table>
				<tr>
					<th>FirstName</th>
					<th>LastName</th>
					<th>Email Address</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempStudentlist" items="${STUDENT_DATA}">
				
					<!--  Used to populate data so that it is used in update form -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${tempStudentlist.id}" />
					</c:url>
					
					<!--  Used to delete data from our Application -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${tempStudentlist.id}" />
					</c:url>
					
					<tr>
						<td>${tempStudentlist.firstName}</td>
						<td>${tempStudentlist.lastName}</td>
						<td>${tempStudentlist.emailAddress}</td>
						<td>
							<a href="${tempLink}">Update</a>
							|
							<a href="${deleteLink}" onclick="if (!(confirm('Are you sure want to delete the data?'))) return false">delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>	
</body>
</html>