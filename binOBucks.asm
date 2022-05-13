# This is my, Eric Butler's, own work. Started on 10/6/2021
.include "macros.asm"
.data
	prompt: .asciiz "Greetings! Im your robot bank teller.\nHow many power-of-two dollars do you want?\n"
	result:	.asciiz "The fewest number of binary bucks is: \n"
	amount1024: .asciiz "\n1024 Dollar bill(s): "
	amount512: .asciiz "\n512 Dollar bill(s): "
	amount256: .asciiz "\n256 Dollar bill(s): "
	amount128: .asciiz "\n128 Dollar bill(s): "
	amount64: .asciiz "\n64 Dollar bill(s): "
	amount32: .asciiz "\n32 Dollar bill(s): "
	amount16: .asciiz "\n16 Dollar bill(s): "
	amount8: .asciiz "\n8 Dollar bill(s): "
	amount4: .asciiz "\n4 Dollar bill(s): "
	amount2: .asciiz "\n2 Dollar bill(s):"
	amount1: .asciiz "\n1 Dollar bill(s): "
.text 
	#initialize coin value
	li $s1, 0
	# Method to print a prompting message
	print_str_at_label(prompt)
	# method to read the users input
	readInt
	# Move the user's input to a register
	move $s0, $v0
	# Method to print a result message
	print_str_at_label(result)
	# Message for 1024 dollar bills
	print_str_at_label(amount1024)
	
	
	# Set $t0 to 1024 
	li $t0, 1
	# Shift left logic gives us a value of 1024
	sll $t0, $t0, 10
	# divide user input by 1024	
	div $s0, $s0, $t0
	#Store the quotient 
	mflo $s0
	#Store the remainder
	mfhi $t1
	#Multiply the quotient by the divisor store in $t3
	mul $t3, $s0, $t0
	#Subtract The remainder by the divisor store in t$2
	sub $t2, $t3, $t1	
	printInt($s0)
	
	
	# division on the user input by 512 then quotient and remainder are stored in hi/lo registers and the bill value is displayed
	print_str_at_label(amount512)
	#Set register $t0 to 512
	li $t0, 512	
	#Perform shift right logic on 512
	srl $s0, $t0, 9
	# Repeats the same step from part 1
	div $s0, $t1, $t0  
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	
	# the pattern continues
	print_str_at_label(amount256)
	# set divisor value to register $t0
	li $t0, 256
	#divide and store in $s0
	div $s0, $t1, $t0 
	#Store the quotient in $s0
	mflo $s0
	#Store remainder in $t1
	mfhi $t1	
	#Multiply quotient by the divisor, store in $t3	
	mul $t3, $s0, $t0
	#Subtract the product of quotient and divisor by the remainder. Store in $t2
	sub $t2, $t3, $t1
	printInt($s0)	
	
	
        #Rinse and repeat above steps
	print_str_at_label(amount128)
	li $t0, 128
	div $s0, $t1, $t0 
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)
			
	#Rinse and repeat above steps
	print_str_at_label(amount64)
	li $t0, 64
	div $s0, $t1, $t0
	mflo $s0
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1	
	printInt($s0)		
	
	#Nothing new, same as above
	print_str_at_label(amount32)
	li $t0, 32
	div $s0, $t1, $t0		
	mflo $s0
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	#Smooth sailing to the Bin O Bucks
	print_str_at_label(amount16)
	li $t0, 16
	div $s0, $t1, $t0 
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	#Do it all again
	print_str_at_label(amount8)
	li $t0, 8
	div $s0, $t1, $t0 
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	#And again
	print_str_at_label(amount4)
	li $t0, 4	
	div $s0, $t1, $t0
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	#Aaand again
	print_str_at_label(amount2)
	li $t0, 2
	div $s0, $t1, 2 
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	#Finish line!
	print_str_at_label(amount1)
	li $t0, 1
	div $s0, $t1, 1 
	mflo $s0		
	mfhi $t1
	mul $t3, $s0, $t0
	sub $t2, $t3, $t1
	printInt($s0)		
	
	
	
	halt
