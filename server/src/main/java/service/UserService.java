package service;

import dataaccess.*;
import exceptions.*;
import models.*;
import org.eclipse.jetty.server.Authentication;
import requests.*;
import results.*;

import java.util.Objects;

public class UserService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public LoginResult register(RegisterRequest request) throws DataAccessException, ImproperRequestException, AlreadyTakenException {
        if (RegisterRequest.misformatted(request)) {
            throw new ImproperRequestException("Error: misformatted request");
        }
        if (userDAO.findByUsername(request.getUsername()) != null)
        {
            throw new AlreadyTakenException("Error: Username Taken");
        }
        String authToken = GenerateAuthToken.generateAuthToken();

        UserData user = new UserData(request.getUsername(), request.getPassword(), request.getEmail());
        userDAO.create(user);

        AuthData authUser = new AuthData(request.getUsername(), authToken);
        authDAO.create(authUser);

        return new LoginResult(user.getUsername(), authToken);
    }

    public LoginResult login(LoginRequest request) throws DataAccessException, UserDoesNotExistException, ImproperRequestException, IncorrectPasswordException{
        if (LoginRequest.misformatted(request)){
            throw new ImproperRequestException("Error: misformatted request");
        }
        UserData user = userDAO.findByUsername(request.getUsername());
        if (user == null){
            throw new UserDoesNotExistException("Error: No User with that username");
        }
        if(Objects.equals(user.getPassword(), request.getPassword())){
            String authToken = GenerateAuthToken.generateAuthToken();
            authDAO.create(new AuthData(user.getUsername(),authToken));
            return new LoginResult(user.getUsername(),authToken);
        } else {
            throw new IncorrectPasswordException("Error: Incorrect Password");
        }
    }

    public GenericSuccessfulResult logout(String authToken) throws DataAccessException, UnauthorizedException {
        if (authToken == null){
            throw new UnauthorizedException("Error: No AuthToken");
        }
        AuthData data = authDAO.findByAuth(authToken);
        if (data == null){
            throw new UnauthorizedException("Error: Unauthorized");
        } else {
            authDAO.deleteAuth(data.authToken());
            return new GenericSuccessfulResult();
        }
    }

}
