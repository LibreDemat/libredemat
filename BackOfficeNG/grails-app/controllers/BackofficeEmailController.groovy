import org.libredemat.business.request.RequestVariable
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.business.authority.LocalAuthorityResource.Type
import org.libredemat.security.SecurityContext

import grails.converters.JSON

class BackofficeEmailController {

    IRequestTypeService requestTypeService
    ILocalAuthorityRegistry localAuthorityRegistry

    def emailNotificationAdaptorService
    def beforeInterceptor = [action:this.&check, only:['email', 'saveEmail']]

    def check() {
        if (params.requestTypeId) {
            def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.requestTypeId))
            if (!requestType) {
                render(status:404, text:['message':message(code:'requestType.configuration.unknownRequestType')] as JSON)
                return false
            }
        }
    }

    /**
     * Return the directory where to look for templates.
     */
    def dir() {
        if (params.requestTypeId) {
            def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.requestTypeId))
            def dir = LibredematUtils.requestTypeLabelAsDir(requestType.label)
            return 'templates/mails/notification/' + dir
        } else {
            return 'templates/mails/notification'
        }
    }

    def email = {
        def state = (params.state - 'request.state.').toUpperCase()

        File file = localAuthorityRegistry.getLocalAuthorityResourceFile(
            Type.HTML,
            dir() + '/' + state,
            false)
        def text
        try {
            text = file.getText('utf-8')
        } catch(FileNotFoundException fnfe) {
            text = ''
        } finally {
            render(text:text)
        }
    }

    def saveEmail = {
        def state = (params.state - 'request.state.').toUpperCase()

        def html = params.html ?: ''
        def emptyhtml = """\
<html>
<head>
  <title></title>
</head>
<body>&nbsp;</body>
</html>
"""
        def texts
        if (html && html != emptyhtml) {
          localAuthorityRegistry.saveLocalAuthorityResource(
              Type.HTML,
              dir() + '/' + state,
              html.getBytes('UTF-8'))

          texts = [ option:message(code:params.state) + ' (' + message(code:'requestType.configuration.emails.enabled') + ')'
                  , notif:message(code:'requestType.configuration.emails.notify.enabled', args:[message(code:params.state)])
                  ]

          render([ id: params.state
                 , enabled: true
                 , canBeDisabled: !(state == 'PENDING' && session.currentCredentialBean.hasSiteAdminRole())
                 , texts: texts
                 ] as JSON)

        } else if (!(state == 'PENDING' && session.currentCredentialBean.hasSiteAdminRole())) {

          localAuthorityRegistry.removeLocalAuthorityResource(
              Type.HTML,
              dir() + '/' + state)

          def platformActivated = emailNotificationAdaptorService.states('templates/mails/notification').findAll { it.code == params.state && it.enabled }.size() > 0

          def txtOpt = message(code:params.state)
          if(platformActivated) txtOpt += ' (' + message(code:'requestType.configuration.emails.platformConfigurationEnabled') + ')'

          texts = [ option: txtOpt
                  , notif:message(code:'requestType.configuration.emails.notify.disabled', args:[message(code:params.state)])
                  ]
          render([ id: params.state
                 , enabled: false
                 , canBeDisabled: false
                 , texts: texts
                 ] as JSON)
        } else {
          render([ id: params.state, canBeDisabled: false, enabled: true ] as JSON)
        }
    }

    /**
    * Return groups of email variables, in JSON:
    * [ { label: 'Sujet'
    *   , variables: [ {label:'Prénom', variable:'RR_FNAME'} ]
    *   }
    * ]
    */
    def variables = {
        def groups = [], variables, grouplabel, variablelabel

        RequestVariable.grouped().each { group ->
            grouplabel = message(code:'request.variable.group.' + group.key.toString().toLowerCase())

            variables = group.value.inject([], { array, variable ->
                variablelabel = message(code:'request.variable.' + variable.toString().toLowerCase())
                array.add([label:variablelabel, variable:'#{' + variable.toString() + '}'])
                return array
            })

            groups.add([label:grouplabel, variables:variables])
        }
        render groups as JSON
    }

}
