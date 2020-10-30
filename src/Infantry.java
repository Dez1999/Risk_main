
/**
 * infantry class is the basic unit type of troop worth 1
 *
 * @author Peter Tanyous
 * @version version 1
 */
class infantry extends Troops
{
    private int worth;

    /**
     * Constructor for objects of class infantry
     */
    public infantry()
    {
        worth = 1;
    }
    /*
     * returns the worth of an wildCard to be used in checking what Card is in hands
     *
     * @return int the worth of the troop
     */
    public int getWorth()
    {
        return worth;
    }
}
