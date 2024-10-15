

public abstract class Provider extends Person {

    private Location location;

    // Constructor
    public Provider(Profile person, Location location) {
        super(person);
        this.location = location;
    }

    // Getters
    public Location getLocation() {
        return location;
    }


    public abstract int rate();

    @Override
    public String toString() {
        return String.format("[%s, %s]",
                getProfile(),
                location);
    }
}
