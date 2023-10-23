package info.mayankag.springscale.user.controller;

import info.mayankag.springscale.response.ResponseHandler;
import info.mayankag.springscale.user.domain.*;
import info.mayankag.springscale.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@RestController
@Validated
@RequestMapping(path="/api/user")
@Tag(name = "User Accounts Module", description = "This module is intended to handle all the user accounts related operations.")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register", consumes = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    public ResponseEntity<Object> register(
            @Parameter(description="User register Dto.", required=true, schema=@Schema(implementation = UserRegisterDto.class))
            @Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserOutputDto userOutputDto = userService.registerUser(userRegisterDto);
        return ResponseHandler.generateResponse(
                "User registered",
                HttpStatus.CREATED,
                userOutputDto);
    }

    @PostMapping(path = "/login", consumes = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid Credentials")
    })
    public ResponseEntity<Object> login(
            @Parameter(description="User login Dto.", required=true, schema=@Schema(implementation = UserLoginDto.class))
            @Valid @RequestBody UserLoginDto userLoginDto) {
        String jwtToken = userService.loginUser(userLoginDto);
        return ResponseHandler.generateResponse(
                "User logged in successfully",
                HttpStatus.OK,
                jwtToken);
    }

    @PatchMapping(path = "/updateEmail", consumes = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User email updated successfully"),
            @ApiResponse(responseCode = "400", description = "User email cannot be changed due to email mismatch"),
            @ApiResponse(responseCode = "400", description = "User doesn't exists")
    })
    @SecurityRequirement(name = "JwtAuth")
    public ResponseEntity<Object> updateEmail(
            @Parameter(description="User update email Dto.", required=true, schema=@Schema(implementation = UserUpdateEmailDto.class))
            @Valid @RequestBody UserUpdateEmailDto userUpdateEmailDto) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.equals(userUpdateEmailDto.getOldEmail(), user.getEmail())) {
            UserOutputDto userOutputDto = userService.updateUserEmail(userUpdateEmailDto);
            return ResponseHandler.generateResponse(
                    "User email updated successfully",
                    HttpStatus.OK,
                    userOutputDto);
        } else {
            return ResponseHandler.generateResponse(
                    "User email cannot be changed due to email mismatch",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/updatePassword", consumes = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User password updated successfully"),
            @ApiResponse(responseCode = "400", description = "User password cannot be changed due to email mismatch"),
            @ApiResponse(responseCode = "400", description = "User doesn't exists")
    })
    @SecurityRequirement(name = "JwtAuth")
    public ResponseEntity<Object> updatePassword(
            @Parameter(description="User update password Dto.", required=true, schema=@Schema(implementation = UserUpdatePasswordDto.class))
            @Valid @RequestBody UserUpdatePasswordDto userUpdatePasswordDto) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.equals(userUpdatePasswordDto.getEmail(), user.getEmail())) {
            UserOutputDto userOutputDto = userService.updateUserPassword(userUpdatePasswordDto);
            return ResponseHandler.generateResponse(
                    "User password updated successfully",
                    HttpStatus.OK,
                    userOutputDto);
        } else {
            return ResponseHandler.generateResponse(
                    "User password cannot be changed due to email mismatch",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/findUserInAgeGroup")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list fetched successfully"),
            @ApiResponse(responseCode = "400", description = "No user present in this age group")
    })
    @SecurityRequirement(name = "JwtAuth")
    public ResponseEntity<Object> findUserInAgeGroup(
            @Valid @RequestParam("startAge") Integer startAge,
            @Valid @RequestParam("endAge") Integer endAge) {
        List<UserFilterListDto> userFilterListDtoList =  userService.findUserInAgeGroup(startAge,endAge);
        return ResponseHandler.generateResponse(
                "User list fetched successfully",
                HttpStatus.OK,
                userFilterListDtoList);
    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "User doesn't exists")
    })
    @SecurityRequirement(name = "JwtAuth")
    public ResponseEntity<Object> delete(
            @Parameter(description = "Email", required=true)
            @Valid @RequestParam("email") String email) {
        userService.deleteUser(email);
        return ResponseHandler.generateResponse(
                "User deleted successfully",
                HttpStatus.OK);
    }

}
