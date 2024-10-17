package clinic;
import util.Date;

public class Patient extends Person {
    private Visit visits;      // Head of the linked list of visits (completed appointments)

    /**
     *
     * @param profile
     */
    public Patient(Profile profile) {
        super(profile);
        this.visits = null;  // Initially, no visits are completed
    }
    public Patient(Profile profile, Visit visits) {
        super(profile);
        this.visits = visits;  // Initially, no visits are completed
    }

    /**
     *
     * @return
     */
    public Visit getVisits() {
        return visits;
    }

    /**
     *
     * @param appointment
     */
    public void addVisit(Appointment appointment) {
        Visit newVisit = new Visit(appointment);
        newVisit.setNext(visits);  // Insert the new visit at the beginning of the linked list
        visits = newVisit;         // Update the head of the list
    }
    public void deleteVisit(Appointment appointment) {
        if (visits == null) {
            return; // No visits to delete
        }

        // If the visit to delete is the head of the list
        if (visits.getAppointment().equals(appointment)) {
            visits = visits.getNext();
            return;
        }

        // Traverse the list to find and delete the visit
        Visit current = visits;
        while (current.getNext() != null) {
            if (current.getNext().getAppointment().equals(appointment)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    // Override equals() method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient other = (Patient) obj;
        return profile.equals(other.profile);  // Patients are considered equal if their profiles match
    }


    // Override toString() method to provide a readable representation of the patient
    @Override
    public String toString() {
        return profile.toString();  // The profile's toString() method handles the representation
    }

}
