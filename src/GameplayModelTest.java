import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author: Peter
 */
class GameplayModelTest {
    GameplayModel gpm;
    @Test
    void isAttackerSelected() {
        gpm = new GameplayModel();
        assertEquals(false, gpm.isAttackerSelected());
        gpm.setIsAttackerSelected(true);
        assertEquals(true, gpm.isAttackerSelected());
        assertNotEquals(false, gpm.isAttackerSelected());
        gpm.setIsAttackerSelected(false);
        assertEquals(false, gpm.isAttackerSelected());
        assertNotEquals(true, gpm.isAttackerSelected());
    }


    @Test
    void isDefenderSelected() {
        gpm = new GameplayModel();
        assertEquals(false, gpm.isDefenderSelected());
        gpm.setDefenderSelected(true);
        assertEquals(true, gpm.isDefenderSelected());
        assertNotEquals(false,  gpm.isDefenderSelected());
        gpm.setDefenderSelected(false);
        assertEquals(false, gpm.isAttackerSelected());
        assertNotEquals(true, gpm.isAttackerSelected());

    }

    @Test
    void isDiceSelected() {
        gpm = new GameplayModel();
        assertEquals(false, gpm.isDiceSelected());
        gpm.setDiceSelected(true);
        assertEquals(true, gpm.isDiceSelected());
        assertNotEquals(false,  gpm.isDiceSelected());
        gpm.setDiceSelected(false);
        assertEquals(false, gpm.isDiceSelected());
        assertNotEquals(true, gpm.isDiceSelected());
    }


    @Test
    void isDeployed() {
        gpm = new GameplayModel();
        assertEquals(false, gpm.isDeployed());
        gpm.setDeployed(true);
        assertEquals(true, gpm.isDeployed());
        assertNotEquals(false,  gpm.isDeployed());
        gpm.setDeployed(false);
        assertEquals(false, gpm.isDeployed());
        assertNotEquals(true, gpm.isDeployed());
    }


    @Test
    void setSelectedTerritory() {
        gpm = new GameplayModel();
        assertEquals(null, gpm.getSelectedTerritory());
        Territory afghanistan= new Territory("Afghanistan");
        Territory egypt = new Territory("Egypt");
        gpm.setSelectedTerritory(afghanistan);
        assertEquals(afghanistan, gpm.getSelectedTerritory());
        assertNotEquals(null , gpm.getSelectedTerritory());
        gpm.setSelectedTerritory(egypt);
        assertEquals(egypt, gpm.getSelectedTerritory());
        assertNotEquals(null , gpm.getSelectedTerritory());
    }
    @Test
    void checkPlayerhand(){
        gpm = new GameplayModel();
        Player player1 = new Player(gpm, "Player1");
        Territory afghanistan= new Territory("Afghanistan");
        Territory egypt = new Territory("Egypt");
        Territory brazil = new Territory("Brazil");
        Cavalry first = new Cavalry();
        Cavalry second = new Cavalry();
        Cavalry third = new Cavalry();
        Card card1 = new Card( egypt, first );
        Card card2 = new Card(brazil , second);
        Card card3 = new Card(afghanistan , third);
        player1.getHand().addCard(card1);
        player1.getHand().addCard(card2);
        gpm.setCurrentPlayer(player1);
        gpm.checkPlayerhand();
        assertEquals(2,player1.getHand().handList().size() );
        player1.getHand().addCard(card3);
        gpm.setCurrentPlayer(player1);
        gpm.checkPlayerhand();
        assertEquals(0,player1.getHand().handList().size() );

    }

    @Test
    void initializePlayers() {
        gpm = new GameplayModel();
        gpm.setPlayersAlive();
        gpm.InitializePlayers(3);
        assertEquals("0" , gpm.getPlayers(0).getName());
        assertEquals("1" ,gpm.getPlayers(1).getName() );
        assertEquals("1" ,gpm.getPlayers(1).getName() );
        assertEquals("2" ,gpm.getPlayers(2).getName() );
    }

    @Test
    void removePlayer() {
        gpm = new GameplayModel();
        gpm.setPlayersAlive();
        gpm.InitializePlayers(3);
        assertEquals("1" ,gpm.getPlayers(1).getName() );
        gpm.removePlayer(gpm.getPlayers(1));
        assertNotEquals("1" ,gpm.getPlayers(1).getName() );
    }


    @Test
    void winnerStatus() {
        gpm = new GameplayModel();
        gpm.setPlayersAlive();
        gpm.InitializePlayers(3);
        boolean x = gpm.WinnerStatus();
        assertEquals(false , x );
        gpm.removePlayer(gpm.getPlayers(1));
        gpm.removePlayer(gpm.getPlayers(0));
        boolean y = gpm.WinnerStatus();
        assertEquals(true , y );

    }

    @Test
    void stringTerritoryMapping() {
        gpm = new GameplayModel();
        gpm.setBoard(2);
        assertNotEquals(3,gpm.getBoard().getTerritoriesList()[32].getTroops());
        gpm.stringTerritoryMapping("Afghanistan" , 3);
        assertEquals(3,gpm.getBoard().getTerritoriesList()[32].getTroops() );
    }

    
    @Test
    void changePlayer() {
        gpm = new GameplayModel();
        //Player player1 = new Player("toBeRemoved");
        //Player player2 = new Player("toBeAdded");
        gpm.setPlayersAlive();
        gpm.InitializePlayers(2);
        gpm.setCurrentPlayer(gpm.getPlayers(0));
        gpm.setNextPlayer(gpm.getPlayers(1));
        assertEquals("0" , gpm.getCurrentPlayer().getName());
        gpm.changePlayer();
        assertEquals("1" , gpm.getCurrentPlayer().getName());
    }

    @Test
    void findMostPopulated(){
        gpm = new GameplayModel();
        Territory afghanistan= new Territory("Afghanistan");
        Territory egypt = new Territory("Egypt");
        egypt.setTroops(20000);
        afghanistan.setTroops(3);
        ArrayList<Territory> terrs = new ArrayList<>();
        terrs.add(egypt);
        terrs.add(afghanistan);
        assertEquals("egypt" , gpm.findMostPopulated(terrs).getName());

    }

    @Test
    void clearAllFlags(){
        gpm = new GameplayModel();
        Territory afghanistan= new Territory("Afghanistan");
        Territory egypt = new Territory("Egypt");
        egypt.setTroops(20000);
        afghanistan.setTroops(3);
        ArrayList<Territory> terrs = new ArrayList<>();
        terrs.add(egypt);
        terrs.add(afghanistan);
        egypt.setFlagAdded(true);
        afghanistan.setFlagAdded(true);
        assertEquals(true, egypt.getFlagAdded());
        assertEquals(true, afghanistan.getFlagAdded());
        gpm.clearAllFlags(terrs);
        assertEquals(false , egypt.getFlagAdded());
        assertEquals(false , afghanistan.getFlagAdded());
    }
}