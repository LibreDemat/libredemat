zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype');
(function(){
    var zct = zenexity.libredemat.tools;
    var zcv = zenexity.libredemat.Validation;
    var zcbrt = zenexity.libredemat.bong.requesttype;
    var yud = YAHOO.util.Dom;
    var yue = YAHOO.util.Event;
    var ylj = YAHOO.lang.JSON;

    zcbrt.Pptwr = function() {
        return {
            init: function() {
                zcbrt.Conf.savePptwrPrices = zcbrt.Pptwr.savePrices;
                zcbrt.Conf.savePptwrDates = zcbrt.Pptwr.saveDates;
            },
            savePrices : function(e) {
                yue.preventDefault(e);
                var form = yud.get('pptwrPricesForm');
                var error = yud.get('pptwrPricesFormError');
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
                var form = yud.get('pptwrDatesForm');
                var error = yud.get('pptwrDatesFormError');
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
    YAHOO.util.Event.onDOMReady(zcbrt.Pptwr.init);
}());
