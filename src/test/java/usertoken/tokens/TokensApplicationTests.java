package usertoken.tokens;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import usertoken.tokens.dao.TokenDAO;
import usertoken.tokens.helper.TokenAuthenticationHelper;
import usertoken.tokens.modell.Token;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TokensApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    TokenDAO tokenDAO;

    @Test
    void validate_Token_Success() throws Exception {
        tokenDAO.deleteAll();
        TokenAuthenticationHelper tokenAuthenticationHelper = new TokenAuthenticationHelper();

        tokenDAO.save(new Token(0, tokenAuthenticationHelper.tokenBuilder("Tomte")));

        mvc.perform(MockMvcRequestBuilders.get("/validate_token/?token=" + "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBQ0RQIiwic3ViIjoiVG9tdGUifQ.TsL0q0bKGgTUE108MOwV1HAWqRLgs8MNNaASJzJip_Q")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Tomte"));

    }

    @Test
    void validate_Token_Invalid() throws Exception {
        tokenDAO.deleteAll();
        mvc.perform(MockMvcRequestBuilders.get("/validate_token/?token=" + "rsRHT54y44#werwt")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Token is not valid!"));

    }

    @Test
    void create_Token() throws Exception {
        TokenAuthenticationHelper tokenAuthenticationHelper = new TokenAuthenticationHelper();
        tokenDAO.deleteAll();
        tokenDAO.save(new Token(0, tokenAuthenticationHelper.tokenBuilder("Tomte")));

        mvc.perform(MockMvcRequestBuilders.post("/create_token")
                .contentType(MediaType.APPLICATION_JSON).content("{username:\"Tomte\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBQ0RQIiwic3ViIjoiVG9tdGUifQ.TsL0q0bKGgTUE108MOwV1HAWqRLgs8MNNaASJzJip_Q\"}"));

    }

    @Test
    void create_Token_Failed() throws Exception {

        tokenDAO.deleteAll();
        TokenAuthenticationHelper tokenAuthenticationHelper = new TokenAuthenticationHelper();
        tokenDAO.save(new Token(0, tokenAuthenticationHelper.tokenBuilder("")));
        mvc.perform(MockMvcRequestBuilders.post("/create_token")
                .contentType(MediaType.APPLICATION_JSON).content("{username:\"\"}"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Token failed to be created"));

    }

}
