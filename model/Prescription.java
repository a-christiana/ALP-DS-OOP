package model;

import java.util.HashMap;

public class Prescription {
    private String idPrescription;
    private Patient patient;
    private Doctor doctor;
    private HashMap<Medicine, Integer> medicines;
    private boolean completed;
    
    public Prescription(String idPrescription, Patient patient, Doctor doctor) {
        this.idPrescription = idPrescription;
        this.patient = patient;
        this.doctor = doctor;
        this.medicines = new HashMap<>();
        this.completed = false;
    }
    
    public String getIdPrescription() {
        return idPrescription;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public HashMap<Medicine, Integer> getMedicines() {
        return medicines;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public void addMedicine(Medicine medicine, int qty) {
        medicines.put(medicine, qty);
    }
    
    public double total() {
        double total = 0;

        for (Medicine medicine : medicines.keySet()) {
            total += medicine.getPrice() * medicines.get(medicine);
        }

        return total;
    }

    public boolean updateStatus() {
        completed = true;
        return completed;
    }

    public boolean validateMedicinePurchase() {
        for (Medicine medicine : medicines.keySet()) {
            if (medicine.isControlled() && doctor == null) {
                return false;
            }
        }

        return true;
    }

    public void showDetail() {
        System.out.println("Prescription ID: " + idPrescription);
        System.out.println("Patient: " + patient.getUname());

        if (doctor != null) {
            System.out.println("Doctor: " + doctor.getUname());
        } else {
            System.out.println("Doctor: -");
        }

        for (Medicine medicine : medicines.keySet()) {
            System.out.println(medicine.getMedName() + " x " + medicines.get(medicine));
        }

        System.out.println("Total: " + total());
        System.out.println("Completed: " + completed);
    }
}
