import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePlayController implements ActionListener {
    private GameplayModel gpm;
    public GamePlayController(GameplayModel gpm){

        this.gpm = gpm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame parent = new JFrame();
        if(e.getActionCommand().equals("Alberta")){
            JOptionPane.showMessageDialog(parent, "Dont press me you sick son of a bitch");
        }
        if(e.getActionCommand().equals("Ontario")){
            JOptionPane.showMessageDialog(parent, "Oh boi am literally where you fucking come from");
        }
        /*String[] coordinates = e.getActionCommand().split("");
        tttm.play(Integer.parseInt(coordinates[0]), Integer.parseInt((coordinates[1])));
        TicTacToeModel.Status s = tttm.getStatus();
        JFrame parent = new JFrame();
        if(s.equals(TicTacToeModel.Status.O_WON) ){
            JOptionPane.showMessageDialog(parent, " Player O Won");
        }
        if(s.equals(TicTacToeModel.Status.X_WON)){
            JOptionPane.showMessageDialog(parent, " Player X Won");
        }
        if(s.equals((TicTacToeModel.Status.TIE))){
            JOptionPane.showMessageDialog(parent, "ITS A TIE");

         */
        }
    }
