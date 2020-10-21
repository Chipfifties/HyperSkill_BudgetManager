package budget;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static ListsOfPurchases purchases = new ListsOfPurchases();

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        while (true) {
            System.out.println("Choose your action:");
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("7) Analyze (sort)");
            System.out.println("0) Exit");

            switch (sc.nextLine()) {
                case "1": addIncome(); break;
                case "2": showLists(false); break;
                case "3": showLists(true); break;
                case "4": purchases.getBalance(); break;
                case "5": saveToFile(); break;
                case "6": loadFromFile(); break;
                case "7": analyzePurchases(); break;
                case "0": System.out.println("\nBye!"); return;
            }
        }
    }

    private static void showLists(boolean showAll) {
        while (true) {
            int index = 0;
            System.out.println("\nChoose the type of purchase" + (showAll ? "s" : ""));
            System.out.println(++index + ") Food");
            System.out.println(++index + ") Clothes");
            System.out.println(++index + ") Entertainment");
            System.out.println(++index + ") Other" + (showAll ? String.format("\n%d) All", ++index) : ""));
            System.out.println(++index + ") Back");

            String type = sc.nextLine();

            if ((showAll && type.equals("6")) || (!showAll && type.equals("5"))) {
                System.out.println();
                break;
            } else if (showAll) {
                purchases.getList(type);
            } else {
                addPurchase(type);
            }
        }
    }

    private static void analyzePurchases() {
        while (true) {
            System.out.println("\nHow do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");

            String sortType = sc.nextLine();

            if (sortType.equals("4")) {
                System.out.println();
                break;
            } else {
                purchases.sortPurchases(sortType);
            }
        }
    }

    private static void addIncome() {
        System.out.println("\nEnter income:");
        purchases.addToBalance(Double.parseDouble(sc.nextLine()));
        System.out.println("Income was added!\n");
    }

    private static void addPurchase(String type) {
        System.out.println("\nEnter purchase name:");
        String name = sc.nextLine().trim();

        System.out.println("Enter its price:");
        double price = Double.parseDouble(sc.nextLine());

        purchases.addToList(type, name, price);
        purchases.addToBalance(-price);

        System.out.println("Purchase was added!");
    }

    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("purchases.txt"))) {
            oos.writeObject(purchases);
            System.out.println("\nPurchases were saved!\n");
        } catch (IOException e) {
            System.out.println("Could not save to file.");
        }
    }

    private static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("purchases.txt"))) {
            purchases = (ListsOfPurchases) ois.readObject();
            System.out.println("\nPurchases were loaded!\n");
        } catch (IOException e) {
            System.out.println("File not found.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
        }
    }
}
