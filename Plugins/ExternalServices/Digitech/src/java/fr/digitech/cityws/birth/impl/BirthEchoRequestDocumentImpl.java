/*
 * An XML document type.
 * Localname: BirthEchoRequest
 * Namespace: http://www.digitech.fr/cityws/birth
 * Java type: fr.digitech.cityws.birth.BirthEchoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.birth.impl;
/**
 * A document containing one BirthEchoRequest(@http://www.digitech.fr/cityws/birth) element.
 *
 * This is a complex type.
 */
public class BirthEchoRequestDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.birth.BirthEchoRequestDocument
{
    
    public BirthEchoRequestDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BIRTHECHOREQUEST$0 = 
        new javax.xml.namespace.QName("http://www.digitech.fr/cityws/birth", "BirthEchoRequest");
    
    
    /**
     * Gets the "BirthEchoRequest" element
     */
    public fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest getBirthEchoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest target = null;
            target = (fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest)get_store().find_element_user(BIRTHECHOREQUEST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "BirthEchoRequest" element
     */
    public void setBirthEchoRequest(fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest birthEchoRequest)
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest target = null;
            target = (fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest)get_store().find_element_user(BIRTHECHOREQUEST$0, 0);
            if (target == null)
            {
                target = (fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest)get_store().add_element_user(BIRTHECHOREQUEST$0);
            }
            target.set(birthEchoRequest);
        }
    }
    
    /**
     * Appends and returns a new empty "BirthEchoRequest" element
     */
    public fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest addNewBirthEchoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest target = null;
            target = (fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest)get_store().add_element_user(BIRTHECHOREQUEST$0);
            return target;
        }
    }
    /**
     * An XML BirthEchoRequest(@http://www.digitech.fr/cityws/birth).
     *
     * This is a complex type.
     */
    public static class BirthEchoRequestImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest
    {
        
        public BirthEchoRequestImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName MESSAGE$0 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/birth", "message");
        
        
        /**
         * Gets the "message" element
         */
        public java.lang.String getMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "message" element
         */
        public org.apache.xmlbeans.XmlString xgetMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "message" element
         */
        public void setMessage(java.lang.String message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$0);
                }
                target.setStringValue(message);
            }
        }
        
        /**
         * Sets (as xml) the "message" element
         */
        public void xsetMessage(org.apache.xmlbeans.XmlString message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$0);
                }
                target.set(message);
            }
        }
    }
}
