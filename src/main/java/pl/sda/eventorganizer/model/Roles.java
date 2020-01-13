package pl.sda.eventorganizer.model;


import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    USER,
    ADMIN,
    ORGANIZER;

    @Override
    public String getAuthority() {
        return name();
    }
}
