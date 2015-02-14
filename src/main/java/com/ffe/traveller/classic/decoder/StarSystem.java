package com.ffe.traveller.classic.decoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystem {
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


    public UniversalPlanetaryProfile getProfile() {
        return mainWorld.getProfile();
    }

    protected StarSystem() {
        mainWorld = PlanetMaker.CreatePlanet(null, null, null, null, null, null, null, null, null, null, null, null);

        stars = new HashMap<>();

        zone = TravelZone.Green;
    }
}
