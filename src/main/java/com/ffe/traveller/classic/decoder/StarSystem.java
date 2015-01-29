package com.ffe.traveller.classic.decoder;

import java.util.HashMap;
import lombok.*;
/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystem {
		@Getter @Setter(AccessLevel.PROTECTED)
    private Planet mainWorld;
		@Getter @Setter
    private HashMap<Integer, Planet> minorPlanets;
    @Getter @Setter(AccessLevel.PROTECTED)
    private TravelZone zone;
}
