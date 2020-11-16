import java.awt.*;
import java.util.* ;
/**
 * Player Class represents each player playing the game and their Hand of cars and a list of the territories owned and
 * continents a player is controlling to manage the amount of bonus troops a player is to get on a new turn
 *
 * @author Peter Tanyous
 * @version 1
 */
public class Player
{
    private String name;
    private Hand playerHand;
    private ArrayList<Territory> territoriesOwned;     /**ArrayList or LinkedList*/
    private ArrayList<Continent> ContinentsOwned;      /**ArrayList or LinkedList*/
    private int bonusTroops;
    private boolean isAlive;
    private String color;


    /**
     * Constructor for objects of class Player
     */
    public Player(String name)
    {
        this.name = name;
        playerHand = new Hand();
        territoriesOwned = new ArrayList<>();
        ContinentsOwned = new ArrayList<>();
        isAlive = true;

    }

    /**
     * Return Player Name
     */
    public String getName(){
        return name;
    }

    /**
     * Set Players color
     * @param color player color
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * Return Players Color
     * @return Color String
     */
    public String getColor(){
        return color;
    }

    /**
     * Return Player Hand
     */
    public Hand getHand(){
        return playerHand;
    }

    /**
     * returns true if player is dead
     *
     * @return boolean true if player is dead else false
     */
    public boolean isDead()
    {
        if(isAlive == true){
            return false;
        }
        else return true;
    }

    /**
     * kills the player
     */
    public void setDead(){
        isAlive = false;
    }
    /**
     * returns the list of territories the player own
     *
     * @return linkedlist of territories the player own
     */
    public ArrayList<Territory> getTerritories(){
        return territoriesOwned;
    }
    /**
     * returns the list of Continents the player control
     *
     * @return linkedlist of Continents the player control
     */
    public ArrayList<Continent> getContinents(){
        return ContinentsOwned;
    }
    /**
     * adds continent to the list of continents the player control
     * when a player owns all territories in a certain continent
     *
     * @param continent to be added to the list
     */
    public void addContinents(Continent continent){
        ContinentsOwned.add(continent);
    }
    /**
     * removes continent from the list of continents the player control
     * when a player loses a territory in a certain continent that player was controlling
     *
     *
     * @param removal to be removed from the list of owned continents
     */
    public void removeContinent(Continent removal){
        for (int j = 0; j < ContinentsOwned.size(); j++){
            if(ContinentsOwned.get(j).getName().equals(removal.getName())){
                ContinentsOwned.remove(j);
            }
        }
    }
    /**
     * adds territory to the list of territories the player own
     * when a player wins an attack on a certain territory
     *
     * @param territory to be added to the list
     */
    public void addTerritories(Territory territory){
        territoriesOwned.add(territory);
    }
    /**
     * removes territory from the list of territories the player own
     * when a player loses a territory in a battle
     *
     *
     * @param removal to be removed from the list of owned territories
     */
    public void removeTerritories(Territory removal){
        for (int j = 0; j < territoriesOwned.size(); j++){
            if(territoriesOwned.get(j).getName().equals(removal.getName())){
                territoriesOwned.remove(j);
            }
        }
    }
}
