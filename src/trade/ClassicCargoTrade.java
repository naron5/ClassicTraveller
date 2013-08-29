/**
 * 
 */
package trade;

import java.util.Random;

import decoder.Planet;
import dieRoller.DiceGenerator;


/**
 * @author markknights
 *
 */
public class ClassicCargoTrade {
	boolean debug = false;
	Planet from, to;
	int major, minor, incidental, dieMod;
	int cargoMult[] = {10, 5};
	int cargoOnOffer[] = new int[3];
	Random generator = new Random();
	DiceGenerator sixer = new DiceGenerator();
	
	public ClassicCargoTrade(Planet exporter, Planet importer){
		from = exporter;
		to = importer;
		
		major = 0;
		minor = 0;
		incidental = 0;
		
		if(exporter.getZone()[0]){
			 major = -1;
			 minor = -1;
			 incidental = -1;
		 }else if(exporter.getZone()[1]){
			 major = -1;
		 }
		
		getDieMod();
		cargoOnOffer = getCargoTonnage();
	}
	
	
	/**
	 * @return
	 */
	private int[] getCargoTonnage() {
		int[] cargoAmount = new int[3];
		int population = from.getProfile().getPop();
		
		switch(population){
		case 1:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (-4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (-4 + dieMod)) * cargoMult[1];
			}
			break;
		case 2: 
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (-2 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (-1 + dieMod)) * cargoMult[1];
			}
			break;
		case 3:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (-1 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, dieMod) * cargoMult[1];
			}
			break;
		case 4:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, dieMod) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (1 + dieMod)) * cargoMult[1];
			}
			break;
		case 5:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			break;
		case 6: case 7:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			if(incidental != -1){
				cargoAmount[2] = sixer.rollDiceWithModifier(1, 6, (-3 + dieMod));
			}
			break;
		case 8: case 9:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			if(incidental != -1){
				cargoAmount[2] = sixer.rollDiceWithModifier(1, 6, (-2 + dieMod));
			}
			break;
		case 10:
			if(major != -1){
				cargoAmount[0] = sixer.rollDiceWithModifier(1, 6, (population -4 + dieMod)) * cargoMult[0];
			}
			if(minor != -1){
				cargoAmount[1] = sixer.rollDiceWithModifier(1, 6, (population -3 + dieMod)) * cargoMult[1];
			}
			if(incidental != -1){
				cargoAmount[2] = sixer.rollDiceWithModifier(1, 6, dieMod);
			}
			break;
		}
		return cargoAmount;
	}


	/**
	 * 
	 */
	private void getDieMod() {
		if(from.getProfile().getPop() < 5){
			dieMod -= 4;
		}else if(from.getProfile().getPop()>7){
			dieMod += 1;
		}
		
		dieMod += from.getProfile().getTechLev() - to.getProfile().getTechLev();
		
	}


	private void debug(String s){
		System.out.println();
	}
}
