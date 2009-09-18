zenexity.capdemat.tools.namespace('zenexity.capdemat.bong');
(function() {
  var zcb = zenexity.capdemat.bong;
  var zct = zenexity.capdemat.tools;
  var yud = YAHOO.util.Dom;
  var yue = YAHOO.util.Event;
  var ylj = YAHOO.lang.JSON;
  var yw = YAHOO.widget;
  zcb.Contact = function() {
    var panel = undefined;
    var widgets = {
      "Mail" :
        { recipient : undefined, message : "template", validation : "trace"},
      "Email" :
        { recipient : "email", message : "template", validation : "send"},
      "HomePhone" :
        { recipient : "homePhone", message : undefined, validation : "trace"},
      "OfficePhone" :
        { recipient : "officePhone", message : undefined, validation : "trace"},
      "MobilePhone" :
        { recipient : "mobilePhone", message : undefined, validation : "trace"},
      "Sms" :
        { recipient : "mobilePhone", message : "sms", validation : "send"},
      "LocalAuthorityOffice" :
        { recipient : undefined, message : "template", validation : "trace"}
    };
    return {
      clickEv : undefined,
      init : function(link, container, url) {
        panel = new yw.Panel(
          container,
          { width: "650px",
            visible: false,
            constraintoviewport: false, draggable: true,
            underlay: "shadow", close: true
          });
        panel.render();
        zct.doAjaxCall(url + "/" + zcb.requestId, null, function(o) {
          panel.setBody(o.responseText);
          zcb.Contact.switchMoC();
          yud.getElementsBy(
            function(el) { return /.*Notifier/.test(el.id); },
            null,
            container,
            function(notifier) {
              var field = yud.get(/(.+)Notifier/.exec(notifier.id)[1]);
              yue.on(field, "keyup", function(e) {
                zct.limitArea(yue.getTarget(e), field.getAttribute("maxlength"), notifier.id);
              });
              zct.limitArea(field, field.getAttribute("maxlength"), notifier.id);
            }
          );
          yue.addListener(link, "click", function(e) {
            yue.preventDefault(e);
            panel.show();
          });
        }, true);
        zcb.Contact.clickEv = new zct.Event(zcb.Contact, zcb.Contact.processClick);
        yue.on(container, "click", zcb.Contact.clickEv.dispatch, zcb.Contact.clickEv, true);
      },
      processClick : function(e) {
        return (yue.getTarget(e).id||'_').split('_')[0];
      },
      switchMoC : function(e) {
        zcb.Contact.switchField("recipient");
        zcb.Contact.switchField("message");
        zcb.Contact.switchField("validation");
      },
      switchField : function(field) {
        var fieldset = yud.get(field + "Field");
        var currentWidgets = yud.getChildrenBy(fieldset, function(child) {
          return /.*Widget/.test(child.id);
        });
        zct.each(currentWidgets, function(i, child) {
          child.disabled = "disabled";
          zct.style(child, { display : "none" });
        });
        zct.style(fieldset, { display : "none" });
        var widgetName = widgets[zct.val(yud.get("meansOfContact"))][field];
        if (widgetName) {
          var widget = yud.get(widgetName + "Widget");
          widget.disabled = undefined;
          zct.style(widget, { display : "" });
          zct.style(fieldset, { display : "" });
        }
      },
      hide : function() {
        panel.hide();
      },
      preview : function() {
        var form = yud.get("contactForm");
        var cont = yud.get("contactFormErrors");
        var postURL = form.action;
        form.target = "_blank";
        form.action = zct.val(yud.get("contactPreviewURL"));
        cont.innerHTML = "";
        if (FIC_checkForm(form, cont)) {
          form.submit();
        }
        form.target = undefined;
        form.action = postURL;
      },
      send : function() {
        var cont = yud.get("contactFormErrors");
        cont.innerHTML = "";
        if (FIC_checkForm(yud.get("contactForm"), cont)) {
          zct.doAjaxFormSubmitCall("contactForm", [], zcb.Contact.notify);
        }
      },
      notify : function(o) {
        zct.Notifier.processMessage("success",
          ylj.parse(o.responseText).success_msg, "contactMsg");
      }
    };
  }();
}());