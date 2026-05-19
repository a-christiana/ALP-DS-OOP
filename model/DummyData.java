package model;

import java.util.ArrayList;

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

        Prescription pr1 = null;
        Prescription pr2 = null;
        Prescription pr3 = null;
        Prescription pr4 = null;
        Prescription pr5 = null;

        MedicalRecord mr1 = new MedicalRecord("MR001", p1, d10, "Pusing, demam, dan nyeri otot", "Influenza",  "Pemberian obat penurun demam dan istirahat cukup", pr1);
        MedicalRecord mr2 = new MedicalRecord("MR002", p2, d8, "Gatal dan bentol merah pada tangan dan leher", "Gatal dan bentol merah pada tangan dan leher",  "Pemberian antihistamin dan salep anti inflamasi", pr2);
        MedicalRecord mr3 = new MedicalRecord("MR003", p3, d5, "Nyeri gigi bagian belakang dan gusi bengkak",  "Karies gigi dan radang gusi",    "Pembersihan karang gigi dan pemberian antibiotik", pr3);
        MedicalRecord mr4 = new MedicalRecord("MR004", p4, d4, "Demam tinggi, batuk, dan nafsu makan menurun",   "Infeksi Saluran Pernapasan Atas pada anak", "Pemberian sirup penurun demam dan vitamin anak", pr4);
        MedicalRecord mr5 = new MedicalRecord("MR005", p5, d1, "Nyeri dada ringan dan sesak napas saat aktivitas berat",  "Hipertensi dan angina ringan",  "Pemberian obat penurun tekanan darah dan anjuran mengurangi aktivitas berat", pr5);

        x.addMedicalRecord(mr1, p1);
        x.addMedicalRecord(mr2, p2);
        x.addMedicalRecord(mr3, p3);
        x.addMedicalRecord(mr4, p4);
        x.addMedicalRecord(mr5, p5);
        ArrayList<Medicine> medicines = new ArrayList<>();

        Medicine m1 = new Medicine("M001", "Aspirin", 15000, true);
        Medicine m2 = new Medicine("M002", "Atorvastatin", 35000, true);
        Medicine m3 = new Medicine("M003", "Bisoprolol", 28000, true);

        Medicine m4 = new Medicine("M004", "Paracetamol Syrup", 18000, false);
        Medicine m5 = new Medicine("M005", "Amoxicillin Syrup", 22000, true);
        Medicine m6 = new Medicine("M006", "Vitamin C Kids", 12000, false);

        Medicine m7 = new Medicine("M007", "Mefenamic Acid", 17000, true);
        Medicine m8 = new Medicine("M008", "Clindamycin", 26000, true);
        Medicine m9 = new Medicine("M009", "Mouthwash", 20000, false);

        Medicine m10 = new Medicine("M010", "Acne Cream", 30000, false);
        Medicine m11 = new Medicine("M011", "Antifungal Cream", 27000, false);
        Medicine m12 = new Medicine("M012", "Cetirizine", 15000, false);

        Medicine m13 = new Medicine("M013", "Ibuprofen", 14000, false);
        Medicine m14 = new Medicine("M014", "Cough Syrup", 19000, false);
        Medicine m15 = new Medicine("M015", "Antacid", 13000, false);

        medicines.add(m1);
        medicines.add(m2);
        medicines.add(m3);
        medicines.add(m4);
        medicines.add(m5);
        medicines.add(m6);
        medicines.add(m7);
        medicines.add(m8);
        medicines.add(m9);
        medicines.add(m10);
        medicines.add(m11);
        medicines.add(m12);
        medicines.add(m13);
        medicines.add(m14);
        medicines.add(m15);
    }
}
