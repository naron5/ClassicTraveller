package com.ffe.traveller.classic.decoder;

import javax.validation.constraints.Null;

/**
 * Created by darkmane on 2/15/15.
 */
public class GasGiant extends Planet {

    private Type planetType = Type.SMALL_GAS_GIANT;

    /**
     * Produces an unnamed, unidentified planet
     */
    protected GasGiant() {
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
    protected GasGiant(boolean identified, int hexLocale) {
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

        profile = new UniversalPlanetaryProfile(Starport.X, 10, 10, 0, 0, 0, 0, 0);
        planetType = Type.SMALL_GAS_GIANT;

    }

    /**
     * @param planetName
     * @param hexLocale  Generates a fully formed planet.  Hex location is expected but if it is
     *                   not yet placed put a negative number into the hexLocale parameter
     */
    protected GasGiant(@Null String planetName, @Null Integer hexLocale) {
        name = planetName;

        if (hexLocale != null) {
            hexLocation = hexLocale;
        }

        profile = new UniversalPlanetaryProfile(Starport.X, 10, 10, 0, 0, 0, 0, 0);
        planetType = Type.SMALL_GAS_GIANT;
    }



    @Override
    public Type getPlanetType() {
        return planetType;
    }
}
