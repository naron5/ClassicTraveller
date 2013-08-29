package decoder;

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
	public Planet(String planetName, int hexLocale, char starportType, int planetSize, int planetAtmosphere, int hydroPercent, int population,
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
	
	
	public boolean[] getZone() {
		return zone;
	}

	public void setZone(boolean[] zone) {
		this.zone = zone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHexLocation() {
		return hexLocation;
	}

	public void setHexLocation(int hexLocation) {
		this.hexLocation = hexLocation;
	}

	public UniversalPlanetaryProfile getProfile() {
		return profile;
	}

	public void setProfile(UniversalPlanetaryProfile profile) {
		this.profile = profile;
	}
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	private void debug(String s){
		System.out.println(s);
	}

	
}
