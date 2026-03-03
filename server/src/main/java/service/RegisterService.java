package service;

import Models.AuthData;
import Models.UserData;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;

public class RegisterService {

    private final DataAccess dataAccess;

    public RegisterService(DataAccess dataAccess){this.dataAccess = dataAccess;}


    public AuthData register(UserData user) throws Exception, DataAccessException {
        try {
            String username = user.userName();
            UserData userObject = dataAccess.getUser(username);
            if (userObject != null) {
                throw new Exception("AlreadyTakenException");
            } else {
                dataAccess.createUser(user);
                return dataAccess.createAuth(new AuthData(username, GenerateAuthToken.generateAuthToken()));
            }
        } catch (DataAccessException e) {
            throw e;
        }
    }

}
