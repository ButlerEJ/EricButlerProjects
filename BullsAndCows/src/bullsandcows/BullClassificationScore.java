/**********************************************************************
 * REVISION HISTORY (newest first)
 * THIS CLASS IS FINISHED - DO NOT CHANGE
 **********************************************************************
 * 04/19/2017 - Anne Applin - added documentation
 * 2015 - David Briggs - original starting code
 *********************************************************************/ 
package bullsandcows;

/**
 * Represents the classification score for a bull. 
 * The correct ranges are:<br> 
 * general appearance 0-40<br> 
 * dairy character 0-30 <br>
 * body capacity 0-30<br>
 *
 * but we are not testing for input validity.
 *
 * @author David Briggs 
 */
public class BullClassificationScore extends ClassificationScore {
    /**
     * General appearance is a score between 0 and 40.
     */
    private int generalAppearance;
    /**
     * Dairy character is a score between 0 and 30.
     */
    private int dairyCharacter;
    /**
     * Body capacity is a score between 0 and 30.
     */
    private int bodyCapacity;

    /**
     *  default constructor is not declared.  Java will create one,
     *  but these are immutable  objects so the super class can not
     *  create an empty object.
     */
    

    /**
     * Parameterized Constructor for BullClassificationScore
     *
     * @param month int representing the month the score was determined
     * @param day int representing the day the score was determined
     * @param year int representing the year the score was determined
     * @param gnrlAppearance int ranking for general appearance of the bull
     * @param dryCharacter int ranking for dairy characteristics of the bull
     * @param bdyCapacity int ranking for body capacity of the bull
     */
    public BullClassificationScore(int month, int day, int year, 
            int gnrlAppearance,  int dryCharacter, int bdyCapacity) {
        super(month, day, year);
        this.generalAppearance = gnrlAppearance;
        this.dairyCharacter = dryCharacter;
        this.bodyCapacity = bdyCapacity;        
    }
    /**
     * Accessor for the property generalAppearance.
     * @return the integer value of the property
     */
    public int getGeneralAppearance() {
        return generalAppearance;
    }
    
    /**
     * Accessor for the property dairyCharacter.
     * @return the integer value of the property 
     */
    public int getDairyCharacter() {
        return dairyCharacter;
    }
    
    /**
     * Accessor for the property bodyCapacity.
     * @return the integer value of the property
     */
    public int getBodyCapacity() {
        return bodyCapacity;
    }
    /**
     * Constructs a  string representation of the date and score.
     * All values are labeled as per the specification
     * required format:<br>
     * <em>Date = 6-1-2001</em>    - from super class<br>
     * <em>GA = 28 DC = 17 BC = 18 Total = 33</em> -- from this class
     * 
     */

    public String toString() {
        StringBuffer str = new StringBuffer();
        // get the first part of the string from the super class
        str.append(super.toString());
        str.append(String.format(" GA = %d DC = %d BC = %d "
                + "Total = %d",
                generalAppearance, dairyCharacter, bodyCapacity,
               (generalAppearance + dairyCharacter + bodyCapacity )));

        return str.toString();
    }
}
