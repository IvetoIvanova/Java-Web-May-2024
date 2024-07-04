package com.plannerapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private long id;
    private String username;

    public void login(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public boolean isUserLoggedIn() {
        return id != 0;
    }

    public String username() {
        return username;
    }

    public Long id() {
        return id;
    }
}
