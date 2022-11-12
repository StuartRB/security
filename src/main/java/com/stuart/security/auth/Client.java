package com.stuart.security.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class Client {

    private String user;

    private String token;
    private List<GrantedAuthority> roles;

    public Client(String user, String token, List<GrantedAuthority> roles) {
        this.user = user;
        this.token = token;
        this.roles = roles;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
