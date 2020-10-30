
/**
 * Class Card: Card is a class used to represent the cards to be availabe in the deck
 * that will be used when creating the board there are 42 normal cards and 2 wildCards

 *
 * @author Peter Tanyous
 * @version 1
 */
public class Card
{
    private Territory territory;
    private Troops type;
    /**
     * Constructor for objects of class Card
     */
    public Card(Territory territory , Troops type)
    {
        this.territory = territory;
        this.type = type;
    }

    /**
     * get the territory name that this card represents
     *
     * @return String name of the territory
     */
    public String getTerritoryName()
    {
        return territory.getName();
    }
    /**
     * get the worth of the type of troop the Card belongs too
     *
     * @return int worth of card Troop type
     */
    public int getTypeWorth(){
        return type.getWorth();
    }
}
