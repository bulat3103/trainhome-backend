package com.example.trainhome.configuration;

public enum RoleConfig {
    ROLE_CLIENT("ROLE_CLIENT"),
    ROLE_COACH("ROLE_COACH");

    private final String name;

    RoleConfig(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
