package usertoken.tokens.modell;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "active_tokens")
public class Token {
    @Id
    private long uid;
    private String token;

    public Token() {
    }

    public Token(long uid, String token) {
        this.uid = uid;
        this.token = token;
    }



    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "uid=" + uid +
                ", token='" + token + '\'' +
                '}';
    }
}
