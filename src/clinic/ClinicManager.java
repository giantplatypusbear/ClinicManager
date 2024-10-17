import Clinic.*;
import util.*;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClinicManager {
    private List<Patient> patients;
    private List<Provider> providers;
    private List<Appointment> appointments;
    private List<Technician> technicians;
    private List<Doctor> doctors;
    private List<Imaging> imagingList;
    private List<Appointment> officeAppointments;
    Iterator<Technician> techIterator;





    public ClinicManager() {
        patients = new List<>();
        providers = new List<>();
        appointments = new List<>();
        technicians = new List<>();
        doctors = new List<>();
        imagingList = new List<>();
        techIterator= technicians.iterator();
        officeAppointments = new List<>();


    }

    private void readProvidersFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        readProviders(scanner);
        scanner.close();
    }

    private void readProviders(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line);

            char docOrTech = tokenizer.nextToken().charAt(0); // 'D' or 'T'
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            String dobString = tokenizer.nextToken(); // util.Date of Birth
            String city = tokenizer.nextToken();
            if (docOrTech == 'D') {
                String specialty = tokenizer.nextToken();
                String npi = tokenizer.nextToken();
                Profile prof = new Profile(firstName, lastName, parseDate(dobString));
                Doctor doc = new Doctor(prof, matchLocation(city), matchSpecialty(specialty), npi);
                providers.add(doc);
                doctors.add(doc);
            } else {
                int rate = Integer.parseInt(tokenizer.nextToken());
                Profile prof = new Profile(firstName, lastName, parseDate(dobString));
                Technician tech = new Technician(prof, matchLocation(city), rate);
                providers.add(tech);
                technicians.add(tech);
                reverseRotationList();
            }
        }
    }

    public Location matchLocation(String city) {
        for (Location location : Location.values()) {
            if (location.getCity().equalsIgnoreCase(city)) {
                return location;
            }
        }
        return null;
    }

    public Specialty matchSpecialty(String special) {
        for (Specialty specialty : Specialty.values()) {
            if (specialty.theSpecialty().equalsIgnoreCase(special)) {
                return specialty;
            }
        }
        return null;
    }

    private Date parseDate(String dateString) {
        String[] parts = dateString.split("/");

        int month = Integer.parseInt(parts[0].trim());
        int day = Integer.parseInt(parts[1].trim());
        int year = Integer.parseInt(parts[2].trim());

        return new Date(year, month, day);
    }

    public void printSortedProviders() {
        List<Provider> sortedProviders = new List<>();

        for (int i = 0; i < providers.size(); i++) {
            sortedProviders.add(providers.get(i));
        }
        Sort.sortProvidersByLastName(sortedProviders);
        System.out.println("Providers loaded to the list.");
        for (int i=0; i<providers.size(); i++) {
            System.out.println(sortedProviders.get(i).toString());
        }
    }

    public void printRotationList(){
        System.out.println("\nRotation list for technicians");
        int size= technicians.size();

        for(int i= 0; i<size; i++){

            System.out.print(String.format("%s %s (%s)  --> ",
                    technicians.get(i).getProfile().getFirstName(),
                    technicians.get(i).getProfile().getLastName(),
                    technicians.get(i).getLocation().getCity()
            ));

        }
            System.out.print(String.format("%s %s (%s)",
                    technicians.get(size-1).getProfile().getFirstName(),
                    technicians.get(size-1).getProfile().getLastName(),
                    technicians.get(size-1).getLocation().getCity()
            ));

    }
    private void handleCommand(String input) {
        String[] tokens = input.split(",");
        String command = tokens[0].trim();
        switch (command) {
            case "D":
                if (tokens.length < 7) {
                    System.out.println("Missing data tokens.");
                    return;}
                scheduleDoctorAppointment(tokens);
                break;
            case "T":
                if (tokens.length < 7) {
                    System.out.println("Missing data tokens.");
                    return;}
                scheduleImagingAppointment(tokens);
                break;
            case "C":
                if (tokens.length < 6) {
                    System.out.println("Missing data tokens.");
                    return;}
                cancelAppointment(tokens);
                break;
            case "R":
            if (tokens.length < 7) {
                System.out.println("Missing data tokens.");
                return;}
                rescheduleAppointment(tokens);
                break;
            case "PA":
                Sort.sortByAppointment(appointments);
                printLists();
                break;
            case "PP":
                Sort.sortByPatient(appointments);
                printLists();
                break;
            case "PL":
                Sort.sortByLocation(appointments);
                printLists();
                break;
            case "PO":
              //  util.Sort.sortByOffice();
                printLists();
                break;
            case "PI":
              //  util.Sort.sortByRadiology(imagingList);
                printLists();
                break;
//            case "PC":
//                printCredits();
//                break;
//            case "PS":
//                printBillingStatements();
//                appointmentList.empty();
//                break;
            default:
                System.out.println("Invalid command!");
        }
    }
    private Doctor findDoctorByNpi(String npi) {
    for (int i=0; i<doctors.size(); i++) {
        if (doctors.get(i).getNPI().equals(npi)) {
            return doctors.get(i);
        }
    }
    return null;
    }
    private void scheduleDoctorAppointment(String[] tokens) {
        Date date = parseDate(tokens[1].trim());
        Timeslot timeslot = parseTimeslot(tokens[2].trim());
        String stringTimeslot = tokens[2].trim();
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date dob = parseDate(tokens[5].trim());
        String npi = tokens[6].trim();

        if (!(checkAppDate(date) && checkDOB(dob) && validateTimeslot(timeslot, stringTimeslot))){
            return;
        }
        Profile patientProfile = new Profile(patientFirstName, patientLastName, dob);
        Provider doctor= findDoctorByNpi(npi);
        Person patient = new Person(patientProfile);
        if(!doesProviderExist(npi)){
            return;
        };
        if(hasPatientConflict(patient, date, timeslot)){
            System.out.println(patient.toString()+ " has an existing appointment at the same time slot.");
        }
       if(!hasPatientConflict(patient, date, timeslot)&& !hasProviderConflict(doctor, date, timeslot)){
            Appointment appointment= new Appointment(date, timeslot, patient, doctor);
            appointments.add(appointment);
            officeAppointments.add(appointment);
            System.out.println(appointment.toString()+ " booked.");
            }
    }
    private void scheduleImagingAppointment(String[] tokens) {
        Date date = parseDate(tokens[1].trim());
        Timeslot timeslot = parseTimeslot(tokens[2].trim());
        String stringTimeslot = tokens[2].trim();
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date dob = parseDate(tokens[5].trim());
        String radInput = tokens[6].trim();
        Radiology room = parseRadiology(tokens[6].trim());

        if (room == null) {
            System.out.println(radInput+" - imaging service not provided.");
            return;
        }
        if(!(checkAppDate(date) && checkDOB(dob) && validateTimeslot(timeslot, stringTimeslot))){
            return;
            }
        Technician technician = null;
        Profile patientProfile = new Profile(patientFirstName, patientLastName, dob);
        Person patient = new Person(patientProfile);

        if(!hasPatientConflict(patient, date, timeslot)){
             technician = assignTechnician(date, timeslot, room);
        }
        else{
            System.out.println(patient.toString()+ " has an existing appointment at the same time slot.");

        }

        if(technician == null){
            System.out.println("Cannot find an available technician at all locations for" + room +
                    " at slot " +
                    stringTimeslot + ".");
            return;
        }


            Imaging imagingApt = new Imaging(date, timeslot, patient,technician,room);
            appointments.add(imagingApt);
            imagingList.add(imagingApt);
            System.out.println(imagingApt.toString()+ " booked.");
    }
    private void cancelAppointment(String[] tokens) {
        String dateInput = tokens[1].trim();
        String timeslotInput = tokens[2].trim();
        String firstName = tokens[3].trim();
        String lastName = tokens[4].trim();
        String dobInput = tokens[5].trim();

        Date appointmentDate = parseDate(dateInput);
        Date dob = parseDate(dobInput);
        Timeslot timeslot = parseTimeslot(timeslotInput);
        Profile patientProfile = new Profile(firstName, lastName, dob);
        Person patient = new Person(patientProfile);

        for(int i=0; appointments.size()>i; i++) {
            if (appointments.get(i).getDate().equals(appointmentDate) && appointments.get(i).getTimeslot().equals(timeslot) &&
                    appointments.get(i).getPatient().equals(patient)) {
                appointments.remove(appointments.get(i));
                System.out.println(String.format("%s %s %s %s %s - appointment has been cancelled.",
                        dateInput,
                        timeslot.formatTime(),
                        firstName,
                        lastName,
                        dobInput));
                return;
            }

        }
        System.out.println(String.format("%s %s %s %s %s - appointment does not exist.",
                dateInput,
                timeslot.formatTime(),
                firstName,
                lastName,
                dobInput));
    }
    public void rescheduleAppointment(String[] sInput) {
        String dateInput = sInput[1].trim();
        String originalTimeslotInput = sInput[2].trim();
        String firstName = sInput[3].trim();
        String lastName = sInput[4].trim();
        String dobInput = sInput[5].trim();
        String newTimeslotInput = sInput[6].trim();

        Date appointmentDate = parseDate(dateInput);
        Timeslot originalTimeslot = parseTimeslot(originalTimeslotInput);
        Timeslot newTimeslot = parseTimeslot(newTimeslotInput);
        Date dob = parseDate(dobInput);
        Profile patientProfile = new Profile(firstName, lastName, dob);
        Person patient = new Person(patientProfile);

        for(int i=0; appointments.size()>i; i++) {
            if (appointments.get(i).getDate().equals(appointmentDate) && appointments.get(i).getTimeslot().equals(originalTimeslot) && appointments.get(i).getPatient().equals(patient)) {
                Person doctor = appointments.get(i).getProvider();
                if(hasPatientConflict(patient, appointmentDate, newTimeslot)){
                    System.out.println(String.format("%s has an existing appointment at %s %s.",
                        patient.toString(),
                        dateInput,
                        newTimeslot.formatTime()));
                    return;
                }
                else if(hasProviderConflict((Provider)doctor, appointmentDate, newTimeslot)){
                    return;
                }
               else{
                    appointments.remove(appointments.get(i));
                    Appointment appointment= new Appointment(appointmentDate, newTimeslot, patient, doctor);
                    appointments.add(appointment);
                    System.out.println("Rescheduled to " +appointment.toString());
                    return;
                }

            }
        }
        System.out.println(String.format("%s %s %s does not exist.",
                dateInput,
                originalTimeslot.formatTime(),
                patient.toString()));
    }

    public void printLists() {
        if (appointments.size() == 0) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        for(int i=0; appointments.size()>i; i++){
            System.out.println(appointments.get(i).toString());
        }

        System.out.println("** end of list **\n");
    }

    private boolean hasPatientConflict(Person patient, Date date, Timeslot time) {
        if(appointments.isEmpty()){
            return false;
        }
        for (int i=0; appointments.size()>i; i++) {
            if (appointments.get(i).getPatient().equals(patient) && appointments.get(i).getDate().equals(date) &&
                    appointments.get(i).getTimeslot().equals(time)) {
                return true;
            }
        }
        return false;
    }

    // Assign a technician from the reverse rotation list
    private Technician assignTechnician(Date date, Timeslot timeslot, Radiology room) {
        int i=0;
        while (i<technicians.size()) {
            Technician tech = techIterator.next();
            if (!hasTechnicianConflict(tech, date, timeslot) &&
                    isRoomAvailable(tech.getLocation(), room, date, timeslot)) {
                return tech;
            }
            i++;
        }
        return null;
    }


    // Check for technician conflict (if the technician is already booked at the same time)
    private boolean hasProviderConflict(Provider provider, Date date, Timeslot time) {
        if(appointments.isEmpty()){
            return false;
        }
        for (int i=0; appointments.size()>i; i++) {
            if (appointments.get(i).getProvider().equals(provider) && appointments.get(i).getDate().equals(date) &&
                    appointments.get(i).getTimeslot().equals(time)) {
                System.out.println(provider.toString()+ " is not available at slot "+ Timeslot.getSlotNumber(time));
                return true; // InheritedAndRelated.Technician is already booked
            }
        }
        return false; // InheritedAndRelated.Technician is available
    }
    private boolean hasTechnicianConflict(Provider provider, Date date, Timeslot time) {
        for (int i=0; imagingList.size()>i; i++) {
            if (imagingList.get(i).getProvider().equals(provider) && imagingList.get(i).getDate().equals(date) &&
                    imagingList.get(i).getTimeslot().equals(time)) {
                return true; // InheritedAndRelated.Technician is already booked
            }
        }
        return false; // InheritedAndRelated.Technician is available
    }

    // Check if the room is available at the technician's location for the imaging type
    private boolean isRoomAvailable(Location location, Radiology room, Date date, Timeslot timeslot) {
        for (int i=0; imagingList.size()>i; i++) {
            Technician technician = (Technician) imagingList.get(i).getProvider();
            Location aptLocation = technician.getLocation();
            if (aptLocation.equals(location) && imagingList.get(i).getRadiologyService().equals(room) &&
                    imagingList.get(i).getDate().equals(date) && imagingList.get(i).getTimeslot().equals(timeslot)) {
                return false; // The room for this imaging type at this location is booked
            }
        }
        return true; // Room is available
    }


    private Radiology parseRadiology(String trim) {
        for (Radiology radiology : Radiology.values()) {
            if (radiology.getRoom().equalsIgnoreCase(trim)) {
                return radiology;
            }
        }
        return null;
    }
    private boolean doesProviderExist(String npi){
        findDoctorByNpi(npi);
        if(findDoctorByNpi(npi)==null){
            System.out.println(npi+ " - provider doesn't exist.");
            return false;
        }
        return true;
    }
    public boolean checkAppDate(Date date){
        if(!date.isValid()){
            System.out.println("InheritedAndRelated.Appointment date: "+ date+" is not a valid calendar date ");
            return false;
        }
        if(date.isBeforeToday()){
            System.out.println("InheritedAndRelated.Appointment date: "+ date+" is today or a date before today.");
            return false;
        }
        if(!date.isWithinSixMonths(date)){
            System.out.println("InheritedAndRelated.Appointment date: "+ date+" is not within six months.");
            return false;
        }
        if(date.isWeekend(date)){
            System.out.println("InheritedAndRelated.Appointment date: "+ date+" is Saturday or Sunday.");
            return false;
        }
        return true;
    }
    public boolean checkDOB(Date dob){
        if(!dob.isValid()){
            System.out.println("InheritedAndRelated.Patient dob: "+ dob+" is not a valid calendar date ");
            return false;
        }
        if(dob.isAfterToday()){
            System.out.println("InheritedAndRelated.Patient dob: " +dob+" is today or a date after today.");
            return false;
        }
        return true;
    }

    public boolean validateTimeslot(Timeslot timeslotInput, String timeslot){
        if(timeslotInput == null){
            System.out.println(timeslot +" is not a valid time slot.");
            return false;
        }
        return true;
    }

    private Timeslot parseTimeslot(String timeslotInput) {
        try {
            Integer.parseInt(timeslotInput);
        } catch (NumberFormatException e) {
            return null;
        }
        switch (Integer.parseInt(timeslotInput)) {
            case 1: return Timeslot.SLOT1;
            case 2: return Timeslot.SLOT2;
            case 3: return Timeslot.SLOT3;
            case 4: return Timeslot.SLOT4;
            case 5: return Timeslot.SLOT5;
            case 6: return Timeslot.SLOT6;
            case 7: return Timeslot.SLOT7;
            case 8: return Timeslot.SLOT8;
            case 9: return Timeslot.SLOT9;
            case 10: return Timeslot.SLOT10;
            case 11: return Timeslot.SLOT11;
            case 12: return Timeslot.SLOT12;
            default: return null;
        }

    }
    public void run() {
        try {
            readProvidersFromFile("providers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        printSortedProviders();
        printRotationList();
        System.out.println("\n\nClinic Manager is running...\n\n");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;  // Ignore empty input
            }
            if (input.equals("Q")) {
                System.out.println("Scheduler has been terminated.");
                break;  // Quit the loop and stop execution
            }
            handleCommand(input);  // Process user commands
        }

        scanner.close();
    }

    private void reverseRotationList() {

        for(int i= technicians.size()-1; i>=1; i--){
            Technician temp = technicians.get(i);
            technicians.set(i, technicians.get(i-1));
            technicians.set(i-1, temp);
        }

    }
}
