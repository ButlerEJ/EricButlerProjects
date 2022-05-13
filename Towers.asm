########################################################################
# I, Eric Butler, started this project 11-25-2021
# Created a revised edition on 12-01-2021
# Received a lot of support and psuedo code from Prof. Applin 12-09-2021
# Finished on 12-11-2021.
########################################################################
.include "macros.asm"

.data
userPrompt:	.asciiz "Enter the number of disks: "
startingDisks:	.asciiz "Start with "
onPeg:		.asciiz " disk(s) on peg "
moveDisk:	.ascii "Move disk from"
to:		.asciiz " to "
newLine:	.asciiz "\n"
noDisks:	.asciiz "Give me something to work with."

.text
main: 

	li $s1, 'A'			# Load 'A' to argument register $s1, symbolizes peg A, the source.
	li $s2, 'B'			# Load 'B' to argument register $s2, symbolizes peg B, the destination.
	li $s3, 'C'			# Load 'C' to argument register $s3, symbolizes peg C, the temp.
	print_str_at(userPrompt)	# See how many disks will be used. "System.out.println("Enter number of disks: ");".
	read_int($s0)			# Read the user's input and store in argument register $s0, symbolizes "int numDisks = scan.nextInt();".
	beq $s0, 0, printNone		# If no disks were entered we ask for a number to work with.
	print_str_at(startingDisks)	# Print "Start with ".
	printInt ($s0)			# Display number of disks we have.
	print_str_at(onPeg)		# Print " disk(s) on peg ".
	printChar($s1)			# Displays the source peg that our disks start on.	
	push ($s0)			# Push "n" onto the stack.
	push ($s1)			# Push source on the stack.
	push ($s2)			# Push Destination on the stack.
	push ($s3)			# Push temporary on the stack.
	jal Towers			# Jump and link to Towers function.
	halt				# End the program.
	
###########################################################################
#public static void Hanoi(int n, char source, char dest, char temp){
#        if (n == 1){
#            System.out.println("Move disk from " + source + " to " + dest);
#        } else {    
#            Hanoi(n - 1, source, temp, dest); 
#            System.out.println("Move disk from " + source + " to " + dest);
#            Hanoi(n - 1, temp, dest, source);
#        }
#    }
#    public static void main(String[] args) {
#       Scanner scnr = new Scanner(System.in);
#       
#       System.out.println("Enter the number of disks:");
#       
#       int numDisks = scnr.nextInt();
#       
#       System.out.println("Start with " + numDisks + " on a peg A.");
#       Hanoi(numDisks, 'A', 'B', 'C');       
#    }
#}
############################################################################

Towers: 			# public static void Hanoi(int n, char source, char dest, char temp){.
	pop  ($t3)	     	# pop temp and load into $t3.
	pop  ($t2)		# pop dest and load into $t2.
	pop  ($t1)		# pop source and load into $t1.
	pop  ($t0)   		# pop n and load into $t0.
        beq  $t0, 1, exit	# if we are at the base case go to the exit. if (n == 1){.
	addi $t0, $t0, -1	# decrement n. n - 1.
  	push ($t0)		# push back up for n on the stack.
  	push ($t1)		# push backup for source on the stack.	
  	push ($t2)		# push backup for dest on the stack.
  	push ($t3)    		# push backup for temp on the stack.
  	push ($ra) 		# push the return address on the stack.
        push ($t0)		# push the parameters in mixed order to cause first swap. (n, source, temp, dest).
        push ($t1)		# Push n.
        push ($t3)		# Push temporary.
        push ($t2)		# Push destination.
        jal Towers		# recursive call } else {.
        pop ($ra)		# Retrieve parameters from the stack in order of method signature, creating a parameter swap.
        pop ($t3)		# Temporary register now holds destination.
        pop ($t2)		# Destination register now holds temporary.
        pop ($t1)		# Source register still holds source.
        pop ($t0)		# n register still holds n.
        print_str_at(newLine)	# Print a new line for visiblity.
	print_str_at(moveDisk)	# print the output. System.out.println("Move disk from " + source + " to " + dest);.
	printChar($t1)		# Print source.
	print_str_at(to)	# Print " to ".
	printChar($t2)		# Print destination.
	push($ra)		# Retrieve parameters in a new order that swaps parameters. Moves the disks around.
	push($t0)		# Push "n" on the stack.
	push($t3)		# Push temporary register on the stack.
	push($t2)		# Push destination register on the stack.
	push($t1)		# Push source register on the stack.
	jal Towers		# Recursive call.
	pop ($ra)		# We land here when we are done printing the last swap.
	jr $ra   		# This jr $ra takes us to halt.

exit:
	print_str_at(newLine)	# Print a new line for printing visibility.
	print_str_at(moveDisk)	# print the output.
	printChar($t1)		# System.out.println("Move disk from " + source + " to " + dest);. Print source.
	print_str_at(to)	# Print " to ".
	printChar($t2) 		# Print destination.
 	jr $ra 			# Jumps us back to the return address at line 72 for a disk swap.
 	
printNone:			# Print function incase the user enters "0".
	print_str_at(newLine)	# Print a new line for visiblity.
	print_str_at(noDisks)	# Let the user know they did not enter any disks.
	print_str_at(newLine)	# Print a new line for visibility.
	j main			# Jump back to main and ask for user input.
