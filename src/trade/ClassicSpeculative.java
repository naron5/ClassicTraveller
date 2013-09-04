/**
 * 
 */
package trade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import decoder.Planet;
import decoder.TradeClassifications;
import util.DiceGenerator;

/**
 * @author markknights
 * 
 */
public class ClassicSpeculative {

	String type = "";
	int basePrice, dieModPur, dieModSell, number;
	private final int Agricultural = 0;
	private final int NonAgricultural = 0;
	private final int Industrial = 0;
	private final int NonIndustrial = 0;
	private final int Rich = 0;
	private final int Poor = 0;
	
	private static List<String> Goods;
	private static List<Integer> BasePrices;
	private static Map<Integer, Map<Integer, Integer>> resaleMods;
	private static Map<Integer, Map<Integer, Integer>> purchaseMods;

	public ClassicSpeculative(Planet from, Planet to) {
		refresh(from, to);
	}

	/**
	 * 
	 */
	private void refresh(Planet from, Planet to) {
		
		loadProperties();
		
		
		int[] quantity = new int[BasePrices.size()];

		int choice = -1;


		// Set up the quantity array
		quantity[4] = quantity[5] = quantity[11] = quantity[12] = quantity[17] = quantity[18] = quantity[24] = quantity[25] = quantity[26] = quantity[27] = quantity[28] = quantity[29] = DiceGenerator
				.rollDice(1, 6);
		quantity[19] = quantity[20] = quantity[21] = quantity[22] = quantity[23] = DiceGenerator
				.rollDice(2, 6);
		quantity[2] = quantity[10] = quantity[15] = quantity[30] = quantity[31] = quantity[32] = quantity[33] = quantity[34] = quantity[35] = DiceGenerator
				.rollDice(1, 6) * 5;
		quantity[16] = DiceGenerator.rollDice(2, 6) * 5;
		quantity[0] = DiceGenerator.rollDice(3, 6) * 5;
		quantity[1] = quantity[14] = DiceGenerator.rollDice(4, 6) * 5;
		quantity[13] = DiceGenerator.rollDice(8, 6) * 5;
		quantity[3] = quantity[7] = DiceGenerator.rollDice(2, 6) * 10;
		quantity[9] = DiceGenerator.rollDice(3, 6) * 10;
		quantity[6] = DiceGenerator.rollDice(4, 6) * 10;
		quantity[8] = DiceGenerator.rollDice(5, 6) * 10;

		// get details
		choice = DiceGenerator.rollDice(1, 36) - 1;

		type = Goods.get(choice);
		basePrice = BasePrices.get(choice);

		for (TradeClassifications tc : from.getProfile()
				.getTradeClassifications()) {

			int classification = convertTradeClassification(tc);

			dieModPur += purchaseMods.get(choice).get(classification);

			if (from.getProfile().getPop() > 8) {
				dieModPur += 6;
			} else if (from.getProfile().getPop() < 6) {
				dieModPur -= 6;
			}

			if (dieModPur < 0) {
				dieModPur = 0;
			} else if (dieModPur > 35) {
				dieModPur = 35;
			}

		}

		// Die mod for sale
		for (TradeClassifications tc : to.getProfile()
				.getTradeClassifications()) {

			int classification = convertTradeClassification(tc);
				dieModSell += resaleMods.get(choice).get(classification);
			
		}

		// quantity
		number = quantity[choice];
	}

	/**
	 * @param splitCargo
	 * @param diceThrow
	 * @param quantity
	 * @return
	 * 
	 *         Returns the purchase price for the goods
	 */
	public int finalPurchasePrice(boolean splitCargo, int diceThrow,
			int quantity) {
		int purchasePrice = 0;
		double amount = basePrice;
		double[] multiplier = { 0.4, 0.5, 0.7, 0.8, 0.9, 1, 1.1, 1.2, 1.3, 1.5,
				1.7, 2, 3, 4 }; // Actual value table

		diceThrow += dieModPur;

		// ensure maximum and minimum is adhered to
		if (diceThrow < 0) {
			diceThrow = 0;
		} else if (diceThrow >= multiplier.length) {
			diceThrow = multiplier.length - 1;
		}

		amount *= multiplier[diceThrow];

		// 1% processing fee if the crew did not take the full cargo offered
		if (splitCargo) {
			amount = amount + (amount * 0.01);
		}

		purchasePrice = (int) amount * quantity;

		return purchasePrice;
	}

	/**
	 * @param diceThrow
	 * @param bribery
	 * @param admin
	 * @param broker
	 * @param diceThrow
	 * @return
	 * 
	 *         returns a two space int array. the first array position details
	 *         the net sale amount, the second array position details broker
	 *         fees if a broker was used
	 */
	public int[] finalSalePrice(int diceThrow, int bribery, int admin,
			int broker, int quantity) {
		int salePrice[] = new int[2];
		double temp = 0;
		boolean brokered = false;
		double amount = basePrice;
		double[] multiplier = { 0.4, 0.5, 0.7, 0.8, 0.9, 1, 1.1, 1.2, 1.3, 1.5,
				1.7, 2, 3, 4 }; // Actual value table

		if (bribery > 0) {
			dieModSell += bribery;
		}
		if (admin > 0) {
			dieModSell += admin;
		}
		if (broker > 0) {
			brokered = true;
			dieModSell += broker;
		}

		if (diceThrow < 0) {
			diceThrow = 0;
		} else if (diceThrow >= multiplier.length) {
			diceThrow = multiplier.length - 1;
		}

		amount *= multiplier[diceThrow];
		amount *= quantity;

		if (brokered) {
			if (broker > 4) {
				broker = 4;
			}
			temp = amount * ((double) broker * 0.05);
			salePrice[1] = (int) temp;
		}

		temp = amount - temp;
		salePrice[0] = (int) temp;

		return salePrice;
	}

	private int convertTradeClassification(TradeClassifications tc) {
		int rv;
		switch (tc) {
		case Agricultural:
			rv = Agricultural;
			break;
		case NonAgricultural:
			rv = NonAgricultural;
			break;
		case Industrial:
			rv = Industrial;
			break;
		case NonIndustrial:
			rv = NonIndustrial;
			break;
		case Rich:
			rv = Rich;
			break;
		case Poor:
			rv = Poor;
			break;
		default:
			rv = 0;
			break;
		}
		return rv;
	}

	@SuppressWarnings("unchecked")
	private static void loadProperties() {
		Map<String, Object> propertyMap = new HashMap<String, Object>();


		InputStream input;
		try {
			input = new FileInputStream(new File(
					"src/properties/SpeculativeTrade.yml"));
			Yaml yaml = new Yaml();
			propertyMap = (Map<String, Object>) yaml.load(input);
			if(Goods == null)
				Goods = (List<String>) propertyMap.get("Goods");
			
			if(BasePrices == null)
				BasePrices = (List<Integer>) propertyMap.get("BasePrice");
			
			if(purchaseMods == null)
			{
				purchaseMods = new HashMap<Integer, Map<Integer, Integer>>();
				List<List<Integer>> mods = (List<List<Integer>>)propertyMap.get("PurchaseDM");
				for(List<Integer> m:mods)
				{
					if(purchaseMods.containsKey(m.get(0)))
					{
						purchaseMods.put(m.get(0), (Map<Integer, Integer>)new HashMap<Integer, Integer>());
					}
					purchaseMods.get(m.get(0)).put(m.get(1), m.get(2));
					
				}
			}
			
			if(resaleMods == null)
			{
				resaleMods = new HashMap<Integer, Map<Integer, Integer>>();
				List<List<Integer>> mods = (List<List<Integer>>)propertyMap.get("ResaleDM");
				for(List<Integer> m:mods)
				{
					if(resaleMods.containsKey(m.get(0)))
					{
						resaleMods.put(m.get(0), (Map<Integer, Integer>)new HashMap<Integer, Integer>());
					}
					resaleMods.get(m.get(0)).put(m.get(1), m.get(2));
					
				}
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}
}
