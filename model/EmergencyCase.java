package model;
import java.time.LocalDateTime;

public class EmergencyCase {


    private String emergencyId;
    private Patient patient;
    private String complaint;
    private TriageLevel triageLevel;
    private LocalDateTime arrivalTime;
    private AppointmentStatus status;

    public EmergencyCase(String emergencyId, Patient patient, String complaint) {
        this.emergencyId = emergencyId;
        this.patient = patient;
        this.complaint = complaint;
        this.triageLevel = TriageSystem.determineTriage(complaint);
        this.arrivalTime = LocalDateTime.now();
        this.status = AppointmentStatus.PENDING;
    }

    public Patient getPatient() {
        return patient;
    }

    public TriageLevel getTriageLevel() {
        return triageLevel;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void showDetail() {
        System.out.println("Emergency ID: " + emergencyId);
        System.out.println("Patient: " + patient.getFullName());
        System.out.println("Complaint: " + complaint);
        System.out.println("Triage: " + triageLevel);
        System.out.println("Arrival: " + arrivalTime);
        System.out.println("Status: " + status);
    }
}
