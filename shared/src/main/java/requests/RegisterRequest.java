package requests;

public class RegisterRequest {
    String username;
    String password;
    String email;

    public RegisterRequest(String username,String password,String email){
        this.username = username;
        this.password = password;
        this.email = email;
}



    public static boolean misformatted(RegisterRequest request){
        return request.username == null | request.password == null;
    }
}
