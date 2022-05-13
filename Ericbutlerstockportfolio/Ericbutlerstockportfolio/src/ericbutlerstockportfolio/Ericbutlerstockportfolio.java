/** **************************************************************
 * Revision History
 ****************************************************************
 * 7/17/2021 - implemented first version - Eric Butler
 ************************************************************** */
package ericbutlerstockportfolio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project - Stock Portfolio: 7/17/2021
 *
 * @author Eric Butler
 */
/**
 * The class below initializes a new Portfolio object that will be used to add
 * stocks into.
 *
 */
public class Ericbutlerstockportfolio {

    Portfolio portfolio = new Portfolio();

    /**
     * The method below reads in data from a file containing stock information
     * The name of the company is store as well as the stock quotes. Each quote
     * is parsed into it's own index and the stock information is used to create
     * a new stock object and that is added to the Portfolio. Various catches
     * are in place to handle different errors.
     *
     * @param fileName
     */
    public void readFile(String fileName) {

        String[] tokens = null;
        try {
            Scanner inFile = new Scanner(new FileReader(fileName));
            if (!inFile.hasNext()) {
                System.err.println("No data in file.");
                System.exit(1);
            }
            while (inFile.hasNext()) {
                String name = inFile.nextLine();
                String line = inFile.nextLine();
                tokens = line.split("[ \\s]");
                double[] quotes = new double[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    quotes[i] = Double.parseDouble(tokens[i]);
                }
                portfolio.addStock(new Stock(name, quotes));
            }
            inFile.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            System.exit(1);
        } catch (InputMismatchException ex) {
            System.err.println("tried to read the wrong data type.");
            System.exit(1);
        }
    }

    /**
     * The run method below starts the readFile() method and then prints out the
     * data from the Portfolio class.
     *
     * @param fileName
     */
    public void run(String fileName) {
        readFile(fileName);
        System.out.println(portfolio);
    }

    /**
     * The main method is insuring that we are indeed reading in data from a
     * file supplied to the run arguments. If the supplied error is triggered
     * then we know there is something wrong with the file upload.
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("usage: prog infile, shipment, outfile");
            System.exit(1);
        }
        Ericbutlerstockportfolio driver = new Ericbutlerstockportfolio();
        driver.run(args[0]);
    }
}
