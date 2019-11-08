<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample Point</title>
</head>
<style type="text/css">
body {
	background-image:
		url('https://image.jimcdn.com/app/cms/image/transf/none/path/sc77e1e58a42c514a/image/i9977c7b3a6db203a/version/1516578954/image.jpg');
	background-size: cover;
	font: normal normal 16px quicksand; 
	letter-spacing: 1px;
	
    background-position: center;
    background-repeat: no-repeat;
    overflow-x: hidden;
    padding-top: 20px;
    z-index: 1;
    top: 0;
    left: 0;
    min-height: 100%;
    background-attachment: fixed;
    
}

h1 {
text-align: center;
padding: 13vh;
  font-weight: normal;
  font-size: 40px;
  font-family: 'Lobster';
  text-transform: uppercase;    
}

div {
	font-size: medium;
	font-family: Times New Roman;
	font-weight: bold;
}
button {
border: 2px solid #b5f2f2;  /*borde: estilo y color*/
font: normal normal 18px quicksand;  /*fuente*/
letter-spacing: 2px; /*separación entre las letras*/
text-align: center; /*alineación del texto*/
text-transform: uppercase; /*texto se transforma en mayúsculas*/
padding: 10px;  /*tamaño del fondo*/
border-radius: 30px 0px 30px 0px; /*ángulos de las 4 esquinas del borde/fondo*/
}
.link {
	margin: 0;
	border: 0;
	background: none;
	overflow: visible;
	color: blue;
	cursor: pointer;
}
.Nav {
	margin: auto;
	width: 50%;
	padding: 10px;
}
table, th, td {
  border: 1px solid black;
}
td {
	font-size:10px;
}
th {
	font-size:15px;
}
</style>
<body>
	<div class="Nav"><jsp:include page="NavBar.jsp" /></div><br>
	<h2 align="left">Sample Point: ${SP}</h2>
	<table>
		<tr><td>Location</td><td>${label}</td></tr>
		<tr><td>Easting</td><td>${easting}</td></tr>
		<tr><td>Northing</td><td>${northing}</td></tr>
	</table>
	<br>
	
	<h2 align="left">Measurements taken in the sample point:</h2>
	<table>
		<tr>
			<th>Measurement ID</th>
			<th>result</th>
			<th>material type</th>
			<th>purpose</th>
			<th>dateTime</th>
			<th>is compliance</th>
			<th>Determinand</th>
		</tr>
		<c:forEach items="${measurement_list}" var="measurement">
			<tr>
				<td>${ measurement.meas }</td>
				<td>${ measurement.result }</td>
				<td>${ measurement.materialType }</td>
				<td>${ measurement.purpose }</td>
				<td>${ measurement.dateTime }</td>
				<td>${ measurement.isCompliance }</td>
				<td>
					<form action="DeterminandServlet" method="get">
						<input type="hidden" name="notation" value="${ measurement.determinand.notation }" />
						<input type="submit" value="${ measurement.determinand.label }" class="link" />
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>