package util;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateTest {

    @Test
    public void testLeapYearDay() {
        Date leapYear = new Date(2020, 2, 29);
        assertTrue(leapYear.isValid());
        Date nonLeapYear = new Date(2021, 2, 29);
        assertFalse(nonLeapYear.isValid());
    }

    @Test
    public void testInvalidMonth() {
        Date invalidMonth = new Date(2020, 13, 1);
        assertFalse(invalidMonth.isValid());
    }

    @Test
    public void testDayValidity() {
        Date invalidDay1 = new Date(2024, 9, 31);
        assertFalse(invalidDay1.isValid());
        Date invalidDay2 = new Date(2024, 4, 0);
        assertFalse(invalidDay2.isValid());
        Date validDay = new Date(2024, 10, 31);
        assertTrue(validDay.isValid());
    }



}