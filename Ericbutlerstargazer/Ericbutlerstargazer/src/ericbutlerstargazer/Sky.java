package ericbutlerstargazer;

import acm.graphics.*;
import java.util.*;

/** My Sky class creates an array list to gather stars.
 * 
 * Author: Eric Butler
 */
public class Sky {

    private ArrayList<Star> starList;

/** Initializing constructor that assigns the starList array list
 * 
 */
    public Sky() {
        starList = new ArrayList<Star>();
    }

/** Adds a new star (s) to the starList array
 * 
 * @param s 
 */    
    public void addStar(Star s) {
        starList.add(s);
    }

/** paint method goes through the starList array list and adds them each to the
 * canvas and utilizes the getDot method.
 * @param canvas
 * @param picSize
 * @param maxStarSize 
 */
    public void paint(GCanvas canvas, int picSize, int maxStarSize) {

        for (Star star : starList) {
            canvas.add(star.getDot(picSize, maxStarSize));
        }
    }

/** getStaInSkyByHD method goes through the starList array list and when Henry
 * Draper IDs match we have found a matching star. This method is used to draw the
 * constellations.
 * @param henryD
 * @return 
 */
    public Star getStarInSkyByHD(int henryD) {
        Star starFound = null;
        for (Star star : starList) {
            if (henryD == star.getHenryD()) {
                starFound = star;
            }
        }
        return starFound;
    }
}

