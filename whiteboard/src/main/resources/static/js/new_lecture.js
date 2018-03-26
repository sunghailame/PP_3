new_lecture();


function new_lecture(){

var title = document.getElementById("title");
var details = document.getElementById("details");
var link = document.getElementById("link");
var submit = document.getElementById("submit");
 
submit.addEventListener("click", function(){


if(title.value.length == 0 || details.value.length ==0 || link.value.length==0){

	alert("A Field is empty");
	title.value = "";
	
}



});
}
