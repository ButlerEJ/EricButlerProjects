/**********************************************************************
 * REVISION HISTORY (newest first)
 * THIS CLASS IS FINISHED - DO NOT CHANGE
 **********************************************************************
 * 04/19/2017 - Anne Applin - added documentation
 * 2015 - David Briggs - original starting code
 *********************************************************************/ 
 
package bullsandcows;

/**
 *  The superclass to hold classification scores for
 *  both genders of Holsteins.
 *  
 *  It has integer fields for the year, month and day of the
 *  score.  The representation scheme is<br> 
 *  <br>
 *  year  : some positive value >= 1700<br>
 *  month : 1-12 for January to December<br>
 *  day   : 1-31 for the day of the month<br>
 *  <br>
 *  such that the three together represent a valid date.
 *  We are not testing for input validity, however.
 *  
 * @author David Briggs
 * 
 */

public abstract class ClassificationScore {
    /**
     * The month that this classification was done.
     */
    protected int scoreMonth;
    /**
     * The day of the month that this classification was done.
     */
    protected int scoreDay;
    /**
     * The year that this classification was done.
     */
    protected int scoreYear;
    /**
     * private default constructor to prevent the creation of an
     * empty object.  This class is immutable.
     */
    private ClassificationScore(){}
    /**
     * Parameterized constructor.
     * @param month int representing the month the score was determined
     * @param day int representing the day the score was determined 
     * @param year int representing the year the score was determined
    */
    protected ClassificationScore(int month, int day, int year){
       scoreMonth = month;
       scoreDay   = day;
       scoreYear  = year;
    }
    
    /**
     * A string representation of the date the classification was done
     * The zero flag for month and day ensure 0 padding to the specified
     * width of 2. 
     * @return constructed and labeled string in the mm-dd-yyyy format
     */
    public String toString(){     
        return String.format("Date: %02d-%02d-%d",
                scoreMonth, scoreDay, scoreYear);
    }
    /**
     * Unit test for  BullClassificationScore and CowClassificationScore.
     * Tests constructor and toString .
     * @param args command line arguments
     */
    public static void main(String[] args){
         
        ClassificationScore perfectCow =  
                new CowClassificationScore(1, 2, 2010, 30,20,20,30);
        ClassificationScore aveCow  =  
                new CowClassificationScore(3, 4, 2011, 10,11,12,13);
        ClassificationScore perfectBull  = 
                new BullClassificationScore(5, 6, 2012, 40,30,30);
        ClassificationScore aveBull  =  
               new BullClassificationScore(10, 8, 2013, 9,19,29);
        
        System.out.println("Pefect Cow\n" + perfectCow);
        System.out.println("Average Cow\n" + aveCow);
        System.out.println("Pefect Bull\n" + perfectBull);
        System.out.println("Average Bull\n" + aveBull);

   }
}

