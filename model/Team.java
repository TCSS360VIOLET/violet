package model;

import java.util.HashMap;
import java.util.Map;

/* NOTE TO OTHER DEVELOPERS:
 * Be sure to add your emails into the developers HashMap shown below in the constructor!
 * 
 * You have two ways of getting team info from this class:
 * 1) Just call a for-each style loop like so on the HashMap...
 * 
 * for(Map.Entry<Integer, String> entry : Team.getDevelopersMap().entrySet()) {
 * 		String member = entry.getValue(); //This will give the string "FIRST NAME LAST NAME (EMAIL@uw.edu)" for that member.
 * 		//Do stuff with this member's info here
 * }
 * 
 * 2) There is also an option for just a String[] array of each memeber using getDevelopersArray().
 */

/**
 * This class will hold information on the team members/developers on Team violet.
 * It allows us to keep our personal and contact info in once central place, so
 * we wont need to change it in multiple files.
 * 
 * @author Nickolas Zahos (nzahos@uw.edu)
 * @author An Ho (aho1999@uw.edu)
 * @author Edward Chung (edward92@uw.edu)
 * @author Lixin Wang
 * @author Parker Johnson (ptj7@uw.edu)
 */
public class Team {
	/** This map will hold the developer(s) info as "FIRST NAME LAST NAME, EMAIL" */
	private Map<Integer, String> developers;
	
	/** Default Constructor - Takes no parameters, but has all team info stored. */
	public Team() {
		developers = new HashMap<Integer, String>();
		
		// ADD YOUR PERSONAL INFO HERE! Ex: First Last (email)
		developers.put(1, "Nickolas Zahos (nzahos@uw.edu)");
		developers.put(2, "An Ho (aho1999@uw.edu)");
		developers.put(3, "Edward Chung (edward92@uw.edu)");
		developers.put(4, "Lixin Wang (EMAIL@uw.edu)");
		developers.put(5, "Parker Johnson (ptj7@uw.edu)");
	}
	
	/**
	 * This get method returns a defensive copy of the map of developer(s) info.
	 * 
	 * @return	a defensive copy of developers HashMap.
	 */
	public Map<Integer, String> getDevelopersMap() {
		// Return Defensive Copy
		return new HashMap<>(developers);
	}

	/**
	 * This get methods returns a String array with each of the developer's info as a seperate element.
	 */
	public String[] getDevelopersArray() {
		String[] devsArray = new String[developers.size()];
		int i = 0;
		
		for(Map.Entry<Integer, String> entry : developers.entrySet()) {
			devsArray[i] = entry.getValue();
			i++;
		}

		return devsArray;
	}
}
