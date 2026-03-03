package dataaccess;

import Models.*;


public interface DataAccess {

    UserData getUser(String userName) throws DataAccessException;

    void createUser(UserData user) throws DataAccessException;

    AuthData createAuth(AuthData authdata) throws DataAccessException;

    AuthData getAuth(String token) throws DataAccessException;

    void deleteAuth(AuthData token) throws DataAccessException;

    GameList listGames() throws DataAccessException;

    GameData createGame(GameData game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    GameData updateGame(GameData game) throws DataAccessException;

    void clear() throws DataAccessException;

}
