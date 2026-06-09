package model;

import java.time.LocalDate;

public class MedicalRecord {
    private String idMedicalRecords;
    private Patient patient;
    private Doctor doctor;
    private LocalDate visitDate;
    private String complaint;
    private String diagnose;
    private String treatment;
    private Prescription prescription;

    public MedicalRecord(String idMedicalRecords, Patient patient, Doctor doctor, String complaint, String diagnose, String treatment, Prescription prescription) {
        this.idMedicalRecords = idMedicalRecords;
        this.patient = patient;
        this.doctor = doctor;
        this.visitDate = LocalDate.now();
        this.complaint = complaint;
        this.diagnose = diagnose;
        this.treatment = treatment;
        this.prescription = prescription;
    }

    public void showDetail() {
        System.out.println("Medical Record ID: " + idMedicalRecords);
        System.out.println("Patient: " + patient.getUname());
        System.out.println("Doctor: " + doctor.getUname());
        System.out.println("Visit Date: " + visitDate);
        System.out.println("Complaint: " + complaint);
        System.out.println("Diagnose: " + diagnose);
        System.out.println("Treatment: " + treatment);

        if (prescription != null) {
            System.out.println();
            prescription.showDetail();
        }

        System.out.println("----------------------");
    }

}
