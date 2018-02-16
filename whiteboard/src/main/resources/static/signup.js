
$(document).ready(function () 
{

function signup(){

var x = document.getElementById("user");
var y = document.getElementById("password");
var z = document.getElementById("c-password");
var sb = document.getElementById("sub");
var good =  [];
 
sb.addEventListener("click", function(){


if(x.value.length > 0 && x.value.search(/^[A-z]+$/) != -1 ){

	good.push(true)
	console.log("true")

}

if(y.value.length > 0 && y.value == z.value){


	good.push(true)

}


if(good[0] == true && good[1] == true){

    console.log(good[0])
	window.location.href = "login/signup"


}
else
{
	alert("The passwords do not match, please try again!")
}

});
}); 

}