zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.document');

(function(){

  var zc = zenexity.libredemat;
  var zcb = zenexity.libredemat.bong;
  var zca = zenexity.libredemat.aspect;
  var zct = zenexity.libredemat.tools;
  var zcv = zenexity.libredemat.Validation;
  var zcbd = zenexity.libredemat.bong.document;
  
  // TODO remove
  var zcbr = zenexity.libredemat.bong.request;
  
  var yw = YAHOO.widget;
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;
  
  zcbd.Instruction = function() {
    var initWidgets = function() {
      zcbd.Instruction.panel = new yw.Panel(
        'documentPanel',{
          width: '800px', y: 120,visible: false,
          constraintoviewport: false, draggable: true,
          underlay: 'shadow', close: true
        });
      zcbd.Instruction.panel.render();
      zcbd.Instruction.confirmDialog = new zct.ConfirmationDialog({
        head : 'Attention',
        body : 'Voulez-vous supprimer la page courante ?'
      },zcbd.Instruction.deletePage);
      
      zcbd.Instruction.overlay = new yw.Overlay('documentStateOverlay',{visible:false});
      zcbd.Instruction.overlay.render();
    };
    var initEvents = function() {
      var clicks = yus.query('#documentPanel .bd')
        .concat([yud.get('documentStateOverlay')])
        .concat(yus.query('ul.document-list'));
      
      var event = new zct.Event(zcbd.Instruction,zcbd.Instruction.extractHandler);
      yue.on(clicks,'click',event.dispatch,event,true);
      
      zcbd.Instruction.panel.hideEvent.subscribe(zcbd.Instruction.cancelDocumentState,zcbd.Instruction.panel,true);
    };
    return {
      /**
       * @description Document widget panel
       */
      panel : undefined,
      overlay: undefined,
      /**
       * @description Document tab view
       */
      tabView : undefined,
      dataTabView : undefined,
      confirmDialog: undefined,
      /**
       * @description Document href of clicked link
       */
      docUrl : undefined,
      listUrl : undefined,
      responseText : undefined,
      /**
      * @description Don't forget to comment each of your method
      */
      init : function() {
        zcbd.Instruction.listUrl =
            zc['contextPath']
            + '/backoffice/userDocumentInstruction/documentsList?'
            + 'homeFolderId=' + zcb.homeFolder.Details.homeFolderId;
        initWidgets();
        initEvents();
        zcbd.Instruction.refreshList();
        zca.advise('toggleStateOverlay',new zca.Advice('beforeSuccess',zcbr.Permission.validateAgent),zcbd.Instruction);
      },
      extractHandler : function(e) {
        var target = yue.getTarget(e);
        if(zct.nodeName(target,'img')||(!target.id && !target.name)) target = target.parentNode;
        if(target.name) return ['proceed',zct.capitalize(target.name)].join('');
        else return (target.id||'').split('_')[0];
      },
      refreshList : function(shrt) {
        var url = zcbd.Instruction.listUrl;
        var listElem = yud.get('fullDocumentList');
        if(listElem){
          zct.doAjaxCall(url,[],function(o){listElem.innerHTML = o.responseText;},true);
        }
      },
      displayDocPanel : function(e,json) {
        zcbd.Instruction.cancelDocumentState();
        if(e) {
          yue.preventDefault(e);
          var target = yue.getTarget(e);
          zcbd.Instruction.docUrl = target.href;
        } else if(!!json['newDocumentId']) {
          zcbd.Instruction.docUrl = zcbd.Instruction.docUrl.replace('edit/0?',['edit/',json['newDocumentId'],'?'].join(''));
          zcbd.Instruction.refreshList();
        } else {
          zcbd.Instruction.refreshList();
        }
        
        zct.doAjaxCall(zcbd.Instruction.docUrl,[],function(o) {
          zcbd.Instruction.panel.setBody(o.responseText);
          zcbd.Instruction.panel.show();
          
          zcbd.Instruction.tabView = new yw.TabView("documentData");
          zcbd.Instruction.dataTabView = new yw.TabView("documentMetaData");
          
          if(json) {
            zcbd.Instruction.notify(json);
            if(json['pageNumber']) zcbd.Instruction.tabView.set('activeIndex', parseInt(json['pageNumber']));
          }
        },true);
      },
      displayEditPanel : function(e) {
        var panel = zcbd.Instruction.getRelatedEl(yue.getTarget(e),'pageFormPanel_'),style = {}, val = '';
        val = zct.style(panel,'display').display;
        style.display = val != 'none' ? 'none' : 'block';
        zct.style(panel,style);
      },
      doNothing : function(e) {yue.stopEvent(e);},
      confirmDeletePage : function(e) { zcbd.Instruction.confirmDialog.show(e);},
      notify : function(json) {zct.Notifier.processMessage(json.status,json.message,'documentMessage');},
      deletePage : function(e,se) {
        var form = zcbd.Instruction.getRelatedEl(yue.getTarget(se),'pageDeleteForm_');
        zct.doAjaxFormSubmitCall(form.id,[],function(o){
          zcbd.Instruction.displayDocPanel(undefined,ylj.parse(o.responseText));
        });
      },
      getRelatedEl : function(t,prefix) {
        var target = zct.nodeName(t,'img') ? t.parentNode : t;
        return yud.get([prefix,target.id.split('_')[1]].join(''));
      },
      disableUploadPanel :function (form) {
        form['pageModif'].disabled = 'disabled';
        zct.style(yus.query('.routine-indicator',form,true),{'display':'inline'});
      },
      validate : function(form) {
        zct.style(yus.query('.error',form.parentNode)[0],{display:'none'});
        var factor = form['pageFile'].value.length > 0;
        if(!factor) zct.style(yus.query('.error',form.parentNode)[0],{display:'block'});
        return factor;
      },
      proceedPageModif : function(e) {
        var form = yud.getAncestorByTagName(yue.getTarget(e),'form');
        if(zcbd.Instruction.validate(form)) {
          zcbd.Instruction.disableUploadPanel(form);
          zct.doAjaxFormSubmitCall(form.id,[],function(o){
            var json = ylj.parse(o.responseText);
            if(!json.pageNumber) json.pageNumber = zcbd.Instruction.tabView.get('activeIndex');
            zcbd.Instruction.displayDocPanel(undefined,json);
          },true);
        }
      },
      cancelDocumentState: function(){
        zcbd.Instruction.overlay.hide();
      },
      changeDocumentState : function(e) {
        if (!zcv.check(e, yud.get('documentStateFormErrors')))
          return false;
        zct.doAjaxFormSubmitCall('documentStateForm',[],function(o){
          var json = ylj.parse(o.responseText);
          zcbd.Instruction.displayDocPanel(undefined,json);
          zcbd.Instruction.refreshActions();
        });
      },
      refreshActions : function() {
        var actions = zcbd.Instruction.dataTabView.get("tabs")[0];
        var cacheData = actions.get("cacheData");
        var contentVisible = actions.get("contentVisible");
        actions.set("cacheData", false);
        actions.set("contentVisible", false);
        actions.set("contentVisible", true);
        actions.set("contentVisible", contentVisible);
        actions.set("cacheData", cacheData);
      },
      toggleStateOverlay: function(e) {
        if(zcbd.Instruction.overlay.cfg.getProperty('visible')) {
          zcbd.Instruction.cancelDocumentState();
          return false;
        }
        
        var target = yue.getTarget(e).id ? yue.getTarget(e) : yue.getTarget(e).parentNode;
        var id = target.id.split('_')[1];
        var url = [zc['contextPath'],'/backoffice/userDocumentInstruction/states/',id].join('');
        var css= zct.grep(target.className.split(' '),function(n){return /tag-.*/.test(n);})[0];
        
        zct.removeClass('documentStateOverlay',/overlay-.*/);
        yud.addClass(yud.get('documentStateOverlay'),css.replace('tag-','overlay-'));
        
        zct.doAjaxCall(url,[],function(o){
          zcbd.Instruction.overlay.cfg.setProperty('context',[target.id, "tl", "bl"]);
          zcbd.Instruction.overlay.setBody(o.responseText);
          zcb.Calendar("endValidityDate");
          zcbd.Instruction.overlay.show();
        },true);
      },
      modifyAgentNote : function(e) {
        var target = yue.getTarget(e);
        zct.doAjaxFormSubmitCall(target.parentNode.id,[],function(o){
          var json = ylj.parse(o.responseText);
          if (json)
            zct.Notifier.processMessage(json.status,json.message,'documentInformationtMsg');
        });
      },

      // FIXME : dirty hack to manage baseUrl and Ajax (dis-) utilities
      removeDocument : function(e) {
        var target = yue.getTarget(e);
        var id = target.id.split('_')[1];
        new zct.ConfirmationDialog(
          { head : 'Attention !',
            body : 'Souhaitez-vous réellement supprimer ce document associé à la demande ?' },
          function() {
            var currentBaseUrl = zenexity.libredemat.baseUrl;
            zenexity.libredemat.baseUrl = 
                zc['contextPath']
                + '/backoffice/userDocumentInstruction';

            zct.doAjaxDeleteCall("/removeDocument","homeFolderId=" + zcb.homeFolder.Details.homeFolderId
              + "&documentId=" + id, function(o) {
              var json = ylj.parse(o.responseText);
              if (json.status == "ok") {
                zct.Notifier.processMessage('success', json.success_msg);
                zcb.document.Instruction.refreshList();
              }
            });
            zenexity.libredemat.baseUrl = currentBaseUrl;
          }).show(e);
      },
      rotate : function(e) {
        var params = yue.getTarget(e).parentNode.id.split('_');
        zenexity.libredemat.baseUrl = zc["contextPath"] + "/backoffice/userDocumentInstruction";
        zct.doAjaxCall("/rotate/" + params[1] + "?index=" + params[2] + "&trigonometric=" + params[3], null, function(o) {
          var json = ylj.parse(o.responseText);
          if (!json.pageNumber) json.pageNumber = zcbd.Instruction.tabView.get("activeIndex");
          zcbd.Instruction.displayDocPanel(undefined, json);
        });
      }
    };
  }();

  yue.onDOMReady(zcbd.Instruction.init);
  
}());
