package util;

import org.junit.Test;
import util.List;
import clinic.Doctor;
import clinic.Profile;
import clinic.Provider;
import clinic.Technician;
import clinic.Location;
import clinic.Specialty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ListTest {

    @Test
    public void testAddTechnician() {
        List<Provider> providers = new List<>();
        Technician technician = new Technician(new Profile("Jane", "Doe", new Date(1990, 8, 10)), Location.BRIDGEWATER,150);

        providers.add(technician);

        assertEquals(1, providers.size());
        assertTrue(providers.contains(technician));
    }
    @Test
    public void testAddDoctor() {
        List<Provider> providers = new List<>();
        Doctor doctor = new Doctor(new Profile("John", "Doe", new Date(1980, 5, 15)), Location.CLARK, Specialty.FAMILY, "56");

        providers.add(doctor);

        assertEquals(1, providers.size());
        assertTrue(providers.contains(doctor));
    }

    @Test
    public void testRemoveDoctor() {
        List<Provider> providers = new List<>();
        Doctor doctor = new Doctor(new Profile("John", "Doe", new Date(1980, 5, 15)), Location.CLARK, Specialty.FAMILY, "56");

        providers.add(doctor);
        providers.remove(doctor);

        assertEquals(0, providers.size());
        assertFalse(providers.contains(doctor));
    }

    @Test
    public void testRemoveTechnician() {
        List<Provider> providers = new List<>();
        Technician technician = new Technician(new Profile("Jane", "Doe", new Date(1990, 8, 10)), Location.CLARK,200);

        providers.add(technician);
        providers.remove(technician);

        assertEquals(0, providers.size());
        assertFalse(providers.contains(technician));
    }
}