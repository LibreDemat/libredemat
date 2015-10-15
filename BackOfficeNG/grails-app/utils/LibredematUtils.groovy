import org.libredemat.business.request.RequestState;
import org.apache.commons.lang3.StringUtils as clSU

class LibredematUtils {

    def translationService

    /*
     * Generic method to adapt LibreDemat enums-like classes (Like RequestState / MeansOfContact / TitleType / ...)
     */
    public static adaptLibredematEnum(libredematState, i18nKeyPrefix) {
        return [
            "cssClass": "tag-" + libredematState.toString().toLowerCase(), 
            "i18nKey": i18nKeyPrefix + "." + StringUtils.toLowerCamelCase(libredematState.toString()),
            "enumString": libredematState.toString()
        ]
    }

    public static adaptLibredematEnum(libredematState, i18nKeyPrefix, String context) {
        if(libredematState == RequestState.EXTINPROGRESS.toString() && context != null && context.equals("frontoffice"))
        {
            return adaptLibredematEnum(RequestState.INPROGRESS.toString(), i18nKeyPrefix) // do not show internal complexity to client
        }
        return adaptLibredematEnum(libredematState,i18nKeyPrefix)
    }

    // TODO : check how one can inject the translation service in this class
    public static adaptRequestType(translationService, requestType) {
        return [
            id: requestType.id,
            active: requestType.active,
            label: translationService.translateRequestTypeLabel(requestType.label).encodeAsHTML(),
            categoryId: requestType.category?.id, 
            categoryName: requestType.category?.name,
            displayGroupId: requestType.displayGroup?.id, 
            displayGroupLabel: requestType.displayGroup?.label,
            weight : requestType.weight
        ]
    }

    public static adaptDocumentTypeName(name) {
        return "documentType."+ this.codifyName(name)
    }

    public static codifyName(name) {
        try {
            return StringUtils.firstCase(
                StringUtils.toCamelCase(
                    StringUtils.cleanString(name)
                        .replaceAll(" ", "_")
                ).replaceAll("_", "")
            , 'Lower')
        } catch(NullPointerException e) {
            return '';
        }
    }

    public static requestTypeLabelAsDir(label) {
        def dirName = StringUtils.firstCase(label.replace(' ', ''), 'Lower')
        if (dirName.endsWith('Request'))
            return dirName
        else
            return dirName + 'Request'
    }
}
