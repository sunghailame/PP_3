FillChart();

function FillChart(){

var a = document.getElementById("option1");
var b = document.getElementById("option2");
var c = document.getElementById("option3");
var d = document.getElementById("option4");
var e = document.getElementById("option5");
var f = document.getElementById("option6");
var g = document.getElementById("option7");
var h = document.getElementById("option8");
var i = document.getElementById("option9");
var j = document.getElementById("option10");
var k = document.getElementById("option11");
var l = document.getElementById("option12");
var m = document.getElementById("option13");
var n = document.getElementById("option14");
var o = document.getElementById("option15");
var p = document.getElementById("option16");
var q = document.getElementById("option17");
var r = document.getElementById("option18");

sb.addEventListener("load", function(){


if(a.value.length == 0 || b.value.length ==0 || c.value.length == 0 || d.value.length ==0 || e.value.length == 0 || f.value.length == 0 || g.value.length == 0 || h.value.length==0 || k.value.length == 0 || l.value.length==0 || m.value.length==0 || n.value.length==0 || o.value.length==0 || p.value.length==0 || q.value.length==0 || r.value.length ==0 ){

	alert("aaa");
	document.getElementById("option1").value ="Empty Seat";
	
}



});
}

