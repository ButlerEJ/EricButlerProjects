/** ******************************************************
 * Revision History
 ********************************************************
 * 8/19/2021 - Eric Butler - Coded to read in a file and 
 * create a TreeMap for the herd. Unfinished methods
 * remain.
 * 2014 - AA - skeleton written to prevent errors in the
 *             driver.
 ******************************************************* */
package bullsandcows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Database of cows. Please complete the documentation
 * 
 * @author Eric Butler, and Anne Applin
 */
public class Database {

    // Declare a Map here do not create it.
    TreeMap<String, Animal> herd;

    /**
     * Reads the input file and creates either a Bull or Cow object and adds it
     * to the Database NOTE: During development, we print out the full message
     * of the exception by using e.toString() and e.printStackTrace(). That is
     * not recommended for production programs, since it gives hackers too much
     * insight into the innards of the software, possibly allowing them to
     * exploit it.Instead, production programs write error messages to internal
     * log files that hackers wouldn't see.
     *
     * @param fileName a String for the file that we want to use.
     */
    public Database(String fileName) {
        
        herd = new TreeMap<>();
        try {
            Scanner inputFile = new Scanner(new File(fileName));
            Cow cow;
            Bull bull;

            
            while (inputFile.hasNextLine()) {

                String data = inputFile.nextLine();
                String[] animalData = data.split(" ");
                String regNum = animalData[0];
                String name = animalData[1];
                data = inputFile.nextLine();
                animalData = data.split(" ");
                String g = animalData[0];
                char gender = g.charAt(0);
                int dobMonth = Integer.parseInt(animalData[1]);
                int dobDay = Integer.parseInt(animalData[2]);
                int dobYear = Integer.parseInt(animalData[3]);
                data = inputFile.nextLine();
                animalData = data.split(" ");
                if (gender == 'f') {
                    ArrayList<LactationRecord> cowRec = null;
                    LactationRecord cowRecord = null;
                    CowClassificationScore cowScore = null;
                    if (data.equals("-1")) {
                        cowScore = null;
                    } else {
                        int month = Integer.parseInt(animalData[0]);
                        int day = Integer.parseInt(animalData[1]);
                        int year = Integer.parseInt(animalData[2]);
                        int gnrlAppearance = Integer.parseInt(animalData[3]);
                        int dryCharacter = Integer.parseInt(animalData[4]);
                        int bdyCapacity = Integer.parseInt(animalData[5]);
                        int mmrySystem = Integer.parseInt(animalData[6]);
                        cowScore = new CowClassificationScore(month, day, year,
                        gnrlAppearance, dryCharacter, bdyCapacity, mmrySystem);
                    }
                    data = inputFile.nextLine();
                    animalData = data.split(" ");

                    if (data.equals("-1")) {
                        cowRecord = null;
                    } else {
                        cowRec = new ArrayList<>();
                        while (!data.equals("-1")) {
                            int ageYear = Integer.parseInt(animalData[0]);
                            int ageMonth = Integer.parseInt(animalData[1]);
                            int daysOfMilk = Integer.parseInt(animalData[2]);
                            int lbsOfMilk = Integer.parseInt(animalData[3]);
                            double percentButterFat
                                    = Double.parseDouble(animalData[4]);
                            int totalButterFat
                                   = (int)((lbsOfMilk * percentButterFat)/100);
                            cowRecord = new LactationRecord(ageYear, ageMonth,
                                    daysOfMilk, lbsOfMilk, percentButterFat);
                            cowRec.add(cowRecord);
                            data = inputFile.nextLine();
                            animalData = data.split(" ");
                        }                       
                    }
                    data = inputFile.nextLine();
                    animalData = data.split(" ");
                    String sireRegNum = animalData[0];
                    String damRegNum = animalData[1];

                    cow = new Cow(regNum, sireRegNum, damRegNum, gender,
                            dobMonth, dobDay, dobYear, name,
                            cowScore, cowRec);
                    herd.put(regNum, cow);
                } else {
                    Proving bullProve = null;
                    BullClassificationScore bullScore = null;
                    if (data.equals("-1")) {
                        bullScore = null;
                    } else {                     
                        int month = Integer.parseInt(animalData[0]);
                        int day = Integer.parseInt(animalData[1]);
                        int year = Integer.parseInt(animalData[2]);
                        int gnrlAppearance = Integer.parseInt(animalData[3]);
                        int dryCharacter = Integer.parseInt(animalData[4]);
                        int bdyCapacity = Integer.parseInt(animalData[5]);
                        bullScore = new BullClassificationScore(month, day,
                        year, gnrlAppearance, dryCharacter, bdyCapacity);
                    }
                    data = inputFile.nextLine();
                    animalData = data.split(" ");

                    if (data.equals("-1")) {
                        bullProve = null;
                    } else {                     
                        int month = Integer.parseInt(animalData[0]);
                        int day = Integer.parseInt(animalData[1]);
                        int year = Integer.parseInt(animalData[2]);
                        int numDaughters = Integer.parseInt(animalData[3]);
                        int numRecords = Integer.parseInt(animalData[4]);
                        int lbsOfMilk = Integer.parseInt(animalData[5]);
                        double percentButterFat
                                = Double.parseDouble(animalData[6]);
                        double genetics = Double.parseDouble(animalData[7]);
                        bullProve = new Proving(month, day, year, numDaughters,
                        numRecords, lbsOfMilk, percentButterFat, genetics);
                    }
                    data = inputFile.nextLine();
                    animalData = data.split(" ");
                    String sireRegNum = animalData[0];
                    String damRegNum = animalData[1];

                    bull = new Bull(regNum, sireRegNum, damRegNum, gender,
                            dobMonth, dobDay, dobYear, name, bullScore,
                            bullProve);
                    herd.put(regNum, bull);
                }
            }
            // comment the next line out when you start on part 5!
            System.out.println(printHerd());
            inputFile.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            ex.printStackTrace();
            System.exit(1);
        } catch (InputMismatchException ex) {
            System.err.println("tried to read the wrong data type.");
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

   

    /**
     * This method is for debugging the specifications for a B. You should
     * iterate through your map to generate the output String Look at
     * BookStoreMap for an example of iterating over a map.
     *
     * @return a string that is the output of the entire herd if the herd is
     * less that 14 (use with cattle10) for testing.
     */
    public String printHerd() {
        StringBuilder str = new StringBuilder();
        herd.forEach((regNum, Animal) -> str.append(Animal.toString() + "\n"));
        
        // print only 10 elements from the map        
        return str.toString();
    }

    /**
     * Implement this plus countOffspring() and evaluateOffSpring() for an A
     * Method to create a string that represents 2 generations of an animal's
     * ancestry. If the Animal exists in the database, it is printed. If its dam
     * is present it is printed as are the dam's parents if they exist. If its
     * sire is present it is printed as are the sire's parents if they exist.
     *
     * @param regNum the registration number of the animal to evaluate
     * @return a two generation pedigree of the input animal if it exists or a
     * string that says it does not exist.
     */
    public String printPedigree(String regNum) {
          if (herd.containsKey(regNum)) {
              System.out.println(herd.get(regNum));
          } else {
              System.out.println("This registration number doesn't exist.");
          }
        return "";
    }

    /**
     * Implement this plus printPedigree() and evaluateOffSpring() for an A if
     * you implement this, then change these comments and complete the JavaDoc
     * otherwise leave it ALL alone
     *
     * @param regNum the registration number of the animal to evaluate
     * @return
     */
    public String countOffspring(String regNum) {
        return "Not yet implemented.";
    }

    /**
     * Implement this plus printPedigree() and countOffspring() for an A if you
     * implement this, then change these comments and complete the JavaDoc
     * otherwise leave it ALL alone
     *
     * @param regNum the registration number of the animal to evaluate
     * @return
     */
    public String evaluateOffspring(String regNum) {
        return "Not yet implemented.";
    }
}
