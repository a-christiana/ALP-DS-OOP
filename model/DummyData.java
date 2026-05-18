package model;

import logic.PoliSystem;

public class DummyData {
    public static void dummyData(PoliSystem x) {
        User admin = new User("U000", "Admin", "admin123", Role.ADMIN) {
            @Override
            public void showDetail() {
                System.out.println("ID : " + idUser + "\nName : " + username);
            }
        };
        x.addUser(admin);

        Doctor d1 = new Doctor("U00001", "dokterjantung01", "123", "D001", "Dr. Jonathan Hartono", "08111111111111", "Cardiologist");
        Doctor d2 = new Doctor("U00002", "dokterjantung02", "123", "D002", "Dr. Michael Wijaya", "08111111111112", "Cardiologist");
        Doctor d3 = new Doctor("U00003", "dokterAnak01", "123", "D003", "Dr. Mellisa Winata", "08111111111113", "Pediatrician");
        Doctor d4 = new Doctor("U00004", "dokterAnak02", "123", "D004", "Dr. Kevin Sebastian", "08111111111114", "Pediatrician");
        Doctor d5 = new Doctor("U00005", "doktergigi01", "123", "D005", "Dr. Daniel Kurniawan", "08111111111115", "Dentist");
        Doctor d6 = new Doctor("U00006", "doktergigi02", "123", "D006", "Dr. Olivia Gunawan", "08111111111116", "Dentist");
        Doctor d7 = new Doctor("U00007", "dokterkulit01", "123", "D007", "Dr. Alice Tan", "08111111111117", "Dermatologist");
        Doctor d8 = new Doctor("U00008", "dokterkulit02", "123", "D008", "Dr. Vanessa Halim", "08111111111118", "Dermatologist");
        Doctor d9 = new Doctor("U00009", "dokterUmum01", "123", "D009", "Dr. Christine Lim", "08111111111119", "General Practitioner");
        Doctor d10 = new Doctor("U00010", "dokterUmum02", "123", "D010", "Dr. Vincent Wijoyo", "08111111111110", "General Practitioner");

        x.addDoctor(d1);
        x.addDoctor(d2);
        x.addDoctor(d3);
        x.addDoctor(d4);
        x.addDoctor(d5);
        x.addDoctor(d6);
        x.addDoctor(d7);
        x.addDoctor(d8);
        x.addDoctor(d9);
        x.addDoctor(d10);

        Patient p1 = new Patient("U00011", "angie01", "123", "P00001", "Angie Michelle Grafiella", "357812340001", "0821111111", 0);
        Patient p2 = new Patient("U00012", "angelina01", "123", "P00002", "Angelina Christiana", "357812340002", "0821111112", 0);
        Patient p3 = new Patient("U00013", "callista01", "123", "P00003", "Callista Nathania Andrea Nugroho", "357812340003", "0821111113", 0);  
        Patient p4 = new Patient("U00014", "kevin01", "123", "P00004", "Kevin Sebastian Wijaya", "357812340004", "0821111114", 0);   
        Patient p5 = new Patient("U00015", "jessica01", "123", "P00005", "Jessica Amanda Putri", "357812340005", "0821111115", 0);   
        
        x.addPatient(p1);
        x.addPatient(p2);
        x.addPatient(p3);
        x.addPatient(p4);
        x.addPatient(p5);
    }
}
