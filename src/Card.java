import java.util.*;

public class Card {
    // instance variables
    public String[][] values = new String[][]{
            {"2", "2"},
            {"3", "3"},
            {"4", "4"},
            {"5", "5"},
            {"6", "6"},
            {"7", "7"},
            {"8", "8"},
            {"9", "9"},
            {"10", "10"},
            {"J", "11"},
            {"Q", "12"},
            {"K", "13"},
            {"A", "14"}
    };

    public String[][] symbols = new String[][]{
            {"C", "Clubs"},
            {"D", "Diamonds"},
            {"H", "Hearts"},
            {"S", "Spades"}
    };

    public String[] asciiCard = new String[]{
            "┌─────────┐",
            "│&░░░░░░░│",
            "│░░░░░░░░░│",
            "│░░░░%░░░░│",
            "│░░░░░░░░░│",
            "│░░░░░░░&│",
            "└─────────┘"
    };

    private int symbol;
    private int value;
    public String[] card;
    public boolean isHeld = false;

    // constructors
    public Card() {
        this.symbol = (int)(4*Math.random());
        this.value = (int)(13*Math.random()+2);
        this.card = this.asciiCard;
    }

    public Card(int symbol, int value) {
        this.symbol = symbol;
        this.value = value;
        this.card = this.asciiCard;
    }

    // getters & setters
    public int getSymbol() { return this.symbol; }
    public void setSymbol(int symbol) { this.symbol = symbol; }
    public int getValue() { return this.value; }
    public void setValue(int value) { this.value = value; }

    // methods
    public void fillCard() {
        String val = "";
        boolean is2Digit = false;

        for (int i = 0; i < values.length; i++) {
            if (String.valueOf(this.value).equals(this.values[i][1])) {
                val = this.values[i][0];
                if (this.value == 10) {
                    is2Digit = true;
                }
            }
        }

        if (is2Digit) {
            this.card[1] = "│" + val + "░░░░░░░│";
            this.card[5] = "│░░░░░░░" + val +"│";
        } else {
            this.card[1] = "│" + val + "░░░░░░░░│";
            this.card[5] = "│░░░░░░░░" + val +"│";
        }
        this.card[3] = "│░░░░" + this.symbols[this.symbol][0] + "░░░░│";
    }

    public void printCard() {
        for (int i = 0; i < card.length; i++) {
            System.out.println(card[i]);
        }
    }

    public String name() {
        String name = "";

        for (int i = 0; i < 8; i++) {
            if (this.value-2 == i) { name += values[i][1]; }
        }

        switch (this.value) {
            case 11 -> name += "Jack";
            case 12 -> name += "Queen";
            case 13 -> name += "King";
            case 14 -> name += "Ace";
        }

        name += " of ";
        name += symbols[this.symbol][1];

        return name;
    }
}