package util;

public class Timeslot implements Comparable<Timeslot> {
    private int hour;   // Hour in 24-hour format (0 - 23)
    private int minute; // Minute (0 - 59)

    // Constructor to initialize the timeslot with hour and minute
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }


    // Getters for hour and minute
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // Convert to 12-hour format and return as a string with AM/PM
    public String formatTime() {
        int displayHour = (hour % 12 == 0) ? 12 : hour % 12;  // Convert to 12-hour format
        String amPm = (hour >= 12) ? "PM" : "AM";
        return String.format("%d:%02d %s", displayHour, minute, amPm);  // Format as HH:MM AM/PM
    }
    public static int getSlotNumber(Timeslot timeslot){
        if (timeslot.equals(SLOT1)){
            return 1;
        } else if (timeslot.equals(SLOT2)){
            return 2;
        } else if (timeslot.equals(SLOT3)){
            return 3;
        } else if (timeslot.equals(SLOT4)){
            return 4;
        } else if (timeslot.equals(SLOT5)){
            return 5;
        } else if (timeslot.equals(SLOT6)){
            return 6;
        } else if (timeslot.equals(SLOT7)){
            return 7;
        } else if (timeslot.equals(SLOT8)){
            return 8;
        } else if (timeslot.equals(SLOT9)){
            return 9;
        } else if (timeslot.equals(SLOT10)){
            return 10;
        } else if (timeslot.equals(SLOT11)){
            return 11;
        } else if (timeslot.equals(SLOT12)){
            return 12;
        } else {
            return 0;
        }
    }
    // Override toString to use the formatted time
    @Override
    public String toString() {
        return formatTime();  // Return formatted time as default string representation
    }

    // Static factory methods for the 12 timeslots
    public static final Timeslot SLOT1 = new Timeslot(9, 0);     // 9:00 AM
    public static final Timeslot SLOT2 = new Timeslot(9, 30);    // 9:30 AM
    public static final Timeslot SLOT3 = new Timeslot(10, 0);    // 10:00 AM
    public static final Timeslot SLOT4 = new Timeslot(10, 30);   // 10:30 AM
    public static final Timeslot SLOT5 = new Timeslot(11, 0);    // 11:00 AM
    public static final Timeslot SLOT6 = new Timeslot(11, 30);   // 11:30 AM
    public static final Timeslot SLOT7 = new Timeslot(14, 0);    // 2:00 PM
    public static final Timeslot SLOT8 = new Timeslot(14, 30);   // 2:30 PM
    public static final Timeslot SLOT9 = new Timeslot(15, 0);    // 3:00 PM
    public static final Timeslot SLOT10 = new Timeslot(15, 30);  // 3:30 PM
    public static final Timeslot SLOT11 = new Timeslot(16, 0);   // 4:00 PM
    public static final Timeslot SLOT12 = new Timeslot(16, 30);  // 4:30 PM


    // Implement compareTo method for comparing timeslots
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return Integer.compare(this.hour, other.hour);  // Compare hours first
        }
        else {
            return Integer.compare(this.minute, other.minute);  // Compare minutes if hours are the same
        }
    }

    // Optional: Override equals and hashCode for comparison purposes
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Timeslot timeslot = (Timeslot) obj;
        return hour == timeslot.hour && minute == timeslot.minute;
    }

}
