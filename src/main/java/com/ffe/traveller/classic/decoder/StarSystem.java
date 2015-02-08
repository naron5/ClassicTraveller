package com.ffe.traveller.classic.decoder;

import java.util.HashMap;
import java.util.Map;

import lombok.*;
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

    public UniversalPlanetaryProfile getProfile(){
        return mainWorld.getProfile();
    }

    protected StarSystem() {
        mainWorld = PlanetMaker.CreatePlanet(null, null,null, null, null, null, null, null, null, null, null, null);
        minorPlanets = new HashMap<>();
        zone = TravelZone.Green;
    }
}
