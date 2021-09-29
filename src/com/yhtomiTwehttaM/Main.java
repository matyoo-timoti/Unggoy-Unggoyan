package com.yhtomiTwehttaM;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String line = "————————————————————————————————————————————————————————————————————————————————————————————————————————————————————";
    static Hand userHand = new Hand();
    static Hand cpuHand = new Hand();
    static Cards cards = new Cards();
    static Animations anim = new Animations();

    static ArrayList<String> deckList;

    public static void main(String[] args) {
        outer:
        while (true) {
//            Shuffle the deck
            deckList = cards.getDeckList();
            Collections.shuffle(deckList);
            Collections.shuffle(deckList);
//            Hide one card
            cards.setHiddenCard(deckList);
//            Distribute of cards
            int size = deckList.size();
            for (int i = 0; i < size / 2; i++) {
                userHand.addCard(deckList.get(i));
            }
            for (int i = size / 2; i < size; i++)
                cpuHand.addCard(deckList.get(i));
            anim.titleAnim();
            displayDeck();
            whoGoesFirst(isFirstToPick());
//          End part
            System.out.println(line);
            System.out.println();
            showAllRemoved();
            System.out.println();
            System.out.println(line);
            compareHiddenAndLastCard();
            System.out.println();
            System.out.println(line);
            System.out.println();
            System.out.print("Do you want to play again? Y/N :");
            char choice = scanner.next().toLowerCase().charAt(0);
            do {
                if (choice == 'n') {
                    System.out.println("\nSuccessfully exited the program. \nPlease come again!");
                    break outer;
                } else if (choice == 'y') {
                    System.out.println();
                    userHand.clear();
                    cpuHand.clear();
                    cards.getDiscarded().clear();
                    deckList.clear();
                    break;
                } else if (choice != '?' && choice != ' ') {
                    System.out.print("Is it a no or a yes? Please make up your mind:");
                    choice = scanner.next().toLowerCase().charAt(0);
                } else {
                    System.out.print("Is it a no or a yes? Please make up your mind:");
                    choice = scanner.next().toLowerCase().charAt(0);
                }
            } while (true);
        }
        System.exit(0);
    }

    private static void displayDeck() {
        wait(400);
        var suits = cards.getDECK();
        System.out.println("Matching^2: A card matching game | By: Group 6");
        System.out.println();

//        Display suit names
        System.out.println("Suits: " + Arrays.toString(suits[4]).replace("[", "").replace("]", " "));
        System.out.println();
        System.out.println("Deck:");
        System.out.println(
                Arrays.toString(suits[0]).replace("[", "").replace("]", " | ") + suits[4][0] + "\n" +
                        Arrays.toString(suits[1]).replace("[", "").replace("]", " | ") + suits[4][1] + "\n" +
                        Arrays.toString(suits[2]).replace("[", "").replace("]", " | ") + suits[4][2] + "\n" +
                        Arrays.toString(suits[3]).replace("[", "").replace("]", " | ") + suits[4][3]
        );
        System.out.println();
        System.out.println("Shuffled deck:");
        Collections.shuffle(deckList);
        showInRows(deckList, 26);
        System.out.println("Total cards in deck: " + deckList.size());
        System.out.println();
        System.out.println("Hidden Card: " + cards.getHiddenCard());
        System.out.println();
        System.out.println("Initial distribution of cards: ");
        System.out.println("User hand: \n" + userHand.getHand().toString().replace("[", "").replace("]", ""));
        System.out.println("No. of cards: " + userHand.getHand().size());
        System.out.println();
        System.out.println("CPU hand: \n" + cpuHand.getHand().toString().replace("[", "").replace("]", ""));
        System.out.println("No. of cards: " + cpuHand.getHand().size());
        System.out.println();
        System.out.println("Cancelling User hand pairs...");
        userHand.addAll((ArrayList<String>) cards.cancelPairs(userHand.getHand()));
        System.out.println();
        System.out.println("Cancelling CPU hand pairs...");
        cpuHand.addAll((ArrayList<String>) cards.cancelPairs(cpuHand.getHand()));
        System.out.println();
        System.out.println("Cancelled cards: ");
        showInRows(cards.getDiscarded(), 26);
        System.out.println("No. cards: " + cards.getDiscarded().size());
        System.out.println();
        System.out.println(line);
        System.out.println();
    }

    private static boolean isFirstToPick() {
        var coinToss = new CoinFlip();
        //Game intro
        System.out.println("「 GAME START! 」");
        wait(200);

        System.out.println("We will determine the first to pick via coin toss. ");
        System.out.print("Enter your bet, HEAD [0] or TAILS [1]: ");
        coinToss.setUserBet(scanner.next());
        coinToss.setResult();

        //Show the winner of the toss coin
        System.out.println("\nYou bet on " + coinToss.getUserBet());
        anim.coinTossAnim();
        System.out.println("Result of coin toss " + coinToss.getResult());
        System.out.println();
        wait(500);


        if ((coinToss.getResult().equals(coinToss.getUserBet()))) {
            System.out.println("「 You 」 get to pick first!");
            System.out.println();
            return true;
        } else {
            System.out.println("「 CPU 」 get to pick first!");
            System.out.println();
            return false;
        }
    }


    private static void whoGoesFirst(boolean userFirst) {
        if (userFirst) {
            do {
                if (userHand.getHand().isEmpty() || cpuHand.getHand().isEmpty()) {
                    isThereAWinnerYet();
                    break;
                }
                userTurn();
                pressEnterKeyToContinue();
                System.out.println();
                if (userHand.getHand().isEmpty() || cpuHand.getHand().isEmpty()) {
                    isThereAWinnerYet();
                    break;
                }
                cpuTurn();
                pressEnterKeyToContinue();
                System.out.println();
            } while (true);
        } else {
            do {
                if (cpuHand.getHand().isEmpty() || userHand.getHand().isEmpty()) {
                    isThereAWinnerYet();
                    break;
                }
                cpuTurn();
                pressEnterKeyToContinue();
                System.out.println();
                if (cpuHand.getHand().isEmpty() || userHand.getHand().isEmpty()) {
                    isThereAWinnerYet();
                    break;
                }
                userTurn();
                pressEnterKeyToContinue();
                System.out.println();
            } while (true);
        }
    }

    private static void userTurn() {
        var cancelled = cards.getTempBin();
        wait(500);
        System.out.println("It's your turn.");
        wait(500);
        System.out.println();
        Collections.shuffle(cpuHand.getHand());
        System.out.println("CPU hand:");
        showChoices(cpuHand.getHand());
        System.out.println("No. of cards: " + cpuHand.getHand().size());
        wait(500);
        System.out.print("\nYour hand: ");
        System.out.println(Arrays.toString(userHand.getHand().toArray()).replace("[", "").replace("]", ""));
        System.out.println("No. of cards : " + userHand.getHand().size());
        System.out.println();
        wait(500);
        System.out.println("Please pick a card from CPU's hand. ");
        System.out.printf("Enter the index of the card (0 - %s): ", cpuHand.getHand().size() - 1);
        int pick = scanner.nextInt();
        System.out.println();
        userHand.addCard(cpuHand.getHand().get(pick));
        wait(500);
        System.out.println("Drawn card: " + cpuHand.getHand().get(pick));
        cpuHand.removeCard(pick);
        System.out.println();
        wait(500);
        userHand.addAll((ArrayList<String>) cards.cancelPairs(userHand.getHand()));
        System.out.println();
        System.out.println("Cancelled pairs: ");
        System.out.println(((cancelled.size() == 0) ? "None" :
                Arrays.toString(cancelled.toArray()).replace("[", "").replace("]", "")));
        System.out.println();
        System.out.println("Cards left:");
        System.out.print((userHand.getHand().size() == 0) ? "None" : Arrays.toString(userHand.getHand().toArray()).replace("[", "").replace("]", ""));
        System.out.println();
    }

    private static void cpuTurn() {
        var cancelled = cards.getTempBin();
        Collections.shuffle(userHand.getHand());
        int random = random(userHand.getHand().size() - 1, 0);
        System.out.print("CPU's turn.\r");
        System.out.println();
        System.out.println("Cpu is picking...");
        anim.cpuPickAnim();
        cpuHand.addCard(userHand.getHand().get(random));
        System.out.println();
        wait(1000);
        System.out.println("Drawn card: " + userHand.getHand().get(random));
        System.out.println();
        System.out.print("CPU current hand: ");
        System.out.println(Arrays.toString(cpuHand.getHand().toArray()).replace("[", "").replace("]", ""));
        userHand.removeCard(random);
        System.out.println();
        cpuHand.addAll((ArrayList<String>) cards.cancelPairs(cpuHand.getHand()));
        System.out.println();
        System.out.println("Cancelled pairs: ");
        System.out.println(((cancelled.size() == 0) ? "None" :
                Arrays.toString(cancelled.toArray()).replace("[", "").replace("]", "")));
        System.out.println();
        System.out.println("CPU cards left:");
        System.out.println((cpuHand.getHand().size() == 0) ? "None" : Arrays.toString(cpuHand.getHand().toArray()).replace("[", "").replace("]", ""));
        System.out.println();
    }

    private static void isThereAWinnerYet() {
        if (userHand.getHand().isEmpty()) {
            anim.congratsAnim();
            System.out.println();
            System.out.println("We have a winner!!!");
            System.out.println("You won the game!");
            cards.setLastCard(cpuHand.getHand().get(0));
        }
        if (cpuHand.getHand().isEmpty()) {
            anim.congratsAnim();
            System.out.print("              BUT NOT TO YOU                ");
            System.out.println();
            System.out.println("We have a winner!!!");
            System.out.println("CPU won the game!");
            cards.setLastCard(userHand.getHand().get(0));
        }
    }

    private static void showAllRemoved() {
        System.out.println("All paired cards: ");
        showInRows(cards.getDiscarded(), 20);
        System.out.println("Card count: " + cards.getDiscarded().size());
    }

    public static void compareHiddenAndLastCard() {
        System.out.println("The remaining card: " + cards.getLastCard());
        System.out.println("The hidden card: " + cards.getHiddenCard());
        if (cards.getHiddenCard().charAt(0) == (cards.getLastCard().charAt(0))) {
            System.out.println("The remaining card and the hidden card is a pair.");
        } else {
            System.out.println("They are not a pair and this program failed.");
        }
    }

    public static void showChoices(ArrayList<String> cardList) {
        int num = 0;
        for (String p : cardList) {
            if (p.contains("10")) {
                if (num > 9) {
                    System.out.printf("""
                               ┌─────┐
                            %S | %S |
                               └─────┘
                            """, num, p);
                } else {
                    System.out.printf("""
                               ┌─────┐
                             %S | %S |
                               └─────┘
                            """, num, p);
                }
            } else {
                if (num > 9) {
                    System.out.printf("""
                               ┌────┐
                            %S | %S |
                               └────┘
                            """, num, p);
                } else {
                    System.out.printf("""
                               ┌────┐
                             %S | %S |
                               └────┘
                            """, num, p);
                }
            }
            num++;
        }
//        System.out.println();
    }

    private static void showInRows(ArrayList<String> list, int rows) {
        int index = 0;
        int listSize = 0;
        for (String p : list) {
            System.out.printf((index == rows - 1 || listSize == list.size() - 1 ? "%S " : "%S, "), p);
            index++;
            listSize++;
            if (index == rows) {
                System.out.println();
                index = 0;
            }
        }
        System.out.println();
    }

    public static int random(int max, int min) { //This method returns a random number, based on the parameters min and max.
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void pressEnterKeyToContinue() {
        System.out.println();
        anim.pressAnyKeyAnim();
        scanner.nextLine();
        System.out.println(line);
    }

}