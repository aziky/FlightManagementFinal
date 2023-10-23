
package Data;

import java.util.List;

public class CrewM {
    private String fNumber;
    private List<Pilot> pList;
    private List<Attendant> aList;
    private List<Staff>sList;

    public CrewM() {
    }

    public CrewM( List<Pilot> pList, List<Attendant> aList, List<Staff> sList) {
        this.pList = pList;
        this.aList = aList;
        this.sList = sList;
    }
    
    public CrewM(String fNumber, List<Pilot> pList, List<Attendant> aList, List<Staff> sList) {
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
        return fNumber + "; " + pList + "; " + aList + "; " + sList  ;
    }
    public void print() {
         System.out.format("|%-15s|%-28s|%-28s|%-44s|", fNumber, pList, aList, sList);
    }
    
}
