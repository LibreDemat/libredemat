/*
 * XML Type:  SeverityType
 * Namespace: http://www.digitech.fr/cityws/messageResponse
 * Java type: fr.digitech.cityws.messageResponse.SeverityType
 *
 * Automatically generated - do not modify.
 */
package fr.digitech.cityws.messageResponse;


/**
 * An XML SeverityType(@http://www.digitech.fr/cityws/messageResponse).
 *
 * This is an atomic type that is a restriction of fr.digitech.cityws.messageResponse.SeverityType.
 */
public interface SeverityType extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SeverityType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3393CF5BDEB92BD6B2214B81B8D5DAE7").resolveHandle("severitytype56fbtype");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum INFO = Enum.forString("INFO");
    static final Enum WARN = Enum.forString("WARN");
    static final Enum ERROR = Enum.forString("ERROR");
    static final Enum FATAL = Enum.forString("FATAL");
    
    static final int INT_INFO = Enum.INT_INFO;
    static final int INT_WARN = Enum.INT_WARN;
    static final int INT_ERROR = Enum.INT_ERROR;
    static final int INT_FATAL = Enum.INT_FATAL;
    
    /**
     * Enumeration value class for fr.digitech.cityws.messageResponse.SeverityType.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_INFO
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_INFO = 1;
        static final int INT_WARN = 2;
        static final int INT_ERROR = 3;
        static final int INT_FATAL = 4;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("INFO", INT_INFO),
                new Enum("WARN", INT_WARN),
                new Enum("ERROR", INT_ERROR),
                new Enum("FATAL", INT_FATAL),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static fr.digitech.cityws.messageResponse.SeverityType newValue(java.lang.Object obj) {
          return (fr.digitech.cityws.messageResponse.SeverityType) type.newValue( obj ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType newInstance() {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static fr.digitech.cityws.messageResponse.SeverityType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.messageResponse.SeverityType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static fr.digitech.cityws.messageResponse.SeverityType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (fr.digitech.cityws.messageResponse.SeverityType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
