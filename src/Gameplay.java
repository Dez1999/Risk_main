import java.util.*;
import java.lang.*;


public class Gameplay {
  public static final int MAX_DICE = 3;
  private Board board;
  private Player currentPlayer;
  private Die die;
  private twoDice dice2;
  private threeDice dice3;
  public ArrayList<Player> players;
  public int numPlayers;
  private int initialPlayerTroopstoplace;
  //parser : Parser
  private Territory attackingTerritory;
  private int diceToUse;
  private int currentPhase;

  public Gameplay(){
    startGame();
  }


  public void startGame() {
    printWelcome();
    this.numPlayers = getNumofPlayersPlaying();
    this.players = new ArrayList<Player>();
    InitializePlayers(numPlayers);
    board = new Board(numPlayers);
    addInitialTroopstoTerritories(numPlayers);
    NumberInitialTroops();
    Scanner s = new Scanner(System.in);
    System.out.println("welcome to game....");
    printRules();
    printCommands();
    boolean gameWon = false;
    System.out.println("At the start of each turn each player receives 3 or more troops and" +
      " if you rule a whole continent you will get more bonus troops.");
    System.out.println("The game will start with Player 1");
      for (int i = 0; i < numPlayers; i++) {
        currentPlayer = getPlayers(i);
        System.out.println("It is " + "Player" + i + 1 + "'s turn");
        int bonus = 0;
        if (currentPlayer.getContinents().size() > 0) {
          for (int j = 0; j < currentPlayer.getContinents().size(); j++) {
            bonus = bonus + currentPlayer.getContinents().get(j).getBonusArmies();
          }
          System.out.println("you received" + bonus + "bonus troops for the continents you are holding");
        }
        int troopsNewTurn = (currentPlayer.getTerritories().size() / 3) + bonus;
        System.out.println("Player" + i + 1 + " receives " + troopsNewTurn + " troops");
        DeployTroops(troopsNewTurn);
        //Asks the user for a command
        String command;
        printCommands();
        System.out.println("Enter a command:");
        command = s.nextLine();   //execute command
        processCommand(command);
        //if pass is entered cycle to the next player
        System.out.println("Player" + i + 1 + " passes");
      }
  }


  private void printWelcome() {
    System.out.println();
    System.out.println("Welcome to Risk!");
    System.out.println("Everybody wants to rule the world!");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
  }

  public int getNumofPlayersPlaying() {
    System.out.println("Please choose how many players 2-6");
    try {
      Scanner scan = new Scanner(System.in);
      int num = 0;
      do {
        System.out.print(">");
        num = scan.nextInt();
        if (!(num >= 1 && num <= 7)) System.out.println("Sorry, only 2-6 is Valid.");
      } while (!(num >= 2 && num <= 6));
      return num;
    } catch (Exception e) {
      System.out.println("Sorry, only 2-6 is Valid.");
      return getNumofPlayersPlaying();
    }
  }

  public void InitializePlayers(int num) {
    for (int i = 0; i < num; i++) {
      players.add(new Player("i"));
      System.out.println(players);
    }
  }


  public Player getPlayers(int index) {

    return players.get(index);
  }

  public void removePlayer(Player player)
  {
    players.remove(player);
  }



   public void processCommand(String comm) {
  while(!comm.equals("PASS")) {


    if(comm =="HELP") {
      printHelp();
    }
    else if(comm == "QUIT") {
      quit();
    }
    else if(comm == "ATTACK") {
      System.out.println("Please choose the attacking territory, the target territory, and the number of troops you are attacking with");
      Scanner s = new Scanner(System.in);
      String Offence = s.nextLine();
      System.out.println("Please choose the target territory");
      String Target = s.nextLine();
      int troopsAmount = mapper(Offence).getTroops();
      attack(Target, Offence, troopsAmount);
    }
    else if(comm == "FORTIFY") {
      fortify();
    }
    else if(comm == "PRINT") {
      getGameStatus();
    }
    else {
      System.out.println("INVALID COMMAND");
    }
  nextPlayerTurn();
  }}

  private void printHelp() {
    {
      System.out.println("ARE YOU LOST?");
      System.out.println("I CAN HELP YOU");
      System.out.println();
      printCommands();
    }
  }
  private void fortify()
  {}
  private void getGameStatus()
  {}
  private void nextPlayerTurn()
  {}

  private void printCommands() {
    System.out.println("THESE ARE THE POSSIBLE COMMANDS:\n" +
      "ATTACK: ATTACKS A COUNTRY\n" +
      "PASS: PASSES YOUR TURN\n" +
      "PRINT: PRINTS THE STATE OF THE MAP\n" +
      "DEPLOY: DEPLOY TROOPS IN THE SPECIFIED REGION \n" +
      "QUIT: QUITS THE GAME SESSION\n" +
      "START: STARTS THE GAME\n" +
      "HELP: SHOWS HELP\n" +
      "RULES: SHOWS THE RULES OF RISK!\n");
  }


  public void WinnerStatus() {
  if(players.size() == 1){
    System.out.println("Winner Winner Chicken Dinner");
    System.out.println("Player" + players.get(0).getName() + ", you have conquered all your enemies' territories!");
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

  private void addInitialTroopstoTerritories(int p) {

    int i = 0;
    int Num = 0;

    for (i = 0; i <= board.getNumTerritories()-1; i++) {
      for (Territory terr : board.getTerritoriesList()) {
        if (terr.getName() == terrList[i]) {
          getPlayers(Num).addTerritories(terr);
          terr.changeOwner(players.get(Num));
        }
        System.out.println(terr.getName());
      }
      Num = (Num + 1) % p;
    }
  }


  public int stringTerritoryMapping(String t, int troop) {
    Territory addT = mapper(t);
    if (addT !=null)
    {
          addT.addTroops(troop);
          return 0;
        }
    return -1;
      }

  public Territory mapper(String confirm)
  {
    //int i;
    //for (i = 0; i < terrList.length; i++) {
      //if (terrList[i] == confirm)
      //{
        for (Territory terr : board.getTerritoriesList())
          if (terr.getName().equals(confirm)) {
            return terr;
          }
     // }
    //}
    return null;
  }


  /**
   * Sets the amount of initial troops each player can start out with depending on number of players
   */
  private void NumberInitialTroops() {


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
      }
      for (i = 16; i <= 36; i++) {
        board.getTerritoriesList()[i].addTroops(3);
      }
    } else if (numPlayers == 3) {
      //initialPlayerTroopstoplace = 35;
      for (i = 0; i < 21; i++) {
        board.getTerritoriesList()[i].addTroops(3);
      }
      for (i = 21; i < 35; i++) {
        board.getTerritoriesList()[i].addTroops(2);
      }
    } else if (numPlayers == 4) {
      //initialPlayerTroopstoplace = 30;
      for (i = 0; i < 36; i++) {
        board.getTerritoriesList()[i].addTroops(3);
      }
      board.getTerritoriesList()[36].addTroops(2);
      board.getTerritoriesList()[37].addTroops(2);
      board.getTerritoriesList()[38].addTroops(3);
      board.getTerritoriesList()[39].addTroops(3);
      board.getTerritoriesList()[40].addTroops(1);
      board.getTerritoriesList()[41].addTroops(1);
    } else if (numPlayers == 5) {
      //initialPlayerTroopstoplace = 25;
      for (i = 0; i < 35; i++) {
        board.getTerritoriesList()[i].addTroops(3);
      }
      board.getTerritoriesList()[35].addTroops(2);
      board.getTerritoriesList()[36].addTroops(2);
      board.getTerritoriesList()[37].addTroops(4);
      board.getTerritoriesList()[38].addTroops(4);
      board.getTerritoriesList()[39].addTroops(4);
      board.getTerritoriesList()[40].addTroops(2);
      board.getTerritoriesList()[41].addTroops(2);
    } else if (numPlayers == 6) {
      //initialPlayerTroopstoplace = 20;
      for (i = 0; i < 36; i++) {
        board.getTerritoriesList()[i].addTroops(3);
      }
      for (i = 36; i < 42; i++) {
        board.getTerritoriesList()[i].addTroops(2);
      }
    }
  }


  //private void NumberInitialTroops() {
  //The number of players ranges from 2 to 6, and the corresponding initial number of armies
  //is 50, 35, 30, 25, and 20 respectively, depending on the number of players
  //2 : 50 troops each
  //3: 35 troops each
  //4: 30 troops
  //5: 25
  //6: 20
  //if (numPlayers == 2)
  //initialPlayerTroopstoplace = 50;
  //else if(numPlayers == 3)
  //initialPlayerTroopstoplace = 35;
  //else if(numPlayers == 4)
  //initialPlayerTroopstoplace = 30;
  //else if(numPlayers == 5)
  //initialPlayerTroopstoplace = 25;
  //else if(numPlayers ==6)
  //initialPlayerTroopstoplace = 20;
  //}

  /**
   * Returns the number of troops that will be given to each player
   *
   * @return
   */
  private int getInitialPlayerTroopstoplaceInitialiTroopstoUse() {
    return initialPlayerTroopstoplace;
  }

  String[] terrList = {"Alaska", "NorthWestTerritories", "GreenLand", "Alberta", "Ontario", "Quebec", "WestUSA",
    "EastUSA", "CentralAmerica", "Venezuela", "Peru", "Brazil", "Argentina", "NorthAfrica", "Egypt", "EastAfrica",
    "Congo", "SouthAfrica", "Madagascar", "Iceland", "Scandinavia", "Ukraine", "Great Britain", "Northern Europe",
    "Southern Europe", "Western Europe", "Indonesia", "New Guinea", "Western Australia", "Eastern Australia",
    "New Zealand", "Middle East", "Afghanistan", "Japaan", "China", "India", "Mongolia"};


  private void listTheTerritories(ArrayList<Territory> territoryList) {
    System.out.println();
    System.out.println("Territories Owned: ");
    for (Territory terr : territoryList) {
      System.out.println(terr.getName() + ": Troops = " + terr.getTroops());
    }
  }

    private void listTheContinents(ArrayList<Continent> continentList){
      System.out.println();
      System.out.println("Continents Owned: ");

      if (continentList.size() == 0){
        System.out.println("No Continents Owned");
      }
      else {
        for (Continent cont : continentList) {
          System.out.println(cont.getName());
        }
      }
  }

private void DeployTroops(int newTroops)
{
  Scanner s = new Scanner(System.in);
  listTheTerritories(currentPlayer.getTerritories());
  listTheContinents(currentPlayer.getContinents());
  System.out.println("add Troops to:");
    String addingToTerritories;
    addingToTerritories = s.nextLine();
    int g = stringTerritoryMapping(addingToTerritories, newTroops);
    while (g == -1) {
      System.out.println("You have entered an invalid territory");
      System.out.println("Enter a territory to add armies too");
      addingToTerritories = s.nextLine();
      g = stringTerritoryMapping(addingToTerritories, newTroops);
    }
  listTheTerritories(currentPlayer.getTerritories());
    System.out.println();
    //board.getTer(g).addTroops(1);
  }

  public Player getCurrentPlayer() {
  return currentPlayer;
  }

  private void attack(String defendingTerritoryName, String attackingTerritoryName, int attackers){
    int a;
    int d;
    int aa;
    int dd;
    Territory t1 = mapper(defendingTerritoryName);
    Territory t2 = mapper(attackingTerritoryName);
    if(!t1.getBorderTerritories().contains(t2)) {
      System.out.println("The two territories selected don't border each other");
      return;
    }
    if(t2.getPlayer() != currentPlayer) {
      System.out.println("You can't attack from that territory. You don't control it");
      return;
    }
    if(t1.getPlayer() == currentPlayer) {
      System.out.println("You cannot attack your own territory");
      return;
    }
    if(attackers == 1) {
      System.out.println("You can only attack with a 2 or more Soldiers");
      return;
    }
    if(attackers == 2) {

      System.out.println("only one attacking die");
      a = die.roll();
      d = die.roll();
      if (a>d)
      {
        t1.removeTroops(1);
        System.out.println("defender lost 1 soldier");
      }
      else {
        t2.removeTroops(1);
        System.out.println("attacker lost 1 soldier");
      }
      return;
    }
    if(attackers == 3) {
      System.out.println("two attacking dice");
      a = dice2.getHighest();
      aa = dice2.getSecondHighest();
      if (t1.getTroops() >= 2) {
        System.out.println("two defending dice");
        d = dice2.getHighest();
        dd = dice2.getSecondHighest();
        if (a>d && aa>dd)
        {
          t1.removeTroops(2);
          System.out.println("defender lost 2 soldier");
        }
        else if(a>d && aa<dd)
        {
          t1.removeTroops(1);
          System.out.println("defender lost 1 soldier");
          t2.removeTroops(1);
          System.out.println("attacker lost 1 soldier");
        }
        else
        {
          t2.removeTroops(2);
          System.out.println("attackers lost 2 soldier");
        }
      } else if (t1.getTroops() == 1) {
        System.out.println("only one defending dice");
        d = die.roll();
        if (a > d) {
          t1.removeTroops(1);
          System.out.println("defender lost 1 soldier");
        }
        else {
          t2.removeTroops(1);
          System.out.println("attackers lost 1 soldier");
        }}
      return;
    }

    if(attackers >= 4) {
      System.out.println("three attacking dice");
      a = dice3.getHighest();
      aa = dice3.getSecondHighest();
      if (t1.getTroops() == 1) {
        System.out.println("only one defending dice");
        d = die.roll();
        if (a > d) {
          t1.removeTroops(1);
          System.out.println("defender lost 1 soldier");
        } else {
          t2.removeTroops(1);
          System.out.println("attacker lost 1 soldier");
        }
      } else if (t1.getTroops() == 2) {
        System.out.println("two defending dice");
        d = dice2.getHighest();
        dd = dice2.getSecondHighest();
        if (a > d && aa > dd) {
          t1.removeTroops(2);
          System.out.println("defender lost 2 soldier");
        } else if (a > d && aa < dd) {
          t1.removeTroops(1);
          System.out.println("defender lost 1 soldier");
          t2.removeTroops(1);
          System.out.println("attacker lost 1 soldier");
        } else {
          t2.removeTroops(2);
          System.out.println("attacker lost 2 soldier");
        }
      }else{
        d = dice3.getHighest();
        dd = dice3.getSecondHighest();
        if (a > d && aa > dd) {
          t1.removeTroops(3);
          System.out.println("defender lost 3 soldier");
        } else if (a > d && aa < dd) {
          t1.removeTroops(1);
          System.out.println("defender lost 1 soldier");
          t2.removeTroops(2);
          System.out.println("attacker lost 2 soldier");
        } else {
          t2.removeTroops(3);
          System.out.println("attacker lost 3 soldier");
        }
      }
      return;
    }
    attackResult(t1,t2);
}

  public void attackResult(Territory t, Territory tt) {
    if (tt.getTroops() == 1)
    {
      System.out.println("cant attack daug common have some sense, you got nobody there");
  }
  else if(t.getTroops() <= 0) {//defending territory has no troops left
      System.out.println("defending territory has no troops left");
      System.out.println(t.getName() + " was conquered!");
      Player OldOwner = t.getPlayer();
      OldOwner.removeTerritories(t);
      tt.getPlayer().addTerritories(t);
      t.changeOwner(tt.getPlayer());
  //Continent continent = t1.getContinent();
  //if(continent.isConquered()) displayMessage(continent.getName()+" was conquered!");

  if(OldOwner.getTerritories().size() == 0) {

  //prevOwner is eliminated
  removePlayer(OldOwner);
  if(players.size() == 1){
  }
    //game is over
    System.out.println(tt.getPlayer() + " has won!");
  }
      //ask owner how many armies they want to move
      Scanner s = new Scanner(System.in);
      int TroopsToMove = s.nextInt();
      System.out.println("How many armies would you like to move?");
      while(TroopsToMove < tt.getTroops() || TroopsToMove > tt.getTroops() - 1){
        String message = TroopsToMove < tt.getTroops() ? "You must move at least "+ tt.getTroops() +" armies": "There are not enough armies in "+ tt.getName();
        System.out.println(message);
      }
    }
  else {
    attack(t.getName(), tt.getName(), tt.getTroops());
    }
  }

  public static void main(String[] args) {
    Gameplay gameplay = new Gameplay();
  }

}
