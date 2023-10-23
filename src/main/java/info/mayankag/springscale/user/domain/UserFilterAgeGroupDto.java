package info.mayankag.springscale.user.domain;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterAgeGroupDto {

    private Integer startAge;

    private Integer endAge;
}
