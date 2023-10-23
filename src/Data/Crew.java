package Data;

public class Crew {
    private String id, name;
    private int age;

    public Crew() {
    }

    public Crew(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return  id + ": " + name + ": " + age ;
    }

    public void printEmployee() {
        System.out.format("|%-15s|%-15s|%-7d|" , id, name, age);
        System.out.println();
    }
     public void printPilot() {
        System.out.format("|PILOT|%-5s|%-10s|%-3d|" , id, name, age);
    }
     
     public void printAttendant() {
        System.out.format("|Attendant|%-5s|%-10s|%-3d|" , id, name, age);
    }
     
      public void printStaff() {
        System.out.format("|Staff|%-5s|%-10s|%-3d|" , id, name, age);
    }
}
