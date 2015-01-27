/**
 * @author markknights
 *
 * Bringing Traveller into the Applications world!
 */
package com.ffe.traveller.classic.decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

public class UniversalPlanetaryProfile {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int diameter;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int atmosphere;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int hydro;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int pop;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int planGov;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int lawLevel;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int techLev;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    Starport starport;

    boolean debug = false;

    private static Map<String, Object> propertyMap;

    public UniversalPlanetaryProfile(){
        loadProperties();
    }

    @SuppressWarnings("unchecked")
    private static void loadProperties() {
        if (propertyMap != null)
            return;

        InputStream input;
        try {
            input = new FileInputStream(new File(
                    "src/properties/universal_planetary_profile.yml"));
            Yaml yaml = new Yaml();
            propertyMap = (Map<String, Object>) yaml.load(input);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }

    public String getSize() {

        return String.format((String) propertyMap.get("PlanetSize"),
                diameter * 1000, diameter * 1600);
    }

    public String getAtmosphere() {
        @SuppressWarnings("unchecked")
        ArrayList<String> atmosArray = (ArrayList<String>) propertyMap.get("Atmosphere");

        return atmosArray.get(atmosphere);
    }

    public String getHydrographics() {
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

    public String getPopulation() {
        @SuppressWarnings("unchecked")
        ArrayList<String> popArray = (ArrayList<String>) propertyMap.get("Population");
        return popArray.get(pop);
    }

    public String getGovernment() {
        @SuppressWarnings("unchecked")
        ArrayList<String> govArray = (ArrayList<String>) propertyMap.get("Government");
        return govArray.get(planGov);
    }

    public String getLawLevel() {
        @SuppressWarnings("unchecked")
        ArrayList<String> lawArray = (ArrayList<String>) propertyMap.get("LawLevel");
        return lawArray.get(lawLevel);

    }

    @SuppressWarnings("unchecked")
    public String getStarport() {

        String rv;
        Map<String, String> ports = (Map<String, String>) propertyMap
                .get("Starport");

        switch (starport) {
            case A:
                rv = ports.get("ClassA");
                break;
            case B:

                rv = ports.get("ClassB");
                break;
            case C:
                rv = ports.get("ClassC");
                break;
            case D:
                rv = ports.get("ClassD");
                break;

            case E:
                rv = ports.get("ClassE");
                break;

            case none:
                rv = ports.get("None");
                break;
            default:
                rv = "";
                break;
        }
        return rv;
    }

    /**
     * @param string
     */
    private void debug(String string) {
        if (debug) {
            System.out.println(string);
        }
    }
}
