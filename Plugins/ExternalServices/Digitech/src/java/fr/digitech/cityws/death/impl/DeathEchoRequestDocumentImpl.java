/*
 * An XML document type.
 * Localname: DeathEchoRequest
 * Namespace: http://www.digitech.fr/cityws/death
 * Java type: fr.digitech.cityws.death.DeathEchoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.death.impl;
/**
 * A document containing one DeathEchoRequest(@http://www.digitech.fr/cityws/death) element.
 *
 * This is a complex type.
 */
public class DeathEchoRequestDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.death.DeathEchoRequestDocument
{
    
    public DeathEchoRequestDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DEATHECHOREQUEST$0 = 
        new javax.xml.namespace.QName("http://www.digitech.fr/cityws/death", "DeathEchoRequest");
    
    
    /**
     * Gets the "DeathEchoRequest" element
     */
    public fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest getDeathEchoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest target = null;
            target = (fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest)get_store().find_element_user(DEATHECHOREQUEST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DeathEchoRequest" element
     */
    public void setDeathEchoRequest(fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest deathEchoRequest)
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest target = null;
            target = (fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest)get_store().find_element_user(DEATHECHOREQUEST$0, 0);
            if (target == null)
            {
                target = (fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest)get_store().add_element_user(DEATHECHOREQUEST$0);
            }
            target.set(deathEchoRequest);
        }
    }
    
    /**
     * Appends and returns a new empty "DeathEchoRequest" element
     */
    public fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest addNewDeathEchoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest target = null;
            target = (fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest)get_store().add_element_user(DEATHECHOREQUEST$0);
            return target;
        }
    }
    /**
     * An XML DeathEchoRequest(@http://www.digitech.fr/cityws/death).
     *
     * This is a complex type.
     */
    public static class DeathEchoRequestImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest
    {
        
        public DeathEchoRequestImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName MESSAGE$0 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/death", "message");
        
        
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
