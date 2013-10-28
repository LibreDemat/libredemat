// TODO - maybe merge all tags
class LibredematEnumTagLib {
    
    def libredematEnumToFlag = { attrs, body ->
        def libredematEnum
        if (request.requestURI.contains("frontoffice"))
        {
            libredematEnum = LibredematUtils.adaptLibredematEnum(attrs.var, attrs.i18nKeyPrefix,"frontoffice")
        }else
        {
            libredematEnum = LibredematUtils.adaptLibredematEnum(attrs.var, attrs.i18nKeyPrefix)
        }
        
        def sb = new StringBuffer()
        sb << "<span class=\"" 
        sb << libredematEnum.cssClass
        sb << " tag-state\">"
        sb << g.message(code: libredematEnum.i18nKey)
        sb << "</span>"
          
	      out << body() << sb
	}

    def tag = { attrs, body ->
        def sb = new StringBuffer()
        sb << "<span class=\"tag ${attrs.var}\">"
        sb << g.message(code: attrs.i18n + '.' + attrs.var.toString().toLowerCase())
        sb << "</span>"
        out << body() << sb
    }

    def libredematEnumToText = { attrs, body ->
        def libredematEnum = LibredematUtils.adaptLibredematEnum(attrs.var, attrs.i18nKeyPrefix)  
	      out << body() << g.message(code: libredematEnum.i18nKey)
    }
	  
    def libredematEnumToField = { attrs, body ->
        
        def sb = new StringBuffer()

        // TODO : finished it in a more industrialized way
        if (attrs.var == null) {
            sb << "<span class=\""
            sb << "null"
            sb << " "
            sb << attrs.i18nKeyPrefix
            sb << "\">"
            sb << "&nbsp;"
            sb << "</span>"
        } else {
            def libredematEnum = LibredematUtils.adaptLibredematEnum(attrs.var, attrs.i18nKeyPrefix)
            sb << "<span class=\"" 
            sb << libredematEnum.enumString
            sb << " "
            sb << attrs.i18nKeyPrefix
            sb << "\">"
            sb << g.message(code: libredematEnum.i18nKey)
            sb << "</span>"
        }

	    out << body() << sb
    }
}
