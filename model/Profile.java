package model;

import controller.ProfileManager;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class will be the Profile object that each user's profile information will be stored as.
 * 
 * @author Parker Johnson
 * @author Nickolas Zahos (nzahos@uw.edu)
 */
public class Profile {
    /** The name of the profile */
    private String myName;

    /** The email address of the profile. */
    private String myEmail;
    /**
     * The document for the user
     */
    private Document myDoc;
    /**
     * The manager for the profile.
     */
    private ProfileManager myManager;

    /** Constructor - Given Name, and Email.
     * 
     * @param theName   The name of the user.
     * @param theEmail  The email of the user.
     */
    public Profile(String theName, String theEmail) throws ParserConfigurationException {
        setProfile(theName, theEmail);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        myDoc = docBuilder.newDocument();
        myManager = new ProfileManager();
    }

    /**
     * This set method sets the profile name and email.
     * 
     * @param theName   The name of the user to set the profile to.
     * @param theEmail  The email of the user to set the profile to.
     */
    public void setProfile(String theName, String theEmail) {
        myName = theName;
        myEmail = theEmail;
    }

    /** 
     * This get method returns a reference the profile's name.
     * 
     * @return  The profile's name.
     */
    public String getName() {
        return myName;
    }

    /**
     * This get method returns a reference to the profile's email.
     * 
     * @return  The profile's emial.
     */
    public String getEmail() {
        return myEmail;
    }





}