import org.libredemat.exception.CvqException
import org.libredemat.security.SecurityContext;
import org.libredemat.service.users.IUserService;

import grails.converters.JSON

class BackofficeUserAdminController {

    IUserService userService

    def defaultAction = 'index'

    def subMenuEntries = [
        "userAdmin.index",
        "userSecurity.index",
        "homeFolder.meansOfContact",
        "homeFolder.importHomeFolders",
        "homeFolder.childInformationSheetDateInitialisation"]

    def beforeInterceptor = {
        session['currentMenu'] = 'users'
    }

    def index = {
        if (request.get) {
            return [
                "subMenuEntries" : subMenuEntries,
                "globalUserConfiguration" : userService.getGlobalHomeFolderConfiguration()
            ]
        } else if (request.post) {
            bind(SecurityContext.getCurrentSite())
            bind(userService.getGlobalHomeFolderConfiguration())
            render ([status:"success", success_msg:message(code:"message.updateDone")] as JSON)
            return false
        }
    }
}
