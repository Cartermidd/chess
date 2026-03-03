package results;

public class RegisterResult {
    String username;
    String authToken;

    public RegisterResult(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }
}
