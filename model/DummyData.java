package model;

import java.time.LocalDate;
import java.time.LocalTime;

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

                Doctor d1 = new Doctor("U00001", "dokterjantung01", "123", "D001", "Dr. Jonathan Hartono", "08111111111111",
                        "Cardiologist");
                Doctor d2 = new Doctor("U00002", "dokterjantung02", "123", "D002", "Dr. Michael Wijaya", "08111111111112",
                        "Cardiologist");
                Doctor d3 = new Doctor("U00003", "dokterAnak01", "123", "D003", "Dr. Mellisa Winata", "08111111111113",
                        "Pediatrician");
                Doctor d4 = new Doctor("U00004", "dokterAnak02", "123", "D004", "Dr. Kevin Sebastian", "08111111111114",
                        "Pediatrician");
                Doctor d5 = new Doctor("U00005", "doktergigi01", "123", "D005", "Dr. Daniel Kurniawan", "08111111111115",
                        "Dentist");
                Doctor d6 = new Doctor("U00006", "doktergigi02", "123", "D006", "Dr. Olivia Gunawan", "08111111111116",
                        "Dentist");
                Doctor d7 = new Doctor("U00007", "dokterkulit01", "123", "D007", "Dr. Alice Tan", "08111111111117",
                        "Dermatologist");
                Doctor d8 = new Doctor("U00008", "dokterkulit02", "123", "D008", "Dr. Vanessa Halim", "08111111111118",
                        "Dermatologist");
                Doctor d9 = new Doctor("U00009", "dokterUmum01", "123", "D009", "Dr. Christine Lim", "08111111111119",
                        "General Practitioner");
                Doctor d10 = new Doctor("U00010", "dokterUmum02", "123", "D010", "Dr. Vincent Wijoyo", "08111111111110",
                        "General Practitioner");

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

                Patient p1 = new Patient("U00011", "angie01", "123", "P00001", "Angie Michelle Grafiella", "357812340001",
                        "0821111111");
                Patient p2 = new Patient("U00012", "angel01", "123", "P00002", "Angelina Christiana", "357812340002",
                        "0821111112");
                Patient p3 = new Patient("U00013", "callista01", "123", "P00003", "Callista Nathania Andrea Nugroho",
                        "357812340003", "0821111113");
                Patient p4 = new Patient("U00014", "kevin01", "123", "P00004", "Kevin Sebastian Wijaya", "357812340004",
                        "0821111114");
                Patient p5 = new Patient("U00015", "jessica01", "123", "P00005", "Jessica Amanda Putri", "357812340005",
                        "0821111115");

                x.addPatient(p1);
                x.addPatient(p2);
                x.addPatient(p3);
                x.addPatient(p4);
                x.addPatient(p5);

                // Influenza
                Medicine m1 = new Medicine("M001", "Paracetamol", 12000, false);
                Medicine m2 = new Medicine("M002", "Oseltamivir", 45000, true);

                // Alergi / gatal bentol merah
                Medicine m3 = new Medicine("M003", "Cetirizine", 18000, false);
                Medicine m4 = new Medicine("M004", "Hydrocortisone Cream", 25000, false);

                // Karies gigi dan radang gusi
                Medicine m5 = new Medicine("M005", "Amoxicillin", 30000, true);
                Medicine m6 = new Medicine("M006", "Mefenamic Acid", 20000, true);

                // ISPA anak
                Medicine m7 = new Medicine("M007", "Paracetamol Syrup", 17000, false);
                Medicine m8 = new Medicine("M008", "Vitamin C Kids", 15000, false);

                // Hipertensi dan angina ringan
                Medicine m9 = new Medicine("M009", "Amlodipine", 40000, true);
                Medicine m10 = new Medicine("M010", "Nitroglycerin", 55000, true);

                x.addMedicine(m1);
                x.addMedicine(m2);
                x.addMedicine(m3);
                x.addMedicine(m4);
                x.addMedicine(m5);
                x.addMedicine(m6);
                x.addMedicine(m7);
                x.addMedicine(m8);
                x.addMedicine(m9);
                x.addMedicine(m10);

                Prescription pr1 = new Prescription("PR001", p1, d10);
                Prescription pr2 = new Prescription("PR001", p2, d8);
                Prescription pr3 = new Prescription("PR001", p3, d5);
                Prescription pr4 = new Prescription("PR001", p4, d4);
                Prescription pr5 = new Prescription("PR001", p5, d1);

                pr1.addMedicine(m1, 2);
                pr1.addMedicine(m2, 1);
                pr2.addMedicine(m3, 3);
                pr2.addMedicine(m4, 1);
                pr3.addMedicine(m5, 1);
                pr3.addMedicine(m6, 1);
                pr4.addMedicine(m7, 1);
                pr4.addMedicine(m8, 4);
                pr5.addMedicine(m9, 1);
                pr5.addMedicine(m10, 1);

                p1.addPrescription(pr1);
                p2.addPrescription(pr2);
                p3.addPrescription(pr3);
                p4.addPrescription(pr4);
                p5.addPrescription(pr5);

                x.addPharmacyQueue(pr1);
                x.addPharmacyQueue(pr2);
                x.addPharmacyQueue(pr3);
                x.addPharmacyQueue(pr4);
                x.addPharmacyQueue(pr5);

                MedicalRecord mr1 = new MedicalRecord("MR001", p1, d10, "Pusing, demam, dan nyeri otot", "Influenza",
                        "Pemberian obat penurun demam dan istirahat cukup", pr1);
                MedicalRecord mr2 = new MedicalRecord("MR001", p2, d8, "Gatal dan bentol merah pada tangan dan leher",
                        "Gatal dan bentol merah pada tangan dan leher", "Pemberian antihistamin dan salep anti inflamasi", pr2);
                MedicalRecord mr3 = new MedicalRecord("MR001", p3, d5, "Nyeri gigi bagian belakang dan gusi bengkak",
                        "Karies gigi dan radang gusi", "Pembersihan karang gigi dan pemberian antibiotik", pr3);
                MedicalRecord mr4 = new MedicalRecord("MR001", p4, d4, "Demam tinggi, batuk, dan nafsu makan menurun",
                        "Infeksi Saluran Pernapasan Atas pada anak", "Pemberian sirup penurun demam dan vitamin anak", pr4);
                MedicalRecord mr5 = new MedicalRecord("MR001", p5, d1, "Nyeri dada ringan dan sesak napas saat aktivitas berat",
                        "Hipertensi dan angina ringan",
                        "Pemberian obat penurun tekanan darah dan anjuran mengurangi aktivitas berat", pr5);

                x.addMedicalRecord(mr1, p1);
                x.addMedicalRecord(mr2, p2);
                x.addMedicalRecord(mr3, p3);
                x.addMedicalRecord(mr4, p4);
                x.addMedicalRecord(mr5, p5);

                d1.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(1), LocalTime.of(8, 0), LocalTime.of(12, 0)));
                d1.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(2), LocalTime.of(13, 0), LocalTime.of(16, 0)));
                d2.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(13, 0)));
                d3.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(14, 0)));
                d4.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(2), LocalTime.of(8, 0), LocalTime.of(11, 0)));
                d5.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(1), LocalTime.of(7, 0), LocalTime.of(15, 0)));
                d6.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(2), LocalTime.of(9, 0), LocalTime.of(12, 0)));
                d7.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(1), LocalTime.of(13, 0), LocalTime.of(16, 0)));
                d8.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(2), LocalTime.of(10, 0), LocalTime.of(14, 0)));
                d9.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(1), LocalTime.of(8, 0), LocalTime.of(11, 0)));
                d10.addSchedule(new DoctorSchedule(LocalDate.now().plusDays(2), LocalTime.of(14, 0), LocalTime.of(17, 0)));

                DoctorSchedule s1 = d1.getDoctorSchedules().get(0);
                DoctorSchedule s2 = d2.getDoctorSchedules().get(0);
                DoctorSchedule s3 = d3.getDoctorSchedules().get(0);
                DoctorSchedule s4 = d4.getDoctorSchedules().get(0);
                DoctorSchedule s5 = d5.getDoctorSchedules().get(0);

                Appointment a1 = new Appointment("A001", d1, p1, s1, s1.getNextAvailableTime(), "Chest Pain");
                Appointment a2 = new Appointment("A002", d2, p2, s2, s2.getNextAvailableTime(), "High Fever");
                Appointment a3 = new Appointment("A003", d3, p3, s3, s3.getNextAvailableTime(), "Tooth Pain");
                Appointment a4 = new Appointment("A004", d4, p4, s4, s4.getNextAvailableTime(), "Skin Rash");
                Appointment a5 = new Appointment("A005", d5, p5, s5, s5.getNextAvailableTime(), "Flu");

                x.addAppointment(a1);
                x.addAppointment(a2);
                x.addAppointment(a3);
                x.addAppointment(a4);
                x.addAppointment(a5);

                EmergencyCase e1 = new EmergencyCase("E001", p1, "Sesak Napas");
                EmergencyCase e2 = new EmergencyCase("E002", p2, "Patah Tulang");
                EmergencyCase e3 = new EmergencyCase("E003", p3, "Serangan Jantung");
                EmergencyCase e4 = new EmergencyCase("E004", p4, "Demam Tinggi");

                x.addEmergencyCase(e1);
                x.addEmergencyCase(e2);
                x.addEmergencyCase(e3);
                x.addEmergencyCase(e4);

                Pharmacist pharmacist = new Pharmacist("U005", "apoteker01", "123", "PH001", "Sinta Permata");
                x.addPharmacist(pharmacist);

        }
}
