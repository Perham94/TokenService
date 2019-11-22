package usertoken.tokens.Service;

public interface TokenAuthenticationService {
    boolean validateToken(String token);
}
