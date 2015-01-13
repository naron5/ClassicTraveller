/**
 * 
 */
package com.ffe.traveller.classic.trade;

import util.DiceGenerator;
import com.ffe.traveller.classic.decoder.Ship;

/**
 * @author markknights
 * This class still needs the Charter method completed which will occur after
 * the Ship class is implemented.
 */
public class ClassicMailIncidental {
	//class variables
	boolean debug = false;
	boolean subsidized = false;
	DiceGenerator sixer = new DiceGenerator();
	
	public  ClassicMailIncidental(boolean SubsidisedMerchant){
		subsidized = SubsidisedMerchant;
	}
	
	public boolean getSubsidized(){
		return subsidized;
	}
	
	public boolean privateMessage(){
		boolean approached = false;
		int roll = DiceGenerator.rollDice(2, 6);
		
		if(roll > 8){
			approached = true;
		}
		
		return approached;
	}
	
	public int charterPrice(Ship charterBoat){
		//toDo  this method needs to be completed
		return -1;
	}
}
