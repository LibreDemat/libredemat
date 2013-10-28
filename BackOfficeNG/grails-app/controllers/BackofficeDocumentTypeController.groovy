import org.libredemat.service.document.IDocumentTypeService
import org.libredemat.business.document.DocumentType
import org.libredemat.business.document.DocumentTypeValidity
import org.libredemat.business.document.DocumentUsageType
import org.libredemat.dao.document.hibernate.DocumentTypeDAO
import org.libredemat.exception.CvqException
import org.libredemat.security.SecurityContext
import org.libredemat.service.authority.ILocalAuthorityRegistry

import java.util.Properties;
import java.lang.Character;

import grails.converters.JSON
import org.apache.commons.lang3.text.WordUtils
import org.apache.commons.lang3.StringUtils as apacheCommonLang3StringUtils
import org.apache.commons.lang3.StringEscapeUtils


class BackofficeDocumentTypeController {
    
    IDocumentTypeService documentTypeService
    ILocalAuthorityRegistry localAuthorityRegistry

    def static subMenuEntries = BackofficeRequestAdminController.subMenuEntries

    def beforeInterceptor = { session["currentMenu"] = "requestAdmin" }

    def list = {
        return [
            "subMenuEntries" : subMenuEntries
        ]
    }

    def _types = {
        def list = []
        documentTypeService.getAllDocumentTypes().each{ d ->
            list.add([
                'id' : d.id,
                'name' : message(code:LibredematUtils.adaptDocumentTypeName(d.name)),
                'code' : LibredematUtils.adaptDocumentTypeName(d.name)
            ])
        }
        list = list.sort{it.name.toLowerCase()}
        return [
            "documentTypes" : list
        ]
    }

    def createForm = {
        def validities = []
        DocumentTypeValidity.allDocumentTypeValidity.each { val ->
            validities.add([
                'id' : val.name.toUpperCase().replaceAll(" ", "_"),
                'name' : message(code:"documentTypeValidity."+LibredematUtils.codifyName(val.name)),
                ])
        }
        render( template : "form",
                model : [
                    "documentTypeValidityValues" : validities,
                    "usageTypeValues" : DocumentUsageType.allDocumentUsageType,
                    "validityDurationFormStyle" : "display:none"
                ]
            )
        return false
    }

    def editForm = {
        def docType = documentTypeService.getDocumentTypeById(params.id.toInteger())
        def validities = []
        DocumentTypeValidity.allDocumentTypeValidity.each { val ->
            validities.add([
                'id' : val.name.toUpperCase().replaceAll(" ", "_"),
                'name' : message(code:"documentTypeValidity."+LibredematUtils.codifyName(val.name)),
                ])
        }
        render( template : "form",
                model : [
                    "documentTypeValidityValues" : validities,
                    "usageTypeValues" : DocumentUsageType.allDocumentUsageType,
                    "documentType" : docType,
                    'name' : StringEscapeUtils.escapeHtml4(message(code:LibredematUtils.adaptDocumentTypeName(docType.name))),
                    "codename" : LibredematUtils.adaptDocumentTypeName(docType.getName()),
                    "validityDurationFormStyle" : this.displayValidityDurationForm(docType)?"":"display:none"
                ]
            )
        return false
    }

    def deleteDocumentType = {
        if(!(documentTypeService.isRequiredByARequest(params.id.toInteger()) || documentTypeService.isUsedInARequest(params.id.toInteger())) ) {
            documentTypeService.delete(documentTypeService.getDocumentTypeById(params.id.toInteger()))
            render([status:"success", success_msg:message(code : "message.deleteDone")] as JSON)
            return false
        }
        def docTypeName = message(code:LibredematUtils.adaptDocumentTypeName(documentTypeService.getDocumentTypeById(params.id.toInteger()).name))
        render([status:"error", error_msg:message(code : "documentType.error.requiredAnrOrUsedInRequest", args:[docTypeName])] as JSON)
        return false
    }


    def editDocumentType = {
        if(request.post) {
            def name = WordUtils.capitalize(StringUtils.cleanString(apacheCommonLang3StringUtils.trim(params.name)))

            def libelle = apacheCommonLang3StringUtils.trim(params.name)

            def codename = params.codename
            if(codename == '') codename = LibredematUtils.adaptDocumentTypeName(name)

            if((params.id == null || params.id.trim().isEmpty())
                && (documentTypeService.nameAlreadyInUse(name)))
            {
                render([status:"error", error_msg:message(code : "documentType.error.nameAlreadyInUse", args : [name])] as JSON)
                return false
            }
            else if (name == '') {
                render([status:"error", error_msg:message(code : "documentType.error.nameInvalid", args : [name])] as JSON)
                return false
            }
            else {
                def assets_dir = localAuthorityRegistry.assetsBase
                def la_name = SecurityContext.getCurrentConfigurationBean().getName()

                File i18n_dir = new File(assets_dir+la_name+"/i18n/");
                File documentTypesProperties = new File(assets_dir+la_name+"/i18n/documents.properties");
                def dirStructOk = true
                if(!i18n_dir.exists())
                {
                    dirStructOk = i18n_dir.mkdirs()
                    if(!documentTypesProperties.exists())
                        dirStructOk = documentTypesProperties.createNewFile()
                }
                else{
                    if(!documentTypesProperties.exists())
                        dirStructOk = documentTypesProperties.createNewFile()
                }

                if(dirStructOk)
                {
                    def originMessagesFile = assets_dir+la_name+"/i18n/documents.properties"
                    def codeString
                    if (params.id == null || params.id.trim().isEmpty()) {
                        OutputStreamWriter writer = new OutputStreamWriter(
                              new FileOutputStream(originMessagesFile, true), "UTF-8");
                        BufferedWriter fbw = new BufferedWriter(writer);
                        fbw.newLine();
                        fbw.write(codename+" = "+libelle);
                        fbw.newLine()
                        fbw.close()
                        writer.close()

                        def type = new DocumentType()
                        type.setName(name)
                        type.setType(documentTypeService.getNextTypeId())
                        type.setValidityDurationType(DocumentTypeValidity.valueOf(params.validityDurationType.toUpperCase()))
                        type.setValidityDuration(params.validityDuration ? params.validityDuration.toInteger() : 0)
                        type.setUsageType(params.usageType ? DocumentUsageType.REUSABLE : DocumentUsageType.SINGLE_USE)


                        documentTypeService.create(type);
                        codeString = "message.creationDone"

                    } else {
                        def tempMessagesFile = originMessagesFile+".temporaryFile"

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(originMessagesFile)));
                        String line;

                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempMessagesFile),"UTF8"));

                        def written = false

                        while((line = br.readLine()) != null) {
                            if(!line.startsWith(codename)) {
                                out.write(line)
                            } else {
                                out.write(codename+" = "+libelle)
                                written = true
                            }
                            out.newLine()
                        }
                        if(!written)
                            out.write(codename+" = "+libelle)

                        out.close();
                        br.close();

                        def originFile = new File(originMessagesFile)
                        def finalFile = new File(tempMessagesFile)

                        if(!originFile.delete())
                            throw new Exception("unable_to_delete_file "+tempMessagesFile)

                        finalFile.renameTo(originFile);

                        DocumentType type = documentTypeService.getDocumentTypeById(Long.valueOf(params.id))
                        type.setValidityDurationType(DocumentTypeValidity.valueOf(params.validityDurationType.toUpperCase()))
                        type.setValidityDuration(params.validityDuration ? params.validityDuration.toInteger() : 0)
                        type.setUsageType(params.usageType ? DocumentUsageType.REUSABLE : DocumentUsageType.SINGLE_USE)


                        documentTypeService.update(type);
                        codeString = "message.updateDone"
                    }
                    render([status:"success", success_msg:message(code : codeString)] as JSON)
                    return false
                }
            }

        }
        return false
    }

    def displayValidityDurationForm(docType) {
        return docType.getValidityDurationType() == DocumentTypeValidity.YEAR ||
            docType.getValidityDurationType() == DocumentTypeValidity.MONTH
    }
}
