<div id="ft">
  <a class="logo" href="http://www.libredemat.org/"><img src="${resource(dir:'images', file:'logo_libredemat.png')}" alt="LibreDémat"/></a>
  <a class="logo" href="http://europa.eu"><img src="${resource(dir:'images', file:'eu.jpg')}" alt="Projet cofinancé par l’Union Européenne (FEDER)"/></a>
  <span>${message(code:'message.libredemat')} ${grailsApplication.metadata['app.version']}</span>
  | <a href="${createLink(controller:'frontofficeHome', action:'browsers')}">${message(code:'home.header.browsers')}</a>
  | <a href="${createLink(controller:'frontofficeHome', action:'legal')}">${message(code:'message.legalInformation')}</a>
</div>
