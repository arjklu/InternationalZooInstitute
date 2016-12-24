<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The International Zoo Institute</title>
</head>
<body>
	<center>
		<h1>Welcome to International Zoo Institute!</h1>
		<hr />
		<h3>View Inventory Activities</h3>
	</center>
	<form id="viewInventoryForm1">
		<table align="center">
			<tr>
				<td>Animal</td>
				<td>
					<%
						String aName = (String) request.getAttribute("animalName");
					%> <input name="animalName" value=<%=aName%> readonly="readonly"
					disabled="disabled" />
				</td>
			</tr>
			<tr>
				<td>Number of times fed</td>
				<td>
					<%
						String fTime = (String) request.getAttribute("feedTimes");
					%> <input name="feedTimes" readonly="readonly" disabled="disabled"
					value=<%=fTime%> />/day
				</td>
			</tr>
			<tr>
				<td>Zoo Name</td>
				<td>
					<%
						String zName = (String) request.getAttribute("zooName");
					%> <input name="quantityFed" readonly="readonly"
					disabled="disabled" value=<%=zName%> />
				</td>
			</tr>
			<tr>
				<td>Average Quantity fed</td>
				<td>
					<%
						String qFed = (String) request.getAttribute("quantityFed");
					%> <input name="quantityFed" readonly="readonly"
					disabled="disabled" value=<%=qFed%> />lbs /day
				</td>
			</tr>
			<tr>
				<td>Food Wastage</td>
				<td>
					<%
						String fWaste = (String) request.getAttribute("foodWaste");
					%> <input name="foodWaste" readonly="readonly" disabled="disabled"
					value=<%=fWaste%> />lbs /day
				</td>
			</tr>
		</table>
		<p></p>
		<center>
			<%
				String pDiff = (String) request.getAttribute("percentDiff");
			%>
			<p>
				This animal is being fed by
				<%=pDiff%>
				than the average.
			</p>
		</center>
		<p></p>
		<center>
			<h4 style="font-family: tempus sans itc;">
				<a href="iziHome.jsp">Click here for getting back to home screen</a>
				<br />
			</h4>
		</center>
	</form>
</body>
</html>