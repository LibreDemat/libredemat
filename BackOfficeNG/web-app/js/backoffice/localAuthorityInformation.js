zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.localauthority');
(function(){
  var zcb = zenexity.libredemat.bong;
  var zcbl = zenexity.libredemat.bong.localauthority;
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  var zct = zenexity.libredemat.tools;

  zcbl.Information = function() {
    return {
      init : function() {
        var editors = yud.getElementsBy(function(e){return true },'textarea');
        zct.each(editors, function(index,el){
          var ck = CKEDITOR.replace(el.id, { customConfig: 'libredemat-config.js' })
          var messageId= el.id.split('Editor')[0];
          var submitId = messageId + 'Button';
          var formId = messageId + 'Form';
          var notifierId = messageId + 'Notifier';
          yue.on(yud.get(submitId), 'click', function(event) {
          event.preventDefault();
            var oncesaved = function(response) {
              zct.Notifier.processMessage( 'success', response.responseText, yud.get(notifierId), null);
            };
            var params = zct.param({ 'editor': ck.getData(), 'id': messageId });
            zct.doAjaxPostCall('/information', params, oncesaved);
          });
        });
      }
    };
  }();
  yue.onDOMReady(zcbl.Information.init);
}());
