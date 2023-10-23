package info.mayankag.springscale.user.service;

import info.mayankag.springscale.security.config.PasswordEncoder;
import info.mayankag.springscale.security.jwt.JwtTokenService;
import info.mayankag.springscale.user.domain.*;
import info.mayankag.springscale.user.repository.UserRepository;
import info.mayankag.springscale.user.validation.UserValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserOutputDto registerUser(UserRegisterDto userRegisterDto) {
        Optional<User> userOptional = userRepository.findByEmail(userRegisterDto.getEmail());
        if(userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.bCryptPasswordEncoder().encode(userRegisterDto.getPassword()))
                .age(userRegisterDto.getAge())
                .build();
        userRepository.save(user);
        return UserOutputDto.builder()
                .email(user.getEmail())
                .age(user.getAge())
                .token(jwtTokenService.createJwtToken(user))
                .build();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        User loginUser = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED, "Invalid Credentials"
                        )
                );
        userValidator.validatePassword(userLoginDto, loginUser);
        return jwtTokenService.createJwtToken(loginUser);
    }

    @Override
    public UserOutputDto updateUserEmail(UserUpdateEmailDto userUpdateEmailDto) {
        User user = userRepository.findByEmail(userUpdateEmailDto.getOldEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User doesn't exists"));
        user.setEmail(userUpdateEmailDto.getNewEmail());
        userRepository.save(user);
        return UserOutputDto.builder()
                .email(user.getEmail())
                .age(user.getAge())
                .token(jwtTokenService.createJwtToken(user))
                .build();
    }

    @Override
    public UserOutputDto updateUserPassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        User user = userRepository.findByEmail(userUpdatePasswordDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User doesn't exists"));
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(userUpdatePasswordDto.getPassword()));
        userRepository.save(user);
        return UserOutputDto.builder()
                .email(user.getEmail())
                .age(user.getAge())
                .token(jwtTokenService.createJwtToken(user))
                .build();
    }

    @Override
    public List<UserFilterListDto> findUserInAgeGroup(Integer startAge, Integer endAge) {
        List<User> userList = userRepository.findByAgeGroup(startAge, endAge);
        if(userList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user present in this age group");
        }
        List<UserFilterListDto> userFilterListDtoList = new ArrayList<>();
        for(User user : userList) {
            UserFilterListDto userFilterListDto = UserFilterListDto.builder()
                    .email(user.getEmail())
                    .age(user.getAge())
                    .build();
            userFilterListDtoList.add(userFilterListDto);
        }
        return userFilterListDtoList;
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            LOGGER.error("User doesn't exists - " + email + ", Date and Time : "+LocalDateTime.now());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists");
        }
    }
}
