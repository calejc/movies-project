package cale.spring.Movies.service;

import cale.spring.Movies.authorization.Authorized;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationService {


    private final String FILENAME = "src/main/resources/authorized-usernames.txt";
//    private final String FILENAME = "src/main/resources/authorized-usernames-example.txt";
    Map<String, String> authorizationMap = new HashMap<>();

    public AuthorizationService() throws IOException {
        readInAuthorizedUsers(FILENAME);
    }

    public void readInAuthorizedUsers(String filename) throws IOException {
        File file = new File(filename);
        Map<String, String> users = new HashMap<>();
        String s;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while((s = br.readLine()) != null){
            String[] splitString = s.split(" ");
            users.put(splitString[0], splitString[1]);
        }
        this.authorizationMap = users;
    }

    public Authorized authorized(Principal principal) {
        Authorized authorized = new Authorized();
        String userName = (String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login");
        if (authorizationMap.containsKey(userName)){
            authorized.setCreate(authorizationMap.get(userName).contains("create"));
            authorized.setUpdate(authorizationMap.get(userName).contains("update"));
            authorized.setDelete(authorizationMap.get(userName).contains("delete"));
        }
        authorized.setReturnMessage();
        return authorized;
    }
}
