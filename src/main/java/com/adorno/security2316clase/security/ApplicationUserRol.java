package com.adorno.security2316clase.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRol {
    ADMIN(Set.of(ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.STUDENT_WRITE)),
    GUEST(Set.of(ApplicationUserPermission.STUDENT_READ));


    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRol(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
        getGrantedAuthorities();
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissionsAuth = getPermissions().stream()
                //el map crea una nueva lista donde sustituye cada permiso por una authority de tipo simple
                //recuerda que getPermision() te da "student:read" o algo asi
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        //le add el usuario actual de la enumeracion y volvemos al ApllicationSecurityConfig
        permissionsAuth.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissionsAuth;
    }


}
