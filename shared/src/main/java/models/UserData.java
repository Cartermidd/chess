
package models;


public record UserData(String userName, String password, String email) {

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


}
