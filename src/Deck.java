import java.util.* ;
/**
 * Class Deck is a class used to represent the cards in the deck and the
 * cards out of the deck used (in players hand) while the game is running
 *
 * @author Peter Tanyous
 * @version 1
 */
public class Deck
{
    private LinkedList<Card> deck;
    private LinkedList<Card> playingCards;
    private Random r = new Random();
    /**
     * Constructor for objects of class Deck
     */
    public Deck()
    {
        deck = new LinkedList<Card>();
        playingCards = new LinkedList<Card>();
    }

    /**
     * draws a random card from the deck
     *
     * @return Card the card removed from the deck and added to playingCards list
     */
    public Card drawCard()
    {
        int i = r.nextInt(deck.size());
        Card s = deck.get(i);
        deck.remove(i);
        playingCards.add(s);
        return s;
    }
    /**
     * adds a Card to deck should be used by gamePlay class when player trades in matching cards
     *
     * @param aCard the card added to the deck when player trades in
     */
    public void addCard(Card aCard){
        //removing cards from playing cards and adding them to the deck
        for (int j = 0; j < playingCards.size(); j++) {
            if(playingCards.get(j).getTerritoryName().equals(aCard.getTerritoryName())){
                playingCards.remove(j);
            }
        }
        deck.add(aCard);
    }
}
