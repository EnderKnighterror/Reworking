package re.working.reworking;

import java.util.Random;

public class GameLogic {

    private int score;

    public GameLogic() {
        this.score = 0;
    }

    public boolean checkGuess(boolean guessedHeads) {
        boolean actualOutcome = new Random().nextBoolean();
        if (guessedHeads == actualOutcome) {
            score++;
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }



    public boolean checkGuess(boolean guessedHeads, CoinAnimation coin1, CoinAnimation coin2) {
        boolean actualOutcome1 = coin1.isHeadsup();
        boolean actualOutcome2 = coin2.isHeadsup();

        if ((guessedHeads && (actualOutcome1 || actualOutcome2)) || (!guessedHeads && (!actualOutcome1 || !actualOutcome2))) {
            score++;
            return true;
        }
        return false;
    }
}
