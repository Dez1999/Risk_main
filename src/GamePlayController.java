import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Des, Peter, Raul
 */
public class GamePlayController implements ActionListener {
    private GameplayModel gpm;
    private int next;
    private boolean success;

    private int userTroops;

    private boolean isFortifying = false;




    public GamePlayController(GameplayModel gpm){

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
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame parent = new JFrame();


        //Select Territory to Deploy Troops
        if(next == 0){
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setSelectedTerritory(terr);
                    //Check if player owns Territory
                    //Add troops to Territory
                    //Ask User to choose Attacking Territory
                    success = gpm.userDeploysTroops();
                    if (success){ //Troops were deployed to selected Territory
                        //gpm.updateBoardStatus()   -> ADD METHOD


                        //Show in GameStatus() used in JLabel : gpm
                        gpm.setInstructions("Please choose your own Attacking Territory with Neighbouring enemy Territories");
                        gpm.gameStatus();
                    }
                    else{//Troops were not deployed to selected Territory
                        //Show in GameStatus()
                        gpm.setInstructions("Please Select Territory to add your " + gpm.getBonus() + " bonus Troops to");
                        gpm.gameStatus();
                        next = -1;
                    }
                }
            }

        }

        //Select Attacking Territory
        else if(next == 1){
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


        //Select Defending Territory
            //Check if Defending Territory can be used
            //If yes, then have a pop up window to ask the user to select # of troops
            //then run one attack.
            //Display attackOutcome()
            //Display message to tell the user to select A New Attacking Territory
            //set n = 0 restart attack phase.
        else if(next == 2){
            for (Territory terr : gpm.getBoard().getTerritoriesList()) {
                if (e.getActionCommand().equals(terr.getName())) {
                    gpm.setDefendingTerritory(terr);
                    gpm.isTargetBordering();
                    if (gpm.isExitAttack()){  //Defending Territory was not successfully Selected

                        //Show in GameStatus
                        gpm.setInstructions("You must choose a Territory you don't own and adjacent to " + gpm.getAttackingTerritory().getName()  + ". Please Choose Defending Territory");
                        gpm.gameStatus();
                        next = 1;

                    }
                    else { //Defending Territory was successfully selected

                        //Show in GameStatus
                        gpm.setInstructions("Choose the Number of Dice to Attack with (1 to 3 Dice");
                        gpm.gameStatus();
                        JFrame diceFrame = new JFrame("amount of dice for the attack");
                        diceFrame.getContentPane().setBackground(Color.pink);
                        diceFrame.setLayout(new FlowLayout());
                        diceFrame.setSize(300, 200);
                        JLabel label = new JLabel("choose the amount of dice you for the attack");
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
                            }
                        });
                        diceFrame.setVisible(true);
                        diceFrame.add(oneDie);
                        diceFrame.add(twoDie);
                        diceFrame.add(threeDie);
                        //Get user input number and save it then run method below:
                       // gpm.setUserAttackingTroops(userTroops);
                       // gpm.chooseAttackingTroops();

                        if(gpm.isExitAttack()){

                            //Show in GameStatus

                            gpm.setInstructions("You must choose the correct amount of Dice. Please choose the Attacking Territory with Neighbouring Enemy Territories");

                            gpm.gameStatus();
                            next = 0;
                        }
                        else {
                            //Attack was Successful
                            //updateBoardStatus()
                            //diceFrame.setVisible(false);
                            if(gpm.isExitAttack() == true){
                               // diceFrame.setVisible(false);
                            }
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
                            }
                            else {
                                //Show GameStatus
                                gpm.setInstructions("Please choose Attacking Territory");
                                gpm.gameStatus();
                                next = 0;
                            }
                        }
                        if(gpm.getCloseDiceFrame()){
                          //  diceFrame.setVisible(false);
                        }
                    }

                }
            }
        }

        //Next Button is Selected. Change Player and start to deploy Troops, Checks if AI player is next
        else if(e.getActionCommand().equals("next")){

            if(isFortifying){

                //Tell the user to select Territory A
                //update the gameStatus
                next = 3;
            }
            else if(!isFortifying)
            {
                JOptionPane.showMessageDialog(parent, "Your turn is now Over");

                //Show GameStatus
                gpm.setInstructions("Player " + gpm.getCurrentPlayer().getName() + " has passed their Turn. Player "+ gpm.getNextPlayer().getName() + " is up Next. Please choose the Territory to add Troops to");
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

                    //try {
                        //gpm.AIUtilityFunction();
                       // }
                if(gpm.getCurrentPlayer().isAIplayer()) {
                    gpm.setUserCurrentPlayer();
                    gpm.CheckAiPlayer();
                }

                    //} catch (InterruptedException ex) {
                     //   ex.printStackTrace();
                    //}
                    //gpm.getCurrentPlayer().AIplayerFunction();


            }




    //Next (first time)


            //isFortifying = true;
        }
        else if(e.getActionCommand().equals("next") && isFortifying){
            //next (2nd time)
            //update instructions to have Fortify intstructions
            next = 4;


        }
        //consider removing
        //BACK Button is Selected. Go back to ATTACK
        else if(e.getActionCommand().equals("Back")){
            JOptionPane.showMessageDialog(parent, "You have Restarted the Attack Phase");

            //Show GameStatus
            gpm.setInstructions("Player Selected Back. Please choose Attacking Territory with Neighbouring Enemy Territories");
            gpm.gameStatus();

            //This will bring the user back to ATTACK phase and ask them to choose an attacking Territory
            next = 0;
        }
        //Start deploy phase
        else if(next == 4 && isFortifying){
            //this is where next e.getActionComm... should return selected territory "A"
            //update instructions to ask for territory to fortify "B"
            gpm.gameStatus();
            //gpm.fortifyFrom();
            //gps.fortifyTo();
            //if Terr A is OK -> Continue to next = 5
                //Update Status: select territory B
            //else if TerrA is NOt ok -> set  next 3
                //Update GameStatus: select territory A

        } else if(next == 5 && isFortifying){
            //e.getActionComm... should return selected terr;
            //Check if there is a path from A-B
                //if no path -> set next = 4
                //if yes -> continue with User prompt below
                         //prompt user for a troop amount and fortify from territory A to B
                         //If successful: fortify complete -> set isFortifying to false
                         //End the turn
        }

        next++;   //Updates the Phase in the Game
        }
    }
