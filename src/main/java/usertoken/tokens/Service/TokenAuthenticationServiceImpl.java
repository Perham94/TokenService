package usertoken.tokens.Service;

import org.springframework.stereotype.Service;
import usertoken.tokens.dao.TokenDAO;

@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {
    private TokenDAO tokenDao;

    public TokenAuthenticationServiceImpl(TokenDAO tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public boolean validateToken(String token) {
        return tokenDao.findByToken(token).isPresent();
    }

}

