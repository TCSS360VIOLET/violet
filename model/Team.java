package model;

import java.util.HashMap;
import java.util.Map;

/* NOTE TO OTHER DEVELOPERS:
 * Be sure to add your emails into the developers HashMap shown below in the constructor!
 * 
 * To get each team member's info from this class,
 * just call a for-each style loop like so on the HashMap...
 * 
 * for(Map.Entry<Integer, String> entry : Team.getDevelopers().entrySet()) {
 * 		String member = entry.getValue(); //This will give the string "FIRST NAME LAST NAME (EMAIL@uw.edu)" for that member.
 * 		//Do stuff with this member's info here
 * }
 */

/**
 * This class will hold information on the team members/developers on Team violet.
 * 
 * @author Nickolas Zahos (nzahos@uw.edu)
 */
public class Team {
	/** This map will hold the developer(s) info as "FIRST NAME LAST NAME, EMAIL" */
	private Map<Integer, String> developers;
	
	/** Default Constructor - Takes no parameters, but has all team info stored. */
	public Team() {
		developers = new HashMap<Integer, String>();
		
		//ADD YOUR PERSONAL INFO HERE!
		developers.put(1, "Nickolas Zahos (nzahos@uw.edu");
		developers.put(2, "An Ho (aho1999@uw.edu");
		developers.put(3, "Edward Chung (edward92@uw.edu");
		developers.put(4, "Lixin Wang (EMAIL@uw.edu");
		developers.put(5, "Parker Johnson (EMAIL@uw.edu");
	}
	
	/**
	 * This method returns a defensive copy of the map of developer(s) info.
	 * 
	 * @return	a defensive copy of developers HashMap.
	 */
	public Map<Integer, String> getDevelopers() {
		//Defensive Copy
		return new HashMap<>(developers);
	}
}
