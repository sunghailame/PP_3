signup();

function signup() {
		var a = 0
		var b = 0
		var c = 0
		var d = 0
		
		document.getElementById("sub").addEventListener("click", function(){
		
			if(document.getElementById("name").value.search(/^[A-z]+$/)!=-1){
		        a = 1
			}
			if(document.getElementById("user").value.search(/^[A-z]+$/) != -1){	
			    b = 1
			}	
			if(document.getElementById("password").value == document.getElementById("c-password").value){
				c = 1     
			}	         
			if(document.getElementById("email").value.length > 0){
				d = 1
			}
				      			      
		});
				
		if( a == 0){
			alert("name must be alphabets")
			document.getElementById("myForm").reset();	
		}
		if( b == 0){
			document.getElementById("user") = "???"
			alert(document.getElementById("user"))
			alert("username must be alphabets")
			document.getElementById("myForm").reset();
		}
		if(c == 0){
			alert("password fields are not the same")
			document.getElementById("myForm").reset();
		}
		if( d == 0){
			alert("wrong email format")
			document.getElementById("myForm").reset();
		}
		if( a == 1){
			if(b == 1){
				if(c == 1){
					if(d == 1){		
							window.location.href = "whiteboard"
					}
				}
			}
		}
}