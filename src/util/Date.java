package util;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    // Constants for date validation
    private static final int[] DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final int FEBRUARY = 2;
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;

    // Constructor
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // Getters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    // Check if the date is a valid calendar date
    public boolean isValid() {
        // Check if month is valid
        if (month < 1 || month > 12) {
            return false;
        }

        // Check for leap year
        boolean isLeapYear = (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0);

        // Get the number of days in the given month
        int daysInMonth = (month == FEBRUARY && isLeapYear) ? 29 : DAYS_IN_MONTH[month - 1];

        // Check if the day is valid for the given month
        return day >= 1 && day <= daysInMonth;
    }

    // Override equals() method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date other = (Date) obj;
        return year == other.year && month == other.month && day == other.day;
    }

    // Override compareTo() method for comparing dates
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;  // Compare by year
        }
        if (this.month != other.month) {
            return this.month - other.month;  // Compare by month
        }
        return this.day - other.day;  // Compare by day
    }

    // Override toString() method for string representation of the date
    @Override
    public String toString() {
        return String.format("%d/%d/%04d", month, day, year);
    }


    public Date getCurrentDate() {
            // Get the current date and time
        Calendar calendar = Calendar.getInstance();

            // Extract individual components of the date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: Months are zero-indexed
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date currentDate = new Date(year, month, day);
        return currentDate;

    }
    public boolean isBeforeToday() {
        Date currentDate = getCurrentDate();

        // Compare the current date with the given date
        return this.compareTo(currentDate) <= 0;
    }
    public boolean isAfterToday() {
        Date currentDate = getCurrentDate();

        // Compare the current date with the given date
        return this.compareTo(currentDate) >= 0;
    }
    public boolean isWithinSixMonths(Date targetDate) {

        Date currentDate = getCurrentDate();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentDate.getYear());
        calendar.set(Calendar.MONTH, currentDate.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, currentDate.getDay());

        calendar.add(Calendar.MONTH, 6);


        Date sixMonthsLater = new Date(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));


        return targetDate.compareTo(currentDate) >= 0 && targetDate.compareTo(sixMonthsLater) <= 0;
    }

    public boolean isWeekend(Date date) {
        // Step 1: Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Step 2: Set the calendar to the given date
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth() - 1);  // Months are 0-indexed in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());

        // Step 3: Get the day of the week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Step 4: Check if the day is Saturday or Sunday
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }


}
