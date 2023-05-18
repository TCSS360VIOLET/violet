package model;

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

    /** Constructor - Given Name, and Email.
     * @author Parker Johnson
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param theName   The name of the user.
     * @param theEmail  The email of the user.
     */
    public Profile(String theName, String theEmail) {
        setProfile(theName, theEmail);
    }

    /**
     * This set method sets the profile name and email.
     * @author Parker Johnson
     * @author Nickolas Zahos (nzahos@uw.edu)
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
     * @author Parker Johnson
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @return  The profile's name.
     */
    public String getName() {
        return myName;
    }

    /**
     * This get method returns a reference to the profile's email.
     * @author Parker Johnson
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @return  The profile's emial.
     */
    public String getEmail() {
        return myEmail;
    }
}
