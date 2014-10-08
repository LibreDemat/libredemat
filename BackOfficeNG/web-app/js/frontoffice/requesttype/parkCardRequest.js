zenexity.libredemat.tools.namespace("zenexity.libredemat.fong.requesttype");

var zcf = zenexity.libredemat.fong
var zcfr = zenexity.libredemat.fong.requesttype;
var zct = zenexity.libredemat.tools;
var yue = YAHOO.util.Event;
var yud = YAHOO.util.Dom;
var yus = YAHOO.util.Selector;
var ylj = YAHOO.lang.JSON;

zcfr.ParkCardRequest = function()
{
	
	var newValue;
	var par;
	
	var decomposeUrl = zenexity.libredemat.baseUrl.split('/');
	decomposeUrl.pop();
	var recreationActivityUrl = decomposeUrl.join('/');

	var optionSelected = yud.get('requesterId'); 
	yue.addListener(optionSelected,"change",changeValue);
	
	function changeValue()
	{ 
		newValue=this.options[this.selectedIndex].value;
		if(newValue != null)
		{
			// Lors du changement du décideur de la requete on recharge la page avec les propriétés...
			var requetId = yud.get('requetId'); 
			newURL = recreationActivityUrl + "/edit/" + requetId.value + "?requesterId=" + newValue;
			window.location = newURL;
		}
	}
}

yue.onDOMReady(zcfr.ParkCardRequest);
