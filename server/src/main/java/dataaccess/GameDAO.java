package dataaccess;

import models.GameData;
import results.CreateGameResult;

import java.util.Collection;

public interface GameDAO {


    CreateGameResult createGame(String name) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void clear() throws DataAccessException;
}
