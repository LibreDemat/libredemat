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

      initSections: function() {
          if (yud.get('subjectId').value === '') {
              yud.get('section').setAttribute('disabled','disabled');
              yud.get('idSchoolName').value = "";
              for (i=1;i< yud.get('idSchoolName').length;  i++) {
                  yud.get('idSchoolName').remove(i);
              }
          } else {
              yud.get('section').removeAttribute('disabled');
          }
      },

      initConditions : function() {

        yue.on(
          yud.get('subjectId'),
          'change',
          function () {
            yud.get('section').value='';
            if ( this.value === '') {
              yud.get('section').setAttribute('disabled','disabled');
              yud.get('idSchoolName').value = "";
              for (i=1;i< yud.get('idSchoolName').length;  i++) {
                yud.get('idSchoolName').remove(i);
              }
            } else {
              yud.get('section').removeAttribute('disabled');
            }
          }
        )
      },

      init : function() {
        // Switch on step name.
        if (yus.query('div.form', 'request', true).id == "registration")  {
            zcfr.SchoolWithRemoteCirilnetenfanceRequest.initSections();
            zcfr.SchoolWithRemoteCirilnetenfanceRequest.initConditions()
            zcfr.SchoolWithRemoteCirilnetenfanceRequest.altererSchoolsName()
            yud.get('idSchoolName').remove(1);
        }
      }
    }
  }();
  yue.onDOMReady(zcfr.SchoolWithRemoteCirilnetenfanceRequest.init);
}());
