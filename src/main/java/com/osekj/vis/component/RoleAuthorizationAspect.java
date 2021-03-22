package com.osekj.vis.component;

import com.osekj.vis.security.VisAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAuthorizationAspect {

    @Before(value = "@annotation(com.osekj.vis.annotation.RoleAuthorization)")
    public void secureRoleController() {
        VisAuthenticationToken authentication = (VisAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getRole().equalsIgnoreCase("ADMIN_ROLE")) {
            throw new AccessDeniedException("");
        }
    }
}
