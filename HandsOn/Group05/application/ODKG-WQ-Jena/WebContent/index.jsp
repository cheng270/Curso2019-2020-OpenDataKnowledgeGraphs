<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Water Quality</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Temas-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<style type="text/css">
body {
	background-image:
		url('https://image.jimcdn.com/app/cms/image/transf/none/path/sc77e1e58a42c514a/image/i9977c7b3a6db203a/version/1516578954/image.jpg');
	background-size: cover;
	font: normal normal 16px quicksand; 
	letter-spacing: 1px;
	
	text-align: center;
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
.btn-register > a{
color: #fff
}
/**
 * se aplica el ancho, margen centrado
 * borde de un pixel con redondeado, y rellenado
 * a la izquierda y derecha
 */
#Contenedor{
	width: 400px;
	margin: 50px auto;
	background-color: #F3EDED;
        border: 15px solid #ECE8E8;
	height: 270px;
	border-radius:8px;
	padding: 0px 9px 0px 9px;
}
 
/**
 * Aplicando al icono de usuario el color de fondo,
 * rellenado de 20px y un redondeado de 120px en forma
 * de un circulo
 */
.Icon span{
      background: #A8A6A6;
      padding: 20px;
      border-radius: 120px;
}
/**
 * Se aplica al contenedor madre un margen de tamaño 10px hacia la cabecera y pie,
 * color de fuente blanco,un tamaño de fuente 50px y texto centrado.
 */
.Icon{
     margin-top: 10px;
     margin-bottom:10px; 
     color: #FFF;
     font-size: 50px;
     text-align: center;
}
/**
 * Se aplica al contenedor donde muestra en el pie
 * la opción de olvidaste tu contraseña?
 */
.opcioncontra{
	text-align: center;
	margin-top: 20px;
	font-size: 14px;
}
h1 {
	text-align: center;
	padding: 8vh;
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
.big {
	font-size:30px;
}
.mid-big {
	font-size: 20px;
}
.left {
	text-align:left;
	width: 80%;
	padding-left: 20%;
	font-size: 18px;
}
</style>
<body>
	<jsp:include page="NavBar.jsp" />
	
	<h1>Welcome to UK Water Quality Project</h1>
	<div class="left">
		<ul>
			<li>The application will show an interactive map with the sampling points. By searching for a point using the available fields and by clicking on each point, the user will be able to see all the data related to that sample.</li>
			<br>
			<li>By linking the determinands with other open data sources, the user will be able to inspect the determinand in an external source.</li>
			
		</ul>
	</div>
	<div>
		<br><br><br><br><br><br><br>
		<div class="mid-big">
			Xiao Luo<br>
			Yifu Qiu<br>
			Alejandro Cobo Cabornero<br>
			Oussama Tahiri Alaoui<br>
			Shanshan Cheng
		</div>
	</div>

</body>
</html>