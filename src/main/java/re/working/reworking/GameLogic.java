package re.working.reworking;

public class GameLogic {
    private int score;
    private String resultOfGame = "";

    public GameLogic() {
        this.score = 0;
    }

    public String checkGuess(boolean guessedHeads, CoinAnimation coin1, CoinAnimation coin2, String playerName) {
        if (playerName.equals("")) {
            return "No Player";
        }

        boolean actualOutcome1 = coin1.isHeadsup();
        boolean actualOutcome2 = coin2.isHeadsup();

        coin1.stopAnimation(actualOutcome1);
        coin2.stopAnimation(actualOutcome2);

        String currentBet = guessedHeads ? "Heads Heads" : "Tails Tails";

        switch (currentBet) {
            case "Heads Heads":
                if (actualOutcome1 && actualOutcome2) {
                    score++;
                    resultOfGame = "HH";
                    WriteFile.writeFileScores(
                            "|" + playerName + "|" + currentBet + "|" + resultOfGame
                    );
                } else if (!actualOutcome1 && !actualOutcome2) {
                    resultOfGame = "HH Lose";
                    WriteFile.writeFileScores(
                            "|" + playerName + "|" + currentBet + "|" + resultOfGame
                    );
                } else {
                    resultOfGame = "HH Flip Again";
                }
                break;

            case "Tails Tails":
                if (!actualOutcome1 && !actualOutcome2) {
                    score++;
                    resultOfGame = "TT";
                    WriteFile.writeFileScores(
                            "|" + playerName + "|" + currentBet + "|" + resultOfGame
                    );
                } else if (actualOutcome1 && actualOutcome2) {
                    resultOfGame = "TT Lose";
                    WriteFile.writeFileScores(
                            "|" + playerName + "|" + currentBet + "|" + resultOfGame
                    );
                } else {
                    resultOfGame = "TT Flip Again";
                }
                break;
        }

        return resultOfGame;
    }

    public int getScore() {
        return score;
    }
}