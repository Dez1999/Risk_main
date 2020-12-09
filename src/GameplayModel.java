import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;


import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 * Class GamePlay
 *
 * @authors: Desmond, Peter, Raul
 */


public class GameplayModel {


    private int deployed;
    private boolean closeDiceFrame;
    public static final int MAX_DICE = 3;
    private Board board;
    private Player currentPlayer;
    private int inputPlayersplaying ;
    private JFrame AIsetup = new JFrame("Set up your AIplayers");
    private JRadioButton jplayer1 = new JRadioButton("Player 1");
    private JRadioButton jplayer2 = new JRadioButton("Player 2");
    private JRadioButton jplayer3 = new JRadioButton("Player 3");
    private JRadioButton jplayer4 = new JRadioButton("Player 4");
    private JRadioButton jplayer5 = new JRadioButton("Player 5");
    private JRadioButton jplayer6 = new JRadioButton("Player 6");
    private JButton ok = new JButton("OK");
    private ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
    private boolean UserCurrentPlayer;
    private JLabel stat = new JLabel("Choose which players you want to be AI players");
    private Player nextPlayer;
    private Die die = new Die();
    private twoDice dice2 = new twoDice();
    private threeDice dice3 = new threeDice();

    public ArrayList<Player> playersAlive;
    public ArrayList<Player> playersDead;

    public int numPlayers;
    private int initialPlayerTroopstoplace;
    private int currentPhase;

    private Territory attackingTerritory;
    private Territory selectedTerritory;
    private Territory defendingTerritory;

    private boolean exitAttack = false;  //This is global variable for attack method

    private boolean playerOwnsAttackingTerritory = false; //Check if Attacking Territory can be used
    private boolean targetTerritoryisBordering = false;
    private boolean canAttack = false;
    private int territoriesConquered = 0;

    private int bonus = 0;
    boolean gameWon = false;
    private int i;
    int userAttackingTroops = 1;
    private List<GamePlayView> GamePlayView = new ArrayList<>();


    private String instructions;

    private static String RED = "RED";
    private static String GRAY = "GRAY";
    private static String GREEN = "GREEN";
    private static String PINK = "PINK";
    private static String YELLOW = "YELLOW";
    private static String ORANGE = "ORANGE";
    private ArrayList<String> colorList;
    private Color playerColor;
    private boolean isAttackerSelected = false;
    private boolean isDefenderSelected = false;
    private boolean isDiceSelected = false;
    private boolean isDeployed = false;

    boolean isPathable = false;
    private Territory From;
    private Territory To;

    private boolean doneAiSelection = false;


    private boolean attackTerrNoOpp = false;

    public static void main(String[] args) throws InterruptedException {
        GameplayModel gamePlayModel = new GameplayModel();
    }


    /** Game Logic*/
    public GameplayModel() throws InterruptedException {
        startGame();
    }
    public int getDeployedNum() {
        return deployed;
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public boolean getCloseDiceFrame() {return this.closeDiceFrame;}
    public void startGame( ) throws InterruptedException {

        setUpColorList();
       inputNumberofPlayers() ;
        this.playersAlive = new ArrayList<Player>();
        InitializePlayers(numPlayers);
       setAIPlayers();
       if(playersAlive.get(0).isAIplayer()){
           UserCurrentPlayer = false;
       }
        board = new Board(numPlayers);
        initializeLand();

        i = 1;
        /**Sets the currentPlayer*/
        currentPlayer = getPlayers(0);

        /**Sets Next Player Turn*/
        nextPlayer = getPlayers(1);
        }

    /**
     * Method: Asks User which players will be AI
     */
    private void inputNumberAIplayers() {

        int AIPlayersplaying = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please select"));


    }

    /**
     * Method: Sets the Numebr of Ai Players depending on User Input
     * @throws InterruptedException
     */
    public void setAIPlayers() throws InterruptedException {
        AIsetup.setVisible(true);
        AIsetup.add(stat);
        int j = inputPlayersplaying;
        buttons.add(jplayer1);
        buttons.add(jplayer2);
        buttons.add(jplayer3);
        buttons.add(jplayer4);
        buttons.add(jplayer5);
        buttons.add(jplayer6);
        AIsetup.setLayout(new FlowLayout());
        stat.setSize(290,100);
        AIsetup.getContentPane().setBackground(Color.pink);
        AIsetup.setSize(300,300);
        for(int i = 0; i< inputPlayersplaying; i++ ){
            AIsetup.add(buttons.get(i));
        }
        AIsetup.remove(buttons.get(0));
        AIsetup.add(ok);

        while (!doneAiSelection){
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               if(jplayer1.isSelected()){
                   playersAlive.get(0).setAIplayer(true);
               }
                if(jplayer2.isSelected()){
                    playersAlive.get(1).setAIplayer(true);
                }
                if(jplayer3.isSelected()){
                    playersAlive.get(2).setAIplayer(true);
                }
                if(jplayer4.isSelected()){
                    playersAlive.get(3).setAIplayer(true);
                }
                if(jplayer5.isSelected()){
                    playersAlive.get(4).setAIplayer(true);
                }
                if(jplayer6.isSelected()){
                    playersAlive.get(5).setAIplayer(true);
                }
                doneAiSelection = true;
                AIsetup.dispose();
            }
        });
        }
    }


    /**
     * Method: Used to show the Initial Instructions and game Rules when the game first starts
     */
    public void displayInitialInstructions(){
        calculateBonusTroops();
        setInstructions("Player 1 begins the Game. Please choose the Territory to Deploy " + getBonus() + " Troops to");

        //Territory Ownership for First Player
        String territories = "Player 1";
        for (Territory terr : getCurrentPlayer().getTerritories()) {
            System.out.println(terr.getName() + "Player 1: Troops = " + terr.getTroops());
            territories = territories + "\n " + terr.getName() + ": Troops = " + terr.getTroops();
        }

        String gameRules = "" ;

        gameRules = printWelcome() + printRules() + "At the start of each turn each player receives 3 or more troops and" +
                " if you rule a whole continent you will get more bonus troops.";

        JOptionPane.showMessageDialog(null, gameRules,
                "Risk", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method: Sets the colors for each Player Playing
     */
    private void setUpColorList() {
        colorList = new ArrayList<>();
        colorList.add(GRAY);
        colorList.add(RED);
        colorList.add(GREEN);
        colorList.add(YELLOW);
        colorList.add(ORANGE);
        colorList.add(PINK);
    }

    public Territory getFrom() {
        return From;
    }

    public void setFrom(Territory from) {
        From = from;
    }
    public Territory getTo() {
        return To;
    }

    public void setTo(Territory to) {
        To = to;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void addGamePlayView(GamePlayView gpv){
        GamePlayView.add(gpv);

    }

    public void setUserAttackingTroops(int userAttackingTroops) {
        this.userAttackingTroops = userAttackingTroops;
    }


    public Player getNextPlayer() {
        return nextPlayer;
    }

    public boolean isExitAttack() {
        return exitAttack;
    }

    public boolean isPlayerOwnsAttackingTerritory() {
        return playerOwnsAttackingTerritory;
    }

    public Territory getSelectedTerritory() {
        return selectedTerritory;
    }

    public void setSelectedTerritory(Territory selectedTerritory) {
        this.selectedTerritory = selectedTerritory;
    }

    public boolean isAttackerSelected() {
        return isAttackerSelected;
    }

    public void setIsAttackerSelected(boolean attackerSelected) {
        isAttackerSelected = attackerSelected;
    }


    public boolean isDefenderSelected() {
        return isDefenderSelected;
    }

    public void setDefenderSelected(boolean defenderSelected) {
        isDefenderSelected = defenderSelected;
    }


    public boolean isDiceSelected() {
        return isDiceSelected;
    }

    public void setDiceSelected(boolean diceSelected) {
        isDiceSelected = diceSelected;
    }


    public boolean isDeployed() {
        return isDeployed;
    }

    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }

    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }



    /**
     * Method: User Deploys Troops
     */
    public boolean userDeploysTroops(){
        //Check Player hand
        //checkPlayerhand();

        //Calculate Troops
       //calculateBonusTroops();
        /**Method to deploy troops */

        boolean success = deployInTerritory(deployed);
        //Update Map View to show Troops being added. NEED METHOD

        return success;
    }

    void checkPlayerhand(){
        /**Checks the Players Hand*/

        //Show in GameStatus
        for (Card card : currentPlayer.getHand().handList()) {
            System.out.println("Current Player Hand:" +
                    card.getTerritoryName() + ": " + card.getClass());
        }

        currentPlayer.getHand().checkCardSet();
        boolean istradable = currentPlayer.getHand().canTurnCardsIn();
        tradeInCards(istradable); //boolean

    }

    /**Calculates the bonus troops a player receives
     *
     */
    void calculateBonusTroops() {
        checkPlayerhand();
        if (currentPlayer.getContinents().size() > 0) {
            for (int j = 0; j < currentPlayer.getContinents().size(); j++) {
                bonus = bonus + currentPlayer.getContinents().get(j).getBonusArmies();
            }

            //Put this into GameStatus
            //JOptionPane.showMessageDialog(null, "You received " + bonus + " bonus troops for the continents you are holding",
              //      "Bonus Troops", JOptionPane.INFORMATION_MESSAGE);
            //instructions = ("you received" + bonus + "bonus troops for the continents you are holding");
        }
        bonus = (currentPlayer.getTerritories().size() / 3) + bonus;
        // "" + currentPlayer.getName() + " receives " + bonus + " troops"

        //Put this into GameStatus
        gameStatus();
    }


    /**
     * Method: Returns board
     * @return
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Method: Removes cards in Players hand
     *
     * @param istradable true if Player can turn in Hands
     */
    private void tradeInCards(boolean istradable) {
        if (istradable) {
            currentPlayer.getHand().removeCards();
            bonus = bonus + 10;

            //Show in GameStatus
            JOptionPane.showMessageDialog(null, "Player's cards have been removed from Hand. Troops were added to Bonus Troops",
                    "Card Hand In", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println("Player's cards have been removed from Hand. Troops were added to Bonus Troops");
        }
    }


    /**
     * Method: Prints the Welcome message to the users
     */
    String printWelcome() {
        System.out.println();
        System.out.println("Welcome to Risk! ");
        System.out.println("Everybody wants to rule the world! ");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        return "Welcome to Risk!" + "Everybody wants to rule the world!" ;
    }

    public void setInputPlayersplaying(int n){
        this.inputPlayersplaying = n;
    }

    /**
     * Method: Method regarding the number of players
     */
    public void inputNumberofPlayers() {
            //System.out.println("Please choose how many players 2-6");
            this.inputPlayersplaying = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the number of Players Playing. Choose 2-6"));
            numPlayers = this.inputPlayersplaying;

            // JFrame playersFrame = new JFrame("number of players");
           // playersFrame.setSize(300, 100);
            // playersFrame.setLayout(new FlowLayout());
            //playersFrame.getContentPane().setBackground(Color.BLUE);
            // JLabel stat = new JLabel("Please select the number of players playing");
            //JButton twoP = new JButton("2");
            //JButton threeP = new JButton("3");
            //JButton fourP = new JButton("4");
            //JButton fiveP = new JButton("5");
            //JButton sixP = new JButton("6");
            //twoP.addActionListener(this);
            //threeP.addActionListener(this);
            //fourP.addActionListener(this);
            //fiveP.addActionListener(this);
            //sixP.addActionListener(this);
            //playersFrame.add(stat);
            //playersFrame.add(twoP);
            //playersFrame.add(threeP);
            //playersFrame.add(fourP);
            //playersFrame.add(fiveP);
            //playersFrame.add(sixP);
            //playersFrame.setVisible(true);

            try {
                //Scanner scan = new Scanner(System.in);
                //int num = 0;
                do {
                    //System.out.print(">");
                    //num = scan.nextInt();
                    if (!(inputPlayersplaying >= 1 && inputPlayersplaying <= 7)) inputPlayersplaying = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the number of Players Playing. Choose 2-6"));
                } while (!(inputPlayersplaying >= 2 && inputPlayersplaying <= 6));
                numPlayers = this.inputPlayersplaying;
                //playersFrame.setVisible(false);

            } catch (Exception e) {
                System.out.println("Sorry, only 2-6 players are allowed");
                inputNumberofPlayers();
            }
        }


    /**
     * Method: This method initializes the players in the Game
     *
     * @param num the number of players playing in the game
     */
    public void InitializePlayers(int num) {
        int j = 0;
        for (int i = 0; i < num; i++) {
            String str1 = Integer.toString(i + 1);
            playersAlive.add(new Player(this, str1));
            playersAlive.get(i).setColor(colorList.get(i));   //Set color for player
            System.out.println("player" + playersAlive.get(i).getName());
        }
        //playersAlive.get(0).setAIplayer(true);

        playersDead = new ArrayList<>();
    }

    /**
     * Method to return a player from the playersAlive list
     *
     * @param index
     * @return
     */
    public Player getPlayers(int index) {

        return playersAlive.get(index);
    }

    /**
     * Method: Removes the player from the playersAlive List
     *
     * @param player
     */
    public void removePlayer(Player player) {
        playersAlive.remove(player);
        playersDead.add(player);
    }


    void fortify() {
    }
    //FIX This: print each players own Territories. Each territory has player owner
    //NOT BEING USED?
    private void getGameStatus() {
        //Prints all Territories: Territory : Name, Troops : int, PlayerOwnerShip : player
        String playerstatus;
        for (Territory territory1 : board.getTerritoriesList()) {
            System.out.println(territory1.getName() + "-> Troops : " + territory1.getTroops() + ", Owner: Player " + territory1.getPlayer().getName());
        }
        //enterCommand();

    }


    private void printCommands() {
        System.out.println("THESE ARE THE POSSIBLE COMMANDS:\n" +
                "ATTACK: ATTACKS A COUNTRY\n" +
                "PASS: PASSES YOUR TURN\n" +
                "PRINT: PRINTS THE STATE OF THE MAP\n" +
                "DEPLOY: DEPLOY TROOPS IN THE SPECIFIED REGION \n" +
                "QUIT: QUITS THE GAME SESSION\n" +
                "HELP: SHOWS HELP\n" +
                "RULES: SHOWS THE RULES OF RISK!\n");
    }


    public boolean WinnerStatus() {

        if (playersAlive.size() == 1) {
            gameWon = true;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method: Saves the State of the Game Board
     */
    //public static void printBoard(Command command) {
    //}


    /**
     * current player quits the game
     */
    private void quit() {
        removePlayer(currentPlayer);
    }

    /**
     * Prints out rules in textbased version of game and returns them to be used in GUI.
     * @return String rules
     */
    public String printRules() {
        System.out.println
                ("Rules \n" +
                        "1. The winner is the first player to eliminate every opponent by " +
                        "capturing all the territories on the board.\n"
                        + "2. You can only attack a country that is adjacent to a country you control.\n"
                        + "3. At the start of each turn you will receive at least 3 armies or the # of territories " +
                        "your own divided by 3 (which ever one is higher).\n"
                        + "4. You can only attack a country if you own at least 2 armies in the attacking country.\n"
                        + "5. When attacking the person who is attacking can choose to roll up to 3 dice.\n"
                        + "6. The person defending can roll up to 2 dice but must have at least 2 armies in the " +
                        "defending country (if not they can only roll one dice).\n"
                        + "7. When you capture a territory, you must move at least as many armies as " +
                        "dice you rolled in your last attack.\n" +
                        " 8. Player 1 starts the game!                                 "
                );
        return "Rules \n" +
                "1. The winner is the first player to eliminate every opponent by " +
                "capturing all 42 territories on the board.\n"
                + "2. You can only attack a country that is adjacent to a country you control.\n"
                + "3. At the start of each turn you will receive at least 3 armies or the # of territories " +
                "you own divided by 3 (which ever one is higher).\n"
                + "4. You can only attack a country if you own at least 2 armies in the attacking country.\n"
                + "5. When attacking the person who is attacking can choose to roll up to 3 dice.\n"
                + "6. The person defending can roll up to 2 dice but must have at least 2 armies in the " +
                "defending country (if not they can only roll one dice).\n"
                + "7. When you capture a territory, you must move at least as many armies as " +
                "dice you rolled in your last attack.\n"
                + "8. Player 1 starts the game! ";
    }


    /**
     * Method: This method adds troops to specific Territory. CHANGE NAME
     *
     * @param t     Territory name
     * @param troop Number of troops
     * @return 0 or -1
     */
    public int stringTerritoryMapping(String t, int troop) {
        Territory addT = mapper(t);
        if (addT != null) {
            addT.addTroops(troop);

            return troop;
        }
        return -1;
    }

    /**
     * Method: Returns Territory
     *
     * @param confirm Territory String name
     * @return Terr Territory
     */
    public Territory mapper(String confirm) {
        for (Territory terr : board.getTerritoriesList())
            if (terr.getName().equals(confirm)) {
                return terr;
            }
        return null;
    }
    // consider using initialTroop variable and its getters/setters by introducing a new method that updates initialtroop count
    // replace hardcoded troop numbers with initialTroops
    /**
     * Sets the amount of initial troops each player can start out with depending on number of players. Also assigns Territory Ownership
     */
    private void initializeLand() {
        //The number of players ranges from 2 to 6, and the corresponding initial number of armies
        //is 50, 35, 30, 25, and 20 respectively, depending on the number of players
        //2 : 50 troops each
        //3: 35 troops each
        //4: 30 troops
        //5: 25
        //6: 20
        int i;
        if (numPlayers == 2) {
            //initialPlayerTroopstoplace = 50;
            for (i = 0; i < 16; i++) {
                board.getTerritoriesList()[i].addTroops(2);
                board.getTerritoriesList()[i].changeOwner(getPlayers(0));
                getPlayers(0).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 16; i <= 36; i++) {
                board.getTerritoriesList()[i].addTroops(2);
                board.getTerritoriesList()[i].changeOwner(getPlayers(1));
                getPlayers(1).addTerritories(board.getTerritoriesList()[i]);
            }
        } else if (numPlayers == 3) {
            //initialPlayerTroopstoplace = 35;
            for (i = 0; i < 12; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(0));
                getPlayers(0).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 12; i < 24; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(1));
                getPlayers(1).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 24; i <= 36; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(2));
                getPlayers(2).addTerritories(board.getTerritoriesList()[i]);
            }
        } else if (numPlayers == 4) {
            //initialPlayerTroopstoplace = 30;
            for (i = 0; i < 8; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(0));
                getPlayers(0).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 8; i < 17; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(1));
                getPlayers(1).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 17; i < 26; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(2));
                getPlayers(2).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 26; i <= 36; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(3));
                getPlayers(3).addTerritories(board.getTerritoriesList()[i]);
            }

        } else if (numPlayers == 5) {
            //initialPlayerTroopstoplace = 25;
            for (i = 0; i < 7; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(0));
                getPlayers(0).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 7; i < 14; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(1));
                getPlayers(1).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 14; i < 21; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(2));
                getPlayers(2).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 21; i < 29; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(3));
                getPlayers(3).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 29; i <= 36; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(4));
                getPlayers(4).addTerritories(board.getTerritoriesList()[i]);
            }

        } else if (numPlayers == 6) {
            //initialPlayerTroopstoplace = 20;
            for (i = 0; i < 6; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(0));
                getPlayers(0).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 6; i < 12; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(1));
                getPlayers(1).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 12; i < 18; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(2));
                getPlayers(2).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 18; i < 24; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(3));
                getPlayers(3).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 24; i < 30; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(4));
                getPlayers(4).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 30; i <= 36; i++) {
                board.getTerritoriesList()[i].addTroops(3);
                board.getTerritoriesList()[i].changeOwner(getPlayers(5));
                getPlayers(5).addTerritories(board.getTerritoriesList()[i]);
            }
        }
    }


    /**
     * Returns the number of troops that will be given to each player
     *
     * @return initialPlayerTroopstoplace
     */
    private int getInitialPlayerTroopstoplace() {
        return initialPlayerTroopstoplace;
    }

    /**
     * Method: List the territories owned
     *
     * @param territoryList
     */
    private void listTheTerritories(ArrayList<Territory> territoryList) {
        System.out.println();
        System.out.println("Territories Owned: ");
        for (Territory terr : territoryList) {
            System.out.println(terr.getName() + ": Troops = " + terr.getTroops());
        }
    }

    /**
     * Method: Lists the Continents owned
     *
     * @param continentList
     */
    private void listTheContinents(ArrayList<Continent> continentList) {
        System.out.println();
        System.out.println("Continents Owned: ");

        if (continentList.size() == 0) {
            System.out.println("No Continents Owned");
        } else {
            for (Continent cont : continentList) {
                System.out.println(cont.getName());
            }
        }
    }

    /**
     * Method: Used to deploy troops to selected Territory
     *
     * @param newTroops
     */
    public void DeployTroops(int newTroops) {
        //listTheTerritories(currentPlayer.getTerritories());
        //listTheContinents(currentPlayer.getContinents());

        //System.out.println("Add Troops to:");

        //deployInTerritory(newTroops);

        //listTheTerritories(currentPlayer.getTerritories());
        //System.out.println();
        //board.getTer(g).addTroops(1);
    }

    /**
     * Method: Used to check if selected Territory is owned by Player. Then helps to deploy troops into selected Territory
     *
     * @param newTroops
     */
    private boolean deployInTerritory(int newTroops) {
        //Scanner s = new Scanner(System.in);
        //String addingToTerritory;
        //addingToTerritory = s.nextLine();
        int temp = currentPlayer.getTerritories().size() - 1; // store last element in dynamic array

        for (Territory terr : currentPlayer.getTerritories()) {
            if (terr.getName().equals(selectedTerritory.getName())) {//deploying troops if == true
                deployed = stringTerritoryMapping(selectedTerritory.getName(), newTroops);
                return true;
            } else if (terr.getName().equals((currentPlayer.getTerritories().get(temp)).getName())) {

                System.out.println("You have entered an invalid Territory");
                System.out.println("Enter a Territory to deploy troops to");
                return false;

            }
        }
        return false;
    }

    /**
     * gets current player
     * @return Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    //for testing
    public void setBoard(int x){
        board = new Board(x);
    }

    //testing
    public void setNumPlayers(int in){
        this.numPlayers = in;
    }

    public void setPlayersAlive(){
        this.playersAlive = new ArrayList<Player>();
    }

    /**
     * Method: Changes the current Players Color to indicate whos turn it is
     */
    public void changeColor(){
        if (currentPlayer.getColor().equals("RED")){
            playerColor = new Color(184, 59, 59);
        }
        else if(currentPlayer.getColor().equals("ORANGE")){
            playerColor = new Color(
                    181, 121, 224);
        }
        else if(currentPlayer.getColor().equals("GRAY")){
            playerColor = Color.GRAY;
        }
        else if(currentPlayer.getColor().equals("GREEN")){
            playerColor = new Color(73, 186, 58);
        }
        else if(currentPlayer.getColor().equals("YELLOW")){
            playerColor = new Color(224, 227, 75);
        }
        else if(currentPlayer.getColor().equals("PINK")){
            playerColor = Color.PINK;
        }
    }

    /**
     * Currently unused in GUI implementation.
     */
    public void attack() {
        //Scanner s = new Scanner(System.in);

        //Check if Current Player Owns Attacking Territory
        checkAttackingOwnership();

        //Check if Defending Territory can Be used
        if (playerOwnsAttackingTerritory) {
            isTargetBordering();
        }

        //Check if TargetIsBorderingterritory = true. targetTerritoryisBordering() to check if Number of Attacking Troops is correct
        if (targetTerritoryisBordering) {
            chooseAttackingTroops();   //Sets Number of Attacking Troops
        }

    }

    /**
     * Method: Chooses number of Attacking Troops to use
     */
    public void chooseAttackingTroops() {
        canAttack = false;
        exitAttack = false;
        Scanner s = new Scanner(System.in);
        int troopsAmount = attackingTerritory.getTroops();  //Returns number of troops in selected Attacking Territory

        while (!canAttack && !exitAttack) {
            //System.out.println("Please choose the Number of Troops to Attack with. Press BACK to exit ATTACK phase");

            //int stringword = s.nextInt();

            if (userAttackingTroops >= 1 && userAttackingTroops <= 3) {
                attackTroopLogic(userAttackingTroops);
                //Need to exit while loop if canAttack is true

            } else if (userAttackingTroops > 3) {

                //Show GameStatus

                //JOptionPane.showMessageDialog(null, "You cannot attack with more than 3 dice at one time. Please attack with 1-3 dice.",
                      //  "Number of Dice", JOptionPane.INFORMATION_MESSAGE);
                //exitAttack = true;
            } else {

                //Show GameStatus
                //JOptionPane.showMessageDialog(null, "You did not enter the right amount of Dice to Attack with",
                      // "Number of Dice", JOptionPane.INFORMATION_MESSAGE);
                //exitAttack = true;
            }

        }//End of While loop

    }

    /**
     * Method: Used to figure out the Troop Logic. The number of troops for each Battle/Roll
     *
     * @param troops number of troops selected by User
     */
    private void attackTroopLogic(int troops) {
        if (attackingTerritory.getTroops() > troops) {
            //check amount of defending troops can be used
            if (defendingTerritory.getTroops() == 1) {
                int defendingTroops = 1;
                rollDiceSuccess(troops, defendingTroops);  //Run Dice Roll
                canAttack = true;
            } else if (defendingTerritory.getTroops() > 1) {
                int defendingTroops = 2;
                rollDiceSuccess(troops, defendingTroops);
                canAttack = true;
            } else if (defendingTerritory.getTroops() == 0) {

                //GameStatus
                System.out.println("ERROR: defending Territory has no troops");
                exitAttack = true;
            }
        } else {

            //GameStatus
            JOptionPane.showMessageDialog(null, "Attacking Territory does not have enough troops to Attack",
                    "Number of Troops", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Attacking Territory does not have enough troops to Attack");

            exitAttack = true;

        }
    }

    /**
     * Method: Battle Logic using RollDice()
     *
     * @param attackingDice
     * @param defendingDice
     */
    private void rollDiceSuccess(int attackingDice, int defendingDice) {
        int a1;
        int a2;
        int d1;
        int d2;
        int attackLoss = 0;
        int defendLoss = 0;
        int attackingTroops = attackingDice;
        int defendingTroops = defendingDice;


        //1 Attacking Dice
        if (attackingDice == 1) {
            if (defendingTroops == 1) {
                a1 = die.roll();
                d1 = die.roll();

                if (a1 > d1) {
                    attackLoss = 0;
                    defendLoss = 1;
                    attackOutcome(1, attackLoss, defendLoss, attackingTroops);
                } else {
                    attackLoss = 1;
                    defendLoss = 0;
                    attackOutcome(-1, attackLoss, defendLoss, attackingTroops);
                }
            } else { //def troops == 2
                a1 = die.roll();
                dice2.roll();
                d1 = dice2.getHighest();
                d2 = dice2.getSecondHighest();

                if (a1 > d1) {
                    attackLoss = 0;
                    defendLoss = 1;
                    attackOutcome(1, attackLoss, defendLoss, attackingTroops);
                } else {
                    attackLoss = 1;
                    defendLoss = 0;
                    attackOutcome(-1, attackLoss, defendLoss, attackingTroops);
                }
            }
        }

        //2 Attacking Dice
        else if (attackingDice == 2) {
            if (defendingTroops == 1) {
                dice2.roll();
                a1 = dice2.getHighest();
                a2 = dice2.getSecondHighest();
                d1 = die.roll();

                if (a1 > d1 || a2 > d1) {
                    attackLoss = 0;
                    defendLoss = 1;
                    attackOutcome(1, attackLoss, defendLoss, attackingTroops);
                } else {
                    attackLoss = 1;
                    defendLoss = 0;
                    attackOutcome(-1, attackLoss, defendLoss, attackingTroops);
                }
            } else { //def troops == 2
                dice2.roll();
                a1 = dice2.getHighest();
                a2 = dice2.getSecondHighest();

                dice2.roll();
                d1 = dice2.getHighest();
                d2 = dice2.getSecondHighest();

                if (a1 > d1) {
                    attackLoss = 0;
                    defendLoss = 1;

                    if (a2 > d2) {
                        attackLoss = 0;
                        defendLoss = 2;
                        attackOutcome(1, attackLoss, defendLoss, attackingTroops);
                    } else {
                        attackLoss = 1;
                        defendLoss = 1;
                        attackOutcome(0, attackLoss, defendLoss, attackingTroops);
                    }
                } else if (a1 <= d1) {
                    attackLoss = 1;
                    defendLoss = 0;

                    if (a2 > d2) {
                        attackLoss = 1;
                        defendLoss = 1;
                        attackOutcome(0, attackLoss, defendLoss, attackingTroops);
                    } else {
                        attackLoss = 2;
                        defendLoss = 0;
                        attackOutcome(-1, attackLoss, defendLoss, attackingTroops);

                    }
                }
            }

        }

        //3 Attacking Dice
        else if (attackingDice == 3) {
            if (defendingTroops == 1) {
                dice3.roll();
                a1 = dice2.getHighest();
                a2 = dice2.getSecondHighest();
                d1 = die.roll();

                if (a1 > d1 || a2 > d1) {
                    attackLoss = 0;
                    defendLoss = 1;
                    attackOutcome(1, attackLoss, defendLoss, attackingTroops);
                } else {
                    attackLoss = 1;
                    defendLoss = 0;
                    attackOutcome(-1, attackLoss, defendLoss, attackingTroops);
                }
            } else {
                dice3.roll();
                a1 = dice3.getHighest();
                a2 = dice3.getSecondHighest();

                dice2.roll();
                d1 = dice2.getHighest();
                d2 = dice2.getSecondHighest();

                if (a1 > d1) {
                    attackLoss = 0;
                    defendLoss = 1;

                    if (a2 > d2) {
                        attackLoss = 0;
                        defendLoss = 2;
                        attackOutcome(1, attackLoss, defendLoss, attackingTroops);
                    } else {
                        attackLoss = 1;
                        defendLoss = 1;
                        attackOutcome(0, attackLoss, defendLoss, attackingTroops);
                    }
                } else if (a1 <= d1) {
                    attackLoss = 1;
                    defendLoss = 0;

                    if (a2 > d2) {
                        attackLoss = 1;
                        defendLoss = 1;
                        attackOutcome(0, attackLoss, defendLoss, attackingTroops);
                    } else {
                        attackLoss = 2;
                        defendLoss = 0;
                        attackOutcome(-1, attackLoss, defendLoss, attackingTroops);
                    }
                }

            }

        }
    }

    /**
     * calculate the outcome of an attack and remove troops from losing territory. This method also handles the transfer of ownership for a territory
     * and hands out territory cards by calling helper methods.
     * @param rollResult
     * @param attackLoss
     * @param defendLoss
     * @param attackingTroops
     */
    private void attackOutcome(int rollResult, int attackLoss, int defendLoss, int attackingTroops) {
        //setTroops
        Player prevOwnerPlayer = defendingTerritory.getPlayer();
        String prevOwnerName = defendingTerritory.getPlayer().getName();
        closeDiceFrame = false;
        //Attacker Wins
        if (rollResult == 1) {
            defendingTerritory.removeTroops(defendLoss);

            if (defendingTerritory.getTroops() <= 0) {

                closeDiceFrame = true;
                //Show GameStatus
               JOptionPane.showMessageDialog(null, attackingTerritory.getName() + " has won the battle. " + defendingTerritory.getName() + " has lost " + defendLoss + " troops. " +
                                attackingTerritory.getName() + " has conquered " + defendingTerritory.getName(),
                        "Attacking Territory Has Won the Battle", JOptionPane.INFORMATION_MESSAGE);

                System.out.println(attackingTerritory.getName() + " has won the battle. " + defendingTerritory.getName() + " has lost " + defendLoss + " troops");
                System.out.println(attackingTerritory.getName() + " has conquered " + defendingTerritory.getName());
                territoriesConquered++;

                defendingTerritory.changeOwner(currentPlayer);
                defendingTerritory.getPlayer().removeTerritories(defendingTerritory);
                currentPlayer.addTerritories(defendingTerritory);
                prevOwnerPlayer.removeTerritories(defendingTerritory);

                //Method to move attacking Troops
                defendingTerritory.addTroops(attackingTroops);
                attackingTerritory.removeTroops(attackingTroops);

                //Method to give currentPlayer a new Card if this is it's first attack in the Turn
                if (territoriesConquered == 1) {
                    Card newCard = board.getDeck().drawCard();
                    currentPlayer.getHand().addCard(newCard);
                    closeDiceFrame = true;
                    //Show GameStatus
                    //Players Receives a new Card
                }

                if (defendingTerritory.getPlayer().getTerritories().isEmpty()) {
                    //Remove player from game
                    closeDiceFrame = true;
                    kill(prevOwnerPlayer);
                }
            }
            else {
                closeDiceFrame = true;
                //Show GameStatus
                JOptionPane.showMessageDialog(null, attackingTerritory.getName() + " has won the battle. " + defendingTerritory.getName() + " has lost " + defendLoss + " troops",
                        "Attacking Territory Has Won the Battle", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(attackingTerritory.getName() + " has won the battle. " + defendingTerritory.getName() + " has lost " + defendLoss + " troops");
            }
        }


        //Defender Wins
        else if(rollResult == -1){
            attackingTerritory.removeTroops(attackLoss);
            closeDiceFrame = true;

            //Show GameStatus
            JOptionPane.showMessageDialog(null, defendingTerritory.getName() + " has won the battle. " + attackingTerritory.getName() + " has lost " + attackLoss + " troops",
                    "Defending Territory Has Won the Battle", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(defendingTerritory.getName() + " has won the battle. " + attackingTerritory.getName() + " has lost " + attackLoss + " troops");
        }

        //There was a Tie
        else if (rollResult == 0){
            closeDiceFrame = true;
            attackingTerritory.removeTroops(attackLoss); //expecting problem handling empty territory incase of tie in a 2v1 battle. 2v1 => 1v0  after tie
            defendingTerritory.removeTroops(defendLoss);

            if(defendingTerritory.getTroops()<=0) {
                closeDiceFrame = true;
                //Show GameStatus

                JOptionPane.showMessageDialog(null, defendingTerritory.getName() + " tied with  " + attackingTerritory.getName() + ". " +
                                attackingTerritory.getName() +
                                " has lost " + attackLoss + " troops. " +  defendingTerritory.getName() + " has lost " + defendLoss + " troops." + attackingTerritory.getName() + " has conquered " + defendingTerritory.getName(),
                        "Territory Conquered", JOptionPane.INFORMATION_MESSAGE);

                System.out.println(defendingTerritory.getName() + " tied with " + attackingTerritory.getName() + ". ");
                System.out.println(attackingTerritory.getName() +
                        " has lost " + attackLoss + " troops. " +  defendingTerritory.getName() + " has lost " + defendLoss + " troops");
                System.out.println(attackingTerritory.getName() + " has conquered " + defendingTerritory.getName());
                territoriesConquered++;

                defendingTerritory.changeOwner(currentPlayer);
                defendingTerritory.getPlayer().removeTerritories(defendingTerritory);
                currentPlayer.addTerritories(defendingTerritory);
                prevOwnerPlayer.removeTerritories(defendingTerritory);

                //Method to move attacking Troops
                defendingTerritory.addTroops(attackingTroops);
                attackingTerritory.removeTroops(attackingTroops);

                //Method to give currentPlayer a new Card if this is it's first attack in the Turn
                if (territoriesConquered == 1){
                    Card newCard = board.getDeck().drawCard();
                    currentPlayer.getHand().addCard(newCard);

                    //Show GameStatus
                    //Player receives a new Card
                }

                //Check if defending player is killed
                if (defendingTerritory.getPlayer().getTerritories().isEmpty()) {
                    //Remove player from game
                    kill(prevOwnerPlayer);
                }
            }
            else {
                closeDiceFrame = true;
                //Show GameStatus
                JOptionPane.showMessageDialog(null, defendingTerritory.getName() + " tied with  " + attackingTerritory.getName() + ". " +
                                attackingTerritory.getName() +
                                " has lost " + attackLoss + " troops. " +  defendingTerritory.getName() + " has lost " + defendLoss + " troops",
                        "Battle Tied", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(defendingTerritory.getName() + " tied with " + attackingTerritory.getName() + ". ");
                System.out.println(attackingTerritory.getName() +
                        " has lost " + attackLoss + " troops. " +  defendingTerritory.getName() + " has lost " + defendLoss + " troops");

            }
        }
    }


    /**
     * Removes player from list of players Alive, Adds to list of players dead.
     * @param prevOwnerPlayer
     */
    private void kill(Player prevOwnerPlayer) {
        playersAlive.remove(prevOwnerPlayer);
        playersDead.add(prevOwnerPlayer);
        prevOwnerPlayer.setDead();

        /**Fix logic below
        //if (nextPlayer == prevOwnerPlayer){
       //     nextPlayer =
       // }
         */

        //Show GameStatus
        JOptionPane.showMessageDialog(null, "Player " + prevOwnerPlayer.getName() + " is killed.",
                "Player is Killed", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Player " + prevOwnerPlayer.getName() + " is killed.");
    }

    /**
     * Checks if Target is Bordering Attacking Territory
     */
    public void isTargetBordering() {
        exitAttack = false;
        targetTerritoryisBordering = false;
        //Scanner s = new Scanner(System.in);
        while (!targetTerritoryisBordering && !exitAttack) {
            //System.out.println("Please choose the Target territory. Press BACK to exit attack phase");
            //String Target = s.nextLine();

            //Check Target territory is bordering Attacking Territory and Not currentPlayers Territory
            if (defendingTerritory.getPlayer() != currentPlayer) {
                for (Territory borderterr : attackingTerritory.getBorderTerritories()) {
                    if (borderterr == defendingTerritory) {
                        targetTerritoryisBordering = true;
                        exitAttack = false;
                    }
                    else{
                        if(targetTerritoryisBordering == false) {
                            exitAttack = true;
                        }
                    }
                }
            }

            //Check if currentPlayer owns Target Territory. THIS MIGHT NOT BE NEEDED FOR GUI
            else if (defendingTerritory.getPlayer() == currentPlayer) {
                targetTerritoryisBordering = false;
                exitAttack = true;
            }

            //Check if TargetTerrirtory is not bordering Attacking territory
            else {
                for (Territory borderterr : attackingTerritory.getBorderTerritories()) {
                    if (borderterr != defendingTerritory) {
                        exitAttack = true;
                    }
                }
            }

        } //End of While Loop
    }

    /**
     * Method: Checks if currentPlayer Owns Attacking Territory
     */
    public void checkAttackingOwnership() {
        resetNumberOppTerritories();
        attackTerrNoOpp = false;
        exitAttack = false;
        playerOwnsAttackingTerritory = false;
        //Scanner s = new Scanner(System.in);
        while (!playerOwnsAttackingTerritory && !exitAttack) {
            //System.out.println("Please choose the attacking territory. Press BACK to exit attack method");

            if(attackingTerritory.getPlayer() != currentPlayer){
                exitAttack = true;
                //System.out.println("You must own the attacking Territory");

            }
            //Check if current player owns AttackingTerritory
            else if (attackingTerritory.getPlayer() == currentPlayer) {
                //check if Attacking Territory has Opp adjacent Territories
                //Check all Territories Owned and deploy to Territory that has the most adjacent Opponent Territories
                setNumberOppTerrirtories();

                if(attackingTerritory.getNumberOppTerritories() > 0){
                    playerOwnsAttackingTerritory = true;
                }
                else{
                    attackTerrNoOpp = true;
                    exitAttack = true;

                }
            }
        }
    }

    /**
     * Method: Sets the number of Opponent Territories that each Territory for the Current Player has
     */
    private void setNumberOppTerrirtories() {
        for (Territory terr : currentPlayer.getTerritories()){
            for(Territory adjTerr : terr.getBorderTerritories()){
                if(adjTerr.getPlayer() != terr.getPlayer()){
                    terr.addNumberOppTerr(1);
                }
            }
        }
    }

    /**
     * Returns boolean variable if Attacking Territory has no Opponent Bordering Territory
     * @return
     */
    public boolean noOppBorderingTerr() {
        return attackTerrNoOpp;
    }

    public void setAttackingTerritory(Territory attackingTerritory) {
        this.attackingTerritory = attackingTerritory;
    }

    public void setDefendingTerritory(Territory defendingTerritory) {
        this.defendingTerritory = defendingTerritory;
    }


    /**THIS NEEDS TO BE REFINED*/
    /**
     *  changes current player turn
     */
    public void changePlayer() {

        //for (int i = 0; i < playersAlive.size(); i++) {
        //  bonus = 0; //Reset bonus troops

        /**Sets the currentPlayer*/
        currentPlayer = nextPlayer;

        //Show in GameStatus
        //System.out.println("It is " + currentPlayer.getName() + "'s turn");

        /**Set Next Player Turn*/
        if (getPlayers(playersAlive.size() -1) == currentPlayer) {
            nextPlayer = getPlayers(0);
            i = 0;
        }
        else if (getPlayers(playersAlive.size() -1) != currentPlayer) {
            i++;
            nextPlayer = getPlayers(i);
        }

        /*Reset Territories conquered in the current Turn to 0*/
        territoriesConquered = 0;
    }

    /**
     * Method: Shows gameStatus in JLabel
     */
    public void gameStatus(){
        //Shows currentPlayer's turn
        //Shows currentPlayer's Hand
        //Shows extra message to show User
        String tellUser = instructions;
        changeColor();

        //Add Instructions
        //Check to see if the player is the correct one


        for (GamePlayView tttv: GamePlayView)
            tttv.handleGamePlayUpdate(new GamePlayEvent(this, currentPlayer, currentPlayer.getHand(), currentPlayer.getName(),instructions, board.getTerritoriesList(), playerColor));
    }

    /**
     * Method: UpDates View Board Status
     */
    public void updateBoardStatus(){
        //updates Board with correct Territory Names, Owner Names, Troop Count

    }

    public void setNextPlayer(Player player) {
        this.nextPlayer = player;
    }

    public int getBonus() {
        return bonus;
    }

    /**
     * Performs all the functions for the AI player
     */
    public void AIUtilityFunction() throws InterruptedException {
        AIdeploy();
        //   wait(100);

        //update GameStatus
        setInstructions("AI Player " + currentPlayer.getName() + " has finished the Deploy Phase");
        gameStatus();

        //TimeUnit.SECONDS.sleep(1);  //Not Necessary

        /**
         * FIX: AI will Sometimes Attack own Territory. Attacking Territory is correct. Check how Defending Territory is Chosen
         */
        AIattack();
        setInstructions("AI Player " + currentPlayer.getName() + " has finished the Attack Phase");
        gameStatus();

        TimeUnit.SECONDS.sleep(1);

        //    wait(100);

        //fortify()

        //   wait(100);

        resetNumberOppTerritories();

        AInextTurn();

        //  wait(100);
    }

    /**
     * Method: Resets Numebr of Opponent Territories for Current Player
     */
    private void resetNumberOppTerritories() {
        for (Territory terr : currentPlayer.getTerritories()){
            terr.setNumberOppTerr(0);
        }
    }

    /**
     * Deploy Method: helps the AI player choose which Territory to deploy troops to at the start of the turn
     */
    public void AIdeploy() throws InterruptedException {
        Territory highestOppTerritory = new Territory("HighestOpponentTerritories");
        bonus = 0;
        calculateBonusTroops();
        //checkPlayerhand();  //Checks to see if AI can trade in Cards for Extra Bonus Troops

        //Check all Territories Owned and deploy to Territory that has the most adjacent Opponent Territories
        setNumberOppTerrirtories();

        //Finds the Territory with the highest Number of Opponent Territories
        for(Territory terr : currentPlayer.getTerritories()){
            if (terr.getNumberOppTerritories() >= highestOppTerritory.getNumberOppTerritories()){
                highestOppTerritory = terr;
            }
        }

        //Adds Bonus Troops to highestOppTerritory (Territory with the highest Number of Opponent Territories
        for(Territory terr : currentPlayer.getTerritories()){
            if (highestOppTerritory == terr){
                //Now Deploy Troops to highestOppTerritory
                highestOppTerritory.addTroops(bonus);
            }
        }

        //Extra Message for Other Users
        Component frame = new Frame();
        JOptionPane.showMessageDialog(frame, "AI Player " + currentPlayer.getName() + " has chosen to deploy " + bonus + " troops to " + highestOppTerritory.getName());

    }

    /**
     * Attack Method: helps AI player perform an Attack in the game. AI will continue to Attack until it is not Ideal anymore
     */
    public void AIattack() throws InterruptedException {

        boolean doneAttacking = false;
        int count = 0;

        while(!doneAttacking) {

            //For loop to see which Territories are bordering Opponent Territories
            //Add Available options to a list:
            //CHECK THESE LISTS!!!!
            ArrayList<Territory> TerrWith3plus = AiTerrAttack(3);  //returns all Territories with more than 3 troops
            ArrayList<Territory> TerrWith2plus = AiTerrAttack(2);  //returns all Territories with more than 2 troops: NEED TO REMOVE
            ArrayList<Territory> TerrWith1plus = AiTerrAttack(1);  //returns all Territories with more than 1 troops

            Territory terrAgainstMostPop = new Territory("terrAgainstMostPop");
            Territory terrBiggestThreat = new Territory("terrBiggestThreat");
            ArrayList<Territory> terrWeakestThreats = new ArrayList<>();
            Territory bestAttackingTerritory;

            Territory chosenTargetTerritory;
            Territory chosenAttackingTerritory;

            //Determine the Target using 1 and 2.
            //1) With the most populated Territory -> return Target   : Find most populated terr, return enemy neighbouring Territory with least troops to attack
            //2) The biggest Threat  -> return BiggestThreat Target
            //3) list of weakest threat

            //Develop Rankings for Territory Attack Mode
            //1) With the most populated Territory -> return Target
            terrAgainstMostPop = findMostPopulated(TerrWith1plus); //Find most populated terr, return enemy neighbouring Territory with least troops to attack

            //2) The biggest Threat  -> return BiggestThreat Target
            terrBiggestThreat = returnBiggestOppTerr(TerrWith1plus);

            //Choose which Target Territory to Attack (terrAgainstMostPop, terrBiggestThreat,noAttack)
            chosenTargetTerritory = chooseBestTargetTerritory(TerrWith1plus, TerrWith3plus, terrAgainstMostPop, terrBiggestThreat);

            bestAttackingTerritory = chooseBestAttackingTerritroy();

            if(bestAttackingTerritory.getTroops() >= 10 && defendingTerritory.getPlayer() != currentPlayer){
                //defendingTerritory = smallestOppTerr(bestAttackingTerritory);
                attackingTerritory = bestAttackingTerritory;


                int numberDice = aiChooseNumberAttackingDie();

                Component frame = new Frame();
                //JOptionPane.showMessageDialog(frame, "AI Player " + currentPlayer.getName() + " has chosen: " + attackingTerritory.getName() + ": Attacking Territory. " +
                //      defendingTerritory.getName() + ": Defending Territory");

                attackTroopLogic(numberDice);
                count++;
                setInstructions("AI Player" + currentPlayer.getName() + " has finished a battle");
                gameStatus();

                if(count == 6){
                    doneAttacking = true;
                }

            }

            else if (chosenTargetTerritory == terrAgainstMostPop || chosenTargetTerritory == terrBiggestThreat) {
                //Find Attacking Territory
                chosenAttackingTerritory = aiFindAttackingTerr(chosenTargetTerritory);
                attackingTerritory = chosenAttackingTerritory;
                defendingTerritory = chosenTargetTerritory;

                int numberDice = aiChooseNumberAttackingDie();

                Component frame = new Frame();
                //JOptionPane.showMessageDialog(frame, "AI Player " + currentPlayer.getName() + " has chosen: " + attackingTerritory.getName() + ": Attacking Territory. " +
                //      defendingTerritory.getName() + ": Defending Territory");

                attackTroopLogic(numberDice);
                count++;
                setInstructions("AI Player" + currentPlayer.getName() + " has finished a battle");
                gameStatus();

                if(count == 6){
                    doneAttacking = true;
                }
            } else if (chosenTargetTerritory.getName().equals("DoNotAttack")) {
                //StandBy: No Attack
                Component frame = new Frame();
                //JOptionPane.showMessageDialog(frame, "AI Player " + currentPlayer.getName() + " has chosen to Not Attack ");
                doneAttacking = true;
            }
        }





        //3) Standby No Attack
                //Not being threatened
                //No Territory with more than 3 troops against a Territory 1 troop
                //

        //rankTarget() -> chooses the best target and attacking Territory





        //Choose Which territories can attack()
        //For loop to see which Territories are bordering Opponent Territories
        //Add Available options to a list
        //ArrayList<Territory> TerrWith3plus = AiTerrAttack(3);  //returns all Territories with more than 3 troops
        //ArrayList<Territory> TerrWith2plus = AiTerrAttack(2);  //returns all Territories with more than 2 troops: NEED TO REMOVE
        //ArrayList<Territory> TerrWith1plus = AiTerrAttack(1);  //returns all Territories with more than 1 troops

        //Of the available Territories: Select which Territories have more than 3 troops
        /*
        if (TerrWith3plus.size() > 0 ){

            //Of the available Territories: Select which Territories have more than 3 troops
            //Territories with more than 3 troops:
            //Choose Territory with more troops than opponent Territory as best option
            //else: Choose Territory with equal amount of troops as opponent Territory
            //else: exit attack
            for (Territory terr : TerrWith3plus){
                for(Territory adjTerr : terr.getBorderTerritories()){
                    if(terr.getTroops() > adjTerr.getTroops()){

                    }
                }
            }

        }
        //if No Territories have more than 3 troops: Choose Territories with 3 troops
        else if ((TerrWith2plus.size() > 0 )){

            //if No Territories have more than 3 troops: Choose Territories with 3 troops
            //Territories with 3 troops
            //Choose Territory with more troops than opponent Territory as best option
            //else: Choose Territory with equal amount of troops as opponent Territory
            //else: exit attack

        }
        //if No Territories have more than 2 troops: Choose Territories with 2 troops
        else if ((TerrWith1plus.size() > 0 )){
            //if No Territories have more than 2 troops: Choose Territories with 2 troops
            //Territories with 2 troops
            //Choose Territory with more troops than opponent Territory as best option
            //else: Choose Territory with equal amount of troops as opponent Territory
            //else: exit attack

        }
        //If all Territories have 1 troop:
        else {
            //exit Attack and next player Turn
        }

        //wait(100);

         */
    }

    private Territory smallestOppTerr(Territory bestAttackingTerritory) {
        Territory smallest = new Territory("smallest");
        smallest.setTroops(0);
        for(Territory terr : bestAttackingTerritory.getBorderTerritories()){
            if(terr.getTroops() < smallest.getTroops() && terr.getPlayer()!= currentPlayer)
                smallest = terr;
        }
        defendingTerritory = smallest;
        return smallest;
    }

    /**
     * Helps AI Player find Best Attacking Territory
     * @return
     */
    private Territory chooseBestAttackingTerritroy() {
        Territory bestAttackingTerritory = new Territory("bestAttackingTrritory");
        bestAttackingTerritory.setTroops(0);
        for (Territory terr : currentPlayer.getTerritories()){
            if (terr.getTroops() > bestAttackingTerritory.getTroops()){
                bestAttackingTerritory = terr;
            }
        }
        return bestAttackingTerritory;
    }

    /**
     * Method: Helps AI choose Number of Attacking Dice
     * @return
     */
    private int aiChooseNumberAttackingDie() {

        if(attackingTerritory.getTroops() > 3) {
            return 3;
        }
        else if (attackingTerritory.getTroops() == 3){
            return 2;
        }
        else {
            return 1;
        }
    }

    /**
     * Method: Helps AI find the Attacking Territory based on the chosen Target Territory
     * @param chosenTargetTerritory
     * @return highestTroop Territory that the current player owns
     */
    private Territory aiFindAttackingTerr(Territory chosenTargetTerritory) {

        Territory highestTroops = new Territory("highestTroopTerritory");
        highestTroops.setTroops(0);
        for(Territory borderTerr : chosenTargetTerritory.getBorderTerritories()){
            if(borderTerr.getPlayer() == currentPlayer && borderTerr.getTroops() > highestTroops.getTroops()){
                highestTroops = borderTerr;
            }
        }
        return highestTroops;
    }

    /**
     * Method: Helps AI player choose which territory to Attack
     * @param with1plus
     * @param terrWith1plus all Owned Territories with more than 1 troop and have Opponent Territories
     * @param terrAgainstMostPop
     * @param terrBiggestThreat
     * @return
     */
    private Territory chooseBestTargetTerritory(ArrayList<Territory> with1plus, ArrayList<Territory> terrWith1plus, Territory terrAgainstMostPop, Territory terrBiggestThreat) {
        Territory noAttack = new Territory("DoNotAttack");

        //Select terrAgainstMostPop if its troops <= Player Owner territories
        if (terrAgainstMostPop.getTroops() > 1){
            for (Territory borderTerr : terrAgainstMostPop.getBorderTerritories()){
                if(borderTerr.getPlayer() == currentPlayer && borderTerr.getTroops() > terrAgainstMostPop.getTroops()*1.5){
                    return terrAgainstMostPop;
                }
            }
        }

        //Select terrBiggestThreat if Satisfies the following Conditions
        if (terrBiggestThreat.getTroops() > 2){
            for (Territory borderTerr : terrBiggestThreat.getBorderTerritories()){
                if(borderTerr.getPlayer() == currentPlayer && borderTerr.getTroops() > 3){
                    return terrBiggestThreat;
                }
            }
        }

        //Select terrAgainstMostPop if it's troops = 1
        if (terrAgainstMostPop.getTroops() == 1){
            return terrAgainstMostPop;
        }

        //Select terrAgainstMostPop if its troops <= Player Owner territories
        if (terrAgainstMostPop.getTroops() > 1){
            for (Territory borderTerr : terrAgainstMostPop.getBorderTerritories()){
                if(borderTerr.getPlayer() == currentPlayer && borderTerr.getTroops() >= terrAgainstMostPop.getTroops()){
                    return terrAgainstMostPop;
                }
            }
        }

        return noAttack;
    }

    /**
     * Returns the Biggest Threat Territory
     * @param terrWith1plus
     * @return
     */
    private Territory returnBiggestOppTerr(ArrayList<Territory> terrWith1plus) {
        Territory biggestThreat = new Territory("Biggest Threat");
        biggestThreat.setTroops(0);

        for (Territory terr : terrWith1plus){
            for(Territory adjTerr : terr.getBorderTerritories()){
                if(adjTerr.getPlayer() != currentPlayer && adjTerr.getTroops() > biggestThreat.getTroops()){
                    biggestThreat = adjTerr;
                }
            }
        }

        return biggestThreat;
    }

    /**
     * Returns the weakest Territory adjacent to the most populated Territory
     * @return
     */
    public Territory findMostPopulated(ArrayList<Territory> Territories) {
        Territory mostPop;
        mostPop = Territories.get(0);
        for (Territory terr : Territories){
            if(terr.getTroops() > mostPop.getTroops()){
                mostPop = terr;
            }
        }

        Territory weakestOpp = new Territory("weakestOpp");
        weakestOpp.setTroops(10000);
        for(Territory oppTerr : mostPop.getBorderTerritories()){
            if (oppTerr.getPlayer() != currentPlayer && oppTerr.getTroops() < weakestOpp.getTroops()){
                weakestOpp = oppTerr;
            }
        }

        return weakestOpp;
    }

    /**
     * Method AiTerrAttack: Returns an ArrayList of the available Attacking Territories
     * @param troops
     * @return
     */
    private ArrayList<Territory> AiTerrAttack(int troops) {
        //Returns arrayList of Potential Territories to Attack with
        //Checks all Territories checks if troops is greater than 2 and Bordering Opponent Territory
        ArrayList<Territory> TerrWithtroops = new ArrayList<>();
        for (Territory terr : currentPlayer.getTerritories()){
            for(Territory adjTerr : terr.getBorderTerritories()){
                if(adjTerr.getPlayer() != terr.getPlayer() && (terr.getTroops() > troops) && terr.getFlagAdded() == false){
                    TerrWithtroops.add(terr);
                    terr.setFlagAdded(true);
                }
            }
        }

        clearAllFlags(TerrWithtroops);

        return TerrWithtroops;
    }

    /**
     * Method: Clears all the flags in the Territories. Flags represent
     * @param terrWithtroops
     */
    public void clearAllFlags(ArrayList<Territory> terrWithtroops) {
        for(Territory terr : terrWithtroops){
            terr.setFlagAdded(false);
        }
    }

    /**
     * Method: Fortify used for Players to transfer troops from one territory to another Territory
     * @param troops
     * @return
     */
    public boolean Fortify(int troops){

    //move troops FROM -> TO.
        if(troops < From.getTroops()) {
                From.removeTroops(troops);
                To.addTroops(troops);
            return true;
        } else{
            return false;
        }
    }

    /**
     * Path finding algo -  In progress
     * @param From
     * @param To
     * @param path
     * @return boolean
     */
    /*
    public boolean findPath(Territory From, Territory To, LinkedList path){

        for (Territory terr : getCurrentPlayer().getTerritories()) {
            if(terr.equals(From)){
                path.add(terr);
            } else{
                // if FROM is not in owned territory list.
            }

           for(Territory adj: From.getBorderTerritories()){
               if(To==adj){
                   path.add(adj);
                   return true;

               }else{
                   if(!findPath(adj,To,path)){
                       path.removeLast();

                   }

                   return findPath(adj,To,path);
               }
           }return false;
        }

        return false;
    }
*/

    /**
     * Should run path finding algo- return true if path FROM->To.
     * currently returning always true for testing purposes.
     * @return
     */
    public boolean isPathable(){
        LinkedList<Territory> path = new LinkedList<>();
        //insert pathing algo
        isPathable = true;
        // ^ = findPath(this.From,this.To,path);

        return isPathable;
    }

    /**
     * Fortify Method: helps AI player fortify Troops to a specfic Territory that it owns
     */
    public void AIfortify() throws InterruptedException {


       // wait(100);
    }
    public  void save(){
        JOptionPane.showMessageDialog(null, "game saved");
    }


    /**
     * NextTurn: helps AI player choose when to go to the next players Turn
     */
    public void AInextTurn() throws InterruptedException {
        changePlayer();
        setInstructions("Player " + getCurrentPlayer().getName() + " is up Next. Please choose the Territory to add Troops to");
        //setInstructions("Player " + getCurrentPlayer().getName() + " has passed their Turn. Player "+ getNextPlayer().getName() + " is up Next. Please choose the Territory to add Troops to");
        gameStatus();
        // wait(100);
    }
    public void CheckAiPlayer() {
        while (!UserCurrentPlayer) {
            try {
                AIUtilityFunction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (! currentPlayer.isAIplayer() ) {
                UserCurrentPlayer = true;
            }
        }
    }
    public void setUserCurrentPlayer(){
        UserCurrentPlayer = false;
    }
    public boolean getUserCurrentPlayer(){
        return UserCurrentPlayer ;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setDeployedNum(int d) {
        deployed = d;
    }
}