import org.apache.commons.lang.StringUtils
import org.libredemat.util.logging.impl.Log

class MenuTagLib {
    def static namespace = "menu"
    def exclude = ['backoffice','frontoffice','monitoring']
    
    def current = {attrs,body ->
        def elem = attrs['elem']
        def clss = attrs['class']
        def current = getCurrentItem()
        
        if(!elem) elem = 'home'
        if(!clss) clss = 'current'
        
        if(elem.toLowerCase() != current.toLowerCase()) clss = ''
        
        out << clss
    }
    
    def subMenu = {attrs,body ->
        def items = attrs['data'], id = attrs['id'], blocks = ''
        def i18nPrefix = attrs['i18nPrefix']
        def itemAction, itemController, itemModule, itemParams
        
        for(String item : items) {
            
            // External link
            if (item.contains('|')) {
                def itemParts = item.split("\\|")
                if (itemParts.length != 2) {
                    log.warn("Invalid external link. Expected format: 'label|link', found: '$item'")
                } else {
                    blocks += """
                    <li>
                        <a id="display${StringUtils.capitalize(item)}" href="${itemParts[1]}" target="_self">
                         ${itemParts[0]}
                        </a>
                    </li>
                    """
                }

            // Internal link
            } else {
                def split = item.split('\\.')
                itemAction = split[1]
                itemController = split[0]
                def qs = (split.size() == 3) ? split[2] : ''
                itemParams = qs.split('&').inject([:]) {map, kv ->
                    def (key, value) = kv.split('=').toList()
                    if (value != null) {
                        map[key] = value
                    }
                    return map
                }
                itemModule = exclude.find { controllerName.contains(it) }

                if ((itemAction != actionName) || (itemModule + StringUtils.capitalize(itemController) != controllerName)) {
                    blocks += """
                    <li>
                        <a id="display${StringUtils.capitalize(item)}" href="${createLink(controller:itemModule+StringUtils.capitalize(itemController), action:itemAction, params:itemParams)}" target="_self">
                        ${message(code:itemController+'.'+i18nPrefix+'.'+itemAction, args: itemParams.values())}
                        </a>
                    </li>
                    """
                } else {
                    blocks += """
                    <li>
                       ${message(code:itemController +'.'+ i18nPrefix +'.'+ itemAction)}
                    </li>
                    """
                }
            }
        }
        if (items.size() > 1)
            out << """
              <div class="nobox">
                <h3>${message(code:'header.subMenus')}</h3>
                <div class="body">
                  <ul class="second-level-menu" id="${id}">
                    ${blocks}
                  </ul>
                </div>
              </div>
            """
    }
    
    def protected getCurrentItem = {
        def result = controllerName
        exclude.each {result = result.replaceAll(it,'')}
        return result.toLowerCase()
    }
}
