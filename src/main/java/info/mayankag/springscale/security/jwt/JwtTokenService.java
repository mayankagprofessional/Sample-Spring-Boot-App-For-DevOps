package info.mayankag.springscale.security.jwt;

import info.mayankag.springscale.user.domain.User;

public interface JwtTokenService {

    String createJwtToken(User user);

    boolean verifyJwtToken(String jwtToken);

    String getUserNameFromJwtToken(String token);
}
