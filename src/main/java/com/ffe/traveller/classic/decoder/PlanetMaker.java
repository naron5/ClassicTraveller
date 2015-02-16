package com.ffe.traveller.classic.decoder;


import javax.validation.constraints.Null;

import static com.ffe.traveller.util.DiceGenerator.*;

/**
 * @author darkmane
 */

public class PlanetMaker {

    /**
     * @param planetName
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
     * @param scoutBase        Generates a fully formed planet.  Hex location is expected but if it is
     *                         not yet placed put a negative number into the hexLocale parameter
     */
    public static Planet CreatePlanet(@Null String planetName, @Null Integer hexLocale, @Null Starport starportType,
                                      @Null Integer planetSize, @Null Integer planetAtmosphere, @Null Integer hydroPercent,
                                      @Null Integer population, @Null Integer planetGovernment, @Null Integer law,
                                      @Null Integer techLevel, @Null Boolean navalBase, @Null Boolean scoutBase) {


        UniversalPlanetaryProfile upp = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(starportType,
                planetSize, planetAtmosphere, hydroPercent, population, planetGovernment, law);


        if ((upp.getStarport() == Starport.A || upp.getStarport() == Starport.B)
                && rollDice(1) % 2 == 0) {
            navalBase = true;
        }

        if ((upp.getStarport() == Starport.A || upp.getStarport() == Starport.B
                || upp.getStarport() == Starport.C || upp.getStarport() == Starport.D)
                && rollDice(1) % 2 == 0) {
            scoutBase = true;
        }

        return new Planet(planetName, hexLocale, upp, navalBase, scoutBase);

    }

    public static Planet CreatePlanet(@Null String planetName, @Null Integer hexLocale,
                                      @Null UniversalPlanetaryProfile upp, @Null Boolean navalBase,
                                      @Null Boolean scoutBase) {

        UniversalPlanetaryProfile profile = upp;

        if (upp == null) {
            profile = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile();
        }

        if (navalBase == null) {
            int navalRoll = rollDice(1);
            navalBase = ((profile.getStarport() == Starport.A || profile.getStarport() == Starport.B)
                    && navalRoll % 2 == 0);
        }

        if (scoutBase == null) {
            int scoutRoll = rollDice(1);
            scoutBase = ((profile.getStarport() == Starport.A || profile.getStarport() == Starport.B
                    || profile.getStarport() == Starport.C || profile.getStarport() == Starport.D)
                    && scoutRoll % 2 == 0);
        }
        return new Planet(planetName, hexLocale, profile, navalBase, scoutBase);

    }

}
