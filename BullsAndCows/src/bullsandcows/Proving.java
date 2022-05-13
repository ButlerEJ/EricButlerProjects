/** ********************************************************************
 * REVISION HISTORY (newest first)
 **********************************************************************
 * 8/13/2021 - Eric Butler - A class to create a Proving record
 * for Bulls.
 ******************************************************************** */
package bullsandcows;

/**
 * This initializing constructor declares data members for the Proving class.
 *
 *
 * @author Eric Butler
 */
public class Proving {

    private final int month;
    private final int day;
    private final int year;
    private final int numDaughters;
    private final int numRecords;
    private final int lbsOfMilk;
    private final double percentButterFat;
    private final double genetics;
    private final int totalButterFat;

    /**
     * This initializing constructor sets the values for the Proving data
     * members. It also makes the calculation for totalButterFat.
     *
     * @param month
     * @param day
     * @param year
     * @param numDaughters
     * @param numRecords
     * @param lbsOfMilk
     * @param percentButterFat
     * @param genetics
     */
    public Proving(int month, int day, int year, int numDaughters,
            int numRecords, int lbsOfMilk, double percentButterFat,
            double genetics) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.numDaughters = numDaughters;
        this.numRecords = numRecords;
        this.lbsOfMilk = lbsOfMilk;
        this.percentButterFat = percentButterFat;
        this.genetics = genetics;
        totalButterFat = (int) ((lbsOfMilk * percentButterFat) / 100);
    }

    /**
     * This toString formats the proving record for a Bull. Takes into account
     * whether the genetics value is positive or negative, for display purposes.
     *
     * @return str.toString()
     */
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(String.format("Date = %02d-%02d-%d\nDtrs = %d Records = %d\n"
                + "AveMilk = %d AveBf%% = %.2f AveBf = %d",
                month, day, year, numDaughters, numRecords, lbsOfMilk,
                percentButterFat, totalButterFat));
        if (genetics > 0) {
            str.append(String.format("\nExImp = +%.2f", genetics));
        } else {
            str.append(String.format("\nExImp = %.2f", genetics));
        }

        return str.toString();
    }

    /**
     * Unit test for Proving class.
     *
     * @param args
     */
    public static void main(String[] args) {
        Proving proof1 = new Proving(6, 6, 1920, 93, 180, 15894, 3.4, -476.8);
        Proving proof2 = new Proving(6, 6, 1920, 93, 180, 15894, 3.4, 476.8);
        System.out.println(proof1);
        System.out.println("\nExpected Output:\nDate = 06-06-1920\n"
                + "Dtrs = 93 Records = 180\n"
                + "AveMilk = 15894 AveBf% = 3.40 AveBf = 540\n"
                + "ExImp = -476.80");
        System.out.println();
        System.out.println(proof2);
        System.out.println("\nExpected Output:\nDate = 06-06-1920\n"
                + "Dtrs = 93 Records = 180\n"
                + "AveMilk = 15894 AveBf% = 3.40 AveBf = 540\n"
                + "ExImp = +476.80");
    }

}
