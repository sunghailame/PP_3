concatInput();



function concatInput() {
		
		var a = "";
		var b = "";
		var c = "";
		document.getElementById("subBack").addEventListener("click", function(){
		
			a = document.getElementById("stud_data").value;
			alert(a);
			b = document.getElementById("gradeInput").value;
			alert(b);			
			c = a + b;
			alert(c);
			  			      
			document.getElementById("stud_data").value =c;
			
			alert(document.getElementById("stud_data").value);
		});
		
}