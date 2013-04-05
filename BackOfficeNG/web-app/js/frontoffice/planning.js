(function(planning) {
  var yud = YAHOO.util.Dom
    , yue = YAHOO.util.Event

  planning.resize = function(event) {
    if (planning.url.indexOf(event.origin) > -1 && event.data > 0) {
      var iframe = yud.get('booker')
      iframe.style.height = event.data + 'px'
    }
  }

  yue.on(window, 'message', planning.resize)

})(zenexity.capdemat.tools.namespace('zenexity.capdemat.fong.Planning'))
