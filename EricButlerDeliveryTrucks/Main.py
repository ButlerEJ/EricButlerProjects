#Eric Butler | Student ID: 010875049 | C950 PA

import csv
import datetime
from Truck import Truck
from HashTable import HashTable
from Packages import Packages

# Load CSV files
with open("WGUPS Distance File.csv") as csvFile:
    csvDistance = list(csv.reader(csvFile))

with open("WGUPS Address File.csv") as csvFile2:
    csvAddress = list(csv.reader(csvFile2))

# Function to find the distance between two addresses
def distanceInbetweenAddresses(row, col):
    distance = csvDistance[row][col]
    if distance == '':
        distance = csvDistance[col][row]
    return float(distance)

# Function to get the index of an address from the address list
def getAddress(address):
    for row in csvAddress:
        if address.strip().lower() == row[2].strip().lower():
            return int(row[0])
    return None

# Create hash table instance
packageHTable = HashTable()

# Load the packages into the hash table
Packages.loadPackageData("WGUPS Package File.csv", packageHTable)

# Ensure Package 9 starts with its incorrect address
package9 = packageHTable.search('9')
print(f"Package 9 initial address at load time: {package9.packageAddress}")

# Initialize trucks
truck1 = Truck(1)
truck2 = Truck(2)
truck3 = Truck(3)

truck2.currentTime = datetime.timedelta(hours=9, minutes=5)  # 9:05 AM
truck3.currentTime = datetime.timedelta(hours=8, minutes=0)  # 8:00 AM

truck1.loadDistanceData('WGUPS Distance File.csv')
truck1.loadAddressData('WGUPS Address File.csv')
truck2.loadDistanceData('WGUPS Distance File.csv')
truck2.loadAddressData('WGUPS Address File.csv')
truck3.loadDistanceData('WGUPS Distance File.csv')
truck3.loadAddressData('WGUPS Address File.csv')

truck1.packages = [2, 4, 5, 7, 8, 9, 12, 17, 21, 22, 26, 27, 28, 32, 33, 35]
truck2.packages = [3, 6, 18, 23, 24, 25, 29, 34, 36, 38, 39, 40]
truck3.packages = [1, 10, 11, 13, 14, 15, 16, 19, 20, 30, 31, 37]

# Assign priority packages
packagesToBeDelivered9am = [15]
packagesToBeDelivered1030am = [1, 6, 13, 14, 16, 20, 25, 29, 30, 31, 34, 37, 40]

def deliverThePackages(truck, priorityPackages=None):
    packagesNotDelivered = []
    for packageID in truck.packages:
        package = packageHTable.search(str(packageID))
        if package is not None:
            packagesNotDelivered.append(package)

    truck.packages.clear()

    if priorityPackages:
        priorityPackagesNotDelivered = [package for package in packagesNotDelivered if
                                        int(package.packageID) in priorityPackages]
        packagesNotDelivered = [package for package in packagesNotDelivered if
                                int(package.packageID) not in priorityPackages]
        packagesNotDelivered = priorityPackagesNotDelivered + packagesNotDelivered

    for package in packagesNotDelivered:
        package.packageDepartTime = truck.currentTime
        package.truckID = truck.truckId

    while len(packagesNotDelivered) > 0:
        next_address = float('inf')
        nextPackage = None
        for package in packagesNotDelivered:
            # Handle Package 9's address update at 10:20 AM
            if package.packageID == '9' and truck.currentTime >= datetime.timedelta(hours=10, minutes=20):
                package.packageAddress = "410 S State St"
                package.packageCity = "Salt Lake City"
                package.packageState = "UT"
                package.packageZip = "84111"

            address1 = getAddress(truck.currentAddress)
            address2 = getAddress(package.packageAddress)
            if address1 is None or address2 is None:
                continue
            distance = distanceInbetweenAddresses(address1, address2)
            if distance <= next_address:
                next_address = distance
                nextPackage = package

        if nextPackage is not None:
            truck.packages.append(nextPackage.packageID)
            packagesNotDelivered.remove(nextPackage)
            truck.totalMiles += next_address
            truck.currentAddress = nextPackage.packageAddress
            travel_time = datetime.timedelta(hours=next_address / 18)  # Assuming 18 mph
            truck.currentTime += travel_time
            nextPackage.packageDeliveryTime = truck.currentTime
            nextPackage.packageStatus = "Delivered"
        else:
            break

    print(f"Truck {truck.truckId} return time: {truck.currentTime}")

# Deliver packages for Truck 2 and Truck 3 first with priority
print(f"Truck 2 departure time: {truck2.currentTime}")
deliverThePackages(truck2, priorityPackages=packagesToBeDelivered9am + packagesToBeDelivered1030am)

print(f"Truck 3 departure time: {truck3.currentTime}")
deliverThePackages(truck3, priorityPackages=packagesToBeDelivered9am + packagesToBeDelivered1030am)

truck1.currentTime = max(truck2.currentTime, truck3.currentTime)
print(f"Truck 1 departure time: {truck1.currentTime}")

# Deliver packages for Truck 1
deliverThePackages(truck1)

class Main:
    @staticmethod
    def menu():
        print("\nWestern Governors University Parcel Service (WGUPS)")
        print("1. View the mileage for today's route")
        print("2. Check status of package(s)")
        print("q. Quit")

    @staticmethod
    def getTotalMileage():
        total_mileage = truck1.totalMiles + truck2.totalMiles + truck3.totalMiles
        print(f"The mileage for today's route was: {total_mileage}")

    @staticmethod
    def getPackageStatus():
        timeInput = input("At what time would you like to view the status of package(s)? Use this format when entering"
                          " the desired time -- HH:MM:SS: ")
        (h, m, s) = timeInput.split(":")
        timeConversion = datetime.timedelta(hours=int(h), minutes=int(m), seconds=int(s))

        secondInput = input("You can either view the details of a single package by entering 'single' or view the "
                            "details for all packages on today's route by entering 'all': ")

        if secondInput == "single":
            try:
                singleInput = input("Enter the package's ID: ")
                package = packageHTable.search(singleInput)

                # Handle Package 9's address based on the time of query
                if package and package.packageID == '9':
                    if timeConversion >= datetime.timedelta(hours=10, minutes=20):
                        package.packageAddress = "410 S State St"
                    else:
                        package.packageAddress = "300 State St"

                if package:
                    package.getCurrentStatus(timeConversion)
                    deliveryTimeDisplay = package.packageDeliveryTime if package.packageStatus == "Delivered" else "N/A"
                    print(f"Package ID: {package.packageID} | Delivery Address: {package.packageAddress} | "
                          f"City: {package.packageCity} | Zip Code: {package.packageZip} | "
                          f"Deadline: {package.packageDeadline} | Weight: {package.packageWeight} | "
                          f"Status: {package.packageStatus} | Delivery Time: {deliveryTimeDisplay} | "
                          f"Delivered by Truck: {package.truckID}")
                else:
                    print("Package not found.")
            except ValueError:
                print("Entry invalid.")
        elif secondInput == "all":
            try:
                for packageID in range(1, 41):
                    package = packageHTable.search(str(packageID))
                    if package:
                        # Handle Package 9's address based on the time of query
                        if package.packageID == '9':
                            if timeConversion >= datetime.timedelta(hours=10, minutes=20):
                                package.packageAddress = "410 S State St"
                            else:
                                package.packageAddress = "300 State St"

                        package.getCurrentStatus(timeConversion)
                        deliveryTimeDisplay = package.packageDeliveryTime if package.packageStatus == "Delivered" else "N/A"
                        print(f"Package ID: {package.packageID} | Delivery Address: {package.packageAddress} | "
                              f"City: {package.packageCity} | Zip Code: {package.packageZip} | "
                              f"Deadline: {package.packageDeadline} | Weight: {package.packageWeight} | "
                              f"Status: {package.packageStatus} | Delivery Time: {deliveryTimeDisplay} | "
                              f"Delivered by Truck: {package.truckID}")
            except ValueError:
                print("Entry invalid.")

    @staticmethod
    def run():
        while True:
            Main.menu()
            choice = input("Enter your choice, '1' for the route's mileage or '2' to view package details: ").lower()
            if choice == '1':
                Main.getTotalMileage()
            elif choice == '2':
                Main.getPackageStatus()
            elif choice == 'q':
                print("Exiting program.")
                break
            else:
                print("Invalid choice. Please try again.")


if __name__ == "__main__":
    Main.run()