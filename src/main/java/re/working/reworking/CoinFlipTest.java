package re.working.reworking;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

//public class CoinFlipTest {
//
//    @Test
//    public void testCoinFlipRandomness() {
//        Coin coin = new Coin();
//        int headsCount = 0;
//        int tailsCount = 0;
//        int totalFlips = 1000;
//
//        for (int i = 0; i < totalFlips; i++) {
//            coin.flip();
//            if (coin.isHeads()) {
//                headsCount++;
//            } else {
//                tailsCount++;
//            }
//        }
//
//        System.out.println("Heads: " + headsCount);
//        System.out.println("Tails: " + tailsCount);
//
//        // Check if the coin flip is fair (approximately 50% heads and 50% tails)
//        Assert.assertTrue(Math.abs(headsCount - tailsCount) < (totalFlips * 0.1)); // 10% tolerance for randomness
//    }
//}
public class CoinFlipTest {

    @Test
    public void testTwoCoinFlipRandomnessAndLoseCondition() {
        Coin coin1 = new Coin();
        Coin coin2 = new Coin();
        int totalRounds = 1000;
        int loseCount = 0;

        Map<String, Integer> combinationCounts = new HashMap<>();

        for (int i = 0; i < totalRounds; i++) {
            coin1.flip();
            coin2.flip();
            String combination = (coin1.isHeads() ? "Heads" : "Tails") + "-" + (coin2.isHeads() ? "Heads" : "Tails");

            combinationCounts.put(combination, combinationCounts.getOrDefault(combination, 0) + 1);

            if (isLoseCondition(coin1, coin2)) {
                loseCount++;
            }
        }

        // Output the counts for each combination
        combinationCounts.forEach((combination, count) -> System.out.println(combination + ": " + count));

        // Check if the distribution of each combination is fair
        combinationCounts.values().forEach(count ->
                assertTrue(Math.abs(count - (totalRounds / 4)) < (totalRounds * 0.1)) // Assuming equal probability for each combination
        );

        // Check the lose condition frequency
        System.out.println("Lose Count: " + loseCount);
        // Assert based on expected lose condition probability
    }

    private boolean isLoseCondition(Coin coin1, Coin coin2) {
        // Implement the logic to determine if the combination is a lose condition
        // For example, if both coins are tails, it's a lose condition
        return !coin1.isHeads() && !coin2.isHeads();
    }
}
