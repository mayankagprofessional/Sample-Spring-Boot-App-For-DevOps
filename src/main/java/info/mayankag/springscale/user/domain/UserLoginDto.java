package info.mayankag.springscale.user.domain;

import info.mayankag.springscale.user.validation.email.ValidEmail;
import info.mayankag.springscale.user.validation.password.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotNull
    @NotEmpty
    @ValidEmail
    String email;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;
}
