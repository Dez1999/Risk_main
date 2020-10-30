
/**
 * Artillery Class: is a type of troop thats worth 10 infantry
 *
 * @author Peter Tanyous
 * @version version 1
 */
class artillery extends Troops // should this be public?
{
    private int worth;

    /**
     * Constructor for objects of class artillery
     */
    public artillery()
    {
        worth = 10;
    }
    /*
     * returns the worth of an artillerey troop
     *
     * @return int the worth of the troop
     */
    public int getWorth()
    {
        return worth;
    }
}
