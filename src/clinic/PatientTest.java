package clinic;
import org.junit.Test;
import util.Date;
import static org.junit.Assert.assertEquals;

public class PatientTest {

    @Test
    public void testCompareToReturnsNegativeOne() {
        // Test cases for when first profile < second profile
        Profile profile1 = new Profile("Alice", "Doe", new Date(1980, 5, 15));
        Profile profile2 = new Profile("John", "Smith", new Date(1990, 5, 15));

        Profile profile3 = new Profile("Carrie", "Bradshaw", new Date(1981, 3, 14));
        Profile profile4 = new Profile("Jack", "Hungry", new Date(1990, 2, 13));

        Profile profile5 = new Profile("Betty", "Boop", new Date(1970, 5, 15));
        Profile profile6 = new Profile("Gertrude", "Hill", new Date(1990, 6, 5));

        assertEquals(-1, profile1.compareTo(profile2));
        assertEquals(-1, profile3.compareTo(profile4));
        assertEquals(-1, profile5.compareTo(profile6));
    }

    @Test
    public void testCompareToReturnsOne() {
        // Test cases for when first profile > second profile
        Profile profile1 = new Profile("John", "Smith", new Date(1990, 5, 15));
        Profile profile2 = new Profile("Alice", "Doe", new Date(1980, 5, 15));

        Profile profile3 = new Profile("Jack", "Hungry", new Date(1990, 5, 15));
        Profile profile4 = new Profile("Carrie", "Bradshaw", new Date(1980, 5, 15));

        Profile profile5 = new Profile("Gertrude", "Hill", new Date(1990, 6, 5));
        Profile profile6 = new Profile("Betty", "Boop", new Date(1970, 5, 15));


        assertEquals(1, profile1.compareTo(profile2));
        assertEquals(1, profile3.compareTo(profile4));
        assertEquals(1, profile5.compareTo(profile6));
    }

    @Test
    public void testCompareToReturnsZero() {
        // Test case where both profiles are equal
        Profile profile1 = new Profile("John", "Doe", new Date(1985, 1, 1));
        Profile profile2 = new Profile("John", "Doe", new Date(1985, 1, 1));

        assertEquals(0, profile1.compareTo(profile2));
    }
}