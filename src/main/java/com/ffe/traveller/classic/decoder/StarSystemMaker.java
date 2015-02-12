package com.ffe.traveller.classic.decoder;

import com.ffe.traveller.util.Utility;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.Random;

import static com.ffe.traveller.classic.decoder.Star.StellarClass.*;
import static com.ffe.traveller.classic.decoder.Star.StellarSize.*;
import static com.ffe.traveller.classic.decoder.Star.StarPosition.*;
import static com.ffe.traveller.util.DiceGenerator.roll;


/**
 * Created by darkmane on 1/15/15.
 */
public class StarSystemMaker {

    private static final int SOLITARY = 7;
    private static final int BINARY = 11;
    private static final int TRINARY = 12;

    public static StarSystem CreateStarSystem() {
        return new StarSystem();
    }

    public static StarSystem CreateStarSystem(Planet planet) {
        String hashSeed = planet.getSector() + "|" + planet.getSubsector()
                + "|" + (new Integer(planet.getHexLocation())).toString();
        Random rng = new Random(Utility.getSHA256(hashSeed));

        StarSystem newWorld = new StarSystem();

        newWorld.setMainWorld(planet);

        newWorld.setStars(generateStars(rng, planet.getProfile().getPopulation(), planet.getProfile().getAtmosphere());


//        10. Determine rtar system details.
//A. Svrrem nature Irolitary, binary,
//01 trinaw star ryrtem).
//8. Pllmaw rtar type and rile.
//*/
//
//DMM if main world has population 8+
//or atmosphere 4 .9.
//C. Companion star type and rize.
//D. Cmpanion orbit.
//E. Number of orbits availaMe for
//ash star.
//F. Unavailable, inner, habitable.
//nd mter zones within the rymm.
//G. Captured planets and empty
//abin. -- .
//H. Presence and quantity of gar
//gime.
//I. Pmnce and quantity of
//planetoid bele.
//11. Plke known components.
//A. Place gar giants.
//B. Place planetoid belts.
//C. Plan main world in habitable
//mne.
//12. Generate worlds within system.
//A. Orbit Locat80n.
//6. Size: 2D-2. For orbit 0. DM-5;
//for orbit 1. DM-4; for orbit 2. DM-2.
//If type M rtar. DM-2 for all orbits. If
//~. -
//C. Atmosphere: 2D-7 + size. If
//inner zone. DM-2: if outer rone, DM-4.
//If rim 0 or S, then atmarhere 0. If
//outer zone+2, throw 12 for A.
//D. Hydrographicr: 2D-7 +size. If
//innsr rons. then 0: outer zone, DM-?.
//If sire 1- or S. then hydrographin 0.
//If maphere 1- or A+. DM-4.
//E. Pqulation: 20-2. If inner
//zone, DM-5:if outer rone. DM-3. If not
//atmosphere 0.5.6. or 8, DM-2. If equal
//to or greater than main world. then
//reduce to main world minur 1.
//13. Determine number of ratelliter
//for esh planet, or gar giant in the
//system. Disregard planetoid bele and
//size S woddl.
//A. Plmets: 10-3.
//6. Small gar giants: 2D-4.
//C. Large gar giants: 20.
//14. Generateratelliter within ryrtwn.
//A. Size: Planetaw rize -lD. For
//larv gar giant. 2D-4. For small gar
//giant, 2D-6. If rize 0. use R. If size lerr
//than 0, use S.
//8. Orbit LMim.
//C. Atmosphere: 2D-7 + satellite
//size. If inner zone. DM -4. If outer
//rone. DM -4. If sire I-, then 0. If outer
//zone +2, throw 12 for A.
//D. Hydropraphin: 2D-7 + ratellite
//size. If inner zone. then 0: if wter
//rons. DM-4. If size 0-. then 0. If atmo.
//sphere 1- or A+, DM-4.
//E. Population: 2D-2. If inner
//zone, DM-5: if outer zone. DM -4. If
//atmosphere not 5. 6, or 8. DM-2. If
//sire 4-. DM-2. If ring. than 0. If equal
//to or greater than main world, then
//reduce to main world minur 1.
//15. Determine additional planet and
//satellite characteristin.
//A. Subordinate Government: ID.
//DM +2 if main world government 7t.
//Equalr 6 if main world wvernment 5.
//8. Subordinate Law Level: 10-3
//+main world law level.
//C. Note subordinate fkilitier.
//D. Subordinate Tech Level: Main
//world lewl -1. Equalr main world level
//if rerearch lab or m8litary bare preunt.
//E. Spaceport Type.
//16. Record Itatlstin and data.
//A. Map data on rubsector grid.
//8. Note main world data on
//rubrector data form.
//C. Note complete System data on


        return CreateStarSystem();
    }

    private static Star generateCompanionStar(@Null Random rng, int classMod, int sizeRoll) {
        int rollClass = roll(rng);
        int rollSize = roll(rng);
        Star.StellarClass sClass = M;
        Star.StellarSize sSize = D;

        rollClass += classMod;
        rollSize += sizeRoll;

        switch (rollClass) {

            case 1:
                sClass = B;
                break;
            case 2:
                sClass = A;
                break;
            case 3:
            case 4:
                sClass = F;
                break;
            case 5:
            case 6:
                sClass = G;
            case 7:
            case 8:
                sClass = K;
                break;
        }

        switch (rollSize) {
            case 0:
                sSize = Ia;
                break;
            case 1:
                sSize = Ib;
                break;
            case 2:
                sSize = II;

                break;
            case 3:
                sSize = III;
                break;
            case 4:
                sSize = IV;
                break;
            case 5:
            case 6:
                sSize = D;
                break;
            case 7:
            case 8:
                sSize = V;
                break;
            case 9:
                sSize = VI;
                break;

        }

        return new Star(sClass, sSize);
    }

    private static HashMap<Star.StarPosition, Star> generateStars(@Null Random rng, @Null Integer population, @Null Integer atmosphere) {
        int rollClass = roll(rng);
        int rollSize = roll(rng);
        int star = roll(rng);
        HashMap<Star.StarPosition, Star> map = new HashMap<>();


        if (population > 7 || (atmosphere > 3 && atmosphere < 10)) {
            rollClass += 4;
            rollSize += 4;
        }
        Star.StellarClass sClass = M;
        Star.StellarSize sSize = V;
        switch (rollClass) {
            case 0:
            case 1:
                sClass = B;
                break;
            case 2:
                sClass = A;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                sClass = M;
                break;
            case 8:
                sClass = K;
                break;
            case 9:
                sClass = G;
                break;
            case 10:
            case 11:
            case 12:
                sClass = F;
                break;


        }

        switch (rollSize) {
            case 0:
                sSize = Ia;
                break;
            case 1:
                sSize = Ib;
                break;
            case 2:
                sSize = II;

                break;
            case 3:
                sSize = III;
                break;
            case 4:
                sSize = IV;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                sSize = V;
                break;
            case 11:
                sSize = VI;
                break;
            case 12:
                sSize = D;
                break;


        }

        map.put(PRIMARY, new Star(sClass, sSize));

        if (star > BINARY) {
            map.put(SECONDARY, generateCompanionStar(rng, rollClass, rollSize));
        }

        if (star > TRINARY) {
            map.put(TERTIARY, generateCompanionStar(rng, rollClass, rollSize));
        }
        return map;
    }
}
