import org.apache.commons.lang.StringUtils;

import fr.cg95.cvq.business.authority.LocalAuthorityResource.Type
import fr.cg95.cvq.business.payment.ExternalAccountItem;
import fr.cg95.cvq.business.payment.ExternalDepositAccountItem;
import fr.cg95.cvq.business.payment.ExternalInvoiceItem;
import fr.cg95.cvq.business.payment.ExternalTicketingContractItem;
import fr.cg95.cvq.business.payment.InternalInvoiceItem;
import fr.cg95.cvq.business.payment.Payment
import fr.cg95.cvq.business.payment.PaymentState
import fr.cg95.cvq.business.payment.PaymentMode
import fr.cg95.cvq.business.payment.PurchaseItem
import fr.cg95.cvq.service.payment.IPaymentService
import fr.cg95.cvq.service.payment.PaymentUtils;
import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.util.Critere

import grails.converters.JSON

class BackofficePaymentController {
    
    IPaymentService paymentService
    ILocalAuthorityRegistry localAuthorityRegistry
    
    def defaultAction = 'search'
    def defaultSortBy = 'initializationDate'
    def supportedKeys = ["requesterLastName", "homeFolderId", "cvqReference","bankReference", 
                         "initDateFrom", "initDateTo"]
    def longKeys = ["homeFolderId"]
    def dateKeys = ["initDateFrom", "initDateTo"]
    def resultsPerPage = 25
    
    def beforeInterceptor = {
        session['currentMenu'] = 'payment'
    }

    def afterInterceptor = { model ->
        model['subMenuEntries'] = ['payment.search', 'payment.configure', 'externalApplication.applications']
    }
    
    def configure = {
        return false
    }

    def displayConfiguration = {
        if (request.get) {
            render(template : "displayConfiguration",
                   model : ["displayInProgressPayments" : SecurityContext.currentSite.displayInProgressPayments])
            return false
        } else if (request.post) {
            bind(SecurityContext.currentSite)
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
            return false
        }
    }

    def deactivation = {
        if (request.get) {
            def paymentDeactivationStartDate = SecurityContext.currentSite.paymentDeactivationStartDate
            def paymentDeactivationEndDate = SecurityContext.currentSite.paymentDeactivationEndDate
            def inactive = paymentDeactivationStartDate || paymentDeactivationEndDate
            if (!inactive) paymentDeactivationStartDate = new Date()
            render(template : "deactivation", model : [
                "inactive" : inactive,
                "paymentDeactivationStartDate" : paymentDeactivationStartDate,
                "paymentDeactivationEndDate" : paymentDeactivationEndDate
            ])
            return false
        } else if (request.post) {
            if (!params.inactive) {
                SecurityContext.currentSite.paymentDeactivationStartDate = null
                SecurityContext.currentSite.paymentDeactivationEndDate = null
            } else {
                bind(SecurityContext.currentSite)
            }
            localAuthorityRegistry.saveLocalAuthority(SecurityContext.currentSite)
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
            return false
        }
    }

    def displayedMessage = {
        def name = "paymentlackmessage"
        File file = localAuthorityRegistry.getLocalAuthorityResourceFile(
            Type.HTML, name, false)
            
        if(!file.exists()) {
            localAuthorityRegistry.saveLocalAuthorityResource(Type.HTML, name,
                "".getBytes());
            file = localAuthorityRegistry.getLocalAuthorityResourceFile(
                Type.HTML, name, false)
        }
        
        if(request.post) {
            def String content = (params.editor == null ? "" : params.editor.toString())
            localAuthorityRegistry.saveLocalAuthorityResource(Type.HTML, name,
                content.getBytes());

            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
        } else {
            render(template:'displayedMessage',model:[editorContent:file.getText()])
        }
    }

    def details = {
        fr.cg95.cvq.business.payment.PurchaseItem.metaClass.type = {
            return StringUtils.removeStartIgnoreCase(delegate.getClass().toString(), "class fr.cg95.cvq.business.payment.")
        }
        Payment payment = this.paymentService.getById(Long.parseLong(params.id))
        // hack to lazy load
        payment.getPurchaseItems().each{ it.id}
        def result = [
            'payment' : payment,
            'requesterId' : payment.getRequesterId(),
            'state': payment.getState().toString().toLowerCase(),
            'items': payment.getPurchaseItems()
        ]
        return result
    }

    def homeFolder = {
        def result = [payments:[]]
        Payment payment = this.paymentService.getById(Long.valueOf(params.id))
        def homeFolderPayments = 
            this.paymentService.getByHomeFolder(payment.getHomeFolderId())

        homeFolderPayments.each {
            def record = [
                'id':it.id,
                'broker':it.broker,
                'cvqReference':it.cvqReference,
                'bankReference':it.bankReference,
                'requesterLastName':it.requesterLastName + " " + it.requesterFirstName,
                'homeFolderId':it.homeFolderId,
                'initializationDate':it.initializationDate,
                'commitDate':it.commitDate,
                'paymentState':it.state.toString(),
                'amount':it.amount,
                'paymentMode':it.paymentMode.toString()
            ]
            result.payments.add(record)
        }
        return result
    }

    def search = {

        // deal with search criteria
        Set<Critere> criteria = new HashSet<Critere>()
        params.each { key,value ->
            if (supportedKeys.contains(key) && value != "") {
                Critere critere = new Critere()
                critere.attribut = key
                critere.comparatif = Critere.EQUALS
                if (key == 'requesterLastName')
                    critere.comparatif = Critere.STARTSWITH
                if (longKeys.contains(key)) {
                    critere.value = LongUtils.stringToLong(value.trim())
                } else if (dateKeys.contains(key)) {
                    critere.value = DateUtils.stringToDate(value)
                    if (key == 'initDateFrom') {
                        critere.attribut = 'initializationDate'
                        critere.comparatif = Critere.GTE
                    } else { 
                        critere.attribut = 'initializationDate'
                        critere.comparatif = Critere.LTE
                    }
                } else {
                    critere.value = value.trim()
                }
                criteria.add(critere)
            }
        }
        
        // deal with dynamic filters
        def parsedFilters = SearchUtils.parseFilters(params.filterBy)
        parsedFilters.filters.each { key, value ->
            Critere critere = new Critere()
            critere.attribut = key.replaceAll('Filter','')
            critere.comparatif = Critere.EQUALS
            if (key == 'paymentStateFilter') {
                critere.value = PaymentState.forString(value)
            } else if (key == 'paymentModeFilter') {
                critere.value = PaymentMode.forString(value)
            } else {
                critere.value = value
            }
            criteria.add(critere)
        }
        
        // deal with dynamic sorts
        def sortBy = params.sortBy ? params.sortBy : defaultSortBy 
        def dir = null
        if (sortBy.equals("initializationDate")) dir = "desc"
        
        // deal with pagination settings
        def results = params.results == null ? resultsPerPage : Integer.valueOf(params.results)
        def recordOffset = 0
        if (params.paginatorChange.equals("true"))
            recordOffset = Integer.valueOf(params.recordOffset)        
                    
        def payments = paymentService.get(criteria, sortBy, dir, results, recordOffset)        
                      
        def recordsList = []

        payments.each {
            def record = [
                'id':it.id,
                'broker':it.broker,
                'cvqReference':it.cvqReference,
                'bankReference':it.bankReference,
                'requesterLastName':it.requesterLastName + " " + it.requesterFirstName,
                'homeFolderId':it.homeFolderId,
                'initializationDate':it.initializationDate,
                'commitDate':it.commitDate,
                'paymentState':it.state.toString(),
                'amount':it.amount,
                'paymentMode':it.paymentMode.toString()
            ]		
            recordsList.add(record)
        }
        render(view:'search', model:[
                                     'recordsReturned':payments.size(),
                                     'totalRecords':paymentService.getCount(criteria),
                                     'recordOffset':recordOffset,
                                     'records':recordsList,
                                     'paymentsIds':paymentService.getIds(criteria),
                                     'filters':parsedFilters.filters,
                                     'filterBy':parsedFilters.filterBy,
                                     'sortBy':params.sortBy,
                                     'dir':params.dir,
                                     'inSearch':true].plus(initSearchReferential()))        
    }
    
    def initSearchReferential() {
    	return ['allStates':PaymentState.allPaymentStates,
    	        'allBrokers':paymentService.getAllBrokers(),
    	        'allModes': PaymentMode.allPaymentModes]
    }

    def export = {
        def ids = []
        if (params.ids instanceof String) ids.add(Long.valueOf(params.ids))
        else params.ids.each { ids.add(Long.valueOf(it)) }
        def file = paymentService.exportPayments(ids)
        byte[] data = file.readBytes()

        response.setHeader("Content-disposition", "attachment; filename=\"" + file.name + "\"")
        response.contentType = "text/csv"
        response.contentLength = data.length
        response.outputStream << data
    }
}
