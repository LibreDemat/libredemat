(function(){
  var zc = zenexity.capdemat;
  var zcb = zenexity.capdemat.bong;
  var zct = zenexity.capdemat.tools;
  var ylj = YAHOO.lang.JSON;
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  zcb.agentEdit = function() {
    return {
      init : function() {
      var url = [zc['contextPath'],'/backoffice/category/categories/?id=',zcb.agentId,'&scope=Agent'].join('');
        zct.doAjaxCall(
          url,
          [zcb.agentId],
          function(o) {
            yud.get('agentCategories').innerHTML = o.responseText;
          },
          true);
        yue.on("agentId", "change", zcb.agentEdit.changeAgent);
        yue.on("saveProfile", "click", zcb.agentEdit.editProfile);
      },
      editProfile : function(e) {
        var target = yue.getTarget(e);
        zct.doAjaxFormSubmitCall("agentProfileForm", null, function(o) {
          zct.Notifier.processMessage('success', ylj.parse(o.responseText).success_msg, null, target);
        });
      },
      changeAgent : function() {
        yud.get("agentId").form.submit();
      }
    };
  }();
  YAHOO.util.Event.onDOMReady(zcb.agentEdit.init);
}());
