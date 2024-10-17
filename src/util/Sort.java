package util;

import clinic.Appointment;
import clinic.Patient;
import clinic.Provider;

public class Sort {

    public static void sortByPatientAppointments(List<Appointment> list) {
        System.out.println("\n** Appointments ordered by patient/date/time");
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getPatient().compareTo(list.get(j + 1).getPatient()) > 0) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    public static void sortByProvider(List<Provider> list) {

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getProfile().compareTo(list.get(j + 1).getProfile()) > 0) {
                    proSwap(list, j, j + 1);
                }
            }
        }
    }

    public static void sortByLocation(List<Appointment> apptType, boolean printLine) {
        if(printLine) {
            System.out.println("\n** List of appointments, ordered by county/date/time.");
        }
        for (int i = 0; i < apptType.size() - 1; i++) {
            for (int j = 0; j < apptType.size() - i - 1; j++) {
                Provider provider = (Provider) apptType.get(j).getProvider();
                Provider providerNext = (Provider) apptType.get(j + 1).getProvider();
                int countyComparison = provider.getLocation().getCounty().compareTo(providerNext.getLocation().getCounty());
                if (countyComparison > 0) {
                    swap(apptType, j, j + 1);
                } else if (countyComparison == 0) {
                    int dateComparison = apptType.get(j).getDate().compareTo(apptType.get(j + 1).getDate());
                    if (dateComparison > 0) {
                        swap(apptType, j, j + 1);
                    } else if (dateComparison == 0) {
                        int timeComparison = apptType.get(j).getTimeslot().compareTo(apptType.get(j + 1).getTimeslot());
                        if (timeComparison > 0) {
                            swap(apptType, j, j + 1);
                        } else if (timeComparison == 0) {
                            Date patientDOB=apptType.get(j).getPatient().getProfile().getDateOfBirth();
                            int patientComparison = patientDOB.compareTo(apptType.get(j + 1).getPatient().getProfile().getDateOfBirth());
                            if (patientComparison > 0) {
                                swap(apptType, j, j + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void sortByAppointment(List<Appointment> list) {
        System.out.println("\n** List of appointments, ordered by date/time/provider.");
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                Appointment a1 = list.get(j);
                Appointment a2 = list.get(j + 1);

                int dateComparison = a1.getDate().compareTo(a2.getDate());
                if (dateComparison > 0) {
                    swap(list, j, j + 1);
                } else if (dateComparison == 0) {
                    int timeComparison = a1.getTimeslot().compareTo(a2.getTimeslot());
                    if (timeComparison > 0) {
                        swap(list, j, j + 1);
                    } else if (timeComparison == 0) {
                        if (a1.getProvider().compareTo(a2.getProvider()) > 0) {
                            swap(list, j, j + 1);
                        }
                    }
                }
            }
        }
    }

    public static void sortByPatient(List<Patient> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = 0; j < patients.size() - i - 1; j++) {
                if (patients.get(j).compareTo(patients.get(j + 1)) > 0) {
                    patientsSwap(patients, j, j + 1);
                }
            }
        }
    }

    public static void sortByRadiology(List<Appointment> list) {
        System.out.println("\n** List of radiology appointments ordered by county/date/time.");
        sortByLocation(list,false);
    }
    public static void sortByOffice(List<Appointment> list) {
        System.out.println("\n** List of office appointments ordered by county/date/time.");
        sortByLocation(list,false);
    }

    private static void swap(List<Appointment> list, int i, int j) {
        Appointment temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    private static void proSwap(List<Provider> provider, int i, int j) {
        Provider temp = provider.get(i);
        provider.set(i, provider.get(j));
        provider.set(j, temp);
    }
    private static void patientsSwap(List<Patient> patients, int i, int j) {
        Patient temp = patients.get(i);
        patients.set(i, patients.get(j));
        patients.set(j, temp);
    }


}