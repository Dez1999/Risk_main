import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
    private JButton next = new JButton("next");
    public GamePlayFrame(){
        super("Risk Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLUE);
        this.setLayout(new FlowLayout());
        GameplayModel gpm = new GameplayModel();
        this.setBackground(Color.BLUE);
        gpm.addGamePlayView(this);
        GamePlayController gpc = new GamePlayController(gpm);
        //buttons = new JButton[2][2];
        /*for( int i =0; i < 2 ; i++) {
            for (int j = 0; j < 2; j++) {
                JButton b = new JButton("some Country ");
                buttons[i][j] = b;
                b.addActionListener(gpc);
                b.setActionCommand(i + "" + j);
                Africa.add(b);

            }
        }*/
        next.addActionListener(gpc);
        next.setActionCommand("next");
        this.setSize(1600, 800);
        this.setVisible(true);
        this.setResizable(false);
        LabelPanel.setPreferredSize(new Dimension(1400 , 50));
        LabelPanel.setBackground(Color.YELLOW);
        LabelPanel.add(GameStatus);
        //Show GameStatus
        gpm.setInstructions(gpm.printWelcome() + gpm.printRules() + "At the start of each turn each player receives 3 or more troops and" +
                " if you rule a whole continent you will get more bonus troops.");
        gpm.gameStatus();
        gpm.setInstructions("Player 0 begins the Game. Please choose the Territory to Deploy Troops to");
        gpm.gameStatus();
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

    }

    @Override
    public void handleGamePlayUpdate(GamePlayEvent e ) {
        //  int x = e.getX();
        // int y = e.getY();
        e.getcurrentPlayer();

        int handsize = e.getPlayerHand().handList().size();
        e.getPlayerName();
        e.getInstructions();

        GameplayModel gpm = (GameplayModel) e.getSource();
        String output = null;
        output = "Current Player: " + e.getPlayerName() + ". Number of cards: " + handsize + ". " + e.getInstructions();  //ADD later: Instructions and Outcome
        GameStatus.setText(output);

    }
    private  void MadagascarSetup(ActionListener gpc){
        Madagascar = new JButton("Madagascar");
        Madagascar.setPreferredSize((new Dimension(110,110)));
        Madagascar.addActionListener(gpc);
        Madagascar.setActionCommand("Madagascar");
        this.add(Madagascar);
    }
    private void JapanSetup(ActionListener gpc){
        Japan = new JButton("Japan");
        Japan.setPreferredSize(new Dimension( 100 ,50 ));
        Japan.addActionListener(gpc);
        Japan.setActionCommand("Japan");
        this.add(Japan);

    }
    private void NewZealandSetup(ActionListener gpc){
        NewZealand = new JButton("NewZealand");
        NewZealand.setPreferredSize(new Dimension( 120 ,80 ));
        NewZealand.addActionListener(gpc);
        NewZealand.setActionCommand("NewZealand");
        this.add(NewZealand);}
    private void extraSetup(ActionListener gpc){
        JPanel extra = new JPanel();
        extra.setLayout(new GridLayout(3,1));
        extra.setPreferredSize(new Dimension(300,200));
        JPanel exFirstPanel = new JPanel();
        exFirstPanel.setLayout(new GridLayout(1,3));
        JPanel exempty1 = new JPanel();
        exempty1.setBackground(Color.BLUE);
        greenLand = new JButton("GreenLand");
        greenLand.addActionListener(gpc);
        greenLand.setActionCommand("GreenLand");
        iceLand = new JButton("Iceland");
        iceLand.addActionListener(gpc);
        iceLand.setActionCommand("Iceland");
        exFirstPanel.add(greenLand);
        exFirstPanel.add(exempty1);
        exFirstPanel.add(iceLand);
        JPanel exSecond = new JPanel();
        exSecond.setLayout(new GridLayout(1,1));
        exSecond.setBackground(Color.BLUE);
        JPanel exThird = new JPanel();
        exThird.setLayout(new GridLayout(1,3));
        JPanel exempty2 = new JPanel();
        exempty2.setBackground(Color.BLUE);
        JPanel exempty3 = new JPanel();
        exempty3.setBackground(Color.BLUE);
        exThird.add(exempty2);
        exThird.add(exempty3);
        greatBritain = new JButton("GreatBritain");
        greatBritain.addActionListener(gpc);
        greatBritain.setActionCommand("GreatBritain");
        exThird.add(greatBritain);
        extra.add(exFirstPanel);
        extra.add(exSecond);
        extra.add(exThird);
        this.add(extra);


    }
    private void uselessSetup(){
        JPanel useless = new JPanel();
        useless.setPreferredSize(new Dimension(100 , 100));
        useless.setBackground(Color.BLUE);
        this.add(useless);
    }
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
            }
            if(i>0 ){
                b.setText("EasternAustralia");
                b.setActionCommand("EasternAustralia");
            }
            ASThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            ASThirdPanel.add(b);
        }
        JPanel ASSecondPanel = new JPanel();
        ASSecondPanel.setLayout(new GridLayout(1,1));
        ASSecondPanel.setBackground(Color.BLUE);
        JPanel ASFirstPanel = new JPanel();
        ASFirstPanel.setLayout(new GridLayout(1,3));
        JPanel ASempty1 = new JPanel();
        ASempty1.setBackground(Color.BLUE);
        ASIbutton = new JButton("Indonesia");
        ASIbutton.addActionListener(gpc);
        ASIbutton.setActionCommand("Indonesia");
        ASNbutton = new JButton("NewGuinea");
        ASNbutton.addActionListener(gpc);
        ASNbutton.setActionCommand("NewGuinea");
        ASFirstPanel.add(ASNbutton);
        ASFirstPanel.add(ASempty1);
        ASFirstPanel.add(ASIbutton);
        Australia.add(ASFirstPanel);
        Australia.add(ASSecondPanel);
        Australia.add(ASThirdPanel);
        this.add(Australia);

    }
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
            }
            if(i>0 ){
                b.setText("India");
                b.setActionCommand("India");
            }
            AThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            AThirdPanel.add(b);
        }
        JPanel ASecondPanel = new JPanel();
        ASecondPanel.setLayout(new GridLayout(1,3));
        JPanel AemptyPanel = new JPanel();
        AemptyPanel.setBackground(Color.BLUE);
        ASecondPanel.add(AemptyPanel);
        ASecondbutton = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Afghanistan");
                b.setActionCommand("Afghanistan");
            }
            if(i==1){
                b.setText("China");
                b.setActionCommand("China");
            }
            ASecondbutton[0][i] = b;
            b.addActionListener(gpc);
            ASecondPanel.add(b);
        }
        JPanel AFirstPanel = new JPanel();
        AFirstPanel.setLayout(new GridLayout(1,3));
        JPanel Aempty2 = new JPanel();
        Aempty2.setBackground(Color.BLUE);
        JPanel Aempty3 = new JPanel();
        Aempty3.setBackground(Color.BLUE);
        AFirstPanel.add(Aempty2);
        AFirstPanel.add(Aempty3);
        AFirstButton = new JButton("Mongolia");
        AFirstButton.addActionListener(gpc);
        AFirstButton.setActionCommand("Mongolia");
        AFirstPanel.add(AFirstButton);
        Asia.add(AFirstPanel);
        Asia.add(ASecondPanel);
        Asia.add(AThirdPanel);
        this.add(Asia);

    }
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
            }
            if(i>0 ){
                b.setText("SouthernEurope");
                b.setActionCommand("SouthernEurope");
            }
            EThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            ElastPanel.add(b);
        }
        JPanel ESecondPanel = new JPanel();
        ESecondPanel.setLayout(new GridLayout(1,3));
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.BLUE);
        ESecondPanel.add(emptyPanel);
        ESecondbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("NorthernEurope");
                b.setActionCommand("NorthernEurope");
            }
            if(i==1){
                b.setText("Ukraine");
                b.setActionCommand("Ukraine");
            }
            ESecondbuttons[0][i] = b;
            b.addActionListener(gpc);
            ESecondPanel.add(b);
        }
        JPanel EFirstPanel = new JPanel();
        EFirstPanel.setLayout(new GridLayout(1,3));
        JPanel Eempty2 = new JPanel();
        Eempty2.setBackground(Color.BLUE);
        JPanel Eempty3 = new JPanel();
        Eempty3.setBackground(Color.BLUE);
        EFirstPanel.add(Eempty2);
        EFirstPanel.add(Eempty3);
        EFirstbutton = new JButton("Scandinavia");
        EFirstbutton.addActionListener(gpc);
        EFirstbutton.setActionCommand("Scandinavia");
        EFirstPanel.add(EFirstbutton);
        Europe.add(EFirstPanel);
        Europe.add(ESecondPanel);
        Europe.add(ElastPanel);
        this.add(Europe);
    }
    private void NorthAmericaSetup(ActionListener gpc){
        JPanel northAmerica = new JPanel();
        northAmerica.setLayout(new GridLayout(4,1));
        northAmerica.setPreferredSize(new Dimension(350, 300));
        JPanel NaFirstPanel = new JPanel();
        NaFirstPanel.setLayout(new GridLayout(1,4));
        NaFirstPanel.setBackground(Color.BLUE);
        JPanel emptyPanel = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBackground(Color.BLUE);
        emptyPanel.setBackground(Color.BLUE);
        NaFirstbuttons = new JButton[1][2];
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Alaska");
                b.setActionCommand("Alaska");
            }
            if(i>0 ){
                b.setText("NorthWestTerritories");
                b.setActionCommand("NorthWestTerritories");
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
        NaSecondPanel.setBackground(Color.BLUE);
        NaSecondbuttons = new JButton[1][3];
        for(int i = 0 ; i < 3 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Alberta");
                b.setActionCommand("Alberta");
            }
            if(i==1 ){
                b.setText("Ontario");
                b.setActionCommand("Ontario");
            }
            if(i>1 ){
                b.setText("Quebec");
                b.setActionCommand("Quebec");
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
            }
            if(i==1 ){
                b.setText("EastUSA");
                b.setActionCommand("EastUSA");
            }
            NaThirdbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
            NaThirdPanel.add(b);
        }
        NaLastbutton = new JButton("CentralAmerica");
        NaLastbutton.addActionListener(gpc);
        NaLastbutton.setActionCommand("CentralAmerica");
        northAmerica.add(NaFirstPanel);
        northAmerica.add(NaSecondPanel);
        northAmerica.add(NaThirdPanel);
        northAmerica.add(NaLastbutton);
        this.add(northAmerica );
    }
    private void SouthAmericaSetUp(ActionListener gpc){
        JPanel southAmerica = new JPanel();
        southAmerica.setLayout(new GridLayout(3,1));
        southAmerica.setPreferredSize(new Dimension(250, 300));
        SafButton = new JButton("Peru");
        SafButton.addActionListener(gpc);
        SafButton.setActionCommand("Peru");///HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
        JPanel SafFirstPanel = new JPanel();
        SafFirstPanel.setLayout(new GridLayout(1,2));
        SafFirstbuttons = new JButton[1][2];
        SafSecondbutton = new JButton("Argentina");
        SafSecondbutton.addActionListener(gpc);
        SafSecondbutton.setActionCommand("Argentina");//HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
        for(int i = 0 ; i < 2 ; i ++){
            JButton b = new JButton();
            if(i == 0){
                b.setText("Venezuela");
                b.setActionCommand("Venezuela");
            }
            if(i>0 ){
                b.setText("Brazil");
                b.setActionCommand("Brazil");
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
            }
            if(i>0 ){
                b.setText("Egypt");
                b.setActionCommand("Egypt");
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
            }
            if(i>0 ){
                b.setText("EastAfrica");
                b.setActionCommand("EastAfrica");
            }
            AfSecondbuttons[0][i] = b;
            b.addActionListener(gpc);
            //b.setActionCommand(1 + "" + i); //HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand()
            AfSecondPanel.add(b);
        }
        AfButton = new JButton("SouthAfrica");
        AfButton.addActionListener(gpc);
        AfButton.setActionCommand("SouthAfrica");//HAS TO BE CHANGED TO KNOW WHAT BUTTON IS USED IN THE CONTROLLER WHEN we use e.getActionCommand() each should have a different command
        Africa.add(AfFirstPanel);
        Africa.add(AfSecondPanel);
        Africa.add(AfButton);

        this.add(Africa );
    }
    public static void main(String[] args){
        new GamePlayFrame();
    }
}