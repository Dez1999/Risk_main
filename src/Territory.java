import java.util.* ;
/*
 *Territory Class represents each territorty and the amount of troops on it and who is
 * the owner player along with a list of all adjacent territiories
 *
 * @author Peter Tanyous
 * @version 1
 */
public class Territory
{
    private String name;
    private Player owner;
    private int troops ;
    private LinkedList<Territory> boardingTerritories;
    private int numberOppTerritories;
    private boolean flagAdded;
    /*
     * Constructor for objects of class Territory
     */
    public Territory(String name)
    {
        this.name = name;
        //this.boardingTerritories = boardingTerritories;
        boardingTerritories = new LinkedList<>();
        numberOppTerritories = 0;
        flagAdded = false;
    }

    /**
     * returns the name of the territory
     *
     *
     * @return String name of the territory
     */
    public String getName()
    {

        return name;
    }
    /**
     * returns the owner Player of the territory
     *
     *
     * @return Player owner of the territory
     */
    public Player getPlayer(){
        return owner;
    }
    /**
     * returns the number of troops on the territory
     *
     *
     * @return int troops on the territory
     */
    public int getTroops(){
        return troops;
    }
    /**
     * adds a territory to the bordering territories used by Board class
     * to initialize the map
     *
     * @param adjacent to be added to the boardingTerritories of the list
     */
    void addBorderTerritories(Territory adjacent){
        if (adjacent != null) {
            boardingTerritories.add(adjacent);
        }
    }
    /**
     * adds troops to the territory
     *
     * @param troops number of troops to be added to the territory when gamePlay class has a
     * player in the drafting phase
     */
    public void addTroops(int troops){
        this.troops = this.troops + troops;
    }
    /**
     * removes troops from the territory
     *
     * @param troops number of troops to be removed from the territory when gamePlay has player in fortifying phase
     */
    public void removeTroops(int troops){
        this.troops = this.troops - troops;
    }
    /**
     * changes the owner of the territory when  defender loses or attacker loses in the
     * battle
     *
     * @param newOwner new owner of the territory
     */
    public void changeOwner(Player newOwner){
        owner = newOwner;
    }

    /**
     * Returns the borderingTerritories for the Territory
     * @return boardingTerritories
     */
    public LinkedList<Territory> getBorderTerritories() {
        return boardingTerritories;
    }

    /**
     * For AI Player: Adds to number of Opponent Bordering Territories
     * @param i
     */
    public void addNumberOppTerr(int i) {
        numberOppTerritories++;

    }

    /**
     * Returns number of Opponent Bordering Territories
     * @return
     */
    public int getNumberOppTerritories() {
        return numberOppTerritories;
    }

    /**
     * Sets Number of Opponent Bordering Territories
     * @param i
     */
    public void setNumberOppTerr(int i){
        numberOppTerritories = i;
    }

    public void setTroops(int i) {
        troops = i;
    }

    public void setFlagAdded(boolean flag) {
        flagAdded = flag;
    }
    public boolean getFlagAdded(){
        return  flagAdded;
    }
}