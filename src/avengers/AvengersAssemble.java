package avengers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

//import javax.swing.JOptionPane;

public class AvengersAssemble {

	/*** Main method
	 * 
	 * @param avengers	String array of names from the user (optional)
	 */
	public static void main(String[] avengers) {
		String[] defaultAvengers = { "Ironman", "Hulk", "Captain America",
				"Thor", "Hawkeye", "Black Widow" };

		// If command line arguments (a list of Avengers) are supplied, use them
		if (avengers.length != 0) {
			assemble(avengers);
			defeatBadGuys(avengers);
		} else {
			assemble(defaultAvengers);
			defeatBadGuys(defaultAvengers);
		}
	}// End of main

	/***
	 * Prints out a list of available Avengers
	 * 
	 * @param avengers	String array of Avenger names
	 */
	static void assemble(String[] avengers) {
		System.out.println("**********************");
		System.out.println("*Avengers Assemble!!!*");
		System.out.println("**********************");

		// Loop through the Avengers and print them out
		for (String hero : avengers) {
			System.out.println(hero + " has assembled!");
		}
	}// End of assemble

	/***
	 * The battle - match Avengers against Villains. This method
	 * calls setNumberOfVillains, hypothesizeWinners, and summarizeBattle
	 * 
	 * @param avengers	String array of Avenger names
	 */
	static void defeatBadGuys(String[] avengers) {
		String[] badGuys = { "Whiplash", "Abomination", "Red Skull", "Loki", 
							"Venom", "Doc Oct", "Destroyer", "Green Goblin",
							"Sandman", "a Random Thug", "Magneto", "Mandarin"};
		Random generator = new Random();
		int weight = 8;
		int heroBonus = 2; // it pays to be a good guy
		int heroWinCount = 0;
		int villainWinCount = 0;

		int numberOfVillains = setNumberOfVillains(badGuys.length);
		hypothesizeWinner(avengers.length, numberOfVillains);

		System.out.println("\n");
		System.out.println("****************************");
		System.out.println("*Let the battle commence!!!*");
		System.out.println("****************************");

		// Loop through the bad guys, matching them with the good guys
		int i; // This is used after the for loop, hence it's declared out here
		for (i = 0; i < numberOfVillains; i++) {
			// Ensure there is an Avenger ready to attack the villain
			if (i < avengers.length){
				// Assign a weight to each person's attack
				int heroWeight = generator.nextInt(weight) + heroBonus;
				int villainWeight = generator.nextInt(weight);
				if (heroWeight < villainWeight) {
					System.out.println(badGuys[i] + " (" + villainWeight +
							") has defeated " + avengers[i] + " (" + heroWeight +
							")!");
					villainWinCount++;
				}
				// In the event of a tie, the Avenger wins
				else {
					System.out.println(avengers[i] + " (" + heroWeight +
							") has defeated " + badGuys[i] + " (" + villainWeight +
							")!");
					heroWinCount++;
				}
			}
			else
				System.out.println(badGuys[i] + " has escaped!!!");
		}
		// Check if there are any extra Avengers
		for (; i < avengers.length; i++) {
			System.out.println(avengers[i] + " has nothing to do...");
		}
		// If only one Avenger shows up
		if (avengers.length == 1) {
			System.out.println("\n");
			System.out.println(avengers[0]
					+ " says 'I can't take them all alone!'");
		}
		
		summarizeBattle(avengers.length, numberOfVillains, heroWinCount, villainWinCount);
	}// End of defeatBadGuys
	
	/***
	 * Asks the user for the number of villains to fight
	 * 
	 * @param maxBadGuys	(int) the maximum number of bad guys available
	 * @return				(int) the number of villains requested to fight
	 */
	static int setNumberOfVillains(int maxBadGuys) {
		int numberOfVillains = 0;

		// Get a number of villains from the user
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("\nHow many bad guys will you face today? (1 - " +
					maxBadGuys + ")");
			numberOfVillains = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			// If they didn't enter a number, supply one.
			System.out.println("--Error: enter a number. I'll do it for you: 1.--");
			numberOfVillains = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Verify input is between min (1) and max (# in badGuys array)
		if (numberOfVillains > maxBadGuys) {
			System.out.println("--Too many: setting number of villains to " + 
					maxBadGuys + ".--");
			numberOfVillains = maxBadGuys;
		}
		else if (numberOfVillains < 1) {
			System.out.println("--Too few: setting number of villains to 1.--");
			numberOfVillains = 1;
		}
	
		return numberOfVillains;
	}// End of setNumberOfVillains
	
	/***
	 * Make an educated guess at who will win
	 * 
	 * @param numberOfAvengers	(int) number of available Avengers
	 * @param numberOfVillains	(int) number of available villains
	 */
	static void hypothesizeWinner(int numberOfAvengers, int numberOfVillains){
		// Hypothesize who will win
		if (numberOfVillains == numberOfAvengers) {
			System.out.println("That's a great number - " + numberOfVillains + 
					" can be defeated.");
		}
		else if (numberOfVillains > numberOfAvengers) {
			int num = (numberOfVillains - numberOfAvengers);
			System.out.println("That's too many - I think " + num + " villain" + 
					checkPlural(num) + "might get away.");
		}
		else {
			int num = (numberOfAvengers - numberOfVillains);
			System.out.println("That's too few - " + num + " Avenger" +
					checkPlural(num) + "might be bored.");
		}
	}// End of hypothesizeWinner
	
	/***
	 * Summarize the battle (who won and by how much)
	 * 
	 * @param numberOfAvengers	(int) how many Avengers fought
	 * @param numberOfVillains	(int) how many villains fought
	 */
	static void summarizeBattle(int numberOfAvengers, int numberOfVillains, int heroWinCount, int villainWinCount){
		if (numberOfAvengers == numberOfVillains){
			System.out.println("A nicely matched battle - no villains got away " + 
					"and no Avengers were bored.");
		}
		else if (numberOfAvengers > numberOfVillains){
			int num = (numberOfAvengers - numberOfVillains);
			System.out.println("That was too easy - " + num + " Avenger" +
					checkPlural(num) + "nearly died of boredom.");
		}
		else if (numberOfAvengers < numberOfVillains){
			int num = (numberOfVillains - numberOfAvengers);
			System.out.println("Oh no - " + num + " villain" + checkPlural(num) +
					"got away!");
		}
		System.out.println(heroWinCount + " Avenger" + checkPlural(heroWinCount) +
				"won a battle, but " + villainWinCount + " Villain" +
				checkPlural(villainWinCount) + "won a battle.");
	}// End of summarizeBattle
	
	/***
	 * See if a number is plural
	 * 
	 * @param num	(int) number to be checked if plural
	 * @return		(String) 's ' if the number is plural, ' ' if singular
	 */
	static String checkPlural(int num){
		return ((num == 1) ? " " : "s ");
	}// End of checkPlural
}// End of Class
