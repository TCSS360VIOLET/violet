package controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class XMLTest {
    public static void main(String[] args) {
        try {
            // Create a new XML document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create root element
            Element rootElement = doc.createElement("Profile");
            doc.appendChild(rootElement);

            // Create username element
            Element usernameElement = doc.createElement("Username");
            usernameElement.setTextContent("JohnDoe");
            rootElement.appendChild(usernameElement);

            // Create email element
            Element emailElement = doc.createElement("Email");
            emailElement.setTextContent("johndoe@example.com");
            rootElement.appendChild(emailElement);

            // Create projects element
            Element projectsElement = doc.createElement("Projects");
            rootElement.appendChild(projectsElement);

            // Create project 1 element
            Element project1Element = doc.createElement("Project");
            projectsElement.appendChild(project1Element);

            // Add project 1 details
            Element projectName1Element = doc.createElement("ProjectName");
            projectName1Element.setTextContent("Project 1");
            project1Element.appendChild(projectName1Element);

            Element startDate1Element = doc.createElement("StartDate");
            startDate1Element.setTextContent("2023-01-01");
            project1Element.appendChild(startDate1Element);

            Element endDate1Element = doc.createElement("EndDate");
            endDate1Element.setTextContent("2023-12-31");
            project1Element.appendChild(endDate1Element);

            Element budget1Element = doc.createElement("Budget");
            budget1Element.setTextContent("10000");
            project1Element.appendChild(budget1Element);

            // Create project 2 element
            Element project2Element = doc.createElement("Project");
            projectsElement.appendChild(project2Element);

            // Add project 2 details
            Element projectName2Element = doc.createElement("ProjectName");
            projectName2Element.setTextContent("Project 2");
            project2Element.appendChild(projectName2Element);

            Element startDate2Element = doc.createElement("StartDate");
            startDate2Element.setTextContent("2024-01-01");
            project2Element.appendChild(startDate2Element);

            Element endDate2Element = doc.createElement("EndDate");
            endDate2Element.setTextContent("2024-12-31");
            project2Element.appendChild(endDate2Element);

            Element budget2Element = doc.createElement("Budget");
            budget2Element.setTextContent("15000");
            project2Element.appendChild(budget2Element);

            // Configure transformer for pretty-printing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Write the content into XML file
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data/Profile.xml"));
            transformer.transform(source, result);

            System.out.println("XML file generated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
