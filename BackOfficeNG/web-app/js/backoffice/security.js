(function(){
  var zcb = zenexity.libredemat.bong;
  var zcc = zenexity.libredemat.common;
  var zct = zenexity.libredemat.tools;
  var zcv = zenexity.libredemat.Validation;
  var yud = YAHOO.util.Dom;
  var yu = YAHOO.util;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;

  zcb.DisplayGroupEdit = function() {

    function manageAgent(targetEl) {
      var AgentId = targetEl.id.split('_')[1];
      var action = targetEl.id.split('_')[0];
      var url = '';

      url += ['/',action,'Agent',
              '?agentId=',AgentId].join('');

      zct.doAjaxCall(url, null, function(o) {
        var json = ylj.parse(o.responseText);
        if (json.status !== 'success') return false;

        var liYuiEl = new yu.Element('Agent_' + AgentId);
        if (action === "associate") {
          liYuiEl.removeClass('notBelong');
          yud.replaceClass(targetEl, action, "unassociate");
          targetEl.id = targetEl.id.replace('associate','unassociate');
        } else {
          liYuiEl.addClass('notBelong');
          yud.replaceClass(targetEl, action, "associate");
          targetEl.id = targetEl.id.replace('unassociate','associate');
        }

        var spanEl = yus.query("span.displayGroupName", "Agent_" + AgentId, true);
        if (!!spanEl && !YAHOO.lang.isArray(spanEl)) liYuiEl.removeChild(spanEl);
      });
    }

    return {
      clickEvent : undefined,

      init: function() {
        zcb.DisplayGroupEdit.clickEvent = new zct.Event(zcb.DisplayGroupEdit, zcb.DisplayGroupEdit.getHandler);
        yue.on('displayGroupAgentsBox','click', zcb.DisplayGroupEdit.clickEvent.dispatch, zcb.DisplayGroupEdit.clickEvent, true);
      },

      changeDisplayGroup : function() {
        yud.get("changeDisplayGroup").form.submit();
      },

      getHandler: function(e) {
        return yue.getTarget(e).id.split('_')[0];
      },

      associate: function(e) { manageAgent(yue.getTarget(e)); },

      unassociate: function(e) { manageAgent(yue.getTarget(e)); },

      displayAgents: function(o) {
        yud.removeClass(yus.query('a.current', "sortAgentForm", true), 'current');
        yud.addClass('viewAgents_' + o.argument[0], "current");
        yud.get("displayGroupAgentsWrapper").innerHTML = o.responseText;
      },

      viewAgents: function(e) {
        var scopeEl = yud.get("scope");
        scopeEl.value = yue.getTarget(e).id.split('_')[1]
        zct.doAjaxFormSubmitCall ("sortAgentForm",[scopeEl.value] , zcb.DisplayGroupEdit.displayAgents);
      }

    };

  }();

  YAHOO.util.Event.onDOMReady(zcb.DisplayGroupEdit.init);

}());
