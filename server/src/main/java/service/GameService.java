package service;

import chess.ChessGame;
import dataaccess.*;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import exceptions.NoGameException;
import exceptions.UnauthorizedException;
import models.AuthData;
import models.GameData;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.CreateGameResult;
import results.GenericSuccessfulResult;
import results.ListGamesResult;

public class GameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public ListGamesResult listGames(String authToken) throws UnauthorizedException, DataAccessException {
        if(!isAuthorized(authToken)){
            throw new UnauthorizedException("Error: Unauthorized");
        }
        return gameDAO.listGames();
    }



    public CreateGameResult createGame(String authToken, CreateGameRequest request)
            throws UnauthorizedException, DataAccessException, ImproperRequestException{
        if(!isAuthorized(authToken)){
            throw new UnauthorizedException("Error: Unauthorized");
        } else if(CreateGameRequest.misformatted(request)){
            throw new ImproperRequestException("Error: Misformatted Request");
        } else {
            return gameDAO.createGame(request.getGameName());
        }
    }

    public GenericSuccessfulResult joinGame(String authToken, JoinGameRequest request)
            throws DataAccessException, UnauthorizedException, ImproperRequestException, NoGameException, AlreadyTakenException{
        if(!isAuthorized(authToken)){
            throw new UnauthorizedException("Error: Unauthorized");
        }
        if(JoinGameRequest.misformatted(request)) {
            throw new ImproperRequestException("Error: Misformatted Request");
        }
        ChessGame.TeamColor requestedColor = request.getPlayerColor();
        AuthData data = authDAO.findByAuth(authToken);
        String username = data.userName();
        GameData game = gameDAO.findByID(request.getGameId());
        if (game == null){
            throw new NoGameException("Error: No game by that ID");
        } else {
            if (requestedColor == ChessGame.TeamColor.BLACK) {
                if (game.blackUsername() == null) {
                    gameDAO.updateGame(request.getGameId(), ChessGame.TeamColor.BLACK, username);
                    return new GenericSuccessfulResult();
                } else {
                    throw new AlreadyTakenException("Error: Black is already taken");
                }
            }
            if (requestedColor == ChessGame.TeamColor.WHITE){
                if (game.whiteUsername() == null){
                    gameDAO.updateGame(request.getGameId(), ChessGame.TeamColor.WHITE, username);
                    return new GenericSuccessfulResult();
                } else {
                    throw new AlreadyTakenException("Error: White is already taken");
                }
            }
        }
        return null;
    }


    private boolean isAuthorized(String authToken) throws DataAccessException{
        if (authToken == null){
            return false;
        }
        AuthData data = authDAO.findByAuth(authToken);
        if (data == null) {
            return false;
        }else{
            return true;
        }
    }


}
