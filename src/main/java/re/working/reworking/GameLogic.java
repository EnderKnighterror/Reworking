package re.working.reworking;

public class GameLogic {
    private int score;
    private String resultOfGame = "";

    public GameLogic() {
        this.score = 0;
    }

    public String playGame(Boolean coin1, Boolean coin2, String currentBet, String playerName) {
        User user = JavaSQL.getUserByUsername(playerName);
        if (user == null) {
            return "No Player";
        }

        switch (currentBet) {
            case "Heads Heads":
                if (coin1 && coin2) {
                    score++;
                    resultOfGame = "HH";
                    JavaSQL.saveScore(user.getUser_id(), score);  // Save only on win
                } else if (!coin1 && !coin2) {
                    resultOfGame = "HH Lose";
                } else {
                    resultOfGame = "HH Flip Again";
                }
                break;

            case "Tails Tails":
                if (!coin1 && !coin2) {
                    score++;
                    resultOfGame = "TT";
                    JavaSQL.saveScore(user.getUser_id(), score);  // Save only on win
                } else if (coin1 && coin2) {
                    resultOfGame = "TT Lose";
                } else {
                    resultOfGame = "TT Flip Again";
                }
                break;
        }

        return resultOfGame;
    }
}