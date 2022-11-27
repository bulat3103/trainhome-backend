package com.example.trainhome.filters;

import com.example.trainhome.configuration.PermissionConfig;
import com.example.trainhome.configuration.RoleConfig;
import com.example.trainhome.entities.Session;
import com.example.trainhome.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(10)
public class TokenFilter implements Filter {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PermissionConfig permissionConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUrl = request.getRequestURL().toString();
        if (permissionConfig.containsUrl(RoleConfig.UNAUTHORIZED.toString(), requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = parseToken(request);
            if (token == null) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            } else {
                Session session = sessionRepository.getByToken(token);
                if (session == null || session.isExpired()) {
                    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
                } else {
                    if ((permissionConfig.containsUrl(RoleConfig.ROLE_COACH.toString(), requestUrl)
                            && !session.getPerson().getRoleId().getName().equals(RoleConfig.ROLE_COACH.toString()))
                            || (permissionConfig.containsUrl(RoleConfig.ROLE_CLIENT.toString(), requestUrl)
                            && !session.getPerson().getRoleId().getName().equals(RoleConfig.ROLE_CLIENT.toString()))) {
                        ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, "You have no rights for this action.");
                    }
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
