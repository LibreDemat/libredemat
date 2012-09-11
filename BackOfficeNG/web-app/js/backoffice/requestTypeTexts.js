;(function(Texts) {
  // Namepaces
  var yu      = YAHOO.util
    , yud     = yu.Dom
    , yue     = yu.Event
    , yus     = yu.Selector
    , zc      = zenexity.capdemat
    , zct     = zenexity.capdemat.tools
    , zcbrt   = zc.bong.requesttype

  // Fetch HTML on step change
  Texts.fetch = function() {
    var oncefetched
      , currentStep = yud.get('step').value

    oncefetched = function(response) {
      this.argument[0].setData(response.responseText)
    }

    zct.doAjaxCall( '/help/' + zcbrt.currentId + '/step/' + currentStep
                  , [ this ]
                  , oncefetched)
  }

  // Save HTML when clicking on "Save" button
  Texts.save = function() {
    var oncesaved
      , params
      , currentStep = yud.get('step').value

    oncesaved = function(response) {
      zct.Notifier.processMessage( 'success'
                                 , response.responseText
                                 , null
                                 , yus.query('.editor-form', 'texts', true))
    }

    params = zct.param({ 'html': this.getData() })

    zct.doAjaxPostCall( '/help/' + zcbrt.currentId + '/step/' + currentStep
                      , params
                      , oncesaved)
  }

  Texts.init = function() {
    var onready = function() {
      var editor = this
      yue.on('step', 'change', Texts.fetch, editor, true)
      Texts.fetch.call(editor)
      zcbrt.Conf.save = function() { Texts.save.call(editor) }
    }

    CKEDITOR.replace( 'editor'
                    , { customConfig: 'capdemat-config-fortexts.js' }
                    )
            .on('instanceReady', onready)
  }

  yue.onDOMReady(Texts.init)

})(zenexity.capdemat.tools.namespace('zenexity.capdemat.bong.requesttype'))
