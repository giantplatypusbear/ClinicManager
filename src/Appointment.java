

public class Appointment implements Comparable<Appointment> {

    protected Date date;
    protected Timeslot timeslot;
    protected Person patient;
    protected Person provider;

    // Constructor
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    // Getters
    public Date getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Person getPatient() {
        return patient;
    }

    public Person getProvider() {
        return provider;
    }

    // Override equals() method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Appointment other = (Appointment) obj;
        return date.equals(other.date) &&
                timeslot.equals(other.timeslot) &&
                patient.equals(other.patient);
    }

    // Override compareTo() method for sorting
    @Override
    public int compareTo(Appointment other) {
        // First, compare by date
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) return dateComparison;

        // If dates are the same, compare by timeslot
        int timeslotComparison = this.timeslot.compareTo(other.timeslot);
        if (timeslotComparison != 0) return timeslotComparison;

        // If both date and timeslot are the same, compare by patient
        return this.patient.compareTo(other.patient);
    }
    public static String forTimeslot(Timeslot timeslot) {
        int hour = timeslot.getHour();
        int minute = timeslot.getMinute();
        String amPm = (hour >= 12) ? "PM" : "AM";
        if (hour > 12) {
            hour -= 12;  // Convert to 12-hour format
        }
        return String.format("%d:%02d %s", hour, minute, amPm);
    }
    // Override toString() method using inherited toString() methods from Person
    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                date,
                forTimeslot(timeslot),
                patient.getProfile().toString(),
                ((Provider) provider).toString()

        );
    }

    public static void main(String[] args) {
        // Create a profile for a patient
        Profile patientProfile = new Profile("Alice", "Smith", new Date(2000, 1, 1));
        Patient patient = new Patient(patientProfile, null);

        // Create a profile for a provider
        Profile providerProfile = new Profile("Dr. John", "Doe", new Date(1970, 1, 1));;
        Provider provider = new Technician(providerProfile, Location.CLARK,20);

        // Create an appointment
        Date date = new Date(2022, 1, 1);
        Appointment appointment = new Imaging(date, Timeslot.SLOT1, patient, provider, Radiology.XRAY);

        // Print the appointment
        System.out.println(appointment);
    }
}