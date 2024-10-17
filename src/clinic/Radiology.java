package clinic;

public enum Radiology {
    XRAY,
    ULTRASOUND,
    CATSCAN;


    public String getRoom() {
        return this.name();
    }


    // Override toString to return a formatted string
    @Override
    public String toString() {
        return String.format("[%s]", getRoom());
    }
}
