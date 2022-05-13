package ericbutlervetclinic;

/** Below is my Dog class and variables
*/
public class Dog {

    private String dogName;
    private String dogOwner;
    private String dogBreed;
    private double dogWeight;
    static int numDogs = 0;

/** Below is my default constructor that takes zero parameters
*/ 
    public Dog() {
        dogName = "";
        dogOwner = "";
        dogBreed = "";
        dogWeight = 0.0;
        numDogs++;
    }

/** Below is my initializing constructor
*/
    public Dog(String dogName, String dogOwner, String dogBreed, double dogWeight) {
        setDogName(dogName);
        setDogOwner(dogOwner);
        setDogBreed(dogBreed);
        setDogWeight(dogWeight);
        numDogs++;
    }

/** Below is my setter for dogName
*/
    final public void setDogName(String dogName) {
        this.dogName = dogName;
    }

/** Below is the getter for dogName
*/
    public String getDogName() {
        return dogName;
    }

/** Below is the setter for dogOwner
*/
    final public void setDogOwner(String dogOwner) {
        this.dogOwner = dogOwner;
    }

/** Below is the getter for dogOwner
*/
    public String getDogOwner() {
        return dogOwner;
    }

/** Below is the setter for dogBreed
*/
    final public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

/** Below is the getter for dogBreed
*/
    public String getDogBreed() {
        return dogBreed;
    }

/** Below is the setter for dogWeight
*/
    final public void setDogWeight(double dogWeight) {        
        this.dogWeight = dogWeight;
    }

/** Below is the getter for dogWeight
*/
    public double getDogWeight() {
        return dogWeight;
    }

/** Below is the toString method that displays the dog's profile in the output 
 * text field when a new dog is created or when the heaviest dog is called.
*/
    @Override
    public String toString() {
        return String.format("%s(%s), %.2f lbs owned by %s.", this.dogName, this.dogBreed, this.dogWeight, this.dogOwner);
    }
    
/** Below is my  method to keep track of how many dogs have been created
*/
    public static int numDogs() {
        return Dog.numDogs;
    }
    
/** Below is the method that figures out which dog is the heaviest in the array
*/
    public static Dog getHeaviest(Dog[] d) {       
       Dog h;
       h = d[0];       
        for (int i = 0; i < Dog.numDogs; i++) {
            if (d[i].getDogWeight() > h.getDogWeight()) 
                h = d[i];                 
        }       
        return h;
    }
}

