package pl.sda.eventorganizer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.model.Roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
//        extract list of permissions (only name)
        this.user.getPermissionsAsList().forEach(p ->
        {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });
//          I need role too! It's like really big authority with syntax ROLE_NAME
        Roles role = this.user.getRole();
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role);
        authorities.add(roleAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive() == 1;
    }
}
