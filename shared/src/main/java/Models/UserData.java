
package Models;


public record UserData(String userName, String password, String email) {

    public UserData(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

}
