/**
 * three Dice used when attacker is taking turn
 * if attacker decides to use three.
 *
 * @author Peter Tanyous
 * @version 1.01 October 24, 2020
/**
 * two Dice used when attacker is taking turn
 * if attacker decides to use two dice.
 * Also used by defender when being attacked
 *
 * @author Peter Tanyous
 * @version 1.01 October 24, 2020
 */
import java.util.Random;public class twoDice {

    private Die first;
    private Die second;
    private int x ;
    private int y ;
    public twoDice() {

        Random r = new Random();
        first= new Die(r);
        second= new Die(r);

    }
    public void roll(){
        x = first.roll();
        y = second.roll();
    }
    public int getHighest(){

        if(x>y){
            return x;
        }
        else return y;

    }
    public int getSecondHighest(){
        if(x > y){
            return y;
        }
        else return x;
    }
}
