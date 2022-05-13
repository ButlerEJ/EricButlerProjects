##########################################################################################
# I, Eric Butler, began working on this project on 11/14/2021. Many revisions and much
# help later from Professor Anne Applin I was able to finish a solution.
##########################################################################################

.include "macros.asm"
.data
cartItems:	.space  480
buffer:		.space  20
unsortMsg: 	.asciiz "Your unsorted cart: \n"
sortMsg: 	.asciiz "Your sorted cart: \n"
itemPrompt:	.asciiz "Enter the name of the item --> "
pricePrompt:	.asciiz "Enter the price of the item --> "
newLine:		.asciiz "\n"
nullChar:	.asciiz "\0"
another:		.asciiz "Is there another item to add to the cart? 1 for yes. --> "
space:		.ascii  " "
comma:		.ascii  ","

.text
###########################################################################		 		
main:	# use the s registers only in main.

	la    	$s0,	cartItems  	# load base address of the array
  	li    	$s1, 	20         	# max count for array			 
  	li    	$s2, 	0          	# counter in $s2
	lb	$s4,	newLine		# Line break in $s4	
	lb	$s5,	nullChar		# Store the \0 in $s5
	
inLoop:	bge   	$s2, 	$s1,	 endInLoop  	# if the counter hits 20 items stop
	print_str_at(itemPrompt)	# Prompt for item name
	la	$s3,	buffer	# Store the buffer space in $s3
	la    	$a0, 	($s3)    # put the buffer address in the array in $a0
	li    	$a1, 	20	# length of the string
	li    	$v0, 	8        # load 8 (read string)into $v0
	syscall  		# do the syscall
	push($s3)		# source
	push($s0)		# destination.
	jal cleanString	 	# copies string from buffer to cart.
	print_str_at(pricePrompt)	# Prompt user for item price. 
	addi	$s0,	$s0,	20	# increment to price .
	read_int($s6)		# Read in the item price.
	sw $s6, 0($s0)		# Store item price in $s0 20 indices in.
	addi	$s0,	$s0,	4	# increment to next item.
	addi	$s2,	$s2,	1	# increment counter.	
	print_str_at(another)	# Prompt user if they want to add more items.
	read_int($s4)		# Read in and store user choice for another item.
	beq $s4, 1, inLoop 	# If user pressed 1 cycle back through and read data.
	bne $s4, 1, endInLoop	# If user doesnt enter 1 as an integer we go to endInLoop.
	
endInLoop:
	la $s0, cartItems		# Load the array into $s0.
	push($s0)		# Push the array on the stack.
    	push($s2)		# Push the buffer space on the stack.
    	print_str_at_label(newLine)# Go to a new line.
    	print_str_at(unsortMsg)	# Print the unsorted prompt.
    	jal printCart		# Jump and link to the printCart function where unsorted cart will be printed.
    	print_str_at(sortMsg)	# Print out the sorted message prompt.
    	jal sort			# Jump and link to the sort method where the user input is sorted.
    	push($s0)		# Push the array onto the stack.
    	push($s2)		# Push the buffer onto the stack.
    	jal printCart		# Jump and link to the printCart function to print the sorted array.
    	halt 			# After the sorted cart is displayed we terminate the program.
######################################################
# Pre-conditions: Receives the starting addresses 
# (a 32-bit value) of the source and 
# destination string arrays pushed on the stack.  
# Assume that the source string ends with 
# the '\n' (ascii code 10) .  
# Assume that the destination array is 
# large enough to hold the source characters.
#
# Post-condition: Copies the contents of 
# source string into the destination array. Removes
# the '\n' puts in a comma, fills with spaces and 
# ends with a '\0' null char to end the string.  
# Use the stack to preserve and restore modified 
# registers.   Does not call any other function so 
# I don't need to back up the return address register
# use the t registers 
# $t0 source array, $t1 destingation, 
# Author:  Anne Applin 11/12/2021
######################################################
# These blocks of code are used for string formating.
cleanString:	# back up current values
	push($t0)
	push($t1)
	lw	$t1,	8($sp)		# destination address
	lw	$t0,	12($sp)		# source address
	lb	$t4,	newLine
	lb	$t5,	nullChar
	lb	$t2,	($t0)		# load char from source
	li	$t6,	0		# we will process 19 chars

copyLoop:			
	beq	$t2,	$t4,	endcopy	# once we find \n goto fill
	sb 	$t2,	($t1)		# store char in destination
	addi	$t0,	$t0,	1	# move to next byte
	addi	$t1,	$t1,	1	# move to next byte
	lb	$t2,	($t0)		# get source char
	addi	$t6,	$t6,	1	# increment counter
	b 	copyLoop
		
endcopy:	
	lb	$t4,	comma
	sb	$t4,	($t1)		# store char in destination
	addi	$t1,	$t1,	1	# move to next byte 
	addi	$t6,	$t6,	1	# increment counter
	lb	$t4,	space
		
fill:	beq	$t6,	19,	endFill
	sb	$t4,	($t1)		# store char in destination
	addi	$t1,	$t1,	1	# move to next byte 
	addi	$t6,	$t6,	1	# increment counter
	b	fill
		
endFill:	sb	$t5,	($t1)		# finish string with a null
	pop($t1)			# restore backed up value
	pop($t0)			# restore backed up value
	addi	$sp,	$sp,	8	# deallocate 2 parameters
	jr   $ra


   	
 # Sorting by numberical value. Will always be 20 offset from the base. Have to swap the two strings,
 ##################################
 # Bubble Sort (this is a bad sort!)
 # procedure bubbleSort( A : list of sortable items ) $s0 (A, base Address)
 #   n = length(A) 				      $s2 (n, Length of A) 
 #   repeat
 #     swapped = false
 #     for i = 1 to n-1 inclusive do 		      $t0 (i)  $t3(n - 1)
 #       /* if this pair is out of order */
 #       if A[i-1] > A[i] then			      $t3(a[i -1] <-- calculate offset) $t5(a[i])
 #         /* swap them and remember something changed */
 #         swap( A[i-1], A[i] )
 #         swapped = true
 #       end if
 #     end for
 #   until not swapped
 # end procedure
 #################################### 
sort:	
	push($ra)    		# Return pushed on the stack
	push($s0)		# array pushed on the stack
	push($s2)		# Size of the array pushed on stack
	push($t0)		# $t registers that will be used for the sort. Push them on the stack
	push($t1)
	push($t2)
	push($t3)
	push($t4)
	push($t5)
	push($t6)
	push($t7)		# Size pushed on the stack
   	add $t0, $zero, $zero 	# Starting the index at 0, for $t0 = i   	
  outerLoop:        
  	addi $t0, $t0, 1	        	# Move index up one, i++
  	bgt $t0, $s2, endouterLoop # If our current index is greater than the overall stored in $s2 then be are done with the outerloop
   	add $t1, $s2, $zero 	# Putting the current index back into register $t1, a second counter.  	   	
  innerLoop:
  	bge $t0, $t1, outerLoop	# If current index is less than or equal to i we hop back through the outer loop.
   	slt $t2, $t1, $t0     	# Store the index location in $t2 if i is less than j.
   	bne $t2, $zero, outerLoop 	# If We haven't scanned through the indexes go back to the outler loop.
   	addi $t1, $t1, -1 	# Walk back on the indices of j, our inner loop marker.   	
   	mul  $t3, $t1, 24    	# array offset for j.
         addi $t2, $t3, -24     	# Take away 24 bytes to achieve array offset for j - 1. 
   	add $t6, $t3, $s0 	# Get us back to current index of j and store in a new register.   
   	add $t7,$t2, $s0 		# Another register used to store the location/value of j - 1.   	
   	lw $t4, 20($t6) 		# Load the value of index at $t6 into register $t4.
   	lw $t5, 20($t7) 		# Load the value of index at $t7 into register $t5.
   	bgt $t4, $t5, innerLoop 	# If The value of the current index is greater than the value of the next index we go back through the inner loop and sort   	
   	push($t4)		#Push $t4 and $t5 on the stack before jump and link.
   	push($t5)
   	jal intSwap 		# Head to the integer swapping function. Determines ascending order of item prices.
   	push($t6)		# Next three calls to copyString act in similar fashion to how we store and swap values in Java.
       	push($s3)
       	jal copyString
       	push($t7)
       	push($t6)
       	jal copyString  
       	push($s3)
       	push($t7)
       	jal copyString   		
  	j innerLoop		# Do it all again	  	
  endouterLoop: 
	pop($t7)			# Pop all registers we used off the stack.
	pop($t6)
	pop($t5)
	pop($t4)
	pop($t3)
	pop($t2)
	pop($t1)
	pop($t0)
	pop($s2)
	pop($s0)
   	pop($ra)
   	addi    $sp,    $sp,    8	# Reallocate space on the stack.
	jr $ra 			# End and return to the address we came from.

intSwap:	
	push($t4)		# Push $t4 and $t5 on the stack
	push($t5)	
	sw $t4, 20($t7) 		# Swap the values in $t4 and $t5
   	sw $t5, 20($t6)   
	pop($t5)			# Pop them back off the stack
	pop($t4)	
	addi $sp, $sp, 8		# Deallocating parameters. Returning the stack to origin
	jr $ra			# return back to the address the function call occured.
	
copyString: 
   	push($t0)		# Back up registers to be used.
    	push($t1)   
    	push($t2)
    	push($t3)   
   	push($t6)
    	
    	lw   $t1,  20($sp)	#  Get values from the stack.
    	lw   $t0,  24($sp)
    	li   $t6,  0    		#  Loop counter.
  copyStringBytes:
    	bge  $t6, 20, endCopyString# Leave the loop when we have reached the end.
    	lb $t2, ($t0)  		# Load source into $t2.
    	sb $t2, ($t1)   		# Put it in destination $t1.
    	addi $t0, $t0, 1		# Traverses up by one in $t0 register.
   	addi $t1, $t1, 1   	# Traverses up by one in $t1 register.
    	addi  $t6,    $t6,    1	# Incrementing the loop counter.
    	b     copyStringBytes          
endCopyString:
    	pop($t6)			# Pop all the registers used in reverse order, LIFO.
    	pop($t3)
    	pop($t2)
    	pop($t1)
    	pop($t0) 
   	addi  $sp,    $sp,   8    # deallocate 2 parameters
    	jr    $ra   
	
printCart:
	push($t0)		# Backing up current value at register $t0.
	push($t1)		# $t1 is used as the counter. Backing up before we use.
	push($t9)		# $t9 being used as the beginning of an element.
	lw $t1, 12($sp)		# Number of elements
	lw $t0, 16($sp)		# Base address of the array		
  printCartItem:
        	beqz $t1, exit		# If we reach max capacity, exit the loop.	
	print_str_at_address($t0)  # Prints The string at array address. 
	addi $t0, $t0, 20		# Increment counter by 20 bytes to cover a word.
	lw $t9, ($t0)		# Loads the contents of current array index into $t9.
	printInt ($t9)		# Prints the integer
	print_str_at_label(newLine)
	addi $t0, $t0, 4		# Gets us to the next element	
	addi $t1, $t1, -1		# Decrements 
	b printCartItem		
  exit: 
	pop($t9)			# Restored backed up values
	pop($t1)			# Restored backed up values
	pop($t0)			# Restored backed up values
	addi $sp, $sp, 8		# Deallocating parameters. Returning the stack to origin
	jr $ra
	
