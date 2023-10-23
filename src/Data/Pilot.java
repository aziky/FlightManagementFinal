
package Data;

public class Pilot extends Crew{

    public Pilot() {
    }

    public Pilot(String id, String name, int age) {
        super(id, name, age);
    }

    public void printPilot() {
        super.printPilot();
    }  

    @Override
    public String toString() {
        return  super.toString();
    }

}
