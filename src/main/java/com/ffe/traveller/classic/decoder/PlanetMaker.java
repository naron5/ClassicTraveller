package com.ffe.traveller.classic.decoder;


import javax.validation.constraints.Null;

import java.io.InputStream;
import java.util.*;

import com.ffe.traveller.classic.decoder.StarSystem.Zone.*;
import org.yaml.snakeyaml.Yaml;

import static com.ffe.traveller.util.DiceGenerator.*;
import static com.ffe.traveller.util.DiceGenerator.rollDiceWithModifier;


/**
 * @author darkmane
 */

public class PlanetMaker {

    private static Map<String, List<Integer>> satelliteOrbits = new HashMap<>();

    /**
     * @param planetName
     * @param hexLocale
     * @param starportType
     * @param planetSize
     * @param planetAtmosphere
     * @param hydroPercent
     * @param population
     * @param planetGovernment
     * @param law
     * @param techLevel
     * @param navalBase
     * @param scoutBase        Generates a fully formed planet.  Hex location is expected but if it is
     *                         not yet placed put a negative number into the hexLocale parameter
     */
    public static Planet CreatePlanet(@Null Random rng, @Null String planetName, @Null Integer hexLocale, @Null Starport starportType,
                                      @Null Integer planetSize, @Null Integer planetAtmosphere, @Null Integer hydroPercent,
                                      @Null Integer population, @Null Integer planetGovernment, @Null Integer law,
                                      @Null Integer techLevel, @Null Boolean navalBase, @Null Boolean scoutBase) {


        UniversalPlanetaryProfile upp = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(starportType,
                planetSize, planetAtmosphere, hydroPercent, population, planetGovernment, law);


        if ((upp.getStarport() == Starport.A || upp.getStarport() == Starport.B)
                && rollDice(1) % 2 == 0) {
            navalBase = true;
        }

        if ((upp.getStarport() == Starport.A || upp.getStarport() == Starport.B
                || upp.getStarport() == Starport.C || upp.getStarport() == Starport.D)
                && rollDice(1) % 2 == 0) {
            scoutBase = true;
        }

        return new Planet(planetName, hexLocale, upp, navalBase, scoutBase);

    }

    /**
     * @param rng
     * @param zone
     * @param mainPlanet
     * @return
     */
    public static Planet CreateGasGiant(Random rng, StarSystem.Zone zone, Planet mainPlanet) {
        Planet gg = null;
        int numberOfMoons;
        if (roll(rng) % 2 == 0) {
            gg = new GasGiant(null, null, Planet.Type.SMALL_GAS_GIANT);
            numberOfMoons = rollDiceWithModifier(rng, 2, -4);
        } else {
            gg = new GasGiant(null, null, Planet.Type.LARGE_GAS_GIANT);
            numberOfMoons = rollDice(rng, 2);
        }
        Map<Integer, Planet> moons = new HashMap<>();

        for (int counter = 0; counter < numberOfMoons; counter++) {
            Planet moon = CreateSatellite(rng, zone, mainPlanet, gg);
            int orbit = lookUpOrbit(rng, (moon.getProfile().getDiameter() ==0));
            moons.put(orbit, moon);

        }

        gg.setSatellites(moons);
        return gg;
    }


    /**
     * @param rng
     * @param star
     * @param orbit
     * @param zone
     * @param mainPlanet
     * @return
     */
    public static Planet CreateMinorPlanet(Random rng, Star star, Integer orbit, StarSystem.Zone zone, Planet mainPlanet) {
        Starport port = null;
        MinorPlanet s = new MinorPlanet();
        int size = rollDiceWithModifier(rng, 2, -2);

        if (orbit <= 2) {
            size -= (5 - orbit);
        }
        if (star.getStellarClass() == Star.StellarClass.M) {
            size -= 2;
        }


        int atmo = rollDiceWithModifier(rng, 2, -7) + size;
        int hydro = rollDiceWithModifier(rng, 2, -7) + size;
        int pop = rollDiceWithModifier(rng, 2, -2);
        int gov = rollDiceWithModifier(rng, 1, 0);
        int law = rollDiceWithModifier(rng, 1, -3) + mainPlanet.getProfile().getLaw_level();
        switch (zone) {
            case INNER:
                atmo -= 2;
                hydro -= 4;
                pop -= 6;
                break;
            case HABITABLE:
                break;
            case OUTER:
                atmo -= 4;
                hydro -= 2;
                pop -= 5;
                break;
        }

        if (size < 1) {
            atmo = 0;
            hydro = 0;
        }

        //if(zone == OUTER && orbit - )

        if (size <= 4) {
            pop -= 2;
        }

        if (!(atmo == 5 || atmo == 6 || atmo == 8)) {
            pop -= 2;
        }

        if (size == 0) {
            pop = 0;
        }

        if (pop < 0) {
            pop = 0;
        }
        if (mainPlanet.getProfile().getGovernment() == 6) {
            gov += pop;
        } else if (mainPlanet.getProfile().getGovernment() >= 7) {
            gov += 1;
        }

        switch (gov) {
            case 1:
                gov = 0;
                break;
            case 2:
                gov = 1;
                break;
            case 3:
                gov = 2;
                break;
            case 4:
                gov = 3;
                break;
            default:
                gov = 6;
                break;
        }

        if (pop == 0) {
            gov = 0;
        }

        if (law < 0 || pop == 0) {
            law = 0;
        }

        if (size == 0) {
            size = Planet.Ring;
        } else if (size < 0) {
            size = Planet.Small;
        }

        if (atmo < 0) {
            atmo = 0;
        }

        if (hydro < 0) {
            hydro = 0;
        }

        UniversalPlanetaryProfile upp = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(
                port, size, atmo, hydro, pop, gov, law
        );
        s.setProfile(upp);
        s.setTradeClassifications(calculateTradeFacilities(rng, upp, mainPlanet, zone));

        int techLevel = mainPlanet.getProfile().getTechnological_level() - 1;
        if (s.getTradeClassifications().contains(TradeClassifications.Military)) {
            techLevel = mainPlanet.getProfile().getTechnological_level();
        }
        int mainWorldAtmosphere = mainPlanet.getProfile().getAtmosphere();
        if (techLevel < 7 && !(mainWorldAtmosphere == 5 || mainWorldAtmosphere == 6 || mainWorldAtmosphere == 8)) {
            techLevel = 7;
        }
        s.getProfile().setTechnological_level(techLevel);

        int numberOfMoons = rollDiceWithModifier(rng, 1, -3);
        Map<Integer, Planet> moons = new HashMap<>();
        for (int counter = 0; counter < numberOfMoons; counter++) {
            Planet moon = CreateSatellite(rng, zone, mainPlanet, s);
            int o = lookUpOrbit(rng, (moon.getProfile().getDiameter() ==0));
            moons.put(o, moon);
        }
        s.setSatellites(moons);
        return s;
    }

    /**
     * @param rng
     * @param zone
     * @param mainPlanet
     * @param parentPlanet
     * @return
     */
    public static Planet CreateSatellite(Random rng, StarSystem.Zone zone, Planet mainPlanet, Planet parentPlanet) {
        Starport port = null;
        MinorPlanet s = new MinorPlanet();
        int size = parentPlanet.getProfile().getDiameter() - rollDice(rng, 1);
        int atmo = rollDiceWithModifier(rng, 2, -7) + size;
        int hydro = rollDiceWithModifier(rng, 2, -7) + size;
        int pop = rollDiceWithModifier(rng, 2, -2);
        int gov = rollDiceWithModifier(rng, 1, 0);
        int law = rollDiceWithModifier(rng, 1, -3) + mainPlanet.getProfile().getLaw_level();

        if (parentPlanet.getPlanetType() == Planet.Type.SMALL_GAS_GIANT) {
            size = rollDiceWithModifier(rng, 2, -6);
        } else if (parentPlanet.getPlanetType() == Planet.Type.LARGE_GAS_GIANT) {
            size = rollDiceWithModifier(rng, 2, -4);
        }

        switch (zone) {
            case INNER:
                atmo -= 4;
                hydro -= 4;
                pop -= 6;
                break;
            case HABITABLE:
                break;
            case OUTER:
                atmo -= 4;
                hydro -= 2;
                pop -= 5;
                break;
        }

        if (size <= 1) {
            atmo = 0;
            hydro = 0;
        }

        if (size <= 4) {
            pop -= 2;
        }

        if (!(atmo == 5 || atmo == 6 || atmo == 8)) {
            pop -= 2;
        }

        if (size == 0) {
            pop = 0;
        }

        if (pop < 0) {
            pop = 0;
        }
        if (mainPlanet.getProfile().getGovernment() == 6) {
            gov += pop;
        } else if (mainPlanet.getProfile().getGovernment() >= 7) {
            gov += 1;
        }

        switch (gov) {
            case 1:
                gov = 0;
                break;
            case 2:
                gov = 1;
                break;
            case 3:
                gov = 2;
                break;
            case 4:
                gov = 3;
                break;
            default:
                gov = 6;
                break;
        }

        if (pop == 0) {
            gov = 0;
        }

        if (law < 0 || pop == 0) {
            law = 0;
        }

        if (size == 0) {
            size = Planet.Ring;
        } else if (size < 0) {
            size = Planet.Small;
        }

        if (atmo < 0) {
            atmo = 0;
        }

        if (hydro < 0) {
            hydro = 0;
        }

        UniversalPlanetaryProfile upp = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(
                port, size, atmo, hydro, pop, gov, law
        );
        s.setProfile(upp);
        s.setTradeClassifications(calculateTradeFacilities(rng, upp, mainPlanet, zone));

        int techLevel = mainPlanet.getProfile().getTechnological_level() - 1;
        if (s.getTradeClassifications().contains(TradeClassifications.Military)) {
            techLevel = mainPlanet.getProfile().getTechnological_level();
        }
        int mainWorldAtmosphere = mainPlanet.getProfile().getAtmosphere();
        if (techLevel < 7 && !(mainWorldAtmosphere == 5 || mainWorldAtmosphere == 6 || mainWorldAtmosphere == 8)) {
            techLevel = 7;
        }
        s.getProfile().setTechnological_level(techLevel);
        return s;
    }

    /**
     * @param rng
     * @param profile
     * @param mainWorld
     * @param zone
     * @return
     */
    private static Set<TradeClassifications> calculateTradeFacilities(Random rng, UniversalPlanetaryProfile profile, Planet mainWorld, StarSystem.Zone zone) {
        // Agricultural

        int atmosphere = profile.getAtmosphere();
        int hydro = profile.getHydro();
        int population = profile.getPopulation();
        int planGov = profile.getGovernment();

        Set<TradeClassifications> tradeClassifications = new HashSet<>();

        // Farming
        if ((atmosphere > 3 && atmosphere < 10) && (hydro > 3 && hydro < 9)
                && population > 2 && zone == StarSystem.Zone.HABITABLE) {
            tradeClassifications.add(TradeClassifications.Farming);
        }

        // Mining
        if (mainWorld.getProfile().getTradeClassifications().contains(TradeClassifications.Industrial) &&
                population > 2) {
            tradeClassifications.add(TradeClassifications.Mining);
        }

        // Colony
        if (planGov == 6 && population >= 5) {
            tradeClassifications.add(TradeClassifications.Colony);
        }

        // Financial
        if ((atmosphere == 6 || atmosphere == 8) && (population > 5 && population < 9)
                && (planGov > 3 && planGov < 10)) {
            tradeClassifications.add(TradeClassifications.Rich);
        } else if ((atmosphere > 1 && atmosphere < 6) && hydro < 4) {
            tradeClassifications.add(TradeClassifications.Poor);
        }

        int mod = mainWorld.getProfile().getTechnological_level() >= 10 ? 2 : 0;

        if (rollDiceWithModifier(rng, 2, mod) >= 10) {
            if (mainWorld.getProfile().getTechnological_level() >= 8 && population > 0) {
                tradeClassifications.add(TradeClassifications.Research);
            }
        }

        if (mainWorld.getProfile().getPopulation() >= 8) {
            mod = 1;
        } else {
            mod = 0;
        }

        if (mainWorld.getProfile().getAtmosphere() == atmosphere) {
            mod += 2;
        }

        if (mainWorld.getNavalBase() || mainWorld.getScoutBase()) {
            mod += 1;
        }

        if (rollDiceWithModifier(rng, 2, mod) > 12) {
            if (population > 0 && !mainWorld.getProfile().getTradeClassifications().contains(TradeClassifications.Poor)) {
                tradeClassifications.add(TradeClassifications.Military);
            }
        }


        return tradeClassifications;
    }

    private static Integer lookUpOrbit(Random rng, boolean isRing){

        if(satelliteOrbits.isEmpty()){
            loadProperties();
        }

        int orbitRoll = rollDice(rng, 2);
        List<Integer> orbits;

        if (orbitRoll <= 7) {
            orbits = satelliteOrbits.get("close");
        } else if (orbitRoll <= 11) {
            orbits = satelliteOrbits.get("far");
        } else {
            orbits = satelliteOrbits.get("extreme");
        }

        orbitRoll = rollDice(rng, 2);

        if(isRing){
            orbits = satelliteOrbits.get("ring");
            orbitRoll = rollDice(rng, 1);
        }

        return orbits.get(orbitRoll);

    }

    @SuppressWarnings("unchecked")
    private static void loadProperties() {
        InputStream input;

        if (satelliteOrbits.isEmpty()) {

            input = Star.class.getResourceAsStream("orbital_distance.yml");

            Yaml yaml = new Yaml();

            Map<String, Object> propertyMap = (Map<String, Object>) yaml.load(input);
            satelliteOrbits = (Map<String, List<Integer>>) propertyMap.get("satellite_orbits");
        }

    }
}
