package requests;

public class LoginRequest {
    String username;
    String password;

    public LoginRequest( String username,String password) {
        this.password = password;
        this.username = username;
    }

    public LoginRequest(String[] params) {
        this.username = (params.length > 0) ? params[0] : null;
        this.password = (params.length > 1) ? params[1] : null;
    }


    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }

    public static boolean misformatted(LoginRequest request){
        return request.username == null | request.password == null;
    }
}