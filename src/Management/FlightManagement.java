package Management;

import Data.Attendant;
import Data.Crew;
import Data.CrewM;
import Data.FlightSchedule;
import Data.Pilot;
import Data.Reservation;
import Data.Staff;
import Validation.Validation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class FlightManagement {

    private Scanner sc = new Scanner(System.in);
//    ArrayList<Reservation> reFileList = new ArrayList<>();
//    ArrayList<FlightSchedule> fFileList = new ArrayList<>();
    ArrayList<FlightSchedule> fList;
    ArrayList<Reservation> bList;
    ArrayList<Crew> crList;
    ArrayList<CrewM> cList;
    ArrayList<Pilot> pList = new ArrayList<>();
    ArrayList<Attendant> aList = new ArrayList<>();
    ArrayList<Staff> sList = new ArrayList<>();
    Validation valid = new Validation();
    FlightSchedule fs = new FlightSchedule();
    public String[] seats = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"};

    public FlightManagement() {
        cList = new ArrayList<>();
        crList = new ArrayList<>();
        fList = new ArrayList<>();
        bList = new ArrayList<>();
    }

    public FlightSchedule searchByFlightID(String flight) {
        for (FlightSchedule f : fList) {
            if (f.getfNumber().equals(flight)) {
                return f;
            }
        }
        return null;
    }

    public void createFlight() throws ParseException {
        String flightNumber, destinationCity, departureCity, aTime, dTime;
        boolean validTime = true;
        String getChoice;
        do {
            flightNumber = valid.checkDuplicate("Enter the Flight Number (Fxxxx): ", fList);
            departureCity = valid.checkString("Enter Departure City: ");
            destinationCity = valid.checkString("Enter Destination City: ");
            do {
                do {
                    dTime = valid.checkString("Enter Departure Time (dd/MM/yyyy hh:mm am/pm): ").trim();
                    if (valid.checkValidTime2(dTime) == false) {
                        System.out.println("Please enter Departure Time again (dd/MM/yyyy hh:mm am/pm): ");
                    }
                } while (valid.checkValidTime2(dTime) == false);

                do {
                    aTime = valid.checkString("Enter Arrival Time (dd/MM/yyyy hh:mm am/pm): ").trim();
                    if (valid.checkValidTime2(aTime) == false) {
                        System.out.println("Please enter Arrival Time again (dd/MM/yyyy hh:mm am/pm): ");
                    }
                } while (valid.checkValidTime2(aTime) == false);

                validTime = valid.checkValidTime(dTime, aTime);

            } while (validTime == false);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date date1 = format.parse(dTime);
            Date date2 = format.parse(aTime);
            int duration = (int) ((date2.getTime() - date1.getTime()) / (60 * 60 * 1000));

            fList.add(new FlightSchedule(flightNumber, destinationCity, departureCity, aTime, dTime, duration));
            List<String> seat = new ArrayList<>(Arrays.asList(seats));
            fList.get(fList.size() - 1).setS(seat);
            getChoice = valid.checkYesOrNo("Do you want to continue(Y/N)");
            if (getChoice.equalsIgnoreCase("n")) {
                System.out.println("Returning to menu!........");
                break;
            }
        } while (getChoice.equalsIgnoreCase("y"));
    }

    // print the information of the flight
    public void showFlight(List<FlightSchedule> m) {
        if (m.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            for (FlightSchedule f : m) {
                System.out.format("|%-15s|%-15s|%-18s|%-20s|%-20s|%-15s|%-15s|",
                        f.getfNumber(), f.getDepartCity(), f.getDesCity(), f.getDepartTime(), f.getArrTime(), f.getNumberSeats(), f.getDurationTime());
                System.out.println("");
            }
        }
    }

    public void searchByDepartLocation() {
        List<FlightSchedule> result = new ArrayList<>();
        if (fList.isEmpty()) {
            System.out.println("Empty List");

        } else {
            String departureCity = valid.checkString("Enter Departure City to search: ");
            System.out.println("Flight List: ");
            for (FlightSchedule f : fList) {
                if (f.getDepartCity().equalsIgnoreCase(departureCity)) {
                    result.add(f);
                }
            }
            showFlight(result);
        }
    }

    public void searchByArrLocation() {
        List<FlightSchedule> result = new ArrayList<>();
        String destinationCity = valid.checkString("Enter Destination City to search: ");
        if (fList.isEmpty()) {
            System.out.println("Empty List");
            return;
        }
        System.out.println("Flight List: ");
        for (FlightSchedule f : fList) {
            if (f.getDesCity().equalsIgnoreCase(destinationCity)) {
                result.add(f);
            }
        }
        showFlight(result);

    }

    public void searchByDate() {
        List<FlightSchedule> result = new ArrayList<>();
        String dTime;
        if (fList.isEmpty()) {
            System.out.println("Empty List");
            return;
        }
        do {
            dTime = valid.checkString("Enter Departure Time: ");
            if (valid.checkValidTime(dTime) == false) {
                System.out.println("Enter right format and valid again!!");
            }
        } while (valid.checkValidTime(dTime) == false);
        System.out.println("Flight List: ");
        for (FlightSchedule f : fList) {
            String part[] = f.getDepartTime().split(" ");
            String d = part[0].trim();
            if (d.equals(dTime)) {
                result.add(f);
            }
        }
        showFlight(result);
    }

    public void createBooking() {
       
        String choice;
        if (fList.isEmpty()) {
            System.out.println("There are no flight list");
        } else {
            do {
                String ID = "";
                ID += "B";
                if (bList.isEmpty()) {
                    ID += "001";
                } else {
                    ID += "00" + (bList.size() + 1);
                }
                String name = valid.checkString("Enter customer name: ");
                String contact = valid.checkContact("Enter customer phone: ", "^[0-9]{9,11}$");

                showFlight(fList);
                String flightNumber = valid.checkStringWithRegex("Enter the Flight Number: ", "^F\\d{4}$", "Wrong format, please enter (Fxxxx)");

                if (searchByFlightID(flightNumber) == null) {
                    System.out.println("Can not find! Flight so can not add");
                } else {
                    bList.add(new Reservation(name, ID, flightNumber, contact));
                    System.out.println("Add successfully");
                }
                choice = valid.checkYesOrNo("Do you want to enter book again?(Y/N): ");
                if (choice.equalsIgnoreCase("n")) {
                    System.out.println("Returning to menu!........");
                    break;
                }
            } while (choice.equalsIgnoreCase("y"));
            System.out.format("|%-15s|%-15s|%-15s|%-15s|%-9s|\n", "Name", "Booking ID", "Flight Number", "Phone", "Seat");
            for (Reservation r : bList) {
                r.print();
            }
        }
    }

    // public void printFlight(){
    //     if (fList.isEmpty()) {
    //         System.out.println("There is no Flight!");
    //     } else {
    //         System.out.println("==========================================================Flight===============================================================================");
    //         System.out.printf("|%-15s|%-15s|%-18s|%-20s|%-20s|%-15s|%-15s| \n", "Flight Number", "Departure City",
    //                 "Destination City", "Departure Time", "Arrival Time", "Available seats", "Duration");
    //         sortByDate();
    //         for (FlightSchedule f : fList) {
    //             System.out.format("|%-15s|%-15s|%-18s|%-20s|%-20s|%-15d|%-15d|",
    //                     f.getfNumber(), f.getDepartCity(), f.getDesCity(), f.getDepartTime(), f.getArrTime(), f.getNumberSeats(), f.getDurationTime());
    //             System.out.println("");

    //         }
    //         System.out.println("");
    //     }
    // }

    public Reservation searchReservationID(String id) {
        for (Reservation r : bList) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public Reservation findRes(String resID) {
        for (Reservation res : bList) {
            if (res.getId().equalsIgnoreCase(resID)) {
                return res;
            }
        }
        return null;
    }

    public void checkIn() {
        if (bList.isEmpty()) {
            System.out.println("Empty List");
        } else {
            String bookingId = valid.checkString("Enter customer ID: ").toUpperCase();
            Reservation r = this.findRes(bookingId);
            if (searchReservationID(bookingId) == null) {
                System.out.println("There is no ID");

            } else if (!r.getSeat().equalsIgnoreCase("Not Book")) {
                System.out.println("The customer ID already has a seat");
            }
            else {

                FlightSchedule fl = this.searchByFlightID(r.getfNumber());
                System.out.format("|%-12s|%-12s|%-13s|%-10s|%-17s|%-17s|%-20s|%-20s|",
                        r.getId(), r.getName(), r.getContact(), r.getfNumber(), fl.getDepartCity(), fl.getDesCity(), fl.getDepartTime(), fl.getArrTime());
                System.out.println("");

                fl.printSeats();
                String seat = valid.checkString("Enter seat to choose: ").toUpperCase();
                fl.setEmptySeatsToSold(seat);
                for (Reservation res : bList) {
                    if (res.getSeat().equalsIgnoreCase(seat)) {
                        System.out.println("The seat had been sold");
                        break;
                    } else if (res.getSeat().equalsIgnoreCase("Not book") && res.getId().equalsIgnoreCase(bookingId)) {
                        r.setSeat(seat);
                        System.out.println("Successfully Check In!");
                    }
                }

                System.out.println("There are " + fl.getNumberSeats() + " available");
            }
        }
        for (Reservation r : bList) {
            r.print();
        }
    }

    public void addPilot() {
            String pID = valid.checkDuplicateCrew("Please enter Pilot ID: ", "^P\\d{2}$", "Wrong format, please enter (Pxx)", crList);
            String pName = valid.checkString("Enter Pilot name: ");
            int pAge = valid.checkIntMinMax("Enter Pilot age (18-65): ", 18, 65);
            crList.add(new Pilot(pID, pName, pAge));
            System.out.println("Add Pilot successfully");
    }

    public void addAttendant() {

            String aID = valid.checkDuplicateCrew("Please enter Attendant ID: ", "^A\\d{2}$", "Wrong format, please enter (Axx)", crList);
            String aName = valid.checkString("Enter Attendant name: ");
            int aAge = valid.checkIntMinMax("Enter Attendant age (18-65): ", 18, 65);
            crList.add(new Attendant(aID, aName, aAge));
            System.out.println("Add Attendant successfully");

    }

    public void addStaff() {
            String sID = valid.checkDuplicateCrew("Please enter Staff ID: ", "^S\\d{2}$", "Wrong format, please enter (Sxx)", crList);
            String sName = valid.checkString("Enter Staff name: ");
            int sAge = valid.checkIntMinMax("Enter Staff age (18-65): ", 18, 65);
            crList.add(new Staff(sID, sName, sAge));
            System.out.println("Add Staff successfully");

        }


    public CrewM searchByCrew(String id) {
        for (CrewM cm : cList) {
            if (cm.getfNumber().equalsIgnoreCase(id)) {
                return cm;
            }
        }
        return null;
    }

    public Crew searchByCrewaAss(List<Crew> p, String s) {
        for (Crew pi : p) {
            if (pi.getId().equalsIgnoreCase(s)) {
                return pi;
            }
        }
        return null;
    }

    public Crew searchByPilot(List<Pilot> p, String s) {
        for (Pilot pi : p) {
            if (pi.getId().equalsIgnoreCase(s)) {
                return pi;
            }
        }
        return null;
    }

    public Attendant searchByAtt(List<Attendant> a, String s) {
        for (Attendant at : a) {
            if (at.getId().equalsIgnoreCase(s)) {
                return at;
            }
        }
        return null;
    }

    public Staff searchByStaff(List<Staff> st, String str) {
        for (Staff sta : st) {
            if (sta.getId().equalsIgnoreCase(str)) {
                return sta;
            }
        }
        return null;
    }

    public void crewManagementNew() {
        ArrayList<Pilot> pList = new ArrayList<>();
        ArrayList<Attendant> aList = new ArrayList<>();
        ArrayList<Staff> sList = new ArrayList<>();
        String enterPilot;
        String enterAttendant;
        String enterStaff;
        Pilot pilot;
        Attendant att;
        Staff sta;


            String flight = valid.checkString("Enter the Flight Number (Fxxxx) you want to add crew: ").toUpperCase().trim();
            FlightSchedule fNum = this.searchByFlightID(flight);
            CrewM cm = this.searchByCrew(flight);
            if (fNum == null) {
                System.out.println("There is no Flight");
            } else if (cm != null) {
                System.out.println("The Crew has been created");
            } else {
                String getChoice;
                //        String enterCrew = valid.checkString("Enter the crew you want to add(Pilot, Attendant, Staff): ");
                do {
                    enterPilot = valid.checkStringWithRegex("Please enter Pilot ID: ", "^P\\d{2}$", "Wrong format, please enter (Pxx)");
                    Crew p = this.searchByCrewaAss(crList, enterPilot);
                    if (p != null) {
                        pList.add(new Pilot(enterPilot, p.getName(), p.getAge()));
                    } else {
                        System.out.println("Pilot id not exist");
                    }
                    //                   pilot = (Pilot) this.searchByPilot(pList, enterPilot); 
                    getChoice = valid.checkYesOrNo("Do you want to add more Pilots(Y/N)");
                    if (getChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (getChoice.equalsIgnoreCase("y"));
//-----------------------------------------------------------------------------------------------------------------
                do {
                    enterAttendant = valid.checkStringWithRegex("Please enter Attendant ID: ", "^A\\d{2}$", "Wrong format, please enter (Axx)");

                    Crew a = this.searchByCrewaAss(crList, enterAttendant);

                    if (a != null) {
                        aList.add(new Attendant(a.getId(), a.getId(), a.getAge()));
                    } else {
                        System.out.println("Attendant id not exist");

                    }
                    //           att = this.searchByAtt(aList, enterAttendant);
                    getChoice = valid.checkYesOrNo("Do you want to add more Attendants(Y/N)");
                    if (getChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (getChoice.equalsIgnoreCase("y"));
//-----------------------------------------------------------------------------------------------------------------
                do {
                    enterStaff = valid.checkStringWithRegex("Please enter Staff ID: ", "^S\\d{2}$", "Wrong format, please enter (Sxx)");

                    Crew s = this.searchByCrewaAss(crList, enterStaff);
                    if (s != null) {
                        sList.add(new Staff(s.getId(), s.getName(), s.getAge()));
                    } else {
                        System.out.println("Ground staff id not exist");
                    }
                    //                sta = this.searchByStaff(sList, enterStaff);
                    getChoice = valid.checkYesOrNo("Do you want to add more Ground Staff(Y/N)");
                    if (getChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (getChoice.equalsIgnoreCase("y"));
                pilot = (Pilot) this.searchByPilot(pList, enterPilot);
                sta = this.searchByStaff(sList, enterStaff);
                att = this.searchByAtt(aList, enterAttendant);
                if (pilot != null && att != null && sta != null) {
                    cList.add(new CrewM(flight, pList, aList, sList));
                } else {
                    System.out.println("Cannot add Crew to Flight");
                }
                for (CrewM cr : cList) {
                    System.out.println(cr.toString());
                }
            }

    }

    public void crewManagement() {
        ArrayList<Pilot> pList = new ArrayList<>();
        ArrayList<Attendant> aList = new ArrayList<>();
        ArrayList<Staff> sList = new ArrayList<>();
        String passWord = valid.getString("If you are Administrators, please enter password: ");
        if (passWord.equals("1234")) {
            String flight = valid.checkString("Enter the Flight Number (Fxxxx) you want to add crew: ").toUpperCase().trim();
            FlightSchedule fNum = this.searchByFlightID(flight);
            CrewM cm = this.searchByCrew(flight);
            if (fNum == null) {
                System.out.println("There is no Flight");
            } else if (cm != null) {
                System.out.println("The Crew has been created");
            } else {
                String getChoice;
                String enterCrew = valid.checkString("Enter the crew you want to add(Pilot, Attendant, Staff): ");
                do {
                    String enterPilot = valid.checkString("Enter Pilot id: ");
                    if (enterCrew.equalsIgnoreCase("Pilot")) {
                        Crew p = this.searchByCrewaAss(crList, enterPilot);
                        if (p != null) {
                            pList.add(new Pilot(p.getId(), p.getName(), p.getAge()));
                        } else {
                            System.out.println("Pilot id not exist");
                        }
                    }
                    getChoice = valid.checkYesOrNo("Do you want to add more Pilots(Y/N)");
                    if (getChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (getChoice.equalsIgnoreCase("y"));
//-----------------------------------------------------------------------------------------------------------------
                do {
                    String enterAttendant = valid.checkString("Enter Attendant id: ");
                    if (enterCrew.equalsIgnoreCase("Attendant")) {
                        Crew a = this.searchByCrewaAss(crList, enterAttendant);
                        if (a != null) {
                            aList.add(new Attendant(a.getId(), a.getId(), a.getAge()));
                        } else {
                            System.out.println("Attendant id not exist");
                        }
                    }
                    getChoice = valid.checkYesOrNo("Do you want to add more Attendants(Y/N)");
                    if (getChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (getChoice.equalsIgnoreCase("y"));
//-----------------------------------------------------------------------------------------------------------------
                do {
                    String enterStaff = valid.checkString("Enter Ground Staff id: ");
                    if (enterCrew.equalsIgnoreCase("Staff")) {
                        Crew s = this.searchByCrewaAss(crList, enterStaff);
                        if (s != null) {
                            sList.add(new Staff(s.getId(), s.getName(), s.getAge()));
                        } else {
                            System.out.println("Ground staff id not exist");
                        }
                    }
                    getChoice = valid.checkYesOrNo("Do you want to add more Ground Staff(Y/N)");
                    if (getChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (getChoice.equalsIgnoreCase("y"));
                cList.add(new CrewM(flight, pList, aList, sList));
                for (CrewM cr : cList) {
                    System.out.println(cr.toString());
                }

            }

        } else {
            System.out.println("Wrong password");
        }
    }


    public void manageFlight() throws ParseException {
        String newDepTime, newArrTime;
        boolean validTime = true;

            if (fList.isEmpty()) {
                System.out.println("There is no list");
            } else {
                String fNumber = valid.checkStringWithRegex("Enter Flight number to search: ", "^F\\d{4}$", "Wrong format, Please enter (Fxxxx)");
                FlightSchedule newFlight = searchByFlightID(fNumber);
                if (newFlight == null) {
                    System.out.println("There is no Flight!");
                } else {

                    String oldDepCity = newFlight.getDepartCity();
                    String newDepCity = valid.getString("Enter new Departure City: ");
                    if (newDepCity.isBlank()) {
                        newFlight.setDepartCity(oldDepCity);
                    } else {
                        newFlight.setDepartCity(newDepCity);
                    }

                    String oldDesCity = newFlight.getDesCity();
                    String newDesCity = valid.getString("Enter new Destination City: ");
                    if (newDesCity.isBlank()) {
                        newFlight.setDesCity(oldDesCity);
                    } else {
                        newFlight.setDesCity(newDesCity);
                    }

                    do {
                        String oldDepTime = newFlight.getDepartTime();
                        String oldArrTime = newFlight.getArrTime();
                        newDepTime = valid.getString("Enter Departure Time: ").trim();
                        newArrTime = valid.getString("Enter Arrival Time: ").trim();
                        if (newArrTime.isBlank() && newDepTime.isBlank()) {
                            // validTime = valid.checkValidTime(oldDepTime, oldArrTime);
                            newFlight.setDepartTime(oldDepTime);
                            newFlight.setArrTime(oldArrTime);
                            // if (validTime == false) {
                            //     System.out.println("Enter Departure Time and Arrival Time again");
                            // }
                        } else if (newArrTime.isBlank()) {
                            validTime = valid.checkValidTime(newDepTime, oldArrTime);
                            newFlight.setDepartTime(newDepTime);
                            newFlight.setArrTime(oldArrTime);
                            if (validTime == false) {
                                System.out.println("Enter Departure Time and Arrival Time again");
                            }
                        } else if (newDepTime.isBlank()) {
                            validTime = valid.checkValidTime(oldDepTime, newArrTime);
                            newFlight.setDepartTime(oldDepTime);
                            newFlight.setArrTime(newArrTime);
                            if (validTime == false) {
                                System.out.println("Enter Departure Time and Arrival Time again");
                            }
                        } else {
                            validTime = valid.checkValidTime(newDepTime, newArrTime);
                            newFlight.setDepartTime(newDepTime);
                            newFlight.setArrTime(newArrTime);
                            if (validTime == false) {
                                System.out.println("Enter Departure Time and Arrival Time again");
                            }
                        }
                    } while (validTime == false);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    Date date1 = format.parse(newFlight.getDepartTime());
                    Date date2 = format.parse(newFlight.getArrTime());
                    int duration = (int) ((date2.getTime() - date1.getTime()) / (60 * 60 * 1000));
                    newFlight.setDurationTime(duration);
                }
            }

    }

    public void saveFlight() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(System.getProperty("user.dir") +"\\src\\output\\FlightManagement.dat"));
            String line;
            for (FlightSchedule f : fList) {
                line = f.toString() + "\n";
                br.write(line);
            }
            br.close();
            System.out.println("The Flight saved successfully");
        } catch (Exception e) {
            System.out.println("File error");
        }
    }
    public void saveEmployee() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(System.getProperty("user.dir") +"\\src\\output\\Employee.dat"));
            String line;
            for (Crew f : crList) {
                line = f.toString() + "\n";
                br.write(line);
            }
            br.close();
            System.out.println("The Employee saved successfully");
        } catch (Exception e) {
            System.out.println("File error");
        }
    }

    public void saveReservation() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(System.getProperty("user.dir") +"\\src\\output\\Reservation.dat"));
            String line;
            for (Reservation r : bList) {
                line = r.toString() + "\n";
                br.write(line);
            }
            br.close();
            System.out.println("The Reservation saved successfully");
        } catch (Exception e) {
            System.out.println("File error");
        }
    }

    public void saveCrew() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(System.getProperty("user.dir") +"\\src\\output\\CrewAssignment.dat"));
            String line;
            for (CrewM cm : cList) {
                line = cm.toString() + "\n";
                br.write(line);
            }
            br.close();
            System.out.println("The Crew Assignment saved successfully");
        } catch (Exception e) {
            System.out.println("File error");
        }
    }
    public void readEmployee() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") +"\\src\\output\\Employee.dat"));
        String line = "";
        while ((line = br.readLine()) != null) {
            String parts[] = line.split(":");
            String id = parts[0].trim();
            String name = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());
            crList.add(new Crew(id, name, age));
        }
        br.close();

    }
    public void readFlight() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") +"\\src\\output\\FlightManagement.dat"));
        String line = "";
        while ((line = br.readLine()) != null) {
            String parts[] = line.split(",");
            String fNumber = parts[0].trim();
            String desCity = parts[1].trim();
            String departCity = parts[2].trim();
            String departTime = parts[3].trim();
            String arrTime = parts[4].trim();
            String seat[] = parts[5].trim().split(" ");
            int duration = Integer.parseInt(parts[6].trim());
            FlightSchedule newFlight = new FlightSchedule(fNumber, desCity, departCity, arrTime, departTime, duration);
            //   fFileList.add(newFlight);
            fList.add(newFlight);
            List<String> eSeat = new ArrayList<>(Arrays.asList(seat));
            fList.get(fList.size() - 1).setS(eSeat);
        }
        br.close();

    }

    public void readReservation() throws IOException {
        //        ArrayList<Reservation> reList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") +"\\src\\output\\Reservation.dat"));
        String line = "";
        Reservation newReservation = null;
        while ((line = br.readLine()) != null) {
            String parts[] = line.split(",");
            String name = parts[0].trim();
            String id = parts[1].trim();
            String fNumber = parts[2].trim();
            String contact = parts[3].trim();
            String seat = parts[4].trim();
            newReservation = new Reservation(name, id, fNumber, contact, seat);
            // reFileList.add(newReservation);
            bList.add(newReservation);
        }
        br.close();
    }

    public void readCrew() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") +"\\src\\output\\CrewAssignment.dat"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] arr = line.split(";");
                String id = arr[0].trim();
                String itemsStr1 = arr[1].trim();
                String itemsStr2 = arr[2].trim();
                String itemsStr3 = arr[3].trim();

                itemsStr1 = itemsStr1.substring(1, itemsStr1.length() - 1);
                String[] itemsArr = itemsStr1.split(",");

                itemsStr2 = itemsStr2.substring(1, itemsStr2.length() - 1);
                String[] itemsArr2 = itemsStr2.split(",");

                itemsStr3 = itemsStr3.substring(1, itemsStr3.length() - 1);
                String[] itemsArr3 = itemsStr3.split(",");

                List<Pilot> pilotList = new ArrayList<>();
                for (String itemStr1 : itemsArr) {
                    String[] itemParts = itemStr1.split(":");
                    String pId = itemParts[0].trim();
                    String name = itemParts[1].trim();
                    int age = Integer.parseInt(itemParts[2].trim());
                    Pilot p = new Pilot(pId, name, age);
                    pilotList.add(p);
                }

                List<Attendant> attendantList = new ArrayList<>();
                for (String itemStr2 : itemsArr2) {
                    String[] itemParts = itemStr2.split(":");
                    String pId = itemParts[0].trim();
                    String name = itemParts[1].trim();
                    int age = Integer.parseInt(itemParts[2].trim());
                    Attendant a = new Attendant(pId, name, age);
                    attendantList.add(a);
                }

                List<Staff> staffList = new ArrayList<>();
                for (String itemStr3 : itemsArr3) {
                    String[] itemParts = itemStr3.split(":");
                    String pId = itemParts[0].trim();
                    String name = itemParts[1].trim();
                    int age = Integer.parseInt(itemParts[2].trim());
                    Staff s = new Staff(pId, name, age);
                    staffList.add(s);
                }

                CrewM importCrew1 = new CrewM(id, pilotList, attendantList, staffList);
                cList.add(importCrew1);
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void printAll() throws IOException, ParseException {
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (fList.isEmpty()) {
            System.out.println("There is no Flight!");
        } else {
            System.out.println("==========================================================Flight===============================================================================");
            System.out.printf("|%-15s|%-15s|%-18s|%-20s|%-20s|%-15s|%-15s| \n", "Flight Number", "Departure City",
                    "Destination City", "Departure Time", "Arrival Time", "Available seats", "Duration");
            sortByDate();
            for (FlightSchedule f : fList) {
                System.out.format("|%-15s|%-15s|%-18s|%-20s|%-20s|%-15d|%-15d|",
                        f.getfNumber(), f.getDepartCity(), f.getDesCity(), f.getDepartTime(), f.getArrTime(), f.getNumberSeats(), f.getDurationTime());
                System.out.println("");

            }
            System.out.println("");
        }

        if (bList.isEmpty()) {
            System.out.println("There is no Reservation!");
        } else {
            System.out.println("=======================================================Reservation=============================================================================");
            System.out.printf("|%-15s|%-15s|%-15s|%-15s|%-9s| \n", "Name", "Customer ID",
                    "Flight Number", "Contact detail", "Seat");
            for (Reservation b : bList) {
                b.print();
            }
            System.out.println("");
        }

        if (cList.isEmpty()) {
            System.out.println("There is no Crew!");
        } else {
            System.out.println("===========================================================Crew================================================================================");
            System.out.printf("|%-15s|%-40s|%-40s|%-40s|\n", "Flight Number", "Pilots",
                    "Attendants", "Staffs");
            for (CrewM cm : cList) {
                System.out.printf("|%-15s|%-40s|%-40s|%-40s|\n", cm.getfNumber(), cm.getpList(),
                    cm.getaList(), cm.getsList());
            }
            System.out.println("");
        }
    }
    public void printEmployee() throws IOException, ParseException {
        showFlight(fList);
        if (bList.isEmpty()) {
            System.out.println("There is no Employee!");
        } else {
            System.out.println("========================================================Employee============================================================================");
            System.out.printf("|%-15s|%-15s|%-7s| \n", "Employee ID", "Name", "Age");
            for (Crew b : crList) {
                b.printEmployee();
            }
            System.out.println("");
        }
    }
    public void sortByDate() {
        Collections.sort(fList, new Comparator<FlightSchedule>() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            @Override
            public int compare(FlightSchedule o1, FlightSchedule o2) {
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(o1.getDepartTime());
                    d2 = sdf.parse(o2.getDepartTime());
                } catch (ParseException ex) {
                    System.out.println(ex);
                }
                if (d1.compareTo(d2) > 0) {
                    return 1;
                } else if (d1.compareTo(d2) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
        );

    }

}
