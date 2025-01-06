import csv
from HashTable import *


# Constructor to initialize variables and their values.
# Big O --> O(1)
class Packages:
    def __init__(self, packageID, packageAddress, packageCity, packageState, packageZip, packageDeadline, packageWeight,
                 packageNote, packageStatus):
        self.packageID = packageID
        self.packageAddress = packageAddress
        self.packageCity = packageCity
        self.packageState = packageState
        self.packageZip = packageZip
        self.packageDeadline = packageDeadline
        self.packageWeight = packageWeight
        self.packageNote = packageNote
        self.packageStatus = packageStatus
        self.truckID = None
        self.packageDepartTime = None
        self.packageDeliveryTime = None

    # Method to extract and load the data from the package file provided.
    # Assigned the different CSV to different array indexes and adds to the
    # Hash Table we created.
    # Big O --> O(n)
    @staticmethod
    def loadPackageData(filename, packageHTable):
        with open(filename, mode='r') as file:
            csv_reader = csv.reader(file)
            for row in csv_reader:
                packageID = row[0]
                packageAddress = row[1]
                packageCity = row[2]
                packageState = row[3]
                packageZip = row[4]
                packageDeadline = row[5]
                packageWeight = row[6]
                packageNote = row[7]
                packageStatus = "At WGUPS"
                package = Packages(packageID, packageAddress, packageCity, packageState, packageZip, packageDeadline,
                                   packageWeight, packageNote, packageStatus)
                packageHTable.insert(packageID, package)

    def getCurrentStatus(self, convert_timedelta):
        # Ensure delivery and depart times are properly set before making comparisons
        if self.packageDeliveryTime is not None and convert_timedelta >= self.packageDeliveryTime:
            self.packageStatus = "Delivered"
        elif self.packageDepartTime is not None and convert_timedelta >= self.packageDepartTime:
            self.packageStatus = "In Transit"
        else:
            self.packageStatus = "At WGUPS Hub"