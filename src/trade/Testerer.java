/**
 * 
 */
package trade;

/**
 * @author markknights
 *
 */
public class Testerer {

	public static void main(String[] args){
		int amount = 16;
		int count = 7;
		
		do{
			amount = amount/count +16;
			if(amount % 2 == 0){
				count = count+1;
			}
		}while(count < 10);
		System.out.println("" + count);
		System.out.println("" + amount);
		
	}
}
