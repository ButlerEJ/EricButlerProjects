package ericbutlerstargazer;

import java.util.*;
import java.io.*;
import java.awt.*;
import acm.graphics.*;
import acm.program.*;

/**
 * Project 5 - StarGazer 
 * Date: 5/09/2021
 * Author: Eric Butler
 */

/**
 * Below is the method that initializes global variables. It also extends the
 * graphics library.
 */
public class Ericbutlerstargazer extends GraphicsProgram {

    final String TITLE = "Eric Butler's Star Gazer";
    final int BOX_WIDTH = 750;
    final int BOX_HEIGHT = 800;
    int picSize = 700;
    int maxStarSize = 20;
    Sky mySky;

/**
 * Main method, starts the program.
 * @param args 
 */
    public static void main(String[] args) {
        new Ericbutlerstargazer().start();
    }

/** 
 * Init method initiates the display box that will contain the star ovals
 * and constellation lines. Also creates the sky object.
 */    
    @Override
    public void init() {
        setTitle(TITLE);
        setSize(BOX_WIDTH, BOX_HEIGHT);
        setBackground(Color.BLACK);
        mySky = new Sky();

    }

/**
 * Run method calls methods and creates a canvas while the program is running.
 */
    public void run() {
        loadStars();
        GCanvas canvas = getGCanvas();
        mySky.paint(canvas, picSize, maxStarSize);
        drawConstellations();
    }

/**
 * loadStars method reads the data from file StarList.txt and the data is split
 * and parsed into new fragments of information according the their functions. A
 * new star object is created with it's dimensions being made up from the parsed 
 * information. Input stops being taken in and a catch is implemented for errors.
 */
    public void loadStars() {
        mySky = new Sky();
        File infile = new File("C:\\Users\\Lenovo1\\Downloads\\StarGazer\\StarList.txt");

        try {
            Scanner input = new Scanner(infile);
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] starData = data.split(" ");
                String starX = starData[0];
                String starY = starData[1];
                String starZ = starData[2];
                String starHD = starData[3];
                String starMag = starData[4];
                String starHR = starData[5];
                double xCoord = 0.0;
                double yCoord = 0.0;
                int henryD = 0;
                double brightness = 0.0;
                starZ = "";
                starHR = "";
                if (starData[0].length() > 0) {
                    xCoord = Double.parseDouble(starData[0]);

                }
                if (starData[1].length() > 0) {
                    yCoord = Double.parseDouble(starData[1]);

                }
                if (starData[3].length() > 0) {
                    henryD = Integer.parseInt(starData[3]);
                }
                if (starData[4].length() > 0) {
                    brightness = Double.parseDouble(starData[4]);
                }

                mySky.addStar(new Star(xCoord, yCoord, henryD, brightness));
            }

            input.close();
        } catch (IOException ioe) {

        }
    }

/**
 * the drawConstellations method also takes information in from a file called
 * constellations.txt and while there is another line of information to scan the 
 * program read whether each line begins with a letter or a number and behaves
 * accordingly. If a letter is present then that line of data is printed. If it 
 * begins with a number then information is parsed for x and y coordinates. In the
 * sky object a method is called getStarInSKyByHD and beginning and ending coordinates
 * are gathered for a new GLine to be created between the two points. Finally, a
 * condition is in place to detect if we are at the end of the file to accurately
 * print the number of lines used to create the last constellation. Two other if
 * statements to print our constellation names and number of lines to create it.
 */
    public void drawConstellations() {

        File infile2 = new File("C:\\Users\\Lenovo1\\Downloads\\StarGazer\\Constellations.txt");

        try {
            int conLine = 0;
            int totalLines = 0;
            Scanner input = new Scanner(infile2);
            while (input.hasNextLine()) {
                String data2 = input.nextLine();
                if (Character.isLetter(data2.charAt(0)) && conLine != 0) {
                    System.out.println("Added " + conLine + " lines.");

                }
                if (Character.isLetter(data2.charAt(0))) {
                    System.out.println("Adding constellation " + data2);
                    conLine = 0;

                } else if (Character.isDigit(data2.charAt(0))) {
                    String[] conData = data2.split(",");
                    String con1 = conData[0];
                    String con2 = conData[1];
                    GPoint startPoint;
                    GPoint endPoint;
                    Star starOne;
                    Star starTwo;
                    starOne = mySky.getStarInSkyByHD(Integer.parseInt(con1));
                    starTwo = mySky.getStarInSkyByHD(Integer.parseInt(con2));
                    startPoint = starOne.coordsToPoint(picSize);
                    endPoint = starTwo.coordsToPoint(picSize);
                    GLine line = new GLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
                    line.setColor(Color.YELLOW);
                    add(line);
                    conLine++;

                }
                if (Character.isLetter(data2.charAt(0)) && conLine != 0 || !input.hasNextLine()) {
                    System.out.println("Added " + conLine + " lines.");
                }
            }

        } catch (IOException ioe) {

        }
    }
}
