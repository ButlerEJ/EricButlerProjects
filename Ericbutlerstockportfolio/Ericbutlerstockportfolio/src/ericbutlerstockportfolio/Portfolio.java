/** **************************************************************
 * Revision History
 ****************************************************************
 * 7/17/2021 - implemented first version - Eric Butler
 ************************************************************** */
package ericbutlerstockportfolio;

import java.util.ArrayList;

/**
 * Project - Stock Portfolio: 7/17/2021
 *
 * @author Eric Butler
 */
/**
 * The constructor below creates a new ArrayList of Stock object type. This is
 * where the stock objects will be stored in our portfolio.
 *
 */
public class Portfolio {

    private ArrayList<Stock> stonks = new ArrayList<>();

    /**
     * Initializing constructor that is initializing to default.
     */
    public Portfolio() {

    }

    /**
     * This method adds "this" instance of a stock to the ArrayList of Stock
     * objects.
     *
     * @param stock1
     */
    public void addStock(Stock stock1) {
        this.stonks.add(stock1);
    }

    /**
     * This method returns an instance of Stock object at the given index.
     *
     * @param i
     * @return the instance of stock in the Stock object ArrayList.
     */
    public Stock getStockAt(int i) {
        return stonks.get(i);
    }

    /**
     * This toString method takes in all of the created stocks that have
     * undergone the calculations executed in the Stock class. It supplies a
     * header with categorized sections that pertain to the different values of
     * our calculations.
     *
     * @return the completed toString for the Portfolio class.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Company                    Low       Hi      Net  "
                + "    Ave      Dev      Lng  BestTrRt BestPerRt BestPer\n");
        for (Stock stock : stonks) {
            str.append(stock.toString()).append("\n");
        }
        return str.toString();
    }

    /**
     * This main method doesn't serve purpose to our program since the main
     * method in our driver class is the only one the compiler will read.
     * Therefore, we are using this main method as a place to conduct an unit
     * test. Use these outputs as reference for accuracy as far as what our
     * program outputs.
     *
     * @param args
     */
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        double[] quotes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        String name = "A";
        portfolio.addStock(new Stock(name, quotes));
        name = "B";
        double[] quotes2 = {100, 90, 80, 70, 60, 50, 40, 30, 20, 10};
        portfolio.addStock(new Stock(name, quotes2));
        System.out.println(portfolio);
        System.out.println("Expected Output without EC");
        System.out.println("Company                Low    Hi    Net    Ave   "
                + " Dev  Lng  BestTrRt");
        System.out.println("A                     10.0 100.0   90.0   55.0 "
                + " 28.72    9     10.00");
        System.out.println("B                     10.0 100.0  -90.0   55.0 "
                + " 28.72    0       n/a");
    }

}
