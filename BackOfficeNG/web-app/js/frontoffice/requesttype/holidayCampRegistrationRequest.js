;(function(request) {
  var zct = zenexity.libredemat.tools
    , yud = YAHOO.util.Dom
    , yus = YAHOO.util.Selector

  var initCamps = function() {
    var url = function() {
      var prefix = zenexity.libredemat.contextPath + '/frontoffice/technocarte'
      return prefix
           + '/holidayCamps/?requestTypeLabel=Holiday Camp Registration'
           + '&requestId='
           + yud.get('stepForm').id.value
           + '&childId='
           + yud.get('subjectId').value
    }

    zct.alterer.toDropDown('CentreSejours', url, 'subjectId')
  }

  request.init = function() {
    // Switch on step name.
    switch (yus.query('div.form', 'request', true).id) {
      case 'enfant':
        initCamps()
        break
    }
  }

}(zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype.HolidayCampRegistrationRequest')))
