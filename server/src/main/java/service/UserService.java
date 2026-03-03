package service;

import dataaccess.*;
import exceptions.*;
import models.*;
import requests.*;
import results.*;

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

    public LoginResult login(LoginRequest request){
        return new LoginResult("Todd","Toddathy");
    }

    public GenericSuccessfulResult logout(AuthorizedRequest request) throws DataAccessException, UnauthorizedException {
        return new GenericSuccessfulResult();
    }


}
