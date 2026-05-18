package model;

public class Medicine {
    public String idMedicine;
    public String medicineName;
    public double price;
    public boolean controlledSubstance;
    
    public Medicine() {
        idMedicine = "M";
        medicineName = "";
        price = 0;
        controlledSubstance = false;
    }
    
    public Medicine(String idMedicine, String medicineName, double price, boolean controlledSubstance) {
        this.idMedicine =idMedicine;
        this.medicineName = medicineName;
        this.price = price;
        this.controlledSubstance = controlledSubstance;
    }
    
    public void showDetail(){
        System.out.println(getIdMedicine() + " - nama: " + medicineName + " - price: " + price + " rupiah - controlled: " + controlledSubstance);
    }
    
    public boolean isControlled(){
        return controlledSubstance;
    }
    
    public String getMedName() {
        return medicineName;
    }
    
    public String getIdMedicine() {
        return idMedicine;
    }

    public double getPrice() {
        return price;
    }

    public void setIdMedicine(String idMedicine) {
        this.idMedicine = idMedicine;
    }
}
