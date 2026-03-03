package models;

public record AuthData(String userName, String authToken) {

    public AuthData(String userName, String authToken){
        this.userName = userName;
        this.authToken = authToken;
    }

    @Override
    public String userName() {
        return userName;
    }

    @Override
    public String authToken() {
        return authToken;
    }
}
