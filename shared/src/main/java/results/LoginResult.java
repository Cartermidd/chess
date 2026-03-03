package results;

public class LoginResult {
    String username;
    String authToken;

    public LoginResult(String userName, String authToken) {
        this.username = userName;
        this.authToken = authToken;
    }


    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }
}
