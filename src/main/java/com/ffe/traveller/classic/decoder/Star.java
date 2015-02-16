package com.ffe.traveller.classic.decoder;

import com.google.common.collect.ImmutableSet;
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



    /**
     * @param c        Class
     * @param s        Size
     * @param location Orbit
     */
    public Star(StellarClass c, StellarSize s, Integer location) {
        aClass = c;
        starSize = s;
        starOrbit = location;


    }

    public double getLuminosity() {
        double lum = 0.0;

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

    }

}
