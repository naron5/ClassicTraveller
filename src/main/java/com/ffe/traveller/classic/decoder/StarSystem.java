package com.ffe.traveller.classic.decoder;

import java.util.HashMap;
import lombok.*;
/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystem {
		@Getter @Setter(AccessLevel.PROTECTED)
    public Planet MainWorld;
		@Getter @Setter
    public HashMap<Integer, Planet> MinorPlanets;
}
