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
		@Getter @Setter(AccessLevel.PROTECTED)
    private Planet mainWorld;
		@Getter @Setter
    private Map<Integer, Planet> minorPlanets;
    @Getter @Setter(AccessLevel.PROTECTED)
    private TravelZone zone;

    @Getter @Setter(AccessLevel.PROTECTED)
    private HashMap<Star.StarPosition, Star> stars;

    public UniversalPlanetaryProfile getProfile(){
        return mainWorld.getProfile();
    }

    protected StarSystem() {
        mainWorld = PlanetMaker.CreatePlanet(null, null,null, null, null, null, null, null, null, null, null, null);
        minorPlanets = new HashMap<>();
        stars = new HashMap<>();

        zone = TravelZone.Green;
    }
}
