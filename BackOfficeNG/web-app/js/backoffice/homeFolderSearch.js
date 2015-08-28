
/**
 * @description This file contains homefolder search client-module
 * @namespace zenexity.libredemat.bong.homeFolder
 * 
 * @author vba@zenexity.fr
 */

zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.homeFolder');

(function(){

  var zct = zenexity.libredemat.tools;
  var zcbh = zenexity.libredemat.bong.homeFolder;
  
  var yl = YAHOO.lang;
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = yu.Event;
  var yus = yu.Selector;
  var ylj = yl.JSON;
  
  
  zcbh.Search = function() {
    var initControls = function() {
      zcbh.Search.paginator = new YAHOO.widget.Paginator({
        containers: ['pagination-top','pagination-bottom'],
        rowsPerPage : parseInt(yud.get('currentMax').value),
        totalRecords: parseInt(yud.get('currentCount').value),
        recordOffset: parseInt(yud.get('currentOffset').value),
        template : "{FirstPageLink} {PreviousPageLink} <span>{CurrentPageReport}</span> {NextPageLink} {LastPageLink}",
        previousPageLinkLabel : '&lt;',
        firstPageLinkLabel : '&lt;&lt;',
        nextPageLinkLabel : '&gt;',
        lastPageLinkLabel : '&gt;&gt;',
        pageReportTemplate : 'R&eacute;sultats {startRecord} &agrave; {endRecord} sur {totalRecords}'
      });
      
      zcbh.Search.paginator.subscribe('changeRequest', function(state){
        yud.get('currentOffset').value = state.recordOffset;
        zcbh.Search.doSearch();
      });
      
      zcbh.Search.paginator.render();

      yue.on(yud.get('searchForm'), 'submit', function(e) {
        yud.get('searchForm').action = yud.get('searchAction').value;
        zcbh.Search.doSearch(e);
      });
    };

    return {
      /**
       * @description Page state descriptor
       */
      pageState : undefined,
      /**
       * @description Search results paginator
       */
      paginator : undefined,
      /**
      * @description Initialize current module
      */
      init : function() {
        zcbh.Search.pageState = ylj.parse(yud.get('pageState').value);
        yue.on(yus.query('.sort'),'change',zcbh.Search.doSearch);
        yue.on(yus.query('.filter'),'change', function(e) {
          yud.get('searchForm').action = yud.get('searchAction').value;
          zcbh.Search.doSearch(e);
        });
        initControls();
        zcbh.Search.initSecurityRule(zcbh.Search.agentCanWrite);
      },
      initSecurityRule : function(canWrite) {
        if (!canWrite) {
          var div = yud.get("createAccount");
          div.parentNode.removeChild(div);
        }
      },
      /**
       * @description Retrives and save page state to input element
       */
      saveState : function() {
        zct.each(yus.query('.persistent'),function(){zcbh.Search.processValue(this);});
        yud.get("pageState").value = ylj.stringify(zcbh.Search.pageState);
      },
      /**
       * @description Applays user defined/default method for element processing
       * @param el {HTMLElement} element to process
       */
      processValue : function(el) {
        var method = ['process',zct.capitalize(el.type.toLowerCase().replace('-','')||'none')].join('');
        if(!zct.tryToCall(zcbh.Search[method],zcbh.Search,el)) 
          zcbh.Search.pageState[el.name] = zct.val(el);
      },
      /**
       * @see zenexity.libredemat.bong.homeFolder.Search.processValue
       */
      processRadio: function(el) {
        if(el.checked) zcbh.Search.pageState[el.name] = zct.val(el);
        return true;
      },
      /**
       * @see zenexity.libredemat.bong.homeFolder.Search.processValue
       */
      processCheckbox: function(el) {
        if(el.checked) zcbh.Search.pageState[el.name] = zct.val(el);
        else delete zcbh.Search.pageState[el.name];
        return true;
      },
      /**
       * @description Produce home folder search
       */
      doSearch : function(e) {
        zcbh.Search.saveState();
        if(!e || yue.getTarget(e).nodeName != 'form') yud.get('searchForm').submit();
      }
    }
  }();
  
  yue.onDOMReady(zcbh.Search.init);
  
}());
