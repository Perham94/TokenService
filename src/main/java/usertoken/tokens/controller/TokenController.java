package usertoken.tokens.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usertoken.tokens.Service.TokenAuthenticationService;
import usertoken.tokens.dao.TokenDAO;
import usertoken.tokens.helper.TokenAuthenticationHelper;

@RestController
public class TokenController {
    @Autowired
    private TokenAuthenticationHelper tokenAuthenticationHelper;
    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private TokenDAO tokenDAO;

    public TokenController(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @RequestMapping("/validate_token")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {


        if (tokenAuthenticationService.validateToken(token)) {

            return ResponseEntity.status(HttpStatus.OK).body(tokenAuthenticationHelper.tokenParser(token));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token is not valid!");
    }

    @PostMapping("/create_token")
    public ResponseEntity<String> createToken(@RequestBody String jsonObject) {
        String name = new JSONObject(jsonObject).getString("username");


        if (!name.isEmpty()) {

            tokenAuthenticationHelper = new TokenAuthenticationHelper();
            JSONObject token_output = new JSONObject();
            token_output.put("token", tokenAuthenticationHelper.tokenBuilder(name));

            return ResponseEntity.status(HttpStatus.CREATED).body(token_output.toString());

        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Token failed to be created");
        }
    }
}
