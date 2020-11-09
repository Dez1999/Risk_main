import java.util.EventObject;

public class GamePlayEvent extends EventObject {
    private Board gamePlayBoard;
   // private int x;
   // private int y;

    public GamePlayEvent(GameplayModel gameModel) {//(, TicTacToeModel.Status status, int x, int y))
        super(gameModel);
      // this.status = status;
        //this.x = x;
        //this.y = y;
    }

   // public TicTacToeModel.Status getStatus(){
    //    return status;
    //}
    //public int getX(){
     //   return this.x;
    //}
    //public int getY(){
      //  return this.y;
    //}
}
