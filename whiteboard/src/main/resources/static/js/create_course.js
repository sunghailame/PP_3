create_course();

function create_course(){

var x = document.getElementById("course_code");
var y = document.getElementById("course_name");
var sb = document.getElementById("sub");
 
sb.addEventListener("click", function(){


if(x.value.length == 0 || y.value.length ==0 ){

	alert("A Field is empty");
	document.getElementById("courseForm").reset();
}



});
}

