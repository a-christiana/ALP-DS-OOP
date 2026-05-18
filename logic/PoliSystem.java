package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import model.*;

public class PoliSystem {
    Scanner scan = new Scanner(System.in);
    Medicine m = new Medicine();
    ArrayList<User> u = new ArrayList<>();
    private HashMap<String, User> users;
    private HashMap<String, Doctor> doctors;
    private HashMap<String, Patient> patients;
    private HashMap<String, Medicine> medicines;

    private ArrayList<Appointment> appointments;
    private ArrayList<MedicalRecord> medicalRecords;

    private PriorityQueue<Patient> poliQueue;
    private Queue<Prescription> pharmacyQueue;

    public PoliSystem() {
        users = new HashMap<>();
        doctors = new HashMap<>();
        patients = new HashMap<>();
        medicines = new HashMap<>();

        appointments = new ArrayList<>();
        medicalRecords = new ArrayList<>();

        poliQueue = new PriorityQueue<>(
                (a1, a2) -> Integer.compare(
                        a2.getUrgencyLlevel(),
                        a1.getUrgencyLlevel()));

        pharmacyQueue = new LinkedList<>();
    }

    public void start() {
        int choose = 0;
        System.out.print("1. Login\n2. Register\nChoose Menu: ");
        choose = scan.nextInt();
        System.out.println();
        String usernametext;
        String passwordtext;
        String roletext;
        switch (choose) {
            case 1:
                System.out.println();
                System.out.println("=== LOGIN ===");
                System.out.print("Username: ");
                usernametext = scan.next() + scan.nextLine();
                System.out.print("Password: ");
                passwordtext = scan.next() + scan.nextLine();

                User user = users.get(usernametext);

                if (user == null) {
                    System.out.println("username not found!");
                    return;
                }

                if (!user.getPassword().equals(passwordtext)) {
                    System.out.println("Wrong password");
                    return;
                }

                System.out.println("Login success");

                if (user.getRole() == Role.ADMIN) {

                    UserAdmin();

                } else if (user.getRole() == Role.DOCTOR) {

                    System.out.println("=== DOCTOR MENU ===");

                    user.showDetail();

                } else if (user.getRole() == Role.PATIENT) {

                    System.out.println("=== PATIENT MENU ===");

                    user.showDetail();
                }
                break;

            case 2:
                System.out.println("=== REGISTER PATIENT ===");

                System.out.print("Username: ");
                String usernameText = scan.next() + scan.nextLine();

                if (users.containsKey(usernameText)) {
                    System.out.println("Username sudah digunakan.");
                    return;
                }

                System.out.print("Password: ");
                String passwordText = scan.next() + scan.nextLine();

                String idUser = "U" + String.format("%05d", users.size() + 1);
                String idPatient = "P" + String.format("%03d", patients.size() + 1);

                System.out.print("Full Name: ");
                String fullName = scan.next() + scan.nextLine();

                System.out.print("NIK: ");
                String nik = scan.next() + scan.nextLine();

                System.out.print("Phone Number: ");
                String phoneNumber = scan.next() + scan.nextLine();

                System.out.print("Urgency Level 1-5: ");
                int urgencyLevel = scan.nextInt();

                Patient newPatient = new Patient(
                        idUser,
                        usernameText,
                        passwordText,
                        idPatient,
                        fullName,
                        nik,
                        phoneNumber,
                        urgencyLevel);

                addPatient(newPatient);

                System.out.println("Register berhasil!");
                System.out.println("User ID    : " + idUser);
                System.out.println("Patient ID : " + idPatient);
                start();
            default:
                break;
        }
    }

    public void UserAdmin() {
        int input = 0;
        System.out.println();
        System.out.println("=== ADMIN MENU ====");
        System.out.println("1. Kelola Dokter");
        System.out.println("2. Kelola Obat");
        System.out.println("3. Kelola Pasien");
        System.out.println("4. Lihat Jadwal Appointment");
        System.out.print("Input: ");
        input = scan.nextInt();
        System.out.println();

        switch (input) {
            case 1:
                doctorMenu();
                break;

            case 2:
                int in = 0;
                do {
                    System.out.println("=== MEDICINE ===");
                    System.out.println("1. View Medicine List");
                    System.out.println("2. Add New Medicine");
                    System.out.println("3. Delete Medicine");
                    System.out.print("Input: ");
                    in = scan.nextInt();
                    System.out.println();

                    switch (in) {
                        case 1:
                            System.out.println("=== MEDICINE LIST ===");
                            int no = 1;
                            for (Medicine med : medicines.values()) {
                                System.out.print(no + ". ");
                                med.showDetail();
                                no++;
                            }
                            System.out.println();
                            break;

                        case 2:
                            System.out.println("=== NEW MEDICINE ===");
                            System.out.print("Nama Obat: ");
                            String namaobat = scan.next() + scan.nextLine();
                            System.out.print("Harga Obat: ");
                            int hargaobat = scan.nextInt();
                            System.out.print("Controlled Substance (y/n): ");
                            String controlled = scan.next() + scan.nextLine();
                            String id = m.getIdMedicine() + medicines.size();
                            medicines.put(id, new Medicine(id, namaobat, hargaobat, m.isControlled()));
                            System.out.println();
                            break;

                        case 3:
                            System.out.println("=== DELETE MEDICINE ===");
                            System.out.print("Pilih ID: ");
                            String idhapus = scan.next() + scan.nextLine().toUpperCase();
                            String namehapus = medicines.get(idhapus).medicineName;
                            medicines.remove(idhapus);
                            System.out.println(namehapus + " telah dihapus");
                            break;
                    }

                } while (in != 0);

                break;
        }
    }

    public void doctorMenu() {
        System.out.println();
        System.out.println("=== DOCTOR ===");
        System.out.println("1. View Doctor List");
        System.out.println("2. Add New Doctor");
        System.out.println("3. Delete Doctor");
        System.out.println("4. Back");
        System.out.print("Input: ");
        int inp = scan.nextInt();
        scan.nextLine();
        System.out.println();

        switch (inp) {
            case 1:
                showDoctor();
                break;

            case 2:
                addDoctorMenu();
                break;

            case 3:
                deleteDoctorMenu();

            case 4:
                UserAdmin();
            default:
                throw new AssertionError();
        }
    }

    public void showDoctor() {
        System.out.println("=== DOCTOR LIST ===");

        if (doctors.isEmpty()) {
            System.out.println("Belum ada dokter.");
            return;
        }

        int no = 1;

        for (Doctor doctor : doctors.values()) {
            System.out.print(no + ".");
            doctor.showDetail();
            System.out.println("--------------------");
            System.out.println();
            no++;
        }

        doctorMenu();
    }

    public void showSpecialization() {
        System.out.println();
        System.out.println("=== SPECIALIZATION LIST ===");
        System.out.println("1. Cardiologist");
        System.out.println("2. Pediatrician");
        System.out.println("3. Dentist");
        System.out.println("4. Dermatologist");
        System.out.println("5. General Practitioner");
        System.out.println();
    }

    public void addDoctorMenu() {
        System.out.println("=== NEW DOCTOR ===");
        String idUser = "U" + String.format("%03d", users.size() + 1);
        String idDoctor = "D" + String.format("%03d", doctors.size() + 1);

        showSpecialization();
        System.out.print("Specialization: ");
        String specialization = scan.next() + scan.nextLine();

        String username = generateDocUsername(specialization);

        System.out.print("Password: ");
        String password = scan.next() + scan.nextLine();

        System.out.print("Full Name: ");
        String fullName = scan.next() + scan.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scan.next() + scan.nextLine();

        Doctor doctor = new Doctor(
                idUser,
                username,
                password,
                idDoctor,
                fullName,
                phoneNumber,
                specialization);

        addDoctor(doctor);

        System.out.println("Dokter berhasil ditambahkan.");
        System.out.println("Username dokter: " + username);
        System.out.println("ID dokter: " + idDoctor);

        doctorMenu();
    }

    public void deleteDoctorMenu() {
        System.out.println();
        System.out.println("=== DELETE DOCTOR ===");
        int no = 1;
        for (Doctor doctor : doctors.values()) {
            System.out.print(no + ".");
            doctor.showDetail();
            System.out.println("--------------------");
            System.out.println();
            no++;
        }
        System.out.print("Input ID Doctor: ");
        String idDoctor = scan.nextLine().toUpperCase();

        if (!doctors.containsKey(idDoctor)) {
            System.out.println("Doctor dengan ID " + idDoctor + " tidak ditemukan.");
            return;
        }

        Doctor deletedDoctor = doctors.get(idDoctor);

        doctors.remove(idDoctor);
        users.remove(deletedDoctor.getUname());

        System.out.println(deletedDoctor.getFullName() + " berhasil dihapus.");

        doctorMenu();
    }

    public String generateDocUsername(String specialization) {
        String code;

        if (specialization.equalsIgnoreCase("Cardiologist")) {
            code = "jantung";
        } else if (specialization.equalsIgnoreCase("Pediatrician")) {
            code = "anak";
        } else if (specialization.equalsIgnoreCase("Dentist")) {
            code = "gigi";
        } else if (specialization.equalsIgnoreCase("Dermatologist")) {
            code = "kulit";
        } else if (specialization.equalsIgnoreCase("General Practitioner")) {
            code = "umum";
        } else {
            code = specialization.toLowerCase().replace(" ", "");
        }

        int count = 1;

        for (Doctor doctor : doctors.values()) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                count++;
            }
        }

        return "dokter" + code + String.format("%02d", count);
    }

    public void addUser(User user) {

        users.put(user.getUname(), user);
    }

    public void addDoctor(Doctor doctor) {

        doctors.put(doctor.getIdDoctor(), doctor);

        users.put(doctor.getUname(), doctor);
    }

    public void addPatient(Patient patient) {

        patients.put(patient.getIdPatient(), patient);

        users.put(patient.getUname(), patient);
    }

    public void addMedicine(Medicine medicine) {

        medicines.put(medicine.getIdMedicine(), medicine);
    }

    public void addAppointment(Appointment appointment) {

        appointments.add(appointment);

        poliQueue.offer(appointment.getPatient());
    }

    public void addMedicalRecord(
            MedicalRecord record,
            Patient patient) {

        medicalRecords.add(record);

        patient.addMedicalRecord(record);
    }

    public void addPharmacyQueue(
            Prescription prescription) {

        pharmacyQueue.offer(prescription);
    }
}