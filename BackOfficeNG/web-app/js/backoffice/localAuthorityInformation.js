zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.localauthority');
(function(){
  var zcb = zenexity.libredemat.bong;
  var zcbl = zenexity.libredemat.bong.localauthority;
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  zcbl.Information = function() {
    return {
      init : function() {
        yud.getElementsBy(
          function(el) { return /(.*)Box/.test(el.id); },
          null,
          yud.get("yui-main"),
          function(box) {
            zcb.Editor.init(/(.*)Box/.exec(box.id)[1]);
          }
        );
      }
    };
  }();
  yue.onDOMReady(zcbl.Information.init);
}());
