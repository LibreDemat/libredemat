;(function(requesttype) {
  var zl    = zenexity.libredemat
    , zlt   = zl.tools
    , zlc   = zl.common
    , zlv   = zl.Validation
    , zlbrt = zl.bong.requesttype
    , yl    = YAHOO.lang
    , yu    = YAHOO.util
    , yud   = yu.Dom
    , yue   = yu.Event
    , yus   = yu.Selector
    , ylj   = yl.JSON

  zlbrt.Steps = function() {
    return {
      init: function() {
        zlbrt.Conf.saveRequestTypeSteps = zlbrt.Steps.save
      }
    , save : function(e) {
        var form  = yud.get('requestTypeStepsForm')
          , error = yud.get('dialogRequestTypeStepsFormError')

        if (zlv.check(form, error)) {
          var target = yue.getTarget(e)
          zlt.doAjaxFormSubmitCall( form.getAttributeNode('id').value
                                  , []
                                  , function(o) {
            var json = ylj.parse(o.responseText)
            zlt.Notifier.processMessage(json.status, json.msg, null, target)
          })
        }

      }
    }
  }()

  yue.onDOMReady(zlbrt.Steps.init)

}(zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype')))
