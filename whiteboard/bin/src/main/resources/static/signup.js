
signup();

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
else
{
	alert("Invalid Username, please try again!")
}

if(y.value.length > 0 && y.value == z.value){


	good.push(true)

}
else
{
	alert("The passwords do not match, please try again!")
}

if(good[0] == true && good[1] == true){


	window.location.href = "greeting.html"


}

});


}