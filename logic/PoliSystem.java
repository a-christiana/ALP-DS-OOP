package logic;

import java.util.*;
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

    private PriorityQueue<Appointment> poliQueue;
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
                        a2.getPatient().getUrgencyLlevel(),
                        a1.getPatient().getUrgencyLlevel()));

        pharmacyQueue = new LinkedList<>();
    }







    public void start() {
        int choose = 0;
        do{
            System.out.println();
            System.out.print("1. Login\n2. Register\n3. Exit\nChoose Menu: ");
            choose = scan.nextInt();
            System.out.println();
            String usernametext;
            String passwordtext;
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
                    start();
                }
                if (!user.getPassword().equals(passwordtext)) {
                    System.out.println("Wrong password");
                    start();
                }
                System.out.println("Login success");
                if (user.getRole() == Role.ADMIN) {
                    UserAdmin();
                } else if (user.getRole() == Role.DOCTOR) {
                    UserDoctor((Doctor) user);
                } else if (user.getRole() == Role.PATIENT) {
                    UserPatient((Patient) user);
                }
                break;


            case 2:
                System.out.println("=== REGISTER PATIENT ===");
                System.out.print("Username: ");
                String usernameText = scan.next() + scan.nextLine();
                if (users.containsKey(usernameText)) {
                    System.out.println("Username sudah digunakan.");
                    start();
                }
                
                System.out.print("Password: ");
                String passwordText = scan.next() + scan.nextLine();
                String idUser = "U" + String.format("%05d", users.size() + 1);
                String idPatient = "P" + String.format("%05d", patients.size() + 1);
                
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

            case 3:
                break;
            default:
                System.out.println("Please input from 1-3");
                start();
            }
        } while (choose!=3);
    }







// USER ADMIN
// USER ADMIN
// USER ADMIN
    public void UserAdmin() {
        int input = 0;
        do{
            System.out.println();
            System.out.println("=== ADMIN MENU ====");
            System.out.println("1. Kelola Dokter");
            System.out.println("2. Kelola Obat");
            System.out.println("3. Kelola Pasien");
            System.out.println("4. Lihat Jadwal Appointment");
            System.out.println("5. Logout");
            System.out.print("Input: ");
            input = scan.nextInt();

            switch (input) {
                case 1:
                    doctorMenu();
                    break;

                case 2:
                    medicineMenu();
                    break;

                case 3:
                    patientMenu();
                    break;
                    
                case 4:
                    showAppointment();
                    break;
                    
                case 5:
                    start();
                default:
                    System.out.println("Please input from 1-5");
                    UserAdmin();
                }
            }while (input!=5);
        }




    public void doctorMenu() {
        int inp = 0;
        do{
            System.out.println();
            System.out.println("=== DOCTOR ===");
            System.out.println("1. View Doctor List");
            System.out.println("2. Add New Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. Back");
            System.out.print("Input: ");
            inp = scan.nextInt();
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
                    break;
                case 4:
                    UserAdmin();
                default:
                    System.out.println("Please input from 1-5");
                    doctorMenu();
            }
        }while(inp !=4);
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
    }



    public void addDoctorMenu() {
        System.out.println("=== NEW DOCTOR ===");
        String idUser = "U" + String.format("%05d", users.size() + 1);
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
        System.out.println();
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




    public void medicineMenu(){
        int inp = 0;
        do {
            System.out.println();
            System.out.println("=== MEDICINE ===");
            System.out.println("1. View Medicine List");
            System.out.println("2. Add New Medicine");
            System.out.println("3. Delete Medicine");
            System.out.println("4. Back");
            System.out.print("Input: ");
            inp = scan.nextInt();
            System.out.println();
            
            switch (inp) {
                case 1:
                    showMedicine();
                    break;
                case 2:
                    addMedicineMenu();
                    break;
                case 3:
                    deleteMedicineMenu();
                    break;
                case 4:
                    UserAdmin();
                default:
                    System.out.println("Please input from 1-4");
                    medicineMenu();
                }
            } while(inp != 4);
        }



    public void showMedicine() {
        System.out.println("=== MEDICINE LIST ===");
        int no = 1;
        if (medicines.size() == 0) {
            System.out.println("Belum ada obat");
        } else {
            for (Medicine med : medicines.values()) {
                System.out.print(no + ".");
                med.showDetail();
                System.out.println("--------------------");
                System.out.println();
                no++;
            }
        }
        medicineMenu();
    }



    public void addMedicineMenu() {
        System.out.println("=== NEW MEDICINE ===");
        System.out.print("Nama Obat: ");
        String namaobat = scan.next() + scan.nextLine();
        
        System.out.print("Harga Obat: ");
        int hargaobat = scan.nextInt();

        System.out.print("Controlled Substance (y/n): ");
        String controlled = scan.next() + scan.nextLine();

        String idMed = "M" + String.format("%03d", medicines.size() + 1);
        medicines.put(idMed, new Medicine(idMed, namaobat, hargaobat, m.isitControlled(controlled)));
        System.out.println();
        System.out.println("Obat berhasil ditambahkan.");
        System.out.println("Nama obat: " + namaobat);
        System.out.println("ID obat: " + idMed);
        medicineMenu();
    }



    public void deleteMedicineMenu() {
        System.out.println("=== DELETE MEDICINE ===");
        if (medicines.size() == 0) {
            System.out.println("Belum ada obat");
            System.out.println();
        } else {
            int no = 1;
            if (medicines.size() == 0) {
                System.out.println("Belum ada obat");
            } else {
                for (Medicine med : medicines.values()) {
                    System.out.print(no + ".");
                    med.showDetail();
                    System.out.println("--------------------");
                    System.out.println();
                    no++;
                }
            }
            System.out.print("Input ID Obat: ");
            String idMedicine = scan.next().toUpperCase() + scan.nextLine().toUpperCase();

            if (!medicines.containsKey(idMedicine)) {
                System.out.println("Obat dengan ID " + idMedicine + " tidak ditemukan.");
            } else {
                Medicine deletedMed = medicines.get(idMedicine);
                medicines.remove(idMedicine);
                System.out.println(deletedMed.getMedName() + " berhasil dihapus.");
            }
        }
        medicineMenu();
    }




    public void patientMenu() {
        int inp;
        do {
            System.out.println();
            System.out.println("=== PATIENT MENU ===");
            System.out.println("1. View Patient List");
            System.out.println("2. Add New Patient");
            System.out.println("3. Select Patient");
            System.out.println("4. Back");
            System.out.print("Input: ");
            inp = scan.nextInt();
            scan.nextLine();

            switch (inp) {
                case 1:
                    showPatient();
                    break;
                case 2:
                    addNewPatient();
                    break;
                case 3:
                    selectPatient();
                    break;
                case 4:
                    UserAdmin();
                default:
                    System.out.println("Please input from 1-4");
                    patientMenu();
            }
        } while (inp != 4);
    }



    public void showPatient() {
        System.out.println();
        System.out.println("=== PATIENT LIST ===");
        if (patients.isEmpty()) {
            System.out.println("Belum ada list pasien");
            return;
        }
        ArrayList<Patient> patientList = new ArrayList<>(patients.values());
        patientList.sort((p1, p2) -> p1.getIdPatient().compareTo(p2.getIdPatient()));
        int no = 1;
        for (Patient patient : patientList) {
            System.out.println();
            System.out.println(no + ". " + patient.getIdPatient() + "\nNIK: " + patient.getNik() + "\nNama Pasien: "
                    + patient.getFullName() + "\nUsername: " + patient.getUname() + "\nNomor telepon: "
                    + patient.getTelepon());
            no++;
        }
    }



    public void addNewPatient() {
        System.out.println();
        System.out.println("=== NEW PATIENT ===");
        System.out.print("Password: ");
        String passwordText = scan.next() + scan.nextLine();
        String idUser = "U" + String.format("%05d", users.size() + 1);
        String idPatient = "P" + String.format("%05d", patients.size() + 1);

        System.out.print("Full Name: ");
        String fullName = scan.next() + scan.nextLine();
        String username = generatePatientUsername(fullName);

        System.out.print("NIK: ");
        String nik = scan.next() + scan.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scan.next() + scan.nextLine();

        System.out.print("Urgency Level 1-5: ");
        int urgencyLevel = scan.nextInt();
        
        Patient newPatient = new Patient(
            idUser,
            username,
                passwordText,
                idPatient,
                fullName,
                nik,
                phoneNumber,
                urgencyLevel);
        addPatient(newPatient);
        System.out.println();
        System.out.println("Register berhasil!");
        System.out.println("User ID    : " + idUser);
        System.out.println("Patient ID : " + idPatient);
    }



    public void selectPatient() {
        int inp;
        do {
            System.out.println();
            System.out.println("=== SELECT PATIENT ===");
            System.out.println("1. View Patient List");
            System.out.println("2. Search Patient by ID or Name");
            System.out.println("3. Back");
            System.out.print("Input: ");
            inp = scan.nextInt();
            scan.nextLine();
            Patient selectedPatient = null;

            switch (inp) {
                case 1:
                    selectedPatient = choosePatientFromList();
                    break;
                case 2:
                    selectedPatient = searchPatientByIdOrName();
                    break;
                case 3:
                    patientMenu();
                    break;
                default:
                    System.out.println("Please input from 1-3");
                    selectPatient();
            }

            if (selectedPatient != null) {
                selectedPatientActionMenu(selectedPatient);
            }
            System.out.println();
        } while (inp != 3);
    }



    public Patient choosePatientFromList() {
        System.out.println();
        if (patients.isEmpty()) {
            System.out.println("Belum ada pasien.");
            return null;
        }

        ArrayList<Patient> patientList = new ArrayList<>(patients.values());
        patientList.sort((p1, p2) -> p1.getIdPatient().compareTo(p2.getIdPatient()));

        System.out.println("=== PATIENT LIST ===");
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            System.out.println((i + 1) + ". "
                    + patient.getIdPatient()
                    + " | "
                    + patient.getFullName());
        }
        System.out.print("Choose patient number: ");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 1 || choice > patientList.size()) {
            System.out.println("Pilihan tidak valid.");
            return null;
        }
        return patientList.get(choice - 1);
    }



    public Patient searchPatientByIdOrName() {
        System.out.println();
        System.out.print("Input patient ID or name: ");
        String keyword = scan.nextLine().toLowerCase();

        ArrayList<Patient> result = new ArrayList<>();
        for (Patient patient : patients.values()) {
            boolean matchId = patient.getIdPatient().toLowerCase().contains(keyword);
            boolean matchName = patient.getFullName().toLowerCase().contains(keyword);
            if (matchId || matchName) {
                result.add(patient);
            }
        }

        if (result.isEmpty()) {
            System.out.println("Pasien tidak ditemukan.");
            return null;
        }

        result.sort((p1, p2) -> p1.getIdPatient().compareTo(p2.getIdPatient()));
        System.out.println("=== SEARCH RESULT ===");
        for (int i = 0; i < result.size(); i++) {
            Patient patient = result.get(i);
            System.out.println((i + 1) + ". "
                    + patient.getIdPatient()
                    + " | "
                    + patient.getFullName());
        }

        System.out.print("Choose patient number: ");
        int choice = scan.nextInt();
        scan.nextLine();
        if (choice < 1 || choice > result.size()) {
            System.out.println("Pilihan tidak valid.");
            return null;
        }
        return result.get(choice - 1);
    }



    public void selectedPatientActionMenu(Patient patient) {
        int input = 0;
        do {
            System.out.println();
            System.out.println("=== SELECTED PATIENT ===");
            System.out.println("Patient ID : " + patient.getIdPatient());
            System.out.println("Name       : " + patient.getFullName());
            System.out.println();
            System.out.println("1. View Patient Detail");
            System.out.println("2. View Medical Records");
            System.out.println("3. Back");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();

            switch (input) {
                case 1:
                    patient.showDetail();
                    break;
                case 2:
                    viewMedicalRecordsByPatient(patient);
                    break;
                case 3:
                    selectPatient();
                    break;
                default:
                    System.out.println("Please input from 1-3");
                    selectedPatientActionMenu(patient);
            }
            System.out.println();
        } while (input != 3);
    }



    public void viewMedicalRecordsByPatient(Patient patient) {
        System.out.println();
        System.out.println("=== MEDICAL RECORDS ===");
        if (patient.getMedicalRecords().isEmpty()) {
            System.out.println("Pasien ini belum memiliki medical record.");
            return;
        }

        int no = 1;
        for (MedicalRecord record : patient.getMedicalRecords()) {
            System.out.println("Medical Record " + no);
            record.showDetail();
            System.out.println("----------------------");
            no++;
        }
    }




    public void showAppointment() {
        System.out.println("=== APPOINTMENT LIST ===");
        if (appointments.isEmpty()) {
            System.out.println("Belum ada Appointment.");
            return;
        }

        int no = 1;
        for (Appointment appointment : appointments) {
            System.out.println("Appointment " + no);
            System.out.println("Patient: " + appointment.getPatient().getFullName());
            System.out.println("Doctor: " + appointment.getDoctor().getFullName());
            System.out.println("Date: " + appointment.appointmentDate());
            System.out.println("Status: " + appointment.getAppointmentStatus());
            System.out.println("----------------------------------");
            no++;
        }
    }







// USER DOKTER
// USER DOKTER
// USER DOKTER
    public void UserDoctor(Doctor doctor){
    int input;
        do {
            System.out.println();
            System.out.println("=== DOCTOR MENU ===");
            System.out.println( "Hello, " + doctor.getFullName());
            System.out.println("1. View Queue");
            System.out.println("2. Select Patient");
            System.out.println("3. Logout");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (input) {
                case 1:
                    showDoctorQueue(doctor);
                    break;
                case 2:
                    selectPatientDoc(doctor);
                    break;
                case 3:
                    start();
                default:
                    System.out.println("Menu not available.");
                    UserDoctor(doctor);
                    break;
            }
        }while (input!=3);
    }



    public void showDoctorQueue(Doctor activeDoctor){
        System.out.println();
        System.out.println("=== MY PATIENT QUEUE ===");
        ArrayList<Appointment> doctorQueue = new ArrayList<>();
        for (Appointment appointment : poliQueue) {
            if (appointment.getDoctor().getIdDoctor().equals(activeDoctor.getIdDoctor())) {
                doctorQueue.add(appointment);
            }
        }
        if (doctorQueue.isEmpty()) {
            System.out.println("No queue available.");
            return;
        }

        doctorQueue.sort((a1, a2) -> Integer.compare(a1.getQueueNumber(), a2.getQueueNumber()));
        int no = 1;
        for (Appointment appointment : doctorQueue) {
            System.out.println(no + ". " + appointment);
            no++;
        }
    }


    public void selectPatientDoc(Doctor activeDoctor){
        int inp;
        do {
            System.out.println();
            System.out.println("=== SELECT PATIENT ===");
            System.out.println("1. View Patient List");
            System.out.println("2. Search Patient by ID or Name");
            System.out.println("3. Back");
            System.out.print("Input: ");
            inp = scan.nextInt();
            scan.nextLine();
            Patient selectedPatient = null;

            switch (inp) {
                case 1:
                    selectedPatient = choosePatientFromListDoc(activeDoctor);
                    break;
                case 2:
                    selectedPatient = searchPatientByIdOrNameDoc(activeDoctor);
                    break;
                case 3:
                    UserDoctor(activeDoctor);
                default:
                    System.out.println("Please input from 1-3");
                    selectPatientDoc(activeDoctor);
            }

            if (selectedPatient != null) {
                selectedPatientActionMenuDoc(activeDoctor, selectedPatient);
            }
            System.out.println();
        } while (inp != 3);
    }



    public Patient choosePatientFromListDoc(Doctor  doctor) {
        ArrayList<Patient> patientList = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor())) {
                Patient patient = appointment.getPatient();
                if (!patientList.contains(patient)) {
                    patientList.add(patient);
                }
            }
        }

        if (patientList.isEmpty()) {
            System.out.println("No patient found.");
            return null;
        }

        System.out.println();
        System.out.println("=== PATIENT LIST ===");
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            System.out.println((i + 1) + ". " + patient.getIdPatient() + " | " + patient.getFullName());
        }
        System.out.print("Choose patient: ");
        int choice = scan.nextInt();

        if (choice < 1 || choice > patientList.size()) {
            System.out.println("Invalid choice.");
            return null;
        }
        return patientList.get(choice - 1);
    }



    public Patient searchPatientByIdOrNameDoc(Doctor doctor) {
        System.out.print("Input patient ID or name: ");
        String keyword = scan.next().toLowerCase() + scan.nextLine().toLowerCase();
        ArrayList<Patient> result = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor())) {
                Patient patient = appointment.getPatient();
                boolean matchId = patient.getIdPatient().toLowerCase().contains(keyword);
                boolean matchName = patient.getFullName().toLowerCase().contains(keyword);
                if ((matchId || matchName) && !result.contains(patient)) {
                    result.add(patient);
                }
            }
        }

        if (result.isEmpty()) {
            System.out.println("Patient not found.");
            return null;
        }

        System.out.println();
        System.out.println("=== SEARCH RESULT ===");
        for (int i = 0; i < result.size(); i++) {
            Patient patient = result.get(i);
            System.out.println((i + 1) + ". " + patient.getIdPatient() + " | " + patient.getFullName());
        }
        System.out.print("Choose patient: ");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 1 || choice > result.size()) {
            System.out.println("Invalid choice.");
            return null;
        }
        return result.get(choice - 1);
    }



    public void selectedPatientActionMenuDoc(Doctor doctor, Patient patient) {
        int input;
        do {
            System.out.println();
            System.out.println("=== SELECTED PATIENT ===");
            System.out.println("Patient : " + patient.getFullName());
            System.out.println("1. View Medical Records");
            System.out.println("2. Create Medical Record & Prescription");
            System.out.println("3. Complete Appointment");
            System.out.println("4. Back");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();

            switch (input) {
                case 1:
                    viewMedicalRecordsByPatient(patient);
                    break;
                case 2:
                    if (!isPatientInDoctorQueue(doctor, patient)) {
                        System.out.println("Patient is not in queue.");
                        break;
                    }
                    createMedicalRecord(patient, doctor);
                    break;
                case 3:
                    if (!isPatientInDoctorQueue(doctor, patient)) {
                        System.out.println("Patient is not in queue.");
                        break;
                    }
                    completeAppointment(doctor, patient);
                    break;
                case 4:
                    selectPatientDoc(doctor);
                default:
                    System.out.println("Invalid input.");
                    selectedPatientActionMenuDoc(doctor, patient);
            }
        } while (input != 4);
    }




    public void createMedicalRecord(Patient selectedPatient, Doctor activeDoctor) {
        System.out.println();
        System.out.println("=== CREATE MEDICAL RECORD ===");
        int totalRecord = selectedPatient.getMedicalRecords().size() + 1;
        String idRecord = "MR" + String.format("%03d", totalRecord);
        
        System.out.print("Symptoms: ");
        String symptoms = scan.nextLine();
        
        System.out.print("Diagnosis: ");
        String diagnosis = scan.nextLine();
        
        System.out.print("Treatment: ");
        String treatment = scan.nextLine();
        
        Prescription prescription = null;
        System.out.print("Make a prescription? (y/n): ");
        String input = scan.next().toLowerCase() + scan.nextLine().toLowerCase();
        if (input.equals("y")) {
            prescription = createPrescription(selectedPatient, activeDoctor);
        }
        MedicalRecord record =
        new MedicalRecord(
            idRecord,
            selectedPatient,
            activeDoctor,
            symptoms,
            diagnosis,
            treatment,
            prescription
        );
        addMedicalRecord(record, selectedPatient);
        System.out.println("Medical record created.");
    }
    
    
    
    public Prescription createPrescription(Patient selectedPatient, Doctor activeDoctor){
        System.out.println();
        System.out.println("=== CREATE PRESCRIPTION ===");
        int totalPres = selectedPatient.getPrescriptions().size() + 1;
        String idPrescription = "PR" + String.format("%03d", totalPres);
        Prescription prescription = new Prescription(idPrescription, selectedPatient, activeDoctor);
        ArrayList<Medicine> medList = new ArrayList<>(medicines.values());
        for (int i = 0; i < medList.size(); i++) {
            Medicine med = medList.get(i);
            System.out.println((i + 1) + ". " + med.getMedName() + " | Rp" + med.getPrice());
        }

        while (true) {
            System.out.print("Choose medicine number (enter to finish): ");
            String input = scan.nextLine();
            if (input.isEmpty()) {
                break;
            }
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > medList.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            Medicine selectedMedicine = medList.get(choice - 1);
            System.out.print("Qty: ");
            int qty = scan.nextInt();
            scan.nextLine();
            prescription.addMedicine(selectedMedicine, qty);
            System.out.println(selectedMedicine.getMedName() + " added.");
        }

        if (prescription.getMedicines().isEmpty()) {
            System.out.println("No medicine added.");
            return null;
        }

        addPharmacyQueue(prescription);
        System.out.println();
        System.out.println("Prescription created.");
        return prescription;
    }



    public void completeAppointment(Doctor doctor, Patient patient) {
        Appointment target = null;
        for (Appointment appointment : poliQueue) {
            boolean sameDoctor = appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor());
            boolean samePatient = appointment.getPatient().getIdPatient().equals(patient.getIdPatient());
            if (sameDoctor && samePatient) {
                target = appointment;
                break;
            }
        }

        if (target != null) {
            target.completeAppointment();
            poliQueue.remove(target);
            System.out.println("Appointment completed.");
        }
    }







// USER PASIEN
// USER PASIEN
// USER PASIEN
    public void UserPatient(Patient patient) {
        int input;
        do {
            System.out.println();
            System.out.println("=== PATIENT MENU ===");
            System.out.println( "Hello, " + patient.getFullName());
            System.out.println("1. View Doctor List");
            System.out.println("2. Make Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. View Booking History");
            System.out.println("5. View Hospital Queue");
            System.out.println("6. View Pharmacy Queue");
            System.out.println("7. Buy Medicine");
            System.out.println("8. Profile");
            System.out.println("9. Logout");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (input) {
                case 1:
                    showDoctor();
                    break;

                case 2:
                    makeAppointment(patient);
                    break;

                case 3:
                    cancelAppointmentMenu(patient);
                    break;

                case 4:
                    viewAppointmentHistory(patient);
                    break;

                case 5:
                    showHospitalQueue();
                    break;

                case 6:
                    System.out.println("Pharmacy queue");
                    break;

                case 7:
                    System.out.println("buy medicine");
                    break;

                case 8:
                    System.out.println();
                    System.out.println("=== PROFILE ===");
                    patient.showDetail();
                    break;

                case 9:
                    System.out.println("Logout success.");
                    start();
                    break;

                default:
                    System.out.println("Menu not available.");
                    UserPatient(patient);
                    break;
            }
        } while (input != 9);
    }



    public void makeAppointment(Patient patient) {
        System.out.println("=== MAKE APPOINTMENT ===");
        if (doctors.isEmpty()) {
            System.out.println("Mohon maaf. Belum ada dokter.");
            return;
        }

        ArrayList<Doctor> doctorList = new ArrayList<>(doctors.values());
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            System.out.println((i + 1) + ". " + doctor.getFullName() + " - " + doctor.getSpecialization());
        }
        System.out.print("Choose Doctor: ");
        int chooseD = scan.nextInt();

        if (chooseD < 1 || chooseD > doctorList.size()) {
            System.out.println("Input error. Please Try Again.");
            return;
        }
        Doctor selectedD = doctorList.get(chooseD - 1);
        System.out.print("Complain: ");
        String complain = scan.next() + scan.nextLine();

        Appointment appointment = patient.creatAppointment(selectedD, complain);
        addAppointment(appointment);
        System.out.println();
        System.out.println("Appointment added successfully.");
        System.out.println();
        appointment.showDetail();
    }


    
    public void cancelAppointmentMenu(Patient patient) {
        System.out.println("=== CANCEL APPOINTMENT ===");
        if (patient.getAppointmentHistory().isEmpty()) {
            System.out.println("No appointment found.");
            return;
        }

        int no = 1;
        for (Appointment appointment : patient.getAppointmentHistory()) {
            System.out.println(no + ". " + appointment);
            no++;
        }
        System.out.print("Choose: ");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 1 || choice > patient.getAppointmentHistory().size()) {
            System.out.println("Invalid choice.");
            return;
        }
        Appointment appointment = patient.getAppointmentHistory().get(choice - 1);
        appointment.cancelAppointment();
        System.out.println("Appointment canceled");
    }


    
    public void viewAppointmentHistory(Patient patient) {
        System.out.println("=== BOOKING HISTORY ===");
        if (patient.getAppointmentHistory().isEmpty()) {
            System.out.println("No booking history.");
            return;
        }

        int no = 1;
        for (Appointment appointment : patient.getAppointmentHistory()) {
            System.out.println();
            System.out.println("Appointment " + no);
            appointment.showDetail();
            System.out.println("--------------------");
            no++;
        }
    }

    

    public void showHospitalQueue() {
        System.out.println("=== HOSPITAL QUEUE ===");
        if (poliQueue.isEmpty()) {
            System.out.println("No queue available.");
            return;
        }

        PriorityQueue<Appointment> tempQueue = new PriorityQueue<>(poliQueue);
        int no = 1;
        while (!tempQueue.isEmpty()) {
            Appointment appointment = tempQueue.poll();
            System.out.println(no + ". " + appointment);
            no++;
        }
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
    
    
    
    public boolean isPatientInDoctorQueue(Doctor doctor, Patient patient) {
        for (Appointment appointment : poliQueue) {
            boolean sameDoctor = appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor());
            boolean samePatient = appointment.getPatient().getIdPatient().equals(patient.getIdPatient());
            if (sameDoctor && samePatient) {
                return true;
            }
        }
        return false;
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
    
    
    
    public String generatePatientUsername(String fullName) {
        String firstName = fullName.split(" ")[0].toLowerCase();
        int count = 1;
        for (Patient patient : patients.values()) {
            if (patient.getUname().startsWith(firstName)) {
                count++;
            }
        }
        return firstName + String.format("%02d", count);
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
        poliQueue.offer(appointment);
    }
    
    public void addMedicalRecord(MedicalRecord record, Patient patient) {
        medicalRecords.add(record);
        patient.addMedicalRecord(record);
    }
    
    public void addPharmacyQueue(Prescription prescription) {
        pharmacyQueue.offer(prescription);
    }
}
