package ericbutlerstargazer;

import acm.graphics.*;
import java.awt.*;

/**
 * Below is my Star class and variables.
 * Author: Eric Butler
 */
public class Star {

   private final double xCoord;
   private final double yCoord;
   private final int henryD;
   private final double brightness;

/** Below is my initializing constructor that accepts appropriate variables.
 * 
 * @param xCoord
 * @param yCoord
 * @param henryD
 * @param brightness 
 */
    public Star(double xCoord, double yCoord, int henryD, double brightness) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.henryD = henryD;
        this.brightness = brightness;
    }

/** My getter method for x coordinates
 * 
 * @return 
 */
    public double getXCoord() {
        return xCoord;
    }

/** My getter method for y coordinates
 * 
 * @return 
 */
    public double getYCoord() {
        return yCoord;
    }

/** My getter method for Henry Draper ID
 * 
 * @return 
 */
    public int getHenryD() {
        return henryD;
    }

/** My getter method for the star magnitudes
 * 
 * @return 
 */
    public double getBrightness() {
        return brightness;

    }

/** My coordsToPoint method takes in the coordinates and relocates them based upon
 * the java coordinate system so that they will be located correctly on the canvas.
 * @param picSize
 * @return 
 */
    public GPoint coordsToPoint(int picSize) {
         double newX = (xCoord + 1) / 2 * picSize;
                double newY = (yCoord + 1) / 2 * picSize;
                newY = picSize - newY;
                GPoint starPoint = new GPoint (newX, newY);
                return starPoint;
        

    }

/** getDot method correct assigns a star size based on the star's magnitude and
 * assigns the correct location. New GOvals are created to represent stars on the
 * canvas. The GOvals are then filled to the color white.
 * 
 * @param picSize
 * @param maxStarSize
 * @return 
 */
    public GOval getDot(int picSize, int maxStarSize) {
        double starSize = Math.round(maxStarSize/(this.brightness + 2));
        GPoint starPoint = coordsToPoint(picSize);
        GOval starOval = new GOval(starPoint.getX() - starSize / 2, starPoint.getY() - starSize / 2,starSize, starSize);
        starOval.setFilled(true);
        starOval.setFillColor(Color.WHITE);
        return starOval;

    }

 }
