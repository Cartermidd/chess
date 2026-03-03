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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public static boolean misformatted(RegisterRequest request){
        return request.username == null | request.password == null;
    }
}
