/** **************************************************************
 * Revision History
 ****************************************************************
 * 7/17/2021 - implemented first version - Eric Butler
 ************************************************************** */
package ericbutlerstockportfolio;

/**
 * Project - Stock Portfolio: 7/17/2021
 *
 * @author Eric Butler
 */
/**
 * The constructor below initializes the global variable for the Stock class.
 *
 */
public class Stock {

    private String companyName;
    private double[] prices;
    private double minimum;
    private double maximum;
    private double netChange;
    private double averagePrice;
    private double standardDeviation;
    private double bestUpwardTrendGrowthRate;
    private double longestUpwardTrend;
    private double thisTrend;

    /**
     * This constructor initializes some parameter variables as "this" and also
     * calls the methods that make various calculations on the stock quotes.
     *
     * @param companyName
     * @param prices
     */
    public Stock(String companyName, double[] prices) {
        this.companyName = companyName;
        this.prices = prices;
        calcMin();
        calcMax();
        calcNetChange();
        calcAvg();
        calcStdDev();
        calcLUT();
        calcBestGrowth();
    }

    /**
     * This method finds and assigns the minimum value of each stock's quotes.
     */
    private void calcMin() {
        minimum = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minimum) {
                minimum = prices[i];
            }
        }
    }

    /**
     * This method finds and assigns the maximum value of each stock's quotes.
     */
    private void calcMax() {
        maximum = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > maximum) {
                maximum = prices[i];
            }
        }
    }

    /**
     * this method finds and assigns the net change in each stock's quotes from
     * the beginning quote compared to the last quote.
     */
    private void calcNetChange() {
        double firstPrice = prices[0];
        double lastPrice = prices[prices.length - 1];
        netChange = lastPrice - firstPrice;
    }

    /**
     * This method finds and assigns the average value of each stock's total
     * quote amount.
     */
    private void calcAvg() {
        double sum = 0;

        for (int i = 0; i < prices.length; i++) {
            sum = sum + prices[i];
        }
        averagePrice = sum / prices.length;
    }

    /**
     * This method finds and assigns the standard deviation of each stock's
     * quote range.
     */
    private void calcStdDev() {
        double sum = 0;
        for (int i = 0; i < prices.length; i++) {
            sum += Math.pow((prices[i] - averagePrice), 2);
        }
        standardDeviation = Math.sqrt(sum / prices.length);
    }

    /**
     * This method finds and assigns the longest upward
     * trend in each Stock's quote ticker.
     */
    public void calcLUT() {
        thisTrend = 0;
        longestUpwardTrend = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] >= prices[i - 1]) {
                thisTrend++;
                if (thisTrend > longestUpwardTrend) {
                    longestUpwardTrend = thisTrend;
                }
            } else {
                thisTrend = 0;
            }
        }
    }

    /**
     * If this method was working it would find and assign the best rate of
     * growth for each stock's range of quotes.
     */
    private void calcBestGrowth() {
        double firstQuote = -1;
        double lastQuote = -1;
        double someGrowthRate = 0;
        double trendCount = 0;
        bestUpwardTrendGrowthRate = 0;
       
        for (int i = 1; i < prices.length - 1; i++) {
            if (prices[i] >= prices[i - 1]) {
                firstQuote = prices[i - 1];
                trendCount++;
            }
            if (prices[i] > prices[i + 1]) {
                lastQuote = prices[i];                
            }
            if (firstQuote > 0 && lastQuote > 0) { 
                someGrowthRate = lastQuote - firstQuote / trendCount ;
                trendCount = 0;
                firstQuote = -1;
                lastQuote = -1;                
                if (someGrowthRate > bestUpwardTrendGrowthRate) {
                    bestUpwardTrendGrowthRate = someGrowthRate;
                }
            }
        }
    }
        /**
         * This toString method adds in each company name, as well as the values
         * we calculated in our methods above. We also format all of the fields
         * so that they are vertically aligned with the header provided in the
         * Portfolio's toString.
         *
         * @return the completed toString for the Stock class.
         */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("%-22s", companyName));
        str.append(String.format("%8.2f %8.2f %8.2f %8.2f %8.2f %8.2f %9.2f",
                minimum, maximum, netChange, averagePrice,
                standardDeviation, longestUpwardTrend,
                bestUpwardTrendGrowthRate));
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
        double[] quotes = {114.5, 120.6, 130.2, 128.1, 126.7, 129.3};
        String name = "Apple Inc";
        Stock s = new Stock(name, quotes);
        System.out.println(s);
        System.out.println("Expected Output without EC");
        System.out.println("Apple Inc.           114.5 130.2   14.8  "
                + "124.9   5.59   2     7.85");
    }
}
