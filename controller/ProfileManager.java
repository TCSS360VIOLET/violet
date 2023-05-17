package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The ProfileManager class handles creation and storage of user profiles
 * and their associated projects and items.
 * It builds an XML document which is then saved to a file.
 *
 * @author  Nickolas Zahos (nzahos@uw.edu)
 */

public class ProfileManager {

    /**
     * The XML Document that is used to build the user profiles.
     */
    private Document doc;

    /**
     * The root element in the XML Document. All user profiles are children of this root element.
     */
    private Element rootElement;

    /**
     *
     */
    private File file;

    /**
     * Constructor for the ProfileManager class.
     * Initializes a new XML Document and sets the root element to "Profile".
     */
    public ProfileManager() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("Profile");
            doc.appendChild(rootElement);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    /* The code loads an XML file corresponding to a given username. 
    If the file exists, it parses the existing file, 
    and if it doesn't exist, it creates a new document with a root element named "User".
    */
    public void loadUserFile(String username) {
        try {
            // Define the file path
            file = new File("data/" + username + ".xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Add this line to ignore whitespace in the XML content
            dbFactory.setIgnoringElementContentWhitespace(true);
            // Create a DocumentBuilder object
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 

            if (file.exists()) {
                // If the file exists, parse the existing file
                doc = dBuilder.parse(file);
            } else {
                // If the file does not exist, create a new one
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("User");
                doc.appendChild(rootElement);
            }

            // This step is performed to ensure that the XML document follows the standard formatting rules.
            doc.getDocumentElement().normalize();

            rootElement = doc.getDocumentElement();
            removeWhitespaceNodes(rootElement);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void removeWhitespaceNodes(Node node) {
        NodeList nodeList = node.getChildNodes();
        Node childNode;
        for (int x = nodeList.getLength() - 1; x >= 0; x--) {
            childNode = nodeList.item(x);
            if (childNode.getNodeType() == Node.TEXT_NODE) {
                if (childNode.getTextContent().trim().isEmpty()) {
                    node.removeChild(childNode);
                }
            } else if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                removeWhitespaceNodes(childNode);
            }
        }
    }

    /**
     * Adds a new user profile to the XML document.
     *
     * @param username The username of the user.
     * @param email    The email of the user.
     */
    public void addProfile(String username, String email) throws IOException {
        String fileName = "data/" + username + ".xml";
        File file = new File(fileName);
        if(file.exists()) {
            throw new IOException("A profile with this username already exists.");

        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("User");
            doc.appendChild(rootElement);

            Element usernameElement = doc.createElement("Username");
            usernameElement.appendChild(doc.createTextNode(username));
            rootElement.appendChild(usernameElement);

            Element emailElement = doc.createElement("Email");
            emailElement.appendChild(doc.createTextNode(email));
            rootElement.appendChild(emailElement);

            saveProfile(username);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new project to a user's profile in the XML document.
     *
     * @param username The username of the user.
     * @param startDate The start date of the project.
     * @param endDate   The end date of the project.
     * @param budget    The budget of the project.
     */
    public void addProject(String username, String name, String startDate, String endDate, String budget) {
        loadUserFile(username);

        // Check if project already exists
        Node existingProjectNode = getProjectNode(name);
        if (existingProjectNode != null) {
            System.out.println("A project with this name already exists.");
            return;
        }

        Element project = doc.createElement("Project");
        rootElement.appendChild(project);

        Element nameElement = doc.createElement("Name");
        nameElement.appendChild(doc.createTextNode(name));
        project.appendChild(nameElement);

        Element startDateElement = doc.createElement("StartDate");
        startDateElement.appendChild(doc.createTextNode(startDate));
        project.appendChild(startDateElement);

        Element endDateElement = doc.createElement("EndDate");
        endDateElement.appendChild(doc.createTextNode(endDate));
        project.appendChild(endDateElement);

        Element budgetElement = doc.createElement("Budget");
        budgetElement.appendChild(doc.createTextNode(budget));
        project.appendChild(budgetElement);

        saveProfile(username);
    }


    public void addItem(String username, String projectName, String itemName, String description, String costPerUnit, String quantity) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);

        if (projectNode != null) {
            Element item = doc.createElement("Item");
            projectNode.appendChild(item);

            Element itemNameElement = doc.createElement("ItemName");
            itemNameElement.appendChild(doc.createTextNode(itemName));
            item.appendChild(itemNameElement);

            Element descriptionElement = doc.createElement("Description");
            descriptionElement.appendChild(doc.createTextNode(description));
            item.appendChild(descriptionElement);

            Element costPerUnitElement = doc.createElement("CostPerUnit");
            costPerUnitElement.appendChild(doc.createTextNode(costPerUnit));
            item.appendChild(costPerUnitElement);

            Element quantityElement = doc.createElement("Quantity");
            quantityElement.appendChild(doc.createTextNode(quantity));
            item.appendChild(quantityElement);
        }

        saveProfile(username);
    }

    public void addFilePath(String username, String projectName, String filePath) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);

        if (projectNode != null) {
            Element filePathElement = doc.createElement("FilePath");
            filePathElement.appendChild(doc.createTextNode(filePath));
            projectNode.appendChild(filePathElement);
        }

        saveProfile(username);
    }

    private Node getProjectNode(String projectName) {
        Node projectNode = null;
        NodeList projectNodes = rootElement.getElementsByTagName("Project");

        for (int i = 0; i < projectNodes.getLength(); i++) {
            Node node = projectNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element projectElement = (Element) node;
                String projectNameInElement = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                if (projectNameInElement.equals(projectName)) {
                    projectNode = node;
                    break;
                }
            }
        }

        return projectNode;
    }


    /**
     * Saves the XML document to the user's file.
     * @param username The username of the user.
     */
    public void saveProfile(String username) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            File file = new File("data/" + username + ".xml");
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public List<String> getProjects(String username) {
        loadUserFile(username);
        List<String> projectNames = new ArrayList<>();

        NodeList projectNodes = rootElement.getElementsByTagName("Project");
        for (int i = 0; i < projectNodes.getLength(); i++) {
            Node node = projectNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element projectElement = (Element) node;
                String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                projectNames.add(projectName);
            }
        }

        return projectNames;
    }

    public List<String> getProjectItems(String username, String projectName) {
        loadUserFile(username);
        List<String> itemNames = new ArrayList<>();

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemName = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    itemNames.add(itemName);
                }
            }
        }

        return itemNames;
    }

    public List<String> getProjectFilePaths(String username, String projectName) {
        loadUserFile(username);
        List<String> filePaths = new ArrayList<>();

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList filePathNodes = projectElement.getElementsByTagName("FilePath");
            for (int i = 0; i < filePathNodes.getLength(); i++) {
                Node node = filePathNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String filePath = node.getTextContent();
                    filePaths.add(filePath);
                }
            }
        }

        return filePaths;
    }

    public String getProjectStartDate(String username, String projectName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            return projectElement.getElementsByTagName("StartDate").item(0).getTextContent();
        }

        return null;
    }

    public String getProjectEndDate(String username, String projectName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            return projectElement.getElementsByTagName("EndDate").item(0).getTextContent();
        }

        return null;
    }

    public String getProjectBudget(String username, String projectName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            return projectElement.getElementsByTagName("Budget").item(0).getTextContent();
        }

        return null;
    }

    public String getItemDescription(String username, String projectName, String itemName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemNameInElement = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    if (itemNameInElement.equals(itemName)) {
                        return itemElement.getElementsByTagName("Description").item(0).getTextContent();
                    }
                }
            }
        }

        return null;
    }

    public String getItemCostPerUnit(String username, String projectName, String itemName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemNameInElement = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    if (itemNameInElement.equals(itemName)) {
                        return itemElement.getElementsByTagName("CostPerUnit").item(0).getTextContent();
                    }
                }
            }
        }

        return null;
    }

    public String getItemQuantity(String username, String projectName, String itemName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemNameInElement = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    if (itemNameInElement.equals(itemName)) {
                        return itemElement.getElementsByTagName("Quantity").item(0).getTextContent();
                    }
                }
            }
        }

        return null;
    }
}