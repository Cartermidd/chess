package service;

import Models.*;
import chess.*;
import dataaccess.*;
import java.util.UUID;

public class Service {

    private final DataAccess dataAccess;

    public Service(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData user) throws Exception {
        String username = user.userName();
        UserData userObject = dataAccess.getUser(username);
        if (userObject != null){
            throw new Exception("AlreadyTakenException");
        } else {
            dataAccess.createUser(user);
            return dataAccess.createAuth(new AuthData(username,generateAuthToken()));
        }
    }

    public AuthData login(UserData user){

        return 
    }

    public ChessGame newGame(ChessGame game) throws Exception {
        return dataAccess.addGame(game);
    }

    public GameList listGames() throws DataAccessException {
        return dataAccess.listGames();
    }

    public ChessGame getGame(int id) throws DataAccessException {
        validateId(id);
        return dataAccess.getGame(id);
    }

    public ChessGame updateGame(ChessGame game) throws Exception {
        return dataAccess.updateGame(game);
    }

    public void clearDB() throws DataAccessException {
        dataAccess.clear();
    }

    private void validateId(int id) throws Exception {
        if (id <= 0) {
            throw new DataAccessException(ResponseException.Code.ClientError, "Error: invalid pet ID");
        }
    }


    private String generateAuthToken(){
        return UUID.randomUUID().toString();
    }

}