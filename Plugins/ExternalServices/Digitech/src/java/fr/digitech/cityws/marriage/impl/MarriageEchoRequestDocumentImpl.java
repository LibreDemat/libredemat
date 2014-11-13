/*
 * An XML document type.
 * Localname: MarriageEchoRequest
 * Namespace: http://www.digitech.fr/cityws/marriage
 * Java type: fr.digitech.cityws.marriage.MarriageEchoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.marriage.impl;
/**
 * A document containing one MarriageEchoRequest(@http://www.digitech.fr/cityws/marriage) element.
 *
 * This is a complex type.
 */
public class MarriageEchoRequestDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.marriage.MarriageEchoRequestDocument
{
    
    public MarriageEchoRequestDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MARRIAGEECHOREQUEST$0 = 
        new javax.xml.namespace.QName("http://www.digitech.fr/cityws/marriage", "MarriageEchoRequest");
    
    
    /**
     * Gets the "MarriageEchoRequest" element
     */
    public fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest getMarriageEchoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest target = null;
            target = (fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest)get_store().find_element_user(MARRIAGEECHOREQUEST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "MarriageEchoRequest" element
     */
    public void setMarriageEchoRequest(fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest marriageEchoRequest)
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest target = null;
            target = (fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest)get_store().find_element_user(MARRIAGEECHOREQUEST$0, 0);
            if (target == null)
            {
                target = (fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest)get_store().add_element_user(MARRIAGEECHOREQUEST$0);
            }
            target.set(marriageEchoRequest);
        }
    }
    
    /**
     * Appends and returns a new empty "MarriageEchoRequest" element
     */
    public fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest addNewMarriageEchoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest target = null;
            target = (fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest)get_store().add_element_user(MARRIAGEECHOREQUEST$0);
            return target;
        }
    }
    /**
     * An XML MarriageEchoRequest(@http://www.digitech.fr/cityws/marriage).
     *
     * This is a complex type.
     */
    public static class MarriageEchoRequestImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest
    {
        
        public MarriageEchoRequestImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName MESSAGE$0 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/marriage", "message");
        
        
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
