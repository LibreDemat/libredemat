;(function(request) {
  var zct = zenexity.libredemat.tools
    , yud = YAHOO.util.Dom
    , yus = YAHOO.util.Selector

  var initSchools = function() {
    var prefix = zenexity.libredemat.contextPath + '/frontoffice/technocarte'

    var sectorsURL = function() {
      return prefix
           + '/schoolSectors/?requestTypeLabel=Global School Registration'
           + '&childId='
           + yud.get('subjectId').value
    }
    var derogsURL = function() {
      return prefix
           + '/schoolDerogs/?requestTypeLabel=Global School Registration'
           + '&childId='
           + yud.get('subjectId').value
    }
    zct.alterer.toDropDown('EcoleSecteur', sectorsURL, 'subjectId')
    zct.alterer.toDropDown('EcoleDerog', derogsURL, 'subjectId')
  }

  request.init = function() {
    // Switch on step name.
    switch (yus.query('div.form', 'request', true).id) {
      case 'enfant':
        initSchools()
        break
    }
  }

}(zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype.GlobalSchoolRegistrationRequest')))
