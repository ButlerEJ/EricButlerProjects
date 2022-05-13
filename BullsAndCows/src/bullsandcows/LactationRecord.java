/** ********************************************************************
 * REVISION HISTORY (newest first)
 **********************************************************************
 * 8/13/2021 - Eric Butler - A class to create Lactation Record
 * objects for Cows.
 ******************************************************************** */
package bullsandcows;

import java.util.ArrayList;

/**
 * This constructor declares the data members for LactationRecord class.
 *
 * @author Eric Butler
 */
public class LactationRecord {

    private final int ageYear;
    private final int ageMonth;
    private final int daysOfMilk;
    private final int lbsOfMilk;
    private final double percentButterFat;
    private final int totalButterFat;

    /**
     * This initializing constructor sets the values for the data members. It
     * also makes the calculation for totalButterFat.
     *
     * @param ageYear
     * @param ageMonth
     * @param daysOfMilk
     * @param lbsOfMilk
     * @param percentButterFat
     */
    public LactationRecord(int ageYear, int ageMonth, int daysOfMilk,
            int lbsOfMilk, double percentButterFat) {
        this.ageYear = ageYear;
        this.ageMonth = ageMonth;
        this.daysOfMilk = daysOfMilk;
        this.lbsOfMilk = lbsOfMilk;
        this.percentButterFat = percentButterFat;
        totalButterFat = (int) ((lbsOfMilk * percentButterFat) / 100);
    }

    /**
     * This toString method formats the display of a Cow's lactation record.
     *
     * @return str.toString()
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("%02d-%02d %4d %6d %4.1f %5d", ageYear,
                ageMonth, daysOfMilk, lbsOfMilk, percentButterFat,
                totalButterFat));

        return str.toString();

    }

    /**
     * Unit test for LactationRecord.
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<LactationRecord> lacRecs = new ArrayList<>();
        lacRecs.add(new LactationRecord(2, 10, 228, 17232, 5.3));
        lacRecs.add(new LactationRecord(3, 2, 178, 3290, 4.0));
        lacRecs.add(new LactationRecord(4, 5, 260, 266, 3.2));
        System.out.println("Milk Records");
        for (LactationRecord record : lacRecs) {
            System.out.println(record);
        }
        System.out.println("Expected Output:\nMilk Records\n"
                + "02-10  228  17232  5.3   913\n"
                + "02-10  228  17232  5.3   913\n"
                + "03-02  178   3290  4.0   131\n"
                + "04-05  260    266  3.2     8 \n");

    } // end Unit Test

}
