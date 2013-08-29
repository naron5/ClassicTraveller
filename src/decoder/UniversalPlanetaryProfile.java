/**
 * 
 */
package decoder;

import java.util.Random;

import dieRoller.DiceGenerator;

/**
 * @author markknights
 * 
 * Bringing Traveller into the Applications world!
 */
public class UniversalPlanetaryProfile {
	int planSize, planAtmos, hydro, pop, planGov, lawLevel, techLev;
	static int temp;
	char starport;
	boolean navy, scout, gg;
	String description;
	DiceGenerator sixer = new DiceGenerator();
	boolean debug = false;
	boolean[] tradeClassifications = new boolean[6]; //In order, Agricultural, Non-Agricultural, Industrial, Non-Industrial, Rich, Poor
	
	
	public UniversalPlanetaryProfile(){
		starport = getStarport(sixer.rollDice(2, 6));
		planSize = sixer.rollDiceWithModifier(2, 6, -2);
		if(planSize == 0){
			planAtmos = 0;
		}else{
			planAtmos = sixer.rollDiceWithModifier(2, 6, (-7+planSize));
			if(planAtmos < 0){
				planAtmos = 0;
			}
		}
		hydro = Math.abs(sixer.rollDiceWithModifier(2, 6, (-7+planAtmos)));
		pop = sixer.rollDiceWithModifier(2, 6, -2);
		planGov = Math.abs(sixer.rollDiceWithModifier(2, 6, (-7+pop)));
		if(planGov > 13){
			planGov = 13;
		}
		lawLevel = sixer.rollDiceWithModifier(2, 6, (-7+planGov));
		if(lawLevel < 0){
			lawLevel = 0;
		}
		
		techLev = getTechLevel();
		
		if(sixer.rollDice(1, 2)%2 == 0 && (starport == 'A' || starport == 'B')){
			navy = true;
		}
		
		if(sixer.rollDice(1, 2)%2 == 0 && (starport == 'A' || starport == 'B' || starport == 'C' || starport == 'D')){
			scout = true;
		}
		
		if(sixer.rollDice(1, 2)%2 == 0){
			gg = true;
		}
		determineTradeClass();
		description = printPlanetaryData();
		debug(description);
	}
	
	public UniversalPlanetaryProfile(char starportType, int planetSize, int planetAtmosphere, int hydroPercent, int population,
			int planetGovernment, int law, int techLevel, boolean navalBase, boolean scoutBase, boolean gasGiant){
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
		//Agricultural
		if((planAtmos > 3 && planAtmos < 10) && (hydro > 3 && hydro < 9) && (pop > 4 && pop < 8)){
			tradeClassifications[0] = true;
		}else if(planAtmos < 4 && pop > 5){
			tradeClassifications[1] = true;
		}
		
		//Industrial
		if((planAtmos < 3 || planAtmos == 4 || planAtmos == 7 || planAtmos == 9) && pop > 8){
			tradeClassifications[2] = true;
		}else if(pop < 7){
			tradeClassifications[3] = true;
		}
		
		//Financial
		if((planAtmos == 6 || planAtmos == 8) && (pop > 5 && pop <9) && (planGov > 3 && planGov < 10)){
			tradeClassifications[4] = true;
		}else if((planAtmos > 1 && planAtmos < 6) && hydro < 4){
			tradeClassifications[5] = true;
		}
		
	}

	
	/**
	 * 
	 */
	private String printPlanetaryData() {
		final String[] STARPORTDESC = {"Excellent quality installation. Refined fuel available. Annual maintenance overhaul available. " +
				"Shipyard capable of constructing starships and non-starships present. Naval base and or scout base may be present.",
				"Good quality installation. Refined fuel available. Annual maintenance overhaul available." + 
				" Shipyard capable of constructing non-starships present. Naval base and/or scout base may be present.", "Routine " +
				"quality installation. Only unrefined fuel available. Reasonable repair facilities present. Scout base may be present.",
				"Poor quality installation. Only unrefined fuel available. No repair or shipyard facilities present. Scout base may be" +
				" present.", "Frontier installation. Essentially a marked spot of bedrock with no fuel, facilities, or bases " +
				"present.","No starport. No provision is made for any ship landings."};
		final String[] SIZE = {"AsteroidIPlanetoid Belt.", "1000 miles (1600 km).", "2000 miles (3200 km).", "3000 miles (4800km).",
				"4000 miles (6400 km).", "5000 miles (8000 km).", "6000 miles (9600 km).", "7000 miles (11200km).", 
				"8000 miles (12800 km).", "9000 miles (14400 km).", "10000 miles (16000 km)."};
		final String[] ATMOSDESC = {"No atmosphere.", "Trace.", "Very thin, tainted.", "Very thin.", "Thin, tainted.", "Thin.",
				"Standard", "Standard, tainted", "Dense.", "Dense, tainted.", "Exotic.", "Corrosive.", "Insidious."};
		final String[] HYDROGRAPH = {"No free standing water. Desert.", "10% Water.", "20% Water.", "30% Water.", "40% Water.", 
				"50% Water.", "60% Water.", "70% Water.", "80% Water.", "90% Water.", "No land masses. Water World."};
		final String[] POPULATION = {"No inhabitants.", "Tens of inhabitants.", "Hundreds of inhabitants.", "Thousands of inhabitants.", 
				"Tens of thousands.", "Hundreds of thousands.", "Millions of inhabitants.", "Tens of millions.", 
				"Hundreds of millions.", "Billions of inhabitants.", "Tens of billions."};
		final String[] GOVERNMENT = {"No government structure. In many cases, family bonds predominate.", "Company/Corporation. " +
				"Government by a company managerial elite; citizens are company employees.", "Participating Democracy. Government " +
				"by advice and consent of the citizen.", "Self-Perpetuating Oligarchy. Government by a restricted minority, with " +
				"little or no input from the masses.", "Representative Democracy. Government by elected representatives.", "Feudal " +
				"Technocracy. Government by specific individuals for those who agree to be ruled. Relationships are based on the " +
				"performance of technical activities which are mutually beneficial.", "Captive Government. Government by an imposed " +
				"leadership answerable to an outside group. A colony or conquered area.", "Balkanization. No central ruling authority " +
				"exists; rival governments compete for control.", "Civil Service Bureaucracy. Government by agencies employing " +
				"individuals selected for their expertise.", "Impersonal Bureaucracy. Government by agencies which are insulated " +
				"from the governed.", "Charismatic Dictator. Government by a single leader enjoying the confidence of the citizens.", 
				"Non-Charismatic Leader. A previous charismatic dictatar has been replaced by a leader through normal channels.", 
				"Charismatic Oligarchy. Government by a select group, organization, or class enjoying the overwhelming confidence of " +
				"the citizenry.", "Religious Dictatorship. Government by a religious organization without regard to the specific needs " +
				"of the citizenry."};
		final String[] LAW = {"No prohibitions.", "Body pistols undetectable by standard detectors, explosives (bombs, grenades), and " +
				"poison gas prohibited.", "Portable energy weapons (laser carbine, laser rifle) prohibited. Ship's gunnery not affected.",
				"Weapons of a strict military nature (machine guns, automatic rifles) prohibited.", "Light assault weapons machineguns)prohibited.",
				"Personal concealable firearms (such as pistols and revolvers) prohibited.", "Most firearms (all except shotguns) prohibited. " +
				"The carrying of any type of weapon openly is discouraged.", "Shotguns are prohibited.", "Long bladed weapons (all but daggers) " +
				"are controlled, and open poss- ession is prohibited.", "Possession of any weapon outside one's residence is prohibited."};
		boolean flag = false;
		int count = 0;
		
		//Prepare String for output
		
		String planet = new String("");
		
		//starport
		planet += starport + ": ";
		if(starport == 'A'){
			planet += STARPORTDESC[0] + "\n";
		}else if(starport == 'B'){
			planet += STARPORTDESC[1] + "\n";
		}else if(starport == 'C'){
			planet += STARPORTDESC[2] + "\n";
		}else if(starport == 'D'){
			planet += STARPORTDESC[3] + "\n";
		}else if(starport == 'E'){
			planet += STARPORTDESC[4] + "\n";
		}else{
			planet += STARPORTDESC[5] + "\n";
		}
		
		//planet size
		planet += Integer.toHexString(planSize).toUpperCase() + ": ";
		
		do{
			if(count == planSize){
				if(count >= SIZE.length){
					count = SIZE.length - 1;
				}
				planet += SIZE[count] + "\n";
				flag = true;
			}
			count++;
		}while(!flag || count < SIZE.length);
		
		//reset reusable variables
		flag = false;
		count = 0;
		
		//Atmosphere
		planet += Integer.toHexString(planAtmos).toUpperCase() + ": ";
		
		do{
			if(count == planSize){
				if(count >= ATMOSDESC.length){
					count = ATMOSDESC.length - 1;
				}
				planet += ATMOSDESC[count] + "\n";
				flag = true;
			}
			count++;
		}while(!flag || count < ATMOSDESC.length);
		
		//reset reusable variables
		flag = false;
		count = 0;
		
		//Hydrosphere
		planet += Integer.toHexString(hydro).toUpperCase() + ": ";

		do{
			if(count == hydro){
				if(count >= HYDROGRAPH.length){
					count = HYDROGRAPH.length - 1;
				}
				planet += HYDROGRAPH[count] + "\n";
				flag = true;
			}
			count++;
		}while(!flag || count < HYDROGRAPH.length);
		
		//reset reusable variables
		flag = false;
		count = 0;
		
		//Population
		planet += Integer.toHexString(pop).toUpperCase() + ": ";

		do{
			if(count == pop){
				if(count >= POPULATION.length){
					count = POPULATION.length - 1;
				}
				planet += POPULATION[count] + "\n";
				flag = true;
			}
			count++;
		}while(!flag || count < POPULATION.length);
		
		//reset reusable variables
		flag = false;
		count = 0;
		
		//Government
		planet += Integer.toHexString(planGov).toUpperCase() + ": ";

		do{
			if(count == planGov){
				if(count >= GOVERNMENT.length){
					count = GOVERNMENT.length - 1;
				}
				planet += GOVERNMENT[count] + "\n";
				flag = true;
			}
			count++;
		}while(!flag || count < GOVERNMENT.length);
		
		//reset reusable variables
		flag = false;
		count = 0;
		
		//Law Level
		planet += Integer.toHexString(lawLevel).toUpperCase() + ": ";

		do{
			if(count == lawLevel){
				if(count >= LAW.length){
					count = LAW.length - 1;
				}
				planet += LAW[count] + "\n";
				flag = true;
			}
			count++;
		}while(!flag || count < LAW.length);
		
		//TechLevel
		planet += "Tech Level: " + techLev + "\n";
		
		//Naval base
		if(navy){
			planet += "Naval base present \n";
		}else{
			planet += "No naval base present \n"; 
		}
		
		//Scout base
		if(scout){
			planet += "Scout base present \n";
		}else{
			planet += "No scout base present \n"; 
		}
		
		//Gas Giant
		if(gg){
			planet += "Gas giant present \n";
		}else{
			planet += "No gas giant present \n"; 
		}
		
		//Trade classifications
		planet += "Trade Classifications: ";
		for(int i = 0; i < tradeClassifications.length; i++){
			if(i == 0 && tradeClassifications[i]){
				planet += "Agricultural, ";
			}else if(i == 1 && tradeClassifications[i]){
				planet += "Non-agricultural, ";
			}else if(i == 2 && tradeClassifications[i]){
				planet += "Industrial, ";
			}else if(i == 3 && tradeClassifications[i]){
				planet += "Non-Industrial, ";
			}else if(i == 4 && tradeClassifications[i]){
				planet += "Rich.";
			}else if(tradeClassifications[i]){
				planet += "Poor.";
			}
		}
		planet += "\n";
		
		return planet;
	}

	
	public boolean[] getTradeClassifications() {
		return tradeClassifications;
	}

	public void setTradeClassifications(boolean[] tradeClassifications) {
		this.tradeClassifications = tradeClassifications;
	}

	/**
	 * @return
	 */
	private int getTechLevel() {
		int level=0;
		
		//starport effects
		switch(starport){
			case 'A': 
				level += 6;
			break;
			case 'B':
				level += 4;
				break;
			case 'C':
				level += 2;
				break;
			case 'X': 
				level -= 4;
				break;
			default:
				level = 0;	
		}
		
		//planetary size effects
		if(planSize < 2){
			level += +2;
		}else if(planSize > 1 && planSize < 5){
			level += 1;
		}
		
		//planetary atmosphere effects
		if(planAtmos < 4 || planAtmos > 9){
			level += 1;
		}
		
		//Hydrography percentage effects
		if(hydro == 9){
			level += 1;
		}else if (hydro == 10){
			level += 2;
		}
		
		//population effects
		if(pop > 0 && pop < 6){
			level += 1;
		}else if(pop == 9){
			level += 2;
		}else if(pop == 10){
			level += 4;
		}
		
		//Government effects
		if(planGov == 0 || planGov == 5){
			level += 1;
		}else if (planGov == 13){
			level -= 2;
		}
		level += sixer.rollDice(1, 6);
		
		return level;
	}


	/**
	 * @param i
	 * @return
	 */
	private char getStarport(int randRoll) {
		char result = '.';
		switch(randRoll){
			case 2: case 3: case 4:
				result = 'A';
				break;
			case 5: case 6:
				result = 'B';
				break;
			case 7: case 8:
				result = 'C';
				break;
			case 9:
				result = 'D';
				break;
			case 10: case 11:
				result = 'E';
				break;
			case 12:
				result = 'X';
				break;
			default:
				result = '.';
				
		}
		return result;
	}
	
	public int getPlanSize() {
		return planSize;
	}

	public void setPlanSize(int planSize) {
		this.planSize = planSize;
	}

	public int getPlanAtmos() {
		return planAtmos;
	}

	public void setPlanAtmos(int planAtmos) {
		this.planAtmos = planAtmos;
	}

	public int getHydro() {
		return hydro;
	}

	public void setHydro(int hydro) {
		this.hydro = hydro;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public int getPlanGov() {
		return planGov;
	}

	public void setPlanGov(int planGov) {
		this.planGov = planGov;
	}

	public int getLawLevel() {
		return lawLevel;
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

	public char getStarport() {
		return starport;
	}

	public void setStarport(char starport) {
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
		if(debug){
			System.out.println(string);
		}
	}
}
