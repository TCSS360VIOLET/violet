package controller;

import model.About;

/**
 * The purpose of this Driver class is to debug and test different parts of the program.
 * This is not intended to be the main method that will operate the entire program.
 * 
 * @author Nickolas Zahos(nzahos@uw.edu)
 */
public class AboutTester {
	public static void main(String[] theArgs) {
		// Initialize and setup the About window
		About myAbout = new About();

		// Show the about window
		myAbout.show(true);
	}
}
