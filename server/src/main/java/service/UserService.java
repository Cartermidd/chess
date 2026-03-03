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
            throw new ImproperRequestException("misformatted request");
        }
        if (userDAO.findByUsername(request.getUsername()) != null)
        {
            throw new AlreadyTakenException("Username Taken");
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
            throw new ImproperRequestException("misformatted request");
        }
        UserData user = userDAO.findByUsername(request.getUsername());
        if (user == null){
            throw new UserDoesNotExistException("No User with that username");
        }
        if(Objects.equals(user.getPassword(), request.getPassword())){
            String authToken = GenerateAuthToken.generateAuthToken();
            authDAO.create(new AuthData(user.getUsername(),authToken));
            return new LoginResult(user.getUsername(),authToken);
        } else {
            throw new IncorrectPasswordException("Incorrect Password");
        }
    }

    public GenericSuccessfulResult logout(AuthorizedRequest request) throws DataAccessException, UnauthorizedException {
        AuthData data = authDAO.findByAuth(request.getAuthToken());
        if (data == null){
            throw new UnauthorizedException("Unauthorized Error");
        } else {
            authDAO.deleteAuth(data.authToken());
            return new GenericSuccessfulResult();
        }
    }


}
