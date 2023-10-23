package info.mayankag.springscale.user.domain;

import info.mayankag.springscale.user.validation.email.ValidEmail;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputDto {

    @NotNull
    @NotEmpty
    @ValidEmail
    String email;

    @NotNull
    private Integer age;

    @NotNull
    private String token;
}
