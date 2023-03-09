import java.util.*;

public class VideoPoker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Card> deck = new ArrayList<Card>();
        ArrayList<Card> hand = new ArrayList<>();
        makeDeck(deck);
        Collections.shuffle(deck);

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
            } input.nextLine();

            String choice = input.nextLine();

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

                    for (int i = 0; i < 5; i++) {

                    }


                    System.out.println("[ #--------------------------[ HAND ]--------------------------# ]");
                    printDeck(deck, 1, 5);
                    System.out.println("[ #--------------------------[ HAND ]--------------------------# ]");

                }
                case "4", "cash out" -> {

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

    public static void printDeck(ArrayList<Card> deck, int length, int width) {
        int c = 0;

        for (int k = 0; k < length; k++) {
            for (int i = 0; i < deck.get(i).card.length; i++) {
                for (int l = 0; l < width; l++) {
                    System.out.print(deck.get(l+c).card[i]);
                }
                System.out.println();
            }
            c+=width;
        }
    }
}