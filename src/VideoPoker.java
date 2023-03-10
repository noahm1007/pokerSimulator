// noah marceau 3/9/23
// method testing method at bottom

// 3/10/23 doesnt count 3 of a kind on ?second win?

import java.lang.reflect.Array;
import java.util.*;

public class VideoPoker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Card> deck = new ArrayList<Card>();
        ArrayList<Card> hand = new ArrayList<>();
        makeDeck(deck);
        shuffleDeck(deck);

        boolean testingMode = true; // very useful variable
        if (testingMode) { methodTest(); }

        System.out.println("""
                [ #---------------# ]
                welcome to Video Poker!
                [ #---------------# ]
                """);

//        check for valid input
        System.out.print("please insert money: ");
        int money = input.nextInt();

        while (money <= 0) {
            System.out.print("[#] invalid input, please try again: ");
            money = input.nextInt();
        }

        boolean runAgain = true;
        boolean first = true;

        while (runAgain) {
            System.out.println("""
                    \n[ #--------[ MENU ]--------# ]
                    \t1. insert money
                    \t2. view machine payouts
                    \t3. bet
                    \t4. cash out
                    [ #--------[ MENU ]--------# ]
                    """);

            if (first) {
                System.out.print("[#] you are starting with " + money + " credits, please choose an option: ");
                first = false;
            } else {
                System.out.print("[#] you have " + money + " credits, please choose an option: ");
            }

            String choice = input.next();

            switch (choice) {
                case "1", "insert", "insert money" -> {
                    System.out.print("[#] how much money would you like to insert?: ");
                    int insertedMoney = input.nextInt();

                    while (insertedMoney <= 0) {
                        System.out.print("[#] invalid input, please try again: ");
                        insertedMoney = input.nextInt();
                    }

                    money += insertedMoney;
                    System.out.println("[#] successfully inserted " + insertedMoney + " credits.");
                }
                case "2", "view", "machine payouts", "view machine payouts", "payouts", "view payouts" -> {
                    System.out.println("""
                            \n[ #---------------# ]
                            the payouts for this machine are:
                            \troyal flush: 250 * bet (jackpot bet of 5: 4000)
                            \tstraight flush: 50 * bet
                            \t4 of a kind: 25 * bet
                            \tfull house: 8 * bet
                            \tflush: 5 * bet
                            \tstraight: 4 * bet
                            \t3 of a kind: 3 * bet
                            \t2 pair: 2 * bet
                            \tjacks or better: 1 * bet
                            [ #---------------# ]
                            """);
                }
                case "3", "bet" -> {
                    System.out.print("[#] bet how many credits? (1-5): ");
                    int bet = input.nextInt();

                    while (bet <= 0 || bet > 5) {
                        System.out.print("[#] invalid bet, please try again: ");
                        bet = input.nextInt();
                    }
                    System.out.println("[#] bet of " + bet + " credits confirmed.");
                    money-=bet;

                    for (int i = 0; i < 5; i++) { hand.add(deck.get(i)); }
                    for (int i = 0; i < hand.size(); i++) { deck.remove(0); }
                    boolean keepHoldSelection = true;
                    int cardsHeld = 0;

                    while (keepHoldSelection) {
                        System.out.println("[ #---------------------[ HAND ]---------------------# ]");
                        printDeck(hand, 1, 5);

                        for (int i = 0; i < hand.size(); i++) {
                            if (hand.get(i).isHeld) {
                                System.out.print("  [HELD]  ");
                            } else {
                                System.out.print(" [NOT HELD]");
                            }
                        }

                        System.out.println("\n[ #---------------------[ HAND ]---------------------# ]");
                        System.out.print("""
                                [#] select which cards to hold: 
                                \t0. draw
                                """);

                        for (int i = 0; i < hand.size(); i++) {
                            if (hand.get(i).isHeld) {
                                System.out.println("\t" + (i + 1) + ". " + hand.get(i).name() + " [HELD] ");
                            } else {
                                System.out.println("\t" + (i + 1) + ". " + hand.get(i).name() + " [NOT HELD] ");
                            }
                        }
                        int selection = input.nextInt();

                        switch (selection) {
                            case 0 -> keepHoldSelection = false;
                            case 1 -> hand.get(0).isHeld = !hand.get(0).isHeld;
                            case 2 -> hand.get(1).isHeld = !hand.get(1).isHeld;
                            case 3 -> hand.get(2).isHeld = !hand.get(2).isHeld;
                            case 4 -> hand.get(3).isHeld = !hand.get(3).isHeld;
                            case 5 -> hand.get(4).isHeld = !hand.get(4).isHeld;
                        }
                    }

                    for (int i = 0; i < hand.size(); i++) {
                        if (!hand.get(i).isHeld) {
                            cardsHeld++;
                            hand.set(i, deck.get(i));

                        }
                    }

                    if (cardsHeld != 0) {
                        System.out.println("[#] drawing " + cardsHeld + " new cards...");
                        System.out.println("[#] your new hand is: ");
                    } else {
                        System.out.println("[#] drawing no new cards...");
                        System.out.println("[#] your hand is unchanged: ");
                    }

                    System.out.println("[ #---------------------[ HAND ]---------------------# ]");
                    printDeck(hand, 1, 5);
                    System.out.println("[ #---------------------[ HAND ]---------------------# ]");

                    System.out.print("[#] hand result: ");
                    int oldBalance = money;

                    if (onePair(hand)) { System.out.print("one pair | payout: " + bet); money+=bet; }
                    if (twoPair(hand)) { System.out.print("two pair | payout: " + bet*2); money+=bet*2; }
                    if (threeOfAKind(hand)) { System.out.print("three of a kind | payout: " + bet*3); money+=bet*3; }
                    if (straight(hand)) { System.out.print("straight | payout: " + bet*4); money+=bet*4; }
                    if (flush(hand)) { System.out.print("flush | payout: " + bet*5); money+=bet*5; }
                    if (fullHouse(hand)) { System.out.print("full house | payout: " + bet*8); money+=bet*8; }
                    if (fourOfAKind(hand)) { System.out.print("four of a kind | payout: " + bet*25); money+=bet*25; }
                    if (straightFlush(hand)) { System.out.print("straight flush | payout: " + bet*50); money+=bet*50; }
                    if (royalFlush(hand) && bet != 5) { System.out.print("royal flush 🎉 | payout: " + bet*250); money+=bet*250; }
                    if (royalFlush(hand) && bet == 5) { System.out.print("royal flush 🎉 | payout: " + 4000); money+=4000; }
                    if (oldBalance == money) { System.out.print("nothing!"); }

                    hand.clear();
                    deck.clear();
                    makeDeck(deck);
                    shuffleDeck(deck);
                }
                case "4", "cash out" -> {
                    System.out.println("[#] printing voucher for " + money + " credits. thank you for playing!");
                    runAgain = false;
                }
                default -> { System.out.println("[#] invalid input, returning to menu..."); }
            }
        }
    }

    public static void makeDeck(ArrayList<Card> deck) {
        int c = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card(i, j+2));
                deck.get(c).fillCard();
                c++;
            }
        }
    }

    public static void shuffleDeck(ArrayList<Card> deck) {
        for (int i = 0; i < deck.size(); i++) {
            Card c = deck.get(i);
            deck.remove(i);
            deck.add((int)(deck.size() * Math.random()), c);
        }
    }

    public static void printDeck(ArrayList<Card> deck, int length, int width) {
        int c = 0;
        for (int k = 0; k < length; k++) {
            for (int i = 0; i < deck.get(0).card.length; i++) {
                for (int l = 0; l < width; l++) {
                    System.out.print(deck.get(l+c).card[i]);
                }
                System.out.println();
            }
            c+=width;
        }
    }

//    hand methods

    public static int minCardIndex(ArrayList<Card> hand) {
        int minValue = 0;

        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getValue() < minValue) {
                minValue = i;
            }
        } return minValue;
    }

    public static ArrayList<Card> sortHand(ArrayList<Card> hand) {
        ArrayList<Card> tempHand = hand;
        ArrayList<Card> sortedHand = new ArrayList<Card>();

        for (int i = 0; i < tempHand.size(); i++) {
            for (int j = 0; j < tempHand.size(); j++) {
                if (minCardIndex(hand) == i) {
                    sortedHand.add(hand.get(i));
                }
            }
        }

        return sortedHand;
    }

    public static boolean onePair(ArrayList<Card> hand) {
        hand = sortHand(hand);
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == hand.get(i+1).getValue()) { return true; }
        }
        return false;
    }

    public static boolean twoPair(ArrayList<Card> hand) {
        hand = sortHand(hand);
        boolean hasPair = false;

        for (int i = 0; i < hand.size()-1; i++) {
            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(i).getValue() == hand.get(j).getValue()) {
                    hand.remove(i);
                    hand.remove(j-1);
                    hasPair = true;
                }
            }
        }

        return hand.size() == 1;
    }

    public static boolean threeOfAKind(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size() - 2; i++) {
            Card c1 = hand.get(i);
            int count = 1;
            for (int j = i + 1; j < hand.size(); j++) {
                Card c2 = hand.get(j);
                if (c1.getValue() == c2.getValue()) {
                    count++;
                    if (count == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean straight(ArrayList<Card> hand) {
        int minCard = hand.get(0).getValue();
        int c = 1;

        for (Card card : hand) {
            if (card.getValue() < minCard) {
                minCard = card.getValue();
            }
        }

        if (minCard == 2) {
            for (Card card : hand) {
                if (card.getValue() == 14) {
                    minCard = 1;
                }
            }
        }

        for (int i = 0; i < hand.size(); i++) {
            for (Card card : hand) {
                if (card.getValue() == minCard + c) {
                    c++;
                }
            }
        }
        return c == 5;
    }

    public static boolean flush(ArrayList<Card> hand) {
        for (int i = 0; i < hand.get(0).symbols.length; i++) {
            int c = 0;

            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(j).getSymbol() == i) { c++; }
            }

            if (c == 5) { return true; }
        } return false;
    }

    public static boolean fullHouse(ArrayList<Card> hand) {
        boolean hasThreeOfKind = false;
        boolean hasPair = false;

        for (int i = 0; i < hand.size(); i++) {
            int count = 0;
            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(i).getValue() == hand.get(j).getValue()) {
                    count++;
                }
            }
            if (count == 3) {
                hasThreeOfKind = true;
            } else if (count == 2) {
                hasPair = true;
            }
        }

        return hasThreeOfKind && hasPair;
    }


    public static boolean fourOfAKind(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            int c = 0;

            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(i).getValue() == hand.get(j).getValue()) { c++; }
            } return c == 4;
        } return false;
    }

    public static boolean straightFlush(ArrayList<Card> hand) { return flush(hand) && straight(hand); }

    public static boolean royalFlush(ArrayList<Card> hand) {
        int minCard = hand.get(0).getValue();

        for (Card card : hand) {
            if (card.getValue() < minCard) {
                minCard = card.getValue();
            }
        }

        if (minCard >= 10) { return straight(hand) && flush(hand); } return false;
    }

    public static void methodTest() {
//        plug in card values and change return statement
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card(2, 2)); // symbol, value
        hand.add(new Card(0, 3));
        hand.add(new Card(3, 4));
        hand.add(new Card(0, 5));
        hand.add(new Card(3, 13));

        for (int i = 0; i < hand.size(); i++) { hand.get(i).fillCard(); }

        System.out.println("[ #------- [METHOD TESTING] -------# ]");
        printDeck(hand, 1, 5);
        System.out.println(twoPair(hand)); // change method
        System.out.println("[ #------- [METHOD TESTING] -------# ]\n");
    }
}