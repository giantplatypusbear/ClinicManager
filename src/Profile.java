

public class Profile implements Comparable<Profile> {
    private String fname;  // First name
    private String lname;  // Last name
    private Date dob;      // Date of birth

    // Constructor
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    // Getters
    public String getFirstName() {
        return fname;
    }

    public String getLastName() {
        return lname;
    }

    public Date getDateOfBirth() {
        return dob;
    }

    // Override equals() method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile other = (Profile) obj;
        return fname.equalsIgnoreCase(other.fname) &&
                lname.equalsIgnoreCase(other.lname) &&
                dob.equals(other.dob);
    }

    // Override compareTo() method for sorting profiles
    @Override
    public int compareTo(Profile other) {
        // Compare by last name (case insensitive)
        int lnameComparison = this.lname.compareToIgnoreCase(other.lname);
        if (lnameComparison != 0) {
            return lnameComparison;
        }

        // If last names are the same, compare by first name (case insensitive)
        int fnameComparison = this.fname.compareToIgnoreCase(other.fname);
        if (fnameComparison != 0) {
            return fnameComparison;
        }

        // If both names are the same, compare by date of birth
        return this.dob.compareTo(other.dob);
    }

    // Override toString() method for string representation of the profile
    @Override
    public String toString() {
        return String.format("%s %s %s", fname, lname, dob.toString());
    }

}
