package dataaccess;

import models.UserData;


public interface UserDAO {

    void create(UserData user) throws DataAccessException;

    UserData findByUsername(String username) throws DataAccessException;

    void clear() throws DataAccessException;

}
