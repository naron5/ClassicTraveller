package com.ffe.traveller.classic.decoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by darkmane on 2/19/15.
 */
public class MinorPlanet extends Planet {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private Planet mainWorld;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private StarSystem.Zone zone;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Set<TradeClassifications> tradeClassifications;





}
