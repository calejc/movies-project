package cale.spring.Movies.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationService {
    public String filename = "src/main/resources/authorized-usernames.txt";
    public static Map<String, String> readInAuthorizedUsers(String filename) throws IOException {
        File file = new File(filename);
        Map<String, String> users = new HashMap<>();
        String s;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while((s = br.readLine()) != null){
            String[] splitString = s.split(" ");
            users.put(splitString[0], splitString[1]);
        }
        return users;
    }
}
