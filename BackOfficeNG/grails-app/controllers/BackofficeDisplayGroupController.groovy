import org.libredemat.business.request.DisplayGroup
import org.libredemat.service.request.IDisplayGroupService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.business.authority.LocalAuthorityResource.Type
import org.libredemat.business.authority.LocalAuthorityResource.Version

import grails.converters.JSON

class BackofficeDisplayGroupController {
    
    ILocalAuthorityRegistry localAuthorityRegistry
    IDisplayGroupService displayGroupService
    IRequestTypeService requestTypeService
    
    def translationService
    def defaultAction = 'list'
    
    def beforeInterceptor = { session['currentMenu'] = 'requestAdmin' }
    
    def static subMenuEntries = BackofficeRequestAdminController.subMenuEntries

    def list = {
        def displayGroups = displayGroupService.getAll().sort{it.label}
        
        def requestTypesByDisplayGroup = [:]
        displayGroups.each { dg ->
            requestTypesByDisplayGroup[dg.id] = []
        	  dg.requestTypes?.each { rt ->
        	      requestTypesByDisplayGroup[dg.id].add(LibredematUtils.adaptRequestType(translationService, rt))
        	  }
        	  requestTypesByDisplayGroup[dg.id] = requestTypesByDisplayGroup[dg.id].sort{it.weight!=null ? it.weight : it.label}
        }
        def orphanRequestTypes = []
        requestTypeService.getAllRequestTypes().each {
            if (it.displayGroup == null)
                orphanRequestTypes.add(LibredematUtils.adaptRequestType(translationService,it))
        }

        return ['subMenuEntries':subMenuEntries,
                'displayGroups':displayGroups,
                'requestTypesByDisplayGroup':requestTypesByDisplayGroup,
                'orphanRequestTypes':orphanRequestTypes
               ]
    }

    def edit = {           
        def displayGroup = displayGroupService.getById(Long.valueOf(params.id))
        def displayGroups = displayGroupService.getAll()
        def requestTypes = []
        displayGroup.requestTypes.each { 
            requestTypes.add(LibredematUtils.adaptRequestType(translationService,it))
        }
        requestTypes = requestTypes.sort{it.weight!=null ? it.weight : it.label}

        return ['subMenuEntries':subMenuEntries,
                'editMode':'edit', 
                'displayGroups':displayGroups,
                'displayGroup':displayGroup,
                'hasLogo' : localAuthorityRegistry.getLocalAuthorityResourceFile(
                    Type.DISPLAY_GROUP_IMAGE, displayGroup.name, Version.CURRENT, true)?.exists(),
                'requestTypes':requestTypes,
                'orderRequestTypeBy':'label',
                'scope':'bounded'
               ]
    }

    def create = {
        render(view:'edit',model:['subMenuEntries':subMenuEntries, 
                                  editMode:"create", displayGroups:displayGroupService.getAll()])
    }

    def save = {
        def displayGroup = null
        def create = true
        if (params.id != null && params.id != "") {
            displayGroup = displayGroupService.getById(Long.valueOf(params.id))
            def oldName = displayGroup.name
            bindData(displayGroup, params)
            displayGroupService.modify(displayGroup)
            if (Boolean.valueOf(params.hasLogo))
                localAuthorityRegistry.renameLocalAuthorityResource(Type.DISPLAY_GROUP_IMAGE, oldName, displayGroup.name)
            create = false
        } else {
            displayGroup = new DisplayGroup()
            bindData(displayGroup, params)
            displayGroupService.create(displayGroup)
        }

        render ([ 'id': displayGroup.id, 'displayGroupLabel':displayGroup?.label, 'displayGroupName':displayGroup?.name,
                  'status':'success', 'message':message(code:'message.updateDone')] as JSON)
    }

    def delete = {
        def displayGroup = displayGroupService.getById(Long.valueOf(params.id))
        displayGroupService.delete(Long.valueOf(params.id))
        localAuthorityRegistry.removeLocalAuthorityResource(Type.DISPLAY_GROUP_IMAGE, displayGroup.name)
        render (['status':'success', 'message':message(code:'message.deleteDone')] as JSON)
    }

    def logo = {
        def file = request.getFile('logo')
        response.contentType = 'text/html; charset=utf-8'
        if (file.empty) {
            render (new JSON(['status':'warning', 'message':message(code:'displayGroup.message.noLogoFile')]).toString())
            return false
        }
        if (Boolean.valueOf(params.hasLogo))
            localAuthorityRegistry.removeLocalAuthorityResource(Type.DISPLAY_GROUP_IMAGE, params.name)
        localAuthorityRegistry.saveLocalAuthorityResource(Type.DISPLAY_GROUP_IMAGE, params.name, file.bytes)
        render (new JSON([ 'rand' : UUID.randomUUID().toString(),
                           'status':'success', 
                           'message':message(code:'displayGroup.message.logoUpdateDone')]).toString())
    }

    /* requestType managment
     * --------------------------------------------------------------------- */

    def requestTypes = {
        def requestTypes = []
        
        if ((request.post && params.scope == null) || params.scope == 'all') {
            requestTypeService.getAllRequestTypes().each{
                requestTypes.add(LibredematUtils.adaptRequestType(translationService,it)) 
            }
        } else if (params.scope == 'bounded') {
            displayGroupService.getById(Long.valueOf(params.id)).requestTypes.each {
                requestTypes.add(LibredematUtils.adaptRequestType(translationService,it))
            }
        }

        def orderRequestTypeBy
        if (params.orderRequestTypeBy == null || params.orderRequestTypeBy == "label") {
            requestTypes = requestTypes.sort{ it.label.toLowerCase() }
            orderRequestTypeBy = "label"
        } else if (params.orderRequestTypeBy == "displayGroupLabel") {
            requestTypes = requestTypes.sort{ it.displayGroupLabel != null ? it.displayGroupLabel.toLowerCase() : "zzz" }
            orderRequestTypeBy = "displayGroupLabel"
        }

        render( template:"requestTypes",
                model:[ displayGroupId: new Long(params.id), requestTypes: requestTypes, 
                        orderRequestTypeBy: orderRequestTypeBy, scope:params.scope ])
    }

    def displayOrder = {
        def toto = JSON.parse(params.json)
        toto.each{ requestTypeService.setRequestTypeWeight(it.id, it.weight)}
        render([status:'success', success_msg:message(code:"message.updateDone")] as JSON)
    }

    def associateRequestType = {
        def displayGroup = 
            displayGroupService.addRequestType(Long.valueOf(params.displayGroupId),Long.valueOf(params.requestTypeId))
        render ([ displayGroupName:displayGroup.name,
                  status:'success', success_msg:message(code:"message.updateDone")] as JSON)
    }

    def unassociateRequestType = {
        def displayGroup = 
            displayGroupService.removeRequestType(Long.valueOf(params.displayGroupId),Long.valueOf(params.requestTypeId))
         render ([ displayGroupName:displayGroup.name,
            status:'success', success_msg:message(code:"message.updateDone")] as JSON)
    }

}
