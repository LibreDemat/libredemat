import javax.servlet.http.HttpServletRequest

class BootStrap {
    
    def init = { servletContext ->
        servletContext.setAttribute("newDataBinder", GlobalPropertyEditorConfig.&newDataBinder)
        HttpServletRequest.metaClass.isXhr = {->
            "XMLHttpRequest" == delegate.getHeader("X-Requested-With")
        }
    }
    
    def destroy = {
    }
} 
