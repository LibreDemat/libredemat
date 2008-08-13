package fr.cg95.cvq.fo.taglib;

import javax.servlet.jsp.JspWriter;

import fr.cg95.cvq.fo.business.BusinessManager;
import fr.cg95.cvq.service.authority.LocalAuthorityConfigurationBean;

public class HtmlTitleTag extends BaseTag {

    public int doEndTag() {
        try {
            JspWriter out = pageContext.getOut();

            LocalAuthorityConfigurationBean siteData = BusinessManager.getCurrentSiteData();
            out.println("  <title>CapDémat: " + siteData.getDisplayTitle() + "</title>");

        } catch (Exception ignored) {
        }
        return EVAL_PAGE;
    }

}
