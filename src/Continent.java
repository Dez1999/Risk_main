import java.util.ArrayList;


/**
 * Continents are used to give bonus troops for players 
 * who control the continent. Each continent gives different
 * amount of bonus troops for players in control.
 *
 * @author Peter Tanyous
 * @version 1.01 October 24, 2020
 */
public class Continent {

    private String name;
    private int bonusArmies;
    private ArrayList<Territory> territories;

    public Continent(String name, int bonusArmies, ArrayList<Territory> territories) {
        this.name = name;
        this.bonusArmies = bonusArmies;
        this.territories = territories;

        System.out.println("Created continent: " + name + "\nBonus armies: " + bonusArmies);
    }

    public String getName() {
        return name;
    }

    /**
     *  Returns the number of bonus armies a player gets per round when the player controls this
     * continent
     **/
    public int getBonusArmies() {
        return bonusArmies;
    }

    /**
     * Retuens a list of the country objects that are on this continent
     **/
    public ArrayList<Territory> getMemberTerritories() {
        return territories;
    }
}