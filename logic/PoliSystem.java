package logic;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private HashMap<String, Pharmacist> pharmacists;

    private ArrayList<Appointment> appointments;
    private ArrayList<MedicalRecord> medicalRecords;

    private Queue<Appointment> poliQueue;
    private Queue<Prescription> pharmacyQueue;
    private PriorityQueue<EmergencyCase> emergencyQueue;
    private HashMap<String, Appointment> currentPatient;
    private int medicineOrderCounter = 1;

    public PoliSystem() {
        users = new HashMap<>();
        doctors = new HashMap<>();
        patients = new HashMap<>();
        pharmacists = new HashMap<>();
        medicines = new HashMap<>();
        appointments = new ArrayList<>();
        medicalRecords = new ArrayList<>();
        poliQueue = new LinkedList<>();
        pharmacyQueue = new LinkedList<>();
        emergencyQueue = new PriorityQueue<>((e1, e2) -> {
            int compareTriage = Integer.compare(e1.getTriageLevel().getPriority(), e2.getTriageLevel().getPriority());
            if (compareTriage != 0) {
                return compareTriage;
            }
            return e1.getArrivalTime().compareTo(e2.getArrivalTime());
        });
        currentPatient = new HashMap<>();
    }





    public void start() {
        int choose = 0;
        do {
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
                    } else if (user.getRole() == Role.PHARMACIST) {
                        userPharmacist((Pharmacist) user);
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

                    Patient newPatient = new Patient(
                            idUser,
                            usernameText,
                            passwordText,
                            idPatient,
                            fullName,
                            nik,
                            phoneNumber);
                    addPatient(newPatient);
                    System.out.println("Register berhasil!");
                    System.out.println("User ID    : " + idUser);
                    System.out.println("Patient ID : " + idPatient);
                    start();

                case 3:
                    return;
                default:
                    System.out.println("Please input from 1-3");
                    start();
            }
        } while (choose != 3);
    }











    // USER ADMIN
    // USER ADMIN
    // USER ADMIN
    public void UserAdmin() {
        int input = 0;
        do {
            System.out.println();
            System.out.println("=== ADMIN MENU ====");
            System.out.println("1. Manage Doctor");
            System.out.println("2. Manage Medicne");
            System.out.println("3. Manage Patient");
            System.out.println("4. View Appointment Schedule");
            System.out.println("5. IGD");
            System.out.println("6. Logout");
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
                    emergencyMenu();
                    break;

                case 6:
                    start();
                    break;
                default:
                    System.out.println("Please input from 1-5");
                    UserAdmin();
            }
        } while (input != 5);
    }



    public void doctorMenu() {
        int inp = 0;
        do {
            System.out.println();
            System.out.println("=== DOCTOR ===");
            System.out.println("1. View Doctor List");
            System.out.println("2. Add New Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. Manage Doctor Schedule");
            System.out.println("5. Back");
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
                    manageDoctorScheduleMenu();
                    break;
                case 5:
                    UserAdmin();
                    break;
                default:
                    System.out.println("Please input from 1-5");
                    doctorMenu();
            }
        } while (inp != 4);
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



    public void manageDoctorScheduleMenu() {
        System.out.println();
        System.out.println("=== MANAGE DOCTOR SCHEDULE ===");
        if (doctors.isEmpty()) {
            System.out.println("Belum ada dokter.");
            return;
        }

        ArrayList<Doctor> doctorList = new ArrayList<>(doctors.values());
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            System.out.println((i + 1) + ". " + doctor.getFullName() + " - " + doctor.getSpecialization());
        }

        System.out.print("Choose Doctor: ");
        int chooseDoctor = scan.nextInt();
        scan.nextLine();
        if (chooseDoctor < 1 || chooseDoctor > doctorList.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        Doctor selectedDoctor = doctorList.get(chooseDoctor - 1);
        int input = 0;
        do {
            System.out.println();
            System.out.println("=== SCHEDULE MENU ===");
            System.out.println("Doctor: " + selectedDoctor.getFullName());
            System.out.println("1. View Schedule");
            System.out.println("2. Add Schedule");
            System.out.println("3. Delete Schedule");
            System.out.println("4. Back");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            switch (input) {
                case 1:
                    selectedDoctor.viewSchedules();
                    break;
                case 2:
                    addDoctorScheduleMenu(selectedDoctor);
                    break;
                case 3:
                    deleteDoctorScheduleMenu(selectedDoctor);
                    break;
                case 4:
                    doctorMenu();
                    break;
                default:
                    System.out.println("Please input from 1-4");
            }
        } while (input != 4);
    }



    public void addDoctorScheduleMenu(Doctor doctor) {
        System.out.println();
        System.out.println("=== ADD DOCTOR SCHEDULE ===");
        System.out.print("Date Year  : ");
        int year = scan.nextInt();
        System.out.print("Date Month : ");
        int month = scan.nextInt();
        System.out.print("Date Day   : ");
        int day = scan.nextInt();
        System.out.print("Start Hour : ");
        int startHour = scan.nextInt();
        System.out.print("Start Min  : ");
        int startMin = scan.nextInt();
        System.out.print("End Hour   : ");
        int endHour = scan.nextInt();
        System.out.print("End Min    : ");
        int endMin = scan.nextInt();
        scan.nextLine();
        DoctorSchedule schedule = new DoctorSchedule(
                LocalDate.of(year, month, day),
                LocalTime.of(startHour, startMin),
                LocalTime.of(endHour, endMin));
        doctor.addSchedule(schedule);
        System.out.println("Schedule added successfully.");
    }



    public void deleteDoctorScheduleMenu(Doctor doctor) {
        System.out.println();
        System.out.println("=== DELETE DOCTOR SCHEDULE ===");
        if (doctor.getDoctorSchedules().isEmpty()) {
            System.out.println("No schedule available.");
            return;
        }

        ArrayList<DoctorSchedule> scheduleList = doctor.getDoctorSchedules();
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.print((i + 1) + ". ");
            scheduleList.get(i).showDetail();
        }
        System.out.print("Choose schedule: ");
        int choice = scan.nextInt();
        scan.nextLine();
        if (choice < 1 || choice > scheduleList.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        scheduleList.remove(choice - 1);
        System.out.println("Schedule deleted successfully.");
    }



    public void medicineMenu() {
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
                    return;
                default:
                    System.out.println("Please input from 1-4");
                    medicineMenu();
            }
        } while (inp != 4);
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

        Patient newPatient = new Patient(
                idUser,
                username,
                passwordText,
                idPatient,
                fullName,
                nik,
                phoneNumber);
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
        System.out.println();
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
            System.out.println();
            no++;
        }
    }



    public void emergencyMenu() {
        int input = 0;
        do {
            System.out.println();
            System.out.println("=== IGD / EMERGENCY MENU ===");
            System.out.println("1. Add Emergency Case");
            System.out.println("2. View Emergency Queue");
            System.out.println("3. Handle Emergency Patient");
            System.out.println("4. Back");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            switch (input) {
                case 1:
                    addEmergencyCaseMenu();
                    break;
                case 2:
                    showEmergencyQueue();
                    break;
                case 3:
                    handleEmergencyPatient();
                    break;
                case 4:
                    UserAdmin();
                    break;
                default:
                    System.out.println("Please input from 1-4");
            }
        } while (input != 4);
    }



    public void addEmergencyCaseMenu() {
        System.out.println();
        System.out.println("=== ADD EMERGENCY CASE ===");
        Patient patient = choosePatientFromList();
        if (patient == null) {
            return;
        }
        System.out.print("Complaint: ");
        String complaint = scan.next() + scan.nextLine();

        String emergencyId = "E" + String.format("%03d", emergencyQueue.size() + 1);
        EmergencyCase emergencyCase = new EmergencyCase(
                emergencyId,
                patient,
                complaint);
        emergencyQueue.offer(emergencyCase);
        System.out.println();
        System.out.println("Emergency case added successfully.");
        emergencyCase.showDetail();
    }



    public void showEmergencyQueue() {
        System.out.println();
        System.out.println("=== IGD QUEUE ===");
        if (emergencyQueue.isEmpty()) {
            System.out.println("No emergency queue.");
            return;
        }
        PriorityQueue<EmergencyCase> tempQueue = new PriorityQueue<>(emergencyQueue);
        int no = 1;
        while (!tempQueue.isEmpty()) {
            System.out.println("Emergency Case " + no);
            tempQueue.poll().showDetail();
            System.out.println("--------------------");
            no++;
        }
    }



    public void handleEmergencyPatient() {
        System.out.println();
        System.out.println("=== HANDLE EMERGENCY PATIENT ===");
        if (emergencyQueue.isEmpty()) {
            System.out.println("No emergency patient.");
            return;
        }
        EmergencyCase emergencyCase = emergencyQueue.poll();
        // System.out.println("Next emergency patient:");
        emergencyCase.showDetail();
        System.out.println();
        System.out.println("Patient is now being handled.");
    }










    // USER DOKTER
    // USER DOKTER
    // USER DOKTER
    public void UserDoctor(Doctor doctor) {
        int input;
        do {
            System.out.println();
            System.out.println("=== DOCTOR MENU ===");
            System.out.println("Hello, " + doctor.getFullName());
            System.out.println("1. View Queue");
            System.out.println("2. Call Next Patient");
            System.out.println("3. Current Consultation");
            System.out.println("4. Logout");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (input) {
                case 1:
                    showDoctorQueue(doctor);
                    break;
                case 2:
                    callNextPatient(doctor);
                    break;
                case 3:
                    currentConsultation(doctor);
                    break;
                case 4:
                    start();
                    break;
                default:
                    System.out.println("Menu not available.");
                    break;
            }
        } while (input != 4);
    }



    public void showDoctorQueue(Doctor activeDoctor) {
        System.out.println();
        System.out.println("=== MY PATIENT QUEUE ===");
        boolean hasQueue = false;
        for (DoctorSchedule schedule : activeDoctor.getDoctorSchedules()) {
            ArrayList<Appointment> queue = new ArrayList<>(schedule.getAppointmentQueue());
            queue.sort(Comparator.comparing(Appointment::getAppointmentTime));
            if (!queue.isEmpty()) {
                hasQueue = true;
                System.out.println();
                System.out.println("Date : " + schedule.getDate());
                System.out.println("Time : " + schedule.getStartTime() + " - " + schedule.getEndTime());
                System.out.println("Slot : " + queue.size() + "/" + schedule.getMaxPatient());
                System.out.println("--------------------------------");

                int no = 1;
                for (Appointment appointment : queue) {
                    if (appointment.getAppointmentStatus() != AppointmentStatus.PENDING) {
                        continue;
                    }
                    System.out.println(no + ". " + appointment.getAppointmentTime() + " | " + appointment.getPatient().getFullName() + " | " + appointment.getComplain());
                    no++;
                }
            }
        }
        if (!hasQueue) {
            System.out.println("No queue available.");
        }
    }


    
    // public void selectPatientDoc(Doctor activeDoctor) {
    //     int inp;
    //     do {
    //         System.out.println();
    //         System.out.println("=== SELECT PATIENT ===");
    //         System.out.println("1. View Patient List");
    //         System.out.println("2. Search Patient by ID or Name");
    //         System.out.println("3. Back");
    //         System.out.print("Input: ");
    //         inp = scan.nextInt();
    //         scan.nextLine();
    //         Patient selectedPatient = null;

    //         switch (inp) {
    //             case 1:
    //                 selectedPatient = choosePatientFromListDoc(activeDoctor);
    //                 break;
    //             case 2:
    //                 selectedPatient = searchPatientByIdOrNameDoc(activeDoctor);
    //                 break;
    //             case 3:
    //                 UserDoctor(activeDoctor);
    //             default:
    //                 System.out.println("Please input from 1-3");
    //                 selectPatientDoc(activeDoctor);
    //         }

    //         if (selectedPatient != null) {
    //             selectedPatientActionMenuDoc(activeDoctor, selectedPatient);
    //         }
    //         System.out.println();
    //     } while (inp != 3);
    // }



    // public Patient choosePatientFromListDoc(Doctor doctor) {
    //     nowChecking(doctor);
    //     ArrayList<Patient> patientList = new ArrayList<>();
    //     for (Appointment appointment : appointments) {
    //         if (appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor())) {
    //             Patient patient = appointment.getPatient();
    //             if (!patientList.contains(patient)) {
    //                 patientList.add(patient);
    //             }
    //         }
    //     }

    //     if (patientList.isEmpty()) {
    //         System.out.println("No patient found.");
    //         return null;
    //     }

    //     System.out.println();
    //     System.out.println("=== PATIENT LIST ===");
    //     for (int i = 0; i < patientList.size(); i++) {
    //         Patient patient = patientList.get(i);
    //         System.out.println((i + 1) + ". " + patient.getIdPatient() + " | " + patient.getFullName());
    //     }
    //     System.out.print("Choose patient: ");
    //     int choice = scan.nextInt();

    //     if (choice < 1 || choice > patientList.size()) {
    //         System.out.println("Invalid choice.");
    //         return null;
    //     }
    //     return patientList.get(choice - 1);
    // }



    // public Patient searchPatientByIdOrNameDoc(Doctor doctor) {
    //     nowChecking(doctor);
    //     System.out.println();

    //     System.out.print("Input patient ID or name: ");
    //     String keyword = scan.next().toLowerCase() + scan.nextLine().toLowerCase();
    //     ArrayList<Patient> result = new ArrayList<>();

    //     for (Appointment appointment : appointments) {
    //         if (appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor())) {
    //             Patient patient = appointment.getPatient();
    //             boolean matchId = patient.getIdPatient().toLowerCase().contains(keyword);
    //             boolean matchName = patient.getFullName().toLowerCase().contains(keyword);
    //             if ((matchId || matchName) && !result.contains(patient)) {
    //                 result.add(patient);
    //             }
    //         }
    //     }

    //     if (result.isEmpty()) {
    //         System.out.println("Patient not found.");
    //         return null;
    //     }

    //     System.out.println();
    //     System.out.println("=== SEARCH RESULT ===");
    //     for (int i = 0; i < result.size(); i++) {
    //         Patient patient = result.get(i);
    //         System.out.println((i + 1) + ". " + patient.getIdPatient() + " | " + patient.getFullName());
    //     }
    //     System.out.print("Choose patient: ");
    //     int choice = scan.nextInt();
    //     scan.nextLine();

    //     if (choice < 1 || choice > result.size()) {
    //         System.out.println("Invalid choice.");
    //         return null;
    //     }
    //     return result.get(choice - 1);
    // }



    // public void selectedPatientActionMenuDoc(Doctor doctor, Patient patient) {
    //     int input;
    //     do {
    //         System.out.println();
    //         System.out.println("=== SELECTED PATIENT ===");
    //         System.out.println("Patient : " + patient.getFullName());
    //         System.out.println("1. View Medical Records");
    //         System.out.println("2. Create Medical Record & Prescription");
    //         System.out.println("3. Complete Appointment");
    //         System.out.println("4. Back");
    //         System.out.print("Input: ");
    //         input = scan.nextInt();
    //         scan.nextLine();
    
    //         switch (input) {
    //             case 1:
    //                 viewMedicalRecordsByPatient(patient);
    //                 break;
    //             case 2:
    //                 if (!isPatientInDoctorQueue(doctor, patient)) {
    //                     System.out.println("Patient is not in queue.");
    //                     break;
    //                 }
    //                 createMedicalRecord(patient, doctor);
    //                 break;
    //             case 3:
    //                 if (!isPatientInDoctorQueue(doctor, patient)) {
    //                     System.out.println("Patient is not in queue.");
    //                     break;
    //                 }
    //                 completeAppointment(doctor, patient);
    //                 break;
    //             case 4:
    //                 selectPatientDoc(doctor);
    //             default:
    //                 System.out.println("Invalid input.");
    //                 selectedPatientActionMenuDoc(doctor, patient);
    //         }
    //     } while (input != 4);
    // }
    


    public void nowChecking(Doctor doctor) {
        ArrayList<Appointment> doctorQueue = new ArrayList<>();
        for (Appointment appointment : poliQueue) {
            if (appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor()) && appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                doctorQueue.add(appointment);
            }
        }
        doctorQueue.sort(Comparator.comparing(Appointment::getAppointmentTime));
        if (!doctorQueue.isEmpty()) {
            Appointment nextPatient = doctorQueue.get(0);
            System.out.println();
            System.out.println("NOW CHECKING");
            System.out.println("Patient   : " + nextPatient.getPatient().getFullName());
            System.out.println("Time      : " + nextPatient.getAppointmentTime());
            System.out.println("Date      : " + nextPatient.appointmentDate());
            System.out.println("Complaint : " + nextPatient.getComplain());
            System.out.println("--------------------------------");
        }
    }



    public void callNextPatient(Doctor doctor) {
        System.out.println("=== CALL NEXT PATIENT ===");
        if (currentPatient.containsKey(doctor.getIdDoctor())) {
            System.out.println("You still have a patient in consultation.");
            System.out.println("Please complete the current consultation first.");
            return;
        }

        ArrayList<DoctorSchedule> scheduleList = doctor.getDoctorSchedules();
        if (scheduleList.isEmpty()) {
            System.out.println("No schedule available.");
            return;
        }
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.print((i + 1) + ". ");
            scheduleList.get(i).showDetail();
        }
        System.out.print("Choose Schedule: ");
        int choice = scan.nextInt();
        scan.nextLine();
        if (choice < 1 || choice > scheduleList.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        DoctorSchedule selectedSchedule = scheduleList.get(choice - 1);
        if (selectedSchedule.getAppointmentQueue().isEmpty()) {
            System.out.println("No patient in this queue.");
            return;
        }
        Appointment nextPatient = selectedSchedule.getNextPendingAppointment();
        if (nextPatient == null) {
            System.out.println("No pending patient.");
            return;
        }
        currentPatient.put(doctor.getIdDoctor(), nextPatient);
        System.out.println();
        System.out.println("Now calling:");
        nextPatient.showDetail();
    }



    public void currentConsultation(Doctor doctor) {
        Appointment current = currentPatient.get(doctor.getIdDoctor());
        if (current == null) {
            System.out.println("No patient currently being examined.");
            return;
        }
        int input = 0;
        do {
            System.out.println();
            System.out.println("=== CURRENT CONSULTATION ===");
            System.out.println("Patient   : " + current.getPatient().getFullName());
            System.out.println("Complaint : " + current.getComplain());
            System.out.println("Time      : " + current.getAppointmentTime());
            System.out.println();
            System.out.println("1. View Medical Records");
            System.out.println("2. Create Medical Record & Prescription");
            System.out.println("3. Complete Consultation");
            System.out.println("4. Back");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            switch (input) {
                case 1:
                    viewMedicalRecordsByPatient(current.getPatient());
                    break;
                case 2:
                    createMedicalRecord(current.getPatient(), doctor);
                    break;
                case 3:
                    completeCurrentConsultation(doctor);
                    return;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        } while (input != 4);
    }



    public void completeCurrentConsultation(Doctor doctor) {
        Appointment current = currentPatient.get(doctor.getIdDoctor());
        if (current == null) {
            System.out.println("No patient currently being examined.");
            return;
        }

        current.completeAppointment();
        currentPatient.remove(doctor.getIdDoctor());
        System.out.println("Consultation completed.");
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
        MedicalRecord record = new MedicalRecord(
                idRecord,
                selectedPatient,
                activeDoctor,
                symptoms,
                diagnosis,
                treatment,
                prescription);
        addMedicalRecord(record, selectedPatient);
        System.out.println("Medical record created.");
    }



    public Prescription createPrescription(Patient selectedPatient, Doctor activeDoctor) {
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
        selectedPatient.addPrescription(prescription);
        addPharmacyQueue(prescription);
        System.out.println();
        System.out.println("Prescription created.");
        return prescription;
    }



    public void completeAppointment(Doctor doctor, Patient patient) {
        for (DoctorSchedule schedule : doctor.getDoctorSchedules()) {
            Queue<Appointment> queue = schedule.getAppointmentQueue();
            for (Appointment appointment : queue) {
                boolean samePatient = appointment.getPatient().getIdPatient().equals(patient.getIdPatient());
                boolean pending = appointment.getAppointmentStatus() == AppointmentStatus.PENDING;
                if (samePatient && pending) {
                    appointment.completeAppointment();
                    queue.remove(appointment);
                    System.out.println("Appointment completed.");
                    return;
                }
            }
        }
        System.out.println("No active appointment found.");
    }









    // USER PASIEN
    // USER PASIEN
    // USER PASIEN
    public void UserPatient(Patient patient) {
        int input;
        do {
            System.out.println();
            System.out.println("=== PATIENT MENU ===");
            System.out.println("Hello, " + patient.getFullName());
            System.out.println("1. View Doctor List");
            System.out.println("2. Make Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. View Booking History");
            System.out.println("5. Show My Queue");
            System.out.println("6. View Hospital Queue");
            System.out.println("7. View Pharmacy Queue");
            System.out.println("8. Buy Medicine");
            System.out.println("9. View My Order");
            System.out.println("10. Profile");
            System.out.println("11. Logout");
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
                    showMyQueue(patient);
                    break;

                case 6:
                    showHospitalQueue(patient);
                    break;

                case 7:
                    showPharmacyQueue(patient);
                    break;

                case 8:
                    buyMedicine(patient);
                    break;

                case 9:
                    viewMyOrder(patient);
                    break;

                case 10:
                    System.out.println();
                    System.out.println("=== PROFILE ===");
                    patient.showDetail();
                    break;

                case 11:
                    System.out.println("Logout success.");
                    start();
                    break;

                default:
                    System.out.println("Menu not available.");
                    UserPatient(patient);
                    break;
            }
        } while (input != 11);
    }



    public void makeAppointment(Patient patient) {
        System.out.println("=== MAKE APPOINTMENT ===");
        if (doctors.isEmpty()) {
            System.out.println("Mohon maaf. Belum ada dokter.");
            return;
        }

        ArrayList<Doctor> doctorList = new ArrayList<>(doctors.values());
        doctorList.sort((dA, dB) -> dA.getIdDoctor().compareTo(dB.getIdDoctor()));
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            System.out.println((i + 1) + ". " + doctor.getFullName() + " - " + doctor.getSpecialization());
        }
        System.out.print("Choose Doctor: ");
        int chooseD = scan.nextInt();
        scan.nextLine();
        if (chooseD < 1 || chooseD > doctorList.size()) {
            System.out.println("Input error. Please Try Again.");
            return;
        }
        Doctor selectedD = doctorList.get(chooseD - 1);
        if (selectedD.getDoctorSchedules().isEmpty()) {
            System.out.println("Mohon maaf. Dokter belum memiliki jadwal.");
            return;
        }

        System.out.println();
        System.out.println("=== DOCTOR SCHEDULE ===");
        ArrayList<DoctorSchedule> scheduleList = selectedD.getDoctorSchedules();
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.print((i + 1) + ". ");
            scheduleList.get(i).showDetail();
        }
        System.out.print("Choose Schedule: ");
        int chooseSchedule = scan.nextInt();
        scan.nextLine();
        if (chooseSchedule < 1 || chooseSchedule > scheduleList.size()) {
            System.out.println("Input error. Please Try Again.");
            return;
        }
        DoctorSchedule selectedSchedule = scheduleList.get(chooseSchedule - 1);
        ArrayList<LocalTime> availableTimes = new ArrayList<>();
        LocalTime currentTime = selectedSchedule.getStartTime();
        while (!currentTime.plusMinutes(20).isAfter(selectedSchedule.getEndTime())) {
            if (isSlotAvailable(selectedD, selectedSchedule.getDate(), currentTime)) {
                availableTimes.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(20);
        }
        if (availableTimes.isEmpty()) {
            System.out.println("Jadwal dokter sudah penuh.");
            return;
        }

        System.out.println();
        System.out.println("=== AVAILABLE TIME SLOT ===");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }
        System.out.print("Choose Time: ");
        int chooseTime = scan.nextInt();
        scan.nextLine();
        if (chooseTime < 1 || chooseTime > availableTimes.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        LocalTime appointmentTime =
        availableTimes.get(chooseTime - 1);

        System.out.print("Complain: ");
        String complain = scan.nextLine();
        String idAppointment = "A" + String.format("%03d", appointments.size() + 1);
        Appointment appointment = new Appointment(
                idAppointment,
                selectedD,
                patient,
                selectedSchedule,
                appointmentTime,
                complain);
        addAppointment(appointment);
        System.out.println();
        System.out.println("Appointment added successfully.");
        System.out.println("Estimated Time: " + appointmentTime);
        System.out.println();
        appointment.showDetail();
    }



    public LocalTime getNextAvailableTime(Doctor doctor, DoctorSchedule schedule) {
        int durationPerPatient = 20;
        LocalTime currentTime = schedule.getStartTime();
        while (!currentTime.plusMinutes(durationPerPatient).isAfter(schedule.getEndTime())) {
            if (isSlotAvailable(doctor, schedule.getDate(), currentTime)) {
                return currentTime;
            }
            currentTime = currentTime.plusMinutes(durationPerPatient);
        }
        return null;
    }



    public DoctorSchedule getNextAvailableSchedule(Doctor doctor, DoctorSchedule currentSchedule) {
        ArrayList<DoctorSchedule> schedules = new ArrayList<>(doctor.getDoctorSchedules());
        schedules.sort(Comparator.comparing(DoctorSchedule::getDate));
        boolean foundCurrent = false;
        for (DoctorSchedule schedule : schedules) {
            if (!foundCurrent) {
                if (schedule.equals(currentSchedule)) {
                    foundCurrent = true;
                }
                continue;
            }
            if (getNextAvailableTime(doctor, schedule) != null) {
                return schedule;
            }
        }
        return null;
    }



    public boolean isSlotAvailable(Doctor doctor, LocalDate date, LocalTime time) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime() == null) {
                continue;
            }
            boolean sameDoctor = appointment.getDoctor().getIdDoctor().equals(doctor.getIdDoctor());
            boolean sameDate = appointment.appointmentDate().equals(date);
            boolean sameTime = appointment.getAppointmentTime().equals(time);
            boolean notCanceled = !appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELED);
            if (sameDoctor && sameDate && sameTime && notCanceled) {
                return false;
            }
        }
        return true;
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



    public void showHospitalQueue(Patient patient) {
        System.out.println();
        System.out.println("=== HOSPITAL QUEUE BY DOCTOR SCHEDULE ===");
        boolean hasQueue = false;
        for (Doctor doctor : doctors.values()) {
            for (DoctorSchedule schedule : doctor.getDoctorSchedules()) {
                Queue<Appointment> queue = schedule.getAppointmentQueue();
                if (!queue.isEmpty()) {
                    hasQueue = true;
                    System.out.println();
                    System.out.println("Doctor : " + doctor.getFullName());
                    System.out.println("Date   : " + schedule.getDate());
                    System.out.println("Time   : " + schedule.getStartTime() + " - " + schedule.getEndTime());
                    System.out.println("Slot   : " + queue.size() + "/" + schedule.getMaxPatient());
                    System.out.println("--------------------------------");

                    int no = 1;
                    for (Appointment appointment : queue) {
                        String mark = "";
                        if (appointment.getPatient().getIdPatient().equals(patient.getIdPatient())) {
                            mark = " <-- YOU";
                        }
                        System.out.println(no + ". " + appointment.getAppointmentTime() + " | " + appointment.getPatient().getFullName() + mark);
                        no++;
                    }
                }
            }
        }
        if (!hasQueue) {
            System.out.println("No queue available.");
        }
    }



    public void showMyQueue(Patient patient) {
        ArrayList<Appointment> myAppointments = new ArrayList<>();
        for (Doctor doctor : doctors.values()) {
            for (DoctorSchedule schedule : doctor.getDoctorSchedules()) {
                for (Appointment appointment : schedule.getAppointmentQueue()) {
                    if (appointment.getPatient().getIdPatient().equals(patient.getIdPatient()) && appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                        myAppointments.add(appointment);
                    }
                }
            }
        }

        if (myAppointments.isEmpty()) {
            System.out.println("You don't have any active appointment.");
            return;
        }

        ArrayList<DoctorSchedule> displayedSchedules = new ArrayList<>();
        for (Appointment myAppointment : myAppointments) {
            DoctorSchedule schedule = myAppointment.getDoctorSchedule();
            if (displayedSchedules.contains(schedule)) {
                continue;
            }
            displayedSchedules.add(schedule);
            Doctor doctor = myAppointment.getDoctor();
            ArrayList<Appointment> sortedQueue = new ArrayList<>(schedule.getAppointmentQueue());
            sortedQueue.removeIf(a -> a.getAppointmentStatus() != AppointmentStatus.PENDING);
            sortedQueue.sort(Comparator.comparing(Appointment::getAppointmentTime));

            System.out.println();
            System.out.println("=== MY QUEUE ===");
            System.out.println("Doctor : " + doctor.getFullName());
            System.out.println("Date   : " + schedule.getDate());
            System.out.println("Time   : " + schedule.getStartTime() + " - " + schedule.getEndTime());
            System.out.println("--------------------------------");

            int no = 1;
            for (Appointment appointment : sortedQueue) {
                String mark = "";
                if (appointment.getPatient().getIdPatient().equals(patient.getIdPatient())) {
                    mark = " <-- YOU";
                }
                System.out.println(no + ". " + appointment.getAppointmentTime() + " | " + appointment.getPatient().getFullName() + mark);
                no++;
            }
            System.out.println();

            for (Appointment appointment : sortedQueue) {
                if (appointment.getPatient().getIdPatient().equals(patient.getIdPatient())) {
                    int position = sortedQueue.indexOf(appointment) + 1;
                    System.out.println("Your Queue Number : " + position);
                    System.out.println("Your Appointment  : " + appointment.getAppointmentTime());
                    System.out.println();
                }
            }
        }
    }



    public void showPharmacyQueue(Patient patient) {
        System.out.println();
        System.out.println("=== PHARMACY QUEUE ===");
        if (pharmacyQueue.isEmpty()) {
            System.out.println("No pharmacy queue available.");
            return;
        }

        int no = 1;
        ArrayList<Integer> myPositions = new ArrayList<>();
        for (Prescription prescription : pharmacyQueue) {
            String mark = "";
            if (prescription.getPatient().getIdPatient().equals(patient.getIdPatient())) {
                mark = " <-- YOU";
                myPositions.add(no);
            }
            System.out.println(no + ". " + prescription.getPatient().getFullName() + " | Prescription ID: " + prescription.getIdPrescription() + mark);
            no++;
        }

        if (myPositions.isEmpty()) {
            System.out.println();
            System.out.println("You don't have any active pharmacy queue.");
        } else {
            for (int pos : myPositions) {
                System.out.println();
                System.out.println("Your Pharmacy Queue Number : " + pos);
            }
        }
    }



    public void buyMedicine(Patient patient) {
        System.out.println();
        System.out.println("=== BUY MEDICINE ===");
        ArrayList<Medicine> availableMedicines = new ArrayList<>();
        for (Medicine medicine : medicines.values()) {
            if (!medicine.isControlled()) {
                availableMedicines.add(medicine);
            }
        }
        if (availableMedicines.isEmpty()) {
            System.out.println("No medicine available.");
            return;
        }
    
        HashMap<Medicine, Integer> cart = new HashMap<>();
        for (int i = 0; i < availableMedicines.size(); i++) {
            Medicine med = availableMedicines.get(i);
            System.out.println((i + 1) + ". " + med.getMedName() + " | Rp" + med.getPrice());
        }
        while (true) {
            System.out.print("Choose medicine number (enter to finish): ");
            String input = scan.nextLine();
            if (input.isEmpty()) {
                break;
            }
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > availableMedicines.size()) {
                System.out.println("Invalid choice.");
                continue;
            }
            Medicine selectedMedicine = availableMedicines.get(choice - 1);
            System.out.print("Qty: ");
            int qty = scan.nextInt();
            scan.nextLine();
            cart.put(selectedMedicine, cart.getOrDefault(selectedMedicine, 0) + qty);
            System.out.println(selectedMedicine.getMedName() + " added.");
        }
        if (cart.isEmpty()) {
            System.out.println("No medicine selected.");
            return;
        }
    
        int total = 0;
        System.out.println();
        System.out.println("=== PURCHASE SUMMARY ===");
        for (Medicine med : cart.keySet()) {
            int qty = cart.get(med);
            int subtotal = qty * med.getPrice();
            System.out.println(med.getMedName() + " x " + qty + " = Rp" + subtotal);
            total += subtotal;
        }
        System.out.println("--------------------");
        System.out.println("Total : Rp" + total);
        String idPrescription = "PRM" + String.format("%03d", medicineOrderCounter++);
        Prescription purchase = patient.buyMedicine(cart, idPrescription);
        patient.addPrescription(purchase);
        addPharmacyQueue(purchase);
        System.out.println("Order added to pharmacy queue.");
        System.out.println("Please wait for pharmacist confirmation.");
        

    }

    public void viewMyOrder(Patient patient) {
        System.out.println();
        System.out.println("=== MY ACTIVE ORDERS ===");

        boolean found = false;

        for (Prescription prescription : pharmacyQueue) {
            if (prescription.getPatient() == patient && !prescription.isCompleted()) {
                prescription.showDetail();
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active orders.");
            return;
        }

        System.out.println("1. Cancel Order");
        System.out.println("2. Back");
        System.out.print("Input: ");

        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1:
                cancelMyOrder(patient);
                break;

            case 2:
                return;

            default:
                System.out.println("Invalid menu.");
        }
    }

    public void cancelMyOrder(Patient patient) {

        System.out.print("Input Prescription ID: ");
        String id = scan.nextLine();

        if (!id.startsWith("PRM")) {
            System.out.println("Only PRM orders can be cancelled.");
            return;
        }

        Prescription target = null;

        for (Prescription prescription : pharmacyQueue) {

            if (prescription.getIdPrescription().equalsIgnoreCase(id)
                    && prescription.getPatient() == patient
                    && !prescription.isCompleted()) {

                target = prescription;
                break;
            }
        }

        if (target == null) {
            System.out.println("Order not found.");
            return;
        }

        if (pharmacyQueue.peek() == target) {
        System.out.println("Queue number 1 cannot be cancelled.");
        return;
        }

        pharmacyQueue.remove(target);
        System.out.println("Order cancelled successfully.");
    }

    // public void redeemPrescription(Patient patient) {
    //     ArrayList<Prescription> availablePrescriptions = new ArrayList<>();
    //     for (Prescription prescription : patient.getPrescriptions()) {
    //         if (!prescription.isCompleted()) {
    //             availablePrescriptions.add(prescription);
    //         }
    //     }
    //     if (availablePrescriptions.isEmpty()) {
    //         System.out.println("No prescription available.");
    //         return;
    //     }

    //     System.out.println();
    //     System.out.println("=== PRESCRIPTIONS ===");
    //     for (int i = 0; i < availablePrescriptions.size(); i++) {
    //         Prescription prescription = availablePrescriptions.get(i);
    //         System.out.println((i + 1) + ". Prescription ID: " + prescription.getIdPrescription());
    //         int total = 0;
    //         for (Medicine medicine : prescription.getMedicines().keySet()) {
    //             int qty = prescription.getMedicines().get(medicine);
    //             System.out.println("   - " + medicine.getMedName() + " x " + qty);
    //             total += qty * medicine.getPrice();
    //         }
    //         System.out.println("   Total : Rp" + total);
    //         System.out.println();
    //     }

    //     System.out.print("Choose prescription: ");
    //     int choice = scan.nextInt();
    //     scan.nextLine();
    //     if (choice < 1 || choice > availablePrescriptions.size()) {
    //         System.out.println("Invalid choice.");
    //         return;
    //     }
    //     Prescription selectedPrescription = availablePrescriptions.get(choice - 1);
    //     int total = 0;
    //     for (Medicine medicine : selectedPrescription.getMedicines().keySet()) {
    //         int qty = selectedPrescription.getMedicines().get(medicine);
    //         total += qty * medicine.getPrice();
    //     }

    //     System.out.println();
    //     System.out.println("Prescription redeemed.");
    //     System.out.println("Total Payment : Rp" + total);
    //     selectedPrescription.setCompleted(true);
    //     pharmacyQueue.remove(selectedPrescription);
    // }









    public void userPharmacist(Pharmacist pharmacist){
        int input;
        do {
            System.out.println();
            System.out.println("=== PHARMACIST MENU ===");
            System.out.println("Hello, " + pharmacist.getFullName());
            System.out.println("1. View Queue");
            System.out.println("2. Complete Order");
            System.out.println("3. Manage Medicine");
            System.out.println("4. Logout");
            System.out.print("Input: ");
            input = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (input) {
                case 1:
                    viewPharmacyQueue();
                    break;
                case 2:
                    completePharmacyOrder();
                    break;
                case 3:
                    medicineMenu();
                    break;
                case 4:
                    start();
                    break;
                default:
                    System.out.println("Menu not available.");
                    break;
            }
        } while (input != 4);
    }



    public void viewPharmacyQueue() {
        System.out.println();
        System.out.println("=== PHARMACY QUEUE ===");
        if (pharmacyQueue.isEmpty()) {
            System.out.println("No queue available.");
            return;
        }

        int no = 1;
        for (Prescription prescription : pharmacyQueue) {
            System.out.println(no + ". " + prescription.getPatient().getFullName() + " | " + prescription.getIdPrescription());
            no++;
        }
    }



    public void completePharmacyOrder() {
        if (pharmacyQueue.isEmpty()) {
            System.out.println("No pharmacy queue.");
            return;
        }

        Prescription prescription =pharmacyQueue.peek();
        System.out.println();
        System.out.println("=== CURRENT ORDER ===");
        prescription.showDetail();
        System.out.print("Complete this order? (y/n): ");
        String confirm = scan.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            prescription.setCompleted(true);
            pharmacyQueue.poll();
            System.out.println("Order completed successfully.");
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
        for (DoctorSchedule schedule : doctor.getDoctorSchedules()) {
            for (Appointment appointment : schedule.getAppointmentQueue()) {
                boolean samePatient = appointment.getPatient().getIdPatient().equals(patient.getIdPatient());
                boolean pending = appointment.getAppointmentStatus() == AppointmentStatus.PENDING;
                if (samePatient && pending) {
                    return true;
                }
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

    public void addPharmacist(Pharmacist pharmacist) {
        pharmacists.put(pharmacist.getIdPharmacist(), pharmacist);
        users.put(pharmacist.getUname(), pharmacist);
    }

    public void addMedicine(Medicine medicine) {
        medicines.put(medicine.getIdMedicine(), medicine);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.getDoctorSchedule().addAppointment(appointment);
        appointment.getPatient().addAppointmentHistory(appointment);
        appointment.getDoctor().addAppointment(appointment);
    }

    public void addMedicalRecord(MedicalRecord record, Patient patient) {
        medicalRecords.add(record);
        patient.addMedicalRecord(record);
    }

    public void addPharmacyQueue(Prescription prescription) {
        pharmacyQueue.offer(prescription);
    }

    public void addEmergencyCase(EmergencyCase emergencyCase) {
        emergencyQueue.offer(emergencyCase);
    }

}
