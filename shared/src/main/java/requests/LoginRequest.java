package requests;

public class LoginRequest {
    String username;
    String password;

    public LoginRequest( String username,String password) {
        this.password = password;
        this.username = username;
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