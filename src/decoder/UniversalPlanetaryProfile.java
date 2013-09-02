/**
 * @author markknights
 * 
 * Bringing Traveller into the Applications world!
 */
package decoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.DiceGenerator;

public class UniversalPlanetaryProfile {
	int planSize, planAtmos, hydro, pop, planGov, lawLevel, techLev;
	static int temp;
	Starport starport;
	boolean navy, scout, gg;
	String description;

	boolean debug = false;
	/*
	 * Agricultural, Non-Agricultural, Industrial, Non-Industrial, Rich, Poor
	 */

	Set<TradeClassifications> tradeClassifications = new HashSet<TradeClassifications>();

	static final String[] LAW = {
			"No prohibitions.",
			"Body pistols undetectable by standard detectors, explosives (bombs, grenades), and "
					+ "poison gas prohibited.",
			"Portable energy weapons (laser carbine, laser rifle) prohibited. Ship's gunnery not affected.",
			"Weapons of a strict military nature (machine guns, automatic rifles) prohibited.",
			"Light assault weapons machineguns)prohibited.",
			"Personal concealable firearms (such as pistols and revolvers) prohibited.",
			"Most firearms (all except shotguns) prohibited. "
					+ "The carrying of any type of weapon openly is discouraged.",
			"Shotguns are prohibited.",
			"Long bladed weapons (all but daggers) "
					+ "are controlled, and open poss- ession is prohibited.",
			"Possession of any weapon outside one's residence is prohibited." };

	public UniversalPlanetaryProfile() {
		starport = getStarport(DiceGenerator.rollDice(2, 6));
		planSize = DiceGenerator.rollDiceWithModifier(2, 6, -2);
		if (planSize == 0) {
			planAtmos = 0;
		} else {
			planAtmos = DiceGenerator.rollDiceWithModifier(2, 6,
					(-7 + planSize));
			if (planAtmos < 0) {
				planAtmos = 0;
			}
		}
		hydro = Math.abs(DiceGenerator.rollDiceWithModifier(2, 6,
				(-7 + planAtmos)));
		pop = DiceGenerator.rollDiceWithModifier(2, 6, -2);
		planGov = Math
				.abs(DiceGenerator.rollDiceWithModifier(2, 6, (-7 + pop)));
		if (planGov > 13) {
			planGov = 13;
		}
		lawLevel = DiceGenerator.rollDiceWithModifier(2, 6, (-7 + planGov));
		if (lawLevel < 0) {
			lawLevel = 0;
		}

		techLev = getTechLevel();

		if ((starport == Starport.A || starport == Starport.B)
				&& DiceGenerator.rollDice(1, 2) % 2 == 0) {
			navy = true;
		}

		if ((starport == Starport.A || starport == Starport.B
				|| starport == Starport.C || starport == Starport.D)
				&& DiceGenerator.rollDice(1, 2) % 2 == 0) {
			scout = true;
		}

		if (DiceGenerator.rollDice(1, 2) % 2 == 0) {
			gg = true;
		}
		determineTradeClass();
		description = printPlanetaryData();
		debug(description);
	}

	public UniversalPlanetaryProfile(Starport starportType, int planetSize,
			int planetAtmosphere, int hydroPercent, int population,
			int planetGovernment, int law, int techLevel, boolean navalBase,
			boolean scoutBase, boolean gasGiant) {
		starport = starportType;
		planSize = planetSize;
		planAtmos = planetAtmosphere;
		hydro = hydroPercent;
		pop = population;
		planGov = planetGovernment;
		lawLevel = law;
		techLev = techLevel;
		navy = navalBase;
		scout = scoutBase;
		gg = gasGiant;

		determineTradeClass();
		description = printPlanetaryData();
		debug(description);
	}

	/**
	 * 
	 */
	private void determineTradeClass() {
		// Agricultural
		if ((planAtmos > 3 && planAtmos < 10) && (hydro > 3 && hydro < 9)
				&& (pop > 4 && pop < 8)) {
			tradeClassifications.add(TradeClassifications.Agricultural);
		} else if (planAtmos < 4 && pop > 5) {
			tradeClassifications.add(TradeClassifications.NonAgricultural);
		}

		// Industrial
		if ((planAtmos < 3 || planAtmos == 4 || planAtmos == 7 || planAtmos == 9)
				&& pop > 8) {
			tradeClassifications.add(TradeClassifications.Industrial);
			;
		} else if (pop < 7) {
			tradeClassifications.add(TradeClassifications.NonIndustrial);
		}

		// Financial
		if ((planAtmos == 6 || planAtmos == 8) && (pop > 5 && pop < 9)
				&& (planGov > 3 && planGov < 10)) {
			tradeClassifications.add(TradeClassifications.Rich);
		} else if ((planAtmos > 1 && planAtmos < 6) && hydro < 4) {
			tradeClassifications.add(TradeClassifications.Poor);
		}

	}

	/**
	 * 
	 */
	private String printPlanetaryData() {

		// Prepare String for output

		StringBuilder planet = new StringBuilder();
		// starport
		planet.append(": ");
		planet.append(getStarportString());

		planet.append("\n");

		// planet size
		planet.append(Integer.toHexString(planSize).toUpperCase());
		planet.append(": ");

		planet.append(getSizeString());
		planet.append("\n");

		// Atmosphere
		planet.append(Integer.toHexString(planAtmos).toUpperCase());
		planet.append(": ");
		planet.append(getPlanAtmosString());
		planet.append("\n");

		// Hydrosphere
		planet.append(Integer.toHexString(hydro).toUpperCase());
		planet.append(": ");

		planet.append(getHydroString());
		planet.append("\n");

		// Population
		planet.append(Integer.toHexString(pop).toUpperCase());
		planet.append(": ");

		planet.append(getPopString());
		planet.append("\n");

		// Government
		planet.append(Integer.toHexString(planGov).toUpperCase());
		planet.append(": ");
		planet.append(getPlanGovString());
		planet.append("\n");
		
		// Law Level
		planet.append(Integer.toHexString(lawLevel).toUpperCase());
		planet.append(": ");

		planet.append(getLawLevelString());
		planet.append("\n");

		// TechLevel
		planet.append(String.format("Tech Level: %d\n", techLev));
		// Naval base
		if (navy) {
			planet.append("Naval base present \n");
		} else {
			planet.append("No naval base present \n");
		}

		// Scout base
		if (scout) {
			planet.append("Scout base present \n");
		} else {
			planet.append("No scout base present \n");
		}

		// Gas Giant
		if (gg) {
			planet.append("Gas giant present \n");
		} else {
			planet.append("No gas giant present \n");
		}

		// Trade classifications
		planet.append("Trade Classifications: ");
		List<String> classifications = new ArrayList<String>();
		if (tradeClassifications.contains(TradeClassifications.Agricultural))
			classifications.add("Agricultural, ");

		if (tradeClassifications.contains(TradeClassifications.NonAgricultural))
			classifications.add("Non-agricultural, ");

		if (tradeClassifications.contains(TradeClassifications.Industrial))
			classifications.add("Industrial, ");

		if (tradeClassifications.contains(TradeClassifications.NonIndustrial))
			classifications.add("Non-Industrial, ");

		if (tradeClassifications.contains(TradeClassifications.Rich))
			classifications.add("Rich.");

		if (tradeClassifications.contains(TradeClassifications.Poor))
			classifications.add("Poor.");

		Iterator<String> iter = classifications.iterator();
		
		while(iter.hasNext())
		{
			planet.append(iter.next());
			if(!iter.hasNext()){
				break;
			}
			planet.append(",");
		}
		planet.append("\n");

		return planet.toString();
	}

	public Set<TradeClassifications> getTradeClassifications() {
		return tradeClassifications;
	}

	public void setTradeClassifications(
			Set<TradeClassifications> tradeClassifications) {
		this.tradeClassifications = tradeClassifications;
	}

	/**
	 * @return
	 */
	private int getTechLevel() {
		int level = 0;

		// starport effects
		switch (starport) {
		case A:
			level += 6;
			break;
		case B:
			level += 4;
			break;
		case C:
			level += 2;
			break;
		case X:
			level -= 4;
			break;
		default:
			level = 0;
		}

		// planetary size effects
		if (planSize < 2) {
			level += +2;
		} else if (planSize > 1 && planSize < 5) {
			level += 1;
		}

		// planetary atmosphere effects
		if (planAtmos < 4 || planAtmos > 9) {
			level += 1;
		}

		// Hydrography percentage effects
		if (hydro == 9) {
			level += 1;
		} else if (hydro == 10) {
			level += 2;
		}

		// population effects
		if (pop > 0 && pop < 6) {
			level += 1;
		} else if (pop == 9) {
			level += 2;
		} else if (pop == 10) {
			level += 4;
		}

		// Government effects
		if (planGov == 0 || planGov == 5) {
			level += 1;
		} else if (planGov == 13) {
			level -= 2;
		}
		level += DiceGenerator.rollDice(1, 6);

		return level;
	}

	/**
	 * @param i
	 * @return
	 */
	private Starport getStarport(int randRoll) {
		Starport result = Starport.none;
		switch (randRoll) {
		case 2:
		case 3:
		case 4:
			result = Starport.A;
			break;
		case 5:
		case 6:
			result = Starport.B;
			break;
		case 7:
		case 8:
			result = Starport.C;
			break;
		case 9:
			result = Starport.D;
			break;
		case 10:
		case 11:
			result = Starport.E;
			break;
		case 12:
			result = Starport.X;
			break;
		default:
			result = Starport.none;

		}
		return result;
	}

	public int getPlanSize() {
		return planSize;
	}

	public String getSizeString() {

		return String.format((String) propertyMap.get("PlanetSize"),
				planSize * 1000, planSize * 1600);

	}

	public void setPlanSize(int planSize) {
		this.planSize = planSize;
	}

	public int getPlanAtmos() {
		return planAtmos;
	}

	public String getPlanAtmosString() {

		@SuppressWarnings("unchecked")
		ArrayList<String> atmosArray = (ArrayList<String>) propertyMap.get("Atmosphere");

		return (String)atmosArray.get(planAtmos);

	}

	public void setPlanAtmos(int planAtmos) {
		this.planAtmos = planAtmos;
	}

	public int getHydro() {
		return hydro;
	}

	public String getHydroString() {
		String rv;
		if (hydro < 1) {
			rv = "No free standing water. Desert.";
		} else if (hydro > 9) {
			rv = "No land masses. Water World.";
		} else {

			rv = String.format("%d%% Water", hydro * 10);
		}
		return rv;
	}

	public void setHydro(int hydro) {
		this.hydro = hydro;
	}

	public int getPop() {
		return pop;
	}

	public String getPopString() {

		@SuppressWarnings("unchecked")
		ArrayList<String> popArray = (ArrayList<String> ) propertyMap.get("Population");
		return popArray.get(pop);

	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public int getPlanGov() {
		return planGov;
	}

	public String getPlanGovString() {
		@SuppressWarnings("unchecked")		
		ArrayList<String> govArray = (ArrayList<String>) propertyMap.get("Government");
		return govArray.get(planGov);

	}

	public void setPlanGov(int planGov) {
		this.planGov = planGov;
	}

	public int getLawLevel() {
		return lawLevel;
	}

	public String getLawLevelString() {

		@SuppressWarnings("unchecked")
		ArrayList<String> lawArray = (ArrayList<String>) propertyMap.get("LawLevel");
		return lawArray.get(lawLevel);

	}

	public void setLawLevel(int lawLevel) {
		this.lawLevel = lawLevel;
	}

	public int getTechLev() {
		return techLev;
	}

	public void setTechLev(int techLev) {
		this.techLev = techLev;
	}

	public Starport getStarport() {
		return starport;
	}

	public String getStarportString() {

		String rv;

		switch (starport) {
		case A:
			rv = "Excellent quality installation. Refined fuel available. Annual maintenance overhaul available. Shipyard capable of constructing starships and non-starships present. Naval base and or scout base may be present.";
			break;
		case B:

			rv = "Good quality installation. Refined fuel available. Annual maintenance overhaul available. Shipyard capable of constructing non-starships present. Naval base and/or scout base may be present.";
			break;
		case C:
			rv = "Routine quality installation. Only unrefined fuel available. Reasonable repair facilities present. Scout base may be present.";
			break;
		case D:
			rv = "Poor quality installation. Only unrefined fuel available. No repair or shipyard facilities present. Scout base may be present.";
			break;

		case E:
			rv = "Frontier installation. Essentially a marked spot of bedrock with no fuel, facilities, or bases present.";
			break;

		case none:
			rv = "No starport. No provision is made for any ship landings.";
			break;
		default:
			rv = "";
			break;
		}
		return rv;
	}

	public void setStarport(Starport starport) {
		this.starport = starport;
	}

	public boolean isNavy() {
		return navy;
	}

	public void setNavy(boolean navy) {
		this.navy = navy;
	}

	public boolean isScout() {
		return scout;
	}

	public void setScout(boolean scout) {
		this.scout = scout;
	}

	public boolean isGg() {
		return gg;
	}

	public void setGg(boolean gg) {
		this.gg = gg;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * @param string
	 */
	private void debug(String string) {
		if (debug) {
			System.out.println(string);
		}
	}
}
