package dataaccess;

import models.*;
import chess.ChessGame;
import org.mindrot.jbcrypt.BCrypt;
import results.CreateGameResult;
import results.ListGamesResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryDataAccess implements AuthDAO, GameDAO, UserDAO {

    private final Map<String, UserData> usersByUsername = new HashMap<>();
    private final Map<String, AuthData> usersByAuth = new HashMap<>();
    private final Map<Integer, GameData> gameById = new HashMap<>();
    private Integer gameId = 1;


    //if (not database){database = DatabaseManager.createdatabase
    //if not column creat column


    @Override
    public void create(UserData user) throws DataAccessException{
        try {
            String password = BCrypt.hashpw(user.password(), BCrypt.gensalt());
            UserData newUser = new UserData(user.getUsername(), password, user.email());
            usersByUsername.put(user.getUsername(), newUser);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public UserData findByUsername(String username) throws DataAccessException {
        try {
            return usersByUsername.get(username);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }


    @Override
    public void create(AuthData authData) throws DataAccessException {
        try {
            usersByAuth.put(authData.authToken(), authData);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public AuthData findByAuth(String authToken) throws DataAccessException {
        try {
            return usersByAuth.get(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            usersByAuth.remove(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
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
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public ListGamesResult listGames() throws DataAccessException {
        try {
            Collection<GameData> games = gameById.values();
            return new ListGamesResult(games);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public GameData findByID(Integer id) throws DataAccessException {
        try {
            return gameById.get(id);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public void updateGamePlayer(Integer id, ChessGame.TeamColor color, String username) throws DataAccessException {
        try{
            GameData game = gameById.get(id);
            gameById.remove(id);
            if (color == ChessGame.TeamColor.BLACK){
                GameData updatedGame = new GameData(id, game.whiteUsername(), username, game.gameName(), game.game());
                gameById.put(id, updatedGame);
            }
            if (color == ChessGame.TeamColor.WHITE){
                GameData updatedGame = new GameData(id, username, game.blackUsername(), game.gameName(), game.game());
                gameById.put(id, updatedGame);
            }
        } catch(Exception e){
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public void clearPlayer(Integer id, ChessGame.TeamColor color) throws DataAccessException{
        try{
            GameData game = gameById.get(id);
            gameById.remove(id);
            if (color == ChessGame.TeamColor.BLACK){
                GameData updatedGame = new GameData(id, game.whiteUsername(), null, game.gameName(), game.game());
                gameById.put(id, updatedGame);
            }
            if (color == ChessGame.TeamColor.WHITE){
                GameData updatedGame = new GameData(id, null, game.blackUsername(), game.gameName(), game.game());
                gameById.put(id, updatedGame);
            }
        } catch(Exception e){
            throw new DataAccessException(e + " Data Access Error");
        }
    }

    @Override
    public void updateGame(Integer id, ChessGame newGame) throws DataAccessException{
        try{
            GameData game = gameById.get(id);
            gameById.remove(id);
            GameData updatedGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), newGame);
            gameById.put(id, updatedGame);
        } catch(Exception e){
            throw new DataAccessException(e + " Data Access Error");
        }
    }


    @Override
    public void clear() throws DataAccessException {
        try {
            this.usersByAuth.clear();
            this.usersByUsername.clear();
            this.gameById.clear();
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }
}
