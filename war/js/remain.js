var overDate = 1000 * 60 * 60 * 24 * 3;//due date + 3days  
var NowTime = new Date();  

var EndTime= new Date(NowTime.getTime() + overDate);  
var EndTimeMsg = EndTime.getFullYear() + "Year";  
EndTimeMsg = EndTimeMsg + (EndTime.getMonth()+1) + "Month";  
EndTimeMsg = EndTimeMsg + (EndTime.getDate()) + "Day";  
EndTimeMsg = EndTimeMsg + (EndTime.getHours()) + "Hour";  
EndTimeMsg = EndTimeMsg + (EndTime.getMinutes()) + "Min";  
EndTimeMsg = EndTimeMsg + (EndTime.getSeconds()) + "Second";  
function GetRTime(){  
  
	NowTime = new Date();  
	var nMS=EndTime.getTime() - NowTime.getTime();  
	var nD=Math.floor(nMS/(1000 * 60 * 60 * 24));  
	var nH=Math.floor(nMS/(1000*60*60)) % 24;  
	var nM=Math.floor(nMS/(1000*60)) % 60;  
	var nS=Math.floor(nMS/1000) % 60;  
	var nMS=Math.floor(nMS/100) % 10;  
	if(nD>= 0){  
		document.getElementById("RemainD").innerHTML=nD;  
		document.getElementById("RemainH").innerHTML=nH;  
		document.getElementById("RemainM").innerHTML=nM;  
		document.getElementById("RemainS").innerHTML=nS;  
	}  
	else {  
		document.getElementById("CountMsg").innerHTML=　EndTimeMsg +"Past！";  
	}  
	setTimeout("GetRTime()",100);  
}  
window.onload=function(){  
	document.getElementById("EndTimeMsg").innerHTML = EndTimeMsg;  
	GetRTime();  
}  