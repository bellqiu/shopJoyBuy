/*
	$RCSfile: ajax.js,v $
	$Revision: 1.0 $
	$Date: 2007/11/08 11:45 $
*/
var Ajaxs = new Array();
function Ajax(recvType, statusId) {
	var aj = new Object();
	aj.statusId = statusId ? document.getElementById(statusId) : null;
	aj.targetUrl = '';
	aj.sendString = '';
	aj.recvType = recvType ? recvType : 'XML';
	aj.resultHandle = null;

	aj.createXMLHttpRequest = function() {
		var request = false;
		if(window.XMLHttpRequest) {
			request = new XMLHttpRequest();
			if(request.overrideMimeType) {
				request.overrideMimeType('text/xml');
			}
		} else if(window.ActiveXObject) {
			var versions = ['Microsoft.XMLHTTP', 'MSXML.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.7.0', 'Msxml2.XMLHTTP.6.0', 'Msxml2.XMLHTTP.5.0', 'Msxml2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP'];
			for(var i=0; i<versions.length; i++) {
				try {
					request = new ActiveXObject(versions[i]);
					if(request) {
						return request;
					}
				} catch(e) {
					//alert(e.message);
				}
			}
		}
		return request;
	}

	aj.XMLHttpRequest = aj.createXMLHttpRequest();

	aj.processHandle = function() {
		if(aj.statusId) {
			aj.statusId.style.display = '';
		}
		if(aj.XMLHttpRequest.readyState == 1 && aj.statusId) {
			aj.statusId.innerHTML = xml_http_building_link;
		} else if(aj.XMLHttpRequest.readyState == 2 && aj.statusId) {
			aj.statusId.innerHTML = xml_http_sending;
		} else if(aj.XMLHttpRequest.readyState == 3 && aj.statusId) {
			aj.statusId.innerHTML = xml_http_loading;
		} else if(aj.XMLHttpRequest.readyState == 4) {
			if(aj.XMLHttpRequest.status == 200) {
				for(k in Ajaxs) {
					if(Ajaxs[k] == aj.targetUrl) {
						Ajaxs[k] = null;
					}
				}

				if(aj.statusId) {
					aj.statusId.innerHTML = xml_http_data_in_processed;
					aj.statusId.style.display = 'none';
				}
				if(aj.recvType == 'HTML') {
					aj.resultHandle(aj.XMLHttpRequest.responseText, aj);
				} else if(aj.recvType == 'XML') {
					aj.resultHandle(aj.XMLHttpRequest.responseXML.lastChild.firstChild.nodeValue, aj);
				}
			} else {
				if(aj.statusId) {
					aj.statusId.innerHTML = xml_http_load_failed;
				}
			}
		}
	}

	aj.get = function(targetUrl, resultHandle) {
		if(in_array(targetUrl, Ajaxs)) {
			return false;
		} else {
			arraypush(Ajaxs, targetUrl);
		}
		aj.targetUrl = targetUrl;
		aj.XMLHttpRequest.onreadystatechange = aj.processHandle;
		aj.resultHandle = resultHandle;
		if(window.XMLHttpRequest) {
			aj.XMLHttpRequest.open('GET', aj.targetUrl);
			aj.XMLHttpRequest.send(null);
		} else {
		        aj.XMLHttpRequest.open("GET", targetUrl, true);
		        aj.XMLHttpRequest.send();
		}
	}

	aj.post = function(targetUrl, sendString, resultHandle) {
		if(in_array(targetUrl, Ajaxs)) {
			return false;
		} else {
			arraypush(Ajaxs, targetUrl);
		}
		aj.targetUrl = targetUrl;
		aj.sendString = sendString;
		aj.XMLHttpRequest.onreadystatechange = aj.processHandle;
		aj.resultHandle = resultHandle;
		aj.XMLHttpRequest.open('POST', targetUrl);
		aj.XMLHttpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		aj.XMLHttpRequest.send(aj.sendString);
	}
	return aj;
}