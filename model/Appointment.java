package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String idAppointment;
    private Patient patient;
    private Doctor doctor;
    private LocalDate appointmentDate;
    private AppointmentStatus status;
    private LocalTime appointmentTime;
    private String complain;
    private DoctorSchedule doctorSchedule;

    public Appointment(String idAppointment, Doctor doctor, Patient patient, DoctorSchedule doctorSchedule, LocalTime appointmentTime, String complain) {
        this.idAppointment = idAppointment;
        this.doctor = doctor;
        this.patient = patient;
        this.doctorSchedule = doctorSchedule;
        this.appointmentDate = doctorSchedule.getDate();
        this.appointmentTime = appointmentTime;
        this.status = AppointmentStatus.PENDING;
        this.complain = complain;
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

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getComplain() {
        return complain;
    }

    public DoctorSchedule getDoctorSchedule() {
        return doctorSchedule;
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
        System.out.println("Patient: " + patient.getFullName());
        System.out.println("Doctor: " + doctor.getFullName());
        System.out.println("Date: " + appointmentDate);
        System.out.println("Time: " + appointmentTime);
        System.out.println("Complain: " + complain);
        System.out.println("Status: " + status);
    }
}
