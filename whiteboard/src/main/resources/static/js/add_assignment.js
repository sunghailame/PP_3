add_assignment();

function add_assignment() {

		var a = 0
		var b = 0
		var submit = 0

		document.getElementById("sub").addEventListener("click", function(){

			if(document.getElementById("AssignmentName").value == 0)
			{
				alert("Name field is empty")	
			}

			if(document.getElementById("AssignmentName").value.search(/[^A-Za-z0-9]+$/)!=-1)
			{
		        alert("Field must be only letter and/or numbers")	
			}
			
			if(document.getElementById("percentage").value == 0)
			{
				alert("Weightage field is empty")
			}
		
		});

}