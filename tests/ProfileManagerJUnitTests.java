package tests;

import static org.junit.jupiter.api.Assertions.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import controller.ProfileManager;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * This JUnit test class tests all the XML functions of our program.
 * These are the only unit tests that really matter as the rest of the
 * program's functionality is just UI related things.
 * 
 * @author Nickolas Zahos (nzahos@uw.edu)
 */
class ProfileManagerJUnitTests {
    ProfileManager profileManager;

    @BeforeEach
    void setUp() {
        profileManager = new ProfileManager();
    }

    @Test
    void addProfile() {
        String testUsername = "testUser";
        String testEmail = "testEmail@test.com";

        // Given a profile manager and a test user,
        // When we add the profile,
        try {
            profileManager.addProfile(testUsername, testEmail);

            // Then a file for that user should exist and be properly formed.
            File testUserProfile = new File("data/" + testUsername + ".xml");
            assertTrue(testUserProfile.exists(), "User profile file should exist after calling addProfile.");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(testUserProfile);
            
            NodeList usernameNodes = doc.getElementsByTagName("Username");
            NodeList emailNodes = doc.getElementsByTagName("Email");
            
            assertEquals(testUsername, usernameNodes.item(0).getTextContent(), "Username should match added profile.");
            assertEquals(testEmail, emailNodes.item(0).getTextContent(), "Email should match added profile.");
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        } finally {
            // Cleanup after the test by deleting the test user's profile.
            File testUserProfile = new File("data/" + testUsername + ".xml");
            if(testUserProfile.exists()) {
                testUserProfile.delete();
            }
        }
    }

    @Test
    void addProject() {
        String testUsername = "testUser";
        String testProjectName = "testProject";
        String testStartDate = "06/01/2023";
        String testEndDate = "12/31/2023";
        String testBudget = "10000";

        // Given a profile manager and a test user,
        // When we add the project,
        try {
            profileManager.addProfile(testUsername, "testEmail@test.com");
            profileManager.addProject(testUsername, testProjectName, testStartDate, testEndDate, testBudget);

            // Then a file for that user should exist and be properly formed.
            File testUserProfile = new File("data/" + testUsername + ".xml");
            assertTrue(testUserProfile.exists(), "User profile file should exist after calling addProject.");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(testUserProfile);
            
            NodeList projectNodes = doc.getElementsByTagName("Project");
            Node projectNode = projectNodes.item(0);
            
            if (projectNode.getNodeType() == Node.ELEMENT_NODE) {
                Element projectElement = (Element) projectNode;
                
                assertEquals(testProjectName, projectElement.getElementsByTagName("Name").item(0).getTextContent(), "Project name should match added project.");
                assertEquals(testStartDate, projectElement.getElementsByTagName("StartDate").item(0).getTextContent(), "Project start date should match added project.");
                assertEquals(testEndDate, projectElement.getElementsByTagName("EndDate").item(0).getTextContent(), "Project end date should match added project.");
                assertEquals(testBudget, projectElement.getElementsByTagName("Budget").item(0).getTextContent(), "Project budget should match added project.");
            }
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        } finally {
            // Cleanup after the test by deleting the test user's profile.
            File testUserProfile = new File("data/" + testUsername + ".xml");
            if(testUserProfile.exists()) {
                testUserProfile.delete();
            }
        }
    }

    @Test
    void addItem() {
        String username = "TestUser";
        String projectName = "TestProject";
        String itemName = "TestItem";
        String description = "This is a test item.";
        String costPerUnit = "10.00";
        String quantity = "5";

        // Create a user profile and a project for that user
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add an item to the user's project
        profileManager.addItem(username, projectName, itemName, description, costPerUnit, quantity);

        // Load the XML file
        File inputFile = new File("./data/" + username + ".xml"); // Explicitly mention the project directory
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Find the item
            NodeList nList = doc.getElementsByTagName("Item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String foundItemName = eElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    String foundDescription = eElement.getElementsByTagName("Description").item(0).getTextContent();
                    String foundCostPerUnit = eElement.getElementsByTagName("CostPerUnit").item(0).getTextContent();
                    String foundQuantity = eElement.getElementsByTagName("Quantity").item(0).getTextContent();

                    // Check if the item's details match the ones we used to create the item
                    assertEquals(itemName, foundItemName);
                    assertEquals(description, foundDescription);
                    assertEquals(costPerUnit, foundCostPerUnit);
                    assertEquals(quantity, foundQuantity);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void addFilePath() {
        String username = "TestUser";
        String projectName = "TestProject";
        String filePath = "/path/to/file.txt";

        // Create a user profile and a project for that user
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add a file path to the user's project
        profileManager.addFilePath(username, projectName, filePath);

        // Load the XML file
        File inputFile = new File("./data/" + username + ".xml"); // Explicitly mention the project directory
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Find the file path
            NodeList nList = doc.getElementsByTagName("FilePath");
            String foundFilePath = nList.item(0).getTextContent();

            // Check if the file path matches the one we added
            assertEquals(filePath, foundFilePath);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addProjectNotes() {
        String username = "TestUser";
        String projectName = "TestProject";
        String notes = "These are the project notes.";

        // Create a user profile and a project for that user
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add project notes
        profileManager.addProjectNotes(username, projectName, notes);

        // Load the XML file
        File inputFile = new File("./data/" + username + ".xml"); // Explicitly mention the project directory
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Find the project notes
            NodeList nList = doc.getElementsByTagName("Notes");
            String foundNotes = nList.item(0).getTextContent();

            // Check if the project notes match the added notes
            assertEquals(notes, foundNotes);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProjects() {
        String username = "TestUser";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add some projects for the user
        profileManager.addProject(username, "Project1", "01/01/2023", "12/31/2023", "1000.00");
        profileManager.addProject(username, "Project2", "02/01/2023", "01/31/2024", "2000.00");
        profileManager.addProject(username, "Project3", "03/01/2023", "02/28/2024", "3000.00");

        // Get the list of projects
        List<String> projects = profileManager.getProjects(username);

        // Check if the list of projects contains the added projects
        assertTrue(projects.contains("Project1"));
        assertTrue(projects.contains("Project2"));
        assertTrue(projects.contains("Project3"));
    }

    @Test
    void getProjectItems() {
        String username = "TestUser";
        String projectName = "Project1";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add some items to the project
        profileManager.addItem(username, projectName, "Item1", "Description1", "10.00", "5");
        profileManager.addItem(username, projectName, "Item2", "Description2", "20.00", "10");
        profileManager.addItem(username, projectName, "Item3", "Description3", "30.00", "15");

        // Get the list of items for the project
        List<String> items = profileManager.getProjectItems(username, projectName);

        // Check if the list of items contains the added items
        assertTrue(items.contains("Item1"));
        assertTrue(items.contains("Item2"));
        assertTrue(items.contains("Item3"));
    }


    @Test
    void getProjectFilePaths() {
        String username = "TestUser";
        String projectName = "Project1";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add some file paths to the project
        profileManager.addFilePath(username, projectName, "path1");
        profileManager.addFilePath(username, projectName, "path2");
        profileManager.addFilePath(username, projectName, "path3");

        // Get the list of file paths for the project
        List<String> filePaths = profileManager.getProjectFilePaths(username, projectName);

        // Check if the list of file paths contains the added paths
        assertTrue(filePaths.contains("path1"));
        assertTrue(filePaths.contains("path2"));
        assertTrue(filePaths.contains("path3"));
    }

    @Test
    void getProjectStartDate() {
        String username = "TestUser";
        String projectName = "Project1";
        String startDate = "01/01/2023";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, startDate, "12/31/2023", "1000.00");

        // Get the start date of the project
        String retrievedStartDate = profileManager.getProjectStartDate(username, projectName);

        // Check if the retrieved start date matches the expected start date
        assertEquals(startDate, retrievedStartDate);
    }

    @Test
    void getProjectEndDate() {
        String username = "TestUser";
        String projectName = "Project1";
        String endDate = "12/31/2023";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", endDate, "1000.00");

        // Get the end date of the project
        String retrievedEndDate = profileManager.getProjectEndDate(username, projectName);

        // Check if the retrieved end date matches the expected end date
        assertEquals(endDate, retrievedEndDate);
    }


    @Test
    void getProjectBudget() {
        String username = "TestUser";
        String projectName = "Project1";
        float budget = 1000.00f;

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", Float.toString(budget));

        // Get the budget of the project
        float retrievedBudget = profileManager.getProjectBudget(username, projectName);

        // Check if the retrieved budget matches the expected budget
        assertEquals(budget, retrievedBudget);
    }

    @Test
    void getItemDescription() {
        String username = "TestUser";
        String projectName = "Project1";
        String itemName = "Item1";
        String description = "This is item 1";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add an item to the project
        profileManager.addItem(username, projectName, itemName, description, "10.00", "5");

        // Get the description of the item
        String retrievedDescription = profileManager.getItemDescription(username, projectName, itemName);

        // Check if the retrieved description matches the expected description
        assertEquals(description, retrievedDescription);
    }

    @Test
    void getItemCostPerUnit() {
        String username = "TestUser";
        String projectName = "Project1";
        String itemName = "Item1";
        float costPerUnit = 10.00f;

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add an item to the project
        profileManager.addItem(username, projectName, itemName, "Description", Float.toString(costPerUnit), "5");

        // Get the cost per unit of the item
        float retrievedCostPerUnit = profileManager.getItemCostPerUnit(username, projectName, itemName);

        // Check if the retrieved cost per unit matches the expected cost per unit
        assertEquals(costPerUnit, retrievedCostPerUnit);
    }

    @Test
    void getItemQuantity() {
        String username = "TestUser";
        String projectName = "Project1";
        String itemName = "Item1";
        int quantity = 5;

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add an item to the project
        profileManager.addItem(username, projectName, itemName, "Description", "10.00", Integer.toString(quantity));

        // Get the quantity of the item
        int retrievedQuantity = profileManager.getItemQuantity(username, projectName, itemName);

        // Check if the retrieved quantity matches the expected quantity
        assertEquals(quantity, retrievedQuantity);
    }

    @Test
    void getProjectNotes() {
        String username = "TestUser";
        String projectName = "Project1";
        String notes = "These are the project notes";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add project notes
        profileManager.addProjectNotes(username, projectName, notes);

        // Get the project notes
        String retrievedNotes = profileManager.getProjectNotes(username, projectName);

        // Check if the retrieved notes match the expected notes
        assertEquals(notes, retrievedNotes);
    }

    @Test
    void deleteProject() {
        String username = "TestUser";
        String projectName = "Project1";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Delete the project
        profileManager.deleteProject(username, projectName);

        // Try to retrieve the project again
        assertThrows(NullPointerException.class, () -> profileManager.getProjectStartDate(username, projectName));
    }

    @Test
    void deleteItem() {
        String username = "TestUser";
        String projectName = "Project1";
        String itemName = "Item1";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add an item to the project
        profileManager.addItem(username, projectName, itemName, "Description", "10.00", "5");

        // Delete the item
        profileManager.deleteItem(username, projectName, itemName);

        // Try to retrieve the item again
        assertThrows(NullPointerException.class, () -> profileManager.getItemDescription(username, projectName, itemName));
    }

    @Test
    void deleteFilePath() {
        String username = "TestUser";
        String projectName = "Project1";
        String filePath = "data/file1.txt";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Add a file path to the project
        profileManager.addFilePath(username, projectName, filePath);

        // Delete the file path
        profileManager.deleteFilePath(username, projectName, filePath);

        // Get the file paths of the project
        List<String> filePaths = profileManager.getProjectFilePaths(username, projectName);

        // Check if the file path has been deleted
        assertFalse(filePaths.contains(filePath));
    }

    @Test
    void setProjectName() {
        String username = "TestUser";
        String projectName = "Project1";
        String newName = "NewProject";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Set the project name
        profileManager.setProjectName(username, projectName, newName);

        // Get the project names
        List<String> projectNames = profileManager.getProjects(username);

        // Check if the project name has been changed
        assertTrue(projectNames.contains(newName));
        assertFalse(projectNames.contains(projectName));
    }

    @Test
    void setProjectStartDate() {
        String username = "TestUser";
        String projectName = "Project1";
        String newStartDate = "02/01/2023";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Set the project start date
        profileManager.setProjectStartDate(username, projectName, newStartDate);

        // Get the project start date
        String startDate = profileManager.getProjectStartDate(username, projectName);

        // Check if the start date has been changed
        assertEquals(newStartDate, startDate);
    }

    @Test
    void setProjectEndDate() {
        String username = "TestUser";
        String projectName = "Project1";
        String newEndDate = "12/31/2024";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Set the project end date
        profileManager.setProjectEndDate(username, projectName, newEndDate);

        // Get the project end date
        String endDate = profileManager.getProjectEndDate(username, projectName);

        // Check if the end date has been changed
        assertEquals(newEndDate, endDate);
    }

    @Test
    void setProjectBudget() {
        String username = "TestUser";
        String projectName = "Project1";
        String newBudget = "2000.00";

        // Create a user profile
        try {
			profileManager.addProfile(username, "testuser@test.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add a project for the user
        profileManager.addProject(username, projectName, "01/01/2023", "12/31/2023", "1000.00");

        // Set the project budget
        profileManager.setProjectBudget(username, projectName, newBudget);

        // Get the project budget
        float budget = profileManager.getProjectBudget(username, projectName);

        // Check if the budget has been changed
        assertEquals(Float.parseFloat(newBudget), budget, 0.001);
    }

}