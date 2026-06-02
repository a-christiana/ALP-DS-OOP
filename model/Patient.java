package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Patient extends User {
    private String idPatient;
    private String fullname;
    private String nik;
    private String telepon;
    private LocalDateTime registDateTime;
    private ArrayList<Appointment> appointmentHistory;
    private ArrayList<MedicalRecord> medicalRecords;
    private ArrayList<Prescription> prescriptions;

    public Patient(String idUser, String username, String password, String idPatient, String fullname, String nik,
            String telepon) {
        super(idUser, username, password, Role.PATIENT);
        this.idPatient = idPatient;
        this.fullname = fullname;
        this.nik = nik;
        this.telepon = telepon;
        this.registDateTime = LocalDateTime.now();
        appointmentHistory = new ArrayList<>();
        medicalRecords = new ArrayList<>();
        prescriptions = new ArrayList<>();
    }

    public String getIdPatient() {
        return idPatient;
    }

    public String getFullName() {
        return fullname;
    }

    public String getNik() {
        return nik;
    }

    public String getTelepon() {
        return telepon;
    }

    public LocalDateTime getRegistDateTime() {
        return registDateTime;
    }

    public ArrayList<Appointment> getAppointmentHistory() {
        return appointmentHistory;
    }

    public void addAppointmentHistory(Appointment appointment) {
        appointmentHistory.add(appointment);
    }

    public ArrayList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public boolean cancelAppointment(Appointment appointment) {
        if (appointmentHistory.contains(appointment)) {
            appointment.cancelAppointment();
            return true;
        }
        return false;
    }

    public Prescription buyMedicine(HashMap<Medicine, Integer> meds, String idPrescription) {
        Prescription prescription = new Prescription(idPrescription, this, null);
        for (Medicine medicine : meds.keySet()) {
            prescription.addMedicine(medicine, meds.get(medicine));
        }
        return prescription;
    }

    public void viewHistory() {
        if (appointmentHistory.isEmpty()) {
            System.out.println("Belum ada riwayat appointment");
            return;
        }
        for (Appointment appointment : appointmentHistory) {
            appointment.showDetail();
        }
    }

    public void addAppointment(Appointment appointment) {
        appointmentHistory.add(appointment);
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    @Override
    public void showDetail() {
        System.out.println();
        System.out.println("Patient ID: " + idPatient +
                "\nName: " + fullname +
                "\nNIK: " + nik +
                "\nTelepon: " + telepon +
                "\nRegistration Time: " + registDateTime);
        System.out.println();
    }
}
