package dataaccess;

import models.chess.ChessBoard;
import models.chess.ChessGame;
import models.GameData;
import results.CreateGameResult;
import results.ListGamesResult;

public interface GameDAO {

    CreateGameResult createGame(String name) throws DataAccessException;

    ListGamesResult listGames() throws DataAccessException;

    GameData findByID(Integer id) throws DataAccessException;

    void updateGamePlayer(Integer id, ChessGame.TeamColor color, String username) throws DataAccessException;

    void updateGame(Integer id, ChessGame newGame) throws DataAccessException;

    void clearPlayer(Integer id, ChessGame.TeamColor color) throws DataAccessException;

    void clear() throws DataAccessException;
}
