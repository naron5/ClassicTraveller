/**
 * @author markknights
 *
 * Bringing Traveller into the Applications world!
 */
package com.ffe.traveller.classic.decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import com.ffe.traveller.util.DiceGenerator;
import org.yaml.snakeyaml.Yaml;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

public class UniversalPlanetaryProfile {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer diameter;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer atmosphere;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer hydro;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer population;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer planGov;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer lawLevel;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer techLev;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    Starport starport;

    boolean debug = false;

    private static Map<String, Object> propertyMap;


    protected UniversalPlanetaryProfile(Starport port, Integer size, Integer atmos, Integer water,
                                        Integer pop, Integer gov, Integer law) {
        loadProperties();
        starport = port;
        diameter = size;
        atmosphere = atmos;
        hydro = water;
        population = pop;
        planGov = gov;
        lawLevel = law;

    }

    protected UniversalPlanetaryProfile(Starport port, Integer size, Integer atmos, Integer water,
                                        Integer pop, Integer gov, Integer law, Integer tech) {
        loadProperties();
        starport = port;
        diameter = size;
        atmosphere = atmos;
        hydro = water;
        population = pop;
        planGov = gov;
        lawLevel = law;
        techLev = tech;

    }


    private static void loadProperties() {
        if (propertyMap != null)
            return;


        InputStream input = UniversalPlanetaryProfile.class.getResourceAsStream("universal_planetary_profile.yml");
        Yaml yaml = new Yaml();
        propertyMap = (Map<String, Object>) yaml.load(input);

    }

    public String Size() {

        return String.format((String) propertyMap.get("PlanetSize"),
                diameter * 1000, diameter * 1600);
    }

    public String Atmosphere() {

        ArrayList<String> atmosArray = (ArrayList<String>) propertyMap.get("Atmosphere");

        return atmosArray.get(atmosphere);
    }

    public String Hydrographics() {
        String rv;
        if (hydro < 1) {
            rv = "No free standing water. Desert.";
        } else if (hydro > 9) {
            rv = "No land masses. Water World.";
        } else {

            rv = String.format("%d%% Water", hydro * 10);
        }
        return rv;
    }

    public String Population() {

        ArrayList<String> popArray = (ArrayList<String>) propertyMap.get("Population");
        return popArray.get(population);
    }

    public String Government() {

        ArrayList<String> govArray = (ArrayList<String>) propertyMap.get("Government");
        return govArray.get(planGov);
    }

    public String LawLevel() {

        ArrayList<String> lawArray = (ArrayList<String>) propertyMap.get("LawLevel");
        return lawArray.get(lawLevel);

    }

    protected void rollTechLevel(int roll) {
        int level = DiceGenerator.rollDice(1);

        // starport effects
        switch (starport) {
            case A:
                level += 6;
                break;
            case B:
                level += 4;
                break;
            case C:
                level += 2;
                break;
            case X:
                level -= 4;
                break;
            default:
                level = 0;
        }

        // planetary size effects
        if (diameter < 2) {
            level += +2;
        } else if (diameter > 1 && diameter < 5) {
            level += 1;
        }

        // planetary atmosphere effects
        if (atmosphere < 4 || atmosphere > 9) {
            level += 1;
        }

        // Hydrography percentage effects
        if (hydro == 9) {
            level += 1;
        } else if (hydro == 10) {
            level += 2;
        }

        // population effects
        if (population > 0 && population < 6) {
            level += 1;
        } else if (population == 9) {
            level += 2;
        } else if (population == 10) {
            level += 4;
        }

        // Government effects
        if (planGov == 0 || planGov == 5) {
            level += 1;
        } else if (planGov == 13) {
            level -= 2;
        }
        this.techLev = level;


    }

    public Set<TradeClassifications> getTradeClassifications() {
        // Agricultural
        Set<TradeClassifications> tradeClassifications = new HashSet();
        if ((atmosphere > 3 && atmosphere < 10) && (hydro > 3 && hydro < 9)
                && (population > 4 && population < 8)) {
            tradeClassifications.add(TradeClassifications.Agricultural);
        } else if (atmosphere < 4 && population > 5) {
            tradeClassifications.add(TradeClassifications.NonAgricultural);
        }

        // Industrial
        if ((atmosphere < 3 || atmosphere == 4 || atmosphere == 7 || atmosphere == 9)
                && population > 8) {
            tradeClassifications.add(TradeClassifications.Industrial);
            ;
        } else if (population < 7) {
            tradeClassifications.add(TradeClassifications.NonIndustrial);
        }

        // Financial
        if ((atmosphere == 6 || atmosphere == 8) && (population > 5 && population < 9)
                && (planGov > 3 && planGov < 10)) {
            tradeClassifications.add(TradeClassifications.Rich);
        } else if ((atmosphere > 1 && atmosphere < 6) && hydro < 4) {
            tradeClassifications.add(TradeClassifications.Poor);
        }
        return tradeClassifications;
    }


}
