package com.ffe.traveller.classic.decoder;

import com.ffe.traveller.util.Utility;
import com.google.common.collect.ImmutableSet;

import javax.validation.constraints.Null;
import java.util.*;

import static com.ffe.traveller.classic.decoder.Star.StellarClass.*;
import static com.ffe.traveller.classic.decoder.Star.StellarSize.*;
import static com.ffe.traveller.classic.decoder.Star.StarPosition.*;
import static com.ffe.traveller.classic.decoder.StarSystem.*;
import static com.ffe.traveller.util.DiceGenerator.*;


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


//        11. Place known components.
//                A. Place gas giants.
//        B. Place planetoid belts.
//                C. Place main world in habitable
//        zone.
        StarSystem newWorld = new StarSystem();
        int orbitRoll = roll(rng);

        newWorld.setMainWorld(planet);

//        10. Determine star system details.
//        A. System nature (solitary, binary,
//                or trinary star system).
//        B. Primary star type and size.
//                DM+4 if main world has population 8+
//                or atmosphere 4 - 9.
//        C. Companion star type and size.
//                D. Companion orbit.
//                E. Number of orbits available for
//        each star.
        newWorld.setStars(generateStars(rng, planet.getProfile().getPopulation(), planet.getProfile().getAtmosphere()));

//        F. Unavailable, inner, habitable,
//                and outer zones within the system.
        switch (newWorld.getStars().get(PRIMARY).getStarSize()) {
            case III:
                orbitRoll += 4;
            case Ia:
            case Ib:
            case II:
                orbitRoll += 8;
        }
        switch (newWorld.getStars().get(PRIMARY).getAClass()) {
            case M:
                orbitRoll -= 4;
            case K:
                orbitRoll -= 2;
        }

        int orbits = orbitRoll > 0 ? orbitRoll : 0;

        newWorld.setMaxOrbits(orbits);


        Set<Integer> empty = new HashSet<>();

        int emptyRoll = rollDice(rng, 1);
        int numberEmptyRoll = rollDice(rng, 1);
        if (newWorld.getStars().get(PRIMARY).getAClass() == B ||
                newWorld.getStars().get(PRIMARY).getAClass() == A) {
            emptyRoll += 1;
            numberEmptyRoll += 1;
        }

        int numberEmpty = 0;

        if (emptyRoll >= 4) {
            switch (numberEmptyRoll) {
                case 1:
                case 2:
                    numberEmpty = 1;
                case 3:
                    numberEmpty = 2;
                default:
                    numberEmpty = 3;
            }
        }

        while (empty.size() < numberEmpty) {
            int emptyOrbit = roll(rng);
            if (emptyOrbit < newWorld.getMaxOrbits()) {
                empty.add(emptyOrbit);
            }
        }


        Set<Integer> availableOrbits = new HashSet(newWorld.getHabitableOrbits());
        availableOrbits.addAll(newWorld.getOuterOrbits());
        availableOrbits.removeAll(empty);

//                G. Captured planets and empty
//        orbits.
//                H. Presence and quantity of gas
//        giants.
        int gasGiants = roll(rng);
        int numberOfGasGiants = 0;

        if (gasGiants < 10) {
            int numberGGRoll = roll(rng);

            switch (numberGGRoll) {
                case 1:
                case 2:
                case 3:
                    numberOfGasGiants = 1;
                    break;
                case 4:
                case 5:
                    numberOfGasGiants = 2;
                    break;
                case 6:
                case 7:
                    numberOfGasGiants = 3;
                    break;
                case 8:
                case 9:
                case 10:
                    numberOfGasGiants = 4;
                    break;
                case 11:
                case 12:
                    numberOfGasGiants = 5;
                    break;
            }

            // Place Gas Giants
            numberOfGasGiants = numberOfGasGiants > newWorld.getMaxOrbits() ? newWorld.getMaxOrbits() : numberOfGasGiants;


            for (int counter = 0; counter < numberOfGasGiants; counter++) {
                List<Integer> orbitSet = new ArrayList<>();
                if (availableOrbits.isEmpty()) {
                    availableOrbits.addAll(newWorld.getInnerOrbits());
                }
                for (Integer orbit : availableOrbits) {
                    for (int counter2 = 0; counter2 < orbit; counter2++) {
                        orbitSet.add(orbit);
                    }
                }

                int listIndex = rng.nextInt(orbitSet.size());
                int orbitNum = orbitSet.get(listIndex);
                Planet gg = new GasGiant();
                newWorld.getOrbits().put(orbitNum, gg);
                availableOrbits.remove(orbitNum);

            }
        }

//                I. Presence and quantity of
//        planetoid belts.
        int planetoidBelt = roll(rng);
        planetoidBelt -= numberOfGasGiants;
        planetoidBelt = planetoidBelt < 0 ? 0 : planetoidBelt;
        if (planetoidBelt < 7) {

            int numberPBRoll = roll(rng);
            int numberOfPlanetoidBelts = 0;
            numberPBRoll = (numberPBRoll - numberOfGasGiants < 0 ? 0 : numberPBRoll - numberOfGasGiants);

            switch (numberPBRoll) {
                case 0:
                    numberOfPlanetoidBelts = 3;
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    numberOfPlanetoidBelts = 2;
                    break;

                default:
                    numberOfPlanetoidBelts = 1;
                    break;
            }

            numberOfPlanetoidBelts = numberOfPlanetoidBelts > (newWorld.getMaxOrbits() - numberOfGasGiants) ?
                    (newWorld.getMaxOrbits() - numberOfGasGiants) : numberOfPlanetoidBelts;

            // Place planetoid belts
            for (int counter = 0; counter < numberOfPlanetoidBelts; counter++) {
                List<Integer> orbitList = new ArrayList<>();
                if (availableOrbits.isEmpty()) {
                    availableOrbits.addAll(newWorld.getInnerOrbits());
                }
                for (Integer orbit : availableOrbits) {
                    int probibility = orbit;
                    if (newWorld.getOrbits().containsKey(orbit + 1) &&
                            (newWorld.getOrbits().get(orbit + 1).getPlanetType() == Planet.Type.LARGE_GAS_GIANT ||
                                    newWorld.getOrbits().get(orbit + 1).getPlanetType() == Planet.Type.SMALL_GAS_GIANT)) {
                        probibility *= 2;

                    }
                    for (int counter2 = 0; counter2 < probibility; counter2++) {
                        orbitList.add(orbit);
                    }
                }

                int listIndex = rng.nextInt(orbitList.size());
                int orbitNum = orbitList.get(listIndex);
                Planet pb = PlanetMaker.CreatePlanet(null, null, null, 0, null, null, null, null, null, null, null, null);
                newWorld.getOrbits().put(orbitNum, pb);
                availableOrbits.remove(orbitNum);
            }

        }


        // Place Mainworld
        Set<Integer> hz = newWorld.calculateHabitableZone(planet);
        Set<Integer> all = new HashSet();
        all.addAll(newWorld.getInnerOrbits());
        all.addAll(newWorld.getHabitableOrbits());
        all.addAll(newWorld.getOuterOrbits());

        List<Integer> unoccupied = new ArrayList();
        hz.removeAll(newWorld.getOrbits().keySet());

        if (hz.isEmpty()) {
            hz = ImmutableSet.copyOf(all);
        }

        hz.removeAll(newWorld.getOrbits().keySet());

        Boolean mainWorldIsSatellite = hz.isEmpty();
        if (mainWorldIsSatellite) {
            List<Planet> gasGiantList = new ArrayList<>();
            for (Planet p : newWorld.getOrbits().values()) {
                if (p.getPlanetType() == Planet.Type.LARGE_GAS_GIANT) {
                    p.createOrbits(roll(rng));
                }
                if (p.getPlanetType() == Planet.Type.SMALL_GAS_GIANT) {
                    gasGiantList.add(p);
                }
            }

            Planet centralPlanet = gasGiantList.get(rng.nextInt(gasGiantList.size()));
            int numberOfSatelliteOrbits = 0;


        } else {
            List<Integer> habitableList = new ArrayList<>(hz);
            Integer mainWorldOrbit = habitableList.get(rng.nextInt(hz.size()));
            newWorld.getOrbits().put(mainWorldOrbit, newWorld.getMainWorld());
        }

        Set<Integer> remainingOrbits = new HashSet<>();

        remainingOrbits.addAll(newWorld.getInnerOrbits());
        remainingOrbits.addAll(newWorld.getHabitableOrbits());
        remainingOrbits.addAll(newWorld.getOuterOrbits());
        remainingOrbits.removeAll(newWorld.getOrbits().keySet());


        for (Integer orbit : remainingOrbits) {
            Zone z = Zone.INNER;
            if (newWorld.getHabitableOrbits().contains(orbit)) {
                z = Zone.HABITABLE;
            }
            if (newWorld.getOuterOrbits().contains(orbit)) {
                z = Zone.OUTER;
            }
            newWorld.getOrbits().put(orbit, MinorPlanetMaker.CreateMinorPlanet(rng, z, newWorld.getMainWorld()));

        }


        return newWorld;
    }

    private static Star generateCompanionStar(@Null Random rng, int classMod, int sizeRoll, Star.StarPosition position) {
        int rollClass = roll(rng);
        int rollSize = roll(rng);
        int rollOrbit = roll(rng);
        int orbitRoll = roll(rng);

        int orbit = CENTER;
        Star.StellarClass sClass = M;
        Star.StellarSize sSize = D;
        int orbits = 0;

        rollClass += classMod;
        rollSize += sizeRoll;
        rollOrbit += position == TERTIARY ? 4 : 0;

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

        if (rollOrbit < 4) {
            orbit = NEAR_ORBIT;
        } else if (rollOrbit == 12) {
            orbit = FAR_ORBIT;
        } else {
            orbit = rollOrbit - 3;
            if (rollOrbit > 6) {
                orbit += roll(rng);
            }
        }

        switch (sSize) {
            case III:
                orbitRoll += 4;
            case Ia:
            case Ib:
            case II:
                orbitRoll += 8;
        }
        switch (sClass) {
            case M:
                orbitRoll -= 4;
            case K:
                orbitRoll -= 2;
        }

        orbits = orbitRoll > 0 ? orbitRoll : 0;


        return new Star(sClass, sSize, rollOrbit);
    }

    private static HashMap<Star.StarPosition, Star> generateStars(@Null Random rng, @Null Integer population, @Null Integer atmosphere) {
        int rollClass = roll(rng);
        int rollSize = roll(rng);
        int star = roll(rng);
        int orbitRoll = roll(rng);
        HashMap<Star.StarPosition, Star> map = new HashMap<>();


        if (population > 7 || (atmosphere > 3 && atmosphere < 10)) {
            rollClass += 4;
            rollSize += 4;
        }
        Star.StellarClass sClass = M;
        Star.StellarSize sSize = V;
        int orbits = 0;

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


        map.put(PRIMARY, new Star(sClass, sSize, CENTER));

        if (star > BINARY) {
            map.put(SECONDARY, generateCompanionStar(rng, rollClass, rollSize, SECONDARY));
        }

        if (star > TRINARY) {
            map.put(TERTIARY, generateCompanionStar(rng, rollClass, rollSize, TERTIARY));
        }
        return map;
    }

}
