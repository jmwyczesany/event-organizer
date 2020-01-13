package pl.sda.eventorganizer.dto;


import lombok.Data;
import pl.sda.eventorganizer.model.Roles;


@Data
public class RegisterForm {

    private String email;
    private String userName;
    private String password;
    private String confirmPassword;

    private Roles role = Roles.USER;


}
