(function(planning) {
  var yud = YAHOO.util.Dom
    , yue = YAHOO.util.Event

  planning.handleMessage = function(event) {
    if(event.data.indexOf("resize") == 0) {
      planning.resize(event.data.replace(/resize/, ""))
    } else if(event.data.indexOf("redirect") == 0) {
      var url = event.data.replace(/redirect/, "")
      if(url.indexOf(window.location.origin) >= 0) {
          console.log("assign, "+url)
          window.location.assign(url)
      }
    }
  }

  planning.resize = function(size) {
    var iframe = yud.get('booker')
    iframe.style.height = size + 'px'
  }

  yue.on(window, 'message', planning.handleMessage)

})(zenexity.libredemat.tools.namespace('zenexity.libredemat.fong.Planning'))
