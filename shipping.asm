.include "macros.asm"
.data
welcome: .asciiz "Welcome to Butler Shipping\nPlease enter package volume in cubic inches between 1 and 8000: \n"
enterWeight: .asciiz  "Please enter package weight in pounds between 1 and 100\n"
choiceMenu: .asciiz "1) Ground 5-10 business days\n2) Super saver air 2-4 business days\n3) Next day air\nPlease select your shipping speed. Enter 1, 2, or 3:\n"
ground1: .asciiz "$8"
ground2: .asciiz "$12"
saver1: .asciiz "$12"
saver2: .asciiz "$16"
nextDay1: .asciiz "$15"
nextDay2: .asciiz "$25"
nextLine: .asciiz "\n"
shipping: .asciiz "Ship via: "
groundSelect: .asciiz "Ground shipping."
saverSelect: .asciiz "Super saver shipping."
nextDaySelect: .asciiz "Next day shipping."
invoice: .asciiz "Here is your invoice:\n"
invoiceVolume: .asciiz "Your package volume is: "
invoiceWeight: .asciiz "Your package weight is: "
invoiceCost: .asciiz "Your shipping cost is: "
anotherPackage: .asciiz "Do you want to ship another package? Enter 1 for yes or 0 for no\n"
done: .asciiz "Have a good day!"
.text
#Begin loop prompting for user input
volumeLoop:    print_str_at_label(welcome)
# read user input
   readInt
# move user input to $t1
   move    $t1,    $v0
# checks to see if user input is less than 1
   sle      $a0,    $t1,    $zero
# checks to see if user input is greater than 8000
   sgt      $a1,    $t1,    8000 
# checks to see if $a0 or $a1 both return 0 or not
   or       $a0,    $a0,    $a1
# checks to see if $a0 does not equal zero, will call upon the loop if user input is out of range.
   bnez     $a0,    volumeLoop
# end loop if user input is valid
   endvolumeLoop:
#prompts for user to enter weight
weightLoop:    print_str_at_label(enterWeight)
   readInt
# move user input to $t2
   move    $t2,    $v0
# checks to see if user input is less than 1
   sle      $a0,    $t2,    $zero
# checks to see if user input is greater than 100
   sgt      $a1,    $t2,    100 
# checks to see if $a0 or $a1 both return 0 or not
   or       $a0,    $a0,    $a1
# checks to see if $a0 does not equal zero, will call upon the loop if user input is out of range.
   bnez     $a0,    weightLoop
# end loop if user input is valid
   endweightLoop:# Begin the menu choice loop
choiceLoop:    print_str_at_label(choiceMenu)
# Read in user input
   readInt
# Move user input to register $t3
   move     $t3, $v0
# checks to see if user input is less than 1
   sle      $a0,    $t3,    $zero
# checks to see if user input is greater than 3
   sgt      $a1,    $t3,   3
# checks to see if $a0 or $a1 both return 0 or not
   or       $a0,    $a0,    $a1
# checks to see if $a0 does not equal zero, will call upon the loop if user input is out of range.
   bnez     $a0,    choiceLoop
# end loop if user input is valid
   endchoiceLoop:
# Check if user selected ground shipping
   beq      $t3, 1, ground
# Check if user selected super saver shipping
   beq      $t3, 2, saver
# Check if user selected next day shipping
   beq      $t3, 3, nextDay
# Go to ground shipping calculations if they did
ground: 
# If volume is over the first pricing tier then go to second pricing tier
  bgt $t1, 1000, groundCost2
# If weight is over the first pricing tier then go to second pricing tier
  bgt $t2, 60, groundCost2  
# Else both are within the first pricing tier, go to that 
 groundCost1:
# Invoice prompts for level 1 cost
print_str_at_label(invoice)
print_str_at_label(invoiceVolume)
printInt ($t1)
print_str_at_label(nextLine)
print_str_at_label(invoiceWeight)
printInt ($t2)
print_str_at_label(nextLine)
print_str_at_label(shipping)
print_str_at_label(groundSelect)
print_str_at_label(nextLine)
print_str_at_label(invoiceCost) 
print_str_at_label(ground1)
print_str_at_label(nextLine)
# Branch after we create an invoice and see if we want to ship more packages
b morePackages
# If we print the first pricing tier, then go to the end of this chunk of code.
b endground
# Our branch(es) brought us here, print the second tier price.
 groundCost2:
print_str_at_label(invoice)
print_str_at_label(invoiceVolume)
printInt ($t1)
print_str_at_label(nextLine)
print_str_at_label(invoiceWeight)
printInt ($t2)
print_str_at_label(nextLine)
print_str_at_label(shipping)
print_str_at_label(groundSelect)
print_str_at_label(nextLine)
print_str_at_label(invoiceCost) 
print_str_at_label(ground2)
print_str_at_label(nextLine)
b morePackages
# Exit the ground function 
endground:
# End the program
halt
# The user input brought us to super saver pricing. Same steps as ground shipping
saver: 
   bgt $t1, 1000, saverCost2
   bgt $t2, 40, saverCost2
  saverCost1:
  print_str_at_label(invoice)
print_str_at_label(invoiceVolume)
printInt ($t1)
print_str_at_label(nextLine)
print_str_at_label(invoiceWeight)
printInt ($t2)
print_str_at_label(nextLine)
print_str_at_label(shipping)
print_str_at_label(saverSelect)
print_str_at_label(nextLine)
print_str_at_label(invoiceCost)   
 print_str_at_label(saver1)
 print_str_at_label(nextLine)
b morePackages
   b endsaver
 saverCost2:
 print_str_at_label(invoice)
print_str_at_label(invoiceVolume)
printInt ($t1)
print_str_at_label(nextLine)
print_str_at_label(invoiceWeight)
printInt ($t2)
print_str_at_label(nextLine)
print_str_at_label(shipping)
print_str_at_label(saverSelect)
print_str_at_label(nextLine)
print_str_at_label(invoiceCost)
print_str_at_label(saver2)
print_str_at_label(nextLine)
b morePackages
endsaver:
halt
# The user input brought us to next day pricing. Same steps as ground and super saver shipping
nextDay: 
   bgt $t1, 1000, nextDayCost2
   bgt $t2, 30, nextDayCost2
   nextDayCost1:
   print_str_at_label(invoice)
print_str_at_label(invoiceVolume)
printInt ($t1)
print_str_at_label(nextLine)
print_str_at_label(invoiceWeight)
printInt ($t2)
print_str_at_label(nextLine)
print_str_at_label(shipping)
print_str_at_label(nextDaySelect)
print_str_at_label(nextLine)
print_str_at_label(invoiceCost)
print_str_at_label(nextDay1)
print_str_at_label(nextLine)
b morePackages
b endnextDay
   nextDayCost2:
   print_str_at_label(invoice)
print_str_at_label(invoiceVolume)
printInt ($t1)
print_str_at_label(nextLine)
print_str_at_label(invoiceWeight)
printInt ($t2)
print_str_at_label(nextLine)
print_str_at_label(shipping)
print_str_at_label(nextDaySelect)
print_str_at_label(nextLine)
print_str_at_label(invoiceCost) 
print_str_at_label(nextDay2)
print_str_at_label(nextLine)
b morePackages
endnextDay:
# Method for deciding if we are shipping more packages
morePackages:
print_str_at_label(anotherPackage)
readInt
move $t4, $v0
beq $t4, $zero, weAreDone
beq $t4, 1, volumeLoop
bgt $t4, 1, morePackages
ble $t4, 0, morePackages
# Prints a good bye message if we are done creating shipments
weAreDone:
print_str_at_label(done)
halt


	


