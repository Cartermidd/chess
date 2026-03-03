package dataaccess;

import models.*;
import chess.*;
import java.util.HashMap;
import java.util.Map;

public class MemoryDataAccess implements AuthDAO, GameDAO, UserDAO {

    private final Map<String, UserData> usersByUsername = new HashMap<>();
    private final Map<String, AuthData> usersByAuth = new HashMap<>();




    @Override
    public void create(UserData user){
        usersByUsername.put(user.getUsername(), user);
    }

    @Override
    public UserData findByUsername(String username) throws DataAccessException {
        return usersByUsername.get(username);
    }


    @Override
    public void create(AuthData authData) throws DataAccessException {
        usersByAuth.put(authData.authToken(), authData);
    }

    @Override
    public AuthData findByAuth(String authtoken) throws DataAccessException {
        return usersByAuth.get(authtoken);
    }

    @Override
    public void clear() throws DataAccessException {
        this.usersByAuth.clear();
        this.usersByUsername.clear();
    }
}
