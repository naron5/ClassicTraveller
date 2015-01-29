/**
 * @author darkmane
 *
 * Bringing Traveller into the Applications world!
 */
package com.ffe.traveller.classic.decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ffe.traveller.util.DiceGenerator;

import static com.ffe.traveller.util.DiceGenerator.*;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.yaml.snakeyaml.Yaml;


public class UniversalPlanetaryProfileMaker {

    public static UniversalPlanetaryProfile CreateUniversalPlanetaryProfile() {

        Starport starport = getStarport(rollDice(2));
        Integer diameter = rollDiceWithModifier(2, -2);
        Integer atmosphere = 0;
        if (diameter == 0) {
            atmosphere = 0;
        } else {
            atmosphere = rollDiceWithModifier(2, (-7 + diameter));
            if (atmosphere < 0) {
                atmosphere = 0;
            }
        }
        Integer hydro = rollDiceWithModifier(2, (-7 + atmosphere));
        Integer pop = rollDiceWithModifier(2, -2);
        Integer planGov = rollDiceWithModifier(2, (-7 + pop));
        if (planGov > 13) {
            planGov = 13;
        }
        Integer lawLevel = rollDiceWithModifier(2, (-7 + planGov));
        if (lawLevel < 0) {
            lawLevel = 0;
        }


        UniversalPlanetaryProfile upp = new UniversalPlanetaryProfile(starport, diameter,
                atmosphere, hydro, pop, planGov, lawLevel);

        upp.rollTechLevel(rollDice(1));

        return upp;
    }

    public UniversalPlanetaryProfile CreateUniversalPlanetaryProfile(Starport starportType, Integer planetSize,
                                                                     Integer planetAtmosphere, Integer hydroPercent, Integer population,
                                                                     Integer planetGovernment, Integer law, Integer techLevel) {

        int upperBound = 12;
        int lowerBound = 0;
        if (starportType == null) {
            starportType = getStarport(DiceGenerator.rollDice(2));
        }

        if (planetSize == null) {
            planetSize = rollDiceWithModifier(2, -2);
            // If planet size is null and atmosphere is not,
            // limit size to the range that could possibly result in the atmosphere value
            lowerBound = planetAtmosphere + 7 - 12 < 0 ? 0 : planetAtmosphere + 7 - 12;
            upperBound = planetAtmosphere + 7 - 2 > 0x0A ? 0x0A : planetAtmosphere + 7 - 2;
            if (planetAtmosphere != null && (planetSize < lowerBound || planetSize > upperBound)) {
                planetSize = rollDiceInRange(2, 0, lowerBound, upperBound);
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
                    planetAtmosphere = rollDiceInRange(2, 0, lowerBound, upperBound);
                }
            }
        }

        if (hydroPercent == null) {
            int modifier = (planetAtmosphere < 2 || planetAtmosphere > 9) ? -11 : -7;
            hydroPercent = rollDiceWithModifier(2, modifier + planetAtmosphere);
        }

        if (population == null) {
            population = DiceGenerator.rollDiceWithModifier(2, -2);

            if (planetGovernment != null) {
                lowerBound = planetGovernment + 7 - 12 < 0 ? 0 : planetGovernment + 7 - 12;
                upperBound = planetGovernment + 7 - 2 < 0 ? 0x0A : planetGovernment + 7 - 2;
                if (population < lowerBound || population > upperBound) {
                    population = rollDiceInRange(2, 0, lowerBound, upperBound);
                }
            }
        }

        if (planetGovernment == null) {
            planetGovernment = DiceGenerator.rollDiceWithModifier(2, (-7 + population));

            if (law == null) {
                lowerBound = law + 7 - 12 < 0 ? 0 : law + 7 - 12;
                upperBound = law + 7 - 2 < 0 ? 0 : law + 7 - 2;
                if (law < lowerBound || law > upperBound) {
                    population = rollDiceInRange(2, 0, lowerBound, upperBound);
                }
            }

            if (planetGovernment > 13) {
                planetGovernment = 13;
            }
        }

        if (law == null) {

            law = rollDiceWithModifier(2, (-7 + planetGovernment));

            if (law < 0) {
                law = 0;
            }
        }

        UniversalPlanetaryProfile upp = new UniversalPlanetaryProfile(starportType, planetSize,
                planetAtmosphere, hydroPercent, population, planetGovernment, law);

        upp.rollTechLevel(rollDice(1));

        return upp;
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
