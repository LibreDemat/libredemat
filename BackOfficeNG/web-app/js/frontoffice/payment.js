zenexity.libredemat.tools.namespace('zenexity.libredemat.fong');

(function(){

  var zcf = zenexity.libredemat.fong;
  var zcv = zenexity.libredemat.Validation;  
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  var zcc = zenexity.libredemat.common;
  var zct = zenexity.libredemat.tools;
  var yu = YAHOO.util;
  var yue = YAHOO.util.Event;
  var yuel = YAHOO.util.Element;
  var yw = YAHOO.widget;
  var yus = YAHOO.util.Selector;
  var ylj = YAHOO.lang.JSON;
	var yl = YAHOO.lang;
	var yu = YAHOO.util;

  zcf.Payment = function() {
    return {
      init : function() {

        var path = zenexity.libredemat.baseUrl.split('/');
        path.pop();
        url = path.join("/");

        billPanel = new yw.SimpleDialog("showBill", { // feed variable panel
            width: "700px", 
            fixedcenter: true, 
            constraintoviewport: true, 
            modal:true,
            underlay: "none", 
            close: false, 
            visible: false, 
            draggable: true,
            buttons : [ {text:"Fermer", handler: zcf.Payment.handleClose, isDefault:true} ]
        });	

        billPanel.render();

        resize = new yu.Resize('showBill', {
            handles: ['br'],
            autoRatio: true,
            status: true,
            minWidth: 780,
            minHeight: 500
        });

        resize.on('resize', function(args) { 
          var panelHeight = args.height;
          this.cfg.setProperty("height", panelHeight + "px"); 
          var getFrame = yud.get("embedpdf");
          yud.setStyle(getFrame, "height", (panelHeight - 120) + "px");
        }, billPanel, true); 
        // hack inexine to show bill end


        zcv.putRules({'money': new zcv.rule('regex', /^\d+(\.\d{1,2})?$/)});
        yue.on('ticketingContracts','submit', zcf.Payment.checkAndSubmit);
        yue.on('depositAccounts','submit', zcf.Payment.checkAndSubmit);

        // hack inexine to show bill begin
        zcf.Payment.clickEventBill = new zct.Event(zcf.Payment, zcf.Payment.showBill);
        yue.on(yus.query('.bill'),'click', zcf.Payment.clickEventBill.dispatch, zcf.Payment.clickEventBill,true);
        // hack inexine to show bill end
      },
      checkAndSubmit : function(e) {
        yue.stopEvent(e);
        var formEl = yue.getTarget(e);
        var errorEl = yud.getElementsByClassName('error','p', formEl)[0];
        if (zcv.check(formEl, errorEl)) formEl.submit();
      },
      showBill : function(e){
        var targetEl = yue.getTarget(e);
        var tokens = targetEl.id.split('_');
        billPanel.setBody('<div class="wait"><img src="'+url+'/../../images/spinner.gif" class="spinner"/> patientez...</div>');
        var queryString = "fileName="+tokens[0]+"&fileUrl="+tokens[1];
        zct.doAjaxCall(url + "/showBill?"+ queryString, null, function(o){
          billPanel.setBody(o.responseText);
        },true);		  
        billPanel.show();
      },
      handleClose : function(){
        this.hide();
      }
    };
  }();
  yue.onDOMReady(zcf.Payment.init);
}());
