package fr.cg95.cvq.business.request;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum RequestVariable {

    DATE,
    LAST_AGENT_NAME,
    LAST_AGENT_EMAIL,
    MOC,
    RQ_ID,
    RQ_CAT,
    RQ_CAT_EMAIL,
    RQ_TP_LABEL,
    RQ_CDATE,
    RQ_DVAL,
    RQ_OBSERV,
    RR_FNAME,
    RR_LNAME,
    RR_TITLE,
    RR_LOGIN,
    RR_QUESTION,
    RR_ANSWER,
    SU_FNAME,
    SU_LNAME,
    SU_TITLE,
    HF_ID,
    HF_ADDRESS_ADI,
    HF_ADDRESS_AGI,
    HF_ADDRESS_SNAME,
    HF_ADDRESS_SNUM,
    HF_ADDRESS_PNS,
    HF_ADDRESS_ZIP,
    HF_ADDRESS_TOWN,
    HF_ADDRESS_CN;

    public enum Group {
        AGENT, REQUEST, REQUESTER, SUBJECT, HOMEFOLDER, OTHER;
    }

    public static Map<Group, Set<RequestVariable>> grouped() {
        Map<Group, Set<RequestVariable>> map = new EnumMap<Group, Set<RequestVariable>>(Group.class);
        map.put(Group.AGENT, EnumSet.range(LAST_AGENT_NAME, LAST_AGENT_EMAIL));
        map.put(Group.REQUEST, EnumSet.range(RQ_ID, RQ_OBSERV));
        map.put(Group.REQUESTER, EnumSet.range(RR_FNAME, RR_ANSWER));
        map.put(Group.SUBJECT, EnumSet.range(SU_FNAME, SU_TITLE));
        map.put(Group.HOMEFOLDER, EnumSet.range(HF_ID, HF_ADDRESS_CN));
        map.put(Group.OTHER, EnumSet.of(DATE, MOC));
        return map;
    }
}
