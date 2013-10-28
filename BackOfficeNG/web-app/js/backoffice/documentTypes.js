zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype');

(function(){

  var zct = zenexity.libredemat.tools;
  var zcc = zenexity.libredemat.common;
  var zcv = zenexity.libredemat.Validation;
  var zcb = zenexity.libredemat.bong;
  var zcbrt = zenexity.libredemat.bong.requesttype;

  var yl = YAHOO.lang;
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;

  zcbrt.DocumentTypes = function() {
    var reallyDeleteDialogContent = {head : 'Attention !', body : 'Confirmez-vous la suppression ?'};
    return {
      clickEv : undefined,
      init : function() {
        zcbrt.DocumentTypes.clickEv = new zct.Event(zcbrt.DocumentTypes,zcbrt.DocumentTypes.processClick);
        yue.on(yud.get('documentTypes'),'click',zcbrt.DocumentTypes.clickEv.dispatch,zcbrt.DocumentTypes.clickEv,true);
        zcbrt.DocumentTypes.loadTypes();
      },
      processClick : function(e) {
        var target  = yue.getTarget(e);
        if (target.id == "linkShowDatasheet") {
          return "loadNewDocumentTypeForm";
        }
        return (target.id||'_').split('_')[0];
      },
      validityDurationTypeChange : function(e) {
        var id = (typeof e == 'string')?e:(yue.getTarget(e).id||'_').split('_')[1];
        var val = yud.get('validityDurationType_'+id).value;
        if(val == 'YEAR' || val == 'MONTH') {
          yud.get('validityDuration_'+id).style.display = 'inline';
          yud.get('validityDuration_label_'+id).style.display = 'inline';
        } 
        else if(yud.get('validityDuration_'+id).style.display == 'inline') {
          yud.get('validityDuration_'+id).style.display = 'none';
          yud.get('validityDuration_label_'+id).style.display = 'none';
        }
      },
      loadNewDocumentTypeForm : function() {
        zct.doAjaxCall('/createForm/','',function(o){
          yud.get('newDocumentTypeFormContainer').innerHTML = o.responseText;
          yud.get('validityDurationType_').onchange = zcbrt.DocumentTypes.validityDurationTypeChange;
        });
      },
      loadTypes : function() {
        zct.doAjaxCall('/_types/','',function(o){
          yud.get('documentTypesList').innerHTML = o.responseText;
        });
      },
      editDocumentType: function(e) {
        var id = (yue.getTarget(e).id||'_').split('_')[1];
        zct.doAjaxCall('/editForm?id=' + id,[],function(o){
          yud.get('editDocumentTypeFormContainer_'+id).innerHTML = o.responseText;
          yud.get('validityDurationType_'+id).onchange = zcbrt.DocumentTypes.validityDurationTypeChange;
          zcbrt.DocumentTypes.validityDurationTypeChange(id);
        });
      },
      saveDocumentType: function(e) {
        var target = yue.getTarget(e);
        var id = (yue.getTarget(e).id||'_').split('_')[1];

        var form = yud.get('documentTypeForm_'+id);
        var cont = yud.get('error-container_'+id);
        cont.innerHTML = "";
        var validform = zcv.check(e, cont);
        if (validform) {
          zct.doAjaxFormSubmitCall('documentTypeForm_'+id,[],function(o){
            zcbrt.DocumentTypes.loadTypes();
            if(ylj.parse(o.responseText).status == "ok" || ylj.parse(o.responseText).status == "success" )
            {
              zct.Notifier.processMessage('success',ylj.parse(o.responseText).success_msg, null, target);
              zcbrt.DocumentTypes.cancelNewDocumentType();
            }
            else if (ylj.parse(o.responseText).status == "error")
              new zct.ConfirmationDialog({head : "Erreur", body : ylj.parse(o.responseText).error_msg}).show();
          });
        }
      },
      cancelEditDocumentType : function(e) {
        var id = (yue.getTarget(e).id||'_').split('_')[1];
        yud.get('editDocumentTypeFormContainer_'+id).innerHTML = '';
      },
      cancelNewDocumentType : function() {
        yud.get('newDocumentTypeFormContainer').innerHTML = '';
      },
      deleteDocumentType : function(e) {
        var target = yue.getTarget(e);
        var id = (target.id||'_').split('_')[1];
        new zct.ConfirmationDialog(reallyDeleteDialogContent, function(){
          zct.doAjaxDeleteCall('/deleteDocumentType', 'id=' + id, function(o){
            if(ylj.parse(o.responseText).status == "ok" || ylj.parse(o.responseText).status == "success" )
              zct.Notifier.processMessage('success', ylj.parse(o.responseText).success_msg, null, target);
            else if (ylj.parse(o.responseText).status == "error")
              new zct.ConfirmationDialog({head : "Erreur", body : ylj.parse(o.responseText).error_msg}).show();

            zcbrt.DocumentTypes.loadTypes();
          })
        }).show(e);
      }
    }
  }();
  YAHOO.util.Event.onDOMReady(zcbrt.DocumentTypes.init);
}());
