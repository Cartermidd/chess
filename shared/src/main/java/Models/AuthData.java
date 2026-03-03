package Models;

public record AuthData(String userName, String authToken) {

    public AuthData(String userName, String authToken){
        this.userName = userName;
        this.authToken = authToken;
    }

}
