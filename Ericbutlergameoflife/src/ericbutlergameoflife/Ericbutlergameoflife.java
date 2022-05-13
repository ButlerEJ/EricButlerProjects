package ericbutlergameoflife;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Project 3 - Game of Life
 * Recreate Conway's Game of Life
 * Author: Eric Butler
 * Date 4/11/2021
*/

/**
 * Below contains the global variables used in the program */
public class Ericbutlergameoflife extends GraphicsProgram {

    final String TITLE = "Eric Butler's Game of Life";
    final int BOX_WIDTH = 603;
    final int BOX_HEIGHT = 697;
    final int CELL_WIDTH = 5;
    final int CELL_HEIGHT = 5;
    final int ROWS = 53;
    final int COLS = 49;
    GRect[][] grid = new GRect[ROWS][COLS];
    int[][] neighbor = new int[ROWS][COLS];
    Color LIVE_COLOR = Color.GREEN;
    Color DEAD_COLOR = Color.WHITE;
    JTextField output;
    JTextField generation;
    JButton resetButton;
    JButton startButton;
    int popCounter = 0;
    int genCounter = 0;
    boolean isStarted = false;
    int pauseLength = 300;

    /** Below is the method that generates my JFrame where the game is played.
     * It calls upon two other methods, Grid and Border. These generate the cell
     * grid and the border buttons respectively.
    */
    @Override
    public void init() {
        setTitle(TITLE);
        setSize(BOX_WIDTH, BOX_HEIGHT);
        initGrid();
        initBorder();
    }
    /** Below is my grid method. a nested for loop set the size of the JFrame.
     * It generates cells set to the DEAD_COLOR and then there is code to detect
     * mouse clicks within each cell and switches DEAD_COLOR to LIVE_COLOR, vice
     * versa and keeps track of cell population. Then the cell data is stored into
     * the 2D grid array.
    */
    public void initGrid() {

        int spacing = 2;
        for (int row = 0; row < ROWS; row++) {
            int y = 0 + (row * (10 + spacing));
            for (int col = 0; col < COLS; col++) {
                int x = 0 + (col * (10 + spacing));
                GRect cell = new GRect(x, y, 10, 10);
                cell.setFillColor(DEAD_COLOR);
                cell.setFilled(true);
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        if (cell.getFillColor() == DEAD_COLOR) {
                            cell.setFillColor(LIVE_COLOR);
                            popCounter++;
                        } else {
                            cell.setFillColor(DEAD_COLOR);
                            popCounter--;
                        }
                        updateOutput();
                    }
                });
                add(cell);
                grid[row][col] = cell;

            }
        }
    }
    /** This is the method that creates action buttons and text fields on the
     * bottom border of the JFrame. We begin with start and reset buttons and
     * fields to display generation and population.
    */
    public void initBorder() {
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton, SOUTH);
        generation = new JTextField("Generation:    ");
        add(generation, SOUTH);
        output = new JTextField("Population:     ");
        add(output, SOUTH);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        add(resetButton, SOUTH);
    }
    /** The method below takes the data stored in genCounter and popCounter and
     * displays them in the text fields for generation and population.
    */
    public void updateOutput() {
        String genmsg = "Generation: " + genCounter;
        generation.setText(genmsg);
        String popmsg = "Population: " + popCounter;
        output.setText(popmsg);
    }
    /** The method below dictates what happens when you click on the buttons in
     * the JFrame border. Reset will call the clearGrid method and assign all cells
     * the DEAD_COLOR. If start is selected the game begins by triggering the
     * isStarted boolean. Also, the start button becomes the pause button. If pause
     * is selected the boolean is turned off and the game stops compiling, and
     * pause switches back to start.
    */
    @Override
    public void actionPerformed(ActionEvent ae) {

        String buttonName = ae.getActionCommand();
        if (buttonName.equals("Reset")) {
            clearGrid();
        } else if (buttonName.equals("Start")) {
            isStarted = true;
            startButton.setText("Pause");
        } else if (buttonName.equals("Pause")) {
            isStarted = false;
            startButton.setText("Start");
        }

    }
    /** The method below is a nested for loop that scans all cells and assigns
     * their fill color to DEAD_COLOR, resets the counters to zero and calls upon
     * the updateOutput method the refresh the text fields in the JFrame border.
    */
    public void clearGrid() {
        int spacing = 2;
        for (int row = 0; row < ROWS; row++) {
            int y = 0 + (row * (10 + spacing));
            for (int col = 0; col < COLS; col++) {
                int x = 0 + (col * (10 + spacing));
                grid[row][col].setFillColor(DEAD_COLOR);
                popCounter = 0;
                genCounter = 0;
                updateOutput();
            }
        }
    }
    /** The method below is a nested for loop with multiple if statements inside
     * to test whether or not any particular live cell in the grid has a neighbor
     * on any one of it's sides or corners. If any live cell has a live cell neighbor
     * then the liveNeighbors count is incremented. The data for liveNeighbors is
     * then stored in the 2D array for neighbor.
    */
    public void neighborCount() {

        for (int row = 1; row < ROWS - 1; row++) {
            for (int col = 1; col < COLS - 1; col++) {
                int liveNeighbors = 0;
                if (grid[row - 1][col - 1].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row - 1][col].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row - 1][col + 1].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row][col + 1].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row + 1][col + 1].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row + 1][col].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row + 1][col - 1].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                if (grid[row][col - 1].getFillColor() == LIVE_COLOR) {
                    liveNeighbors++;
                }
                neighbor[row][col] = liveNeighbors;
            }
        }
    }
    /** The method below defines the rules of the game with a nested for loop.
     * If any live cell has less than two live neighbors it turns to a dead cell.
     * If a live cell has two or three neighbors it stays alive for another generation.
     * If there are 4 or more neighbors that live cell dies. If a dead cell has three
     * live neighbor cells that dead cell becomes a live cell. Population increments
     * or declines accordingly with each scenario.
    */
    public void nextGen() {

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (grid[row][col].getFillColor() == LIVE_COLOR) {
                    if (neighbor[row][col] <= 1) {
                        grid[row][col].setFillColor(DEAD_COLOR);
                        popCounter--;
                    } else if (neighbor[row][col] == 2 || neighbor[row][col] == 3) {
                        grid[row][col].setFillColor(LIVE_COLOR);
                    } else if (neighbor[row][col] >= 4) {
                        grid[row][col].setFillColor(DEAD_COLOR);
                        popCounter--;
                    }
                }
                if (grid[row][col].getFillColor() == DEAD_COLOR) {
                    if (neighbor[row][col] == 3) {
                        grid[row][col].setFillColor(LIVE_COLOR);
                        popCounter++;
                    }
                }
            }            
        }
    }
    /** The method below runs the program. It is also where the generations are
     * incremented after each iteration of the game. It contains an infinite while
     * loop while the isStarted boolean is on. inside the while loops a few methods
     * are called and compiled/displayed. There is a pause after the if statement
     * so that changes on the screen are perceivable.
    */
    @Override
    public void run() {
        genCounter = 0;
        while (true) {
            if (isStarted) {
                neighborCount();
                genCounter++;
                nextGen();
                updateOutput();
            }

            pause(pauseLength);
        }
    }

    public static void main(String[] args) {
        new Ericbutlergameoflife().start();

    }
}
