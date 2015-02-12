package com.ffe.traveller.classic.decoder;

import lombok.Getter;

/**
 * Created by sechitwood on 2/9/15.
 */
public class Star {
    public enum StellarClass {
        B, A, M, K, G, F
    }

    public enum StellarSize {
        Ia, Ib, II, III,IV, V, VI,D
    }

    public enum StarPosition {
        PRIMARY, SECONDARY, TERTIARY
    }

    @Getter
    private StellarClass aClass;

    @Getter
    private StellarSize starSize;

    public Star(StellarClass c, StellarSize s){
        aClass = c;
        starSize = s;
    }

}
