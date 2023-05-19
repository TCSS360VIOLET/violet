package controller;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import model.Project;
import model.Item;

public class FileManager{
    /**
     * Load projects from file
     */
    public static List<Project> loadProjects(File file){
        
        try {
            // Define the file path

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Add this line to ignore whitespace in the XML content
            dbFactory.setIgnoringElementContentWhitespace(true);
            // Create a DocumentBuilder object
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 

            Document doc;
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

            Element rootElement = doc.getDocumentElement();
            removeWhitespaceNodes(rootElement);

            List<Project> projects = readProjectsFromDocument(rootElement);
            
            return projects;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    } 

    /**
     * Read projects from root element
     */
    public static List<Project> readProjectsFromDocument(Element rootElement) throws ParseException{
        NodeList projectNodes = rootElement.getElementsByTagName("Project");
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < projectNodes.getLength(); i++) {
            Element projectElement = (Element) projectNodes.item(i);

            // Extract the project details
            String name = projectElement.getElementsByTagName("Name").item(0).getTextContent();
            String startDateStr = projectElement.getElementsByTagName("StartDate").item(0).getTextContent();
            String endDateStr = projectElement.getElementsByTagName("EndDate").item(0).getTextContent();
            String budgetStr = projectElement.getElementsByTagName("Budget").item(0).getTextContent();

            // Parse the date and budget values
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//                Date startDate = dateFormat.parse(startDateStr);
//                Date endDate = dateFormat.parse(endDateStr);
            Date startDate = new Date(startDateStr);
            Date endDate = new Date(endDateStr);

            double budget = Double.parseDouble(budgetStr);

            // Create a Project object with the extracted details
            Project project = new Project(startDate, endDate, name, budget);

            // Extract the list of file paths
            List<String> filePaths = new ArrayList<>();
            NodeList filePathNodes = projectElement.getElementsByTagName("FilePath");
            for (int j = 0; j < filePathNodes.getLength(); j++) {
                Element filePathElement = (Element) filePathNodes.item(j);
                String filePath = filePathElement.getTextContent();
                filePaths.add(filePath);
            }
            project.setFilePaths(filePaths);

            // Extract the list of items
            List<Item> items = new ArrayList<>();
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int j = 0; j < itemNodes.getLength(); j++) {
                Element itemElement = (Element) itemNodes.item(j);
                String itemName = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                String description = itemElement.getElementsByTagName("Description").item(0).getTextContent();
                String costPerUnitStr = itemElement.getElementsByTagName("CostPerUnit").item(0).getTextContent();
                String quantityStr = itemElement.getElementsByTagName("Quantity").item(0).getTextContent();

                double costPerUnit = Double.parseDouble(costPerUnitStr);
                int quantity = Integer.parseInt(quantityStr);

                Item item = new Item(itemName, quantity, budget, costPerUnit);
                items.add(item);
            }
            project.setItems(items);
            projects.add(project);
        }
        return projects;
    }


    public static void removeWhitespaceNodes(Node node) {
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

    /*
     * The action to export data.
     */
    public static void exportData(Component parentComponent) {
        try {
            File dataFolder = new File("data");
            File[] xmlFiles = dataFolder.listFiles((dir, name) -> name.endsWith(".xml"));

            if (xmlFiles.length == 0) {
                JOptionPane.showMessageDialog(parentComponent, "No XML files found in the data folder.", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Destination for Exported ZIP File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("export.zip"));

            int result = fileChooser.showSaveDialog(parentComponent);

            if (result == JFileChooser.APPROVE_OPTION) {
                File exportFile = fileChooser.getSelectedFile();

                FileOutputStream fos = new FileOutputStream(exportFile);
                ZipOutputStream zos = new ZipOutputStream(fos);

                byte[] buffer = new byte[1024];

                for (File xmlFile : xmlFiles) {
                    ZipEntry zipEntry = new ZipEntry(xmlFile.getName());
                    zos.putNextEntry(zipEntry);

                    FileInputStream fis = new FileInputStream(xmlFile);
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    fis.close();

                    zos.closeEntry();
                }

                zos.close();
                fos.close();

                JOptionPane.showMessageDialog(parentComponent, "Data exported successfully.", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentComponent, "An error occurred during export: " + e.getMessage(), "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    /*
     * The action to import data.
     */
    public static void importData(Component parentComponent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select Zip File to Import");
        int result = fileChooser.showOpenDialog(parentComponent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            try {
                // Create a folder to extract the contents of the zip file
                File extractionFolder = new File("data");
                if (!extractionFolder.exists()) {
                    extractionFolder.mkdir();
                }

                // Extract the zip file contents
                FileInputStream fis = new FileInputStream(importFile);
                ZipInputStream zis = new ZipInputStream(fis);
                ZipEntry zipEntry = zis.getNextEntry();

                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File extractedFile = new File(extractionFolder, fileName);

                    FileOutputStream fos = new FileOutputStream(extractedFile);
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }

                    fos.close();
                    zipEntry = zis.getNextEntry();
                }

                zis.close();
                fis.close();

                JOptionPane.showMessageDialog(parentComponent, "Data imported successfully.", "Import",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parentComponent, "An error occurred during import: " + e.getMessage(),
                        "Import Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Date removeTime(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }
}