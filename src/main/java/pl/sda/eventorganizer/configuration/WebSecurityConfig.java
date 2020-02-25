package pl.sda.eventorganizer.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import pl.sda.eventorganizer.UserPrincipalDetailsService;
import pl.sda.eventorganizer.UserPrincipal;
import pl.sda.eventorganizer.model.Roles;
import pl.sda.eventorganizer.service.UserService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoderConfiguration;
    private final DataSource dataSource;

    public WebSecurityConfig(UserService userService, PasswordEncoderConfig passwordEncoderConfiguration, DataSource dataSource) {
        this.userService = userService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
         auth
                 .authenticationProvider(daoAuthenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index", "/registration", "/allEvents/**", "/baza/**",
                        "/eventDetails**", "/css/**", "/error", "/eventSearch", "/result/**")
                .permitAll()
                .antMatchers("/addEvent")
                .hasAnyRole(Roles.ORGANIZER.name(), Roles.ADMIN.name())
                .anyRequest()
                .fullyAuthenticated()


                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/allEvents/page/1/", true)
                .failureForwardUrl("/login")
                .permitAll()

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/")
                .permitAll()

                .and()
                .userDetailsService(userService);


        //H2
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoderConfiguration.passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoderConfiguration.passwordEncoder());
        provider.setUserDetailsService(this.userService);

        return provider;
    }



}
