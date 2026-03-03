package dataaccess;

import Models.*;
import chess.*;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess {
    private int nextId = 1;
    final private HashMap<Integer, ChessGame> chessGames = new HashMap<>();

    public ChessGame newGame(ChessGame game){
        game = new ChessGame(nextId++, ....);

        chessGames.put(game.id(), game);
        return game;
    }

    @Override
    public UserData getUser(String userName) throws DataAccessException {
        try{
            if (userName in DB){
                UserData user = DB(userName);
                return user;
            }else{
                return null;
            }
        } catch(DataAccessException e){
            throw e("getUser Access Error");
        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }

    @Override
    public AuthData createAuth(AuthData authdata) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(AuthData token) throws DataAccessException {

    }

    public GameList listGames() {return new GameList(chessGames.values());}

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        return null;
    }

    public ChessGame getGame(int id){return chessGames.get(id);}

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }

    public void deleteGame(int id){chessGames.remove(id);}

    public void deleteAllGames(){chessGames.clear();}
}
