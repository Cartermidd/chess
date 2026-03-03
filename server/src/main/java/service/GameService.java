package service;

import dataaccess.*;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import exceptions.UnauthorizedException;
import models.GameData;
import requests.AuthorizedRequest;
import requests.CreateGameRequest;
import results.CreateGameResult;
import results.GenericSuccessfulResult;

import java.util.Collection;

public class GameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public Collection<GameData> listGames(AuthorizedRequest request) throws UnauthorizedException, DataAccessException {
        if (authDAO.findByAuth(request.getAuthToken()) == null){
            throw new UnauthorizedException("Unauthorized Error");
        }
        return gameDAO.listGames();
    }



    public CreateGameResult createGame(CreateGameRequest request) throws UnauthorizedException, DataAccessException, ImproperRequestException{
        if(CreateGameRequest.misformatted(request)){
            throw new ImproperRequestException("Misformatted Request");
        }
        if (authDAO.findByAuth(request.getAuthToken()) == null){
            throw new UnauthorizedException("Unauthorized Error");
        }
        return gameDAO.createGame(request.getGameName());
    }


    public GenericSuccessfulResult joinGame() throws UnauthorizedException, ImproperRequestException, AlreadyTakenException{


        return new GenericSuccessfulResult();
    }


}
