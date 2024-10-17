package clinic;

public class Visit {
    // Fields
    private final Appointment appointment;  // The appointment for this visit
    private Visit next;               // Reference to the next visit in the list

    // Constructor
    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null; // Initialize the next visit as null (end of the list)
    }

    // Getter for appointment
    public Appointment getAppointment() {
        return appointment;
    }

    // Getter and Setter for next InheritedAndRelated.Visit
    public Visit getNext() {
        return next;
    }

    public void setNext(Visit next) {
        this.next = next;
    }

    // Override toString() method for better representation of the visit
    @Override
    public String toString() {
        return appointment.toString();
    }
}
