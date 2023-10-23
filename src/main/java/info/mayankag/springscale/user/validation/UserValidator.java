package info.mayankag.springscale.user.validation;

import  info.mayankag.springscale.user.domain.UserLoginDto;
import info.mayankag.springscale.security.config.PasswordEncoder;
import info.mayankag.springscale.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor
public class UserValidator{

    PasswordEncoder passwordEncoder;

    public void validatePassword(UserLoginDto userToVerify, User userStored){
        if(!passwordEncoder.bCryptPasswordEncoder()
                .matches(userToVerify.getPassword(), userStored.getPassword())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid credentials."
            );
        }
    }
}
