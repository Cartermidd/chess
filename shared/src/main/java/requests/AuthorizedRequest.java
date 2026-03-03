package requests;

public class AuthorizedRequest {
    String authToken;

    public AuthorizedRequest(String authToken){
        this.authToken = authToken;
    }

    public String getAuthToken(){
        return this.authToken;
    }
}
