<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<head>
<title>Water Quality Project</title>
 	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"
   	integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
   	crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"
   	integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og=="
  	crossorigin=""></script>
</head>
<style type="text/css">
body {
	background-image:
		url('https://image.jimcdn.com/app/cms/image/transf/none/path/sc77e1e58a42c514a/image/i9977c7b3a6db203a/version/1516578954/image.jpg');
	background-size: cover;
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

h1 {
text-align: center;
padding: 13vh;
  font-weight: normal;
  font-size: 40px;
  font-family: 'Lobster';
  text-transform: uppercase;    
}


button {
border: 2px solid #b5f2f2;  /*borde: estilo y color*/
font: normal normal 12px quicksand;  /*fuente*/
letter-spacing: 2px; /*separación entre las letras*/
text-align: center; /*alineación del texto*/
text-transform: uppercase; /*texto se transforma en mayúsculas*/
padding: 10px;  /*tamaño del fondo*/
border-radius: 30px 0px 30px 0px; /*ángulos de las 4 esquinas del borde/fondo*/
}

/* map size*/
#map { height: 550px; width: 600px; }

/* Split the screen in half */
.split {
  height: 100%;
  width: 70%;
  position: fixed;
}

/* Control the left side */
.left {
  text-align: left;
  left: 30px;
  padding: 10px;
}

/* Control the right side */
.right {
  right: 0;
  padding-top: 20px;
}
input[type="text"]{
	border-radius: 6px;
	padding: 5px 5px;
	line-height: 20px;
	width: 30%;
}
</style>
<body>
	<jsp:include page="NavBar.jsp" /><br>
	<h2>Search sampling points</h2>
	<div>
		<div class="split left">
	  		<form action="SPSearchServlet" method="get">
	  			<h4>Sample point ID</h4>
	  			<input type="text" name="SP" class="form-control" placeholder="Sample Point ID" aria-describedby="sizing-addon1">
	  			<h4>Distance</h4>
	  			<input type="text" name="distance" value="10000" class="form-control" placeholder="Radius distance in meters" aria-describedby="sizing-addon1"><br><br>
	  			<button type="submit">Search</button><br><br>
	  		</form>
	  		<form action="SPNameServlet" method="get">
	  			<h4>Sample point names</h4>
	  			<input type="text" name="name" class="form-control" placeholder="Sample Point name" aria-describedby="sizing-addon1"><br><br>
	  			<button type="submit">Search</button>
	  		</form>
		</div>
		<div class="split right">
	   		<div id="map"></div>   
	   		<script>
				var mymap = L.map('map').setView([${default_lat}, ${default_lng}], ${default_scale});
				L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
					attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
					maxZoom: 18,
					id: 'mapbox.streets',
					accessToken: 'pk.eyJ1Ijoia3Jhbmdlc3RlaW4iLCJhIjoiY2syamE5MWRtMWh2cjNicDU4ZHkweXIxYiJ9.cAVIxkGZP1KEUDt3RPoziQ'
				}).addTo(mymap);
				<c:if test="${not empty samplePoint_list}">
					<c:forEach items="${samplePoint_list}" var="samplePoint">
						var marker = L.marker([${samplePoint.lat}, ${samplePoint.lng}]).addTo(mymap);
						marker.bindPopup('<b><a href="http://localhost:8080/SamplePointServlet?SP=${samplePoint.SP}">${samplePoint.SP}</a></b><br>${samplePoint.label}');
					</c:forEach>
				</c:if>
			</script>
		</div>
	</div>
</body>
</html>