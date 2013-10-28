zenexity.libredemat.tools.namespace('zenexity.libredemat.request.category');
(function() {
  var zcrc = zenexity.libredemat.request.category;
  var zcc = zenexity.libredemat.common;
  var zct = zenexity.libredemat.tools;
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = yu.Event;
  zcrc.List = function() {
    return {
      clickEv : undefined,
      init : function() {
        zcrc.List.clickEv = new zct.Event(zcrc.List, zcrc.List.processClick);
        yue.on(yud.get("categoriesList"), "click", zcrc.List.clickEv.dispatch, zcrc.List.clickEv, true);
      },
      processClick : function(e) {
        return (yue.getTarget(e).id||'_').split('_')[0];
      },
      deleteEntry : function(e) {
        var id = (yue.getTarget(e).id||'_').split('_')[1];
        new zct.ConfirmationDialog(
          {
            body : yud.get("categoryMessage_" + id).value
          },
          function() {
            zct.doAjaxDeleteCall("/delete/" + id, null, function(o) {
              if (zcc.validateAndFilterResponse(o)) {
                var response = YAHOO.lang.JSON.parse(o.responseText);
                var liId = "category-" + response.id;
                var elementToRemove = YAHOO.util.Dom.get(liId);
                var categoryListContainer = new yu.Element("categoriesList");
                categoryListContainer.removeChild(elementToRemove);
                zct.Notifier.processMessage("success",response.success_msg);
              }
            });
          }).show();
      }
    };
  }();
  yue.onDOMReady(zcrc.List.init);
}());
