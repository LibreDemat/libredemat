package fr.cg95.cvq.generator;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ApplicationDocumentation {

    private String nodeName;
    private String xmlString;
    private Node xmlNode;

    public ApplicationDocumentation(String nodeName,
                                    String xmlString,
                                    Node xmlNode) {
        this.nodeName = nodeName;
        this.xmlString = xmlString;
        this.xmlNode = xmlNode;
    }

    public ApplicationDocumentation() {};

    public boolean hasChildNode(String childNodeName) {
        NodeList nodeList = xmlNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeName().equals(childNodeName))
                return true;
        }

        return false;
    }

    /**
     * Get a child node by its name
     *
     * @return the children nodes with the given name if found, null if not found
     */
    public Node[] getChildrenNodes(String childNodeName) {
        ArrayList childrenList = new ArrayList();
        NodeList nodeList = xmlNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeName().equals(childNodeName))
                childrenList.add(childNode);
        }

        if (!childrenList.isEmpty())
            return (Node[]) childrenList.toArray(new Node[0]);
        else
            return null;
    }

    public static Node[] getChildrenNodes(Node node, String childNodeName) {
        ArrayList childrenList = new ArrayList();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeName().equals(childNodeName))
                childrenList.add(childNode);
        }

        if (!childrenList.isEmpty())
            return (Node[]) childrenList.toArray(new Node[0]);
        else
            return null;
    }

    public static String getNodeAttributeValue(Node node, String attribute) {
        NamedNodeMap attributesMap = node.getAttributes();
        if (attributesMap != null && attributesMap.getNamedItem(attribute) != null)
            return attributesMap.getNamedItem(attribute).getNodeValue();
        else
            return null;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }

    public String getXmlString() {
        return this.xmlString;
    }

    public void setXmlNode(Node xmlNode) {
        this.xmlNode = xmlNode;
    }

    public Node getXmlNode() {
        return this.xmlNode;
    }
}
