takeAttendance();

function takeAttendance(){

var attendanceBox = document.getElementById("attendance");

if(attendanceBox){

attendanceBox.addEventListener("click", function(){

if(attendanceBox.checked == true){

	alert("checked!");

	
}

});
}

}
