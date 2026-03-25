package requests;

public class AuthorizedRequest extends AuthTokenCarrier {
    String authToken;

    public AuthorizedRequest(String authToken){
        this.authToken = authToken;
    }

    public String getAuthToken(){
        return this.authToken;
    }

    public static boolean misformatted(AuthorizedRequest request){
        if (request == null){return false;}
        return request.authToken == null;
    }
}
