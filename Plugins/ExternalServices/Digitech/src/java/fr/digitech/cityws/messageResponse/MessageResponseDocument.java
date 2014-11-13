/*
 * An XML document type.
 * Localname: MessageResponse
 * Namespace: http://www.digitech.fr/cityws/messageResponse
 * Java type: fr.digitech.cityws.messageResponse.MessageResponseDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.messageResponse;


/**
 * A document containing one MessageResponse(@http://www.digitech.fr/cityws/messageResponse) element.
 *
 * This is a complex type.
 */
public interface MessageResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MessageResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("messageresponse7b8adoctype");
    
    /**
     * Gets the "MessageResponse" element
     */
    fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse getMessageResponse();
    
    /**
     * Sets the "MessageResponse" element
     */
    void setMessageResponse(fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse messageResponse);
    
    /**
     * Appends and returns a new empty "MessageResponse" element
     */
    fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse addNewMessageResponse();
    
    /**
     * An XML MessageResponse(@http://www.digitech.fr/cityws/messageResponse).
     *
     * This is a complex type.
     */
    public interface MessageResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MessageResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("messageresponsec93eelemtype");
        
        /**
         * Gets the "code" element
         */
        int getCode();
        
        /**
         * Gets (as xml) the "code" element
         */
        org.apache.xmlbeans.XmlInt xgetCode();
        
        /**
         * Sets the "code" element
         */
        void setCode(int code);
        
        /**
         * Sets (as xml) the "code" element
         */
        void xsetCode(org.apache.xmlbeans.XmlInt code);
        
        /**
         * Gets the "description" element
         */
        java.lang.String getDescription();
        
        /**
         * Gets (as xml) the "description" element
         */
        org.apache.xmlbeans.XmlString xgetDescription();
        
        /**
         * Tests for nil "description" element
         */
        boolean isNilDescription();
        
        /**
         * Sets the "description" element
         */
        void setDescription(java.lang.String description);
        
        /**
         * Sets (as xml) the "description" element
         */
        void xsetDescription(org.apache.xmlbeans.XmlString description);
        
        /**
         * Nils the "description" element
         */
        void setNilDescription();
        
        /**
         * Gets the "date" element
         */
        java.util.Calendar getDate();
        
        /**
         * Gets (as xml) the "date" element
         */
        org.apache.xmlbeans.XmlDateTime xgetDate();
        
        /**
         * Sets the "date" element
         */
        void setDate(java.util.Calendar date);
        
        /**
         * Sets (as xml) the "date" element
         */
        void xsetDate(org.apache.xmlbeans.XmlDateTime date);
        
        /**
         * Gets the "severity" element
         */
        fr.digitech.cityws.messageResponse.SeverityType.Enum getSeverity();
        
        /**
         * Gets (as xml) the "severity" element
         */
        fr.digitech.cityws.messageResponse.SeverityType xgetSeverity();
        
        /**
         * Sets the "severity" element
         */
        void setSeverity(fr.digitech.cityws.messageResponse.SeverityType.Enum severity);
        
        /**
         * Sets (as xml) the "severity" element
         */
        void xsetSeverity(fr.digitech.cityws.messageResponse.SeverityType severity);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse newInstance() {
              return (fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (fr.digitech.cityws.messageResponse.MessageResponseDocument.MessageResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument newInstance() {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.messageResponse.MessageResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.messageResponse.MessageResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
