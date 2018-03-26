new_lecture();


function new_lecture(){

var title = document.getElementById("title");
var details = document.getElementById("details");
var link = document.getElementById("link");

//this part is done by yealim. Might need edit
var attendance = document.getElementById("attendance");
var submit = document.getElementById("submit");
 
submit.addEventListener("click", function(){


if(title.value.length == 0 || details.value.length ==0 || link.value.length==0){

	alert("A Field is empty");
	title.value = "";
	
}
//yealim
attendance.addEventListener("click", function(){
	if(attendance.value.length == 0) {
		alert("A Field is empty");
		attendance.value = "";
	}
})


});
}
