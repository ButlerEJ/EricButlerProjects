/**
 **********************************************************************
 * REVISION HISTORY (newest first)
 * THIS CLASS IS FINISHED - DO NOT CHANGE  
 **********************************************************************
 *  the date and YOUR NAME  - implemented loadHerd()
 * 04/19/2017 - Anne Applin - added documentation
 * 2015 - David Briggs - original starting code
 *********************************************************************
 * Driver for the project. 
 * PLEASE NOTE:  Use Unit testing to test your classes and 
 * ignore the errors in this file until you have all classes 
 * working. 
 */
package bullsandcows;
import java.util.*;
import java.io.*;
/**
 * The driver for the Holstein-Fresian Pedigree application.
 * @author various
 */
public class BullsAndCows {
    // Properties of the class
    private static Database herdBook;
    /**
     * Displays a menu, asks for and reads a menu choice
     * This Method is complete - DO NOT CHANGE
     * @return an integer between 1 and 4 inclusive
     */
    public int getMenuChoice(){
        Scanner stdIn = new Scanner(System.in);
        int choice = 0;
        do{
            System.out.print("*************************\n"
                           + "* 1. Print Pedigree     *\n"
                           + "* 2. Count Offspring    *\n"
                           + "* 3. Evaluate Offspring *\n"
                           + "* 4. Quit.              *\n"
                           + "*************************\n"
                           + "  Enter Choice --> ");
            if (stdIn.hasNextInt())
                choice = stdIn.nextInt();
            else
                stdIn.next(); // read and throw away
        }while(choice < 1 || choice > 4);
        
        return choice;
    }
    /**
     *         Make no changes.
     */
    public void printPedigree(String regNum){
        System.out.println(herdBook.printPedigree(regNum));       
    }

    /**
     *         Make no changes.
     */
    public void countOffspring(String regNum){
        System.out.println(herdBook.countOffspring(regNum));
    }

    /**
     *             Make no changes.
     */
    public void evaluateOffspring(String regNum){
        System.out.println(herdBook.evaluateOffspring(regNum));        
    }
    /**
     * The actual driver for the application
     * This method is DONE - DO NOT CHANGE THIS
     * @param fileName comes from the command line arguments
     */
    public void run(String fileName){
        boolean finished = false;
        // create an instance of the Database
        herdBook = new Database(fileName);
        
        Scanner stdIn = new Scanner(System.in);
        String regNum;
        while (!finished) {
            int choice = getMenuChoice();
            switch(choice){
                case 1: 
                    System.out.print("Enter the registration number for "
                           + "the animal\nthat you want the pedigree for: ");
                    regNum = stdIn.next();
                    printPedigree(regNum);     
                    break;
                case 2: 
                    System.out.print("Enter the registration number for "
                           + "the animal\nthat you want the count for: ");
                    regNum = stdIn.next();
                    countOffspring(regNum);    
                break;
                case 3: 
                    System.out.print("Enter the registration number for "
                           + "the animal\nthat you want the evaluation for: ");
                    regNum = stdIn.next();
                    evaluateOffspring(regNum); 
                break;
                case 4: finished = true;
            }                
        }//end while  
    } // end run
    /**
     * This method is DONE DO NOT CHANGE THIS
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BullsAndCows driver = new BullsAndCows();
        herdBook = new Database("cattle10.txt");
      
        driver.printPedigree("0000000017"); // C all there
        driver.printPedigree("0000000685"); // C all there
        driver.printPedigree("0000000748"); // C all there
        driver.printPedigree("0000000138"); // B missing pgd 
        driver.printPedigree("0000000427"); // C all there
        driver.printPedigree("0000000047"); // C all there
        driver.printPedigree("0000000974"); // B missing mgd, mgs, sire
        driver.printPedigree("0000000984"); // B missing dam & sire
        driver.printPedigree("0000001011"); // C missing mgd, mgs, sire
        driver.printPedigree("0000000437"); // all there
        driver.printPedigree("0000000291"); // C missing mgs, pgd
        driver.printPedigree("0000000847"); // C missing mgd, mgs
       
        System.out.println("\n\nCount Offspring\n");
        System.out.println("Expected: \n0000001017 f not present 5 males 4 females");
        driver.countOffspring("0000001017");
        System.out.println("Expected: \n0000000986 m not present 8 males 7 females");
        driver.countOffspring("0000000986");
        System.out.println("Expected:  \n0000001019 f not present 1 males 0 females");
        driver.countOffspring("0000001019");
        System.out.println("Expected: \n0000000994 m not present 0 males 4 females");
        driver.countOffspring("0000000994");
        System.out.println("Expected: \n0000000000 m present 0 males 0 females");
        driver.countOffspring("0000000000");
        System.out.println("Expected: \n0000000001 f present 0 males 0 females");
        driver.countOffspring("0000000001");
        System.out.println("Expected: \n0000000162 m present 0 males 1 females");
        driver.countOffspring("0000000162");
        System.out.println("Expected: \n0000000090 m present 1 males 1 females");
        driver.countOffspring("0000000090");
        System.out.println("Expected: \n9999999999 ? not present 0 males 0 females");
        driver.countOffspring("9999999999");

        System.out.println("\n\nEvaluate Offspring\n");
        System.out.println("Expected: \n0000001017 f not present females with data 3");
        driver.evaluateOffspring("0000001017");
        System.out.println("Expected: \n0000000986 m not present females with data 4");
        driver.evaluateOffspring("0000000986");
        System.out.println("Expected: \n0000000162 m present females with data 1");
        driver.evaluateOffspring("0000000162");
        System.out.println("Expected: \n0000000090 m present females with data 0");
        driver.evaluateOffspring("0000000090");
        System.out.println("Expected: \n0000001019 f not present females with data 0");
        driver.evaluateOffspring("0000001019");
        System.out.println("Expected: \n0000000000 m present females with data 0");
        driver.evaluateOffspring("0000000000");
        System.out.println("Expected: \n0000000139 f present females with data 1");
        driver.evaluateOffspring("0000000139");
        System.out.println("Expected: \n0000000487 f present females with data 0");
        driver.evaluateOffspring("0000000487");
        System.out.println("Expected: \n9999999999 ? not present females with data 0");
        driver.evaluateOffspring("9999999999");
    }
}
