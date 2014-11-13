/*
 * An XML document type.
 * Localname: MarriageEchoRequest
 * Namespace: http://www.digitech.fr/cityws/marriage
 * Java type: fr.digitech.cityws.marriage.MarriageEchoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.marriage;


/**
 * A document containing one MarriageEchoRequest(@http://www.digitech.fr/cityws/marriage) element.
 *
 * This is a complex type.
 */
public interface MarriageEchoRequestDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MarriageEchoRequestDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("marriageechorequesta950doctype");
    
    /**
     * Gets the "MarriageEchoRequest" element
     */
    fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest getMarriageEchoRequest();
    
    /**
     * Sets the "MarriageEchoRequest" element
     */
    void setMarriageEchoRequest(fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest marriageEchoRequest);
    
    /**
     * Appends and returns a new empty "MarriageEchoRequest" element
     */
    fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest addNewMarriageEchoRequest();
    
    /**
     * An XML MarriageEchoRequest(@http://www.digitech.fr/cityws/marriage).
     *
     * This is a complex type.
     */
    public interface MarriageEchoRequest extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MarriageEchoRequest.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("marriageechorequest6304elemtype");
        
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
            public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest newInstance() {
              return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument.MarriageEchoRequest) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument newInstance() {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.marriage.MarriageEchoRequestDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.marriage.MarriageEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
