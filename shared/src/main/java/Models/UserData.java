
package Models;


public record UserData(String userName, String password, String email) {

    public UserData(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public boolean validator(){
        if(userName == null|password ==null){
            return false;
        } else {
            return true;
        }
    }

}
