var container;
var camera, scene, renderer;

var model;
var group;
var group2;
var material2;
var material3;
var material = []
var vidriotest = []

init();
animate();
function init() {
	container = document.getElementById('canvas');
	document.body.appendChild( container );
	camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
	camera.position.y = 2.2
    camera.position.z = 1.2
	// scene
	scene = new THREE.Scene();
    

    



	var light = new window.THREE.DirectionalLight(0xdddddd, 1.5)
	light.position.set( -10, 10, -10 )
	var targetObject = new window.THREE.Object3D();
    targetObject.position.set(0,0,0)
	light.target = targetObject

	scene.add(light);
	


	// Create a material
	var textureLoader = new THREE.TextureLoader();
	var map = textureLoader.load('../public/models/citytext.jpg');
	var materialbase = new THREE.MeshPhongMaterial({map: map});

	group = new window.THREE.Group();

	var loader = new THREE.OBJLoader();
	loader.load(
		// resource URL
		"../public/models/citybase.obj",

		// onLoad callback
		// Here the loaded data is assumed to be an object
		function (obj) {

			obj.traverse( function (node) {

				if(node.isMesh){
					model = node
					model.material = materialbase;
					group.add(model);
					
				}

		  	} );
			
		},

		// onProgress callback
		function (xhr) {
			console.log((xhr.loaded / xhr.total * 100) + '% loaded');
		},

		// onError callback
		function (err) {
			console.error('An error happened');
		}
	);

	material = []
	vidriotest = []

	material2 = new window.THREE.MeshPhongMaterial({color:'#ff00ff' ,transparent:true})
    material2.opacity = 0.7
    material3 = new window.THREE.MeshPhongMaterial({color:'#0000ff' ,transparent:true})
    material3.opacity = 0.7

    group2 = new window.THREE.Group()

    for (let x=1;x<=55;x++){

		loader.load(
			// resource URL
			`../public/models/${x}.obj`,

			// onLoad callback
			// Here the loaded data is assumed to be an object
			function (obj) {

				obj.traverse( function (node) {

					if(node.isMesh){
						vidriotest[x] = node
		                material[x] = new window.THREE.MeshPhongMaterial({color:`hsl(200,50%,70%)` ,opacity: 0.4, transparent:true})
		                if(x<10) { vidriotest[x].name = `2800${x}`}
		                else { vidriotest[x].name = `280${x}`}

		                vidriotest[x].material = material[x]
		                group2.add(vidriotest[x]);
		                if(x===55) group.add(group2)
					}

			  	} );
				
			},

			// onProgress callback
			function (xhr) {
				console.log((xhr.loaded / xhr.total * 100) + '% loaded');
			},

			// onError callback
			function (err) {
				console.error('An error happened');
			}
		);
	}

	scene.add(group);

	renderer = new THREE.WebGLRenderer();
	renderer.setSize( window.innerWidth, window.innerHeight);
	container.appendChild(renderer.domElement);
	
	// CONTROLS
    var controls = new window.THREE.OrbitControls(camera,renderer.domElement);
}
//
function animate() {
	requestAnimationFrame(animate);
	group.rotation.y += 0.003;
	render();
}
function render() {
	camera.lookAt(scene.position);
	renderer.render(scene, camera);
}


var mouse = new window.THREE.Vector2();  
var raycaster = new window.THREE.Raycaster(); 
var INTERSECTED = undefined


onDocumentMouseMove = (event) => {
	console.log("onDocumentMouseMove")
    //event.preventDefault();
  
    mouse.x = (event.clientX / renderer.domElement.clientWidth) * 2 - 1;
    mouse.y = - (event.clientY / renderer.domElement.clientHeight) * 2 + 1;
    update()
}

update = () => {

	raycaster.setFromCamera(mouse, camera);

	intersects = raycaster.intersectObjects(group2.children)
        
	if (intersects.length > 0) {
        // if the closest object intersected is not the currently stored intersection object
		if (intersects[0].object !== INTERSECTED) {
			// restore previous intersection object (if it exists) to its original color
			if (INTERSECTED) {
				INTERSECTED.position.y -= INTERSECTED.currentpositiony
				INTERSECTED.material = INTERSECTED.currentmaterial
			}

			// store reference to closest object as current intersection object
			INTERSECTED = intersects[0].object;
			// store color of closest object (for later restoration)
			INTERSECTED.currentpositiony = INTERSECTED.position.y
			INTERSECTED.currentmaterial = INTERSECTED.material
			// set a new color for closest object
			INTERSECTED.material = material2
			INTERSECTED.position.y += 0.03
			printPopup(INTERSECTED, true);
			
		}
	}else{ // there are no intersections

		printPopup(INTERSECTED, false)
	
		// restore previous intersection object (if it exists) to its original color
		if (INTERSECTED) {
			INTERSECTED.material = INTERSECTED.currentmaterial
			INTERSECTED.position.y = 0
		}
		  
		// remove previous intersection object reference
		//by setting current intersection object to "nothing"
		INTERSECTED = null

	}
} 


printPopup = (intersected, show) => {
	//TODO: Get data, fill popup and show or hide
	if(show){
		//TODO: Substitute random for district name correspondign to postal code (which is what we know)
		var index = Math.floor(Math.random() * 40)
		document.getElementById("district-name").innerHTML = districts[index]
		document.getElementById("district-postal").innerHTML = INTERSECTED.name
		document.getElementById("popup").style.display = "block"
	}else{
		document.getElementById("popup").style.display = "none"
	}
	
}


//TODO: Handle received data
getDeviceData = (id) => {

}

getDevicesList = () => {

}









