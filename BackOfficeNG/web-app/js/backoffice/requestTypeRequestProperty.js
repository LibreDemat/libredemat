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
  zcbrt.RequestProperty = function() {
    var isOfRegistrationKindChange = function(){
        var e = yud.get("isOfRegistrationKind");
        if(e.checked) {
          yud.removeClass(yud.get("liSupportMultiple"),'unactiveLi');
          yud.get("supportMultiple").disabled="";
        } else {
          yud.addClass(yud.get("liSupportMultiple"),'unactiveLi');
          yud.get("supportMultiple").checked=false;
          yud.get("supportMultiple").disabled="disabled";
        }
      };

    return {
      init: function() {
        zcbrt.Conf.saveRequestTypeRequestProperty = zcbrt.RequestProperty.save;
        yue.on(yud.get("isOfRegistrationKind"),"change", isOfRegistrationKindChange);
        yue.on(yud.get("isOfRegistrationKind"),"click", isOfRegistrationKindChange);
      },
      save : function(e) {
        var form = yud.get('requestTypeRequestPropertyForm');
        var error = yud.get('dialogRequestTypeRequestPropertyFormError');
        if(zcv.check(form,error)) {
          var target = yue.getTarget(e);
          zct.doAjaxFormSubmitCall(form.getAttributeNode("id").value,[],function(o){
            var json = ylj.parse(o.responseText);
            if(json.success_msg) {
              zct.Notifier.processMessage('success',json.success_msg, null, target);
              yud.removeClass(yud.get("filingDelay"),"validation-failed");
            } else {
              zct.Notifier.processMessage('error',json.error_msg, null, target);
              yud.addClass(yud.get("filingDelay"),"validation-failed");
            }
          });
        }
      }
    }
  }();
  YAHOO.util.Event.onDOMReady(zcbrt.RequestProperty.init);
}());
