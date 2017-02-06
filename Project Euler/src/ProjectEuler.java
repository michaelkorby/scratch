import java.math.BigInteger;

public class ProjectEuler {
	
	public ProjectEuler() {
		problemEight();
	}

	/**
	 * https://projecteuler.net/problem=8
	 * 
	 * Find the 13 adjacent digits that have the largest product
	 */
	private void problemEight() {
		int windowLength = 13;
		String input = "73167176531330624919225119674426574742355349194934\r\n" + 
				"96983520312774506326239578318016984801869478851843\r\n" + 
				"85861560789112949495459501737958331952853208805511\r\n" + 
				"12540698747158523863050715693290963295227443043557\r\n" + 
				"66896648950445244523161731856403098711121722383113\r\n" + 
				"62229893423380308135336276614282806444486645238749\r\n" + 
				"30358907296290491560440772390713810515859307960866\r\n" + 
				"70172427121883998797908792274921901699720888093776\r\n" + 
				"65727333001053367881220235421809751254540594752243\r\n" + 
				"52584907711670556013604839586446706324415722155397\r\n" + 
				"53697817977846174064955149290862569321978468622482\r\n" + 
				"83972241375657056057490261407972968652414535100474\r\n" + 
				"82166370484403199890008895243450658541227588666881\r\n" + 
				"16427171479924442928230863465674813919123162824586\r\n" + 
				"17866458359124566529476545682848912883142607690042\r\n" + 
				"24219022671055626321111109370544217506941658960408\r\n" + 
				"07198403850962455444362981230987879927244284909188\r\n" + 
				"84580156166097919133875499200524063689912560717606\r\n" + 
				"05886116467109405077541002256983155200055935729725\r\n" + 
				"71636269561882670428252483600823257530420752963450";
		
		//First, parse the string input to remove the newlines
		input = input.replaceAll("\r\n", "");
		
		problemEightBruteForce(input, windowLength);
		
		long startTime = System.currentTimeMillis();
		
		//Zeroes in the string will not produce maximum products. Strip them out and consider the strings that are in between the zeroes separately.
		String[] zerolessStrings = input.split("0");
		
		double maxProduct = 0;
		
		for (String zerolessInput : zerolessStrings) {
			int debugStart, debugEnd;
			debugStart = debugEnd = 0;
			
			int startIndex = 0;
			int endIndex = 0;

			//Move the startindex and endindex along the string and calculate the product;
			double runningProduct = 1;
			while (endIndex < zerolessInput.length()) {

				//Multiply in the value at the end of the window.
				int intAtEndOfWindow = getIntValueFromString(endIndex, zerolessInput);
				debugEnd = endIndex;
				runningProduct *= intAtEndOfWindow;

				//Advance the end index
				endIndex++;

				//If the end index now moved past the length of the window, we need to divide out the number at the start of the window
				if (endIndex > windowLength) {
					//The end index has moved on past the initial x characters. Divide out the value at the start of the window.
					int valueAtStart = getIntValueFromString(startIndex, zerolessInput);
					debugStart = startIndex + 1;
					runningProduct /= valueAtStart;
//					System.out.println("Out " + valueAtStart + " at " + startIndex);
					
					//Advance the start pointer
					startIndex++;
				}
				
				if (endIndex - startIndex >= windowLength && runningProduct > maxProduct) {
					//We have a value made out of at least the x digits we need and it's larger than anything we've seen so far
					maxProduct = runningProduct;
				}
			}
		}
		
		System.out.println("Problem Eight optimized solution: " + formatDoubleNoScientificNotation(maxProduct)  + ". Time: " + elapsedTime(startTime) + " millis");
	}

	/**
	 * Brute force solution to problem 8. Re-multiply all the sequences
	 * @param input
	 * @param frame 
	 */
	private void problemEightBruteForce(String input, int frame) {
		long startTime = System.currentTimeMillis();
		double maxProduct = 0;
		int endIndex = frame;
		
		//Plow through the entire string and multiply out the previous x characters. Save the max product
		while (endIndex < input.length()) {
			String window = input.substring(endIndex - frame, endIndex);
			double windowProduct = 1;
			for (char digit : window.toCharArray()) {
				windowProduct*= Character.getNumericValue(digit);
			}
			
			if (windowProduct > maxProduct) {
				maxProduct = windowProduct;
			}
			endIndex++;
		}
		System.out.println("Problem Eight brute force solution: " + formatDoubleNoScientificNotation(maxProduct) + ". Time: " + elapsedTime(startTime) + " millis");
	}
	
	private long elapsedTime(final long startTime) {
		return System.currentTimeMillis() - startTime;
	}
	
	private String formatDoubleNoScientificNotation(final double formatMe) {
		return String.format("%.0f", formatMe);
	}

	private String extractSubstringAndAsteriskify(String input, int start, int end) {
		
		String substring = input.substring(start, end + 1);
		return String.join("*", substring.split(""));
	}

	/**
	 * Get the int at the startIndex location within a string of numbers
	 * @param startIndex
	 * @param input
	 * @return
	 */
	private int getIntValueFromString(int index, String input) {
		return Character.getNumericValue(input.charAt(index));
	}

	public static void main(String[] args) {
		
		new ProjectEuler();

	}

}
