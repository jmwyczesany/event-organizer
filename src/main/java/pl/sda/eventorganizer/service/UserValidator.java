package pl.sda.eventorganizer.service;


import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventorganizer.dto.LoginForm;
import pl.sda.eventorganizer.dto.RegisterForm;
import pl.sda.eventorganizer.model.User;

@Service
public class UserValidator implements Validator {


    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        RegisterForm registerForm = (RegisterForm) o;

        User user = User.UserBuilder
                .anUser()
                .withEmail(registerForm.getEmail())
                .withUserName(registerForm.getUserName())
                .withPassword(registerForm.getPassword())
                .withConfirmPassword(registerForm.getConfirmPassword())
                .build();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "Not Empty", "All field are required");

        if(userService.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "Duplicate.registerForm.email", "An account with this email address is already defined in our database");
        }
        if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            errors.rejectValue("email", "NotEmail", "Please use proper email address.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "Not Empty","All field are required");
        if (userService.findByUsername(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.registerForm.username", "Username taken. Please choose a different username.");
        }
        if (user.getUserName().length() < 3 || user.getUserName().length() > 25) {
            errors.rejectValue("userName", "Size.registerForm.username", "Username must be between 3 and 25 characters long");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "All fields are required.");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.registerForm.password", "Password must be between 8 and 30 characters long");
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.registerForm.confirmPassword", "Passwords don't match.");
        }

    }

    public void validateLogin(Object o, Errors errors) {
        LoginForm loginForm = (LoginForm) o;

        User user = User.UserBuilder
                .anUser()
                .withEmail(loginForm.getEmail())
                .withPassword(loginForm.getPassword())
                .build();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "All fields are required.");
        if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            errors.rejectValue("email", "NotEmail", "Please use proper email address.");
        }
        if (!userService.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "WrongEmailOrPassword", "Wrong email or password.");
        } else {

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "All fields are required.");
            if (!userService.findByEmail(loginForm.getEmail()).equals(loginForm.getPassword())) {
                errors.rejectValue("password", "WrongEmailOrPassword", "Wrong email or password.");
            }


        }
    }
}
