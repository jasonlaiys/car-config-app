
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Car Configuration </title>
</head>

<h1 align="center">
	Car Configuration Tool
</h1>

<h3 align="center">
	Your configuration is as follow
</h3>

<body>

	<%
		Automobile a1 = (Automobile) request.getSession().getAttribute("automobile");
		for (int i = 0; i < a1.getOptSetLength(); i++) {
			a1.setOptChoice(i, Integer.parseInt(request.getParameter(a1.getOptSetName(i))));
		}
	%>
		
	<table align="center" border="1">
		
		<thead align="center">
		
			<tr>
				<th> Option </th>
				<th> Selection </th>
				<th> Price </th>
			</tr>
			
		</thead>
		
		<tbody>
		
			<tr>
				<td align="left"> Automobile </td>
				<td align="left"><%= a1.getMakeModelYear() %></td>
				<td align="right"><%= a1.getBasePrice() %></td>
			</tr>
			
			<%
				for (int i = 0; i < a1.getOptSetLength(); i++) {
			%>
				
					<tr>
						<td align="left"><%= a1.getOptSetName(i) %></td>
						<td align="left"><%= a1.getOneChoiceName(i) %></td>
						<td align="right"><%= a1.getOneChoicePrice(i) %></td>
					</tr>
			
			<%
				}
			%>
			
			
		</tbody>
		
		<tfoot>
			
			<tr>
				<td align="left"> </td>
				<td align="left"> Total price: </td>
				<td align="right"><%= a1.getTotalChoicePrice() %></td>
			</tr>
			
		</tfoot>
	
	</table>	
	
</body>
</html>