package fr.cg95.cvq.business.users;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import fr.cg95.cvq.xml.common.AdultType;

/**
 * @hibernate.joined-subclass
 *  table="adult"
 *  lazy="false"
 * @hibernate.joined-subclass-key
 *  column="id"
 *
 * @author bor@zenexity.fr
 */
public class Adult extends Individual implements fr.cg95.cvq.business.Historizable,Serializable {

	private static final long serialVersionUID = 1L;

	private static final int HOME_FOLDER_RESPONSIBLE = 1;
    private static final int HOME_FOLDER_FINANCIAL_RESPONSIBLE = 2;

    private int homeFolderRoles;

    private TitleType title;
    private String maidenName;
    private String nameOfUse;
    private FamilyStatusType familyStatus;
    private String homePhone;
    private String mobilePhone;
    private String officePhone;
    private String email;
    private String cfbn;
    private String profession;
    private String question;
    private String answer;
    private String password;

    /** default constructor */
    public Adult() {
        super();
        
        this.familyStatus = FamilyStatusType.getDefaultFamilyStatusType();
        this.title = TitleType.getDefaultTitleType();
    }

    public static AdultType modelToXml(Adult adult) {

        AdultType adultType = AdultType.Factory.newInstance();
        adult.fillCommonXmlInfo(adultType);

        if (adult.getTitle() != null)
            adultType.setTitle(fr.cg95.cvq.xml.common.TitleType.Enum.forString(adult.getTitle().toString()));
        if (adult.getMaidenName() != null)
            adultType.setMaidenName(adult.getMaidenName());
        adultType.setNameOfUse(adult.getNameOfUse());
        if (adult.getFamilyStatus() != null)
            adultType.setFamilyStatus(fr.cg95.cvq.xml.common.FamilyStatusType.Enum.forString(adult.getFamilyStatus().toString()));
        if (adult.getHomePhone() != null)
            adultType.setHomePhone(adult.getHomePhone());
        if (adult.getMobilePhone() != null)
            adultType.setMobilePhone(adult.getMobilePhone());
        if (adult.getOfficePhone() != null)
            adultType.setOfficePhone(adult.getOfficePhone());
        if (adult.getEmail() != null)
            adultType.setEmail(adult.getEmail());
        if (adult.getCfbn() != null)
            adultType.setCfbn(adult.getCfbn());
        if (adult.getProfession() != null)
            adultType.setProfession(adult.getProfession());

        // FIXME : do we include such information when we export user information ??
        if (adult.getPassword() != null)
            adultType.setPassword(adult.getPassword());
        if (adult.getQuestion() != null)
            adultType.setQuestion(adult.getQuestion());
        if (adult.getAnswer() != null)
            adultType.setAnswer(adult.getAnswer());

        if (adult.isHomeFolderResponsible())
            adultType.setIsHomeFolderResponsible(true);
        else
            adultType.setIsHomeFolderResponsible(false);
            
        return adultType;
    }

    public static Adult xmlToModel(fr.cg95.cvq.xml.common.AdultType adultType) {

        if (adultType != null) {
            Adult adult = new Adult();
            adult.fillCommonModelInfo(adultType);

            adult.setPassword(adultType.getPassword());
            if (adultType.getTitle() != null)
                adult.setTitle(TitleType.forString(adultType.getTitle().toString()));
            if (adultType.getMaidenName() != null)
                adult.setMaidenName(adultType.getMaidenName());
            adult.setNameOfUse(adultType.getNameOfUse());
            if (adultType.getFamilyStatus() != null)
                adult.setFamilyStatus(FamilyStatusType.forString(adultType.getFamilyStatus().toString()));
            adult.setHomePhone(adultType.getHomePhone());
            adult.setMobilePhone(adultType.getMobilePhone());
            adult.setOfficePhone(adultType.getOfficePhone());
            adult.setEmail(adultType.getEmail());
            adult.setCfbn(adultType.getCfbn());
            adult.setProfession(adultType.getProfession());
            if (adultType.getQuestion() != null)
                adult.setQuestion(adultType.getQuestion());
            if (adultType.getAnswer() != null)
                adult.setAnswer(adultType.getAnswer());
            if (adultType.getIsHomeFolderResponsible())
                adult.addHomeFolderResponsibleRole();
            
            return adult;
        } else {
            return null;
        }
    }

    public void setHomeFolderRoles(Integer homeFolderRoles) {
        if (homeFolderRoles != null)
            this.homeFolderRoles = homeFolderRoles.intValue();
    }

    public void setHomeFolderRoles(int homeFolderRoles) {
        this.homeFolderRoles = homeFolderRoles;
    }

    public void setHomeFolderRoles(String homeFolderRoles) {
        if (homeFolderRoles != null)
            this.homeFolderRoles = Integer.parseInt(homeFolderRoles);
    }

    public Integer getHomeFolderRolesAsInteger() {
        return new Integer(homeFolderRoles);
    }

    /**
     * @hibernate.property
     *  column="home_folder_roles"
     */
    public int getHomeFolderRoles() {
        return homeFolderRoles;
    }

    public boolean isHomeFolderResponsible() {
        return ((homeFolderRoles & HOME_FOLDER_RESPONSIBLE) == HOME_FOLDER_RESPONSIBLE);
    }

    public boolean isHomeFolderFinancialResponsible() {
        return ((homeFolderRoles & HOME_FOLDER_FINANCIAL_RESPONSIBLE) == HOME_FOLDER_FINANCIAL_RESPONSIBLE);
    }

    public void addHomeFolderResponsibleRole() {
        homeFolderRoles = homeFolderRoles | HOME_FOLDER_RESPONSIBLE;
    }

    public void addHomeFolderFinancialResponsibleRole() {
        homeFolderRoles = homeFolderRoles | HOME_FOLDER_FINANCIAL_RESPONSIBLE;
    }

    public void removeHomeFolderResponsibleRole() {
        homeFolderRoles = homeFolderRoles & (~ HOME_FOLDER_RESPONSIBLE);
    }

    public void removeHomeFolderFinancialResponsibleRole() {
        homeFolderRoles = homeFolderRoles & (~ HOME_FOLDER_FINANCIAL_RESPONSIBLE);
    }

    /**
     * @hibernate.property
     *  column="title"
     *  length="16"
     */
    public TitleType getTitle() {
        return this.title;
    }

    public void setTitle(TitleType title) {
        this.title = title;
    }

    public void setTitleType(String title) {
        TitleType[] allTitleTypes = TitleType.allTitleTypes;
        for (int i=0; i < allTitleTypes.length; i++) {
            TitleType titleType = allTitleTypes[i];
            if (titleType.toString().equals(title))
                this.title = titleType;
        }
    }

    /**
     * @hibernate.property
     *  column="maiden_name"
     */
    public String getMaidenName() {
        return this.maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    /**
     * @hibernate.property
     *  column="name_of_use"
     */
    public String getNameOfUse() {
        return this.nameOfUse;
    }

    public void setNameOfUse(String nameOfUse) {
        this.nameOfUse = nameOfUse;
    }

    /**
     * @hibernate.property
     *  column="family_status"
     *  length="32"
     */
    public FamilyStatusType getFamilyStatus() {
        return this.familyStatus;
    }

    public void setFamilyStatus(FamilyStatusType familyStatus) {
        this.familyStatus = familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        FamilyStatusType[] allFamilyStatusTypes = FamilyStatusType.allFamilyStatusTypes;
        for (int i=0; i < allFamilyStatusTypes.length; i++) {
            FamilyStatusType fs = allFamilyStatusTypes[i];
            if (fs.toString().equals(familyStatus))
                this.familyStatus = fs;
        }
    }

    /**
     * @hibernate.property
     *  column="home_phone"
     *  length="32"
     */
    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * @hibernate.property
     *  column="mobile_phone"
     *  length="32"
     */
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @hibernate.property
     *  column="office_phone"
     *  length="32"
     */
    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    /**
     * @hibernate.property
     *  column="email"
     *  length="50"
     */
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @hibernate.property
     *  column="cfbn"
     *  length="8"
     */
    public String getCfbn() {
        return this.cfbn;
    }

    public void setCfbn(String cfbn) {
        this.cfbn = cfbn;
    }

    /**
     * @hibernate.property
     *  column="profession"
     */
    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * @hibernate.property
     *  column="question"
     */
    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @hibernate.property
     *  column="answer"
     */
    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @hibernate.property
     *  column="password"
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
