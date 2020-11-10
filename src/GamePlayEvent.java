import java.util.ArrayList;
import java.util.EventObject;

/**
 @author  Raul, Des, Peter
 */
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

    /**
     *  gets current player Hand object
     * @return Hand
     */
   public Hand getPlayerHand(){
        return playerHand;
    }

    /**
     * gets current player
     * @return Player
     */
    public Player getcurrentPlayer(){
        return currentPlayer;
    }

    /**
     * gets selected player Name field
      * @return String
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * gets Instructions
     * @return String
     */
    public String getInstructions() {
        return instructions;
    }
}
