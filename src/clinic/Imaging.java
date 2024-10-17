package clinic;

import util.Date;
import util.Timeslot;

public class Imaging extends Appointment {
    private Radiology room;
    // Constructor for InheritedAndRelated.Imaging class
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology radiologyService) {
        super(date, timeslot, patient, provider);  // Call superclass constructor
        this.room = radiologyService;  // Initialize radiology service
    }

    // Getter for the radiology service
    public Radiology getRadiologyService() {
        return room;
    }

    // Override the toString method to include radiology service details
    @Override
    public String toString() {
        // Use the parent class's toString method for date, timeslot, patient, and provider
        return String.format("%s%s", super.toString(), room.toString());
    }
}