package ericbutlervetclinic;

import acm.gui.*;
import acm.program.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

/** Project 4 - Vet Clinic. Create a program that takes in dog data and finds
 * the heaviest dog entered.
 * Author: Eric Butler
 * Date 4/24/21
 */

/** Below Contains the global variable used in the program and the starting method
 */ 
 
public class Ericbutlervetclinic extends Program {

    JTextField dogName, ownerName, dogBreed, output;
    DoubleField dogWeight;
    IntField age;
    JButton add, heaviest;
    Dog[] Dogs = new Dog[5];
    final String TITLE = "Eric Butler's Vet Clinic";

    public static void main(String[] args) {
        new Ericbutlervetclinic().start();
    }
/** Below sets the dimensions for my JFrame and the measurements for text fields
 * Then I create the labels and fields for the dogs data to be entered into. As
 * well as buttons for adding a dog and retrieving the heaviest dog entered.
 */
    @Override
    public void init() {
        setTitle(TITLE);
        setSize(600, 600);
        final int ROWS = 6;
        final int COLS = 2;
        final int ROW_SPACING = 10;
        final int COL_SPACING = 10;
       
        setLayout(new TableLayout(ROWS, COLS, ROW_SPACING, COL_SPACING));
        JLabel labelName = new JLabel("Dog's Name");
        add(labelName);

        dogName = new JTextField(30);
        add(dogName);

        JLabel labelOwner = new JLabel("Owner's Name");
        add(labelOwner);

        ownerName = new JTextField(30);
        add(ownerName);

        JLabel labelBreed = new JLabel("Dog's Breed");
        add(labelBreed);

        dogBreed = new JTextField(30);
        add(dogBreed);

        JLabel labelWeight = new JLabel("Dog's Weight");
        add(labelWeight);

        dogWeight = new DoubleField();
        add(dogWeight);

        add = new JButton("Add Dog");
        add.addActionListener(this);
        add(add);

        heaviest = new JButton("Heaviest Dog");
        heaviest.addActionListener(this);
        add(heaviest);

        output = new JTextField(50);
        add(output, SOUTH);

    }
/** Below is the method that detects when either of the buttons have been clicked
 * If add dog is clicked then we retrieve the information in the text fields and add.
 * A series of if/else statements to deal with errors in entry, or exceeding weight
 * or quantity limits. If all guidelines are followed then we add that dog to the array 
 * and reset the text fields to blank or zero. If the heaviest dog wants to be found we
 * call on the getHeaviest method and display the heaviest dog.
 */
    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();
        
        if (cmd.equals("Add Dog")) {
            String dName = dogName.getText();
            String dOwner = ownerName.getText();
            String dBreed = dogBreed.getText();
            double dWeight = dogWeight.getValue();
            if (Dog.numDogs() > 4) {
                output.setText("Error: Exceeded dog limited");
            } else if (dogWeight.getText().isEmpty() || dogName.getText().isEmpty() || dogBreed.getText().isEmpty() || ownerName.getText().isEmpty()) {
                output.setText("Error: One or more fields empty");
            } else if (dogWeight.getValue() > 350.0) {
                output.setText("Error: Weight limit exceeded");            
            } else {
                Dog a = new Dog(dName, dOwner, dBreed, dWeight);
                Dogs[Dog.numDogs() - 1] = a;
                output.setText(a.toString());
            }
            dogName.setText("");
            ownerName.setText("");
            dogBreed.setText("");
            dogWeight.setValue(0.0);
        }        
        if (cmd.equals("Heaviest Dog")) {
            if (Dog.numDogs() == 0) {
                output.setText("No Dogs Entered");
            } else if (cmd.equals("Heaviest Dog")) {
                Dog b = Dog.getHeaviest(Dogs);
                output.setText(b.toString());
            }
        }
    }
}
