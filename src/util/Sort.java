package util;
import java.util.List;
import Clinic.*;

public class Sort {

    public static void sortByPatient(List<Appointment> list) {
        System.out.println("\n** Appointments ordered by patient/date/time");
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getPatient().compareTo(list.get(j+1).getPatient()) > 0) {
                    swap(list,j, j + 1);
                }
            }
        }
    }
    // Implement an in-place sorting algorithm by county, date, and timeslot
    public static void sortByLocation(List<Appointment> apptType) {
        for (int i = 0; i < apptType.size() - 1; i++) {
            for (int j = 0; j < apptType.size() - i - 1; j++) {
                Provider provider = (Provider) apptType.get(j).getProvider();
                Provider providerNext = (Provider) apptType.get(j + 1).getProvider();
                int countyComparison = provider.getLocation().getCounty().compareTo(providerNext.getLocation().getCounty());
                if (countyComparison > 0) {
                    swap(apptType,j, j + 1);
                } else if (countyComparison == 0) {
                    int dateComparison = apptType.get(j).getDate().compareTo(apptType.get(j + 1).getDate());
                    if (dateComparison > 0) {
                        swap(apptType,j, j + 1);
                    } else if (dateComparison == 0) {
                        int timeComparison = apptType.get(j).getTimeslot().compareTo(apptType.get(j + 1).getTimeslot());
                        if (timeComparison > 0) {
                            swap(apptType, j, j + 1);
                        }
                    }
                }
            }
        }
    }
    //could possibly be an issue at line73 since im comparing by person not name
    public static void sortByAppointment(List<Appointment> list) {
        System.out.println("\n** util.List of appointments, ordered by date/time/provider.");
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                Appointment a1 = list.get(j);
                Appointment a2 = list.get(j + 1);

                int dateComparison = a1.getDate().compareTo(a2.getDate());
                if (dateComparison > 0) {
                    swap(list,j, j + 1);
                } else if (dateComparison == 0) {
                    int timeComparison = a1.getTimeslot().compareTo(a2.getTimeslot());
                    if (timeComparison > 0) {
                        swap(list,j, j + 1);
                    } else if (timeComparison == 0) {
                        if (a1.getProvider().compareTo(a2.getProvider()) > 0) {
                            swap(list,j, j + 1);
                        }
                    }
                }
            }
        }
    }
    public static void sortProvidersByLastName(List<Provider> providers) {
        for (int i = 0; i < providers.size() - 1; i++) {
            for (int j = 0; j < providers.size() - i - 1; j++) {
                if (providers.get(j).getProfile().getLastName()
                        .compareToIgnoreCase(providers.get(j + 1).getProfile().getLastName()) > 0) {
                    proSwap(providers, j, j + 1);;
                }
            }
        }
    }


    private static void swap(List<Appointment> list, int i, int j) {
        Appointment temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    private static void proSwap(List<Provider> providers, int i, int j) {
        Provider temp = providers.get(i);
        providers.set(i, providers.get(j));
        providers.set(j, temp);
    }

    public static void sortByOffice(List<Appointment> list) {
        System.out.println("\n** util.List of office appointments ordered by county/date/time.");
        sortByLocation(list);
    }


    public static void sortByRadiology(List<Imaging> list) {
//        List<Appointment> imagingList = new List<>();
//        for (Imaging imaging : list) {
//            imagingList.add(imaging);
//        }
//        System.out.println("\n** util.List of radiology appointments ordered by county/date/time.");
//        sortByLocation(imagingList);
    }
}

