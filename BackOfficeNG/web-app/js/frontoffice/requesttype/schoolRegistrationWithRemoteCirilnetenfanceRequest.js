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
        // The setTimeouts are there to let condition.js do it's stuff first
        // We should probably re-write all this
        if(yud.get('idSchoolName').tagName.toUpperCase() != "SELECT") {
          setTimeout(
              function() {
                  zct.alterer.toDropDown('SchoolName', zcfr.SchoolWithRemoteCirilnetenfanceRequest.schools, 'section')
              },
          200) // Yeah, so 100 is not enough apparently ... 200 is a lot though,  but it may work on many devices, or not
        } else {
            // let's clean schoolName label and field
            // Yeah it's fucking ugly but condition on an element tranformed with `alterer.js` does not play nice
          setTimeout(
              function() {
                zct.each(
                    yus.query("label[for=labelSchoolName]"),
                    function() { yud.addClass(this, 'unactive') }
                )
                yud.addClass(yud.get("labelSchoolName"), 'unactive')
              },
          200)
        }
      },

      initConditions : function() {

        yue.on(
          yud.get('section'),
          'change',
          zcfr.SchoolWithRemoteCirilnetenfanceRequest.altererSchoolsName
        )
      },

      init : function() {
        // Switch on step name.
        if (yus.query('div.form', 'request', true).id == "registration")  {
            zcfr.SchoolWithRemoteCirilnetenfanceRequest.initConditions()

            // For edition
            if(yud.get('section').value != '') zcfr.SchoolWithRemoteCirilnetenfanceRequest.altererSchoolsName()
        }
      }
    }
  }();
  yue.onDOMReady(zcfr.SchoolWithRemoteCirilnetenfanceRequest.init);
}());
