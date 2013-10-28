package org.libredemat.business.request.ecitizen;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.libredemat.business.request.RequestData;
import org.libredemat.service.request.condition.IConditionChecker;


/**
 * @author bor@zenexity.fr
 */
@Deprecated
public class VoCardRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public final void setId(final Long id) {
        this.id = id;
    }

    public final Long getId() {
        return this.id;
    }
}
