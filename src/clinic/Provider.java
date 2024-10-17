package clinic;

public abstract class Provider extends Person {

    private Location location;
    private int numVisits;

    // Constructor
    public Provider(Profile person, Location location) {
        super(person);
        this.location = location;
        this.numVisits = 0;
    }

    // Getters
    public Location getLocation() {
        return location;
    }


    public int getNumVisits() {
        return numVisits;
    }

    public void addVisit() {
        numVisits++;
    }
    public void cancelVisit() {
        numVisits--;
    }

    public abstract int rate();

    @Override
    public String toString() {
        return String.format("[%s, %s]",
                getProfile(),
                location);
    }
}
