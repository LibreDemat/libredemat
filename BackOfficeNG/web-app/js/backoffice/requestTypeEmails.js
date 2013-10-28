;(function() {
  // Namepaces
  var yu      = YAHOO.util
    , yue     = yu.Event
    , zcb     = zenexity.libredemat.bong
    , zcbrt   = zcb.requesttype

  yue.onDOMReady(function() {
    zcb.ui.EmailEditor.init({'requestTypeId': zcbrt.currentId})
  })
})()
