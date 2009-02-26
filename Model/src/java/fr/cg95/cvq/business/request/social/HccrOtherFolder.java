package fr.cg95.cvq.business.request.social;

import fr.cg95.cvq.business.users.*;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.xml.common.*;
import fr.cg95.cvq.xml.request.social.*;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlObject;

import java.io.Serializable;
import java.util.*;

import java.math.BigInteger;

/**
 * @hibernate.class
 *  table="hccr_other_folder"
 *  lazy="false"
 *
 * Generated class file, do not edit!
 */
public class HccrOtherFolder implements Serializable {

    private static final long serialVersionUID = 1L;



    public HccrOtherFolder() {
        super();
    }


    public final String modelToXmlString() {

        HccrOtherFolderType object = (HccrOtherFolderType) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final XmlObject modelToXml() {

        Calendar calendar = Calendar.getInstance();
        Date date = null;
        HccrOtherFolderType hccrOtherFolder = HccrOtherFolderType.Factory.newInstance();
        hccrOtherFolder.setOtherFolderDepartment(this.otherFolderDepartment);
        hccrOtherFolder.setOtherFolderName(this.otherFolderName);
        hccrOtherFolder.setOtherFolderNumber(this.otherFolderNumber);
        return hccrOtherFolder;
    }

    public static HccrOtherFolder xmlToModel(HccrOtherFolderType hccrOtherFolderDoc) {

        Calendar calendar = Calendar.getInstance();
        List list = new ArrayList();
        HccrOtherFolder hccrOtherFolder = new HccrOtherFolder();
        hccrOtherFolder.setOtherFolderDepartment(hccrOtherFolderDoc.getOtherFolderDepartment());
        hccrOtherFolder.setOtherFolderName(hccrOtherFolderDoc.getOtherFolderName());
        hccrOtherFolder.setOtherFolderNumber(hccrOtherFolderDoc.getOtherFolderNumber());
        return hccrOtherFolder;
    }

    private Long id;


    public final void setId(final Long id) {
        this.id = id;
    }


    /**
     * @hibernate.id
     *  column="id"
     *  generator-class="sequence"
     */
    public final Long getId() {
        return this.id;
    }

    private String otherFolderDepartment;

    public final void setOtherFolderDepartment(final String otherFolderDepartment) {
        this.otherFolderDepartment = otherFolderDepartment;
    }


    /**
     * @hibernate.property
     *  column="other_folder_department"
     *  length="2"
     */
    public final String getOtherFolderDepartment() {
        return this.otherFolderDepartment;
    }

    private String otherFolderName;

    public final void setOtherFolderName(final String otherFolderName) {
        this.otherFolderName = otherFolderName;
    }


    /**
     * @hibernate.property
     *  column="other_folder_name"
     *  length="60"
     */
    public final String getOtherFolderName() {
        return this.otherFolderName;
    }

    private String otherFolderNumber;

    public final void setOtherFolderNumber(final String otherFolderNumber) {
        this.otherFolderNumber = otherFolderNumber;
    }


    /**
     * @hibernate.property
     *  column="other_folder_number"
     *  length="30"
     */
    public final String getOtherFolderNumber() {
        return this.otherFolderNumber;
    }

}
