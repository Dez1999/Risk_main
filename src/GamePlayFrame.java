import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics;

/**
 * @author Peter, Des
 */
public class GamePlayFrame extends JFrame implements GamePlayView{
    private JButton[][] AfFirstbuttons;
    private JButton[][] AfSecondbuttons;
    private JButton[][] SafFirstbuttons;
    private JButton[][]  NaFirstbuttons;
    private JButton[][] NaSecondbuttons;
    private JButton[][] NaThirdbuttons;
    private JButton[][] ESecondbuttons;
    private JButton[][] EThirdbuttons;
    private JButton[][] AThirdbuttons;
    private JButton[][] ASecondbutton;
    private JButton[][] ASThirdbuttons;
    private JButton AFirstButton;
    private JLabel label = new JLabel();
    private JButton AfButton;
    private JButton SafButton;
    private JButton SafSecondbutton;
    private JButton NaLastbutton;
    private JButton EFirstbutton;
    private JButton ASIbutton;
    private JButton ASNbutton;
    private JButton Japan;
    private JButton Madagascar;
    private JButton greenLand;
    private JButton iceLand;
    private JButton greatBritain;
    private JLabel GameStatus = new JLabel();
    private JPanel LabelPanel = new JPanel();
    private JButton NewZealand;
    private JButton next = new JButton("Next");
    private ArrayList<JButton> territoryButtons;
    private GameplayModel gpm;
    private JMenuBar menu = new JMenuBar();

    /**
     * Setting up a frame for the game board layout.
     */
    public GamePlayFrame() throws InterruptedException {

        //MVC Set Up
        super("Risk Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(688590));
        this.setLayout(new FlowLayout());

        gpm = new GameplayModel();


        this.setBackground(new Color(688590));
        gpm.addGamePlayView(this);
        GamePlayController gpc = new GamePlayController(gpm);
        next.addActionListener(gpc);
        next.setActionCommand("next");
        this.setSize(1600, 800);
        this.setVisible(true);
        this.setResizable(true);  //Changed this to tru

        // create a menu
        JMenu x = new JMenu("Menu");

        // create menuitems
        JMenuItem m1 = new JMenuItem("Save Game");
        m1.addActionListener(gpc);
        m1.setActionCommand("save");

        JMenuItem m2 = new JMenuItem("Load Game");
        m2.addActionListener(gpc);
        m2.setActionCommand("load");


        // add menu items to menu
        x.add(m1);
        x.add(m2);

        // add menu to menu bar
        menu.add(x);

        // add menubar to frame
        this.setJMenuBar(menu);
        //JPanel p = new JPanel();
        //Graphics g = p.getGraphics();
        //g.drawLine(120,120,220,200);
        //g.setColor(Color.black);
        //this.add(p);
        LabelPanel.setPreferredSize(new Dimension(1400 , 50));
        LabelPanel.setBackground(Color.gray);
        LabelPanel.add(GameStatus);
        territoryButtons = new ArrayList<>(); //Set up List of all Territory Buttons

        gpm.displayInitialInstructions();


        //GameStatus.setText("This is where we will have the status");
        this.add(LabelPanel);
        this.add(next);
        NorthAmericaSetup(gpc);
        extraSetup(gpc);
        EuropeSetup(gpc);
        AsiaSetup(gpc);
        JapanSetup(gpc);
        SouthAmericaSetUp(gpc);
        uselessSetup();
        AfricaSetUp(gpc);
        MadagascarSetup(gpc);
        AustraliaSetUp(gpc);
        NewZealandSetup(gpc);

        gpm.gameStatus();
       // if(gpm.playersAlive.get(0).isAIplayer()) {
         //   gpm.CheckAiPlayer();
        //}
        ///////**********
    }

    /**
     *Handles game instructions and player name updating for every game play event
     * @param e
     */
    @Override
    public void handleGamePlayUpdate(GamePlayEvent e ) throws InterruptedException {

       // if(custom)(
        //        //create new frame or change the layout and create new buttons
         //       )
        Territory[] territories = e.getTerritories();

        int handsize = e.getPlayerHand().handList().size();
        GameplayModel gpm = (GameplayModel) e.getSource();
        String output = null;    //+ e.getcurrentPlayer().getColor()

        output = "Current Player: " + e.getPlayerName() +  ". Number of cards: " + handsize + ". " + e.getInstructions();  //ADD later: Instructions and Outcome

        Color color = e.getPlayerColor();

        GameStatus.setText(output);
        LabelPanel.setBackground(color);

        //Update the Territory Owners and Number of Troops
        //Iterate through the list of Buttons
        //If Button name = Territory Name
        //Change the Text of the Button to : Territory Name. Territory Owner. # of Troops using Territory List
        for (JButton territoryButton : territoryButtons){
            for (Territory terr : territories){
                if (territoryButton.getActionCommand().equals(terr.getName())){
                    if(terr.getPlayer().getName().equals("1"))
                    {
                        //GRAY
                        territoryButton.setBackground(Color.GRAY);
                    }
                    else if(terr.getPlayer().getName().equals("2"))
                    {
                        territoryButton.setBackground(new Color(184, 59, 59));
                    }
                    else if(terr.getPlayer().getName().equals("3"))
                    {
                        territoryButton.setBackground(new Color(73, 186, 58));
                    }
                    else if(terr.getPlayer().getName().equals("4"))
                    {
                        territoryButton.setBackground(new Color(224, 227, 75));
                    }
                    else if(terr.getPlayer().getName().equals("5"))
                    {
                        territoryButton.setBackground(new Color(
                                181, 121, 224
                        ));
                    }

                    else if(terr.getPlayer().getName().equals("6"))
                    {
                        //PINK
                        territoryButton.setBackground(Color.PINK);
                    }
                    territoryButton.setText(terr.getTroops() +" :"+ territoryButton.getActionCommand());
                }
            }
        }

        if(e.isStartOver()){
            this.dispose();
            System.exit(0);
        }

    }

    /**
     *Madagascar GUI setup
     * @param gpc ActionListener
     */
    private  void MadagascarSetup(ActionListener gpc){
        Madagascar = new JButton("Madagascar");
        Madagascar.setPreferredSize((new Dimension(120,110)));
        Madagascar.addActionListener(gpc);
        Madagascar.setActionCommand("Madagascar");
        this.add(Madagascar);

        territoryButtons.add(Madagascar);  //Add to ArrayList
    }

    /*
    Number of terrs
    <Terr>
        Name

     */

    /**
     * Japan GUI setup
     * @param gpc
     */
    private void JapanSetup(ActionListener gpc){
        Japan = new JButton("Japan");
        Japan.setPreferredSize(new Dimension( 100 ,50 ));
        Japan.addActionListener(gpc);
        Japan.setActionCommand("Japan");
        this.add(Japan);

        territoryButtons.add(Japan);  //Add to ArrayList

    }

    /**
     *NewZealand GUI setup
     * @param gpc
     */
    private void NewZealandSetup(ActionListener gpc) {
        NewZealand = new JButton("NewZealand");
        NewZealand.setPreferredSize(new Dimension(120, 80));
        NewZealand.addActionListener(gpc);
        NewZealand.setActionCommand("NewZealand");
        this.add(NewZealand);

        territoryButtons.add(NewZealand);  //Add to ArrayList
    }

    /**
     * extra GUI elements
     * @param gpc
     */
    private void extraSetup(ActionListener gpc){
        JPanel extra = new JPanel();
        extra.setLayout(new GridLayout(3,1));
        extra.setPreferredSize(new Dimension(360,200));  //Before: width = 300
        JPanel exFirstPanel = new JPanel();
        exFirstPanel.setLayout(new GridLayout(1,3));
        JPanel exempty1 = new JPanel();
        exempty1.setBackground(new Color(688590));

        greenLand = new JButton("GreenLand");
        greenLand.addActionListener(gpc);
        greenLand.setActionCommand("GreenLand");
        territoryButtons.add(greenLand);  //Add to ArrayList
        iceLand = new JButton("Iceland");
        iceLand.addActionListener(gpc);
        iceLand.setActionCommand("Iceland");
        territoryButtons.add(iceLand);  //Add to ArrayList
        exFirstPanel.add(greenLand);
        exFirstPanel.add(exempty1);
        exFirstPanel.add(iceLand);
        JPanel exSecond = new JPanel();
        exSecond.setLayout(new GridLayout(1,1));
        exSecond.setBackground(new Color(688590));
        JPanel exThird = new JPanel();
        exThird.setLayout(new GridLayout(1,3));
        JPanel exempty2 = new JPanel();
        exempty2.setBackground(new Color(688590));
        JPanel exempty3 = new JPanel();
        exempty3.setBackground(new Color(688590));
        exThird.add(exempty2);
        exThird.add(exempty3);

        greatBritain = new JButton("GreatBritain");
        greatBritain.addActionListener(gpc);
        greatBritain.setActionCommand("GreatBritain");
        territoryButtons.add(greatBritain);  //Add to ArrayList
        exThird.add(greatBritain);
        extra.add(exFirstPanel);
        extra.add(exSecond);
        extra.add(exThird);
        this.add(extra);


    }

    /**
     *Background GUI setup
     */
    private void uselessSetup(){
        JPanel useless = new JPanel();
        useless.setPreferredSize(new Dimension(100 , 100));
        useless.setBackground(new Color(688590));
        this.add(useless);
    }

    /**
     * Australia GUI Setup
     * @param gpc
     */
    private void AustraliaSetUp(ActionListener gpc){
        JPanel Australia = new JPanel();
        Australia.setLayout(new GridLayout(3,1));
        Australia.setPreferredSize(new Dimension(300,200));
        JPanel ASThirdPanel = new JPanel();
        ASThirdPanel.setLayout(new GridLayout(1,2));
        ASThirdbuttons = new JButton[1][2];
        for(int i =0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("WesternAustralia");
                b.setActionCommand("WesternAustralia");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("EasternAustralia");
                b.setActionCommand("EasternAustralia");
                territoryButtons.add(b);  //Add to ArrayList
            }
            ASThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            ASThirdPanel.add(b);
        }
        JPanel ASSecondPanel = new JPanel();
        ASSecondPanel.setLayout(new GridLayout(1,1));
        ASSecondPanel.setBackground(new Color(688590));
        JPanel ASFirstPanel = new JPanel();
        ASFirstPanel.setLayout(new GridLayout(1,3));
        JPanel ASempty1 = new JPanel();
        ASempty1.setBackground(new Color(688590));
        ASIbutton = new JButton("Indonesia");
        ASIbutton.addActionListener(gpc);
        ASIbutton.setActionCommand("Indonesia");
        territoryButtons.add(ASIbutton);  //Add to ArrayList

        ASNbutton = new JButton("NewGuinea");
        ASNbutton.addActionListener(gpc);
        ASNbutton.setActionCommand("NewGuinea");
        territoryButtons.add(ASNbutton);  //Add to ArrayList
        ASFirstPanel.add(ASNbutton);
        ASFirstPanel.add(ASempty1);
        ASFirstPanel.add(ASIbutton);
        Australia.add(ASFirstPanel);
        Australia.add(ASSecondPanel);
        Australia.add(ASThirdPanel);
        this.add(Australia);

    }

    /**
     *Asia GUI Setup
     * @param gpc
     */
    private void AsiaSetup(ActionListener gpc){
        JPanel Asia = new JPanel();
        Asia.setLayout(new GridLayout(3 ,1));
        Asia.setPreferredSize(new Dimension(300 ,300));
        JPanel AThirdPanel = new JPanel();
        AThirdPanel.setLayout(new GridLayout(1,2));
        AThirdbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("MiddleEast");
                b.setActionCommand("MiddleEast");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("India");
                b.setActionCommand("India");
                territoryButtons.add(b);  //Add to ArrayList
            }
            AThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            AThirdPanel.add(b);
        }
        JPanel ASecondPanel = new JPanel();
        ASecondPanel.setLayout(new GridLayout(1,3));
        JPanel AemptyPanel = new JPanel();
        AemptyPanel.setBackground(new Color(688590));
        ASecondPanel.add(AemptyPanel);
        ASecondbutton = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Afghanistan");
                b.setActionCommand("Afghanistan");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i==1){
                b.setText("China");
                b.setActionCommand("China");
                territoryButtons.add(b);  //Add to ArrayList
            }
            ASecondbutton[0][i] = b;
            b.addActionListener(gpc);
            ASecondPanel.add(b);
        }
        JPanel AFirstPanel = new JPanel();
        AFirstPanel.setLayout(new GridLayout(1,3));
        JPanel Aempty2 = new JPanel();
        Aempty2.setBackground(new Color(688590));
        JPanel Aempty3 = new JPanel();
        Aempty3.setBackground(new Color(688590));
        AFirstPanel.add(Aempty2);
        AFirstPanel.add(Aempty3);
        AFirstButton = new JButton("Mongolia");
        AFirstButton.addActionListener(gpc);
        AFirstButton.setActionCommand("Mongolia");
        territoryButtons.add(AFirstButton);  //Add to ArrayList

        AFirstPanel.add(AFirstButton);
        Asia.add(AFirstPanel);
        Asia.add(ASecondPanel);
        Asia.add(AThirdPanel);
        this.add(Asia);

    }

    /**
     * Europe GUI Setup
     * @param gpc
     */
    private void EuropeSetup(ActionListener gpc){
        JPanel Europe = new JPanel();
        Europe.setLayout(new GridLayout(3,1));
        Europe.setPreferredSize(new Dimension(300, 260));
        JPanel ElastPanel = new JPanel();
        ElastPanel.setLayout(new GridLayout(1,2));
        EThirdbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("WesternEurope");
                b.setActionCommand("WesternEurope");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("SouthernEurope");
                b.setActionCommand("SouthernEurope");
                territoryButtons.add(b);  //Add to ArrayList
            }
            EThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            ElastPanel.add(b);
        }
        JPanel ESecondPanel = new JPanel();
        ESecondPanel.setLayout(new GridLayout(1,3));
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(688590));
        ESecondPanel.add(emptyPanel);
        ESecondbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("NorthernEurope");
                b.setActionCommand("NorthernEurope");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i==1){
                b.setText("Ukraine");
                b.setActionCommand("Ukraine");
                territoryButtons.add(b);  //Add to ArrayList
            }
            ESecondbuttons[0][i] = b;
            b.addActionListener(gpc);
            ESecondPanel.add(b);
        }
        JPanel EFirstPanel = new JPanel();
        EFirstPanel.setLayout(new GridLayout(1,3));
        JPanel Eempty2 = new JPanel();
        Eempty2.setBackground(new Color(688590));
        JPanel Eempty3 = new JPanel();
        Eempty3.setBackground(new Color(688590));
        EFirstPanel.add(Eempty2);
        EFirstPanel.add(Eempty3);
        EFirstbutton = new JButton("Scandinavia");
        EFirstbutton.addActionListener(gpc);
        EFirstbutton.setActionCommand("Scandinavia");
        territoryButtons.add(EFirstbutton);  //Add to ArrayList
        EFirstPanel.add(EFirstbutton);
        Europe.add(EFirstPanel);
        Europe.add(ESecondPanel);
        Europe.add(ElastPanel);
        this.add(Europe);
    }

    /**
     *North America GUI setup
     * @param gpc
     */
    private void NorthAmericaSetup(ActionListener gpc){
        JPanel northAmerica = new JPanel();
        northAmerica.setLayout(new GridLayout(4,1));
        northAmerica.setPreferredSize(new Dimension(350, 300));  //Before: width = 350
        JPanel NaFirstPanel = new JPanel();
        NaFirstPanel.setLayout(new GridLayout(1,4));
        NaFirstPanel.setBackground(new Color(688590));
        JPanel emptyPanel = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBackground(new Color(688590));
        emptyPanel.setBackground(new Color(688590));
        NaFirstbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Alaska");
                b.setActionCommand("Alaska");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("NorthWestTerritories");
                b.setActionCommand("NorthWestTerritories");
                territoryButtons.add(b);  //Add to ArrayList
            }
            NaFirstbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
            NaFirstPanel.add(b);
        }
        NaFirstPanel.add(emptyPanel);
        NaFirstPanel.add(emptyPanel2);
        JPanel NaSecondPanel = new JPanel();
        NaSecondPanel.setLayout(new GridLayout(1,3));
        NaSecondPanel.setBackground(new Color(688590));
        NaSecondbuttons = new JButton[1][3];
        for(int i = 0 ; i < 3 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Alberta");
                b.setActionCommand("Alberta");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i==1 ){
                b.setText("Ontario");
                b.setActionCommand("Ontario");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>1 ){
                b.setText("Quebec");
                b.setActionCommand("Quebec");
                territoryButtons.add(b);  //Add to ArrayList
            }
            NaSecondbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
            NaSecondPanel.add(b);
        }
        JPanel NaThirdPanel = new JPanel();
        NaThirdPanel.setLayout(new GridLayout(1,2));
        NaThirdbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("WestUSA");
                b.setActionCommand("WestUSA");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i==1 ){
                b.setText("EastUSA");
                b.setActionCommand("EastUSA");
                territoryButtons.add(b);  //Add to ArrayList
            }
            NaThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
            NaThirdPanel.add(b);
        }
        NaLastbutton = new JButton("CentralAmerica");
        NaLastbutton.addActionListener(gpc);
        NaLastbutton.setActionCommand("CentralAmerica");
        territoryButtons.add(NaLastbutton);  //Add to ArrayList
        northAmerica.add(NaFirstPanel);
        northAmerica.add(NaSecondPanel);
        northAmerica.add(NaThirdPanel);
        northAmerica.add(NaLastbutton);
        this.add(northAmerica );
    }

    /**
     *South America GUI setup
     * @param gpc
     */
    private void SouthAmericaSetUp(ActionListener gpc){
        JPanel southAmerica = new JPanel();
        southAmerica.setLayout(new GridLayout(3,1));
        southAmerica.setPreferredSize(new Dimension(250, 300));
        SafButton = new JButton("Venezuela");
        SafButton.addActionListener(gpc);
        SafButton.setActionCommand("Venezuela");///HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
        territoryButtons.add(SafButton);  //Add to ArrayList
        JPanel SafFirstPanel = new JPanel();
        SafFirstPanel.setLayout(new GridLayout(1,2));
        SafFirstbuttons = new JButton[1][2];
        SafSecondbutton = new JButton("Argentina");
        SafSecondbutton.addActionListener(gpc);
        SafSecondbutton.setActionCommand("Argentina");//HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
        territoryButtons.add(SafSecondbutton);  //Add to ArrayList
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Peru");
                b.setActionCommand("Peru");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("Brazil");
                b.setActionCommand("Brazil");
                territoryButtons.add(b);  //Add to ArrayList
            }
            SafFirstbuttons[0][i] = b;
            b.addActionListener(gpc);
            // b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
            SafFirstPanel.add(b);
        }
        southAmerica.add(SafButton);
        southAmerica.add(SafFirstPanel);
        southAmerica.add(SafSecondbutton);
        this.add(southAmerica );

    }

    /**
     *Africa GUI setup
     * @param gpc
     */
    private void AfricaSetUp(ActionListener gpc){
        JPanel Africa = new JPanel();
        Africa.setLayout(new GridLayout(3,1));
        Africa.setPreferredSize(new Dimension(250, 300));
        JPanel AfFirstPanel = new JPanel();
        AfFirstPanel.setLayout(new GridLayout(1,2));
        JPanel AfSecondPanel = new JPanel();
        AfSecondPanel.setLayout(new GridLayout(1,2));
        AfFirstbuttons = new JButton[1][2];
        AfSecondbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("NorthAfrica");
                b.setActionCommand("NorthAfrica");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("Egypt");
                b.setActionCommand("Egypt");
                territoryButtons.add(b);  //Add to ArrayList
            }
            AfFirstbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand("1 + "" + i");//HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand()
            AfFirstPanel.add(b);
        }
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Congo");
                b.setActionCommand("Congo");
                territoryButtons.add(b);  //Add to ArrayList
            }
            if(i>0 ){
                b.setText("EastAfrica");
                b.setActionCommand("EastAfrica");
                territoryButtons.add(b);  //Add to ArrayList
            }
            AfSecondbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand()
            AfSecondPanel.add(b);
        }
        AfButton = new JButton("SouthAfrica");
        AfButton.addActionListener(gpc);
        AfButton.setActionCommand("SouthAfrica");//HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
        territoryButtons.add(AfButton);  //Add to ArrayList
        Africa.add(AfFirstPanel);
        Africa.add(AfSecondPanel);
        Africa.add(AfButton);

        this.add(Africa );
    }
    public void checkPlayer() throws InterruptedException {
        gpm.gameStatus();
        if(gpm.playersAlive.get(0).isAIplayer()) {
            gpm.CheckAiPlayer();
        }
    }

    public static void newgSetup(){
        JFrame x = new JFrame();
        JButton newg = new JButton("new game");
        JButton load = new JButton("load game");
        x.setLayout(new FlowLayout());
        x.getContentPane().setBackground(Color.pink);
        x.add(newg);
        x.add(load);
        x.setSize(400,400);
        x.setVisible(true);
        newg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                x.dispose();
                try {
                    new GamePlayFrame();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        } );
    }
    /**
     *main method for initializing the frame.
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
       // newgSetup();
      new GamePlayFrame();
    }
}
