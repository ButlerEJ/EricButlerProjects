/** ********************************************************************
 * REVISION HISTORY (newest first)
 **********************************************************************
 * 08/15/2021 - Eric Butler - A class to create Cow objects that
 * inherit from the Animal Class.
 ******************************************************************** */
package bullsandcows;

import java.util.ArrayList;

/**
 * This class constructor inherits from Animal class. It also declares the
 * variables for Cow objects and takes in the parameters from it's super class.
 * An array of LactationRecords is created for Cow.
 *
 * @author Eric Butler
 */
public class Cow extends Animal {

    ArrayList<LactationRecord> lacRecs = new ArrayList<>();

    public Cow(String regNum, String sireRegNum, String damRegNum, char gender,
            int dobMonth, int dobDay, int dobYear, String name,
            ClassificationScore classification,
            ArrayList<LactationRecord> lacRecs) {
        super(regNum, sireRegNum, damRegNum, gender, dobMonth, dobDay, dobYear,
                name, classification);

        this.lacRecs = lacRecs;

    }

    /**
     * This toString method calls upon it's super-class' toString and in
     * addition iterates through the lactation record arraylist and appends it's
     * Cow object's milk records if it has one. If there is no milk record we
     * display the appropriate message.
     *
     * @return str.toString()
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());

        if (lacRecs == null || lacRecs.isEmpty()) {
            str.append("\nNo Milk Records");
        } else {
            str.append("\nMilk Records");
            for (LactationRecord record : lacRecs) {
                str.append("\n" + record.toString());
            }
        }
        return str.toString();
    }
}
