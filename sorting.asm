# I, Eric Butler, started creating this program on 10/27/2021. going for the "bad" sort, the bubble sort.
.include "macros.asm"
.data

myArray: .space 80
initializeSize: .asciiz "Enter size of an array between 1 and 20: \n"
getElements: .asciiz "Enter your desired elements:\n"
unsortMsg: .asciiz "\nYou have entered: "
sortMsg: .asciiz "\nHere is the sorted list: "
whiteSpace: .asciiz " "
elementFrontBracket: .asciiz "["
elementBackBracket: .asciiz "]"
elementComma: .asciiz ", "

.text

initializeLoop:
#Begin loop prompting for user input
   print_str_at_label(initializeSize)
# read user input
   readInt
# move user input to $t1
   move $s0, $v0
# checks to see if user input is less than 1
   sle  $a0, $s0, $zero
# checks to see if user input is greater than 20
   sgt  $a1, $s0, 20 
# checks to see if $a0 or $a1 both return 0 or not
   or   $a0, $a0, $a1
# checks to see if $a0 does not equal zero, will call upon the loop if user input is out of range.
   bnez $a0, initializeLoop
# end loop if user input is valid
   endinitializeLoop:

# Prompt user for integers to enter in the array
   print_str_at_label(getElements)
# Set the beginning address for the array
   addi $t0, $zero, 0

fillArray:
# When we reach capacity, branch out.
   bge $t0, $s0, arrayFilled
# Take in user inputs for elements
   readInt
# Create first address index
   add $t1, $t0, $zero
# shift left by 2, moving our location up 4 bits to next index
   sll $t1, $t0, 2
# User input stored as a variable in $t3
   add $t3, $v0, $zero
# Variable at $t3 then put into the array at address
   sw  $t3, myArray($t1)
# Incrementing index
   addi $t0, $t0, 1
   slt  $t1, $s0, $t0
# Loop back and check if array is full, if not keep filling
   j fillArray 

arrayFilled:
# Keeping the index in the right marker.
   sub $s0, $s0, 1
# Display the entered values
   print_str_at_label(unsortMsg)
# Jump to the loading/printing method to print unsorted array
   jal loadMyArray
# Store the array at $a0
   la $a0, myArray
# Keeping the index size in $a0
   addi $a1, $s0, 1 
# Jump to the bubble sort method
   jal bubbleSort
# Store number of elements
   addi $s5, $s0, 1 
endarrayFilled:


# Print the sorted array
   print_str_at_label(sortMsg)
# Jump and link to the loadMyArray function.
   jal loadMyArray

bubbleSort:
# Starting the index at 0, for $t0
   add $t0, $zero, $zero
outerLoop:
# Move index up one
   addi $t0, $t0, 1
# If our current index is greater than the overall stored in $a1 then be are done with the outerloop
   bgt $t0, $a1, endouterLoop
# Putting the current index back into register $t1
   add $t1, $a1, $zero 
innerLoop:
# If index j is less tha or equal to i we hop back through the outer loop
   bge $t0, $t1, outerLoop
# Store the index location in $t3 if i is less than j
   slt $t3, $t1, $t0
# If We haven't scanned through the indexes go back to the outler loop
   bne $t3, $zero, outerLoop
# Walk back on the indices of j, our inner loop marker.
   sub $t1, $t1, 1 
# Storing different indices of j into registers
   sll $t4, $t1, 2
# Take away 4 bytes to achieve j - 1
   addi $t3, $t4, -4
#  Get us back to current index of j and store in a new register.   
   add $t7, $t4, $a0
# Another register used to store the location/value of j - 1
   add $t8,$t3,$a0
# Load the value of index at $t7 into register $t5
   lw $t5, 0($t7)
# Load the value of index at $t8 into register $t6
   lw $t6, 0($t8)
# If The value of the current index is greater than the value of the next index we go back through the inner loop and sort
   bgt $t5, $t6, innerLoop
# Else we swap the values
   sw $t5, 0($t8)
   sw $t6, 0($t7)
# Do it all again
   j innerLoop
# End and store the return value
endouterLoop:
jr $ra



loadMyArray:
# Load myArray to register address $t0
   la $t0, myArray
# Clear the index
   add $t1,$zero,$zero
# For extra credit, first bracket.
print_str_at_label(elementFrontBracket)

printArray:
# Load the first element to $a0
   lw $a0, 0($t0)
# Assembly call to print an int.
   li $v0, 1
   syscall
# Jump up to the next element
   addi $t0, $t0, 4
# Increment the index
   addi $t1, $t1, 1
# Store the value 1 or 0 to $t2
   slt  $t2, $s0, $t1
# If we are at the end of our capacity then there are no more elements to print and we branch off to print the closing bracket for extra credit. Do this before the comma printing call.
   beq $t2, 1, printBracket
# Used for the extra credit. prints a comma and white space after each array element.
   print_str_at_label(elementComma)
 # $t1 was less than $s0 (the capacity) so we continue to print the elements.
   beq $t2, $zero, printArray
# Brings us to sort after unsorted array is displayed.
   jr $ra
# For extra credit prints the closing bracket.
printBracket:
   print_str_at_label(elementBackBracket)
# Jump back into the program after displaying unsorted array and then process our sorted array.
   j bubbleSort
# End program
halt:
