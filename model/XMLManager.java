package model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * This class will be used by our group to easily create, read, and edit XML documents for data import/export.
 * 
 * @author Nickolas Zahos (nzahos@uw.edu)
 */
public class XMLManager {
    DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    /** Default Constructor */
    public XMLManager() {
        dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        doc = dBuilder.newDocument();
    }

    /**
     * This method grabs data from an existing XML file and returns the result as a string.
     * 
     * @param fileName
     * @param dataGroupName
     * @param dataFieldName
     * @return
     */
    public String getData(String fileName, String dataGroupName, String dataFieldName) {
        String result = "N/A";
        
        try {
            Document docRead = dBuilder.parse("data/" + fileName);
            docRead.getDocumentElement().normalize();

            // Create XPath for querying XML data
            XPath xPath = XPathFactory.newInstance().newXPath();

            try {
                // Read properties using XPath
                result = (String) xPath.compile("/root/" + dataGroupName + "/@" + dataFieldName).evaluate(docRead, XPathConstants.STRING);
            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if(result.equals("N/A")) {
            throw new NullPointerException("ERROR: Data was not found at the specified field!");
        }

        return result;
    }
}