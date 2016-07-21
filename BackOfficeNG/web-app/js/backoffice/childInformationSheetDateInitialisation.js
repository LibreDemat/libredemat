zenexity.libredemat.tools.namespace("zenexity.libredemat.bong.homeFolder");
(function(){
  var ylj = YAHOO.lang.JSON;
  var yu = YAHOO.util;
  var yue = yu.Event;
  var yud = yu.Dom;
  var yus = yu.Selector;
  var yw = YAHOO.widget;
  var zcb = zenexity.libredemat.bong;
  var zcbh = zcb.homeFolder;
  var zct = zenexity.libredemat.tools;
  zcbh.Import = function() {
    return {
      init: function() {
        yue.on(yud.get("initialisation"), "click",  zcbh.Import.upload);
        yue.on(yud.get("saveRule"), "click",  zcbh.Import.save);
      },
      upload : function(e) {
        yue.stopEvent(e);
        yue.removeListener(yud.get("initialisation"), "click",  zcbh.Import.upload);
        var target = yue.getTarget(e);
        zct.doAjaxFormSubmitCall("childInformationSheetDateInitialisationForm", null, function(o) {
          var response = ylj.parse(o.responseText);
          if (response.status === "ok") {
            zct.Notifier.processMessage("success", ylj.parse(o.responseText).success_msg, null, target);
          } else {
            zct.Notifier.processMessage("modelError", ylj.parse(o.responseText).error_msg);
          }
          yue.on(yud.get("initialisation"), "click",  zcbh.Import.upload);
        }, true);
      },
      save : function(e) {
        yue.preventDefault(e);
        var target = yue.getTarget(e);
        var formEl = yud.getAncestorByTagName(target, 'form');
        zct.doAjaxFormSubmitCall(formEl.id,[],function(o){
          var json = ylj.parse(o.responseText);
          zct.Notifier.processMessage(json.status, json.message);
        }, true);
      }
    };
  }();
  yue.onDOMReady(zcbh.Import.init);
}());
