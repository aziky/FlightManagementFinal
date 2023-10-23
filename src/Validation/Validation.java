package Validation;

import Data.Crew;
import Data.FlightSchedule;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Validation {

    private static Scanner sc = new Scanner(System.in);

    public String checkString(String msg) {
        String id;
        while (true) {
            System.out.print(msg);
            id = sc.nextLine().trim();
            if (id.length() == 0 || id.isBlank()) {
                System.out.println("Enter again");
                continue;
            }
            return id;
        }
    }

    public String checkStringWithRegex(String msg, String pattern, String a) {
        while (true) {
            try {
                System.out.print(msg);
                String str = sc.nextLine().toUpperCase().trim();
                if (str.isEmpty() || !str.matches(pattern)) {
                    throw new Exception();
                }
                return str;
            } catch (Exception e) {
                System.out.println(a);
            }
        }
    }

    public String checkDuplicate(String msg, ArrayList<FlightSchedule> fList) {
        while (true) {
            String id = checkStringWithRegex(msg, "^F\\d{4}$", "Wrong format. Please enter (Fxxxx)").toUpperCase()
                    .trim();
            int a = 0;
            for (FlightSchedule f : fList) {
                if (f.getfNumber().equals(id)) {
                    System.out.println("Id existed! Please enter again");
                    a = 1;
                    break;
                }
            }
            if (a == 1) {
                continue;
            }
            return id;
        }
    }

    public String checkDuplicateCrew(String msg, String pattern, String out, ArrayList<Crew> cList) {
        while (true) {
            String id = checkStringWithRegex(msg, pattern, out).toUpperCase().trim();
            int a = 0;
            for (Crew c : cList) {
                if (c.getId().equals(id)) {
                    System.out.println("Id existed!!Please enter again");
                    a = 1;
                    break;
                }
            }
            if (a == 1) {
                continue;
            }
            return id;
        }
    }

    public String checkContact(String msg, String pattern) {
        System.out.print(msg);
        while (true) {
            try {
                String str = sc.nextLine();
                if (str.isEmpty() || !str.matches(pattern)) {
                    throw new Exception();
                }
                return str;
            } catch (Exception e) {
                System.out.println("Phone number must be in 9 - 11 numbers!");
            }
        }
    }

    public int checkMenu(String msg, int max) {
        int value;
        while (true) {
            try {
                System.out.print(msg);
                value = Integer.parseInt(sc.nextLine());
                if (value <= 0) {
                    throw new Exception("Please input greater than 0!");
                } else if (value > max) {
                    throw new Exception("Please input again!");
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a int value!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean checkValidTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean checkValidTime2(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean checkValidTime(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date mDate = null;
        Date eDate = null;
        Date now = new Date();

        while (true) {
            try {
                mDate = sdf.parse(date1);
                eDate = sdf.parse(date2);
                if (mDate.compareTo(eDate) < 0 && mDate.compareTo(now) >= 0 && eDate.compareTo(now) >= 0) {
                    return true;
                } else {
                    System.out.println("You must input depart time before arrival time and in the future");
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    public int checkIntMinMax(String msg, int min, int max) {
        while (true) {
            try {
                System.out.println(msg);
                int number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max){
                    System.err.println("Please enter form " + min + ", to " + max);
                    continue;
                }
                return number;
            } catch (Exception e) {
                System.out.println("Please enter a number!!");
            }
        }
    }

    public int getInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                int number = Integer.parseInt(sc.nextLine());
                return number;
            } catch (Exception e) {
                System.out.println("Enter number again");
            }
        }
    }

    public String getString(String msg) {
        String id;
        System.out.print(msg);
        id = sc.nextLine().trim();
        return id;
    }

    public String checkYesOrNo(String msg) {
        String type;
        while (true) {
            System.out.print(msg);
            type = sc.nextLine().trim();
            if (type.isBlank()) {
                System.out.println("Please Input Y or N!!");
            }
            if (type.equalsIgnoreCase("y") || type.equalsIgnoreCase("n")) {
                return type;
            } else {
                System.out.println("Must input 1 in 2 choice is 'Y' or 'N' ! Please input again !");
            }
        }
    }

}
