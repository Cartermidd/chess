package dataaccess;

import chess.ChessGame;
import models.GameData;
import results.CreateGameResult;
import results.ListGamesResult;

import java.util.Collection;

public interface GameDAO {

    CreateGameResult createGame(String name) throws DataAccessException;

    ListGamesResult listGames() throws DataAccessException;

    GameData findByID(Integer ID) throws DataAccessException;

    void updateGame(Integer ID, ChessGame.TeamColor color, String username) throws DataAccessException;

    void clear() throws DataAccessException;
}
