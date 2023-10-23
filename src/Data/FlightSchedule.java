package Data;

import java.util.ArrayList;
import java.util.List;

public class FlightSchedule {

    private String fNumber, desCity, departCity, arrTime, departTime;
    private int seat;

    List<String> s = new ArrayList<>();
    private int durationTime;

    public FlightSchedule() {
    }

    public FlightSchedule(String fNumber, String desCity, String departCity, String arrTime, String departTime,
            int seat, int durationTime) {
        this.fNumber = fNumber;
        this.desCity = desCity;
        this.departCity = departCity;
        this.arrTime = arrTime;
        this.departTime = departTime;
        this.seat = seat;
        this.durationTime = durationTime;
        this.s = new ArrayList<>();
    }

    public FlightSchedule(String fNumber, String desCity, String departCity, String arrTime, String departTime,
            int durationTime) {
        this.fNumber = fNumber;
        this.desCity = desCity;
        this.departCity = departCity;
        this.arrTime = arrTime;
        this.departTime = departTime;
        this.durationTime = durationTime;
        this.s = new ArrayList<>();
    }

    public String getfNumber() {
        return fNumber;
    }

    public void setfNumber(String fNumber) {
        this.fNumber = fNumber;
    }

    public String getDesCity() {
        return desCity;
    }

    public void setDesCity(String desCity) {
        this.desCity = desCity;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public List<String> getS() {
        return s;
    }

    public void setS(List<String> s) {
        this.s = s;
    }

    public int getNumberSeats() {
        int soldCount = 0;
        for (String seat : s) {
            if (seat.equalsIgnoreCase("X")) {
                soldCount++;
            }
        }
        return s.size() - soldCount;
    }

    public void getSeatWasSold(String se) {
        for (String seat : s) {
            if (!seat.equalsIgnoreCase(se)) {

            }
        }
    }

    // print out all seats 
    public void printSeats() {
        for (int i = 0; i < s.size(); i++) {
            System.out.format("%4s", s.get(i));
            // to the new line
            // one line allow 4 seats
            if ((i + 1) % 4 == 0) {
                System.out.println("");
            }
        }
    }

    public void setEmptySeatsToSold(String enterSeat) {
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).equalsIgnoreCase(enterSeat)) {
                s.set(i, "X");
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (String str : s) {
            result += (str + " ");
        }
        return fNumber + ", " + desCity + ", " + departCity + ", " + departTime + ", " + arrTime + ", " + result + ", "
                + durationTime;
    }

    // public void print() {
    // System.out.format("|%-15s|%-15s|%-18s|%-20s|%-20s|%-15d|%-15d|", fNumber,
    // departCity, desCity, departTime, arrTime, this.getNumberSeats(),
    // durationTime);
    // System.out.println("");
    // }

}
