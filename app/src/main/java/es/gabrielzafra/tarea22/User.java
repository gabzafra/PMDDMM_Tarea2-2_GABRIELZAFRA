package es.gabrielzafra.tarea22;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String surnames;
    private String email;
    private String phone;
    private boolean online;

    public User() {
        this("", "", "", "", true);
    }

    public User(String name, String surnames, String email, String phone, boolean online) {
        this.name = name;
        this.surnames = surnames;
        this.email = email;
        this.phone = phone;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
