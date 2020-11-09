import java.util.*;
public class GameplayModel {
    public static final int MAX_DICE = 3;
    private Board board;
    private Player currentPlayer;
    private Player nextPlayer;
    private Die die = new Die();
    private twoDice dice2 = new twoDice();
    private threeDice dice3 = new threeDice();

    public ArrayList<Player> playersAlive;
    public ArrayList<Player> playersDead;

    public int numPlayers;
    private int initialPlayerTroopstoplace;
    //parser : Parser
    private int diceToUse;
    private int currentPhase;
    private String attackingTerritoryName;
    private int attackers;
    private String defendingTerritoryName;
    private Territory attackingTerritory;
    private Territory defendingTerritory;

    private boolean exitAttack = false;  //Come back to this to implement. This is only local variable for attack method
    private boolean playerOwnsAttackingTerritory = false; //Check if Attacking Territory can be used
    private boolean targetTerritoryisBordering = false;
    private boolean canAttack = false;
    private int defendingTroops;
    private int territoriesConquered = 0;

    private int bonus = 0;


    public GameplayModel() {
        startGame();
    }


    public void startGame() {

        printWelcome();
        inputNumberofPlayers();
        this.playersAlive = new ArrayList<Player>();
        InitializePlayers(numPlayers);
        board = new Board(numPlayers);
        //addInitialTroopstoTerritories(numPlayers);
        initializeLand();
        Scanner s = new Scanner(System.in);
        System.out.println("welcome to game....");
        printRules();
        printCommands();
        boolean gameWon = false;


        System.out.println("At the start of each turn each player receives 3 or more troops and" +
                " if you rule a whole continent you will get more bonus troops.");
        System.out.println("The game will start with Player 1");

        //Start of Player Turn
        for (int i = 0; i < playersAlive.size(); i++) {
            bonus = 0; //Reset bonus troops

            /**Sets the currentPlayer*/
            currentPlayer = getPlayers(i);
            System.out.println("It is " + "Player " + (i + 1) + "'s turn");


            /**Set Next Player Turn*/
            if (i == playersAlive.size() - 1) {
                nextPlayer = getPlayers(0);
            }
            if (i != playersAlive.size() - 1) {
                nextPlayer = getPlayers(i + 1);
            }

            /**Checks the Players Hand*/
            for (Card card : currentPlayer.getHand().handList()) {
                System.out.println("Current Player Hand:" +
                        card.getTerritoryName() + ": " + card.getClass());
            }


            String input;
            if (currentPlayer.getHand().handList().size() > 2) {
                System.out.println("Would you like to turn in your Cards? (YES or NO)");
                input = s.nextLine();   //execute command

                if (input.equals("YES")) {
                    tradeInCards(true);
                } else {
                    System.out.println("You have chosen not to use any cards this turn.");
                    currentPlayer.getHand().checkCardSet();
                    boolean istradable = currentPlayer.getHand().canTurnCardsIn();
                    tradeInCards(istradable); //boolean
                }
            }


            /**Calculates the bonus troops a player receives */
            if (currentPlayer.getContinents().size() > 0) {
                for (int j = 0; j < currentPlayer.getContinents().size(); j++) {
                    bonus = bonus + currentPlayer.getContinents().get(j).getBonusArmies();
                }
                System.out.println("you received" + bonus + "bonus troops for the continents you are holding");
            }
            int troopsNewTurn = (currentPlayer.getTerritories().size() / 3) + bonus;
            System.out.println("Player " + (i + 1) + " receives " + troopsNewTurn + " troops");


            /**Method to deploy troops */
            DeployTroops(troopsNewTurn);


            /**Asks the user for a command*/
            enterCommand();

            //if pass is entered cycle to the next player
            System.out.println("Player " + (i + 1) + " passes");

            if (i == numPlayers - 1) {
                i = -1;
            }

            /** Reset Territories conquered in the current Turn to 0*/
            territoriesConquered = 0;

            /** Checks the WinnerStatus at the end of each Turn*/
            WinnerStatus();
        }
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
            System.out.println("Player's cards have been removed from Hand. Troops were added to Bonus Troops");
        }
    }


    /**
     * Method: Prints the Welcome message to the users
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Risk!");
        System.out.println("Everybody wants to rule the world!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }

    /**
     * Method: Method regarding the number of players
     */
    public void inputNumberofPlayers() {
        System.out.println("Please choose how many players 2-6");
        try {
            Scanner scan = new Scanner(System.in);
            int num = 0;
            do {
                System.out.print(">");
                num = scan.nextInt();
                if (!(num >= 1 && num <= 7)) System.out.println("Sorry, only 2-6 is Valid.");
            } while (!(num >= 2 && num <= 6));
            numPlayers = num;
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
        for (int i = 0; i < num; i++) {
            String str1 = Integer.toString(i);
            playersAlive.add(new Player(str1));
            System.out.println("player" + playersAlive.get(i).getName());
        }

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


    /**
     * Method: Used to process the Command from the user
     *
     * @param comm
     */
    public void processCommand(String comm) {

        if (comm.equals("PASS")) {
            nextPlayerTurn();
        } else if (comm.equals("HELP")) {
            printHelp();
        } else if (comm.equals("QUIT")) {
            quit();
        } else if (comm.equals("ATTACK")) {
            attack();
        }
        //else if (comm.equals("FORTIFY")) {
        //fortify();   //Milestone 3
        //}
        else if (comm.equals("PRINT")) {
            getGameStatus();
        } else {
            System.out.println("INVALID COMMAND");
            enterCommand();
        }
    }

    private void enterCommand() {
        Scanner s = new Scanner(System.in);
        String command;
        System.out.println("Enter a command:");
        command = s.nextLine();   //execute command
        processCommand(command);
    }

    private void printHelp() {
        {
            System.out.println();
            printCommands();
        }
        enterCommand();
    }

    private void fortify() {
    }

    //FIX This: print each players own Territories. Each territory has player owner
    private void getGameStatus() {
        //Prints all Territories: Territory : Name, Troops : int, PlayerOwnerShip : player
        for (Territory territory1 : board.getTerritoriesList()) {
            System.out.println(territory1.getName() + "-> Troops : " + territory1.getTroops() + ", Owner: Player " + territory1.getPlayer().getName());
        }
        enterCommand();

    }

    /**
     * Method: Changes the current player turn to the next player turn
     */
    private void nextPlayerTurn() {
        currentPlayer = nextPlayer;
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


    public void WinnerStatus() {
        if (playersAlive.size() == 1) {
            System.out.println("Winner!! Winner!!");
            System.out.println("Player" + playersAlive.get(0).getName() + ", you have conquered the WORLD!");
            System.out.println("");
            System.out.println("The game has now ended.");
            System.exit(0);
        }
    }

    //public static void printBoard(Command command) {
    //}


    private void quit() {
        removePlayer(currentPlayer);
    }

    public void printRules() {
        System.out.println
                ("Rules \n" +
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
                        "dice you rolled in your last attack.\n" +
                        "                                  "
                );
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
            return 0;
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

//FIX THIS

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
            for (i = 8; i < 15; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(1));
                getPlayers(1).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 15; i < 22; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(2));
                getPlayers(2).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 22; i < 20; i++) {
                board.getTerritoriesList()[i].addTroops(4);
                board.getTerritoriesList()[i].changeOwner(getPlayers(3));
                getPlayers(3).addTerritories(board.getTerritoriesList()[i]);
            }
            for (i = 30; i <= 36; i++) {
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
    private void DeployTroops(int newTroops) {
        listTheTerritories(currentPlayer.getTerritories());
        listTheContinents(currentPlayer.getContinents());

        System.out.println("Add Troops to:");

        deployInTerritory(newTroops);

        listTheTerritories(currentPlayer.getTerritories());
        System.out.println();
        //board.getTer(g).addTroops(1);
    }

    /**
     * Method: Used to check if selected Territory is owned by Player. Then helps to deploy troops into selected Territory
     *
     * @param newTroops
     */
    private void deployInTerritory(int newTroops) {
        Scanner s = new Scanner(System.in);
        String addingToTerritory;
        addingToTerritory = s.nextLine();
        int temp = currentPlayer.getTerritories().size() - 1; // store last element in dynamic array

        for (Territory terr : currentPlayer.getTerritories()) {
            if (terr.getName().equals(addingToTerritory)) {//deploying troops if == true
                int g = stringTerritoryMapping(addingToTerritory, newTroops);
                return;
            } else if (terr.getName().equals((currentPlayer.getTerritories().get(temp)).getName())) {
                System.out.println("You have entered an invalid Territory");
                System.out.println("Enter a Territory to deploy troops to");
                deployInTerritory(newTroops);
            }

        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This Method needs to be Reviewed
     */
    private void attack() {
        Scanner s = new Scanner(System.in);

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

        //Ask User if they want to attack again
        /**Asks the user for a command*/
        enterCommand();

    }

    /**
     * Method: Chooses number of Attacking Troops to use
     */
    private void chooseAttackingTroops() {
        canAttack = false;
        exitAttack = false;
        Scanner s = new Scanner(System.in);
        int troopsAmount = attackingTerritory.getTroops();  //Returns number of troops in selected Attacking Territory

        while (!canAttack && !exitAttack) {
            System.out.println("Please choose the Number of Troops to Attack with. Press BACK to exit ATTACK phase");

            String stringword = s.nextLine();
            if (stringword.equals("BACK")) {
                exitAttack = true;
            } else {
                int troops = Integer.parseInt(stringword);

                if (troops >= 1 && troops <= 3) {
                    attackTroopLogic(troops);
                    //Need to exit while loop if canAttack is true
                } else if (troops > 3) {
                    System.out.println("You cannot attack with more than 3 troops at one time. Please attack with 1-3 troops.");
                } else {
                    System.out.println("You did not enter the right amount of troops to Attack with");
                }
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
                System.out.println("ERROR: defending Territory has no troops");
            }
        } else {
            System.out.println("Attacking Territory does not have enough troops to Attack");

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
                    attackOutcome(1, attackLoss, defendLoss, attackingTroops);
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

    private void attackOutcome(int rollResult, int attackLoss, int defendLoss, int attackingTroops) {
        //setTroops
        Player prevOwnerPlayer = defendingTerritory.getPlayer();
        String prevOwnerName = defendingTerritory.getPlayer().getName();

        //Attacker Wins
        if (rollResult == 1) {
            defendingTerritory.removeTroops(defendLoss);

            if (defendingTerritory.getTroops() <= 0) {

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
                }

                if (defendingTerritory.getPlayer().getTerritories().isEmpty()) {
                    //Remove player from game
                    kill(prevOwnerPlayer);
                }
            }
            else {
                System.out.println(attackingTerritory.getName() + " has won the battle. " + defendingTerritory.getName() + " has lost " + defendLoss + " troops");
            }
        }


        //Defender Wins
        else if(rollResult == -1){
            attackingTerritory.removeTroops(attackLoss);
            System.out.println(defendingTerritory.getName() + " has won the battle. " + attackingTerritory.getName() + " has lost " + attackLoss + " troops");
        }

        //There was a Tie
        else if (rollResult == 0){
            attackingTerritory.removeTroops(attackLoss); //expecting problem handling empty territory incase of tie in a 2v1 battle. 2v1 => 1v0  after tie
            defendingTerritory.removeTroops(defendLoss);

            if(defendingTerritory.getTroops()<=0) {
                System.out.println(defendingTerritory.getName() + " tied with  " + attackingTerritory.getName() + ". ");
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
                }

                //Check if defending player is killed
                if (defendingTerritory.getPlayer().getTerritories().isEmpty()) {
                    //Remove player from game
                    kill(prevOwnerPlayer);
                }
            }
            else {
                System.out.println(defendingTerritory.getName() + " tied with  " + attackingTerritory.getName() + ". ");
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
        System.out.println("Player " + prevOwnerPlayer.getName() + " is killed.");
    }

    /**
     * Checks if Target is Bordering Attacking Territory
     */
    private void isTargetBordering() {
        exitAttack = false;
        targetTerritoryisBordering = false;
        Scanner s = new Scanner(System.in);
        while (!targetTerritoryisBordering && !exitAttack) {
            System.out.println("Please choose the Target territory. Press BACK to exit attack phase");
            String Target = s.nextLine();

            defendingTerritory = mapper(Target);

            //Check if word Entered is equal to BACK
            if (Target.equals("BACK")){
                exitAttack = true;
            }
            else {

                //Check Target territory is bordering Attacking Territory and Not currentPlayers Territory
                if (defendingTerritory.getPlayer() != currentPlayer) {
                    for (Territory borderterr : attackingTerritory.getBorderTerritories()) {
                        if (borderterr == defendingTerritory) {
                            targetTerritoryisBordering = true;
                        }
                    }
                }

                //Check if currentPlayer owns Target Territory. THIS MIGHT NOT BE NEEDED FOR GUI
                else if (defendingTerritory.getPlayer() == currentPlayer) {
                    System.out.println("You own this Territory. You must choose a Territory you don't own");
                }

                //Check if TargetTerrirtory is not bordering Attacking territory
                else {
                    for (Territory borderterr : attackingTerritory.getBorderTerritories()) {
                        if (borderterr != defendingTerritory) {
                            System.out.println(Target + " is NOT Bordering " + attackingTerritory.getName());
                        }
                    }
                }
            }
        } //End of While Loop
    }

    /**
     * Method: Checks if currentPlayer Owns Attacking Territory
     */
    private void checkAttackingOwnership() {
        exitAttack = false;
        playerOwnsAttackingTerritory = false;
        Scanner s = new Scanner(System.in);
        while (!playerOwnsAttackingTerritory && !exitAttack) {
            System.out.println("Please choose the attacking territory. Press BACK to exit attack method");
            String userInput = s.nextLine();
            //attackingTerritoryName = userInput;
            attackingTerritory = mapper(userInput);

            if (userInput.equals("BACK")){
                exitAttack = true;
            }
            else if(attackingTerritory.getPlayer() != currentPlayer){
                System.out.println("You must own the attacking Territory");

            }

            //Check if current player owns AttackingTerritory
            else if (attackingTerritory.getPlayer() == currentPlayer) {
                playerOwnsAttackingTerritory = true;
            }
        }
    }

    public static void main(String[] args) {
        GameplayModel gamePlayModel = new GameplayModel();
    }

}