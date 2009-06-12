import fr.cg95.cvq.business.authority.LocalAuthorityResource

public class LocalAuthorityResourceAdaptorService {

    def localAuthorityResources

    public getLocalAuthorityResources() {
        if (localAuthorityResources == null) {

            localAuthorityResources = [:]
            LocalAuthorityResource.localAuthorityResources.each {
                localAuthorityResources.(it.key) = [:]
                localAuthorityResources.(it.key).id = it.value.id
                localAuthorityResources.(it.key).filename = it.value.filename
                localAuthorityResources.(it.key).extension = it.value.extension
                localAuthorityResources.(it.key).resourceType = it.value.resourceType
                localAuthorityResources.(it.key).canFallback = it.value.canFallback()
            }

            localAuthorityResources.cssFo.template = "linkBox"
            localAuthorityResources.logoFo.template = "imageBox"
            localAuthorityResources.logoBo.template = "imageBox"
            localAuthorityResources.banner.template = "imageBox"
            localAuthorityResources.logoPdf.template = "imageBox"
            localAuthorityResources.faqFo.template = "linkBox"
            localAuthorityResources.helpBo.template = "linkBox"
            localAuthorityResources.helpFo.template = "linkBox"
            localAuthorityResources.legal.template = "linkBox"
            localAuthorityResources.use.template = "linkBox"

            localAuthorityResources.cssFo.contentType = "text/css"
            localAuthorityResources.logoFo.contentType = "image/png"
            localAuthorityResources.logoBo.contentType = "image/png"
            localAuthorityResources.banner.contentType = "image/png"
            localAuthorityResources.logoPdf.contentType = "image/png"
            localAuthorityResources.faqFo.contentType = "application/pdf"
            localAuthorityResources.helpBo.contentType = "application/pdf"
            localAuthorityResources.helpFo.contentType = "application/pdf"
            localAuthorityResources.legal.contentType = "application/pdf"
            localAuthorityResources.use.contentType = "application/pdf"
        }
        return localAuthorityResources
    }
}