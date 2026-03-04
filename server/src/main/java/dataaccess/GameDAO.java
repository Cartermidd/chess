package dataaccess;

import chess.ChessGame;
import models.GameData;
import results.CreateGameResult;
import results.ListGamesResult;

public interface GameDAO {

    CreateGameResult createGame(String name) throws DataAccessException;

    ListGamesResult listGames() throws DataAccessException;

    GameData findByID(Integer id) throws DataAccessException;

    void updateGame(Integer id, ChessGame.TeamColor color, String username) throws DataAccessException;

    void clear() throws DataAccessException;
}
