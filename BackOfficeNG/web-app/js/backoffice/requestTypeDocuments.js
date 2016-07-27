/**
 *  Provides request document type client-side helper
 *  
 *  @namespace - zenexity.libredemat.bong.requesttype
 *  @author vba@zenexity.fr
 *
 **/

zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype');

(function(){
  var zcv = zenexity.libredemat.Validation;
  var zct = zenexity.libredemat.tools;
  var zcbrt = zenexity.libredemat.bong.requesttype;
  
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = yu.Event;
  var yus = yu.Selector;
  var ylj = YAHOO.lang.JSON;
  
  zcbrt.Documents = function() {
    return {
      init: function() {
        yue.on(yud.get('mandatoryDocumentStepInputDiv'), 'click',zcbrt.Documents.toggleMandatoryDocumentStep);
        zcbrt.Conf.showAllDocuments = zcbrt.Documents.showAllDocuments;
        zct.doAjaxCall(["/documentList/",(zcbrt.currentId||0)].join(''),[],function(o){
          zct.html(yud.get('documentList'),o.responseText);
          zcbrt.Conf.showAssociatedDocuments(yud.get('showAssociatedDocuments'));
        });
      },
      getScope : function() {
        return ['associate','unassociate'];
      },
      adaptFilterPanel : function(e) {
        var target = (yue.getTarget(e)||e);
        zct.siblings(target,function(n){yud.removeClass(n,'current')});
        yud.addClass(target,'current');
      },
      showAllDocuments : function(e) {
        var elements = yus.query('#documentTypeFormList li');
        zct.each(elements,function(i,n){zct.style(n,{display:'block'});});
        zcbrt.Documents.adaptFilterPanel(e);
      },
      reloadList : function() {
        var url = ["/documentList/",(zcbrt.currentId||0)].join('');
        zct.doAjaxCall(url,[],function(o){
          var div = yud.get('documentList');
          zct.html(div,o.responseText);
        });
      },
      // emplacement à voir
      toggleMandatoryDocumentStep: function(e) {
        var cont = yud.get("mandatoryDocumentStepErrors");
        cont.innerHTML = "";
        if (zcv.check(yud.get("mandatoryDocumentStepForm"), cont)) {
          var target = yue.getTarget(e);
          zct.doAjaxFormSubmitCall("mandatoryDocumentStepForm", [], function(o) {
            zct.Notifier.processMessage('success', ylj.parse(o.responseText).success_msg, yud.get('notification'), target);
          },false);
        }
      }
    }
  }()
  
  zct.each(zcbrt.Documents.getScope(),function(i,name){
    var method = ['show',zct.capitalize(name),'dDocuments'].join('');
    zcbrt.Conf[method] = function(e) {
      var elements = yus.query('#documentTypeFormList li');
      zct.each(elements,function(i,n){zct.style(n,{display:'block'});});
      elements = zct.grep(elements,function(n){ return (yus.query('.'+name,n).length > 0);});
      zct.each(elements,function(i,n){zct.style(n,{display:'none'});});
      
      zcbrt.Documents.adaptFilterPanel(e);
    };
    
    method = [name,'Item'].join('');
    zcbrt.Conf[method] = function(e) {
      var target = (yue.getTarget(e)||e);
      var dtid = target.id.split('_')[1];
      var rtid = zcbrt.currentId;
      
      var classes = zcbrt.Documents.getScope();
      var clss = yud.hasClass(target,'associate') ? 'associate':'unassociate';
      var index1 = zct.inArray(clss,classes);
      var index2 = !!index1 === true ? 0 : 1 ;
      
      var url = ['/',classes[index1],'Document/?rtid=',rtid,'&dtid=',dtid].join('');
      zct.doAjaxCall(url,[],function(o){
        var json = ylj.parse(o.responseText);
        yud.replaceClass(target,classes[index1],classes[index2]);
        zct.toggleClass(yud.getAncestorByTagName(target,'li'),'notBelong');
      });
    }
  });
  YAHOO.util.Event.onDOMReady(zcbrt.Documents.init);
}());

