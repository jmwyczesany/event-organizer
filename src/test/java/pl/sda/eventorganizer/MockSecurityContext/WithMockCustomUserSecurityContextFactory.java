package pl.sda.eventorganizer.MockSecurityContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import pl.sda.eventorganizer.UserPrincipal;
import pl.sda.eventorganizer.model.User;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {


    private PasswordEncoder passwordEncoder;

    public WithMockCustomUserSecurityContextFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        User customUser = new User();
        customUser.setUserName(mockCustomUser.userName());
        customUser.setEmail(mockCustomUser.email());
        customUser.setRole(mockCustomUser.role());

        UserPrincipal principal = new UserPrincipal(customUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, passwordEncoder.encode("password"), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
