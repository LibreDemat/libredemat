/*
 * An XML document type.
 * Localname: MessageResponse
 * Namespace: http://www.digitech.fr/cityws/messageResponse
 * Java type: fr.digitech.cityws.messageResponse.MessageResponseDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.messageResponse.impl;
/**
 * A document containing one MessageResponse(@http://www.digitech.fr/cityws/messageResponse) element.
 *
 * This is a complex type.
 */
public class MessageResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.messageResponse.MessageResponseDocument
{
    
    public MessageResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MESSAGERESPONSE$0 = 
        new javax.xml.namespace.QName("http://www.digitech.fr/cityws/messageResponse", "MessageResponse");
    
    
    /**
     * Gets the "MessageResponse" element
     */
    public fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse getMessageResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse target = null;
            target = (fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse)get_store().find_element_user(MESSAGERESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "MessageResponse" element
     */
    public void setMessageResponse(fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse messageResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse target = null;
            target = (fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse)get_store().find_element_user(MESSAGERESPONSE$0, 0);
            if (target == null)
            {
                target = (fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse)get_store().add_element_user(MESSAGERESPONSE$0);
            }
            target.set(messageResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "MessageResponse" element
     */
    public fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse addNewMessageResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse target = null;
            target = (fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse)get_store().add_element_user(MESSAGERESPONSE$0);
            return target;
        }
    }
    /**
     * An XML MessageResponse(@http://www.digitech.fr/cityws/messageResponse).
     *
     * This is a complex type.
     */
    public static class MessageResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse
    {
        
        public MessageResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName CODE$0 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/messageResponse", "code");
        private static final javax.xml.namespace.QName DESCRIPTION$2 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/messageResponse", "description");
        private static final javax.xml.namespace.QName DATE$4 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/messageResponse", "date");
        private static final javax.xml.namespace.QName SEVERITY$6 = 
            new javax.xml.namespace.QName("http://www.digitech.fr/cityws/messageResponse", "severity");
        
        
        /**
         * Gets the "code" element
         */
        public int getCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODE$0, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "code" element
         */
        public org.apache.xmlbeans.XmlInt xgetCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CODE$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "code" element
         */
        public void setCode(int code)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODE$0);
                }
                target.setIntValue(code);
            }
        }
        
        /**
         * Sets (as xml) the "code" element
         */
        public void xsetCode(org.apache.xmlbeans.XmlInt code)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CODE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(CODE$0);
                }
                target.set(code);
            }
        }
        
        /**
         * Gets the "description" element
         */
        public java.lang.String getDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "description" element
         */
        public org.apache.xmlbeans.XmlString xgetDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "description" element
         */
        public boolean isNilDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "description" element
         */
        public void setDescription(java.lang.String description)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESCRIPTION$2);
                }
                target.setStringValue(description);
            }
        }
        
        /**
         * Sets (as xml) the "description" element
         */
        public void xsetDescription(org.apache.xmlbeans.XmlString description)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$2);
                }
                target.set(description);
            }
        }
        
        /**
         * Nils the "description" element
         */
        public void setNilDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "date" element
         */
        public java.util.Calendar getDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATE$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getCalendarValue();
            }
        }
        
        /**
         * Gets (as xml) the "date" element
         */
        public org.apache.xmlbeans.XmlDateTime xgetDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlDateTime target = null;
                target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATE$4, 0);
                return target;
            }
        }
        
        /**
         * Sets the "date" element
         */
        public void setDate(java.util.Calendar date)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATE$4);
                }
                target.setCalendarValue(date);
            }
        }
        
        /**
         * Sets (as xml) the "date" element
         */
        public void xsetDate(org.apache.xmlbeans.XmlDateTime date)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlDateTime target = null;
                target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATE$4);
                }
                target.set(date);
            }
        }
        
        /**
         * Gets the "severity" element
         */
        public fr.digitech.cityws.messageResponse.SeverityType.Enum getSeverity()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEVERITY$6, 0);
                if (target == null)
                {
                    return null;
                }
                return (fr.digitech.cityws.messageResponse.SeverityType.Enum)target.getEnumValue();
            }
        }
        
        /**
         * Gets (as xml) the "severity" element
         */
        public fr.digitech.cityws.messageResponse.SeverityType xgetSeverity()
        {
            synchronized (monitor())
            {
                check_orphaned();
                fr.digitech.cityws.messageResponse.SeverityType target = null;
                target = (fr.digitech.cityws.messageResponse.SeverityType)get_store().find_element_user(SEVERITY$6, 0);
                return target;
            }
        }
        
        /**
         * Sets the "severity" element
         */
        public void setSeverity(fr.digitech.cityws.messageResponse.SeverityType.Enum severity)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEVERITY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SEVERITY$6);
                }
                target.setEnumValue(severity);
            }
        }
        
        /**
         * Sets (as xml) the "severity" element
         */
        public void xsetSeverity(fr.digitech.cityws.messageResponse.SeverityType severity)
        {
            synchronized (monitor())
            {
                check_orphaned();
                fr.digitech.cityws.messageResponse.SeverityType target = null;
                target = (fr.digitech.cityws.messageResponse.SeverityType)get_store().find_element_user(SEVERITY$6, 0);
                if (target == null)
                {
                    target = (fr.digitech.cityws.messageResponse.SeverityType)get_store().add_element_user(SEVERITY$6);
                }
                target.set(severity);
            }
        }
    }
}
