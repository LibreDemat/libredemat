;(function(me) {
  // Namespaces
  var yw  = YAHOO.widget
    , yu  = YAHOO.util
    , yue = yu.Event
    , zc  = zenexity.capdemat

  var parser = new UAParser()
    , blacklist = [ { name: 'IE', major: 8 }
                  , { name: 'IEMobile', major: 8 }
                  , { name: 'Firefox', major: 4 }
                  , { name: 'Chrome', major: 10 }
                  , { name: 'Safari', major: 4 }
                  , { name: 'Mobile Safari', major: 4 }
                  ]

  me.popup = function(browser) {
    var popup
      , options
      , onok
      , buttons
      , messages = zc.messages
      , content
      , url = zc.baseUrl.replace(/(frontoffice).*/, '$1/home/browsers')

    options = { width: '30em'
              , fixedcenter: true
              , modal: true
              , draggable: false
              }
    popup = new yw.SimpleDialog('browser-alert', options)
    popup.setHeader(messages.browser_alert_title)
    content = messages.browser_alert_content.replace('{0}', browser.name)
                                            .replace('{1}', browser.major)
                                            .replace('{2}', url)
    popup.setBody(content)

    onok = function() {
      this.hide()
    }
    buttons = [ { text: 'OK', handler: onok } ]
    popup.cfg.queueProperty('buttons', buttons)

    popup.render(document.body)
  }

  me.init = function() {
    var browser = parser.getBrowser()
      , b
      , browsermajor = parseInt(browser.major)
    for (var i in blacklist) {
      b = blacklist[i]
      if (browser.name === b.name &&
          browsermajor < b.major &&
          !/\/frontoffice\/home\/browsers/.test(window.location.pathname)) {

        if (!(browser.name === 'IE')) {
          me.popup(browser)
        } else {
          // IE's major can be 7 while its document mode is 8.
          // Check against document mode.
          if (!(document.documentMode && document.documentMode >= b.major)) {
            me.popup(browser)
          }
        }
      }
    }
  }

  yue.onDOMReady(me.init)
})({})
