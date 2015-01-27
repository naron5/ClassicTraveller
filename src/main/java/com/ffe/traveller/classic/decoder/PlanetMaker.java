package com.ffe.traveller.classic.decoder;


import javax.validation.constraints.Null;

import com.ffe.traveller.util.DiceGenerator;

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
                                      @Null Integer techLevel, @Null boolean navalBase, @Null boolean scoutBase) {

        int upperBound = 12;
        int lowerBound = 0;
        if (starportType == null) {
            starportType = getStarport(DiceGenerator.rollDice(2));
        }

        if (planetSize == null) {
            planetSize = DiceGenerator.rollDiceWithModifier(2, 6, -2);
            // If planet size is null and atmosphere is not,
            // limit size to the range that could possibly result in the atmosphere value
            lowerBound = planetAtmosphere + 7 - 12 < 0 ? 0 : planetAtmosphere + 7 - 12;
            upperBound = planetAtmosphere + 7 - 2 > 0x0A ? 0x0A : planetAtmosphere + 7 - 2;
            if (planetAtmosphere != null && (planetSize < lowerBound || planetSize > upperBound)) {
                planetSize = DiceGenerator.rollDiceInRange(2, 0, lowerBound, upperBound);
            }
        }

        if (planetAtmosphere == null) {
            planetAtmosphere = DiceGenerator.rollDiceWithModifier(2, (-7 + planetSize));
            if (planetAtmosphere < 0 || planetSize == 0) {
                planetAtmosphere = 0;
            } else {

                if (hydroPercent != null && (planetAtmosphere < lowerBound && planetAtmosphere > upperBound)) {
                    lowerBound = hydroPercent + 7 - 12 < 0 ? 0 : hydroPercent + 7 - 12;
                    upperBound = hydroPercent + 7 - 2 > 0x0A ? 0x0A : hydroPercent + 7 - 2;
                    planetAtmosphere = DiceGenerator.rollDiceInRange(2, 0, lowerBound, upperBound);
                }
            }
        }

        if (hydroPercent == null) {
            int modifier = (planetAtmosphere < 2 || planetAtmosphere > 9) ? -11 : -7;
            hydroPercent = DiceGenerator.rollDiceWithModifier(2, 0, modifier + planetAtmosphere);
        }

        if (population == null) {
            population = DiceGenerator.rollDiceWithModifier(2, -2);

            if (planetGovernment != null) {
                lowerBound = planetGovernment + 7 - 12 < 0 ? 0 : planetGovernment + 7 - 12;
                upperBound = planetGovernment + 7 - 2 < 0 ? 0x0A : planetGovernment + 7 - 2;
                if(population < lowerBound || population> upperBound){
                    population = DiceGenerator.rollDiceInRange(2, 0, lowerBound, upperBound);
                }
            }
        }

        if(planetGovernment == null){
            planetGovernment = DiceGenerator.rollDiceWithModifier(2, (-7 + population));

            if(law == null){
                lowerBound = law + 7 -12 < 0?0:law + 7 -12;
                upperBound = law + 7 -2 < 0?0:law + 7 -2;
                if(law < lowerBound || law > upperBound){
                    population = DiceGenerator.rollDiceInRange(2, 0, lowerBound, upperBound);
                }
            }

            if (planetGovernment > 13) {
                planetGovernment = 13;
            }
        }

        if(law == null){

            law = DiceGenerator.rollDiceWithModifier(2, (-7 + planetGovernment));

            if (law < 0) {
                law = 0;
            }
        }


        techLev = getTechLevel();

        if ((starportType == Starport.A || starport == Starport.B)
                && DiceGenerator.rollDice(1, 2) % 2 == 0) {
            navy = true;
        }

        if ((starportType == Starport.A || starport == Starport.B
                || starportType == Starport.C || starport == Starport.D)
                && DiceGenerator.rollDice(1, 2) % 2 == 0) {
            scout = true;
        }

        if (util.DiceGenerator.rollDice(1, 2) % 2 == 0) {
            gg = true;
        }
        determineTradeClass();
        description = printPlanetaryData();
        debug(description);

        UniversalPlanetaryProfile upp = new UniversalPlanetaryProfile(starportType, planetSize, planetAtmosphere, hydroPercent, population, planetGovernment, law);

        return new Planet(planetName, hexLocale, upp, techLevel, navalBase, scoutBase);

    }


    /**
     * @param randRoll
     * @return
     */
    protected static Starport getStarport(int randRoll) {
        Starport result = Starport.none;
        switch (randRoll) {
            case 2:
            case 3:
            case 4:
                result = Starport.A;
                break;
            case 5:
            case 6:
                result = Starport.B;
                break;
            case 7:
            case 8:
                result = Starport.C;
                break;
            case 9:
                result = Starport.D;
                break;
            case 10:
            case 11:
                result = Starport.E;
                break;
            case 12:
                result = Starport.X;
                break;
            default:
                result = Starport.none;

        }
        return result;
    }

}
