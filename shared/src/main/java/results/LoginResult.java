package results;

public class LoginResult {
    String userName;
    String authToken;

    public LoginResult(String userName, String authToken) {
        this.userName = userName;
        this.authToken = authToken;
    }
}
