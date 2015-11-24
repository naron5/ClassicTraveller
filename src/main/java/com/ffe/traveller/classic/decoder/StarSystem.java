package com.ffe.traveller.classic.decoder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystem {


    public enum Zone {
        UNAVAILABLE, INNER, HABITABLE, OUTER
    }

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Planet mainWorld;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private TravelZone zone;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private HashMap<Star.StarPosition, Star> stars;



    @Getter
    HashMap<Integer, Planet> orbits;




    private static Map<String, Object> orbitalDistanceMap = new HashMap<>();

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private int maxOrbits;


    public UniversalPlanetaryProfile getProfile() {
        return mainWorld.getProfile();
    }

    protected StarSystem() {
        mainWorld = PlanetMaker.CreatePlanet(null, null, null, null, null, null, null, null, null, null, null, null, null);

        stars = new HashMap<>();

        zone = TravelZone.Green;

        orbits = new HashMap<>();
    }

    @JsonProperty
    public Integer getMainWorldOrbit() {
        Integer orbit = null;
        for (Integer i : orbits.keySet()) {
            if (orbits.get(i) == mainWorld)
                orbit = i;
        }
        return orbit;
    }

    @SuppressWarnings("unchecked")
    private static void loadProperties() {

        InputStream input;

        if (orbitalDistanceMap.isEmpty()) {

            input = Star.class.getResourceAsStream("orbital_distance.yml");

            Yaml yaml = new Yaml();
            orbitalDistanceMap = (Map<String, Object>) yaml.load(input);
        }

    }


}
