zenexity.libredemat.tools.namespace("zenexity.libredemat.fong.homefolder");
(function(){
  var zcfh = zenexity.libredemat.fong.homefolder;
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
      }
    };
  }();
  yu.Event.onDOMReady(zcfh.Creation.init);
}());
