/**
 * @author Jean-Sébastien Bour (jsb@zenexity.com)
 */
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
  zcbrt.Delays = function() {
    return {
      init: function() {
        zcbrt.Conf.saveRequestTypeDelays = zcbrt.Delays.save;
      },
      save : function(e) {
        var form = yud.get('requestTypeDelaysForm');
        var error = yud.get('dialogRequestTypeDelaysFormError');
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
  YAHOO.util.Event.onDOMReady(zcbrt.Delays.init);
}());
