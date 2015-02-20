package com.ffe.traveller.classic.decoder;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystem {

    private static final double K_temp = 374.025;


    public enum Zone {
        INNER, HABITABLE, OUTER
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

    public static final Integer NEAR_ORBIT = -1;
    public static final Integer FAR_ORBIT = Integer.MAX_VALUE;
    public static final Integer CENTER = Integer.MIN_VALUE;

    @Getter
    HashMap<Integer, Planet> orbits;

    @Getter
    private Set<Integer> unavailableOrbits;
    @Getter
    private Set<Integer> innerOrbits;
    @Getter
    private Set<Integer> habitableOrbits;
    @Getter
    private Set<Integer> outerOrbits;

    private static Map<String, Object> orbitalDistanceMap = new HashMap<>();

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private int maxOrbits;


    public UniversalPlanetaryProfile getProfile() {
        return mainWorld.getProfile();
    }

    protected StarSystem() {
        mainWorld = PlanetMaker.CreatePlanet(null, null, null, null, null, null, null, null, null, null, null, null);

        stars = new HashMap<>();

        zone = TravelZone.Green;
    }

    private void determineOrbits() {

        double minAlbedo = 0.01;
        double maxAlbedo = 0.99;
        double minGreenhouse = 1.0;
        double maxGreenhouse = 2.2;


        orbits = new HashMap<>();
        loadProperties();
        Star central = this.getStars().get(Star.StarPosition.PRIMARY);
        double min_available_distance = Math.sqrt(central.getLuminosity()) *
                Math.pow((K_temp * minGreenhouse * (1.0 - minAlbedo) / (2273.0 + 272.15)), 2.0);
        double min_habitable_distance = Math.sqrt(central.getLuminosity()) *
                Math.pow((K_temp * minGreenhouse * (1.0 - minAlbedo) / (50.0 + 272.15)), 2.0);
        double max_habitable_distance = Math.sqrt(central.getLuminosity()) *
                Math.pow((K_temp * maxGreenhouse * (1.0 - maxAlbedo) / (-20.0 + 272.15)), 2.0);

        Set<Integer> u = new HashSet<Integer>();
        Set<Integer> i = new HashSet<Integer>();
        Set<Integer> h = new HashSet<Integer>();
        Set<Integer> o = new HashSet<Integer>();
        int counter = 0;
        int[] orbital_dist = (int[]) orbitalDistanceMap.get("orbital_distances");

        while (orbital_dist[counter] < min_available_distance && counter < maxOrbits) {
            u.add(new Integer(counter++));
        }

        while (orbital_dist[counter] < min_habitable_distance && counter < maxOrbits) {
            i.add(new Integer(counter++));
        }

        while (orbital_dist[counter] < max_habitable_distance && counter < maxOrbits) {
            h.add(new Integer(counter++));
        }

        while (counter < maxOrbits) {
            o.add(new Integer(counter++));
        }

        unavailableOrbits = ImmutableSet.copyOf(u);
        innerOrbits = ImmutableSet.copyOf(i);
        habitableOrbits = ImmutableSet.copyOf(h);
        outerOrbits = ImmutableSet.copyOf(o);
    }


    public Set<Integer> calculateHabitableZone(Planet p) {
        Set<Integer> rv = new HashSet<>();

        double minG = p.getMinimumGreenhouse();
        double maxG = p.getMaximumGreenhouse();

        double minA = p.getMinimumAlbedo();
        double maxA = p.getMaximumAlbedo();

        Star central = this.getStars().get(Star.StarPosition.PRIMARY);
        double min_habitable_distance = Math.sqrt(central.getLuminosity()) *
                Math.pow((K_temp * minG * (1.0 - minA) / (50.0 + 272.15)), 2.0);
        double max_habitable_distance = Math.sqrt(central.getLuminosity()) *
                Math.pow((K_temp * maxG * (1.0 - maxA) / (-20.0 + 272.15)), 2.0);

        int[] orbital_dist = (int[]) orbitalDistanceMap.get("orbital_distances");
        int counter = 0;
        while (orbital_dist[counter] < min_habitable_distance && counter < maxOrbits) {
            counter++;
        }
        while (orbital_dist[counter] < max_habitable_distance && counter < maxOrbits) {
            rv.add(new Integer(counter++));
        }

        return ImmutableSet.copyOf(rv);
    }


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
