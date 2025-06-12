
import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double cash = 10000.0;

    public void buyStock(String symbol, int quantity, double price) {
        double totalCost = quantity * price;
        if (totalCost > cash) {
            System.out.println("Insufficient balance.");
            return;
        }
        cash -= totalCost;
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        System.out.println("Bought " + quantity + " shares of " + symbol);
    }

    public void sellStock(String symbol, int quantity, double price) {
        if (!holdings.containsKey(symbol) || holdings.get(symbol) < quantity) {
            System.out.println("Not enough shares to sell.");
            return;
        }
        double totalValue = quantity * price;
        cash += totalValue;
        holdings.put(symbol, holdings.get(symbol) - quantity);
        System.out.println("Sold " + quantity + " shares of " + symbol);
    }

    public void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n=== Portfolio ===");
        System.out.println("Cash: $" + cash);
        double totalValue = cash;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double price = market.get(symbol).price;
            double value = qty * price;
            totalValue += value;
            System.out.println(symbol + ": " + qty + " shares, Current Value: $" + value);
        }
        System.out.println("Total Portfolio Value: $" + totalValue);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample market data
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 180.50));
        market.put("GOOGL", new Stock("GOOGL", 2900.75));
        market.put("AMZN", new Stock("AMZN", 3450.30));
        market.put("TSLA", new Stock("TSLA", 730.10));

        Portfolio portfolio = new Portfolio();

        int choice;
        do {
            System.out.println("\n=== Stock Trading Menu ===");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock stock : market.values()) {
                        System.out.println(stock.symbol + " : $" + stock.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter quantity to buy: ");
                        int buyQty = scanner.nextInt();
                        portfolio.buyStock(buySymbol, buyQty, market.get(buySymbol).price);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity to sell: ");
                        int sellQty = scanner.nextInt();
                        portfolio.sellStock(sellSymbol, sellQty, market.get(sellSymbol).price);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;

                case 4:
                    portfolio.displayPortfolio(market);
                    break;

                case 0:
                    System.out.println("Exiting platform. Thank you!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 0);

        scanner.close();
    }
}