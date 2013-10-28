package org.libredemat.service.request.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.libredemat.business.request.Category;
import org.libredemat.business.request.Request;
import org.libredemat.business.users.Adult;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.service.request.ICategoryService;
import org.libredemat.service.request.annotation.RequestFilter;
import org.libredemat.util.Critere;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Set;

/**
 *
 * @author bor@zenexity.fr
 */
@Aspect
public class RequestFilterAspect implements Ordered {

    private ICategoryService categoryService;

    @SuppressWarnings("unchecked")
    @Before("org.libredemat.SystemArchitecture.businessService() && @annotation(requestFilter) && (within(org.libredemat.service.request..*) || within(org.libredemat.external..*))")
    public void contextAnnotatedMethod(JoinPoint joinPoint, RequestFilter requestFilter) {

        Object[] arguments = joinPoint.getArgs();
        Set<Critere> criteriaSet = (Set<Critere>) arguments[0];

        Critere crit = new Critere();
        if (SecurityContext.isBackOfficeContext()) {
            List<Category> agentCategories = null;
            if (requestFilter.privilege().equals(ContextPrivilege.MANAGE)) {
                agentCategories = categoryService.getManaged();
            } else if (requestFilter.privilege().equals(ContextPrivilege.READ)) {
                agentCategories = categoryService.getAssociated();
            }
            StringBuffer sb = new StringBuffer();
            if (agentCategories == null || agentCategories.isEmpty()) {
                sb.append("'-1'");
            } else {
                for (Category category : agentCategories) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append("'").append(category.getId()).append("'");
                }
            }
            crit.setAttribut("belongsToCategory");
            crit.setComparatif(Critere.EQUALS);
            crit.setValue(sb.toString());
            criteriaSet.add(crit);
        } else if (SecurityContext.isFrontOfficeContext()) {
            Adult adult = SecurityContext.getCurrentEcitizen();
            crit.setAttribut(Request.SEARCH_BY_HOME_FOLDER_ID);
            crit.setComparatif(Critere.EQUALS);
            crit.setValue(adult.getHomeFolder().getId());
            criteriaSet.add(crit);
        }
    }

    public int getOrder() {
        return 2;
    }

    public void setCategoryService(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
