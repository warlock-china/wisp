package cn.com.warlock.wisp.test.h2.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.helpers.DefaultHandler;

public class ConfigParser {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigParser.class);

    /**
     * parse data
     *
     * @param xmlPath
     *
     * @return
     *
     * @throws Exception
     */
    public static InitDbConfig parse(InputStream xmlPath) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");

        Document document = null;
        DocumentBuilder docBuilder = null;
        docBuilder = factory.newDocumentBuilder();
        DefaultHandler handler = new DefaultHandler();
        docBuilder.setEntityResolver(handler);
        docBuilder.setErrorHandler(handler);

        document = docBuilder.parse(xmlPath);

        List<String> schemaList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();

        Element rootEl = document.getDocumentElement();
        NodeList children = rootEl.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;

                if (elementNameMatch(element, "initialize-database")) {

                    schemaList = parseSchemaList(element);

                } else if (elementNameMatch(element, "initialize-data")) {

                    dataList = parseDataList(element);
                }

            }
        }

        InitDbConfig initDbConfig = new InitDbConfig();
        initDbConfig.setDataFileList(dataList);
        initDbConfig.setSchemaFileList(schemaList);

        return initDbConfig;
    }

    /**
     * @param el
     *
     * @return
     */
    private static List<String> parseSchemaList(Element el) {

        List<String> schemaList = new ArrayList<>();

        NodeList children = el.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (elementNameMatch(element, "schema")) {
                    String schema = element.getTextContent().trim();
                    schemaList.add(schema);
                }
            }
        }

        return schemaList;
    }

    /**
     * @param el
     */
    private static List<String> parseDataList(Element el) {

        List<String> dataList = new ArrayList<>();

        NodeList children = el.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (elementNameMatch(element, "data")) {
                    String schema = element.getTextContent().trim();
                    dataList.add(schema);
                }
            }
        }

        return dataList;
    }

    private static boolean nodeNameMatch(Node node, String desiredName) {
        return (desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName()));
    }

    private static boolean elementNameMatch(Node node, String desiredName) {
        return (node instanceof Element && nodeNameMatch(node, desiredName));
    }

}
