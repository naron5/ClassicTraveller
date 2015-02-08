package com.ffe.traveller.classic.decoder;

import org.elasticsearch.index.engine.Engine;

import java.util.HashMap;

/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystemMaker {

	public static StarSystem CreateStarSystem() {
        return new StarSystem();
	}

    public static StarSystem CreateStarSystem(UniversalPlanetaryProfile upp){
        return CreateStarSystem();
    }
}
