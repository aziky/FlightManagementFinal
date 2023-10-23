
package Data;

public class Reservation {
    private String name, id, fNumber,contact;
    private String seat = "Not book";

    public Reservation() {
    }

    public Reservation(String name, String id, String fNumber, String contact) {
        this.name = name;
        this.id = id;
        this.fNumber = fNumber;
        this.contact = contact;
    }
    
    public Reservation(String name, String id, String fNumber, String contact, String seat) {
        this.name = name;
        this.id = id;
        this.fNumber = fNumber;
        this.contact = contact;
        this.seat = seat;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfNumber() {
        return fNumber;
    }

    public void setfNumber(String fNumber) {
        this.fNumber = fNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
    

    @Override
    public String toString() {
        return  name + ", " + id + ", " + fNumber + ", " + contact + ", " + seat;
    }

    public void print() {
        System.out.format("|%-15s|%-15s|%-15s|%-15s|%-9s|", name,id,fNumber,contact, seat);
        System.out.println("");
    }

   
}
