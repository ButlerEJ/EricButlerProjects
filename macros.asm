##  My Macros
	##################################################
	# 	Print the string at the label 
	.macro print_str_at_label(%str)
	li	$v0,	4		
	la	$a0,	%str
	syscall
	.end_macro
	##################################################
	 .macro print_str_at_address(%str)
	 li	$v0,	4
	 la	$a0,	(%str)
	 syscall
	 .end_macro
	##################################################	
	# print an ascii char - lower 8 bits from register
	.macro printChar(%x)
	add	$a0,	$zero,	%x
	li	$v0,	11
	syscall
	.end_macro
	##################################################
	# print the contents of a register
	.macro printInt (%x)
	add	$a0, 	$zero,	%x
	li	$v0,	1
	syscall
	.end_macro
	##################################################
	# read an integer from the keyboard
	.macro readInt
	li 	$v0, 	5
	syscall	
	.end_macro
	##################################################
	#exit the program (halt)	
	.macro halt
	li	$v0,	10
	syscall
	.end_macro
	##################################################
	# make room for new value & push it onto the stack
	.macro push(%s)
	addiu	$sp, 	$sp,	-4 	# allocate space for word
	sw	%s, 	($sp) 		# store element from register to stack
	.end_macro
	##################################################
	# pop a value from the top of the stack
	.macro pop(%s)
	lw	%s,	($sp)		# load element from stack to register
	addiu	$sp, 	$sp,	4 	# deallocate space for word
	.end_macro
		##################################################
	# 	Print the string at the label
	.macro print_str_at(%str)
	li	$v0,	4		
	la	$a0,	%str
	syscall
	.end_macro
	##################################################
	# read an integer from the keyboard move into reg
	.macro read_int(%reg)
	li 	$v0, 	5
	syscall	
	move	%reg,	$v0
	.end_macro
