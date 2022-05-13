package ericbutlerproject1;

/* Project 1 - Connecting random colored
dots with lines.
Author: Eric Butler
Date: 02/13/2021
 */
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ericbutlerproject1 extends GraphicsProgram {

    final String TITLE = "Eric Butler Project1";
    final String LABEL = "Make a Picture By Connecting Dots";
    final String FONT = "Arial-24";
    final int WIDE = 700;
    final int TALL = 700;
    final int LABEL_WIDE = 200;
    final int LABEL_X = WIDE / 5 + 10;
    final int LABEL_Y = 20;
    final int DOT_WIDE = 20;
    final int DOT_TALL = 20;
    double prevX, prevY, currentX, currentY;
    double totalLen = 0;
    int dotCount = 0;
    double lineLen= 0;
/* The above section contains all of my constant (final) variables that
   will remain unchanged through out the code as well as variable that will 
   will change in value.
*/    
    
    public static void main(String[] args) {
        new ericbutlerproject1().start();
    }

    @Override
    public void init() {
        setSize(WIDE, TALL);
        setTitle(TITLE);

        GLabel myLabel = new GLabel(LABEL);
        myLabel.setFont(FONT);
        myLabel.setLocation(LABEL_X, LABEL_Y);
        add(myLabel);

        addMouseListeners();
    }
/* The above section of code initiates my project's title, as well as the label
   and it's location and font. It also activates the mouse listener which will
   detect where I am clicking inside of the project window.
*/    
    
        
    @Override
    public void mouseClicked(MouseEvent me) {

    currentX = me.getX();
    currentY = me.getY();
    dotCount++;
/* The me.get statements provide the x and y coordinates of my mouse clicks.
   the dotCount ++ signals the program to add 1 dot to the counter per click.
*/
       
                 

        GOval myOval = new GOval(currentX - DOT_WIDE / 2, currentY - DOT_TALL / 2, DOT_WIDE, DOT_TALL);
        myOval.setFilled(true);
        add(myOval);

        Random randGen = new Random();
        int randCol = randGen.nextInt(6);
/* The Goval coding determines the size of the dot, as well as where the dot 
   will be located relative to the cursor. The randGen statement initiates a
   6 digit random number generator from 0 - 5.
*/        

        if (randCol == 0) {
            myOval.setColor(Color.GREEN);
        } else if (randCol == 1) {
            myOval.setColor(Color.BLUE);
        } else if (randCol == 2) {
            myOval.setColor(Color.RED);
        } else if (randCol == 3) {
            myOval.setColor(Color.YELLOW);
        } else if (randCol == 4) {
            myOval.setColor(Color.MAGENTA);
        } else {
            myOval.setColor(Color.DARK_GRAY);
        }
        if (dotCount > 1) {
            GLine newLine = new GLine (prevX, prevY, currentX, currentY);
            add(newLine);
            
        
            lineLen = Math.sqrt(Math.pow(currentX - prevX, 2) + Math.pow(currentY - prevY, 2));
        }
/* The above section of code sets the perameters for which color will be
   associated with the generated dot based on the random number generator.
   Also, a line will created if the value of dotCount is more than one. I used
   Pathagoream's Thereom to determine the length of line generated.
*/

    totalLen = totalLen + lineLen;               
    prevX = (currentX);
    prevY = (currentY);
/* These prevX and prevY values will locate where the program will put the line
   to and after the next mouse click. the totalLen value adds the value of each
   lineLen to it and will compile the total sum of lines
*/
        
        if (dotCount == 1) {
            System.out.printf(dotCount + "  dot, total line length = %11.2f%n", + totalLen); 
        }
        if (dotCount > 1) {
            System.out.printf(dotCount + " dots, total line length = %11.2f%n", totalLen);
        }
/*  These if statements tell the program what to output on the screen according
    to the dotCount value. the %11.2f formatting allows the decimal points to
    align vertically regardless of the value.
*/
            
        
        
            

        }

    }

