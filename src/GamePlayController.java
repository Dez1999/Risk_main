import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Des, Peter, Raul
 */
public class GamePlayController implements ActionListener {
    private GameplayModel gpm;
    private int next;
    private boolean success;
    private boolean isFortifying = true;
    private int userTroops;
    private int currentDeployable;
    private int deployCount;


    public GamePlayController(GameplayModel gpm) {

        this.gpm = gpm;
        next = 0;
        deployCount=0;
    }

    /**
     * responds to actions performed by user and also handles player turn phases.
     * ie; deploy, attack...etc
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame parent = new JFrame();


        if(e.getActionCommand().equals("save")){
            String file = JOptionPane.showInputDialog(null, "Enter FileName");
            try {
                gpm.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            next = next - 1 ;
        }

        if(e.getActionCommand().equals("load")){
            JFileChooser C = new JFileChooser();
            C.showDialog(null,"Choose File to import");
            C.setVisible(true);
            File filename = C.getSelectedFile();
            try {
                gpm.load(filename);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            next = -1;
        }
        //Select Territory to Deploy Troops

        //Deploy Phase: Select Territory to Deploy Troops

        if (next == 0) {
            int a = 0;
            boolean correctTerry = false;
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setSelectedTerritory(terr);
                    // tell user they selected wrong territory
                    if(terr.getPlayer()!= gpm.getCurrentPlayer()){
                        next = -1;


                    }else{//correct
                        correctTerry = true;
                        a = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the number of troops to deploy. You have this many troops to deploy" + currentDeployable + ":"));

                    }



                    if (deployCount == 0) {
                        gpm.setBonus(0);
                        gpm.calculateBonusTroops();
                        currentDeployable = gpm.getBonus();
                    }

                    deployCount = +1;

                    //Check if player owns Territory
                    //Add troops to Territory
                    //Ask User to choose Attacking Territory


                    if (a > currentDeployable || a < 0) {
                        next = -1;
                        gpm.setInstructions("Please Choose the correct number of troops to deploy out of : "+ currentDeployable+"");
                        gpm.gameStatus();
                    }
                    if (currentDeployable > 0 && currentDeployable >= a) {
                        gpm.setDeployedNum(a);
                        success = gpm.userDeploysTroops();
                        currentDeployable = currentDeployable - a;
                        if (currentDeployable == 0) {
                            gpm.setInstructions("Please choose your own Attacking Territory with Neighbouring enemy Territories");
                            gpm.gameStatus();
                            next = 0;
                        } else {

                            gpm.setInstructions("Please Select Territory to add your " + currentDeployable + " bonus Troops to");
                            gpm.gameStatus();
                            next = -1;
                        }
                    }
                }
            }
            if(!correctTerry){
                gpm.setInstructions("error please select your own territory"+" to add your bonus Troops to");
                gpm.gameStatus();
                next = -1;
            }
        }
        //Attack Phase: Select Attacking Territory
        else if (next == 1) {
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setAttackingTerritory(terr);  //Assumes user selects their own Territory
                    gpm.checkAttackingOwnership();


                    if(gpm.noOppBorderingTerr()){ //Attacking Territory has no Opponent Territories
                        //User must select Attacking Territory with Opponent Territories
                        gpm.setInstructions("Selection Invalid. Please choose your own Attacking Territory with Neighbouring Enemy Territories");
                        next = 0;
                    }
                    else if(!gpm.isExitAttack()){  //Attacking Territory was successfully selected
                        //updateBoardStatus()
                        //Show in GameStatus()
                        gpm.setInstructions("Please choose Defending Territory");
                        gpm.gameStatus();
                    }
                    else{ //Attacking Territory was not successfully selected
                        //Show in GameStatus
                        gpm.setInstructions("Selection Invalid. Please choose your own Attacking Territory with Neighbouring Enemy Territories");
                        gpm.gameStatus();
                        next = 0;
                    }
                }
            }
        }


        //Attack Phase: Select Defending Territory
        //Check if Defending Territory can be used
        //If yes, then have a pop up window to ask the user to select # of troops
        //then run one attack.
        //Display attackOutcome()
        //Display message to tell the user to select A New Attacking Territory
        //set n = 0 restart attack phase.
        else if (next == 2) {
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setDefendingTerritory(terr);
                    gpm.isTargetBordering();
                    if (gpm.isExitAttack()) {  //Defending Territory was not successfully Selected

                        //Show in GameStatus
                        gpm.setInstructions("You must choose a Territory you don't own and adjacent to " + gpm.getAttackingTerritory().getName()  + ". Please Choose Defending Territory");
                        gpm.gameStatus();
                        next = 1;

                    } else { //Defending Territory was successfully selected

                        //Show in GameStatus
                        gpm.setInstructions("Choose the Number of Dice to Attack with (1 to 3 Dice)");
                        gpm.gameStatus();
                        JFrame diceFrame = new JFrame("Amount of dice for the attack");
                        diceFrame.getContentPane().setBackground(Color.pink);
                        diceFrame.setLayout(new FlowLayout());
                        diceFrame.setSize(300, 200);
                        JLabel label = new JLabel("Choose the amount of dice you want for the attack");
                        label.setPreferredSize(new Dimension(270, 50));
                        diceFrame.add(label);
                        JButton oneDie = new JButton("1 Die");
                        JButton twoDie = new JButton("2 Dice");
                        JButton threeDie = new JButton("3 Dice");
                        oneDie.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent e)
                            {
                                userTroops = 1;
                                gpm.setUserAttackingTroops(userTroops);
                                gpm.chooseAttackingTroops();
                                diceFrame.setVisible(false);
                                gpm.gameStatus();
                            }
                        });
                        twoDie.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent e)
                            {
                                userTroops = 2;
                                gpm.setUserAttackingTroops(userTroops);
                                gpm.chooseAttackingTroops();
                                diceFrame.setVisible(false);
                                gpm.gameStatus();
                            }
                        });
                        threeDie.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent e)
                            {
                                userTroops = 3;
                                gpm.setUserAttackingTroops(userTroops);
                                gpm.chooseAttackingTroops();
                                diceFrame.setVisible(false);
                                gpm.gameStatus();
                            }
                        });
                        diceFrame.setVisible(true);
                        diceFrame.add(oneDie);
                        diceFrame.add(twoDie);
                        diceFrame.add(threeDie);
                        //Get user input number and save it then run method below:
                       // gpm.setUserAttackingTroops(userTroops);
                       // gpm.chooseAttackingTroops();

                        if (gpm.isExitAttack()) {

                            //Show in GameStatus

                            gpm.setInstructions("You must choose the correct amount of Dice. Please choose the Attacking Territory with Neighbouring Enemy Territories");

                            gpm.gameStatus();
                            next = 0;
                        } else {
                            //Attack was Successful
                            //updateBoardStatus()
                            //diceFrame.setVisible(false);
                            if (gpm.WinnerStatus()) { //Check if Game is Over

                                //Show in Pop-up
                                JOptionPane.showMessageDialog(null, gpm.getWinner().getName() + " is the WINNER",
                                        "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
                                /*
                                System.out.println("Winner!! Winner!!");
                                System.out.println("Player" + gpm.playersAlive.get(0).getName() + ", you have conquered the WORLD!");
                                System.out.println("");
                                System.out.println("The game has now ended.");
                                */
                                System.exit(0);
                                //Exit Game using JFrame
                            } else {
                                //Show GameStatus
                                gpm.setInstructions("Please choose Attacking Territory");
                                gpm.gameStatus();
                                next = 0;
                            }
                        }
                        //if(gpm.getCloseDiceFrame()){//  diceFrame.setVisible(false);}
                    }

                }
            }
        }

        //Next Button is Selected. Changes to Fortify and then Changes Player turn and start to deploy Troops. Checks if AI player is next
        else if (e.getActionCommand().equals("next")) {
            //deployCount = 0;
            if (isFortifying) {

                isFortifying = false;

                //Tell the user to select Territory A
                gpm.setInstructions("Please choose a Territory to fortify from. Press Next to skip Fortifying Phase");
                //update the gameStatus
                gpm.gameStatus();
                next = 3;
            } else if (!isFortifying) {
                deployCount = 0;
                JOptionPane.showMessageDialog(parent, "Your turn is now Over");
                gpm.setBonus(0);
                gpm.calculateBonusTroops();
                //Show GameStatus
                gpm.changePlayer();  //This is good
                gpm.setInstructions("Player " + gpm.getCurrentPlayer().getName() + " is up Next. Please choose the Territory to add "+ gpm.getBonus() + "Troops to"); //preiously had BONUS instead of currentdeployable.

                gpm.gameStatus();

                next = -1;
                isFortifying = true;

                //Test for AI Player
                if(gpm.getCurrentPlayer().isAIplayer()) {
                    gpm.setUserCurrentPlayer();
                    gpm.CheckAiPlayer();

                }
            }
        }

        //consider removing
        //BACK Button is Selected. Go back to ATTACK
        else if (e.getActionCommand().equals("Back")) {
            JOptionPane.showMessageDialog(parent, "You have Restarted the Attack Phase");

            //Show GameStatus
            gpm.setInstructions("Player Selected Back. Please choose Attacking Territory with Neighbouring Enemy Territories");
            gpm.gameStatus();

            //This will bring the user back to ATTACK phase and ask them to choose an attacking Territory
            next = 0;
        }

        //Fortify Phase: Start fortify phase
        else if (next == 4) {
            //this is where next e.getActionComm... should return selected territory "A"
            //update instructions to ask for territory to fortify "B"
            //gps.fortifyTo();
            //does player own selected territory
            boolean Afound = false;
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (terr.getName().equals(e.getActionCommand()) && terr.getPlayer().equals(gpm.getCurrentPlayer())) {
                    Territory A = gpm.mapper(e.getActionCommand());
                    gpm.setFrom(A);
                    gpm.setInstructions("You have selected "+  A.getName() + ". Please choose the Connected Territory to Fortify to ");
                    gpm.gameStatus();
                    Afound = true;
                    next = 4; // player selected a valid territory
                }
            }
            if(!Afound){
                next = 3;
                gpm.setInstructions("You cannot fortify from a Territory you do not own. Please select a valid territory to Fortify again.");

                //update the gameStatus
                gpm.gameStatus();

            }
            //   gpm.setFortifyFrom(e.getActionCommand()); //use the mapper to return territory clicked on
            gpm.gameStatus();

        }
        //Fortifying Phase: Select Territory to Fortify to and select number of Troops
        else if (next == 5) {
            //e.getActionComm... should return selected terr;
            Territory B;
            boolean isFortified = false;
            //making sure user owns selection.
            boolean found = false;
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (terr.getName().equals(e.getActionCommand()) && terr.getPlayer().equals(gpm.getCurrentPlayer())) {
                    // player selected a valid territory
                    found = true;

                    B = terr;
                    gpm.setTo(B);
                    if (!gpm.isPathable()) {
                        gpm.setInstructions("You cannot deploy to another Territory that is not connected. Please select a valid territory to Fortify from again.");

                        //update the gameStatus
                        gpm.gameStatus();

                        next = 3; // this code is repeated below. Figure out if can replace.                    }
                    } else {
                        while (!isFortified) {
                            //Pop-up Window to have user select the # of troops
                            int troops = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the number of troops to move. Please Use less than" + gpm.getFrom().getTroops() + ":"));

                            //check if can fortify
                            isFortified = gpm.Fortify(troops);
                            //
                            if (isFortified) {
                                // notify success
                                gpm.setInstructions("Successful Fortify! Please choose a Territory to fortify from. Press Next to skip Fortifying Phase");

                                //update the gameStatus
                                gpm.gameStatus();
                                isFortified = true;
                                //isFortifying = false;
                                next = 3;

                            }
                        }
                    }
                }
            }

            if (!found) {
                gpm.setInstructions("You cannot deploy to a country you do not own. Please select a valid territory to Fortify from again.");
                //update the gameStatus
                gpm.gameStatus();
                next = 3; // this code is repeated below. Figure out if can replace.
            }
        }
            //create global vars in GPM for A,B. Update them here ^ / v

            //Check if there is a path from B
            //if no path -> set next = 4
            //if yes -> continue with User prompt below
            //prompt user for a troop amount and fortify from territory A to B
            //If successful: fortify complete -> set isFortifying to false
            //End the turn


        next++;   //Updates the Phase in the Game
        }
    }