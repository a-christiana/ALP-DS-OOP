package model;

import java.util.*;

public class Doctor extends User{
    private String idDoctor;
    private String fullName;
    private String phoneNumber;
    private String specialization;
    private boolean available;
    private PriorityQueue<Appointment> appointments;
    private ArrayList<DoctorSchedule> doctorSchedules;

    public Doctor (String idUser, String username, String password, String doctorId, String fullName, String phoneNumber, String specialization) {
        super(idUser, username, password, Role.DOCTOR);

        this.idDoctor = doctorId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.available = true;

        //doctorSchedules = new ArrayList<>();
        //appointments = new PriorityQueue<>((a1, a2) -> Integer.compare(a2.getPatient().getUrgencyLlevel(), a1.getPatient().getUrgencyLlevel()));
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean isAvalaible() {
        return available;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void viewAppointment() {
        
        if (appointments.isEmpty()) {
            System.out.println("No appointment available");
        }

        for (Appointment appointment : appointments) {
            appointment.showDetail();
        }
    }

    public String makeDiagnose(Patient patient) {
        return "Diagnosis Pasien: " + patient.getUname();
    }


    @Override
    public void showDetail() {
        System.out.println("Doctor ID : " + idDoctor + "\nName : " + fullName +"\nPhone : " + phoneNumber + "\nSpecialist : " + specialization); 
    }
}
