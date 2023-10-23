
package Data;

import java.util.List;

public class Employee {
    private String fNumber;
    private List<Pilot> pList;
    private List<Attendant> aList;
    private List<Staff> sList;

    public Employee() {
    }

    public Employee(List<Pilot> pList, List<Attendant> aList, List<Staff> sList) {
        this.pList = pList;
        this.aList = aList;
        this.sList = sList;
    }

    public Employee(String fNumber, List<Pilot> pList, List<Attendant> aList, List<Staff> sList) {
        this.fNumber = fNumber;
        this.pList = pList;
        this.aList = aList;
        this.sList = sList;
    }

    public String getfNumber() {
        return fNumber;
    }

    public void setfNumber(String fNumber) {
        this.fNumber = fNumber;
    }

    public List<Pilot> getpList() {
        return pList;
    }

    public void setpList(List<Pilot> pList) {
        this.pList = pList;
    }

    public List<Attendant> getaList() {
        return aList;
    }

    public void setaList(List<Attendant> aList) {
        this.aList = aList;
    }

    public List<Staff> getsList() {
        return sList;
    }

    public void setsList(List<Staff> sList) {
        this.sList = sList;
    }

    @Override
    public String toString() {
        return fNumber + "; " + pList + "; " + aList + "; " + sList;
    }

    private void printPilot() {
        for (Pilot p : pList) {
            System.out.printf("|%-15s|%-15s|%-15s|%-7s| \n",fNumber, p.getId(), p.getName(), p.getAge());
        }
    }

    private void printAttendant() {
        for (Attendant a : aList) {
            System.out.printf("|%-15s|%-15s|%-15s|%-7s| \n",fNumber, a.getId(), a.getName(), a.getAge());
        }
    }

    private void printStaff() {
        for (Staff s : sList) {
            System.out.printf("|%-15s|%-15s|%-15s|%-7s| \n",fNumber, s.getId(), s.getName(), s.getAge());
        }
    }

    public void print() {
       printPilot();
       printAttendant();
       printStaff();
    }

}
