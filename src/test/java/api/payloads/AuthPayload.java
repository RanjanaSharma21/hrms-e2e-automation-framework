package api.payloads;

import java.util.HashMap;
import java.util.Map;

public class AuthPayload {

    // Clean, dynamic Payload Wrapper where you do not write JSON strings (JSON body)
    // using \" escape character. Instead, you write a clean Java utility method that wraps
    // credentials inside a native Java collection (Map) or a dedicated model object (POJO).
    // RestAssured then acts as the "wrapper engine," converting it dynamically to raw JSON at runtime.
    public static Map<String,String> loginPayload(
            String username,
            String password){

        Map<String,String> payload = new HashMap<>();

        payload.put("username", username);
        payload.put("password", password);

        return payload;
    }
}
