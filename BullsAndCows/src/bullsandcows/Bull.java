/** ********************************************************************
 * REVISION HISTORY (newest first)
 **********************************************************************
 * 08/15/21 - Eric Butler - A class to create Bull objects that inherit
 * for the Animal class.
 ******************************************************************** */
package bullsandcows;

/**
 * This initializing constructor inherits from Animal class. It also declares
 * data members for the Bull object. A new instance of Proving object is
 * declared.
 *
 * @author Eric Butler
 */
public class Bull extends Animal {

    private final Proving proof;

    public Bull(String regNum, String sireRegNum, String damRegNum, char gender,
            int dobMonth, int dobDay, int dobYear, String name,
            ClassificationScore classification, Proving proof) {

        super(regNum, sireRegNum, damRegNum, gender, dobMonth, dobDay, dobYear,
                name, classification);
        this.proof = proof;
    }

    /**
     * This toString method display the super-class' toString and also
     * determines if a Bull has a proving record or not. If it does, the record
     * is displayed, otherwise the appropriate message is displayed.
     *
     * @return str.toString()
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        if (proof == null) {
            str.append("\nProving: n/a");
        } else {
            str.append("\nProving: " + proof.toString());
        }
        return str.toString();

    }
}
