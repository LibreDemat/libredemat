
import org.libredemat.business.users.Individual
import org.libredemat.business.users.Child
import org.libredemat.business.users.Adult
import org.libredemat.security.SecurityContext
import org.libredemat.service.users.IUserSearchService
import org.libredemat.authentication.IAuthenticationService
import org.libredemat.exception.CvqAuthenticationFailedException
import org.libredemat.exception.CvqDisabledAccountException
import org.libredemat.exception.CvqUnknownUserException
import org.libredemat.exception.CvqObjectNotFoundException
import grails.converters.JSON
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.oauth2.IOAuth2Service
import org.libredemat.oauth2.model.AccessToken
import org.libredemat.oauth2.OAuth2Exception
import org.libredemat.service.authority.IAgentService;
import org.libredemat.service.request.ICategoryService
import org.libredemat.service.payment.IPaymentService
import org.libredemat.business.payment.ExternalAccountItem
import org.libredemat.business.payment.ExternalInvoiceItem
import org.libredemat.business.payment.ExternalInvoiceItemDetail
import org.libredemat.service.users.external.IExternalHomeFolderService
import org.libredemat.service.users.IUserNotificationService
import org.libredemat.external.ExternalServiceBean
import org.libredemat.exception.CvqException
import org.libredemat.dao.users.IIndividualMappingDAO
import org.libredemat.dao.users.IHomeFolderMappingDAO
import org.libredemat.business.payment.PaymentMode

import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang.StringUtils

import java.text.SimpleDateFormat

class ServiceInvoiceController {
    IAuthenticationService authenticationService
    ILocalAuthorityRegistry localAuthorityRegistry

    IPaymentService paymentService
    IExternalHomeFolderService externalHomeFolderService
    IUserNotificationService userNotificationService
    IUserSearchService userSearchService
    IIndividualMappingDAO individualMappingDAO
    IHomeFolderMappingDAO homeFolderMappingDAO

    def beforeInterceptor = {
        def token = request.getAttribute("accessToken")
        if (!token?.sufficientScope("individual")) {
            forward(controller: 'OAuth2', action: 'invalidScope')
            return false;
        }
    }

    def create = {
        def token = request.getAttribute("accessToken")
        def user

        try {
          if(params.eCitizenId != null && params.eCitizenId != "") {
            def agent = agentService.getByLogin(token.resourceOwnerName)
            if(agent) {
                user = userSearchService.getById(params.eCitizenId as Long)
            } else {
                render(status: 403)
            }
          } else {
            user = userSearchService.getByLogin(token.resourceOwnerName)
          }
        } catch (CvqObjectNotFoundException ex) {
          render(status: 404)
        }


        def datas = JSON.parse(params.line)

        def lines = []
        def resume = "\n" + message(code : "homeFolder.adult.user.inquestions") + " |     "+ message(code : "activity.title") + " |    " + message(code : "event.property.dates") + "    |    " + message(code : "payment.header.values") + "\n";
        def activityId = ""
        datas.each {
          if (it != null) {
            println(it)

/*
[
subject_name:Émilie MOREAU,
slot_start:2014-10-31T10:00:46.681+01:00,
activity_id:1P,
allDay:false,
status:booking,
modifiable:true,
reservable:,
site_id:-1,
activity_name:PMI - Semainier,
site_name:,
_start:2014-10-31T09:00:46.681Z,
_end:2014-10-31T13:00:46.681Z,
slot_end:2014-10-31T14:00:46.681+01:00,
productId:1P,
guid:346f6243-8c5b-4f9c-976d-a24c4e2cdd3c,
id:slot14530,
title:Aucun enfant à cette période,
siteId:1,
source:[className:[],
url:/blainville/slots?guid=346f6243-8c5b-4f9c-976d-a24c4e2cdd3c&siteid=-1&productid=1P],
_id:slot14530,
start:2014-10-31T09:00:46.681Z,
className:[],
end:2014-10-31T13:00:46.681Z]


          invoiceDetail.setLabel(it.labelService)
          invoiceDetail.setQuantity(new BigDecimal(1))
          invoiceDetail.setSubjectName(it.lastName)
          invoiceDetail.setSubjectSurname(it.fisrtName)
          invoiceDetail.setUnitPrice(it.dayAmount.toInteger())
          invoiceDetail.setValue(it.dayAmount.toInteger())
          totalFacture = totalFacture + it.dayAmount.toInteger()
*/


            //List<IndividualMapping>
            def mappings = individualMappingDAO.findByExternalLibredematId(it.guid)

println("_------------------------------ : "+it.guid)
println(mappings)
            def indId = mappings[0].getIndividualId()

            //def elements = it.toString.tokenize("-")
            activityId = it.activity_id
            Individual ind = userSearchService.getById(indId)
            def invoiceLine = [:]
            invoiceLine.labelService = it.activity_name
            invoiceLine.lastName = ind.lastName
            invoiceLine.fisrtName = ind.firstName
            invoiceLine.dayAmount = 777 // TODO: DELETTE ME!
            if(it.amount != null)
              invoiceLine.dayAmount = it.amount
            lines.add(invoiceLine)

            double amount = -0.00;
            def day = "NC"
            def isNegatif = false;
            try
            {
              amount = Double.parseDouble(invoiceLine.dayAmount) /// 100;
              day = new SimpleDateFormat("yyyy/MM/dd").parse(elements[4]);
              day = day.format("dd-MM-yyyy");
              isNegatif = Boolean.valueOf(elements[6]);
            }
            catch (Exception ex)
            {
            }
            resume += invoiceLine.lastName + " " + invoiceLine.fisrtName + " | " + invoiceLine.labelService + " | " + day + " | " + (isNegatif?"-":"") + amount + "€\n"; 
            // Hack inexine - le euro ne saffiche pas dans le properties
          }
        }

        def res = setInvoice(user, params.amount.toInteger(), lines, activityId)

        ExternalAccountItem eai = (ExternalAccountItem) res.invoices[0]


        SecurityContext.setCurrentContext(SecurityContext.FRONT_OFFICE_CONTEXT)
        SecurityContext.setCurrentEcitizen(user)

        def payment = paymentService.createPaymentContainer(eai, PaymentMode.INTERNET)
        if(params.amount.toInteger() <= 0)
        {
          payment.addPaymentSpecificData('scheme',request.scheme)
          payment.addPaymentSpecificData('domainName',request.serverName)
          payment.addPaymentSpecificData('port',request.serverPort.toString())
          
          // Hack inexine - send a mail if is active in configuration.
          ExternalServiceBean externalBean = SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getBeanForExternalService("CirilNetEnfance");
          if (Boolean.valueOf(externalBean.getProperty("isActiveSendMailInValidNoPay")))
          {
            // send Mail
            def from = externalBean.getProperty("mailFrom");
            Adult responsible = userSearchService.getHomeFolderResponsible(user.homeFolderId.toLong());
            if (responsible != null)
            {
              def to = responsible.getEmail();
              userNotificationService.notifyByEmail(from, to, message(code:"mail.civilnetenfance.senMailInValidWithPay.subject"),
                message(code:"mail.civilnetenfance.senMailInValidWithPay.body", args:[resume]), null, null)
            }
            else
            {
              resultResponse.message += message(code:"nomail.nomessage");
            }
          }
          // fin hack inexine
          
          //def paymentUrl = paymentService.initPayment(payment).toString()
          //session.payment = null;

          url = g.createLink(controller:"serviceCiril", action:'negativePayment', params: ["ref":payment.getCvqReference(), "transNum":session.activityId, "refsfp":session.activityId, "erreur":"00000"])

          render(["url" : url] as JSON)
        }
        else 
        {
          payment.addPaymentSpecificData('scheme',request.scheme)
          payment.addPaymentSpecificData('domainName',request.serverName)
          payment.addPaymentSpecificData('port',request.serverPort.toString())
          
          // Hack inexine - send a mail if is active in configuration.
          ExternalServiceBean externalBean = SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getBeanForExternalService("CirilNetEnfance");
          if (Boolean.valueOf(externalBean.getProperty("isActiveSendMailInValidNoPay")))
          {
            // send Mail
            def from = externalBean.getProperty("mailFrom");
            Adult responsible = userSearchService.getHomeFolderResponsible(params.homeFolderId.toLong());
            if (responsible != null)
            {
              def to = responsible.getEmail();
              userNotificationService.notifyByEmail(from, to, message(code:"mail.civilnetenfance.senMailInValidWithPay.subject"),
                message(code:"mail.civilnetenfance.senMailInValidWithPay.body", args:[resume]), null, null)
            }
            else
            {
              resultResponse.message += message(code:"nomail.nomessage");
            }
          }
          // fin hack inexine

          def paymentUrl = paymentService.initPayment(payment).toString();
          session.payment = null;

          render(['url' : paymentUrl] as JSON)
        }
    }

    def setInvoice(Adult ecitizen, Integer amount, List invoiceLine, String activityId) {
      def sinvoice =  []
      def sinvoiceDetail = []
      def result = [:]
      result.invoices = []
      result.invoiceDetail =[]
      def mapBroker = paymentService.getAllBrokers()
      if (mapBroker == null) 
      { 
        throw new CvqException("error.broker.notexist");
      }
      def broker = [:]
      if(!mapBroker.isEmpty())
      {
        mapBroker.each{
          if(it.key=='FACTURATION SCOLAIRE'){
            broker.broker= it.key 
            broker.label = it.value
          }
        }
      } else {
        broker.broker = 'FACTURATION SCOLAIRE'
        broker.label = 'Services scolaires'
      }
      
      def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance", ecitizen.homeFolder.id)
      if(hfm != null){
        ExternalInvoiceItem invoice = new ExternalInvoiceItem()
        invoice.setLabel("Reservation en ligne")
        invoice.setExternalServiceLabel(hfm.externalServiceLabel)
        invoice.setExternalItemId(activityId)
        invoice.setIssueDate(new Date())
        invoice.setExpirationDate(new Date())
        invoice.setPaymentDate(new Date())
        invoice.setIsPaid(false)
        invoice.setSupportedBroker(broker.broker)
        
        def invoiceDetailTab = []
        def totalFacture = 0
        invoiceLine.each{
          ExternalInvoiceItemDetail invoiceDetail = new ExternalInvoiceItemDetail()
          invoiceDetail.setLabel(it.labelService)
          invoiceDetail.setQuantity(new BigDecimal(1))
          invoiceDetail.setSubjectName(it.lastName)
          invoiceDetail.setSubjectSurname(it.fisrtName)
          invoiceDetail.setUnitPrice(it.dayAmount.toInteger())
          invoiceDetail.setValue(it.dayAmount.toInteger())
          totalFacture = totalFacture + it.dayAmount.toInteger()
          invoice.getInvoiceDetails().add(invoiceDetail)
          invoiceDetailTab.add(invoiceDetail)
        }
        if(amount.toInteger() < 0){
          invoice.setAmount(new Double(totalFacture.toString()))
          invoice.setTotalValue(new Double(totalFacture.toString()))
        } else {
          invoice.setAmount(new Double(amount.toString()))
          invoice.setTotalValue(new Double(amount.toString()))
        }
        
        //session.invoice.add(invoice)
        //session.invoiceDetail.add(invoiceDetailTab)
        result.invoices.add(invoice)
        result.invoiceDetail.add(invoiceDetailTab)
      }
      return result
    }

}
