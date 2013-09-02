/**
 * 
 */
package trade;

import java.util.Random;

import util.DiceGenerator;

import decoder.Planet;


/**
 * @author markknights
 *
 */
public class ClassicCargoTrade {
	//class variables
	boolean debug = false;
	int major, minor, incidental, dieMod;
	int cargoMult[] = {10, 5};
	int cargoOnOffer[] = new int[3];
	Random generator = new Random();
	DiceGenerator sixer = new DiceGenerator();
	
	/**
	 * @param from
	 * @param to
	 * 
	 * Create for cargo trade
	 */
	public ClassicCargoTrade(Planet from, Planet to){
		
		major = 0;
		minor = 0;
		incidental = 0;
		
		if(from.getZone()[0]){
			 major = -1;
			 minor = -1;
			 incidental = -1;
		 }else if(from.getZone()[1]){
			 major = -1;
		 }
		
		getDieMod(from, to);
		cargoOnOffer = getCargoTonnage(from);
	}
	
	
	/**
	 * @return
	 * returns 3 position integer array advising amount of tonnage in cargo for Major at 0, Minor at 1 and 
	 * incidental at 2
	 */
	private int[] getCargoTonnage(Planet from) {
		int[] cargoAmount = new int[3];				//for return type
		int population = from.getProfile().getPop();//for population switch statement
		
		//Switch statement based on population of planet for cargo availability
		switch(population){
		case 0:
			for(int i = 0; i < cargoAmount.length; i++){
				cargoAmount[i] = 0;
			}
		case 1:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (-4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (-4 + dieMod)) * cargoMult[1];
			}
			cargoAmount[2] = 0;
			break;
		case 2: 
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (-2 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (-1 + dieMod)) * cargoMult[1];
			}
			cargoAmount[2] = 0;
			break;
		case 3:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (-1 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, dieMod) * cargoMult[1];
			}
			cargoAmount[2] = 0;
			break;
		case 4:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, dieMod) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (1 + dieMod)) * cargoMult[1];
			}
			cargoAmount[2] = 0;
			break;
		case 5:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (population - 4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (population - 3 + dieMod)) * cargoMult[1];
			}
			cargoAmount[2] = 0;
			break;
		case 6: case 7:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			if(incidental != -1){
				cargoAmount[2] = DiceGenerator.rollDiceWithModifier(1, 6, (-3 + dieMod));
			}
			break;
		case 8: case 9:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			if(incidental != -1){
				cargoAmount[2] = DiceGenerator.rollDiceWithModifier(1, 6, (-2 + dieMod));
			}
			break;
		case 10:
			if(major != -1){
				cargoAmount[0] = DiceGenerator.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = DiceGenerator.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			if(incidental != -1){
				cargoAmount[2] = DiceGenerator.rollDiceWithModifier(1, 6, dieMod);
			}
			break;
		default:
			for(int i = 0; i < cargoAmount.length; i++){
				cargoAmount[i] = 0;
			}	
		}
		
		//make sure there are no minus cargo results
		for(int i = 0; i < cargoAmount.length; i++){
			if(cargoAmount[i] < 0){
				cargoAmount[i] = 0;
			}
		}
		
		//return
		return cargoAmount;
	}


	/**
	 * Determines the Die Modifier for the check based on destination world
	 */
	private void getDieMod(Planet from, Planet to) {
		if(to.getProfile().getPop() < 5){
			dieMod -= 4;
		}else if(to.getProfile().getPop()>7){
			dieMod += 1;
		}
		
		dieMod += from.getProfile().getTechLev() - to.getProfile().getTechLev();
		
	}


	/**
	 * @param s
	 * 
	 * Used to insert debugging comments for myself
	 */
	@SuppressWarnings("unused")
	private void debug(String s){
		if(debug)
			System.out.println();
	}
}
