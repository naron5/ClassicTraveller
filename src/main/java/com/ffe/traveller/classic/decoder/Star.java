package com.ffe.traveller.classic.decoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

/**
 * Created by sechitwood on 2/9/15.
 */
public class Star {

    private static Map<String, Map<String, Double>> luminosityMap = new HashMap<>();
    private static Map<String, Object> orbitalDistanceMap = new HashMap<>();

    private static final double K_temp = 374.025;


    public enum StellarClass {
        B, A, M, K, G, F
    }

    public enum StellarSize {
        Ia, Ib, II, III, IV, V, VI, D
    }

    public enum StarPosition {
        PRIMARY, SECONDARY, TERTIARY
    }

    @Getter
    private StellarClass aClass;

    @Getter
    private StellarSize starSize;

    @Getter
    Integer starOrbit;

    @Getter
    HashMap<Integer, Planet> orbits;

    @Getter
    private Set<Integer> unavailable;
    @Getter
    private Set<Integer> inner;
    @Getter
    private Set<Integer> habitable;
    @Getter
    private Set<Integer> outer;

    private int maxOrbits;

    /**
     * @param c Class
     * @param s Size
     * @param o Orbit
     */
    public Star(StellarClass c, StellarSize s, Integer o, Integer os) {
        aClass = c;
        starSize = s;
        starOrbit = o;
        maxOrbits = os;
        orbits = new HashMap<>();
        loadProperties();

        double min_available_distance = Math.sqrt(this.getLuminosity()) *
                Math.pow((K_temp * minGreenhouse * (1.0 - minAlbedo) / (2273.0 + 272.15)), 2.0);
        double min_habitable_distance = Math.sqrt(this.getLuminosity()) *
                Math.pow((K_temp * minGreenhouse * (1.0 - minAlbedo) / (50.0 + 272.15)), 2.0);
        double max_habitable_distance = Math.sqrt(this.getLuminosity()) *
                Math.pow((K_temp * maxGreenhouse * (1.0 - maxAlbedo) / (-20.0 + 272.15)), 2.0);

        unavailable = new HashSet<Integer>();
        inner = new HashSet<Integer>();
        habitable = new HashSet<Integer>();
        outer = new HashSet<Integer>();
        int counter = 0;
        int[] orbital_dist = (int[]) orbitalDistanceMap.get("orbital_distances");

        while (orbital_dist[counter] < min_available_distance && counter < maxOrbits) {
            unavailable.add(new Integer(counter++));
        }

        while (orbital_dist[counter] < min_habitable_distance && counter < maxOrbits) {
            inner.add(new Integer(counter++));
        }

        while (orbital_dist[counter] < max_habitable_distance && counter < maxOrbits) {
            habitable.add(new Integer(counter++));
        }

        while (counter < maxOrbits) {
            outer.add(new Integer(counter++));
        }
    }

    public double getLuminosity() {
        double lum = 0.0;
        loadProperties();
        lum = luminosityMap.get(this.aClass.toString() + "0").get(this.starSize.toString());
        return lum;
    }

    @SuppressWarnings("unchecked")
    private static void loadProperties() {

        InputStream input;

        if (luminosityMap.isEmpty()) {

            input = Star.class.getResourceAsStream("stellar_luminosity.yml");

            Yaml yaml = new Yaml();
            luminosityMap = (Map<String, Map<String, Double>>) yaml.load(input);
        }

        if (orbitalDistanceMap.isEmpty()) {

            input = Star.class.getResourceAsStream("orbital_distance.yml");

            Yaml yaml = new Yaml();
            orbitalDistanceMap = (Map<String, Object>) yaml.load(input);
        }

    }


    private double minAlbedo = 0.01;
    private double maxAlbedo = 0.99;
    private double minGreenhouse = 1.0;
    private double maxGreenhouse = 2.2;


}
