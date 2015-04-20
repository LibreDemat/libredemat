zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype');

(function () {

  var zcf = zenexity.libredemat.fong;
  var zcfr = zcf.requesttype;

  var yu = YAHOO.util;
  var yud = yu.Dom;
  var yue = YAHOO.util.Event;

  zcfr.ParkingPermitTemporaryWorkRequest = function () {

    var getDate = function (dayAdded, date) {
      var d = new Date();
      if (date != null) {
        var dates = date.split("/");
        d = new Date(dates[2] + "/" + dates[1] + "/" + dates[0]);
      }
      return new Date(d.getTime() + (1000 * 60 * 60 * 24 * dayAdded));
    };

    var getMinDate = function (target) {
      var classes = yud.getAttribute(target, "class").split(" ");
      var mindate = target.id === 'scaffoldingStartDateShow' ? 
        getDate(zenexity.libredemat.pptwrSpecificConfigurationData.minDaysBeforeScaffolding) :
        getDate(zenexity.libredemat.pptwrSpecificConfigurationData.minDaysBeforeFloorOccupation);
      for (var i = 0; i < classes.length; i++) {
        if (classes[i].indexOf('notBeforeDate_') > -1) {
          var inputLabel = classes[i].split("_")[1];
          if (inputLabel != undefined && inputLabel != "null" && yud.get(inputLabel) != null) {
            var inputValue = yud.get(inputLabel).value;
            if (inputValue != undefined && inputValue != "")
              mindate = getDate(0, inputValue);
          }
        }
      }
      return mindate;
    };

    var isDisabled = function (target) {
      var classes = yud.getAttribute(target, "class");
      classes = classes.split(" ");
      for (var i = 0; i < classes.length; i++) {
        if (classes[i].indexOf('disabledWith_') > -1) {
          if (classes[i].split("_").length > 1) {
            var inputLabel = classes[i].split("_")[1];
            if (inputLabel != undefined && inputLabel != "null" && yud.get(inputLabel) != null) {
              var inputValue = yud.get(inputLabel).value;
              return !(inputValue != undefined && inputValue != "");

            }
          }
        }
      }
      return false;
    };

    var disabledFields = function (targetElementName) {
      if (yud.get('work') === null)
        return;
      var periodeEnd = yud.get(targetElementName);
      var periodeEndShow = yud.get(targetElementName + "Show");
      if (isDisabled(periodeEndShow)) {
        periodeEnd.disabled = true;
        yud.setStyle(targetElementName + "Show", "cursor", "default");
        periodeEndShow.disabled = true;
      }
      else {
        yue.on(periodeEndShow, 'click', zcfr.ParkingPermitTemporaryWorkRequest.processClickEnd,
            zcfr.ParkingPermitTemporaryWorkRequest.processClickEnd, true);
        periodeEnd.disabled = false;
        yud.setStyle(targetElementName + "Show", "cursor", "pointer");
        periodeEndShow.disabled = false;
      }
    };

    return {
      init: function () {
        yue.on(yud.get('scaffoldingStartDateShow'), 'click', zcfr.ParkingPermitTemporaryWorkRequest.processClickStart,
            zcfr.ParkingPermitTemporaryWorkRequest.processClickStart, true);
        yue.on(yud.get('occupationStartDateShow'), 'click', zcfr.ParkingPermitTemporaryWorkRequest.processClickStart,
            zcfr.ParkingPermitTemporaryWorkRequest.processClickStart, true);
        disabledFields('scaffoldingEndDate');
        disabledFields('occupationEndDate');
      },
      /**
       * @description The name of the method to call is the first part of the
       *              clicked item's ID, except for new season creation
       */
      processClickStart: function (e) {
        // Pour les options voir le fichier calendar_fo.js
        var target = yue.getTarget(e);
        var targetElementName = target.id.replace("Show","");
        var options =
        {
          close: true,
          title: "Date de début ",
          minDate: getMinDate(target)
        };
        zcf.Calendar(targetElementName, options, zcfr.ParkingPermitTemporaryWorkRequest.callBack);
      },

      processClickEnd: function (e) {
        // Pour les options voir le fichier calendar_fo.js
        var target = yue.getTarget(e);
        var targetElementName = target.id.replace("Show","");
        var options =
        {
          close: true,
          title: "Date de fin ",
          minDate: getMinDate(target)
        };
        zcf.Calendar(targetElementName, options, zcfr.ParkingPermitTemporaryWorkRequest.callBack);
      },

      callBack: function () {
        disabledFields('scaffoldingEndDate');
        disabledFields('occupationEndDate');
      }
    }
  }();
}());
