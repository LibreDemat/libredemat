zenexity.libredemat.tools.namespace("zenexity.libredemat.bong.external");
(function(){
  var ylj = YAHOO.lang.JSON;
  var yu = YAHOO.util;
  var yue = yu.Event;
  var yud = yu.Dom;
  var yus = yu.Selector;
  var yw = YAHOO.widget;
  var zcb = zenexity.libredemat.bong;
  var zcbe = zcb.external;
  var zct = zenexity.libredemat.tools;
  var zcv = zenexity.libredemat.Validation;
  zcbe.Search = function() {
    return {
      before: function() {}, // extension point
      init: function() {
        zcbe.Search.before();
        if (yud.get("dateFrom")) {
          zcb.Calendar("dateFrom")
          zcb.Calendar("dateTo")
        }
        var myPaginator = new yw.Paginator({
          containers : ["pagination-top", "pagination-bottom"],
          rowsPerPage : 25,
          totalRecords : parseInt(yud.get("totalRecords").value),
          recordOffset : parseInt(yud.get("recordOffset").value),
          template : "{FirstPageLink} {PreviousPageLink} <span>{CurrentPageReport}</span> {NextPageLink} {LastPageLink}",
          previousPageLinkLabel : "&lt;",
          firstPageLinkLabel : "&lt;&lt;",
          nextPageLinkLabel : "&gt;",
          lastPageLinkLabel : "&gt;&gt;",
          pageReportTemplate : "R&eacute;sultats {startRecord} &agrave; {endRecord} sur {totalRecords}"
        });
        myPaginator.subscribe("changeRequest", zcbe.Search.handlePaginatorChange);
        myPaginator.render();
        yue.on(yus.query("input[type*=radio]"), "click",  zcbe.Search.sortSearch);
        yue.on(yus.query("select[id*=Filter]"), "change", zcbe.Search.filterSearch);
        yue.on(yud.get("resendButton"), "click", zcbe.Search.resend);
      },
      sortSearch : function(e) {
        yud.get("sortBy").value = yue.getTarget(e).getAttribute("name");
        yud.get("searchForm").submit();
      },
      filterSearch : function(e) {
        var filterBy = yue.getTarget(e).getAttribute("id");
        yud.get("filterBy").value = [
          yud.get("filterBy").value, "@", filterBy, "=", yud.get(filterBy).value
        ].join('');
        yud.get("searchForm").submit();
      },
      handlePaginatorChange : function(state) {
        yud.get("recordOffset").value = state.recordOffset;
        var newNode;
        if(yud.get("paginatorChange")== null){
          newNode = document.createElement('input');
          newNode.name = 'paginatorChange';
          newNode.id = 'paginatorChange';
          newNode.type = 'hidden';
          yud.insertAfter(newNode,yud.get("recordOffset"));
        }
        yud.get("paginatorChange").value = true;
        if(yud.get("results")== null){
          newNode = document.createElement('input');
          newNode.name = 'results';
          newNode.id = 'results';
          newNode.type = 'hidden';
          yud.insertAfter(newNode,yud.get("recordOffset"));
        }
        yud.get("results").value = 25;
        yud.get("searchForm").submit();
      },
      resend : function(e) {
        yue.stopEvent(e);
        var cont = yud.get("sendRequestsFormErrors");
        cont.innerHTML = "";
        if (zcv.check(yud.get("sendRequestsForm"), cont)) {
          var target = yue.getTarget(e);
          zct.doAjaxFormSubmitCall("sendRequestsForm", [], function(o) {
            var response = ylj.parse(o.responseText);
            if (response.status === "ok") {
              yud.get("resendContainer").innerHTML = ylj.parse(o.responseText).success_msg;
            } else {
              yud.addClass(yud.get("notificationEmail"), "validation-failed");
              cont.innerHTML = response.error_msg;
            }
          });
        }
      }
    };
  }();
  yue.onDOMReady(zcbe.Search.init);
}());
