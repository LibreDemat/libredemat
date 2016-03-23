zenexity.libredemat.tools.namespace("zenexity.libredemat.bong.users");
(function(){
  var zct = zenexity.libredemat.tools;
  var zcbu = zenexity.libredemat.bong.users;
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = yu.Event;
  var yus = yu.Selector;
  zcbu.Synchronisation = function() {

    return {
        clickEvent : undefined,
        init : function() {
            yue.addListener(yud.get("synchronisation"), "submit", function(e) {
                yud.get("initialisation").disabled = true;
                e.preventDefault();
                zct.doAjaxFormSubmitCall("synchronisation", [], function(d){
                	d = JSON.parse(d.responseText);
                	zct.Notifier.processMessage(
                	          d.status,
                	          d.msg,
                	          null,
                	          null
                	        );
                	yud.addClass("response", "invisible");
                }, null);
                yud.removeClass("response", "invisible");
                yud.get("message").innerHTML = yud.get("message").innerHTML + yus.query("#synchronisation input[name='email']")[0].value;
            });
        }
    }
  }();
  yue.onDOMReady(zcbu.Synchronisation.init);
}());