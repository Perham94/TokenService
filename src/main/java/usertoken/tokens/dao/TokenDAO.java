package usertoken.tokens.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usertoken.tokens.modell.Token;

import java.util.Optional;

public interface TokenDAO extends JpaRepository<Token, Long> {
    @Query(value = "SELECT * FROM active_tokens WHERE token = :token",
            nativeQuery = true)
    Optional<Token> findByToken(String token);
}
