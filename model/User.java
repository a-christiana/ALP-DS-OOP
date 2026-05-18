package model;

public abstract class User {
    protected String username;
    protected String idUser;
    protected String password;
    protected Role role;

    public User() {
        idUser = "U";
        username = "";
        password = "";
        role = null;
    }

    public User(String idUser, String username, String password, Role r) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = r;
    }

    public String getUname() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIdUser() {
        return idUser;
    }

    public Role getRole() {
        return role;
    }

    public boolean logout() {
        System.out.println("Logout Success.");
        return false;
    }

    public abstract void showDetail();

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}