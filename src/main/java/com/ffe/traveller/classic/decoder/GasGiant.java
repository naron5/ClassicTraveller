package com.ffe.traveller.classic.decoder;

import javax.validation.constraints.Null;

/**
 * Created by darkmane on 2/15/15.
 */
public class GasGiant extends Planet {

    private Type planetType = Type.SMALL_GAS_GIANT;

    /**
     * Produces an unnamed, unidentified planet
     */
    protected GasGiant() {
        name = "Unnamed";
        profile = new UniversalPlanetaryProfile(Starport.C, 7, 7, 7, 7, 7, 7);
        hexLocation = -1;


    }

    /**
     *
     * @param hexLocale
     * @param planetName
     * @param size
     */
    protected GasGiant(@Null Integer hexLocale, @Null String planetName, @Null Type size) {
        if (planetName == null) {
            name = "Unnamed";
        }

        if(hexLocale != null){
            hexLocation = hexLocale;
        }

        profile = new UniversalPlanetaryProfile(Starport.X, 10, 10, 0, 0, 0, 0, 0);
        if(size != null) {
            planetType = Type.SMALL_GAS_GIANT;
        }else{
            planetType = size;
        }
    }



    @Override
    public Type getPlanetType() {
        return planetType;
    }
}
