
class CapdematUtils {

    /*
     * Generic method to adapt CapDemat enums-like classes (Like RequestState / MeansOfContact / TitleType / ...)
     */
    public static adaptCapdematEnum (capdematState, i18nKeyPrefix) {
        return [
            "cssClass": "tag-" + capdematState.toString().toLowerCase(), 
            "i18nKey": i18nKeyPrefix + "." + StringUtils.pascalToCamelCase(capdematState.toString()),
            "enumString": capdematState.toString()
        ]
    }
    
    // TODO : check how one can inject the translation service in this class
    public static adaptRequestType (translationService, requestType) {
        return [
            id: requestType.id,
            active: requestType.active,
            label: translationService.getEncodedRequestTypeLabelTranslation(requestType.label),
            categoryId: requestType.category?.id, 
            categoryName: requestType.category?.name
        ]
    }

    public static adaptDocumentTypeName(name) {
        return "documentType.${name.trim().replaceAll(/^\w/,{it.toLowerCase()}).replaceAll(/\s+/,'')}"
    }
    
    public static adaptRequestActionLabel(label) {
        def transformedLabel = label.toLowerCase().replaceAll('_.',{it.toUpperCase().substring(1)})
        return "request.actionLabel.${transformedLabel}"
    }
}
