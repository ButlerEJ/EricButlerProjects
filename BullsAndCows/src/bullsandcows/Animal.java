/** ********************************************************************
 * REVISION HISTORY (newest first)
 **********************************************************************
 * 08/14/2021 - Eric Butler - A super class for Animal objects.
 * Passes down to Bull and Cow.
 ******************************************************************** */
package bullsandcows;

import java.util.ArrayList;

/**
 * This abstract constructor declares the variables for Animal objects.
 *
 * @author Eric Butler
 */
public abstract class Animal {

    protected String regNum;
    protected String sireRegNum;
    protected String damRegNum;
    protected char gender;
    protected int dobMonth;
    protected int dobDay;
    protected int dobYear;
    protected String name;
    protected ClassificationScore classification;

    /**
     * This initializing constructor sets the values for the data members of
     * Animal objects.
     *
     * @param regNum
     * @param sireRegNum
     * @param damRegNum
     * @param gender
     * @param dobMonth
     * @param dobDay
     * @param dobYear
     * @param name
     * @param classification
     */
    Animal(String regNum, String sireRegNum, String damRegNum,
            char gender, int dobMonth, int dobDay,
            int dobYear, String name, ClassificationScore classification) {
        this.regNum = regNum;
        this.sireRegNum = sireRegNum;
        this.damRegNum = damRegNum;
        this.gender = gender;
        this.dobMonth = dobMonth;
        this.dobDay = dobDay;
        this.dobYear = dobYear;
        this.name = name;
        this.classification = classification;
    }

    /**
     * This toString method formats the output for Animal super class object
     * data.
     *
     * @return str.toString()
     */
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(String.format("Registration number : %s\nName: %s\nBorn: "
                + "%02d-%02d-%d\n", regNum, name, dobMonth, dobDay, dobYear));
        if (classification == null) {
            str.append("Classification: n/a");
        } else {
            str.append("Classification: " + classification.toString());
        }

        return str.toString();

    }

    /**
     * Unit test for the Animal class.
     *
     * @param args
     */
    public static void main(String[] args) {
        ClassificationScore perfectCow
                = new CowClassificationScore(1, 2, 2010, 30, 20, 20, 30);
        ClassificationScore nullCow = null;
        ClassificationScore perfectBull
                = new BullClassificationScore(5, 6, 2012, 40, 30, 30);
        ClassificationScore nullBull = null;

        Proving noProving = null;
        Proving proving = new Proving(6, 4, 2016, 20, 200,
                1500, 3.25, 324);
        ArrayList<LactationRecord> empty = null;
        ArrayList<LactationRecord> lacRecs = new ArrayList<>();
        lacRecs.add(new LactationRecord(2, 10, 228, 17232, 5.3));
        lacRecs.add(new LactationRecord(3, 2, 178, 3290, 4.0));
        lacRecs.add(new LactationRecord(4, 3, 260, 266, 3.2));
        Animal bull1 = new Bull("0001", "0002", "0003", 'm', 4, 8, 2000,
                "aBull", perfectBull, noProving);
        Animal bull2 = new Bull("0001", "0002", "0003", 'm', 4, 8, 2000,
                "anotherBull", nullBull, proving);
        Animal cow1 = new Cow("0001", "0002", "0003", 'f', 4, 8, 2000,
                "aCow", perfectCow, empty);
        Animal cow2 = new Cow("0001", "0002", "0003", 'f', 4, 8, 2000,
                "anotherCow", nullCow, lacRecs);
        System.out.println(bull1);
        System.out.println("Expected Output:\nRegistration number : 0001\n"
                + "Name: aBull\n"
                + "Born: 04-08-2000\n"
                + "Classification: Date = 05-06-2012 GA = 40 DC = 30 BC = 30"
                + " Total = 100\n"
                + "Proving: n/a\n");
        System.out.println();
        System.out.println(bull2);
        System.out.println("Expected Output:\nRegistration number : 0001\n"
                + "Name: anotherBull\n"
                + "Born: 04-08-2000\n"
                + "Classification: n/a\n"
                + "Proving: Proving: Date = 06-04-2016\n"
                + "Dtrs = 20 Records = 200\n"
                + "AveMilk = 1500 AveBf% = 3.25 AveBf = 48\n"
                + "ExImp = +324.00\n");
        System.out.println();
        System.out.println(cow1);
        System.out.println("Expected Output:\nRegistration number : 0001\n"
                + "Name: aCow\n"
                + "Born: 04-08-2000\n"
                + "Classification: Date = 01-02-2010 GA = 30 DC = 20 BC = 20"
                + " MS = 30 Total = 100\n"
                + "No Milk Records\n");
        System.out.println();
        System.out.println(cow2);
        System.out.println("Expected Output:\nRegistration number : 0001\n"
                + "Name: anotherCow\n"
                + "Born: 04-08-2000\n"
                + "Classification: n/a\n"
                + "Milk Records \n"
                + "02-10  228  17232  5.3   913\n"
                + "03-02  178   3290  4.0   131\n"
                + "04-03  260    266  3.2     8 \n");
    }

}
