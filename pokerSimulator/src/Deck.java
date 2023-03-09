import java.util.*;

public class Deck {
    //    instance variables
    int length;
    int width;
    Card[][] deck;
    int[][] cardValues;

    //    constructors
    public Deck(int length, int width) {
        this.length = length;
        this.width = width;
        this.deck = new Card[length][width];
        this.cardValues = new int[length][width];

        for (int i = 0; i < deck.length; i++) {
            for (int j = 0; j < deck[i].length; j++) {
                deck[i][j] = new Card(i, j+2);
                deck[i][j].fillCard();
                cardValues[i][j] = deck[i][j].getValue();
            }
        }
    }

    public Deck() {
        this.length = 4;
        this.width = 13;
        this.deck = new Card[length][width];
        this.cardValues = new int[length][width];

        for (int i = 0; i < deck.length; i++) {
            for (int j = 0; j < deck[i].length; j++) {
                deck[i][j] = new Card();
                deck[i][j].fillCard();
                cardValues[i][j] = deck[i][j].getValue();
            }
        }
    }

    // methods
    public void printDeck() {
        for (int k = 0; k < deck.length; k++) {
            for (int i = 0; i < deck[0][0].card.length; i++) {
                for (int l = 0; l < deck[k].length; l++) {
                    System.out.print(deck[k][l].card[i] + "\t");
                }
                System.out.println();
            }
        }
    }
}