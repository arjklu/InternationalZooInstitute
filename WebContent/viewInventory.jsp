<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The International Zoo Institute</title>
</head>
<script>
	function fieldReset() {
		document.getElementById("newItemsForm").reset();
	}
</script>
<body>
	<center>
		<h1>Welcome to International Zoo Institute!</h1>
		<hr />
		<h3>View Inventory Activities</h3>
	</center>
	<form id="viewInventoryForm" action="IZIServlet.do" method="get">
		<table align="center">
			<tr>
				<td>Animal</td>
				<td><input name="animalName"
					onkeydown='return event.keyCode >= 65 && event.keyCode <= 90' /></td>
			</tr>
			<tr>
				<td>Zoo Name</td>
				<td><input name="zooName" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
				<td><input type="reset" value="Reset" /></td>
			</tr>
		</table>
		<p></p>
		<center>
			<h4 style="font-family: tempus sans itc;">
				<a href="iziHome.jsp">Click here for getting back to home screen</a>
				<br />
			</h4>
		</center>
		<input type="hidden" name="formName" value="viewInventory">
	</form>
</body>
</html>