package com.ffe.traveller.classic.makers;

import java.util.Random;

/**
 * @author markknights
 *
 */

public class PlanetMaker {
	
	/**
	 * Produces an unnamed, unidentified planet
	 */
	public static Planet CreatePlanet(){
		Planet planet = new Planet("Unnamed", null, new UniversalPlanetaryProfile());
		
		details = planet.getName() + "\n" + planet.getProfile().getDescription();
		debug(details);

		return planet;
	}
	
	/**
	 * @param identified
	 * 
	 * Generates an unexplored planet but one that may have been identified
	 * by science with a scientifically generated name.  If not it will be
	 * unnamed.  If the hex location is unknown use a null if 
	 * a location is to be entered enter it in the integer format CCNN where
	 * C is the column number and N is the hex number
	 */
	public static Planet CreatePlanet(boolean identified, @Nullable Integer hexLocale){
		if(identified){
			name = getScientificName();
		}else{
			name = "Unnamed";
		}
		
		if(hexLocale != null){
			hexLocation = hexLocale;
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
	public static Planet CreatePlanet(String planetName, int hexLocale, Starport starportType, int planetSize, int planetAtmosphere, int hydroPercent, int population,
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
	
	
}
