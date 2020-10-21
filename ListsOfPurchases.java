package budget;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ListsOfPurchases implements Serializable {
    private static final Scanner sc = new Scanner(System.in);
    private final Map<String, Double> foodList;
    private final Map<String, Double> clothesList;
    private final Map<String, Double> entertainmentList;
    private final Map<String, Double> otherList;
    private final Map<String, Double> allPurchasesList;
    private final Map<String, Double> prices;

    ListsOfPurchases() {
        this.foodList = new HashMap<>();
        this.clothesList = new HashMap<>();
        this.entertainmentList = new HashMap<>();
        this.otherList = new HashMap<>();
        this.allPurchasesList = new LinkedHashMap<>();
        this.prices = new HashMap<>();
        prices.put("total", 0.0);
        prices.put("1", 0.0);
        prices.put("2", 0.0);
        prices.put("3", 0.0);
        prices.put("4", 0.0);
        prices.put("balance", 0.0);
    }

    protected void addToList(String type, String name, Double currentPrice) {
        switch (type) {
            case "1":
                foodList.put(name, currentPrice);
                break;
            case "2":
                clothesList.put(name, currentPrice);
                break;
            case "3":
                entertainmentList.put(name, currentPrice);
                break;
            case "4":
                otherList.put(name, currentPrice);
                break;
        }

        allPurchasesList.put(name, currentPrice);
        prices.put(type, prices.get(type) + currentPrice);
        prices.put("total", prices.get("total") + currentPrice);
    }

    protected void getList(String type) {
        switch (type) {
            case "1":
                System.out.println("\nFood:");
                showList(foodList, prices.get(type));
                break;
            case "2":
                System.out.println("\nClothes:");
                showList(clothesList, prices.get(type));
                break;
            case "3":
                System.out.println("\nEntertainment:");
                showList(entertainmentList, prices.get(type));
                break;
            case "4":
                System.out.println("\nOther:");
                showList(otherList, prices.get(type));
                break;
            default:
                System.out.println("\nAll:");
                showList(allPurchasesList, prices.get("total"));
                break;
        }
    }

    private void showList(Map<String, Double> list, Double sum) {
        if (list.isEmpty()) {
            System.out.println("Purchase list is empty!");
        } else {
            list.forEach((k, v) -> System.out.printf("%s $%.2f\n", k, v));
            System.out.printf("Total sum: $%.2f\n\n", sum);
        }
    }

    protected void sortPurchases(String sortType) {
        switch (sortType) {
            case "1":
                if (allPurchasesList.isEmpty()) {
                    System.out.println("\nPurchase list is empty!");
                } else {
                    System.out.println("\nAll:");
                    sortList(allPurchasesList, prices.get("total"), true);
                }
                break;
            case "2":
                System.out.println("\nTypes:");
                Map<String, Double> allLists = new HashMap<>();
                allLists.put("Food", prices.get("1"));
                allLists.put("Clothes", prices.get("2"));
                allLists.put("Entertainment", prices.get("3"));
                allLists.put("Other", prices.get("4"));
                double price = 0;
                for (Map.Entry<String, Double> entry : allLists.entrySet()) {
                    price += entry.getValue();
                }
                sortList(allLists, price, false);
                break;
            case "3":
                chooseTypeToSort();
                break;
        }
    }

    private void chooseTypeToSort() {
        System.out.println("\nChoose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");

        String type = sc.nextLine();

        switch (type) {
            case "1":
                if (foodList.isEmpty()) {
                    System.out.println("\nPurchase list is empty!");
                } else {
                    System.out.println("\nFood:");
                    sortList(foodList, prices.get(type), true);
                }
                break;
            case "2":
                if (clothesList.isEmpty()) {
                    System.out.println("\nPurchase list is empty!");
                } else {
                    System.out.println("\nClothes:");
                    sortList(clothesList, prices.get(type), true);
                }
                break;
            case "3":
                if (entertainmentList.isEmpty()) {
                    System.out.println("\nPurchase list is empty!");
                } else {
                    System.out.println("\nEntertainment:");
                    sortList(entertainmentList, prices.get(type), true);
                }
                break;
            case "4":
                if (otherList.isEmpty()) {
                    System.out.println("\nPurchase list is empty!");
                } else {
                    System.out.println("\nOther:");
                    sortList(otherList, prices.get(type), true);
                }
                break;
            default:
                break;
        }
    }

    private static void sortList(Map<String, Double> list, double price, boolean isAll) {
        Map<String, Double> sortedMap = list.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        sortedMap.forEach((k, v) -> System.out.printf("%s " + (isAll ? "$%.2f\n" : "- $%.2f\n"), k, v));
        System.out.printf("Total sum: $%.2f\n", price);
    }

    protected void addToBalance(double sum) {
        prices.put("balance", prices.get("balance") + sum < 0 ? 0 : prices.get("balance") + sum);
    }

    protected void getBalance() {
        System.out.printf("\nBalance: $%.2f\n\n", prices.get("balance"));
    }
}
