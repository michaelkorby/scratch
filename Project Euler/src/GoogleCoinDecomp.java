import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleCoinDecomp {

	public GoogleCoinDecomp() {
		final List<Integer> coinList = Arrays.asList(1, 5, 10, 25);
		final Set<Decomposition> findDecomp = findDecomp(50, 20, coinList);
		if (findDecomp == null || findDecomp.isEmpty()) {
			System.out.println("No solution available");
			return;
		}
		for (final Decomposition decomp : findDecomp) {
			System.out.println(decomp.toString());
		}
	}

	/**
	 * Find a decomposition recursively
	 *
	 * @param value - value that we are trying to decompose at this iteration
	 * @param numberOfCoins - number of coins with which we need to represent this value
	 * @param coinList - available coins
	 * @return a set of valid decompositions for this parameter
	 */
	private Set<Decomposition> findDecomp(final int value, final int numberOfCoins, final List<Integer> coinList) {
		final Set<Decomposition> allDecompositions = new HashSet<>();
		if (numberOfCoins == 1) {
			// This is the base case, there is just one coin left. Can we satisfy it?
			// Go through all of the coins to see if any of them is equal to the value we're seeking
			for (final int coin : coinList) {
				if (coin == value) {
					// This is the coin that equals the value we're looking to
					// decompose. Create a single decomposition
					// that corresponds to this coin.
					final Decomposition decomposition = new Decomposition();
					decomposition.addCoin(coin);
					allDecompositions.add(decomposition);
					return allDecompositions;
				}
			}
		} else {
			// We have a few coins to fill in. What are all the ways we can do
			// that?
			for (final int coin : coinList) {
				// For each available coin, recursively get all of the decompositions that can satisfy the remaining value after we
				// subtract this coin
				final int remainder = value - coin;
				if (remainder > 0) {

					// Recursively find all the decompositions for the remaining
					// value, using one fewer coins than we started with.
					final Set<Decomposition> childDecompositions = findDecomp(remainder, numberOfCoins - 1, coinList);
					if (!childDecompositions.isEmpty()) {
						// Breakdowns starting with this coin do exist
						for (final Decomposition decomp : childDecompositions) {
							if (decomp.notEmpty()) {
								// Add the coin from this level to the decompositions we got from the deeper level
								decomp.addCoin(coin);
							}
						}
						allDecompositions.addAll(childDecompositions);
					}
				}
			}
		}

		return allDecompositions;
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new GoogleCoinDecomp();

	}

	/**
	 * The Decomposition is a collection of coins of various denominations. The
	 * class knows how many coins there are for each denomination.
	 *
	 * @author mkorby
	 *
	 */
	private class Decomposition {
		private final Map<Integer, Integer> _coinMap = new HashMap<>();

		public boolean notEmpty() {
			return !_coinMap.isEmpty();
		}

		public void addCoin(final int coin) {
			final Integer coinCount = _coinMap.get(coin);
			if (coinCount == null) {
				_coinMap.put(coin, 1);
			} else {
				_coinMap.put(coin, coinCount + 1);
			}
		}

		@Override
		/**
		 * Hash code will be consistent with the equals mmethod.
		 */
		public int hashCode() {
			int accumulator = 0;
			for (final int coin : getCoinDenominations()) {
				accumulator += coin * 10;
				accumulator += getCountForCoin(coin);
			}
			return accumulator;
		}

		@Override
		/**
		 * Two decompositions are equal if they have the same number of each
		 * denomination of coin.
		 */
		public boolean equals(final Object obj) {
			if (obj instanceof Decomposition) {
				// First, see if the two objects being compared have the same
				// exact set of denominations.
				if (!getCoinDenominations().equals(((Decomposition) obj).getCoinDenominations())) {
					return false;
				}

				// Then, for each denomination, see if the number of coins for
				// that denomination is the same across both classes.
				for (final Integer ourCoin : getCoinDenominations()) {
					if (getCountForCoin(ourCoin) != ((Decomposition) obj).getCountForCoin(ourCoin)) {
						return false;
					}
				}
			}
			return true;
		}

		public Set<Integer> getCoinDenominations() {
			return _coinMap.keySet();
		}

		public int getCountForCoin(final Integer coin) {
			return _coinMap.get(coin);
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			for (final int coin : getCoinDenominations()) {
				builder.append(getCountForCoin(coin)).append(" ").append(coin).append("'s, ");
			}
			return builder.toString();
		}

	}

}
