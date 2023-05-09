package com.example.l4supp.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String login;
    private String password;
    private boolean isSeller;
    private boolean isLoggedIn;

    public UserEntity() {
    }

    public UserEntity(String login, String password, boolean isSeller) {
        this.login = login;
        this.password = password;
        this.isSeller = isSeller;
    }

    public UserEntity(String login, String password, boolean isSeller, boolean isLoggedIn) {
        this.login = login;
        this.password = password;
        this.isSeller = isSeller;
        this.isLoggedIn = isLoggedIn;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}


