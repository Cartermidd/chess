package dataaccess;

import models.AuthData;

public interface AuthDAO {

    void create(AuthData authData) throws DataAccessException;

    AuthData findByAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    void clear() throws DataAccessException;
}
