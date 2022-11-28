package com.example.trainhome.configuration;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PermissionConfig {
    public final HashMap<String, String[]> allowedUrls;

    public PermissionConfig() {
        this.allowedUrls = new HashMap<>();
        String[] unauthorized = {
                ".*/auth/register.*",
                ".*/auth/login"
        };
        String[] client = {

        };
        String[] coach = {
                "./groups"
        };
        allowedUrls.put(RoleConfig.UNAUTHORIZED.toString(), unauthorized);
        allowedUrls.put(RoleConfig.ROLE_CLIENT.toString(), client);
        allowedUrls.put(RoleConfig.ROLE_COACH.toString(), coach);
    }

    public HashMap<String, String[]> getAllowedUrls() {return allowedUrls;}

    public boolean containsUrl(String type, String requestUrl) {
        String[] urls = allowedUrls.get(type);
        for (String url : urls) {
            if (requestUrl.matches(url)) return true;
        }
        return false;
    }

}