package clinic;
import util.List;
import util.Sort;
import util.Date;
import util.Timeslot;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClinicManager {
    private List<Patient> patients;
    private final List<Provider> providers;
    private List<Appointment> appointments;
    private final List<Technician> technicians;
    private final List<Doctor> doctors;
    private List<Imaging> imagingList;
    Iterator<Technician> techIterator;


    public ClinicManager() {
        patients = new List<>();
        providers = new List<>();
        appointments = new List<>();
        technicians = new List<>();
        doctors = new List<>();
        imagingList = new List<>();
        techIterator= technicians.iterator();


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
        if (!dateString.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            return null;
        }

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
        Sort.sortByProvider(sortedProviders);
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
                scheduleDoctorAppointment(tokens);
                break;
            case "T":
                scheduleImagingAppointment(tokens);
                break;
            case "C":
                cancelAppointment(tokens);
                break;
            case "R":
            if (tokens.length < 7) {
                System.out.println("Missing data tokens.");
                return;}
                rescheduleAppointment(tokens);
                break;
            case "PA":
                if(appointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                Sort.sortByAppointment(appointments);
                printLists(appointments);
                break;
            case "PP":
                if(appointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                Sort.sortByPatientAppointments(appointments);
                printLists(appointments);
                break;
            case "PL":
                if(appointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                Sort.sortByLocation(appointments,true);
                printLists(appointments);
                break;
            case "PO":
                List<Appointment> officeAppointments= getOfficeAppointments(appointments);
                if(officeAppointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                Sort.sortByOffice(officeAppointments);
                printLists(officeAppointments);
                break;
            case "PI":
                List<Appointment> imagingAppointments= getImagingAsAppointments(imagingList);
                if(imagingAppointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                Sort.sortByRadiology(imagingAppointments);
                printLists(imagingAppointments);
                break;
            case "PC":
                if(appointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                calculateAndPrintCredits();
                break;
            case "PS":
                if(appointments.isEmpty()){
                    System.out.println("Schedule calendar is empty.");
                    break;
                }
                calculateAndPrintBilling();
                break;
            default:
                System.out.println("Invalid command!");
        }
    }//60 lines too many
    private List<Appointment> getOfficeAppointments(List<Appointment> appointments){
        List<Appointment> officeAppointments = new List<>();
        for(int i=0; appointments.size()>i; i++){
            if(!(appointments.get(i) instanceof Imaging)){
                officeAppointments.add(appointments.get(i));
            }
        }
        return officeAppointments;
    }
    private List<Appointment> getImagingAsAppointments(List<Imaging> imaging){
        List<Appointment> imagingAppointments = new List<>();
        for(int i=0; imaging.size()>i; i++){
            imagingAppointments.add(imaging.get(i));
        }
        return imagingAppointments;
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
        if (tokens.length < 7) {
            System.out.println("Missing data tokens.");
            return;}
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
        Patient patientLL = new Patient(patientProfile);
        Person patient= new Person(patientProfile);
        if(!doesProviderExist(npi)){
            return;
        };
        if(hasPatientConflict(patient, date, timeslot)){
            System.out.println(patient.toString()+ " has an existing appointment at the same time slot.");
            return;
        }
       if(!hasPatientConflict(patient, date, timeslot)&& !hasProviderConflict(doctor, date, timeslot)){
            Appointment appointment= new Appointment(date, timeslot, patient, doctor);
            appointments.add(appointment);
            doctor.addVisit();
            ifPatientListNeedsToUpdate(patientProfile,patientLL,appointment);
            System.out.println(appointment.toString()+ " booked.");
            }

    }
    private void scheduleImagingAppointment(String[] tokens) {
        if (tokens.length < 7) {
            System.out.println("Missing data tokens.");
            return;}
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
            return;}
        if(!(checkAppDate(date) && checkDOB(dob) && validateTimeslot(timeslot, stringTimeslot))){
            return;
            }
        Technician technician = null;
        Profile patientProfile = new Profile(patientFirstName, patientLastName, dob);
        Person patient = new Person(patientProfile);
        Patient patientLL=new Patient(patientProfile);
        if(!hasPatientConflict(patient, date, timeslot)){
             technician = assignTechnician(date, timeslot, room);
        }
        else{
            System.out.println(patient.toString()+ " has an existing appointment at the same time slot.");
            return;
        }
        if(technician == null){
            System.out.println("Cannot find an available technician at all locations for " + room.getRoom() +" at slot " + stringTimeslot + ".");
            return;}
            Appointment imagingApt = new Imaging(date, timeslot, patient,technician,room);
            appointments.add(imagingApt);
            imagingList.add((Imaging)imagingApt);
            technician.addVisit();
            ifPatientListNeedsToUpdate(patientProfile,patientLL,imagingApt);
            System.out.println(imagingApt.toString()+ " booked.");

    }
    private void cancelAppointment(String[] tokens)  {
        if (tokens.length < 6) {
        System.out.println("Missing data tokens.");
        return;}
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
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getDate().equals(appointmentDate) &&appointment.getTimeslot().equals(timeslot)
                    && appointment.getPatient().equals(patient)) {
                System.out.println(String.format("%s %s %s %s %s - appointment has been canceled.",
                    dateInput,
                    timeslot.formatTime(),
                    firstName,
                    lastName,
                    dobInput));
                if (appointments.get(i) instanceof Imaging) {
                    imagingList.remove((Imaging) appointments.get(i));
                }
                deleteSpecificVisit(patientProfile,appointment);
                appointments.remove(appointments.get(i));
                ((Provider)appointment.getProvider()).cancelVisit();
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
    public void deleteSpecificVisit(Profile targetPatient, Appointment appointmentToDelete) {
    for (int i = 0; i < patients.size(); i++) {
        Profile patient = patients.get(i).getProfile();
        if (patient.equals(targetPatient)) {
            patients.get(i).deleteVisit(appointmentToDelete);
            return;
        }
    }
}
    private void rescheduleAppointment(String[] sInput) {
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
            Appointment appointment1 = appointments.get(i);
            if (appointment1.getDate().equals(appointmentDate) && appointment1.getTimeslot().equals(originalTimeslot) && appointment1.getPatient().equals(patient)) {
                Person doctor = appointment1.getProvider();
                if(hasPatientConflict(patient, appointmentDate, newTimeslot)){
                    System.out.println(String.format("%s has an existing appointment at %s %s",
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
                    if (appointments.get(i) instanceof Imaging) {
                        imagingList.remove((Imaging) appointments.get(i));
                        imagingList.add((Imaging)appointment);
                    }
                    System.out.println("Rescheduled to " +appointment.toString());
                    return;
                }
            }
        }
        System.out.println(String.format("%s %s %s does not exist.",
                dateInput, originalTimeslot.formatTime(), patient.toString()));
    } //4 lines too many

    private void calculateAndPrintCredits() {
        Sort.sortByProvider(providers);
        System.out.println("\n** Credit amount ordered by provider. **");
        int proNum= 1;
        for (int i=0; i< providers.size();i++) {
            int numAppointments = providers.get(i).getNumVisits();
            int rate = providers.get(i).rate();
            int expectedCredit = numAppointments * rate;
            System.out.println(String.format("(%d) %s [credit amount: $%,.2f]",
                    proNum,
                    providers.get(i).getProfile().toString(),
                    (double)expectedCredit));
            proNum++;
            }
        System.out.println("** end of list **");
    }
    private void calculateAndPrintBilling(){
        Sort.sortByPatient(patients);

        int count = 1;
        System.out.println("\n** Billing statement ordered by patient. **");
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            Visit visit = patient.getVisits();
            int totalCharge = 0;
            while (visit != null) {
                int rate = ((Provider) visit.getAppointment().getProvider()).rate();
                totalCharge += rate;
                visit = visit.getNext();
            }
            System.out.println(String.format("(%d) %s [due: $%,.2f]",
                    count,
                    patient.toString(),
                    (double)totalCharge));
            count++;
        }
        System.out.println("** end of list **");
        appointments = new List<>();
        imagingList= new List<>();
        patients= new List<>();
    }
    private void printLists(List<Appointment> sortAppointments) {
        if (sortAppointments.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        for(int i=0; sortAppointments.size()>i; i++){
            System.out.println(sortAppointments.get(i).toString());
        }

        System.out.println("** end of list **");
    }
    private void ifPatientListNeedsToUpdate(Profile profile, Patient patient, Appointment appointment) {
        Visit newVisit = new Visit(appointment);
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getProfile().equals(profile)) {
                Visit currentVisit = patients.get(i).getVisits();
                if (currentVisit == null) {
                    patients.get(i).addVisit(appointment);
                } else {
                    while (currentVisit.getNext() != null) {
                        currentVisit = currentVisit.getNext();
                    }
                    currentVisit.setNext(newVisit);
                }
                return;
            }
        }
        patient.addVisit(appointment);
        patients.add(patient);
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
                return true;
            }
        }
        return false;
    }
    private boolean hasTechnicianConflict(Provider provider, Date date, Timeslot time) {
        for (int i=0; imagingList.size()>i; i++) {
            if (imagingList.get(i).getProvider().equals(provider) && imagingList.get(i).getDate().equals(date) &&
                    imagingList.get(i).getTimeslot().equals(time)) {
                return true;
            }
        }
        return false;
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
            System.out.println("Appointment date: "+ date+" is not a valid calendar date ");
            return false;
        }
        if(date.isBeforeToday()){
            System.out.println("Appointment date: "+ date+" is today or a date before today.");
            return false;
        }
        if(!date.isWithinSixMonths(date)){
            System.out.println("Appointment date: "+ date+" is not within six months.");
            return false;
        }
        if(date.isWeekend(date)){
            System.out.println("Appointment date: "+ date+" is Saturday or Sunday.");
            return false;
        }
        return true;
    }
    public boolean checkDOB(Date dob){
        if(!dob.isValid()){
            System.out.println("Patient dob: "+ dob+" is not a valid calendar date ");
            return false;
        }
        if(dob.isAfterToday()){
            System.out.println("Patient dob: " +dob+" is today or a date after today.");
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

    private void reverseRotationList() {

        for(int i= technicians.size()-1; i>=1; i--){
            Technician temp = technicians.get(i);
            technicians.set(i, technicians.get(i-1));
            technicians.set(i-1, temp);
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
                System.out.println("Clinic Manager terminated.");
                break;  // Quit the loop and stop execution
            }
            handleCommand(input);  // Process user commands
        }

        scanner.close();
    }
}
