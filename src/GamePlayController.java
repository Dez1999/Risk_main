import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Des, Peter, Raul
 */
public class GamePlayController implements ActionListener {
    private GameplayModel gpm;
    private int next;
    private boolean success;
    private boolean isFortifying = true;


    public GamePlayController(GameplayModel gpm) {

        this.gpm = gpm;
        next = 0;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNext() {
        return next;
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


        //Select Territory to Deploy Troops
        if (next == 0) {
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setSelectedTerritory(terr);
                    //Check if player owns Territory
                    //Add troops to Territory
                    //Ask User to choose Attacking Territory
                    success = gpm.userDeploysTroops();
                    if (success) { //Troops were deployed to selected Territory
                        //gpm.updateBoardStatus()   -> ADD METHOD


                        //Show in GameStatus() used in JLabel : gpm
                        gpm.setInstructions("Please choose Attacking Territory");
                        gpm.gameStatus();
                    } else {//Troops were not deployed to selected Territory
                        //Show in GameStatus()
                        gpm.setInstructions("Please Select Territory to add your bonus Troops to");
                        gpm.gameStatus();
                        next = -1;
                    }
                }
            }

        }

        //Select Attacking Territory
        else if (next == 1) {
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setAttackingTerritory(terr);  //Assumes user selects their own Territory
                    gpm.checkAttackingOwnership();
                    if (!gpm.isExitAttack()) {  //Attacking Territory was successfully selected
                        //updateBoardStatus()
                        //Show in GameStatus()
                        gpm.setInstructions("Please choose Defending Territory");
                        gpm.gameStatus();
                    } else { //Attackinf Territory was not successfully selected
                        //Show in GameStatus
                        gpm.setInstructions("You must choose your own Territory");
                        gpm.gameStatus();
                        next = 0;
                    }
                }
            }
        }


        //Select Defending Territory
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
                        gpm.setInstructions("You own this Territory. You must choose a Territory you don't own");
                        gpm.gameStatus();
                        next = 1;

                    } else { //Defending Territory was successfully selected

                        //Show in GameStatus
                        gpm.setInstructions("Choose the Number of Dice to Attack with (1 to 3 Dice");
                        gpm.gameStatus();

                        //Pop-up Window to have user select the # of troops
                        int userTroops = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the number of Dice to use. Only choose values 1-3:"));
                        //Get user input number and save it then run method below:
                        gpm.setUserAttackingTroops(userTroops);
                        gpm.chooseAttackingTroops();

                        if (gpm.isExitAttack()) {

                            //Show in GameStatus
                            gpm.setInstructions("You must choose the correct amount of Dice. Please choose the Attacking Territory");
                            gpm.gameStatus();
                            next = 0;
                        } else {
                            //Attack was Successful
                            //updateBoardStatus()

                            if (gpm.WinnerStatus()) { //Check if Game is Over

                                //Show in Pop-up
                                JOptionPane.showInternalMessageDialog(null, gpm.playersAlive.get(0).getHand() + " is the WINNER",
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

                    }

                }
            }
        }

        //Next Button is Selected. Change Player and start to deploy Troops, Checks if AI player is next
        else if (e.getActionCommand().equals("next")) {

            if (isFortifying) {

                //Tell the user to select Territory A
                gpm.setInstructions("Please choose a Territory to fortify from.");
                //update the gameStatus
                gpm.gameStatus();
                next = 3;
            } else if (!isFortifying) {
                JOptionPane.showMessageDialog(parent, "Your turn is now Over");

                //Show GameStatus
                gpm.setInstructions("Player " + gpm.getCurrentPlayer().getName() + " has passed their Turn. Player " + gpm.getNextPlayer().getName() + " is up Next. Please choose the Territory to add Troops to");
                gpm.gameStatus();
                gpm.changePlayer();  //This is good
                gpm.gameStatus();

                String territories = null;

                for (Territory terr : gpm.getCurrentPlayer().getTerritories()) {
                    System.out.println(terr.getName() + ": Troops = " + terr.getTroops());
                    territories = territories + "\n " + terr.getName() + ": Troops = " + terr.getTroops();
                }

                JOptionPane.showInternalMessageDialog(null, territories,
                        "Territories Owned", JOptionPane.INFORMATION_MESSAGE);

                next = -1;


                //Test for AI Player
                if (gpm.getCurrentPlayer().isAIplayer()) {
                    try {
                        gpm.AIUtilityFunction();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    //gpm.getCurrentPlayer().AIplayerFunction();
                }

            }


            //Next (first time)


            isFortifying = true;
        }
        //      else if(e.getActionCommand().equals("next") && isFortifying){
        //next (2nd time)
        //update instructions to have Fortify intstructions
        //         next = 4;}


        //consider removing
        //BACK Button is Selected. Go back to ATTACK
        else if (e.getActionCommand().equals("Back")) {
            JOptionPane.showMessageDialog(parent, "You have Restarted the Attack Phase");

            //Show GameStatus
            gpm.setInstructions("Player Selected Back. Please choose the Attacking Territory:");
            gpm.gameStatus();

            //This will bring the user back to ATTACK phase and ask them to choose an attacking Territory
            next = 0;
        }
        //Start fortify phase
        else if (next == 4 && isFortifying) {
            //this is where next e.getActionComm... should return selected territory "A"
            //update instructions to ask for territory to fortify "B"


            //gps.fortifyTo();
            //does player own selected territory
            boolean Afound = false;
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (terr.getName().equals(e.getActionCommand()) && terr.getPlayer().equals(gpm.getCurrentPlayer())) {
                    Territory A = gpm.mapper(e.getActionCommand());
                    gpm.setFrom(A);
                    gpm.setInstructions("you have selected"+  A.getName() +". ");
                    gpm.gameStatus();
                    Afound = true;
                    next = 4; // player selected a valid territory
                }
            }
            if(!Afound){
                next = 3;
                gpm.setInstructions("You must select valid connected territories. Please select a valid territory to Fortify again.");

                //update the gameStatus
                gpm.gameStatus();

            }
            //   gpm.setFortifyFrom(e.getActionCommand()); //use the mapper to return territory clicked on
            gpm.gameStatus();

        } else if (next == 5 && isFortifying) {
            //e.getActionComm... should return selected terr;
            Territory B;
            boolean isFortified = false;
            //making sure user owns selection.
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (terr.getName().equals(e.getActionCommand()) && terr.getPlayer().equals(gpm.getCurrentPlayer())) {
                    // player selected a valid territory

                    B = terr;
                    gpm.setTo(B);
                    if (!gpm.isPathable()) {
                        gpm.setInstructions("You must select valid connected territories. Please select a valid territory to Fortify again.");

                        //update the gameStatus
                        gpm.gameStatus();

                        next = 3; // this code is repeated below. Figure out if can replace.                    }
                    } else {
                        while (!isFortified) {
                            //Pop-up Window to have user select the # of troops
                            int troops = (int) Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the number of troops to move. Please Use less than" + gpm.getFrom().getTroops()+":"));

                            //check if can fortify
                            isFortified = gpm.Fortify(troops);
                            //
                            if (isFortified) {
                                // notify success
                                gpm.setInstructions("Success! You have fortified correctly. Press next to pass your turn");

                                //update the gameStatus
                                gpm.gameStatus();
                                isFortified = true;
                                isFortifying = false;
                                next = -2;

                            }
                        }
                    }
                }
            }
            //create global vars in GPM for A,B. Update them here ^ / v

            //Check if there is a path from B
            //if no path -> set next = 4
            //if yes -> continue with User prompt below
            //prompt user for a troop amount and fortify from territory A to B
            //If successful: fortify complete -> set isFortifying to false
            //End the turn


        }

        next++;   //Updates the Phase in the Game

        }
    }