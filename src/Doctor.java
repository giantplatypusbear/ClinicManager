public class Doctor extends Provider {
    private Specialty specialty;
    private String npi;

    // Constructor
    public Doctor(Profile profile, Location location, Specialty specialty, String npi) {
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    // Getters
    public Specialty getSpecialty() {
        return specialty;
    }

    public String getNPI() {
        return npi;
    }
    // Calculate the charge for a visit with this doctor
    @Override
    public int rate() {
        return specialty.getCharge();
    }

    // Override toString() method for string representation of the doctor
    @Override
    public String toString() {
        return String.format("%s[%s, #%s]",
                super.toString(),
                getSpecialty(),
                getNPI()
                );
    }
}
