import java.util.ArrayList;
import java.util.EventObject;

public class GamePlayEvent extends EventObject {
    private Player currentPlayer;
    private Hand playerHand;
    private String playerName;
    private ArrayList<Territory> territories;
    private ArrayList<Continent> continents;



    private String instructions;


    private String instruction;

    public GamePlayEvent(GameplayModel gameModel, Player currentPlayer, Hand playerHand, String playername, String instruction) {//(, TicTacToeModel.Status status, int x, int y))
        super(gameModel);
        this.currentPlayer = currentPlayer;
        this.playerHand = playerHand;
        this.playerName = playername;
        this.instructions = instruction;

    }

   public Hand getPlayerHand(){
        return playerHand;
    }

    public Player getcurrentPlayer(){
        return currentPlayer;
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getInstructions() {
        return instructions;
    }
}
