/*
 * An XML document type.
 * Localname: DeathEchoRequest
 * Namespace: http://www.digitech.fr/cityws/death
 * Java type: fr.digitech.cityws.death.DeathEchoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.death;


/**
 * A document containing one DeathEchoRequest(@http://www.digitech.fr/cityws/death) element.
 *
 * This is a complex type.
 */
public interface DeathEchoRequestDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DeathEchoRequestDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("deathechorequest7b96doctype");
    
    /**
     * Gets the "DeathEchoRequest" element
     */
    fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest getDeathEchoRequest();
    
    /**
     * Sets the "DeathEchoRequest" element
     */
    void setDeathEchoRequest(fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest deathEchoRequest);
    
    /**
     * Appends and returns a new empty "DeathEchoRequest" element
     */
    fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest addNewDeathEchoRequest();
    
    /**
     * An XML DeathEchoRequest(@http://www.digitech.fr/cityws/death).
     *
     * This is a complex type.
     */
    public interface DeathEchoRequest extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DeathEchoRequest.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("deathechorequest0904elemtype");
        
        /**
         * Gets the "message" element
         */
        java.lang.String getMessage();
        
        /**
         * Gets (as xml) the "message" element
         */
        org.apache.xmlbeans.XmlString xgetMessage();
        
        /**
         * Sets the "message" element
         */
        void setMessage(java.lang.String message);
        
        /**
         * Sets (as xml) the "message" element
         */
        void xsetMessage(org.apache.xmlbeans.XmlString message);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest newInstance() {
              return (fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (fr.digitech.cityws.death.DeathEchoRequestDocument.DeathEchoRequest) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static fr.digitech.cityws.death.DeathEchoRequestDocument newInstance() {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.death.DeathEchoRequestDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.death.DeathEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
