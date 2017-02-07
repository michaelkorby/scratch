import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
		final int windowLength = 13;
		String input = "73167176531330624919225119674426574742355349194934\r\n"
				+ "96983520312774506326239578318016984801869478851843\r\n"
				+ "85861560789112949495459501737958331952853208805511\r\n"
				+ "12540698747158523863050715693290963295227443043557\r\n"
				+ "66896648950445244523161731856403098711121722383113\r\n"
				+ "62229893423380308135336276614282806444486645238749\r\n"
				+ "30358907296290491560440772390713810515859307960866\r\n"
				+ "70172427121883998797908792274921901699720888093776\r\n"
				+ "65727333001053367881220235421809751254540594752243\r\n"
				+ "52584907711670556013604839586446706324415722155397\r\n"
				+ "53697817977846174064955149290862569321978468622482\r\n"
				+ "83972241375657056057490261407972968652414535100474\r\n"
				+ "82166370484403199890008895243450658541227588666881\r\n"
				+ "16427171479924442928230863465674813919123162824586\r\n"
				+ "17866458359124566529476545682848912883142607690042\r\n"
				+ "24219022671055626321111109370544217506941658960408\r\n"
				+ "07198403850962455444362981230987879927244284909188\r\n"
				+ "84580156166097919133875499200524063689912560717606\r\n"
				+ "05886116467109405077541002256983155200055935729725\r\n"
				+ "71636269561882670428252483600823257530420752963450";

		// First, parse the string input to remove the newlines
		input = input.replaceAll("\r\n", "");

		problemEightBruteForce(input, windowLength);

		final long startTime = System.currentTimeMillis();

		// Zeroes in the string will not produce maximum products. Strip them
		// out and consider the strings that are in between the zeroes
		// separately.
		final String[] zerolessStrings = input.split("0");

		double maxProduct = 0;

		for (final String zerolessInput : zerolessStrings) {
			int startIndex = 0;
			int endIndex = 0;

			// Move the startindex and endindex along the string and calculate
			// the product;
			double runningProduct = 1;
			while (endIndex < zerolessInput.length()) {

				// Multiply in the value at the end of the window.
				final int intAtEndOfWindow = getIntValueFromString(endIndex, zerolessInput);
				runningProduct *= intAtEndOfWindow;

				// Advance the end index
				endIndex++;

				// If the end index now moved past the length of the window, we
				// need to divide out the number at the start of the window
				if (endIndex > windowLength) {
					// The end index has moved on past the initial x characters.
					// Divide out the value at the start of the window.
					final int valueAtStart = getIntValueFromString(startIndex, zerolessInput);
					runningProduct /= valueAtStart;

					// Advance the start pointer
					startIndex++;
				}

				if (((endIndex - startIndex) >= windowLength) && (runningProduct > maxProduct)) {
					// We have a value made out of at least the x digits we need
					// and it's larger than anything we've seen so far
					maxProduct = runningProduct;
				}
			}
		}

		System.out.println("Problem Eight optimized solution: " + formatDoubleNoScientificNotation(maxProduct)
				+ ". Time: " + elapsedTime(startTime) + " millis");
	}

	/**
	 * Brute force solution to problem 8. Re-multiply all the sequences
	 * 
	 * @param input
	 * @param frame
	 */
	private void problemEightBruteForce(final String input, final int frame) {
		final long startTime = System.currentTimeMillis();
		double maxProduct = 0;
		int endIndex = frame;

		// Plow through the entire string and multiply out the previous x
		// characters. Save the max product
		while (endIndex < input.length()) {
			final String window = input.substring(endIndex - frame, endIndex);
			double windowProduct = 1;
			for (final char digit : window.toCharArray()) {
				windowProduct *= Character.getNumericValue(digit);
			}

			if (windowProduct > maxProduct) {
				maxProduct = windowProduct;
			}
			endIndex++;
		}
		System.out.println("Problem Eight brute force solution: " + formatDoubleNoScientificNotation(maxProduct)
				+ ". Time: " + elapsedTime(startTime) + " millis");
	}

	private void problemFourteen() {
		final HashMap<Double, Integer> chains = new HashMap<Double, Integer>();
		Integer maxChainLength = 0;
		Double maxChainLengthValue = 0.0;
		chains.put(1.0, 0);
		for (int i = 2; i < 1000000; i++) {
			final Stack<Double> iterationStack = new Stack<Double>();
			double nextStep = i;
			while (nextStep > 1) {
				iterationStack.push(nextStep);
				nextStep = nextCollatz(nextStep);
				Integer existingChain = chains.get(nextStep);
				if (existingChain != null) {
					// We reduced down to an existing chain
					while (!iterationStack.isEmpty()) {
						existingChain++;
						final Double startingValue = Double.valueOf(iterationStack.pop());
						chains.put(startingValue, existingChain);
						nextStep = 0;

						if (existingChain > maxChainLength) {
							maxChainLength = existingChain;
							maxChainLengthValue = startingValue;
							System.out.println(maxChainLength + " -> " + maxChainLengthValue);
						}
					}
				}
			}
		}

		System.out.println("Fourteen: " + maxChainLengthValue);
	}

	private double nextCollatz(final double nextStep) {
		if ((nextStep % 2) == 0) {
			return nextStep / 2;
		} else {
			return (3 * nextStep) + 1;
		}
	}

	private void problemThirteen() {
		final String input = "37107287533902102798797998220837590246510135740250\r\n"
				+ "46376937677490009712648124896970078050417018260538\r\n"
				+ "74324986199524741059474233309513058123726617309629\r\n"
				+ "91942213363574161572522430563301811072406154908250\r\n"
				+ "23067588207539346171171980310421047513778063246676\r\n"
				+ "89261670696623633820136378418383684178734361726757\r\n"
				+ "28112879812849979408065481931592621691275889832738\r\n"
				+ "44274228917432520321923589422876796487670272189318\r\n"
				+ "47451445736001306439091167216856844588711603153276\r\n"
				+ "70386486105843025439939619828917593665686757934951\r\n"
				+ "62176457141856560629502157223196586755079324193331\r\n"
				+ "64906352462741904929101432445813822663347944758178\r\n"
				+ "92575867718337217661963751590579239728245598838407\r\n"
				+ "58203565325359399008402633568948830189458628227828\r\n"
				+ "80181199384826282014278194139940567587151170094390\r\n"
				+ "35398664372827112653829987240784473053190104293586\r\n"
				+ "86515506006295864861532075273371959191420517255829\r\n"
				+ "71693888707715466499115593487603532921714970056938\r\n"
				+ "54370070576826684624621495650076471787294438377604\r\n"
				+ "53282654108756828443191190634694037855217779295145\r\n"
				+ "36123272525000296071075082563815656710885258350721\r\n"
				+ "45876576172410976447339110607218265236877223636045\r\n"
				+ "17423706905851860660448207621209813287860733969412\r\n"
				+ "81142660418086830619328460811191061556940512689692\r\n"
				+ "51934325451728388641918047049293215058642563049483\r\n"
				+ "62467221648435076201727918039944693004732956340691\r\n"
				+ "15732444386908125794514089057706229429197107928209\r\n"
				+ "55037687525678773091862540744969844508330393682126\r\n"
				+ "18336384825330154686196124348767681297534375946515\r\n"
				+ "80386287592878490201521685554828717201219257766954\r\n"
				+ "78182833757993103614740356856449095527097864797581\r\n"
				+ "16726320100436897842553539920931837441497806860984\r\n"
				+ "48403098129077791799088218795327364475675590848030\r\n"
				+ "87086987551392711854517078544161852424320693150332\r\n"
				+ "59959406895756536782107074926966537676326235447210\r\n"
				+ "69793950679652694742597709739166693763042633987085\r\n"
				+ "41052684708299085211399427365734116182760315001271\r\n"
				+ "65378607361501080857009149939512557028198746004375\r\n"
				+ "35829035317434717326932123578154982629742552737307\r\n"
				+ "94953759765105305946966067683156574377167401875275\r\n"
				+ "88902802571733229619176668713819931811048770190271\r\n"
				+ "25267680276078003013678680992525463401061632866526\r\n"
				+ "36270218540497705585629946580636237993140746255962\r\n"
				+ "24074486908231174977792365466257246923322810917141\r\n"
				+ "91430288197103288597806669760892938638285025333403\r\n"
				+ "34413065578016127815921815005561868836468420090470\r\n"
				+ "23053081172816430487623791969842487255036638784583\r\n"
				+ "11487696932154902810424020138335124462181441773470\r\n"
				+ "63783299490636259666498587618221225225512486764533\r\n"
				+ "67720186971698544312419572409913959008952310058822\r\n"
				+ "95548255300263520781532296796249481641953868218774\r\n"
				+ "76085327132285723110424803456124867697064507995236\r\n"
				+ "37774242535411291684276865538926205024910326572967\r\n"
				+ "23701913275725675285653248258265463092207058596522\r\n"
				+ "29798860272258331913126375147341994889534765745501\r\n"
				+ "18495701454879288984856827726077713721403798879715\r\n"
				+ "38298203783031473527721580348144513491373226651381\r\n"
				+ "34829543829199918180278916522431027392251122869539\r\n"
				+ "40957953066405232632538044100059654939159879593635\r\n"
				+ "29746152185502371307642255121183693803580388584903\r\n"
				+ "41698116222072977186158236678424689157993532961922\r\n"
				+ "62467957194401269043877107275048102390895523597457\r\n"
				+ "23189706772547915061505504953922979530901129967519\r\n"
				+ "86188088225875314529584099251203829009407770775672\r\n"
				+ "11306739708304724483816533873502340845647058077308\r\n"
				+ "82959174767140363198008187129011875491310547126581\r\n"
				+ "97623331044818386269515456334926366572897563400500\r\n"
				+ "42846280183517070527831839425882145521227251250327\r\n"
				+ "55121603546981200581762165212827652751691296897789\r\n"
				+ "32238195734329339946437501907836945765883352399886\r\n"
				+ "75506164965184775180738168837861091527357929701337\r\n"
				+ "62177842752192623401942399639168044983993173312731\r\n"
				+ "32924185707147349566916674687634660915035914677504\r\n"
				+ "99518671430235219628894890102423325116913619626622\r\n"
				+ "73267460800591547471830798392868535206946944540724\r\n"
				+ "76841822524674417161514036427982273348055556214818\r\n"
				+ "97142617910342598647204516893989422179826088076852\r\n"
				+ "87783646182799346313767754307809363333018982642090\r\n"
				+ "10848802521674670883215120185883543223812876952786\r\n"
				+ "71329612474782464538636993009049310363619763878039\r\n"
				+ "62184073572399794223406235393808339651327408011116\r\n"
				+ "66627891981488087797941876876144230030984490851411\r\n"
				+ "60661826293682836764744779239180335110989069790714\r\n"
				+ "85786944089552990653640447425576083659976645795096\r\n"
				+ "66024396409905389607120198219976047599490197230297\r\n"
				+ "64913982680032973156037120041377903785566085089252\r\n"
				+ "16730939319872750275468906903707539413042652315011\r\n"
				+ "94809377245048795150954100921645863754710598436791\r\n"
				+ "78639167021187492431995700641917969777599028300699\r\n"
				+ "15368713711936614952811305876380278410754449733078\r\n"
				+ "40789923115535562561142322423255033685442488917353\r\n"
				+ "44889911501440648020369068063960672322193204149535\r\n"
				+ "41503128880339536053299340368006977710650566631954\r\n"
				+ "81234880673210146739058568557934581403627822703280\r\n"
				+ "82616570773948327592232845941706525094512325230608\r\n"
				+ "22918802058777319719839450180888072429661980811197\r\n"
				+ "77158542502016545090413245809786882778948721859617\r\n"
				+ "72107838435069186155435662884062257473692284509516\r\n"
				+ "20849603980134001723930671666823555245252804609722\r\n"
				+ "53503534226472524250874054075591789781264330331690";

		final String[] numbers = input.split("\\r\\n");

		final int digitCount = 10;
		double sumFromFront = 0;
		final String previousTenDigits = "";
		int sameCount = 0;

		final int inputNumberLength = numbers[0].length();
		for (int i = 0; i < inputNumberLength; i++) {
			int thisSum = 0;
			for (final String number : numbers) {
				final int valueAtLocation = Integer.parseInt(number.substring(i, i + 1));
				thisSum += valueAtLocation;
			}
			sumFromFront = thisSum * Math.pow(10, inputNumberLength - i);

			final String stringSum = formatDoubleNoScientificNotation(sumFromFront);
			if (stringSum.length() > digitCount) {
				final String firstTenDigits = stringSum.substring(0, digitCount);
				System.out.println(firstTenDigits);
				if (firstTenDigits.equals(previousTenDigits)) {
					sameCount++;
					if (sameCount >= digitCount) {
						// It won't change anymore
						System.out.println("Fourteen: " + firstTenDigits);
					}
				} else {
					sameCount = 0;
				}
			}
		}
	}

	private void problemTwelve() {
		int triangleNumber = 0;
		boolean done = false;
		int maxDivisors = 0;
		for (int i = 1; !done; i++) {
			triangleNumber += i;
			final int divisors = countDivisors(triangleNumber);
			if (divisors > maxDivisors) {
				System.out.println(triangleNumber + " " + divisors + "(" + i + ")");
				maxDivisors = divisors;
			}

			done = divisors >= 500;

		}
		System.out.println("Twelve: " + triangleNumber);
	}

	private int countDivisors(final int number) {
		int divisors = 0;
		for (int i = 1; i <= (Math.sqrt(number) + 1); i++) {
			// This is efficient since we only need to check up to Sqrt of the
			// number and each time we find a divisor we count it and its counterpart
			// so plus 2 for each match.
			if ((number % i) == 0) {
				divisors += 2;
			}
		}
		return divisors;
	}

	private void problemEleven() {
		final String inputMatrix = "08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08\r\n"
				+ "49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00\r\n"
				+ "81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65\r\n"
				+ "52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91\r\n"
				+ "22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80\r\n"
				+ "24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50\r\n"
				+ "32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70\r\n"
				+ "67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21\r\n"
				+ "24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72\r\n"
				+ "21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95\r\n"
				+ "78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92\r\n"
				+ "16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57\r\n"
				+ "86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58\r\n"
				+ "19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40\r\n"
				+ "04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66\r\n"
				+ "88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69\r\n"
				+ "04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36\r\n"
				+ "20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16\r\n"
				+ "20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54\r\n"
				+ "01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48";
		final String[] lines = inputMatrix.split("\\r\\n");
		final int matrixSize = 20;
		final int sumSize = 4;
		final int[][] matrix = new int[matrixSize][matrixSize];
		int row = 0;
		for (final String line : lines) {
			int col = 0;
			final String[] numbers = line.split("\\s");
			for (final String number : numbers) {
				matrix[row][col] = Integer.parseInt(number);
				col++;
			}
			row++;
		}

		int maxProduct = 0;

		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				// Horizontally
				int horizontalProduct = 1;
				int verticalProduct = 1;
				int rDiagonalProduct = 1;
				int lDiagonalProduct = 1;
				for (int k = 0; k < sumSize; k++) {
					if ((j + k) < matrixSize) {
						horizontalProduct *= matrix[i][j + k];
					}
					if ((i + k) < matrixSize) {
						verticalProduct *= matrix[i + k][j];
					}
					if (((i + k) < matrixSize) && ((j + k) < matrixSize)) {
						rDiagonalProduct *= matrix[i + k][j + k];
					}
					if (((i + k) < matrixSize) && ((j - k) >= 0)) {
						lDiagonalProduct *= matrix[i + k][j - k];
					}
				}

				if ((i == (matrixSize - 1)) || (j == (matrixSize - 1))) {
					final int ir = 9;
				}

				if (horizontalProduct > maxProduct) {
					maxProduct = horizontalProduct;
				}
				if (verticalProduct > maxProduct) {
					maxProduct = verticalProduct;
				}
				if (rDiagonalProduct > maxProduct) {
					maxProduct = rDiagonalProduct;
				}
				if (lDiagonalProduct > maxProduct) {
					maxProduct = lDiagonalProduct;
				}
			}
		}

		System.out.println("Eleven: " + maxProduct);
	}

	private void problemTen() {
		final int max = 2000000;
		BigInteger sum = BigInteger.ZERO;

		final ArrayList<Integer> primes = new ArrayList<Integer>();
		for (int i = 1; i < max; i++) {
			if (isPrime(i, primes)) {
				// System.out.println(i);
				sum = sum.add(BigInteger.valueOf(i));
			}
		}

		System.out.println("Ten: " + sum);
	}

	private boolean isPrime(final int i, final ArrayList<Integer> primes) {
		if (i == 1) {
			return false;
		}
		if ((i > 2) && ((i % 2) == 0)) {
			return false;
		}

		final int maxPrime = primes.isEmpty() ? 2 : primes.get(primes.size() - 1);

		for (int j = maxPrime; j < Math.sqrt(i); j += 2) {
			if ((i % j) == 0) {
				return false;
			}
		}
		for (final int prime : primes) {
			if ((i % prime) == 0) {
				return false;
			}
		}
		primes.add(i);
		return true;
	}

	private void problemNine() {
		final int max = 1000;
		for (int a = 1; a < max; a++) {
			for (int b = 1; b < max; b++) {
				for (int c = 1; c < max; c++) {
					if ((((a * a) + (b * b)) == (c * c)) && ((a + b + c) == 1000)) {
						System.out.println("nine: " + (a * b * c));
						return;
					}
				}
			}
		}
	}

	private long elapsedTime(final long startTime) {
		return System.currentTimeMillis() - startTime;
	}

	private String formatDoubleNoScientificNotation(final double formatMe) {
		return String.format("%.0f", formatMe);
	}

	/**
	 * Get the int at the startIndex location within a string of numbers
	 * 
	 * @param startIndex
	 * @param input
	 * @return
	 */
	private int getIntValueFromString(final int index, final String input) {
		return Character.getNumericValue(input.charAt(index));
	}

	public static void main(final String[] args) {

		new ProjectEuler();

	}

}
