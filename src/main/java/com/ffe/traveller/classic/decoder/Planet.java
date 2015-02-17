package com.ffe.traveller.classic.decoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.Random;

/**
 * @author markknights
 */

public class Planet {
    final static String PREFIX = "PSR ";

    private static final double maxLand = 0.2;
    private static final double minLand = 0.2;
    private static final double water = 0.02;
    private static final double maxIce = 0.85;
    private static final double maxCloud = 0.8;
    private static final double minIce = 0.55;
    private static final double minCloud = 0.4;


    private int maxOrbits = 0;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private HashMap<Integer, Planet> satellites = new HashMap<>();

    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected String sector;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected String subsector;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected String name;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected int hexLocation;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected UniversalPlanetaryProfile profile;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected boolean navalBase;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected boolean scoutBase;

    protected double getMinimumGreenhouse() {
        double minG = 1.0;
        switch (profile.getAtmosphere()) {
            case 4:
            case 5:
                minG = 1.05;
                break;
            case 6:
            case 7:
            case 14:
                minG = 1.1;
                break;
            case 8:
            case 9:
            case 13:
                minG = 1.15;
                break;
            case 10:
                minG = 1.2;
                break;
            case 11:
            case 12:
                minG = 1.2;
                break;
        }
        return minG;
    }

    protected double getMaximumGreenhouse() {
        double maxG = 1.0;
        switch (profile.getAtmosphere()) {
            case 4:
            case 5:
                maxG = 1.05;
                break;
            case 6:
            case 7:
            case 14:
                maxG = 1.1;
                break;
            case 8:
            case 9:
            case 13:
                maxG = 1.15;
                break;
            case 10:
                maxG = 1.7;
                break;
            case 11:
            case 12:
                maxG = 2.2;
                break;
        }
        return maxG;
    }

    public double getMaximumAlbedo() {
        double percWater = profile.getHydro() / 10.0;
        double percLand = 1 - percWater;
        double percIce = percLand * 0.1;
        percWater -= percIce / 2;
        percLand -= percIce / 2;

        if ((profile.getAtmosphere() == 0 || profile.getAtmosphere() == 1) && profile.getHydro() > 0) {
            percWater = 0.0;
            percIce = profile.getHydro() / 10.0;
            percLand = 1 - percIce;
        }

        double cloudiness = calculateCloudiness();

        double unobstructedLand = percLand * (1 - cloudiness);
        double unobstructedWater = percWater * (1 - cloudiness);
        double unobstructedIce = percIce * (1 - cloudiness);


        return (cloudiness * maxCloud) + (unobstructedLand * maxLand) + (unobstructedWater * water) + (unobstructedIce * maxIce);

    }

    public double getMinimumAlbedo() {
        double percWater = profile.getHydro() / 10.0;
        double percLand = 1 - percWater;
        double percIce = percLand * 0.1;
        percWater -= percIce / 2;
        percLand -= percIce / 2;

        if ((profile.getAtmosphere() == 0 || profile.getAtmosphere() == 1) && profile.getHydro() > 0) {
            percWater = 0.0;
            percIce = profile.getHydro() / 10.0;
            percLand = 1 - percIce;
        }

        double cloudiness = calculateCloudiness();

        double unobstructedLand = percLand * (1 - cloudiness);
        double unobstructedWater = percWater * (1 - cloudiness);
        double unobstructedIce = percIce * (1 - cloudiness);


        return (cloudiness * minCloud) + (unobstructedLand * minLand) + (unobstructedWater * water) + (unobstructedIce * minIce);

    }

    private double calculateCloudiness() {
        double cloudiness = 0.0;
        switch (profile.getHydro()) {
            case 2:
                cloudiness = 0.1;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                cloudiness = 0.1 * (profile.getHydro() - 2);
                break;
            case 10:
                cloudiness = 0.7;
                break;
        }

        switch (profile.getAtmosphere()) {
            case 0:
            case 1:
            case 2:
            case 3:
                if (cloudiness > 0.20) {
                    cloudiness = 0.2;
                }
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cloudiness += 0.4;
                break;
            case 14:
                cloudiness /= 2;
                break;

        }
        return cloudiness;
    }

    public void createOrbits(int number) {
        maxOrbits = 0;

    }

    public enum Type {
        ROCKY_PLANET, PLANETOID_BELT, LARGE_GAS_GIANT, SMALL_GAS_GIANT
    }

    /**
     * Produces an unnamed, unidentified planet
     */
    protected Planet() {
        name = "Unnamed";
        profile = new UniversalPlanetaryProfile(Starport.C, 7, 7, 7, 7, 7, 7);
        hexLocation = -1;
    }

    /**
     * @param identified Generates an unexplored planet but one that may have been identified
     *                   by science with a scientifically generated name.  If not it will be
     *                   unnamed.  If the hex location is unknown enter a negative number if
     *                   a location is to be entered enter it in the integer format CCNN where
     *                   C is the column number and N is the hex number
     */
    protected Planet(boolean identified, int hexLocale) {
        if (identified) {
            name = getScientificName();
        } else {
            name = "Unnamed";
        }

        if (hexLocale >= 0) {
            hexLocation = hexLocale;
        } else {
            hexLocation = -1;
        }

        profile = new UniversalPlanetaryProfile(Starport.C, 7, 7, 7, 7, 7, 7);


    }

    /**
     * @param planetName
     * @param hexLocale
     * @param upp
     * @param naval
     * @param scout      Generates a fully formed planet.  Hex location is expected but if it is
     *                   not yet placed put a negative number into the hexLocale parameter
     */
    protected Planet(@Null String planetName, @Null Integer hexLocale, UniversalPlanetaryProfile upp, Boolean naval, Boolean scout) {
        name = planetName;

        if (hexLocale != null) {
            hexLocation = hexLocale;
        }

        profile = upp;
        navalBase = naval;
        scoutBase = scout;
    }

    /**
     * @return
     */
    protected static String getScientificName() {
        String scienceName;
        Random generator = new Random();
        int charValue = 97 + (Math.abs(generator.nextInt() % 26));
        char postfix = (char) charValue;

        scienceName = PREFIX + Math.abs(generator.nextInt() % 10000) + "+" + Math.abs(generator.nextInt() % 100) + "-" + postfix;
        return scienceName;
    }

    public Type getPlanetType() {
        if (profile.getDiameter() == 0)
            return Type.PLANETOID_BELT;
        else
            return Type.ROCKY_PLANET;
    }

}
