package dataaccess;

import models.AuthData;

public interface AuthDAO {

    void create(AuthData authData) throws DataAccessException;

    AuthData findByAuth(String authtoken) throws DataAccessException;

    void clear() throws DataAccessException;
}
