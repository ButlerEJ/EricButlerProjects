import csv
import datetime
from HashTable import *
from Packages import *

# Constructor for the Truck object.
# Assigns the object's initial attributes and values.
# Big O --> O(1).
class Truck:
    def __init__(self, truckId):
        self.truckId = truckId
        self.packages = []
        self.currentAddress = "4001 South 700 East"
        self.distanceData = []
        self.addressData = []
        self.totalMiles = 0
        self.currentTime = datetime.timedelta(hours = 8)

# Method to extract CSV from the provided distance table of N rows.
# Big O --> O(n).
    def loadDistanceData(self, fileName):
        with open(fileName, mode = 'r') as file:
            csv_reader = csv.reader(file, delimiter = ',')
            self.distanceData = [row for row in csv_reader]

# Method to extract CSV from the provided address tables of N addresses.
# Big O --> O(n).
    def loadAddressData(self, fileName):
        with open(fileName, mode = 'r') as file:
            csv_reader = csv.reader(file, delimiter = ',')
            self.addressData = [(row[0], row[1]) for row in csv_reader]

# Method to find the distances between two addresses.
# Uses 'enumerate' to iterate over the address data between two addresses,
# If both of the indexes are not 'none' (they have data to compare), we return
# the calculated distance as a float. Iterates over N number of addresses.
# Big O --> O(n)
    def distanceBetweenAddresses(self, address1, address2):
        index1 = next((i for i, addr in enumerate(self.addressData) if addr[1] == address1), None)
        index2 = next((i for i, addr in enumerate(self.addressData) if addr[1] == address2), None)
        if index1 is not None and index2 is not None:
            return float(self.distanceData[index1][index2])
        return None

# Method to find the shortest distance from our current addresses to our
# next stop. minDistance is assigned to the infinite value to ensure we
# don't miss a value in the spectrum. We iterate over all packages and those
# packages not already delivered get compared for the minimum distance, and
# the package with the minimum distance to go to is returned.
# Big O --> O(n)
    def minDistanceFrom(self, fromAddress):
        minDistance = float('inf')
        minPackage = None
        for package in self.packages:
            if package.status != "Delivered":
                distance = self.distanceBetweenAddresses(fromAddress, package.packageAddress)
                if distance is not None and distance < minDistance:
                    minDistance = distance
                    minPackage = package
        return minPackage, minDistance

# Method to add package to the truck per the packages[].
# Ensures only 16 package objects get loaded per truck and
# assigned a status.
# Big O -- > O(1)
    def addPackage(self, package):
        if len(self.packages) < 16:
            self.packages.append(package)
            package.status = "Out for Delivery"
            return True
        else:
            print("Toomany packages on the truck.")
        return False

# Method to iterate though the packages left to deliver.
# calls minDistanceFrom method to determine the package
# with the shortest distance from our current address.
# If we have a package to deliver then we determine travel
# time, travel distance, update the status to delivered,
# assign the delivery time to that package, and updates
# the Truck's current address.
# Big O --> Depends on two separate factors; packages and addresses
# I believe the time space complexity is O(p^2 * n) where 'p' is
# the number of packages.
    def deliverPackages(self, hTable):
        while any(package.status != "Delivered" for package in self.packages):
            nextPackage, distance = self.minDistanceFrom(self.currentAddress)
            if nextPackage:
                travelTime = datetime.timedelta(hours = distance / 18)
                self.currentTime += travelTime
                self.totalMiles += distance
                nextPackage.status = "Delivered"
                nextPackage.deliveryTime = self.currentTime
                self.currentAddress = nextPackage.packageAddress
                hTable.insert(nextPackage.packageID, nextPackage)