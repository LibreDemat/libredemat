/**
 * @description Client-side module for statistics features
 *
 * @namespace zenexity.libredemat.bong.statistics
 * @author bor@zenexity.fr
 */

zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.statistics');

(function(){

  var zct = zenexity.libredemat.tools;
  var zcb = zenexity.libredemat.bong;
  var zcbs = zenexity.libredemat.bong.statistics;
  
  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = yu.Event;
  var yus = yu.Selector;
  var ylj = YAHOO.lang.JSON;

  zcbs.Statistics = function() {

    var initCalendars = function() {
      zcb.Calendar("startDate");
      zcb.Calendar("endDate");
    };

    return {
      /**
       * @description Page state descriptor
       */
      pageState : undefined,
      init : function() {
        initCalendars();
        zcbs.Statistics.pageState = ylj.parse(yud.get('pageState').value);
        yue.on(yud.get('filterDisplay'),'click',zcbs.Statistics.filterDisplay);
      },
      /**
       * @description Perform statistics display filters
       */
      filterDisplay : function(e) {
        zcbs.Statistics.saveState();
        yud.get('filterForm').submit();
      },
      /**
       * @description Retrieve and save page state to input element
       */
      saveState : function() {
        zct.each(yus.query('.persistent'), function() {
            zcbs.Statistics.pageState[this.name] = zct.val(this);
        });
        yud.get('pageState').value = ylj.stringify(zcbs.Statistics.pageState);
      }
    }
  }();
  
  yue.onDOMReady(zcbs.Statistics.init);
  
}());
