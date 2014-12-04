zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.requesttype');

(function() {
  var zct = zenexity.libredemat.tools
  var zcfr = zenexity.libredemat.fong.requesttype;
  var yud = YAHOO.util.Dom
  var yue = YAHOO.util.Event
  var yus = YAHOO.util.Selector

  zcfr.SchoolWithRemoteCirilnetenfanceRequest = function() {
    return {
      schools : function() {
        var prefix = zenexity.libredemat.contextPath + '/frontoffice/ciril'
        return prefix
             + '/schools/?requestTypeLabel=School Registration With Remote Cirilnetenfance'
             + '&requestId='
             + yud.get('stepForm').id.value
             + '&sectionContainer='
             + yud.get('section').value
             + '&subjectIdContainer='
             + yud.get('subjectId').value
      },

      altererSchoolsName : function() {
        zct.alterer.toDropDown('SchoolName', zcfr.SchoolWithRemoteCirilnetenfanceRequest.schools, 'section')
      },

      initConditions : function() {

        yue.on(
          yud.get('section'),
          'change',
          zcfr.SchoolWithRemoteCirilnetenfanceRequest.altererSchoolsName
        )

        yue.on(
          yud.get('subjectId'),
          'change',
          zcfr.SchoolWithRemoteCirilnetenfanceRequest.altererSchoolsName
        )
      },

      init : function() {
        // Switch on step name.
        if (yus.query('div.form', 'request', true).id == "registration")  {
            zcfr.SchoolWithRemoteCirilnetenfanceRequest.initConditions()

            zcfr.SchoolWithRemoteCirilnetenfanceRequest.altererSchoolsName()
        }
      }
    }
  }();
  yue.onDOMReady(zcfr.SchoolWithRemoteCirilnetenfanceRequest.init);
}());
