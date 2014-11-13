/*
 * An XML document type.
 * Localname: BirthEchoRequest
 * Namespace: http://www.digitech.fr/cityws/birth
 * Java type: fr.digitech.cityws.birth.BirthEchoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.birth;


/**
 * A document containing one BirthEchoRequest(@http://www.digitech.fr/cityws/birth) element.
 *
 * This is a complex type.
 */
public interface BirthEchoRequestDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BirthEchoRequestDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("birthechorequesta836doctype");
    
    /**
     * Gets the "BirthEchoRequest" element
     */
    fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest getBirthEchoRequest();
    
    /**
     * Sets the "BirthEchoRequest" element
     */
    void setBirthEchoRequest(fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest birthEchoRequest);
    
    /**
     * Appends and returns a new empty "BirthEchoRequest" element
     */
    fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest addNewBirthEchoRequest();
    
    /**
     * An XML BirthEchoRequest(@http://www.digitech.fr/cityws/birth).
     *
     * This is a complex type.
     */
    public interface BirthEchoRequest extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BirthEchoRequest.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("birthechorequestd92felemtype");
        
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
            public static fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest newInstance() {
              return (fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (fr.digitech.cityws.birth.BirthEchoRequestDocument.BirthEchoRequest) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument newInstance() {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.birth.BirthEchoRequestDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.birth.BirthEchoRequestDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
