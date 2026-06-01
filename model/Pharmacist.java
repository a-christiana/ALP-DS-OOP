package model;

public class Pharmacist extends User {
    private String idPharmacist;
    private String fullName;

    public Pharmacist(String idUser, String username, String password, String idPharmacist, String fullName) {
        super(idUser, username, password, Role.PHARMACIST);
        this.idPharmacist = idPharmacist;
        this.fullName = fullName;
    }

    public String getIdPharmacist() {
        return idPharmacist;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public void showDetail() {
        System.out.println("Pharmacist ID : " + idPharmacist);
        System.out.println("Name          : " + fullName);
    }
}