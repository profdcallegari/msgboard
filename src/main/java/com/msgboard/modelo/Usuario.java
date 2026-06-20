package com.msgboard.modelo;

import java.util.UUID;

public class Usuario {
    private String id;
    private String email;
    private String senha;

    public Usuario() {
        this.id = UUID.randomUUID().toString();
    }

    public Usuario(String email, String senha) {
        this();
        this.email = email;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
