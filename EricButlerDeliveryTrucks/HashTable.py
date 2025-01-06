
class HashTable:
    # Code from C950 - Webinar-1 - Let’s Go Hashing
    # set capacity to 40, in line with the number of packages
    # assigns all buckets with an empty list
    # Big O --> O(n)
    def __init__(self, buckets=40):
        # initialize the hash table with empty bucket list entries
        self.table = []
        for i in range(buckets):
            self.table.append([])

    # inserts a new item into the hash table
    # does both insert and update
    # Big O --> O(1)
    def insert(self, key, item):
        bucket = int(key) % len(self.table)
        bucketList = self.table[bucket]
        for kv in bucketList:
            if kv[0] == key:
                kv[1] = item
                return True
        # Add new element to the end of the list
        keyValue = [key, item]
        bucketList.append(keyValue)
        return True

    # Finds a package based on key value
    # Returns associated package if it exists
    # Big O --> O(1)
    def search(self, key):
        bucket = int(key) % len(self.table)
        bucketList = self.table[bucket]
        for kv in bucketList:
            if kv[0] == key:
                return kv[1]
        return None

    # remove function
    # removes an item with matching key from the hash table
    # Big O --> O(1)
    def remove(self, key):
        bucket = int(key) % len(self.table)
        bucketList = self.table[bucket]
        for kv in bucketList:
            if kv[0] == key:
                bucketList.remove([kv[0], kv[1]])

