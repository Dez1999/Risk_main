import java.util.ArrayList;
import java.util.EventObject;

public class GamePlayEvent extends EventObject {
    private Player currentPlayer;
    private Hand playerHand;
    private String playerName;
    private ArrayList<Territory> territories;
    private ArrayList<Continent> continents;

    private String instruction;

    public GamePlayEvent(GameplayModel gameModel, Player currentPlayer, Hand playerHand, String playername) {//(, TicTacToeModel.Status status, int x, int y))
        super(gameModel);
        this.currentPlayer = currentPlayer;
        this.playerHand = playerHand;
        this.playerName = playername;

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
}
