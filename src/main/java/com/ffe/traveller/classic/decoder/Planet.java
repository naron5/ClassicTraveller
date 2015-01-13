package com.ffe.traveller.classic.decoder;

import java.util.Random;

/**
 * @author markknights
 *
 */

public class Planet {
	String name, details;
	int hexLocation;
	boolean debug = true;
	UniversalPlanetaryProfile profile;
	final String PREFIX = "PSR ";
	boolean[] zone = new boolean[2]; // red, amber
	
	/**
	 * Produces an unnamed, unidentified planet
	 */
	public Planet(){
		name = "Unnamed";
		profile = new UniversalPlanetaryProfile();
		hexLocation = -1;
		
		details = name + "  " + hexLocation + "\n" + profile.getDescription();
		debug(details);
	}
	
	/**
	 * @param identified
	 * 
	 * Generates an unexplored planet but one that may have been identified
	 * by science with a scientifically generated name.  If not it will be
	 * unnamed.  If the hex location is unknown enter a negative number if 
	 * a location is to be entered enter it in the integer format CCNN where
	 * C is the column number and N is the hex number
	 */
	public Planet(boolean identified, int hexLocale){
		if(identified){
			name = getScientificName();
		}else{
			name = "Unnamed";
		}
		
		if(hexLocale >= 0){
			hexLocation = hexLocale;
		}else{
			hexLocation = -1;
		}
		
		profile = new UniversalPlanetaryProfile();
		
		details = name + "  " + hexLocation + "\n" + profile.getDescription();
		debug(details);
	}
	
	/**
	 * @param name
	 * @param hexLocale
	 * @param starportType
	 * @param planetSize
	 * @param planetAtmosphere
	 * @param hydroPercent
	 * @param population
	 * @param planetGovernment
	 * @param law
	 * @param techLevel
	 * @param navalBase
	 * @param scoutBase
	 * @param gasGiant
	 * 
	 * Generates a fully formed planet.  Hex location is expected but if it is
	 * not yet placed put a negative number into the hexLocale parameter
	 */
	public Planet(String planetName, int hexLocale, Starport starportType, int planetSize, int planetAtmosphere, int hydroPercent, int population,
			int planetGovernment, int law, int techLevel, boolean navalBase, boolean scoutBase, boolean gasGiant, boolean redZone, boolean amberZone){
		name = planetName;
		if(hexLocale >= 0){
			hexLocation = hexLocale;
		}else{
			hexLocation = -1;
		}
		
		if(redZone){
			zone[0] = true;
		}else if(amberZone){
			zone[1] = true;
		}
		
		profile = new UniversalPlanetaryProfile(starportType, planetSize, planetAtmosphere, hydroPercent, population, planetGovernment, law, techLevel, navalBase, scoutBase, gasGiant);
		details = name + "  " + hexLocation + "\n" + profile.getDescription();
		debug(details);
	}
	
	/**
	 * @return
	 */
	private String getScientificName() {
		String scienceName;
		Random generator = new Random();
		int charValue = 97 + (Math.abs(generator.nextInt() % 26));
		char postfix =  (char) charValue;
		
		scienceName = PREFIX + Math.abs(generator.nextInt() % 10000) + "+" + Math.abs(generator.nextInt() % 100) + "-" + postfix;
		return scienceName;
	}
	
	
	/**
	 * @return
	 * returns two space boolean array detailing if set to red or amber zone in that order
	 */
	public boolean[] getZone() {
		return zone;
	}

	/**
	 * @param zone
	 * sets zones to input array.  Should be a two space array detailing red or amber zone
	 * status in that order
	 */
	public void setZone(boolean[] zone) {
		this.zone = zone;
	}

	/**
	 * @return
	 * returns name of planet
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * sets name of planet
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * returns hex position of planet
	 */
	public int getHexLocation() {
		return hexLocation;
	}

	/**
	 * @param hexLocation
	 * sets the hex location of the planet
	 */
	public void setHexLocation(int hexLocation) {
		this.hexLocation = hexLocation;
	}

	/**
	 * @return
	 * Returns the UPP of the planet
	 */
	public UniversalPlanetaryProfile getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 * Sets the UPP of the planet to the passed parameter
	 */
	public void setProfile(UniversalPlanetaryProfile profile) {
		this.profile = profile;
	}
	
	/**
	 * @return
	 * Returns the String details of the planet
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 * Sets the details of the planet to the entered string
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	
	/**
	 * @param s
	 * 
	 * Used to insert debugging comments for myself
	 */
	private void debug(String s){
		if(debug)
			System.out.println(s);
	}

	
}
