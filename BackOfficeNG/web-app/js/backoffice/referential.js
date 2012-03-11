zenexity.capdemat.tools.namespace('zenexity.capdemat.bong');
(function(){
  var zct = zenexity.capdemat.tools;
  var zcb = zenexity.capdemat.bong;
  var zcv = zenexity.capdemat.Validation;
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = YAHOO.util.Event;
  var ylj = YAHOO.lang.JSON;
  zcb.Referential = function() {
    var type = undefined;
    var content = {
        head : "Attention !",
        body : "Confirmez-vous la suppression ?"
    };
    return {
      clickEv : undefined,
      init : function() {
        zcb.Referential.clickEv = new zct.Event(zcb.Referential,zcb.Referential.processClick);
        yue.on(yud.get('mainPanel'),'click',zcb.Referential.clickEv.dispatch,zcb.Referential.clickEv,true);
        zcb.Referential.load();
      },
      processClick : function(e) {
        var target  = yue.getTarget(e);
        return (target.getAttribute('id')||'_').split('_')[0];
      },
      load : function() {
        zct.doAjaxCall(['/', zcb.Referential.type, 's'].join(''), [], function(o) {
          yud.get('listContainer').innerHTML = o.responseText;
        });
      },
      loadNewForm : function() {
        var url = '/' + zcb.Referential.type;
        if (zcb.Referential.type == "agent")
          url += "?profile=1&password=1";
        zct.doAjaxCall(url, [], function(o) {
          yud.get("newFormContainer").innerHTML = o.responseText;
          zcv.complexRules["areEqual"].pushFields("password", "passwordConfirmation");
        });
      },
      edit : function(e) {
        var id = (yue.getTarget(e).getAttribute('id')||'_').split('_')[1];
        var url = ['/', zcb.Referential.type, '/', id].join('');
        if (zcb.Referential.type == "agent")
          url += "?profile=1";
        zct.doAjaxCall(url, [], function(o) {
          yud.get('formContainer_' + id).innerHTML = o.responseText;
        });
      },
      editPassword : function(e) {
        var id = (yue.getTarget(e).getAttribute('id')||'_').split('_')[1];
        var url = ['/', zcb.Referential.type, '/', id, "?password=1"].join('');
        zct.doAjaxCall(url, [], function(o) {
          yud.get('formContainer_' + id).innerHTML = o.responseText;
          zcv.complexRules["areEqual"].pushFields("password", "passwordConfirmation");
        });
      },
      cancel : function(e) {
        zct.style(yud.get('form_' + (yue.getTarget(e).getAttribute('id')||'_').split('_')[1]),
          {'display':'none'});
        zcv.complexRules["areEqual"].fieldsList = [];
      },
      save : function(e) {
        var id = (yue.getTarget(e).getAttribute('id')||'_').split('_')[1];
        var form = yud.get('form_' + id);
        var cont = yud.get('error-container_' + id);
        cont.innerHTML = "";
        if (zcv.check(form, cont)) {
          zct.doAjaxFormSubmitCall('form_' + id,[],function(o){
            var json = YAHOO.lang.JSON.parse(o.responseText);
            zct.Notifier.processMessage('success',json.success_msg);
            zcb.Referential.cancel(e);
            zcb.Referential.load();
          });
        }
      },
      remove : function(e) {
        var id = (yue.getTarget(e).getAttribute('id')||'_').split('_')[1];
        new zct.ConfirmationDialog(content, function() {
          zct.doAjaxDeleteCall(['/', zcb.Referential.type, '/', id].join(''), [], function(o) {
            var li = new yu.Element(yue.getTarget(e).parentNode);
            var ul = new yu.Element(yue.getTarget(e).parentNode.parentNode);
            var json = ylj.parse(o.responseText);
            zct.Notifier.processMessage('success',json.success_msg);
            ul.removeChild(li);
          });
        }).show(e);
      }
    };
  }();
  yue.onDOMReady(zcb.Referential.init);
}());