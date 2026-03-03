package dataaccess;

import models.*;
import chess.*;
import results.CreateGameResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDataAccess implements AuthDAO, GameDAO, UserDAO {

    private final Map<String, UserData> usersByUsername = new HashMap<>();
    private final Map<String, AuthData> usersByAuth = new HashMap<>();
    private final Map<Integer, GameData> gameById = new HashMap<>();
    private Integer gameId = 0;


    @Override
    public void create(UserData user) throws DataAccessException{
        try {
            usersByUsername.put(user.getUsername(), user);
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }

    @Override
    public UserData findByUsername(String username) throws DataAccessException {
        try {
            return usersByUsername.get(username);
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }


    @Override
    public void create(AuthData authData) throws DataAccessException {
        try {
            usersByAuth.put(authData.authToken(), authData);
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }

    @Override
    public AuthData findByAuth(String authToken) throws DataAccessException {
        try {
            return usersByAuth.get(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            usersByAuth.remove(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }

    @Override
    public CreateGameResult createGame(String name) throws DataAccessException {
        try {
            Integer newGameId = gameId;
            gameById.put(newGameId, new GameData(newGameId,null,null,name,new ChessGame()));
            gameId++;
            return new CreateGameResult(newGameId);
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        try {
            return gameById.values();
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try {
            this.usersByAuth.clear();
            this.usersByUsername.clear();
            this.gameById.clear();
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
    }
}
