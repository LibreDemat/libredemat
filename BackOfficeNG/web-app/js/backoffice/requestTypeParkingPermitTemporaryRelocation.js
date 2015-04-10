zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype');
(function(){
    var zct = zenexity.libredemat.tools;
    var zcv = zenexity.libredemat.Validation;
    var zcbrt = zenexity.libredemat.bong.requesttype;
    var yud = YAHOO.util.Dom;
    var yue = YAHOO.util.Event;
    var ylj = YAHOO.lang.JSON;

    zcbrt.Pptrr = function() {
        return {
            init: function() {
                zcbrt.Conf.savePptrrPrices = zcbrt.Pptrr.savePrices;
                zcbrt.Conf.savePptrrDates = zcbrt.Pptrr.saveDates;
            },
            savePrices : function(e) {
                yue.preventDefault(e);
                var form = yud.get('pptrrPricesForm');
                var error = yud.get('pptrrPricesFormError');
                if(zcv.check(form,error)) {
                    var target = yue.getTarget(e);
                    zct.doAjaxFormSubmitCall(form.getAttributeNode("id").value,[],function(o){
                        var json = ylj.parse(o.responseText);
                        if(json.success_msg) {
                            zct.Notifier.processMessage('success',json.success_msg, null, target);
                        } else {
                            zct.Notifier.processMessage('error',json.error_msg, null, target);
                        }
                    });
                }
            },
            saveDates : function(e) {
                yue.preventDefault(e);
                var form = yud.get('pptrrDatesForm');
                var error = yud.get('pptrrDatesFormError');
                if(zcv.check(form,error)) {
                    var target = yue.getTarget(e);
                    zct.doAjaxFormSubmitCall(form.getAttributeNode("id").value,[],function(o){
                        var json = ylj.parse(o.responseText);
                        if(json.success_msg) {
                            zct.Notifier.processMessage('success',json.success_msg, null, target);
                        } else {
                            zct.Notifier.processMessage('error',json.error_msg, null, target);
                        }
                    });
                }
            }
        }
    }();
    YAHOO.util.Event.onDOMReady(zcbrt.Pptrr.init);
}());
