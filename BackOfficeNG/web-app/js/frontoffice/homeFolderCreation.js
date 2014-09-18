zenexity.libredemat.tools.namespace("zenexity.libredemat.fong.homefolder");
(function(){
  var zcfh = zenexity.libredemat.fong.homefolder;
  var zcv = zenexity.libredemat.Validation;
  var zcf = zenexity.libredemat.fong;
  var yu = YAHOO.util;
  var yue = yu.Event;
  var yud = yu.Dom;
  zcfh.Creation = function() {
    var form = undefined;
    return {
      init: function() {
        form = yud.get("loginInformations");
        if (form) {
          yue.on("temporary_on", "click", function() { yud.addClass(form, "hidden"); });
          yue.on("temporary_off", "click", function() { yud.removeClass(form, "hidden"); });
        }
        
        // chargement des calendriers :
        if (yud.get("vaccinBcgShow") != undefined) yue.on(yud.get('vaccinBcgShow'),'click', zcfh.Creation.processClickStart,zcfh.Creation.processClickStart,true);
        if (yud.get("vaccinDtPolioShow") != undefined) yue.on(yud.get('vaccinDtPolioShow'),'click', zcfh.Creation.processClickStart,zcfh.Creation.processClickStart,true);
        if (yud.get("vaccinInjectionSerumShow") != undefined) yue.on(yud.get('vaccinInjectionSerumShow'),'click', zcfh.Creation.processClickStart,zcfh.Creation.processClickStart,true);
        if (yud.get("vaccinRorShow") != undefined) yue.on(yud.get('vaccinRorShow'),'click', zcfh.Creation.processClickStart,zcfh.Creation.processClickStart,true);
        if (yud.get("vaccinTetracoqPentacoqShow") != undefined) yue.on(yud.get('vaccinTetracoqPentacoqShow'),'click', zcfh.Creation.processClickStart,zcfh.Creation.processClickStart,true);
        
        // hack inexine - abonnement aux éléments
        var elements = document.getElementsByName('editFieldsForm');
        if (elements != undefined) yue.on(elements[0],'submit', zcfh.Creation.checkAndSubmit);
       
      },
      processClickStart : function(e)
      {
    	  // Pour les options voir le fichier calendar_fo.js
    	  var target = yue.getTarget(e);
    	  var calId = target.getAttribute("id").replace('Show', '');
		  zcf.Calendar(calId);
      },
      checkAndSubmit : function(e) 
      {
	        var formEl = yue.getTarget(e);
	        var formErrorId = formEl.getAttributeNode("id").value + "-error";
	        try
	        {
		        if (zcv.check(formEl, formErrorId)) 
		        {
		        	// pas utile en FO
		        	//formEl.submit();
		        }
		        else
	        	{
		        	e.preventDefault(e);
			        e.stopPropagation(e);
			        e.cancelBubble = true;
	        	}
	        }
	        catch (ex)
	        {
	        	alert("Exception: "+ex+"\nCertain caractères ne sont pas supportés comme : ( )");
	        	e.preventDefault(e);
		        e.stopPropagation(e);
		        e.cancelBubble = true;
	        }
      }
    };
  }();
  yu.Event.onDOMReady(zcfh.Creation.init);
}());
