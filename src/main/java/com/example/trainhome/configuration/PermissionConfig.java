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
                ".*/auth/login",
                ".*/coach/list",

                "./groups/list",
                "./auth/logout",
                ".*/coach/list",
                ".*/calendar/list/dates",
                ".*/calendar/list",
                ".*/groups/list",

                "./groups.*",
                "./auth/logout",
                ".*/coach/list",
                ".*/calendar",
                ".*/calendar/update",
                ".*/groups.*"
        };
        String[] client = {
                "./groups/list",
                "./auth/logout",
                ".*/coach/list",
                ".*/calendar/list/dates",
                ".*/calendar/list",
                ".*/groups/list"
        };
        String[] coach = {
                "./groups.*",
                "./auth/logout",
                ".*/coach/list",
                ".*/calendar",
                ".*/calendar/update",
                ".*/groups.*"
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