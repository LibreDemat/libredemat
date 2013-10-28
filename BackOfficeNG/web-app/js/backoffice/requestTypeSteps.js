zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype');
(function(){
  var zct = zenexity.libredemat.tools;
  var zcc = zenexity.libredemat.common;
  var zcv = zenexity.libredemat.Validation;
  var zcbrt = zenexity.libredemat.bong.requesttype;
  var yl = YAHOO.lang;
  var yu = YAHOO.util;
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;
  zcbrt.Steps = function() {
    return {
      init: function() {
        zcbrt.Conf.saveRequestTypeSteps = zcbrt.Steps.save;
      },
      save : function(e) {
        var form = yud.get('requestTypeStepsForm');
        var error = yud.get('dialogRequestTypeStepsFormError');
        if(zcv.check(form,error)) {
          var target = yue.getTarget(e);
          zct.doAjaxFormSubmitCall(form.getAttributeNode("id").value,[],function(o){
            var json = ylj.parse(o.responseText);
            zct.Notifier.processMessage('success',json.success_msg, null, target);
          });
        }
      }
    }
  }();
  YAHOO.util.Event.onDOMReady(zcbrt.Steps.init);
}());
