/**
 * 
 */
package trade;

import decoder.Planet;
import dieRoller.DiceGenerator;

/**
 * @author markknights
 *
 */
public class ClassicSpeculative {
	
	String type = "";
	int basePrice, dieModPur, dieModSell, number;
	
	public ClassicSpeculative(Planet from, Planet to){
		refresh(from, to);
	}

	/**
	 * 
	 */
	private void refresh(Planet from, Planet to) {
		final String[] GOODS = {"Textiles", "Polymers", "Liquor", "Wood", "Crystals", "Radioactives",
			"Steel", "Copper", "Aluminum", "Tin", "Silver", "Special Alloys", 
			"Petrochemicals", "Grain", "Meat", "Spices", "Fruit", "Pharmaceuticals",
			"Gems", "Firearms", "Ammunition", "Blades", "Tools", "Body Armour",
			"Aircraft", "Air Raft", "Computers", "All Terrain Vehicles", "Armoured Vehicles", "Farm Machinery",
			"Electronic Parts", "Mechanical Parts", "Cybernetic Parts", "Computer Parts", "Machine Tools", "Vacc Suits"};
		final int[] BASEPRICE = {3000, 7000, 10000, 1000, 20000, 1000000,
			500, 2000, 1000, 9000, 70000, 200000,
			10000, 300, 1500, 6000, 1000, 100000,
			1000000, 30000, 30000, 10000, 10000, 50000,
			1000000, 6000000, 10000000, 3000000, 7000000, 150000,
			100000, 70000, 250000, 150000, 750000, 400000};
		int[][] purchaseDM = new int[BASEPRICE.length][6];
		int[][] resaleDM = new int[BASEPRICE.length][6];
		int[] quantity = new int[BASEPRICE.length];
		DiceGenerator sixes = new DiceGenerator();
		int choice = -1;
		
		//Set up the Die Modifier arrays purchaseDM and resaleDM
		purchaseDM[18][3] = resaleDM[29][1] = -8;
		purchaseDM[0][0] = -7;
		resaleDM[0][0]   = purchaseDM[3][0] = resaleDM[3][0] = -6;
		purchaseDM[0][1] = purchaseDM[12][3] = resaleDM[12][3] = purchaseDM[28][2] = purchaseDM[29][2] 
		                 = purchaseDM[31][2] = purchaseDM[33][2] = purchaseDM[34][2] = purchaseDM[35][1] = -5;
		purchaseDM[2][0] = resaleDM[5][4] = purchaseDM[12][1] = resaleDM[12][1] = purchaseDM[24][2] 
		                 = purchaseDM[30][2] = purchaseDM[32][2] = purchaseDM[34][4] = -4;
		purchaseDM[0][3] = purchaseDM[1][4] = resaleDM[2][0] = purchaseDM[4][1] = resaleDM[4][1] 
		                 = purchaseDM[5][3] = resaleDM[5][3] = purchaseDM[7][2] = resaleDM[7][2]
		                 = purchaseDM[8][2] = resaleDM[8][2] = purchaseDM[9][2] = resaleDM[9][2] 
		                 = purchaseDM[11][2] = resaleDM[11][2] = purchaseDM[16][0] = purchaseDM[17][1] 
		                 = resaleDM[17][1] = purchaseDM[18][5] = purchaseDM[19][2] = purchaseDM[20][2] 
		                 = purchaseDM[21][2] = purchaseDM[22][2] = purchaseDM[23][4] = purchaseDM[24][4] 
		                 = purchaseDM[25][2] = resaleDM[26][0] = purchaseDM[30][4] = purchaseDM[31][4] 
		                 = purchaseDM[33][4] = purchaseDM[35][2] = -3;
		purchaseDM[1][2] = resaleDM[1][2] = purchaseDM[6][2] = resaleDM[6][2] = purchaseDM[7][4] 
		                 = purchaseDM[8][4] = purchaseDM[9][4] = purchaseDM[11][4] = purchaseDM[13][0] 
		                 = resaleDM[13][0] = purchaseDM[14][0] = resaleDM[14][0] = purchaseDM[15][0] 
		                 = resaleDM[15][0] = resaleDM[16][0] = resaleDM[18][3] = purchaseDM[19][4] 
		                 = resaleDM[19][2] = purchaseDM[20][4] = resaleDM[20][2] = purchaseDM[21][4] 
		                 = resaleDM[21][2] = purchaseDM[22][4] = resaleDM[22][2] = resaleDM[23][2] 
		                 = purchaseDM[25][4] = purchaseDM[26][2] = purchaseDM[26][4] = purchaseDM[27][2] 
		                 = purchaseDM[27][4] = purchaseDM[28][4] = resaleDM[28][1] = purchaseDM[29][4] = -2;
		purchaseDM[6][4] = resaleDM[6][4] = resaleDM[7][4] = resaleDM[8][4] = resaleDM[9][4] 
		                 = purchaseDM[10][4] = resaleDM[10][4] = resaleDM[11][4] = resaleDM[19][4] 
		                 = resaleDM[20][4] = resaleDM[21][4] = resaleDM[22][4] = purchaseDM[23][2] 
		                 = purchaseDM[32][4] = purchaseDM[35][4] = resaleDM[35][1] = -1;
		resaleDM[0][1]   = resaleDM[2][2] = resaleDM[3][2] = purchaseDM[6][5] = purchaseDM[7][5] 
		                 = purchaseDM[8][5] = purchaseDM[9][5] = purchaseDM[12][2] = purchaseDM[13][1] 
		                 = purchaseDM[14][5] = purchaseDM[16][1] = resaleDM[23][4] = resaleDM[24][5] 
		                 = resaleDM[25][5] = resaleDM[26][5] = resaleDM[27][5] = resaleDM[27][0] 
		                 = resaleDM[28][4] = resaleDM[29][5] = resaleDM[30][5] = resaleDM[32][0] 
		                 = resaleDM[33][0] = resaleDM[34][0] = resaleDM[35][5] = 1;
		resaleDM[2][4]   = resaleDM[3][4] = purchaseDM[10][5] = purchaseDM[13][2] = purchaseDM[14][1] 
		                 = resaleDM[14][2] = purchaseDM[15][2] = resaleDM[15][4] = purchaseDM[16][2] 
		                 = resaleDM[16][5] = resaleDM[24][3] = resaleDM[25][3] = resaleDM[26][3] 
		                 = resaleDM[27][3] = resaleDM[28][0] = resaleDM[30][3] = purchaseDM[31][0] 
		                 = resaleDM[32][1] = resaleDM[33][1] = resaleDM[34][1] = resaleDM[35][3] = 2;
		resaleDM[0][4]   = purchaseDM[1][5] = resaleDM[1][4] = resaleDM[4][2] = resaleDM[4][4] 
		                 = resaleDM[6][5] = resaleDM[12][2] = purchaseDM[14][2] = purchaseDM[15][1] 
		                 = resaleDM[15][5] = resaleDM[16][2] = purchaseDM[17][5] = purchaseDM[19][5] 
		                 = resaleDM[19][5] = purchaseDM[20][5] = resaleDM[20][5] = purchaseDM[21][5] 
		                 = purchaseDM[21][5] = resaleDM[21][5] = purchaseDM[22][5] = resaleDM[22][5] 
		                 = purchaseDM[23][5] = resaleDM[31][3] = resaleDM[33][3] = resaleDM[34][3] = 3;
		purchaseDM[4][2] = resaleDM[8][3] = resaleDM[11][3] = purchaseDM[17][2] = resaleDM[17][4] 
		                 = purchaseDM[18][2] = resaleDM[18][2] = resaleDM[23][5] = purchaseDM[28][5] 
		                 = resaleDM[32][3] = 4;
		purchaseDM[5][4] = purchaseDM[10][2] = resaleDM[10][2] = purchaseDM[11][3] = resaleDM[17][2] 
		                 = resaleDM[29][0] = 5;
		resaleDM[5][2]   = 6;
		purchaseDM[5][2] = 7;
		resaleDM[18][4]  = 8;
		
		//Set up the quantity array
		quantity[4] = quantity[5] = quantity[11] = quantity[12] = quantity[17] = quantity[18] = quantity[24] = 
			quantity[25] = quantity[26] = quantity[27] = quantity[28] = quantity[29] = sixes.rollDice(1, 6);
		quantity[19] = quantity[20] = quantity[21] = quantity[22] = quantity[23] = sixes.rollDice(2, 6);
		quantity[2] = quantity[10] = quantity[15] = quantity[30] = quantity[31] = quantity[32] = quantity[33] = 
			quantity[34] = quantity[35] = sixes.rollDice(1, 6) * 5;
		quantity[16] = sixes.rollDice(2, 6) * 5; 
		quantity[0] = sixes.rollDice(3, 6) * 5;
		quantity[1] = quantity[14] = sixes.rollDice(4, 6) * 5;
		quantity[13] = sixes.rollDice(8, 6) * 5; 
		quantity[3] = quantity[7] = sixes.rollDice(2, 6) * 10;
		quantity[9] = sixes.rollDice(3, 6) * 10;
		quantity[6] = sixes.rollDice(4, 6) * 10;
		quantity[8] = sixes.rollDice(5, 6) * 10; 
		
		//get details
		choice = sixes.rollDice(1, 36) - 1;
		
		type = GOODS[choice];
		basePrice = BASEPRICE[choice];
		
		//Die mod for purchase
		for(int i = 0; i < from.getProfile().getTradeClassifications().length; i++){
			if(from.getProfile().getTradeClassifications()[i]){
				dieModPur += purchaseDM[choice][i];
			}
		}
		
		//Die mod for sale
		for(int i = 0; i < to.getProfile().getTradeClassifications().length; i++){
			if(to.getProfile().getTradeClassifications()[i]){
				dieModSell += purchaseDM[choice][i];
			}
		}
		
		//quantity
		number = quantity[choice];
	}
}
