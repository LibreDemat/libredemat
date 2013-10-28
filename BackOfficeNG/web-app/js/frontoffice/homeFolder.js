zenexity.libredemat.tools.namespace("zenexity.libredemat.fong.");

(function() {
  var zcf = zenexity.libredemat.fong
  var zcfr = zcf.requesttype;
  var zcv = zenexity.libredemat.Validation;
  var zct = zenexity.libredemat.tools;
  var yue = YAHOO.util.Event;
  var yud = YAHOO.util.Dom;
  var yus = YAHOO.util.Selector;

  zcf.HomeFolder = function() {

    return {
      init: function() {
        yue.on(yus.query(".born"), "click", zcf.HomeFolder.isChildBornChange);
        zcf.HomeFolder.initChildBorn();
      },

      initChildBorn : function() {
        var checkedEl;
        zct.each(yus.query(".born"), function() {
            if (this.checked) checkedEl = this;
        });
        zcf.HomeFolder.toogleChildFields(checkedEl);
      },

      isChildBornChange : function(e) {
        var targetEl = yue.getTarget(e);
        zcf.HomeFolder.toogleChildFields(targetEl);
      },

      toogleChildFields : function(checkedEl) {
        var formEl = yud.getAncestorByTagName(checkedEl, "form");
        var fields = ['firstName', 'sex'];
        var toogleClass = (checkedEl.value === 'true' ? 'add' : 'remove') + 'Class';
        for (i in fields) {
          yud[toogleClass](yud.getPreviousSibling(formEl[fields[i]]), 'required');
          yud[toogleClass](formEl[fields[i]], 'required');
        }
        yud.getNextSibling(formEl['sex']).innerHTML =
          checkedEl.value === 'true' ? zcf.i18n['child.birthDate'] : zcf.i18n['child.expectedBirthDate'];
      }
  };

  }();

  yue.onDOMReady(zcf.HomeFolder.init);
}());

