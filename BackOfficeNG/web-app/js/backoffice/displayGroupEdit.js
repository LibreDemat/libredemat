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

    function manageRequestType(targetEl) {
      var requestTypeId = targetEl.id.split('_')[1];
      var action = targetEl.id.split('_')[0];
      var url = '';

      url += ['/',action,'RequestType/',
              '?displayGroupId=',zcb.DisplayGroupEdit.displayGroupId,
              '&requestTypeId=',requestTypeId].join('');

      zct.doAjaxCall(url, null, function(o) {
        var json = ylj.parse(o.responseText);
        if (json.status !== 'success') return false;
        
        var liYuiEl = new yu.Element('requestType_' + requestTypeId);
        if (action === "associate") {
          liYuiEl.removeClass('notBelong');
          yud.replaceClass(targetEl, action, "unassociate");
          targetEl.id = targetEl.id.replace('associate','unassociate');
        } else {
          liYuiEl.addClass('notBelong');
          yud.replaceClass(targetEl, action, "associate");
          targetEl.id = targetEl.id.replace('unassociate','associate');
        }

        var spanEl = yus.query("span.displayGroupName", "requestType_" + requestTypeId, true);
        if (!!spanEl && !YAHOO.lang.isArray(spanEl)) liYuiEl.removeChild(spanEl);
      });
    }

    return {
      clickEvent : undefined,

      init: function() {
        zcb.DisplayGroupEdit.clickEvent = new zct.Event(zcb.DisplayGroupEdit, zcb.DisplayGroupEdit.getHandler);
        yue.on('displayGroupRequestTypesBox','click', zcb.DisplayGroupEdit.clickEvent.dispatch, zcb.DisplayGroupEdit.clickEvent, true);
        yue.on('displayGroupForm','submit', zcb.DisplayGroupEdit.saveDisplayGroup);
        yue.on('saveLogo','click', zcb.DisplayGroupEdit.saveLogo);
        yue.on('orderRequestTypeBy','change', zcb.DisplayGroupEdit.sortRequestTypes);
        yue.on("changeDisplayGroup", "change", zcb.DisplayGroupEdit.changeDisplayGroup);
      },

      changeDisplayGroup : function() {
        yud.get("changeDisplayGroup").form.submit();
      },

      getHandler: function(e) { 
        return yue.getTarget(e).id.split('_')[0]; 
      },

      associate: function(e) { manageRequestType(yue.getTarget(e)); },

      unassociate: function(e) { manageRequestType(yue.getTarget(e)); },

      displayRequestTypes: function(o) {
        yud.removeClass(yus.query('a.current', "sortRequestTypeForm", true), 'current');
        yud.addClass('viewRequestTypes_' + o.argument[0], "current");
        yud.get("displayGroupRequestTypesWrapper").innerHTML = o.responseText;
      },  

      viewRequestTypes: function(e) {
        var scopeEl = yud.get("scope");
        scopeEl.value = yue.getTarget(e).id.split('_')[1]
        zct.doAjaxFormSubmitCall ("sortRequestTypeForm",[scopeEl.value] , zcb.DisplayGroupEdit.displayRequestTypes);
        setTimeout(function(){
          initDragAndDrop();
        },500);
      },

      sortRequestTypes: function(e) {
        if (yue.getTarget(e).value != "")
        {
          zct.doAjaxFormSubmitCall("sortRequestTypeForm", [yud.get("scope").value],zcb.DisplayGroupEdit.displayRequestTypes);
          setTimeout(function(){
            initDragAndDrop();
          },500);
        }
        },

      saveDisplayGroup: function(e) {
        yue.preventDefault(e);
        if (!zcv.check(e, yud.get('displayGroupFormErrors')))
          return false;
        var target = yue.getTarget(e);
        zct.doAjaxFormSubmitCall('displayGroupForm',[],function(o){
          var json = ylj.parse(o.responseText);
          if (json.status === 'success' && zcb.DisplayGroupEdit.editMode === 'create')
            window.location = zenexity.libredemat.baseUrl + '/edit/' + json.id;
          else {
            yud.get('logoForm').name.value = json.displayGroupName;
            zct.Notifier.processMessage(json.status, json.message, null, target);
          }
        });
      },

      saveLogo: function(e) {
        yue.preventDefault(e);
        var target = yue.getTarget(e);
        zct.doAjaxFormSubmitCall('logoForm',[],function(o){
          var json = ylj.parse(o.responseText);
          yud.get('logoImg').src = [zcb.DisplayGroupEdit.ressourceBaseUrl, yud.get('logoForm').name.value,
                                    '&rand=', json.rand].join('');
          zct.Notifier.processMessage(json.status, json.message, null, target);
        }, true);
      }

    };

  }();
  
  YAHOO.util.Event.onDOMReady(zcb.DisplayGroupEdit.init);

}());

/// Chargement de jquery

$(document).ready(function()
{
	initDragAndDrop();
});

function initDragAndDrop()
{
	var adjustment;

	$("ul#displayGroupRequestTypes").sortable({
	  group: 'displayGroupRequestTypes',
	  pullPlaceholder: false,
	  // animation on drop
	  onDrop: function  ($item, container, _super) {
	    var $clonedItem = $('<li/>').css({height: 0});
	    $item.before($clonedItem);
	    $clonedItem.animate({'height': $item.height()},150);

	    $item.animate($clonedItem.position(), function  () {
	      $clonedItem.detach();
	      _super($item, container);
	    });
	    sendPosition();
	  },

	  // set $item relative to cursor position
	  onDragStart: function ($item, container, _super) {
	    var offset = $item.offset(),
	        pointer = container.rootGroup.pointer;

	    adjustment = {
	      left: pointer.left - offset.left,
	      top: pointer.top - offset.top
	    };

	    _super($item, container);
	  },
	  onDrag: function ($item, position) {
	    $item.css({
	      'position' : 'absolute',
	      'height' : '15px',
	      left: position.left - adjustment.left,
	      top: position.top - adjustment.top
	    });
	  }
	});
}

function sendPosition()
{
	var tab  = [];
	var i = 0;
	$("ul#displayGroupRequestTypes li.item").each(function()
	{
		tab.push({
			'id' : $(this).data("id"),
			'weight' : i
		});
		i++;
	});
	$.ajax({
		url : zenexity.libredemat.baseUrl+'/displayOrder',
		type : 'POST',
		data : 'json='+JSON.stringify(tab)
	});
}
