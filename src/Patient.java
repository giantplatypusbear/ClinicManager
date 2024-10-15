

public class Patient extends Person {
    private Visit visits;      // Head of the linked list of visits (completed appointments)

    // Constructor
    public Patient(Profile profile, Visit visits) {
        super(profile);
        this.visits = null;  // Initially, no visits are completed
    }



    // Add a visit to the linked list of completed visits
    public void addVisit(Appointment appointment) {
        Visit newVisit = new Visit(appointment);
        newVisit.setNext(visits);  // Insert the new visit at the beginning of the linked list
        visits = newVisit;         // Update the head of the list
    }

    // Calculate the total charge based on completed visits
//    public int charge() {
//        int totalCharge = 0;
//        Visit current = visits;

        // Traverse the linked list of visits and sum up the charges
//        while (current != null) {
//            totalCharge += current.getAppointment().getProvider().getSpecialty().getCharge();
//            current = current.getNext();  // Move to the next visit in the list
//        }
//
//        return totalCharge;
// }

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
