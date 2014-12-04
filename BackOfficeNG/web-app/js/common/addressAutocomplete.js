zenexity.libredemat.tools.namespace("zenexity.libredemat.common");

(function() {

  var zc = zenexity.libredemat;
  var zcc = zenexity.libredemat.common;
  var zct = zenexity.libredemat.tools;
  var yue = YAHOO.util.Event;
  var yus = YAHOO.util.Selector;
  var yuc = YAHOO.util.Connect;
  var yud = YAHOO.util.Dom;

  zcc.AddressAutocomplete = function() {
    var isActive = false;
    var autocompletes = {};

    var isAddresseReferentialCityRestriction = function() {
      if(window.XMLHttpRequest) // FIREFOX
        xhr_object = new XMLHttpRequest();
      else if(window.ActiveXObject) // IE
      xhr_object = new ActiveXObject("Microsoft.XMLHTTP");
      else return(false);
      // ON APPELLE LA PAGE
      xhr_object.open("GET", zc.contextPath + "/autocomplete/isAddresseReferentialCityRestriction", false);
      xhr_object.send(null);
      if (xhr_object.readyState == 4 && (xhr_object.status == 200 || xhr_object.status == 0))
        return xhr_object.responseText;
      else return "false";
    }

    var reorderAddressFields = function(fieldsetId) {
      if (zc.baseUrl.indexOf('backoffice') > 0)
        return;
      var addressEl = yud.get(fieldsetId);
      if (!addressEl) return;
      var fieldNamesToOrder = ['city', 'postalCode', 'streetName', 'streetNumber'];
      var addressFields = yud.getChildren(addressEl.cloneNode(true));
      addressEl.innerHTML = '';

      var isAddresseReferentialCity = isAddresseReferentialCityRestriction() == "true";
      for (var i=0; i<fieldNamesToOrder.length; i++) {
        for (var j=0; j<addressFields.length; j++) {
          var it = addressFields[j];
          if (!it) continue;
          if ((it.getAttribute('id') + '').indexOf(fieldNamesToOrder[i]) > 0
              || (it.getAttribute('for') + '').indexOf(fieldNamesToOrder[i]) > 0) {
            yud.removeClass(it, 'line1');
            yud.removeClass(it, 'line2');
            addressEl.appendChild(it);
            addressFields[j] = undefined; // hack !
          }
          var id = it.getAttribute("id");
          if (id != null && id.indexOf("_streetName") > 0 && isAddresseReferentialCity) {
            yud.removeClass(it, 'validate-streetName');
            yud.addClass(it, 'validate-streetNameReferential');
          }
        }
      }
      for (var i=0; i<addressFields.length; i++)
        if (!!addressFields[i] && addressFields[i].nodeName != 'BR')
          addressEl.appendChild(addressFields[i]);
    }

    var autocompleteBindFieldset = function(fieldsetId) {
      autocompletes[fieldsetId] = {
        streetName:{},
        postalCode:{},
        city: {}
      };
      reorderAddressFields(fieldsetId);
      var streetNameAutocomplete;
      if(document.getElementById(fieldsetId + "_streetName")) {
        //HACK tdu ;-)
        //Reset hidden fields if street name is null to avoid wrong data when submit
        yue.on(fieldsetId + "_streetName", "change", function () {
            if(this.value=="") {
                resetHiddenInformations(fieldsetId,"street");
            }
        });
        //END HACK
        autocompletes[fieldsetId].streetName = new zcc.AutoComplete({
          inputId: fieldsetId + "_streetName",
          modalId: fieldsetId + "_streetName_autocomplete",
          url: zc.contextPath + "/autocomplete/ways",
          fieldsetId : fieldsetId,
          streetField : true,
          idField: "id",
          minimumChars: 2,
          urlParams : {
            city : yus.query("#"+fieldsetId + "_cityInseeCode")[0].value
          },
          resultText: function(result) {
            //HACK tdu
            //Reset hidden fields when user change a char and trigger the autocomplete 
            resetHiddenInformations(fieldsetId,"street");
            //END HACK
            return result.name;
          },
          inputValue: function(result) {
            return result.name;
          },
          onSelectedResult: function(result) {
            document.getElementById(fieldsetId + "_streetMatriculation").value = result.matriculation || "";
            document.getElementById(fieldsetId + "_streetRivoliCode").value = result.rivoliCode || "";
          }
        });
      }
      if(document.getElementById(fieldsetId + "_city")) {
        //HACK tdu ;-)
        //Reset hidden fields if city is null to avoid wrong data when submit
        yue.on(fieldsetId + "_city", "change", function () {
            if(this.value=="") {
                resetHiddenInformations(fieldsetId,"city");
            }
        });
        //END HACK
        autocompletes[fieldsetId].city = new zcc.AutoComplete({
          inputId: fieldsetId + "_city",
          modalId: fieldsetId + "_city_autocomplete",
          fieldsetId : fieldsetId,
          url: zc.contextPath + "/autocomplete/cities",
          streetField : false,
          idField: "inseeCode",
          resultText: function(result) {
            //HACK tdu
            //Reset hidden fields when user change a char and trigger the autocomplete 
            resetHiddenInformations(fieldsetId,"city");
            //END HACK
            return result.postalCode + " " + result.name;
          },
          inputValue: function(result) {
            return result.name;
          },
          onSelectedResult: function(result) {
            document.getElementById(fieldsetId + "_postalCode").value = result.postalCode;
            autocompletes[fieldsetId].streetName.urlParams.city = result.inseeCode;
            document.getElementById(fieldsetId + "_cityInseeCode").value = result.inseeCode;
            document.getElementById(fieldsetId + "_streetName").value = "";
            document.getElementById(fieldsetId + "_streetNumber").value = "";
            document.getElementById(fieldsetId + "_streetMatriculation").value = "";
            document.getElementById(fieldsetId + "_streetRivoliCode").value = "";
          }
        });
      }
      if(document.getElementById(fieldsetId + "_postalCode")) {
        //HACK tdu ;-)
        //Reset hidden fields if city is null to avoid wrong data when submit
        yue.on(fieldsetId + "_postalCode", "change", function () {
            if(this.value=="") {
                resetHiddenInformations(fieldsetId,"postalCode");
            }
        });
        //END HACK
        autocompletes[fieldsetId].postalCode = new zcc.AutoComplete({
          inputId: fieldsetId + "_postalCode",
          modalId: fieldsetId + "_postalCode_autocomplete",
          url: zc.contextPath + "/autocomplete/cities",
          urlParams: { postalCode: true },
          fieldsetId : fieldsetId,
          streetField : false,
          idField: "inseeCode",
          minimumChars: 2,
          resultText: function(result) {
            //HACK tdu
            //Reset hidden fields when user change a char and trigger the autocomplete 
            resetHiddenInformations(fieldsetId,"postalCode");
            //END HACK
            return result.postalCode + " " + result.name;
          },
          inputValue: function(result) {
            return result.postalCode;
          },
          onSelectedResult: function(result) {
            document.getElementById(fieldsetId + "_city").value = result.name;
            autocompletes[fieldsetId].streetName.urlParams.city = result.inseeCode;
            document.getElementById(fieldsetId + "_cityInseeCode").value = result.inseeCode;
            document.getElementById(fieldsetId + "_streetName").value = "";
            document.getElementById(fieldsetId + "_streetNumber").value = "";
            document.getElementById(fieldsetId + "_streetMatriculation").value = "";
            document.getElementById(fieldsetId + "_streetRivoliCode").value = "";
          }
        });
      }
    };

    var autocompleteBind = function(fieldsetId) {
      if(fieldsetId) {
        fieldsetId = fieldsetId.replace(".", "_").replace("[","_").replace("]","_");
        autocompleteBindFieldset(fieldsetId);
      }
      else {
        zct.each(yus.query(".address"), function() {
          autocompleteBindFieldset(this.id);
        });
      }
    };

    var autocompleteUnbindFieldset = function(fieldsetId) {
      fieldsetId = fieldsetId.replace(".", "_").replace("[","_").replace("]","_");
      delete autocompletes[fieldsetId].streetName;
      delete autocompletes[fieldsetId].postalCode;
      delete autocompletes[fieldsetId].city;
    }

    var autocompleteUnbind = function(fieldsetId) {
      if(fieldsetId) {
        fieldsetId = fieldsetId.replace(".", "_").replace("[","_").replace("]","_");
        autocompleteUnbindFieldset(fieldsetId);
      }
      else {
        zct.each(yus.query(".address"), function() {
          autocompleteUnbindFieldset(this.id);
        });
      }
    };
    var resetHiddenInformations = function(fieldsetId,autocompleteType) {
        if(autocompleteType!='street') {
            document.getElementById(fieldsetId + "_cityInseeCode").value = "";
        }
        document.getElementById(fieldsetId + "_streetMatriculation").value = "";
        document.getElementById(fieldsetId + "_streetRivoliCode").value = "";
    }

    return {
      bind: autocompleteBind,
      unbind: autocompleteUnbind,
      init: function() {
        yuc.asyncRequest("GET", zc.contextPath + "/autocomplete/tokenValidity", {
          success: function() {
            isActive = true;
            autocompleteBind();
          }
        })
      }
    };
  }();

  yue.onDOMReady(zcc.AddressAutocomplete.init);

}());

