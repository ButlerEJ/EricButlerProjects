/*******************************************************************/
/*Author: Eric Butler.                                             */
/*Date: 2/09/2022                                                  */
/*Project01: Create a program that acts like a filter on tab chars.*/
/*Reads in a tab and turns into a certain amount of whitespaces    */
/*depending on the size of a column and the number of chars already*/
/*present in that column.                                          */
/*******************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int main (int argc, char* argv[]){ //Main takes in argument count and argument value.

  printf("Number of arguments: %d\n", argc); //Print number of arguments for debugging.

  int numSpaces = 8; //Unsure if this was supposed to be 4 or 8. Spaces per column.
  int inChar = getchar(); //Control variable for chars we're currently at.
  int charCount =0; //Counting the accumulation of characters prior to hitting a tab or new line.
  int spaces = 0; //Variable used to determine amount of spaces the print.
  const int tab = 9; //Int assigned for a tab character. Increase readability in the code.
  const int newline = 10; //Int assigned for a newline character. Increase readability in the code.
  const int whitespace= 32; //Int used for a whitespace character. Increase readability in the code.

  if (argc == 2 || argc > 3) //If incorrect number of arguments, kill the program.
  {
    fprintf(stderr,"Invalid arguments given.\n"); //Error message for invalid argument count.
    return 0;
  }
  if (argc == 3) // If 3 arguments given then assign the value of numSpaces as the 3rd argument.
  {
    numSpaces = atoi(argv[2]);
  }


  while(inChar != -1) //While Not end of the file.
  {
    if (inChar == tab)  //If the char we're at is a tab
    {
      spaces = numSpaces - (charCount % numSpaces); //Calculate and assign the value of whitespaces to be generated.
      inChar = whitespace; //Change it to a period
      for (int i = 0; i < spaces; i++) //For loop for the number of whitespaces to output.
      {
        putchar(inChar); //Output awhitespace.
        charCount++; //Increment the number of characters seen.
      }
      inChar = getchar(); //Get a new char to loop through.
    } 
    else
    { //Not a tab
      charCount++; //Increment characters seen.
      putchar(inChar); //Output this non-tab character.
      inChar = getchar(); //Get a new char to loop through.
    }
    if (inChar == newline) //If we are looking at a newline character
    {
      charCount = 0; //Reset our character counter. We're entering a newline.   
    }
  }//While not at the end
  return 0;
}//Main
