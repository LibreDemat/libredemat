package org.libredemat.business.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

import org.libredemat.service.users.HasLegalResponsibles;
import org.libredemat.xml.common.ChildType;

import net.sf.oval.constraint.NotNull;

@HasLegalResponsibles
@Entity
@Table(name="child")
public class Child extends Individual {

    private static final long serialVersionUID = 1L;

    public static Child xmlToModel(ChildType childType) {
        if (childType == null) return null;
        Child child = new Child();
        child.fillCommonModelInfo(childType);
        child.setBorn(childType.getBorn());
        if (childType.getSex() != null)
            child.setSex(SexType.forString(childType.getSex().toString()));
        child.setExternalLibreDematId(childType.getExternalLibredematId());
        return child;
    }

    @Column(name="born")
    private boolean born = true;

    @NotNull(message = "sex", when = "groovy:_this.born")
    @Enumerated(EnumType.STRING)
    private SexType sex;

    /** Child information sheet */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="child_information_sheet_id")
    private ChildInformationSheet childInformationSheet;

    public ChildType modelToXml() {
        ChildType childType = ChildType.Factory.newInstance();
        fillCommonXmlInfo(childType);
        childType.setBorn(born);
        if (sex != null)
            childType.setSex(org.libredemat.xml.common.SexType.Enum.forString(sex.getLegacyLabel()));
        if (this.childInformationSheet != null) {
            childType.setChildInformationSheet(ChildInformationSheet.modelToXml(this.childInformationSheet));
        }
        return childType;
    }

    public boolean isBorn() {
        return born;
    }

    public void setBorn(boolean born) {
        this.born = born;
    }

    public SexType getSex() {
        return this.sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public ChildInformationSheet getChildInformationSheet() {
        return childInformationSheet;
    }

    public void setChildInformationSheet(ChildInformationSheet childInformationSheet) {
        this.childInformationSheet = childInformationSheet;
    }

}
