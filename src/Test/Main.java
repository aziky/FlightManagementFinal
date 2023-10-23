package Test;

import Management.FlightManagement;
import Validation.Validation;
import java.io.IOException;
import java.text.ParseException;


public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        Validation valid = new Validation();
        FlightManagement m = new FlightManagement();

        // read all the information already existed in file
        // m.readCrew();
        m.readFlight();
        m.readReservation();
        m.readCrew();

        int choice = 0;
        do {
            choice = menu();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    m.createFlight();
                    break;
                case 2:
                    do {
                        choice = subMenu();
                        switch (choice) {
                            case 1:
                                m.searchByDepartLocation();
                                break;
                            case 2:
                                m.searchByArrLocation();
                                break;
                            case 3:
                                m.searchByDate();
                                break;
                            case 4:
                                m.createBooking();
                                break;
                        }
                    } while (choice >= 0 && choice < 5);
                    choice = 0;
                    break;
                case 3:
                    m.checkIn();
                    break;
                case 4:
                    String passWord = valid.getString("If you are Administrators, please enter password: ");
                    if (passWord.equals("1234")) {
                        do {
                            choice = subMenu2();
                            switch (choice) {
                                case 1:
                                    do {
                                        choice = subMenu3();
                                        switch (choice) {

                                            case 1:
                                                m.addPilot();
                                                break;
                                            case 2:
                                                m.addAttendant();
                                                break;
                                            case 3:
                                                m.addStaff();
                                                break;
                                            case 4:
                                                m.printEmployee();
                                                m.crewManagementNew();
                                                break;
                                        }
                                    } while (choice >= 0 && choice < 5);
                                    break;
                                case 2:
                                    m.manageFlight();
                                    break;
                            }
                        } while (choice >= 0 && choice < 3);
                    } else {
                        System.out.println("Wrong password");
                    }
                    break;
                case 5:
                    m.saveEmployee();
                    m.saveFlight();
                    m.saveReservation();
                    m.saveCrew();
                    break;
                case 6:
                    m.printAll();
                    break;
                default:
                    System.out.println("Goodbye and Thank you!!!");
                    break;
            }
        } while (choice >= 0 && choice < 7);
    }

    public static int menu() {
        Validation valid = new Validation();
        System.out.println("     ======================================================");
        System.out.println("       1. Flight schedule management ");
        System.out.println("       2. Passenger Reservation and Booking");
        System.out.println("       3. Check-In and Seat Allocation and Availability");
        System.out.println("       4. Crew Management and Administrator Access ");
        System.out.println("       5. Save to file");
        System.out.println("       6. Print all lists from file");
        System.out.println("       7. Exit");
        System.out.println("     ======================================================");
        int choice = valid.checkMenu("Enter choice between 1-7: ", 7);
        return choice;
    }

    public static int subMenu() {
        Validation valid = new Validation();
        System.out.println("         ======================================================");
        System.out.println("          2.1. Search Flight by departure location");
        System.out.println("          2.2. Search Flight by arrival location");
        System.out.println("          2.3. Search Flight by date (dd/MM/yyyy)");
        System.out.println("          2.4. Create Booking");
        System.out.println("          2.5. Back to main menu");
        System.out.println("         ======================================================");
        int choice = valid.checkMenu("Enter choice between 1-5: ", 5);
        return choice;
    }

    public static int subMenu2() {
        Validation valid = new Validation();
        System.out.println("         ======================================================");
        System.out.println("          4.1. Create crew assignments");
        System.out.println("          4.2. Manage flight schedules");
        System.out.println("          4.3. Back to main menu");
        System.out.println("         ======================================================");
        int choice = valid.checkMenu("Enter choice between 1-3: ", 3);
        return choice;
    }

    public static int subMenu3() {
        Validation valid = new Validation();
        System.out.println("           ======================================================");
        System.out.println("            4.1.1 Add Pilot");
        System.out.println("            4.1.2 Add Attendant");
        System.out.println("            4.1.3 Add Ground Staff");
        System.out.println("            4.1.4 Crew assignment");
        System.out.println("            4.1.5 Back to main menu");
        System.out.println("           ======================================================");
        int choice = valid.checkMenu("Enter choice between 1-5: ", 5);
        return choice;
    }
}
