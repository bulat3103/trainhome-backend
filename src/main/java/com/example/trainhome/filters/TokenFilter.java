package com.example.trainhome.filters;

import com.example.trainhome.configuration.PermissionConfig;
import com.example.trainhome.configuration.RoleConfig;
import com.example.trainhome.entities.Session;
import com.example.trainhome.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Autowired
    private SessionRepository repository;
    @Autowired
    private PermissionConfig permissionConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String[] unauthorized = permissionConfig.getAllowedUrls().get(RoleConfig.UNAUTHORIZED.toString());
        boolean skipAuth = false;
        String requestUrl = request.getRequestURL().toString();
        for (String url : unauthorized) {
            if (requestUrl.matches(url)) {
                skipAuth = true;
                break;
            }
        }
        if (skipAuth) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = parseToken(request);
            if (token == null) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            } else {
                Session session = repository.getByToken(token);
                if (session == null || session.isExpired()) {
                    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
                } else {
                    servletRequest.setAttribute("session", session);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }

    private String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
