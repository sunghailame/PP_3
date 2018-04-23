enroll();


function enroll() {

	
		
		document.getElementById("sub").addEventListener("click", function(){
		
			if(document.getElementById("courseSelected").selected != true){
				document.getElementById("courseSelected").value == 
		        alert("Please select course");
			}
			if(document.getElementById("studentChecked").selected != true){	
			   	alert("Please select atleast one student");
			}	
			if(document.getElementById("courseRadio").selected != true){
				alert("Please select a course");    
			}	         
			if(document.getElementById("ProfCheckBox").selected != true){
				alert("Please select a Professor");    
			}	 
				      			      
		
		});
		
}