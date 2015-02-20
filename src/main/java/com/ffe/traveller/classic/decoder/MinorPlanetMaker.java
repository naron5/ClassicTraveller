package com.ffe.traveller.classic.decoder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.ffe.traveller.util.DiceGenerator.rollDice;
import static com.ffe.traveller.util.DiceGenerator.rollDiceWithModifier;

/**
 * Created by darkmane on 2/19/15.
 */
public class MinorPlanetMaker {
    public static Planet CreateMinorPlanet(Random rng, StarSystem.Zone zone, Planet mainPlanet) {
        Starport port = null;
        MinorPlanet s = new MinorPlanet();
        int size = mainPlanet.getProfile().getDiameter() - rollDice(rng, 1);
        int atmo = rollDiceWithModifier(rng, 2, -7) + size;
        int hydro = rollDiceWithModifier(rng, 2, -7) + size;
        int pop = rollDiceWithModifier(rng, 2, -2);
        int gov = rollDiceWithModifier(rng, 1, 0);
        int law = rollDiceWithModifier(rng, 1, -3) + mainPlanet.getProfile().getLawLevel();
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

        if (mainPlanet.getProfile().getPlanGov() == 6) {
            gov += pop;
        } else if (mainPlanet.getProfile().getPlanGov() >= 7) {
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

        UniversalPlanetaryProfile upp = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(
                port, size, atmo, hydro, pop, gov, law
        );
        s.setProfile(upp);
        s.setTradeClassifications(calculateTradeClassifications(rng, upp, mainPlanet, zone));

        int techLevel = mainPlanet.getProfile().getTechLev() - 1;
        if (s.getTradeClassifications().contains(TradeClassifications.Military)) {
            techLevel = mainPlanet.getProfile().getTechLev();
        }
        int mainWorldAtmosphere = mainPlanet.getProfile().getAtmosphere();
        if (techLevel < 7 && !(mainWorldAtmosphere == 5 || mainWorldAtmosphere == 6 || mainWorldAtmosphere == 8)) {
            techLevel = 7;
        }
        s.getProfile().setTechLev(techLevel);
        return s;
    }

    private static Set<TradeClassifications> calculateTradeClassifications(Random rng, UniversalPlanetaryProfile profile, Planet mainWorld, StarSystem.Zone zone) {
        // Agricultural

        int atmosphere = profile.getAtmosphere();
        int hydro = profile.getHydro();
        int population = profile.getPopulation();
        int planGov = profile.getPlanGov();

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

        int mod = mainWorld.getProfile().getTechLev() >= 10 ? 2 : 0;

        if (rollDiceWithModifier(rng, 2, mod) >= 10) {
            if (mainWorld.getProfile().getTechLev() >= 8 && population > 0) {
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
}
