package br.edu.unis.sqlite;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String user;
    private String password;

    public User(long id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
