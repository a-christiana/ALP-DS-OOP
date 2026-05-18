package model;

import java.time.LocalDate;

public class Appointment {
    private static int nextQueueNumber = 1;
    private String idAppointment;
    Patient patient;
    Doctor doctor;
    LocalDate appointmentDate;
    private AppointmentStatus status;
    private int queueNumber;

    public Appointment (String idAppointment, Doctor doctor, Patient patient, LocalDate appointmentDate) {
        this.idAppointment = idAppointment;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.status = AppointmentStatus.PENDING;
        this.queueNumber = nextQueueNumber++;
    }

    public String getIdAppointment() {
        return idAppointment;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public AppointmentStatus getAppointmentStatus() {
        return status;
    }

    public LocalDate appointmentDate() {
        return appointmentDate;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void pendingAppointment() {
        status = AppointmentStatus.PENDING;
    }

    public void cancelAppointment() {
        status = AppointmentStatus.CANCELED;
    }

    public void completeAppointment() {
        status = AppointmentStatus.COMPLETED;
    }

    public void showDetail() {
        System.out.println("=== APPOINTMENT DETAIL ===");
        System.out.println("Appointmet ID: " + idAppointment);
        System.out.println("Patient: " + patient.getUname());
        System.out.println("Doctor: " + doctor.getUname());
        System.out.println("Urgency: " + patient.getUrgencyLlevel());
        System.out.println("Date: " + appointmentDate);
        System.out.println("Status: " + status);
    }

     @Override
    public String toString() {
        return "Queue " + queueNumber +
                " | Patient: " + patient.getUname() +
                " | Urgency: " + patient.getUrgencyLlevel() +
                " | Doctor: " + doctor.getUname() +
                " | Status: " + status;
    }
}
