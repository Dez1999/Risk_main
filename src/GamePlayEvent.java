import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;

/**
 @author  Raul, Des, Peter
 */
public class GamePlayEvent extends EventObject {
    private Player currentPlayer;
    private Hand playerHand;
    private String playerName;
    private Territory[] territories;
    private ArrayList<Continent> continents;
    private String instructions;
    private Color playerColor;
    private boolean change;
    private boolean startOver;


    public GamePlayEvent(GameplayModel gameModel, Player currentPlayer, Hand playerHand, String playername, String instruction, Territory[] territoriesList, Color playerColor, boolean startOver) {
        super(gameModel);
        this.currentPlayer = currentPlayer;
        this.playerHand = playerHand;
        this.playerName = playername;
        this.instructions = instruction;
        this.territories = territoriesList;
        this.playerColor = playerColor;
        //this.change = change;
        //this.newTerritoryList = newTerritoryList;
        this.startOver = startOver;

    }

    /**
     * Returns the current Players color
     * @return
     */
    public Color getPlayerColor() {
        return playerColor;
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

    public Territory[] getTerritories() {
        return territories;
    }

    /**
     * Returns startOver Boolean
     * @return
     */
    public boolean isStartOver() {
        return startOver;
    }
}
