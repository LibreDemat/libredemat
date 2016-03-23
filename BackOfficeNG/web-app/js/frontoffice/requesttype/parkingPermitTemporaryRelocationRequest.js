zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype');

(function () {

    var zcf = zenexity.libredemat.fong;
    var zcfr = zcf.requesttype;
    var zcv = zenexity.libredemat.Validation;

    var yu = YAHOO.util;
    var yud = yu.Dom;
    var yue = YAHOO.util.Event;
    var ylj = YAHOO.lang.JSON;
    var yl = YAHOO.lang;

    zcfr.ParkingPermitTemporaryRelocationRequest = function () {

    Date.prototype.addDays = function(days) {
      this.setDate(this.getDate() + parseInt(days));
      return this;
    };

        var getDate = function (dayAdded, date) {
            var d = new Date();
            d.setHours(0,0,0,0);
            if (date != null) {
                var dates = date.split("/");
                d = new Date(dates[2] + "/" + dates[1] + "/" + dates[0]);
            }
            d.addDays(dayAdded);
            return d ;
        };

        var getMinDate = function (target) {
            var classes = yud.getAttribute(target, "class").split(" ");
            var mindate = getDate(zenexity.libredemat.pptrrSpecificConfigurationData.minDaysBeforeRelocation);
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

        var createErrorField = function(elem, date) {
          var error = document.createElement('p');
          error.id= elem.name + 'Error';
          error.className = 'error';
          if(elem.name == 'periodeStart') {
            error.innerHTML = 'La date minimum autorisée est le ' + date.getDate() + '/' + (date.getMonth()+1) + '/' + date.getFullYear();
          } else {
            error.innerHTML = 'La date de fin de période ne peut être antérieure à la date de début';
          }
          elem.parentNode.parentNode.parentNode.parentNode.parentNode.insertBefore(error,elem.parentNode.parentNode.parentNode.parentNode.nextSibling);
        };

        var removeErrorField = function(elem) {
          var error = yud.get(elem.name + 'Error');
          if( !yl.isNull(error)) {
            error.parentNode.removeChild(error)
          }
        }
        return {
            init: function () {
                yue.on(yud.get('periodeStartShow'), 'click', zcfr.ParkingPermitTemporaryRelocationRequest.processClickStart, zcfr.ParkingPermitTemporaryRelocationRequest.processClickStart, true);
                yue.on(yud.get('periodeEndShow'), 'click', zcfr.ParkingPermitTemporaryRelocationRequest.processClickEnd, zcfr.ParkingPermitTemporaryRelocationRequest.processClickEnd, true);
                yud.removeClass(yud.get('periodeEnd'),'validate-calendar');
                yud.removeClass(yud.get('periodeStart'),'validate-calendar');
                yud.addClass(yud.get('periodeStart'),'validate-checkDateStart');
                yud.addClass(yud.get('periodeEnd'),'validate-checkDateEnd');
                zcv.putRules({
                "checkDateStart" : new zcv.rule("func", function(f) {
                  var minDate = getDate(zenexity.libredemat.pptrrSpecificConfigurationData.minDaysBeforeRelocation);
                  var startDate = getDate(0,yud.get('periodeStart').value);
                  if(minDate > startDate && yl.isNull(yud.get('periodeStartError'))) {
                    createErrorField(yud.get('periodeStart'), minDate);
                  } else if(minDate <= startDate) {
                    removeErrorField(yud.get('periodeStart'));
                  }
                  return (minDate <= startDate);
                }),
                "checkDateEnd" : new zcv.rule("func", function(f) {
                    var startDate = getDate(0,yud.get('periodeStart').value);
                    var endDate = getDate(0,yud.get('periodeEnd').value);
                    if(startDate > endDate && yl.isNull(yud.get('periodeEndError'))) {
                        createErrorField(yud.get('periodeEnd'), startDate)
                      } else if(startDate <= endDate) {
                        removeErrorField(yud.get('periodeEnd'));
                      }
                    return (startDate <= endDate);
                  })
                });
            },
            /**
             * @description The name of the method to call is the first part of the
             *              clicked item's ID, except for new season creation
             */
            processClickStart: function (e) {
                // Pour les options voir le fichier calendar_fo.js
                var target = yue.getTarget(e);
                var options =
                {
                    close: true,
                    title: "Date prévue du : ",
                    minDate: getMinDate(target)
                };
                zcf.Calendar("periodeStart", options, zcfr.ParkingPermitTemporaryRelocationRequest.callBack);
            },

            processClickEnd: function (e) {
                // Pour les options voir le fichier calendar_fo.js
                var target = yue.getTarget(e);
                var options =
                {
                    close: true,
                    title: "Au : ",
                    minDate: getMinDate(target)
                };
                zcf.Calendar("periodeEnd", options, zcfr.ParkingPermitTemporaryRelocationRequest.callBack);
            },

            callBack: function () {
            }
        }
    }();
}());
