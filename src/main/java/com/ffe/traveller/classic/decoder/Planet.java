package com.ffe.traveller.classic.decoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.util.Random;

/**
 * @author markknights
 */

public class Planet {
    final static String PREFIX = "PSR ";
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String sector;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String subsector;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String name;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int hexLocation;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private UniversalPlanetaryProfile profile;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private boolean navalBase;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private boolean scoutBase;

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
     * @param navalBase
     * @param scoutBase  Generates a fully formed planet.  Hex location is expected but if it is
     *                   not yet placed put a negative number into the hexLocale parameter
     */
    protected Planet(@Null String planetName, @Null Integer hexLocale, UniversalPlanetaryProfile upp, Boolean navalBase, Boolean scoutBase) {
        name = planetName;

        if (hexLocale != null) {
            hexLocation = hexLocale;
        }

        profile = upp;

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


}
