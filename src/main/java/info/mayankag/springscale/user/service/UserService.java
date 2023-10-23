package info.mayankag.springscale.user.service;

import info.mayankag.springscale.user.domain.*;

import java.util.List;

public interface UserService {

    UserOutputDto registerUser(UserRegisterDto userRegisterDto);
    String loginUser(UserLoginDto userLoginDto);
    UserOutputDto updateUserEmail(UserUpdateEmailDto userUpdateEmailDto);
    UserOutputDto updateUserPassword(UserUpdatePasswordDto userUpdatePasswordDto);
    List<UserFilterListDto> findUserInAgeGroup(Integer startAge, Integer endAge);
    void deleteUser(String email);

}
