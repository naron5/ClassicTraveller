/**
 * 
 */
package com.ffe.traveller.classic.trade;


import com.ffe.traveller.classic.decoder.StarSystem;

import static com.ffe.traveller.util.DiceGenerator.*;

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
	 * @param from
	 * @param to
	 * 
	 * Details the availability of passengers needing travel taking into account
	 * the to and from for planets
	 */
	public ClassicTravellerPassenger(StarSystem from, StarSystem to){
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
	private int[] getPassengers(StarSystem from) {
		int[] passengers = new int[3];					// this for the return
		int population = from.getMainWorld().getProfile().getPop();	//StarSystem population for switch
		int temp = 0;									//To ensure it is a positive number
		
		switch(population){
			case 0:
				for(int i = 0; i < passengers.length; i++){
					passengers[i] = 0;
				}
			case 1:
				passengers[0] = 0;
				if(middle != 1){
					temp = rollDiceWithModifier(1, -2 + dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(2, -6+dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 2: 
				temp = rollDiceWithModifier(1, -(rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(1, dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(2, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 3:
				temp = rollDiceWithModifier(2, -(rollDice(2, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(2, -(rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(2, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 4:
				temp = rollDiceWithModifier(2, -(rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(2, -(rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(3, -(rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 5:
				temp = rollDiceWithModifier(2, -(rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(3, -(rollDice(2, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(3, -(rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 6: 
				temp = rollDiceWithModifier(3, -(rollDice(2, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(3, -(rollDice(2, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(3, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 7:
				temp = rollDiceWithModifier(3, -(rollDice(2, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(3, -(rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(3, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 8: 
				temp = rollDiceWithModifier(3, -(rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(3, -(rollDice(1, 6))+dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(4, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 9:
				temp = rollDiceWithModifier(3, -(rollDice(1, 6)) + dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(3, dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(5, dieMod);
					if(temp > 0){
						passengers[2] = temp;
					}else{
						passengers[2] = 0;
					}
				}
				break;
			case 10:
				temp = rollDiceWithModifier(3, dieMod);
				if(temp > 0){
					passengers[0] = temp;
				}else{
					passengers[0] = 0;
				}
				if(middle != 1){
					temp = rollDiceWithModifier(4, dieMod);
					if(temp > 0){
						passengers[1] = temp;
					}else{
						passengers[1] = 0;
					}
				}
				if(low != -1){
					temp = rollDiceWithModifier(6, dieMod);
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
	private void getDieMod(StarSystem from, StarSystem to) {
		if(to.getMainWorld().getProfile().getPop() < 5){
			dieMod -= 3;
		}else if(to.getMainWorld().getProfile().getPop()>7){
			dieMod += +3;
		}
		
		if(to.getZone()[0]){
			dieMod -= 12;
		}else if(to.getZone()[1]){
			dieMod -= 6;
		}
		
		dieMod += from.getMainWorld().getProfile().getTechLev() - to.getMainWorld().getProfile().getTechLev();
		
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
