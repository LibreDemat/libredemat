zenexity.libredemat.tools.namespace("zenexity.libredemat.fong.requesttype");

(function() {
  var zcfr = zenexity.libredemat.fong.requesttype;
  var zcv = zenexity.libredemat.Validation;
  var yue = YAHOO.util.Event;

  zcfr.StudyGrantRequest = function() {

    return {
      init: function() {
        zcv.complexRules['rib'].pushFields('frenchRIB.bankCode', 'frenchRIB.counterCode', 'frenchRIB.accountNumber', 'frenchRIB.accountKey');
      }
    };

  }();
}());

