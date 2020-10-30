
/**
 * wildCard class is a class extending Troop.java to be able to create
 * Cards of troop type wildCard
 *
 * @author Peter Tanyous
 * @version version 1
 */
class wildCard extends Troops
{
    private int worth;

    /**
     * Constructor for objects of class wildCard
     */
    public wildCard()
    {
        worth = 0;
    }
    /**
     * returns the worth of an wildCard to be used in checking what Card is in hands
     *
     * @return int the worth of the troop
     */
    public int getWorth()
    {
        return worth;
    }
}
