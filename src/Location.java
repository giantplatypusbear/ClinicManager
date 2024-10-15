
public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    private final String county;
    private final String zip;


    // Constructor
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    public String getCity(){
        return name();
    }
    // Getters
    public String getCounty() {
        return county;
    }

    public String getZip() {
        return zip;
    }

    public static Location[] getAllLocations() {
        return Location.values();
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s", name(), county, zip);
    }
}
