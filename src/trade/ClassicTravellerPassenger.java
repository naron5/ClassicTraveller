/**
 * 
 */
package trade;

import util.DiceGenerator;
import decoder.Planet;

/**
 * @author markknights
 *
 */
public class ClassicTravellerPassenger {
	//class variables
	boolean debug = false;
	int high, middle, low, dieMod;
	int passOnOffer[] = new int[3];
	
	/**
	 * @param exporter
	 * @param importer
	 * 
	 * Details the availability of passengers needing travel taking into account
	 * the to and from for planets
	 */
	public ClassicTravellerPassenger(Planet from, Planet to){
		high = 0;
		middle = 0;
		low = 0;
		
		if(to.getZone()[0]){
			 middle = -1;
			 low = -1;
		}
		
		getDieMod(from, to);
		passOnOffer = getPassengers(from);
	}

	/**
	 * @return
	 * 
	 * returns a 3 space integer array.  High space 0, middle space 1 and low space 2
	 */
	private int[] getPassengers(Planet from) {
		int[] passengers = new int[3];					// this for the return
		int population = from.getProfile().getPop();	//Planet population for switch
		int temp = 0;									//To ensure it is a positive number
		
		switch(population){
			case 0:
				for(int i = 0; i < passengers.length; i++){
					passengers[i] = 0;
				}
			case 1:
				passengers[0] = 0;
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(1, 6, -2 + dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(2, 6, -6+dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 2: 
				temp = DiceGenerator.rollDiceWithModifier(1, 6, -(DiceGenerator.rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(1, 6, dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(2, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 3:
				temp = DiceGenerator.rollDiceWithModifier(2, 6, -(DiceGenerator.rollDice(2, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(2, 6, -(DiceGenerator.rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(2, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 4:
				temp = DiceGenerator.rollDiceWithModifier(2, 6, -(DiceGenerator.rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(2, 6, -(DiceGenerator.rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 5:
				temp = DiceGenerator.rollDiceWithModifier(2, 6, -(DiceGenerator.rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(2, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 6: 
				temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(2, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(2, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 7:
				temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(2, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 8: 
				temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(4, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 9:
				temp = DiceGenerator.rollDiceWithModifier(3, 6, -(DiceGenerator.rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(3, 6, dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(5, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 10:
				temp = DiceGenerator.rollDiceWithModifier(3, 6, dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = DiceGenerator.rollDiceWithModifier(4, 6, dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = DiceGenerator.rollDiceWithModifier(6, 6, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			default:
				for(int i = 0; i < passengers.length; i++){
					passengers[i] = 0;
				}	
		}
		
		return passengers;
	}

	/**
	 * 
	 */
	private void getDieMod(Planet from, Planet to) {
		if(to.getProfile().getPop() < 5){
			dieMod -= 3;
		}else if(to.getProfile().getPop()>7){
			dieMod += +3;
		}
		
		if(to.getZone()[0]){
			dieMod -= 12;
		}else if(to.getZone()[1]){
			dieMod -= 6;
		}
		
		dieMod += from.getProfile().getTechLev() - to.getProfile().getTechLev();
		
	}
	
	/**
	 * @param s
	 * 
	 * Used to insert debugging comments for myself
	 */
	private void debug(String s){
		if(debug)
			System.out.println();
	}
}
