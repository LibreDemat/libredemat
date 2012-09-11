;(function(Mails) {
  // Namepaces
  var yu      = YAHOO.util
    , yud     = yu.Dom
    , yue     = yu.Event
    , yus     = yu.Selector
    , zc      = zenexity.capdemat
    , zct     = zenexity.capdemat.tools
    , zcbrt   = zc.bong.requesttype

  var Mails = {}
    , Variables = {}

  // Fetch HTML on state change
  Mails.fetch = function() {
    var oncefetched
      , currentState = yud.get('state').value

    oncefetched = function(response) {
      this.argument[0].setData(response.responseText)
    }

    zct.doAjaxCall( '/mail/' + zcbrt.currentId + '/state/' + currentState
                  , [ this ]
                  , oncefetched)
  }

  // Save HTML when clicking on "save" button
  Mails.save = function() {
    var oncesaved
      , params
      , currentState = yud.get('state').value

    oncesaved = function(response) {
      var response = JSON.parse(response.responseText)
      Mails.sync.option(response)
      zct.Notifier.processMessage( 'success'
                                 , response.texts.notif
                                 , null
                                 , yus.query('.editor-form', 'emails', true))
    }

    zct.doAjaxPostCall( '/mail/' + zcbrt.currentId + '/state/' + currentState
                      , zct.param({ 'html': this.getData() })
                      , oncesaved)
  }

  // Delete HTML when clicking on "disable" button
  Mails.disable = function() {
    this.setData('', Mails.save)
  }

  Mails.sync = {}

  // response: { id: of the option to mark as enabled or disabled
  //           , enabled: boolean
  //           , texts: { option: new option text }
  //           }
  Mails.sync.option = function(response) {
    var option     = yud.get(response.id)
      , wasenabled = yud.hasClass(option, 'enabled')

    if ( wasenabled && !response.enabled) {
      yud.removeClass(option, 'enabled')
      option.innerHTML = response.texts.option
    }
    if (!wasenabled &&  response.enabled) {
      yud.addClass(option, 'enabled')
      option.innerHTML = response.texts.option
    }
    Mails.sync.button(option)
  }

  Mails.sync.button = function(option) {
    var button = yud.get('disable')

    if (yud.hasClass(option, 'enabled')) {
      button.removeAttribute('disabled')
    } else {
      button.setAttribute('disabled', '')
    }
  }

  Variables.grouped = []

  Variables.fetch = function() {
    var oncefetched = function(response) {
      Variables.grouped = JSON.parse(response.responseText)
    }

    zct.doAjaxCall( '/variables'
                  , []
                  , oncefetched)
  }

  Mails.init = function() {
    // Fetch variables
    Variables.fetch()

    // Load and initialize the editor
    var onready
      , definition
      , init
      , panelfor

    onready = function() {
      var editor = this
      yue.on('state', 'change', Mails.fetch, editor, true)
      yue.on('state', 'change', function(event) {
        var option = yud.get(yue.getTarget(event).value)
        Mails.sync.button(option)
      })
      Mails.fetch.call(editor)
      zcbrt.Conf.save    = function() { Mails.save.call(editor) }
      zcbrt.Conf.disable = function() { Mails.disable.call(editor) }
    }

    init = function() {
      var group
        , v
      for (var i in Variables.grouped) {
        group = Variables.grouped[i]
        this.startGroup(group.label)

        for (var j in group.variables) {
          v = group.variables[j]
          this.add(v.variable, v.label, v.label)
        }
      }
    }

    panelfor = function(editor) {
      var config = editor.config
      return { css: [ config.contentsCss, CKEDITOR.skin.path() + 'editor.css' ] }
    }

    definition = { lang: [ 'fr' ]
                 , requires: [ 'richcombo' ]
                 , init: function(editor) {
                     editor.ui.addRichCombo( 'Variables'
                                           , { label: 'Champs prédéfinis'
                                             , panel: panelfor(editor)
                                             , init: init
                                             , onClick: function(value) {
                                                 editor.focus()
                                                 editor.fire('saveSnapshot')
                                                 editor.insertHtml(value)
                                                 editor.fire('saveSnapshot')
                                               }
                                             }
                                           )
                   }
                 }

    CKEDITOR.plugins.add('variables', definition)
    CKEDITOR.replace( 'editor'
                    , { customConfig: 'capdemat-config-formails.js' }
                    )
            .on('instanceReady', onready)
  }

  yue.onDOMReady(Mails.init)

})(zenexity.capdemat.tools.namespace('zenexity.capdemat.bong.requesttype'))
