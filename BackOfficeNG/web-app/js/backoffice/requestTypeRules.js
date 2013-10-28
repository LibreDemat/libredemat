/**
 * @author rdj@zenexity.fr
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

  zcbrt.Rules = function() {

    return {

      init: function() {
        zcbrt.Rules.initRules();
        zcbrt.Conf.saveRule = zcbrt.Rules.save;
      },

      initRules : function() {
        zct.doAjaxCall(['/loadRules/',(zcbrt.currentId||0)].join(''),[],function(o){
          zct.html(yud.get('requestTypeRules'),o.responseText);
        });
      },

      save : function(e) {
        yue.preventDefault(e);
        var target = yue.getTarget(e);
        var formEl = yud.getAncestorByTagName(target, 'form');
        zct.doAjaxFormSubmitCall(formEl.id,[],function(o){
          var json = ylj.parse(o.responseText);
          zcbrt.Rules.initRules();
          zct.Notifier.processMessage(json.status, json.message, null, target);
        }, true);
      }
    }
  }();
  YAHOO.util.Event.onDOMReady(zcbrt.Rules.init);
}());

