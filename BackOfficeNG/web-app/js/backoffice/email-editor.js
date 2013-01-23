;(function(me) {
  // Namepaces
  var yu      = YAHOO.util
    , yud     = yu.Dom
    , yue     = yu.Event
    , yus     = yu.Selector
    , zc      = zenexity.capdemat
    , zct     = zc.tools

  var Variables = {}

  me.url = function(state) {
    var base = zc.contextPath + '/backoffice/email/states/' + state
    if (me.options.requestTypeId) {
      base = base + '?requestTypeId=' + me.options.requestTypeId
    }
    return base
  }

  // Fetch HTML on state change
  me.fetch = function() {
    var oncefetched
      , currentState = yud.get('state').value

    oncefetched = function(response) {
      this.argument[0].setData(response.responseText)
    }

    zct.doAjaxCall( me.url(currentState)
                  , [ this ]
                  , oncefetched
                  , true)
  }

  // Save HTML when clicking on "save" button
  me.save = function() {
    var oncesaved
      , params
      , currentState = yud.get('state').value
      , handlers

    oncesaved = function(response) {
      var response = JSON.parse(response.responseText)
      me.sync.option(response)
      zct.Notifier.processMessage( 'success'
                                 , response.texts.notif
                                 , null
                                 , yus.query('.editor-form', 'emails', true))
    }
    handlers = { failure: zct.handleUnexpectedError
               , success: oncesaved
               }
    yu.Connect.asyncRequest( 'POST'
                           , me.url(currentState)
                           , handlers
                           , zct.param({ 'html': this.getData() }))
  }

  // Delete HTML when clicking on "disable" button
  me.disable = function() {
    this.setData('', me.save)
  }

  me.sync = {}

  // response: { id: of the option to mark as enabled or disabled
  //           , enabled: boolean
  //           , texts: { option: new option text }
  //           }
  me.sync.option = function(response) {
    var option     = yud.get(response.id)
      , wasenabled = yud.hasClass(option, 'mail-enabled')

    if ( wasenabled && !response.enabled) {
      yud.removeClass(option, 'mail-enabled')
      option.innerHTML = response.texts.option
    }
    if (!wasenabled &&  response.enabled) {
      yud.addClass(option, 'mail-enabled')
      option.innerHTML = response.texts.option
    }
    me.sync.button(option)
  }

  me.sync.button = function(option) {
    var button = yud.get('disable')

    if (yud.hasClass(option, 'mail-enabled')) {
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

    zct.doAjaxCall( zc.contextPath + '/backoffice/email/variables'
                  , []
                  , oncefetched
                  , true)
  }

  me.init = function(options) {
    me.options = options || {}

    // Fetch variables
    Variables.fetch()

    // Load and initialize the editor
    var onready
      , definition
      , init
      , panelfor

    onready = function() {
      var editor = this
      yue.on('state', 'change', me.fetch, editor, true)
      yue.on('state', 'change', function(event) {
        var option = yud.get(yue.getTarget(event).value)
        me.sync.button(option)
      })
      me.fetch.call(editor)
      yue.on(yud.get('save'), 'click', function() { me.save.call(editor) })
      yue.on(yud.get('disable'), 'click', function() { me.disable.call(editor) })
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
                    , { customConfig: 'capdemat-config-foremails.js' }
                    )
            .on('instanceReady', onready)
  }

})(zenexity.capdemat.tools.namespace('zenexity.capdemat.bong.ui.EmailEditor'))
