(function(){
  var zcb = zenexity.libredemat.bong;
  var zct = zenexity.libredemat.tools;
  var zcv = zenexity.libredemat.Validation;
  var yud = YAHOO.util.Dom;
  var yu = YAHOO.util;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;

  zcb.categoryAgent = function() {
  
    var displayAgents =  function(o) {
      if (o.argument[0] === "All") {
        yud.addClass("viewAgents_All", "current");
        yud.removeClass("viewAgents_Category", "current");
      } else {
        yud.removeClass("viewAgents_All", "current");
        yud.addClass("viewAgents_Category", "current");
      }
      yud.get("categoryAgents").innerHTML = o.responseText;
    }
    
    function getAgentId(e) {
      return yud.getAncestorByTagName(yue.getTarget(e), 'li').id.replace("agent_", "");
    }
    
    function cancelAgentEditForm(agentId) {
      new yu.Element("agent_" + agentId).removeChild(yud.get("agentEditForm_" + agentId));
      // re-active add/edit item link button
      yud.removeClass(yus.query("a.currentEditItem", "agent_" + agentId, true), "currentEditItem");
    }
    
    //FIXME - simplify agent display update policy by ajax refresh for example
    function updateAgentProfile(agentId, action) {
      var currentLiEl = new yu.Element("agent_" + agentId);
       
      var profileSpanEls = yus.query("> span[class^=tag]", "agent_" + agentId);
      if  (profileSpanEls.length > 0)
        currentLiEl.removeChild(profileSpanEls[0]);
      
      if (action != "unassociate") {
        var cloneNewProfileSpanEl = 
          yud.getPreviousSibling(yus.query("input:checked", "agentEditForm_" + agentId, true)).cloneNode(true);
        currentLiEl.appendChild(cloneNewProfileSpanEl);
      }
    }
    
    function displayAgentEditForm(e) {
      var agentId = getAgentId(e);
      zct.doAjaxCall("/editAgent/" + "?id=" + zcb.categoryId + "&agentId=" + agentId, null, function(o) {
          var currentLiEl = yud.get("agent_" + agentId);
          currentLiEl.innerHTML += o.responseText;
          // unactive edit item link button
          yud.addClass(yus.query("a." + yue.getTarget(e).className, "agent_" + agentId, true), "currentEditItem");
      });
    }

    return {
      clickEvent : undefined,
      
      init: function() {
          zcb.categoryAgent.clickEvent = new zct.Event(zcb.categoryAgent, zcb.categoryAgent.getHandler);
          yue.on('categoryAgentsBox','click', zcb.categoryAgent.clickEvent.dispatch,
              zcb.categoryAgent.clickEvent, true);
      },
      
      getHandler : function(e) {
          var targetClassName = yue.getTarget(e).className
          if (targetClassName.indexOf('currentEditItem') > 0 )
            return 'currentEditItem';
          else
            return targetClassName.split(' ')[0];
      },
      
      associate : function(e) {
          displayAgentEditForm(e);
      },
      
      unassociate : function(e) {
          var agentId = getAgentId(e);
          var url = "/unassociateAgent/?categoryId=" + zcb.categoryId + "&agentId=" + agentId;
          zct.doAjaxCall(url, null, function(o) {
              var response = ylj.parse(o.responseText);
              if (response.status === "ok") {
                var currentLiEl = yud.get("agent_" + agentId);
                yud.addClass(currentLiEl, "notBelong");
                currentLiEl.removeChild(yus.query("a.unassociate", "agent_" + agentId, true));
                yud.replaceClass(yus.query("a.editItem", "agent_" + agentId, true), "editItem", "associate");
                updateAgentProfile(agentId, "unassociate");
              } else
                zct.Notifier.processMessage('modelError',response.success_msg);
          });
      },
      
      editItem : function(e) {
          displayAgentEditForm(e);
      },
      
      submitEditItem : function(e) {
          var agentId = getAgentId(e);
          if (!zcv.check(e, yud.get("agentEditForm_" + agentId + "Errors")))
            return;
          zct.doAjaxFormSubmitCall("agentEditForm_" + agentId, null, function(o) {
              var response = ylj.parse(o.responseText);
              if (response.status === "ok") {
                updateAgentProfile(agentId, "editItem");
                // remove item edit form
                cancelAgentEditForm(agentId);
                // remove cssClass 'notbelong' if necessary
                yud.removeClass("agent_" + agentId, "notBelong");
                // add unassociate button (TODO: it may be dangeourous if anchor element order is changed)
                var firstAnchorEl = yus.query("a", "agent_" + agentId, true);
                if (firstAnchorEl.className === "associate") {
                  cloneAnchorEl =  firstAnchorEl.cloneNode(false);
                  yud.replaceClass(cloneAnchorEl, cloneAnchorEl.className, "unassociate");
                  new yu.Element(cloneAnchorEl).appendTo(yud.get("agent_" + agentId), firstAnchorEl);
                  // transform "asociate button" in "editItem button"
                  yud.replaceClass(firstAnchorEl, firstAnchorEl.className, "editItem");
                }
              } else
                zct.Notifier.processMessage('modelError',response.success_msg);
        });
      },

      cancelEditItem : function(e) {
          cancelAgentEditForm(getAgentId(e));
      },

      viewAgents : function(e) {
        var scope = (yue.getTarget(e).id||'_').split('_')[1];
          zct.doAjaxCall("/agents/?id=" + zcb.categoryId + "&scope=" + scope, [scope], displayAgents);
      }
    };

  }();
  
  YAHOO.util.Event.onDOMReady(zcb.categoryAgent.init);
  
}());
