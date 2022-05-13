package ericbutlerbreakoutproject;
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/* Project 2 - Breakout Game
Create a reiteration of the classic arcade game, Breakout.
Author: Eric Butler
Date: 03/7/2021
 */

 
/* The code below begins my project and is the sectio where I declare and
initialize variables that will be used for the program.
*/
public class Ericbutlerbreakoutproject extends GraphicsProgram { 
    final String TITLE = "Eric Butler's Project 2";
    final String WIN = "You're A Winner!";
    final String LOSS = "You're A Loser!";
    final String FONT = "Arial-24";
    final int BRICK_COLUMNS = 10;
    final int BRICK_ROWS = 10;
    final int BOX_WIDTH = 600;
    final int BOX_HEIGHT = 600;
    final int BALL_SIZE = 20;
    final int BALL_X = BOX_WIDTH / 2;
    final int BALL_Y = BOX_HEIGHT / 3;
    final int PADDLE_Y = 550;
    final int PAUSE_DUR = 800;
    final int LABEL_X = BOX_WIDTH / 2 - 80;
    final int LABEL_Y = BOX_HEIGHT / 2;
    int PAUSELENGTH = 10;
    int numTurns = 3;
    int brickCount = 100;
    GRect paddle;
    GOval ball;
    double ballx;
    double bally;
    double velx = 1.0;
    double vely = 2.0;
    GLine top, left, right, bottom;
   
public static void main(String[] args) {
    new Ericbutlerbreakoutproject().start();
    }
    
/* The section of code below sets a title on my program window. Creates the
   playing field. Creates a paddle and fills it in with color. Then below are 
   lines that create the playing box and game boundaries
*/
    @Override
public void init(){
        setTitle(TITLE);
        setSize(BOX_HEIGHT + 40, BOX_WIDTH + 40);
        paddle = new GRect(400, PADDLE_Y, 60, 10);
        paddle.setColor(Color.BLUE);
        paddle.setFilled(true);
        add(paddle);
        
        top = new GLine (0, 0, BOX_WIDTH, 0);
        add(top);
        left = new GLine (0, 0, 0, BOX_HEIGHT);
        add(left);
        right = new GLine (BOX_WIDTH, 0, BOX_WIDTH, BOX_HEIGHT);
        add(right);
        bottom = new GLine (0,BOX_HEIGHT, BOX_WIDTH,BOX_HEIGHT);
        add(bottom);
        
        
/* Below is my nested loop that will generate the bricks that are spaced out
   by 4 pixels. Then the new bricks (GRects) are created and filled in red. 
   Then my ball (GOval) is created and filled in black. And lastly, I add
   mouse listener to detect mouse movement on the playing field.
*/    
        int spacing = 4;      
                
        for (int row = 0; row < BRICK_ROWS; row++) {
            int y = 0 + (row * (10 + spacing));
         for (int col = 0; col < BRICK_COLUMNS; col++) {
             int x = 5 + (col * (55 + spacing));
            GRect brick = new GRect( x , y + 30, 55, 10);
            brick.setColor(Color.RED);
            brick.setFilled(true);
            add(brick);
         }
        }
        ball = new GOval(BOX_WIDTH / 2, BOX_HEIGHT / 2, BALL_SIZE, BALL_SIZE);
        ball.setColor(Color.BLACK);
        ball.setFilled(true);
        add(ball);
        
        
        
        addMouseListeners();
    }
    
/* Below are if statements setting the limit to hwere my paddle will go. 
   Effectively keeping the paddle within the playing field boundaries.
*/
@Override
public void mouseMoved(MouseEvent me) {
       double x = me.getX();
        
       if (x + 60 > BOX_WIDTH) {
        x = BOX_WIDTH - 60;
       }
       if ( x <= 0) {
        x = 0;
       }
        paddle.setLocation(x, PADDLE_Y);
        
       }
    
/* Below is a random generator that will randomly select the x velocity.
   and the While/if block of code redirects the ball when it touches certain
   elements of the game. It takes into account which element it is and how the
   game should behave accordingly. Redirecting the ball, taking away a turn, or 
   destroying a brick. Resetting the ball's velocity or location depending on
   the scenario at the end. If you run out of turns or win it displays the
   appropriate message.
*/
@Override
public void run() {
        
        Random randGen = new Random();
        velx = randGen.nextDouble() * 3.0 + 1.0;
               
        if (randGen.nextBoolean()) {
            velx = -velx;}
                
        while (true) {
        ballx = ball.getX();
        bally = ball.getY();
                   
        GObject myObject;
        
        myObject = getElementAt(ballx, bally);
        if(myObject == null) {
             myObject = getElementAt(ballx + BALL_SIZE, bally);
        }
        if (myObject == null ){
             myObject = getElementAt(ballx, bally + BALL_SIZE);
        }
        if(myObject == null ){
             myObject = getElementAt(ballx + BALL_SIZE, bally + BALL_SIZE);
        }
        if (myObject == paddle){
            vely = -1 * Math.abs(vely);
        } else if (myObject == top){
            vely *= -1;
        } else if (myObject == left){
            velx *= -1;          
        } else if (myObject == right){
            velx*= -1;
        } else if (myObject == bottom){
            ballx = BOX_WIDTH / 2;
            bally = BOX_HEIGHT / 2;
            pause(PAUSE_DUR);
            numTurns -= 1;            
        } else if (myObject != null){
            brickCount -= 1;
            remove(myObject);
            vely = -1 * vely ;
        }
        if (numTurns == 0) {
            remove(ball);
            vely = 0;
            GLabel lossLabel = new GLabel (LOSS);
            lossLabel.setFont(FONT);
            lossLabel.setLocation(LABEL_X, LABEL_Y);
            add(lossLabel);
        }
        if (brickCount == 0) {
            remove(ball);
            vely = 0;
            GLabel winLabel = new GLabel (WIN);
            winLabel.setFont(FONT);
            winLabel.setLocation(LABEL_X, LABEL_Y);
            add(winLabel);
        }
         ballx += velx;
         bally += vely;            
                  
         ball.setLocation(ballx, bally);
                    
         pause(PAUSELENGTH);
         
        }
        
    }
}

    
   
              
    




