;(function(request) {
  var zct = zenexity.libredemat.tools
    , yud = YAHOO.util.Dom
    , yus = YAHOO.util.Selector

  var initCenters = function() {
    var url = function() {
      var prefix = zenexity.libredemat.contextPath + '/frontoffice/technocarte'
      return prefix
           + '/leisureCenters/?requestTypeLabel=Leisure Center Registration'
           + '&requestId='
           + yud.get('stepForm').id.value
           + '&childId='
           + yud.get('subjectId').value
    }
    zct.alterer.toDropDown('CentreLoisirs', url, 'subjectId')
  }

  var initTransports = function() {
    var prefix = zenexity.libredemat.contextPath + '/frontoffice/technocarte'

    var linesURL = function() {
      return prefix
           + '/transportLines/?requestTypeLabel=Leisure Center Registration'
           + '&requestId='
           + yud.get('stepForm').id.value
           + '&childId='
           + yud.get('subjectId').value
    }
    var stopsURL = function() {
        return prefix
             + '/stops/?requestTypeLabel=Leisure Center Registration'
             + '&requestId='
             + yud.get('stepForm').id.value
             + '&childId='
             + yud.get('subjectId').value
             + '&lineId='
             + yud.get('idLigne').value
    }
    zct.alterer.toDropDown('Ligne', linesURL, 'subjectId')
    zct.alterer.toDropDown('Arret', stopsURL, 'idLigne')
  }

  request.init = function() {
    // Switch on step name.
    switch (yus.query('div.form', 'request', true).id) {
      case 'enfant':
        initCenters()
        initTransports()
        break
    }
  }

}(zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype.LeisureCenterRegistrationRequest')))
